package net.apollosoft.ats.audit.web.audit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.apollosoft.ats.audit.search.ReportSearchCriteria;
import net.apollosoft.ats.audit.security.UserPreferences;
import net.apollosoft.ats.audit.service.AuditService;
import net.apollosoft.ats.audit.service.DocumentService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.web.BaseController;
import net.apollosoft.ats.domain.ContentTypeEnum;
import net.apollosoft.ats.domain.IDocument;
import net.apollosoft.ats.util.DateUtils;
import net.apollosoft.ats.util.ThreadLocalUtils;
import net.apollosoft.ats.web.WebUtils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Controller
public class DocumentController extends BaseController
{

    private final DocumentService documentService;

    private final AuditService auditService;

    @Autowired
    public DocumentController(@Qualifier("beanFactory") BeanFactory beanFactory,
        @Qualifier("documentService") DocumentService documentService,
        @Qualifier("auditService") AuditService auditService)
    {
        super(beanFactory);
        this.documentService = documentService;
        this.auditService = auditService;
    }

    @RequestMapping(value = "/document/download.do", params = "dispatch=report")
    public String downloadReport(
        @RequestParam(value = "documentId", required = true) Long documentId,
        HttpServletResponse response) throws ServiceException, IOException
    {
        IDocument entity = documentService.findById(documentId);
        WebUtils.downloadContent(response, entity.getContent(), entity.getContentType(), entity
            .getName());
        return null;
    }

    @RequestMapping(value = "/document/download.do", params = "dispatch=comment")
    public String downloadComment(
        @RequestParam(value = "documentId", required = true) Long documentId,
        HttpServletResponse response) throws ServiceException, IOException
    {
        IDocument entity = documentService.findById(documentId);
        WebUtils.downloadContent(response, entity.getContent(), entity.getContentType(), entity
            .getName());
        return null;
    }

    @RequestMapping(value = "/document/export.do", params = "dispatch=action")
    public String export(
        @ModelAttribute("criteria") ReportSearchCriteria criteria,
        //exportName - "report" or "action"
        @RequestParam(value = "exportName", required = false) String exportName,
        @RequestParam(value = "reportTypeName", required = false) String reportTypeName,
        @RequestParam(value = "searchUserName", required = false) String searchUserName,
        @RequestParam(value = "businessStatusName", required = false) String businessStatusName,
        @RequestParam(value = "ratingName", required = false) String ratingName,
        HttpServletResponse response) throws ServiceException, IOException
    {
        //TODO: split
        //where this extract is called from
        UserPreferences prefs = getUserPreferences();
        if ("report".equals(exportName))
        {
            //set criteria
            criteria.setAuditGroupDivision(true);
            //reset id, if no name selected
            if (StringUtils.isBlank(reportTypeName))
            {
                criteria.setReportTypeId(null);
            }
            //update UserPreferences
            prefs.setGroupDivisions(criteria.getAuditGroupDivisions());
            prefs.setIssuedDateTo(criteria.getIssuedDateTo());
            prefs.setIssuedDateFrom(criteria.getIssuedDateFrom());
            //prefs.getAudit().setId(criteria.getAuditId()); //can be partial search
            prefs.getAudit().setName(criteria.getSearchAuditName());
            prefs.getReportType().setName(reportTypeName);
            if (ArrayUtils.isEmpty(criteria.getAvailableGroupDivisions()))
            {
                criteria.setAuditGroupDivisions(null); // ALL
            }
        }
        else if ("action".equals(exportName))
        {
            //set criteria
            criteria.setAuditGroupDivision(false);
            //reset id, if no name selected
            if (StringUtils.isBlank(reportTypeName))
            {
                criteria.setReportTypeId(null);
            }
            if (StringUtils.isBlank(searchUserName))
            {
                criteria.getResponsibleUser().setId(null);
            }
            if (StringUtils.isBlank(businessStatusName))
            {
                criteria.setBusinessStatusId(null);
            }
            if (StringUtils.isBlank(ratingName))
            {
                criteria.setRatingId(null);
            }
            //update UserPreferences
            prefs.setGroupDivisions(criteria.getActionGroupDivisions());
            prefs.setDueDateTo(criteria.getDueDateTo());
            prefs.setDueDateFrom(criteria.getDueDateFrom());
            prefs.setClosedDateTo(criteria.getClosedDateTo());
            prefs.setClosedDateFrom(criteria.getClosedDateFrom());
            //prefs.getAudit().setId(criteria.getAuditId()); //can be partial search
            prefs.getAudit().setName(criteria.getSearchAuditName());
            prefs.getReportType().setName(reportTypeName);
            prefs.getResponsibleUser().setName(searchUserName);
            prefs.getBusinessStatus().setName(businessStatusName);
            prefs.getActionStatus().setId(criteria.getActionStatusId());
            prefs.getRating().setName(ratingName);
            if (ArrayUtils.isEmpty(criteria.getAvailableGroupDivisions()))
            {
                criteria.setActionGroupDivisions(null); // ALL
            }
        }
        else
        {
            throw new ServiceException("Unknown exportName: " + exportName);
        }

        //ContentTypeEnum contentType = ContentTypeEnum.TEXT_CSV;
        ContentTypeEnum contentType = ContentTypeEnum.APPLICATION_EXCEL;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        auditService.export(criteria, output, contentType);
        String contentName = exportName + "-export-"
            + DateUtils.DD_MMM_YYYY_HHMM.format(ThreadLocalUtils.getDate()) + "."
            + contentType.getDefaultExt();
        WebUtils.downloadContent(response, output.toByteArray(), contentType.getContentType(),
            contentName);
        return null;
    }

}