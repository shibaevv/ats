package net.apollosoft.ats.domain.hibernate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import net.apollosoft.ats.domain.ITemplate;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLockType;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_template")
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
@Cache(region = "refdata", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Template extends Auditable<Long> implements ITemplate
{

    /** serialVersionUID */
    private static final long serialVersionUID = 8110387967964511105L;

    /** identity persistent field */
    @Id
    @Column(name = "template_id", nullable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long templateId;

    /** persistent field */
    @Basic()
    @Column(name = "template_ref", nullable = false)
    private String reference;

    /** persistent field */
    @Basic()
    @Column(name = "template_name", nullable = false)
    private String name;

    /** persistent field */
    @Lob()
    @Column(name = "template_detail", nullable = true)
    private String detail;

    /** persistent field */
    @Lob()
    @Column(name = "template_content", nullable = false)
    private byte[] content;

    /** persistent field */
    @Basic()
    @Column(name = "template_content_type", nullable = false)
    private String contentType;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getTemplateId();
    }

    /**
     *
     * @return
     */
    public Long getTemplateId()
    {
        if (templateId != null && templateId.longValue() == 0L)
        {
            templateId = null;
        }
        return templateId;
    }

    /**
     *
     * @param templateId
     */
    public void setTemplateId(Long templateId)
    {
        this.templateId = templateId;
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