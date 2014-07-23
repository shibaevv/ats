package net.apollosoft.ats.audit.domain.dto;

import java.util.ArrayList;
import java.util.List;

import net.apollosoft.ats.audit.domain.IAction;
import net.apollosoft.ats.audit.domain.IComment;
import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.IDocument;
import net.apollosoft.ats.domain.dto.AuditableDto;
import net.apollosoft.ats.util.BeanUtils;
import net.apollosoft.ats.util.Formatter;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class CommentDto extends AuditableDto<Long> implements IComment
{

    private Long commentId;

    private String detail;

    private IAction action;

    private List<IDocument> documents;

    private Byte listIndex;

    private boolean auditLog;

    /**
     * 
     */
    public CommentDto()
    {
        super();
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public CommentDto(IComment source) throws DomainException
    {
        BeanUtils.copyProperties(source, this, IComment.IGNORE);
        //TODO: re-factor to jsp ${fn:replace(entity.name,'"','\'')}
        //name = Formatter.formatJson(name, true);
        detail = Formatter.formatJson(detail, false);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getCommentId();
    }

    /**
     * @return the id
     */
    public Long getCommentId()
    {
        return commentId;
    }

    /**
     * @param commentId the commentId to set
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
     * @param document
     */
    public void add(IDocument document)
    {
        getDocuments().add(document);
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
    public void setListIndex(Byte listIndex)
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
    public void setAuditLog(boolean auditLog)
    {
        this.auditLog = auditLog;
    }

}