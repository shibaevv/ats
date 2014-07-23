package net.apollosoft.ats.audit.domain.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.apollosoft.ats.audit.domain.IAction;
import net.apollosoft.ats.audit.domain.IComment;
import net.apollosoft.ats.domain.IDocument;
import net.apollosoft.ats.domain.hibernate.Auditable;
import net.apollosoft.ats.domain.hibernate.Document;
import net.apollosoft.ats.domain.security.IUser;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.WhereJoinTable;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_comment")
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
@NamedQueries(value =
{
    @NamedQuery(name = "comment.maxDocuments", query = "SELECT documents.size FROM Comment WHERE action.id = :actionId")
})
//@Cache(region = "audit", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Comment extends Auditable<Long> implements IComment
{

    /** serialVersionUID */
    private static final long serialVersionUID = 8475168243907964596L;

    /** identity persistent field */
    @Id
    @Column(name = "comment_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    /** persistent field */
    @Basic()
    @Column(name = "audit_log", nullable = false)
    @Type(type = "yes_no")
    private boolean auditLog;

    /** persistent field */
    @Basic()
    @Column(name = "comment_list_index", nullable = false)
    private Byte listIndex;

    /** persistent field */
    @Lob()
    @Column(name = "comment_detail", nullable = false)
    private String detail;

    /** persistent field */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Action.class)
    @JoinColumn(name = "action_id", nullable = false)
    private IAction action;

    /** persistent field */
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Document.class)
    @JoinTable(name = "ats_comment_document", joinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "comment_id"), inverseJoinColumns = @JoinColumn(name = "document_id", referencedColumnName = "document_id"))
    @WhereJoinTable(clause = "deleted_date IS NULL")
    private List<IDocument> documents;

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade =
    {
        CascadeType.PERSIST
    })
    private List<CommentDocument> commentDocuments;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getCommentId();
    }

    /**
     *
     * @return
     */
    public Long getCommentId()
    {
        if (commentId != null && commentId.longValue() == 0L)
        {
            commentId = null;
        }
        return commentId;
    }

    /**
     *
     * @param commentId
     */
    public void setCommentId(Long commentId)
    {
        this.commentId = commentId;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IComment#getName()
     */
    public String getName()
    {
        return StringUtils.substring(detail, 0, 80);
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
     * @return the action
     */
    public IAction getAction()
    {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(IAction action)
    {
        this.action = action;
    }

    /**
     * @return the documents
     */
    public List<IDocument> getDocuments()
    {
        if (documents == null)
        {
            documents = new ArrayList<IDocument>();
        }
        return documents;
    }

    /**
     * @param documents the documents to set
     */
    public void setDocuments(List<IDocument> documents)
    {
        this.documents = documents;
    }

    /**
     * @return the commentDocuments
     */
    public List<CommentDocument> getCommentDocuments()
    {
        if (commentDocuments == null)
        {
            commentDocuments = new ArrayList<CommentDocument>();
        }
        return commentDocuments;
    }

    /**
     * @param commentDocuments the commentDocuments to set
     */
    public void setCommentDocuments(List<CommentDocument> commentDocuments)
    {
        this.commentDocuments = commentDocuments;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.Auditable#setCreatedBy(net.apollosoft.ats.audit.domain.security.User)
     */
    @Override
    public void setCreatedBy(IUser createdBy)
    {
        super.setCreatedBy(createdBy);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.Auditable#setCreatedDate(java.util.Date)
     */
    @Override
    public void setCreatedDate(Date createdDate)
    {
        super.setCreatedDate(createdDate);
    }

    /**
     * @return the listIndex
     */
    public Byte getListIndex()
    {
        return listIndex;
    }

    /**
     * @param listIndex the listIndex to set
     */
    /*public*/ void setListIndex(Byte listIndex)
    {
        this.listIndex = listIndex;
    }

    /**
     * @return the auditLog
     */
    public boolean isAuditLog()
    {
        return auditLog;
    }

    /**
     * @param auditLog the auditLog to set
     */
    /*public*/ void setAuditLog(boolean auditLog)
    {
        this.auditLog = auditLog;
    }

}