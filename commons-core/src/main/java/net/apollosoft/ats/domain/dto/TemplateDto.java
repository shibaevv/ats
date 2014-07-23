package net.apollosoft.ats.domain.dto;

import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.ITemplate;
import net.apollosoft.ats.util.BeanUtils;
import net.apollosoft.ats.util.Formatter;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class TemplateDto extends AuditableDto<Long> implements ITemplate
{

    private Long templateId;

    private String reference;

    private String name;

    private String detail;

    private byte[] content;

    private String contentText;

    private String contentType;

    /**
     * 
     */
    public TemplateDto()
    {
        super();
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public TemplateDto(ITemplate source) throws DomainException
    {
        BeanUtils.copyProperties(source, this, ITemplate.IGNORE);
        //TODO: re-factor to jsp ${fn:replace(entity.name,'"','\'')}
        name = Formatter.formatJson(name, true);
        detail = Formatter.formatJson(detail, false);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getTemplateId();
    }

    /**
     * @return the id
     */
    public Long getTemplateId()
    {
        return templateId;
    }

    /**
     * @param templateId the templateId to set
     */
    public void setTemplateId(Long templateId)
    {
        this.templateId = templateId;
    }

    /**
     * @return the reference
     */
    public String getReference()
    {
        return reference;
    }

    /**
     * @param reference the reference to set
     */
    public void setReference(String reference)
    {
        this.reference = reference;
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
     * @return the contentText
     */
    public String getContentText()
    {
        if (content != null && content.length > 0)
        {
            contentText = new String(content).trim();
        }
        return contentText;
    }

    /**
     * @param contentText the contentText to set
     */
    public void setContentText(String contentText)
    {
        this.contentText = contentText;
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