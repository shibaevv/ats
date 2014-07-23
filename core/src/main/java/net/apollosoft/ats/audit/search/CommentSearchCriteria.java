package net.apollosoft.ats.audit.search;

import net.apollosoft.ats.search.SearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class CommentSearchCriteria extends SearchCriteria
{

    private Long actionId;
    private boolean auditLog;

    /**
     * @return the actionId
     */
    public Long getActionId()
    {
        return actionId;
    }

    /**
     * @param actionId the actionId to set
     */
    public void setActionId(Long actionId)
    {
        this.actionId = actionId;
    }

    public boolean isAuditLog()
    {
        return auditLog;
    }

    public void setAuditLog(boolean auditLog)
    {
        this.auditLog = auditLog;
    }

}