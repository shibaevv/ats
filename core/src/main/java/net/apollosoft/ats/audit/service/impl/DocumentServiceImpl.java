package net.apollosoft.ats.audit.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.apollosoft.ats.audit.dao.DocumentDao;
import net.apollosoft.ats.audit.service.DocumentService;
import net.apollosoft.ats.audit.service.SecurityService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.IDocument;
import net.apollosoft.ats.domain.ITemplate;
import net.apollosoft.ats.domain.dto.DocumentDto;
import net.apollosoft.ats.domain.dto.security.UserDto;
import net.apollosoft.ats.domain.hibernate.Document;
import net.apollosoft.ats.util.DateUtils;
import net.apollosoft.ats.util.ThreadLocalUtils;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.ListTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class DocumentServiceImpl implements DocumentService
{

    /** logger. */
    //private static final Log LOG = LogFactory.getLog(DocumentServiceImpl.class);

    /** used for login details in generated documents */
    private String baseUrl;

    @Autowired
    @Qualifier("documentDao")
    //@Resource(name="documentDao")
    private DocumentDao documentDao;

    @Autowired
    @Qualifier("securityService")
    //@Resource(name="securityService")
    private SecurityService securityService;

    /**
     * Export header
     */
    private static final String[] HEADER_EXPORT =
    {
        "Report Id",
        "Report Name", "Report Date Issued",
        null, "Report Type",
        "Report Group & Division",
        //"Report Team RM",
        "Report OWNER",
        //"Report Classification",
        null, null,
        "Issue Id",
        "Issue No",
        "Issue Title", "Issue Findings",
        null, "Rating",
        //"Issue Classification",
        "Parent Risk Category",
        "Parent Risk",
        //"Control Theme",
        null, null,
        "Action Id",
        "Action No", "Agreed Action", "Person Responsible",
        "Action Group & Division Responsible", "Responsible OWNER", "Due Date",
        //"Action Management Initiated Action",
        null, "Business Status",
        //"Action Classification",
        "Business Date Closed", "Last Comment", "Last Comment username",
        "Last Comment date", "Follow-up status", "Follow-up Status date",
        null, null,
    };

    /**
     * @param baseUrl the baseUrl to set
     */
    public void setBaseUrl(String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.AuditService#findById(java.lang.Long)
     */
    public IDocument findById(Long documentId) throws ServiceException
    {
        try
        {
            Document entity = documentDao.findById(documentId);
            IDocument result = convert(entity);
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.DocumentService#exportCsv(java.util.List, java.io.OutputStream)
     */
    public void exportCsv(List<Object[]> tableData, OutputStream output) throws ServiceException
    {
        try
        {
            CSVWriter writer = new CSVWriter(new OutputStreamWriter(output));
            try
            {
                writer.writeNext(HEADER_EXPORT);
                for (Object[] reportData : tableData)
                {
                    String[] line =
                    {

                    };
                    writer.writeNext(line);
                }
            }
            finally
            {
                writer.close();
            }
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.DocumentService#exportExcel(java.util.List, java.io.OutputStream)
     */
    public void exportExcel(List<Object[]> tableData, OutputStream output) throws ServiceException
    {
        try
        {
            exportExcelPOI(tableData, HEADER_EXPORT, output);
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 
     * @param tableData
     * @param headerData
     * @param output
     * @throws IOException
     */
    //@SuppressWarnings("unused")
    private void exportExcelPOI(List<Object[]> tableData, String[] headerData,
        OutputStream output) throws IOException
    {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        int r = 0;
        //header - 0
        HSSFRow header = sheet.createRow(r);
        for (int c = 0; c < headerData.length; c++)
        {
            HSSFCell cell = header.createCell(c);
            cell.setCellValue(headerData[c]);
        }
        //data - 1..N
        for (Object[] rowData : tableData)
        {
            HSSFRow row = sheet.createRow(++r);
            for (int c = 0; c < headerData.length && c < rowData.length; c++)
            {
                final Object value = rowData[c];
                HSSFCell cell = row.createCell(c);
                if (value == null)
                {
                    cell.setCellValue("");
                }
                else if (value instanceof String)
                {
                    cell.setCellValue((String) value);
                }
                else if (value instanceof Date)
                {
                    cell.setCellValue((Date) value);
                }
                else if (value instanceof Number)
                {
                    cell.setCellValue(((Number) value).doubleValue());
                }
                else if (value instanceof Clob)
                {
                    try
                    {
                        cell.setCellValue(IOUtils.toString(((Clob) value).getAsciiStream()));
                    }
                    catch (SQLException e)
                    {
                        throw new IOException(e.getMessage());
                    }
                }
                else
                {
                    cell.setCellValue(value.toString());
                }
            }
        }
        //save wb to output
        wb.write(output);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.DocumentService#createDocument(net.apollosoft.ats.audit.domain.IAudit, net.apollosoft.ats.audit.domain.hibernate.Template, java.io.OutputStream)
     */
    public void createDocument(ITemplate template, Map<String, Object> params, OutputStream output)
        throws ServiceException
    {
        try
        {
            //init engine
            VelocityEngine ve = new VelocityEngine();
            ve.init();
            //set context
            VelocityContext context = new VelocityContext();
            for (Map.Entry<String, Object> entry : params.entrySet())
            {
                context.put(entry.getKey(), entry.getValue());
            }
            if (!params.containsKey("url"))
            {
                context.put("url", baseUrl);
            }
            context.put("dateTool", new DateTool());
            //context.put("listTool", new LoopTool()); // TODO: use
            context.put("listTool", new ListTool());
            context.put("datePattern", DateUtils.DEFAULT_DATE_PATTERN);
            //generate template
            UserDto user = securityService.getUser();
            String logTag = template.getReference() + "." + user.getId() + "."
                + DateUtils.DD_MMM_YYYY_HHMM.format(ThreadLocalUtils.getDate()) + ".log";
            StringWriter writer = new StringWriter();
            if (!ve.evaluate(context, writer, logTag, new String(template.getContent())))
            {
                throw new ServiceException("see Velocity runtime log for more details: " + logTag);
            }

            output.write(writer.getBuffer().toString().getBytes());
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 
     * @param entity
     * @return
     * @throws DomainException
     */
    private DocumentDto convert(Document entity) throws DomainException
    {
        //copy plain properties
        DocumentDto result = new DocumentDto(entity);
        //
        result.setContent(entity.getContent());
        //
        return result;
    }

}