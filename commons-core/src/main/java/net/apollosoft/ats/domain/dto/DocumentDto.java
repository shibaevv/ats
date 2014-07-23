package net.apollosoft.ats.domain.dto;

import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.IDocument;
import net.apollosoft.ats.util.BeanUtils;
import net.apollosoft.ats.util.Formatter;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class DocumentDto extends AuditableDto<Long> implements IDocument
{

    private Long documentId;

    private String name;

    private String detail;

    private byte[] content;

    private String contentType;

    /**
     * 
     */
    public DocumentDto()
    {
        super();
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public DocumentDto(IDocument source) throws DomainException
    {
        BeanUtils.copyProperties(source, this, IDocument.IGNORE);
        //TODO: re-factor to jsp ${fn:replace(entity.name,'"','\'')}
        name = Formatter.formatJson(name, true);
        detail = Formatter.formatJson(detail, false); 
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getDocumentId();
    }

    /**
     * @return the id
     */
    public Long getDocumentId()
    {
        return documentId;
    }

    /**
     * @param documentId the documentId to set
     */
    public void setDocumentId(Long documentId)
    {
        this.documentId = documentId;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the extension
     */
    public String getExt()
    {
        int idx = name == null ? -1 : (name.lastIndexOf('.') + 1);
        return idx >= 0 && name.length() > idx ? name.substring(idx).toLowerCase() : "";
    }

    /**
     * @return the detail
     */
    public String getDetail()
    {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    /**
     * @return the content
     */
    public byte[] getContent()
    {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(byte[] content)
    {
        this.content = content;
    }

    /**
     * @return the contentType
     */
    public String getContentType()
    {
        if (StringUtils.isBlank(contentType))
        {
            contentType = "text/plain";
        }
        return contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

}