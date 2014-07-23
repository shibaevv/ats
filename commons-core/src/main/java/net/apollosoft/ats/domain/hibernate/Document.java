package net.apollosoft.ats.domain.hibernate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import net.apollosoft.ats.domain.IDocument;

import org.hibernate.annotations.OptimisticLockType;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_document")
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
//@Cache(region = "audit", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Document extends Auditable<Long> implements IDocument
{

    /** serialVersionUID */
    private static final long serialVersionUID = -3252890555214248847L;

    /** identity persistent field */
    @Id
    @Column(name = "document_id", nullable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long documentId;

    /** persistent field */
    @Basic()
    @Column(name = "document_name", nullable = false)
    private String name;

    /** persistent field */
    @Lob()
    @Column(name = "document_detail", nullable = true)
    private String detail;

    /** persistent field */
    @Lob()
    @Column(name = "document_content", nullable = false)
    private byte[] content;

    /** persistent field */
    @Basic()
    @Column(name = "document_content_type", nullable = false)
    private String contentType;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getDocumentId();
    }

    /**
     *
     * @return
     */
    public Long getDocumentId()
    {
        if (documentId != null && documentId.longValue() == 0L)
        {
            documentId = null;
        }
        return documentId;
    }

    /**
     *
     * @param documentId
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