package net.apollosoft.ats.audit.domain.hibernate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.apollosoft.ats.domain.hibernate.Auditable;
import net.apollosoft.ats.domain.hibernate.Document;

import org.hibernate.annotations.OptimisticLockType;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@IdClass(CommentDocumentPK.class)
@Table(name = "ats_comment_document")
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
//@Cache(region = "audit", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class CommentDocument extends Auditable<CommentDocumentPK>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -8594796219908945270L;

    /** persistent field */
    @Id
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Comment.class)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    /** persistent field */
    @Id
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Document.class)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public CommentDocumentPK getId()
    {
        return new CommentDocumentPK(getComment(), getDocument());
    }

    /**
     * @return the comment
     */
    public Comment getComment()
    {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(Comment comment)
    {
        this.comment = comment;
    }

    /**
     * @return the document
     */
    public Document getDocument()
    {
        return document;
    }

    /**
     * @param document the document to set
     */
    public void setDocument(Document document)
    {
        this.document = document;
    }

}