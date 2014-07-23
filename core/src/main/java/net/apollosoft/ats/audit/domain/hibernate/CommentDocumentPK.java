package net.apollosoft.ats.audit.domain.hibernate;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import net.apollosoft.ats.audit.domain.IComment;
import net.apollosoft.ats.domain.hibernate.Document;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Embeddable
public class CommentDocumentPK implements Serializable
{

    /** serialVersionUID */
    private static final long serialVersionUID = -3635313570082214968L;

    /** persistent field */
    //@Id
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Comment.class)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    /** persistent field */
    //@Id
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Document.class)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    public CommentDocumentPK()
    {
        super();
    }

    public CommentDocumentPK(Comment comment, Document document)
    {
        this.comment = comment;
        this.document = document;
    }

    /**
     * @return the comment
     */
    public IComment getComment()
    {
        return comment;
    }

//    /**
//     * @param comment the comment to set
//     */
//    public void setComment(Comment comment)
//    {
//        this.comment = comment;
//    }

    /**
     * @return the document
     */
    public Document getDocument()
    {
        return document;
    }

//    /**
//     * @param document the document to set
//     */
//    public void setDocument(Document document)
//    {
//        this.document = document;
//    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public boolean equals(Object other)
    {
        if (this == other)
        {
            return true; // super implementation
        }
        if (other != null)
        {
            Class clazz = getClass();
            Class otherClazz = other.getClass();
            if (clazz.isAssignableFrom(otherClazz) || otherClazz.isAssignableFrom(clazz))
            {
                CommentDocumentPK castOther = (CommentDocumentPK) other;
                //use "castOther.getXxxId()" as this instance can be hibernate enhancer
                if (getComment() != null && castOther.getComment() != null && getDocument() != null
                    && castOther.getDocument() != null)
                {
                    return new EqualsBuilder().append(getComment(), castOther.getComment())
                        .append(getDocument(), castOther.getDocument()).isEquals();
                }
            }
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return getComment() == null || getDocument() != null ? super.hashCode()
            : new HashCodeBuilder().append(getComment()).append(getDocument()).toHashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this).append("comment", getComment()).append("document",
            getDocument()).toString();
    }

}