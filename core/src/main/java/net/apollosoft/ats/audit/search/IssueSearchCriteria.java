package net.apollosoft.ats.audit.search;

import net.apollosoft.ats.search.SearchCriteria;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class IssueSearchCriteria extends SearchCriteria
{

    private Long auditId;

    private Boolean published;

    /**
     * @return the auditId
     */
    public Long getAuditId()
    {
        return auditId;
    }

    /**
     * @param auditId the auditId to set
     */
    public void setAuditId(Long auditId)
    {
        this.auditId = auditId;
    }

    /**
     * @return the published
     */
    public Boolean getPublished()
    {
        return published;
    }

    /**
     * @param published the published to set
     */
    public void setPublished(Boolean published)
    {
        this.published = published;
    }

}