package net.apollosoft.ats.audit.search;

import java.util.Date;

import net.apollosoft.ats.search.SearchCriteria;
import net.apollosoft.ats.util.Pair;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class ReportSearchCriteria extends SearchCriteria
{

    /** compass query */
    private String compassQuery;

    private boolean auditGroupDivision;

    private boolean myaction;
    
    private String[] availableGroupDivisions;

    /*
     * Audit
     */
    private Long reportTypeId;

    private Long searchAuditId;

    private String searchAuditName;

    private Date issuedDateFrom;

    private Date issuedDateTo;

    private String[] auditGroupDivisions;

    private Boolean published;

    /*
     * Issue
     */
    private Long issueId;

    private Long ratingId;

    /*
     * Action
     */
    private Long actionId;

    private Pair<String> responsibleUser;

    private Long actionStatusId;

    private Long businessStatusId;

    private Date dueDateFrom;

    private Date dueDateTo;

    private Date closedDateFrom;

    private Date closedDateTo;

    private String[] actionGroupDivisions;

    /**
     * @return the compassQuery
     */
    public String getCompassQuery()
    {
        return compassQuery;
    }

    /**
     * @param compassQuery the compassQuery to set
     */
    public void setCompassQuery(String compassQuery)
    {
        this.compassQuery = compassQuery;
    }

    /**
     * @return the auditGroupDivision
     */
    public boolean isAuditGroupDivision()
    {
        return auditGroupDivision;
    }

    /**
     * @param auditGroupDivision the auditGroupDivision to set
     */
    public void setAuditGroupDivision(boolean auditGroupDivision)
    {
        this.auditGroupDivision = auditGroupDivision;
    }

    /**
     * @return the myaction
     */
    public boolean isMyaction()
    {
        return myaction;
    }

    /**
     * @param myaction the myaction to set
     */
    public void setMyaction(boolean myaction)
    {
        this.myaction = myaction;
    }

    /**
     * @return the availableGroupDivisions
     */
    public String[] getAvailableGroupDivisions()
    {
        return availableGroupDivisions;
    }

    /**
     * @param availableGroupDivisions the availableGroupDivisions to set
     */
    public void setAvailableGroupDivisions(String[] availableGroupDivisions)
    {
        this.availableGroupDivisions = availableGroupDivisions;
    }

    /**
     * @return the issueId
     */
    public Long getIssueId()
    {
        return issueId;
    }

    /**
     * @param issueId the issueId to set
     */
    public void setIssueId(Long issueId)
    {
        this.issueId = issueId;
    }

    /**
     * @return the responsibleUser
     */
    public Pair<String> getResponsibleUser()
    {
        if (responsibleUser == null)
        {
            responsibleUser = new Pair<String>();
        }
        return responsibleUser;
    }

    /**
     * @return the dueDateFrom
     */
    public Date getDueDateFrom()
    {
        return dueDateFrom;
    }

    /**
     * @param dueDateFrom the dueDateFrom to set
     */
    public void setDueDateFrom(Date dueDateFrom)
    {
        this.dueDateFrom = dueDateFrom;
    }

    /**
     * @return the dueDateTo
     */
    public Date getDueDateTo()
    {
        return dueDateTo;
    }

    /**
     * @param dueDateTo the dueDateTo to set
     */
    public void setDueDateTo(Date dueDateTo)
    {
        this.dueDateTo = dueDateTo;
    }

    /**
     * @return the closedDateFrom
     */
    public Date getClosedDateFrom()
    {
        return closedDateFrom;
    }

    /**
     * @param closedDateFrom the closedDateFrom to set
     */
    public void setClosedDateFrom(Date closedDateFrom)
    {
        this.closedDateFrom = closedDateFrom;
    }

    /**
     * @return the closedDateTo
     */
    public Date getClosedDateTo()
    {
        return closedDateTo;
    }

    /**
     * @param closedDateTo the closedDateTo to set
     */
    public void setClosedDateTo(Date closedDateTo)
    {
        this.closedDateTo = closedDateTo;
    }

    /**
     * @return the auditGroupDivisions
     */
    public String[] getAuditGroupDivisions()
    {
        if (auditGroupDivisions == null)
        {
            auditGroupDivisions = new String[0];
        }
        return auditGroupDivisions;
    }

    /**
     * @param auditGroupDivisions the auditGroupDivisions to set
     */
    public void setAuditGroupDivisions(String[] auditGroupDivisions)
    {
        this.auditGroupDivisions = auditGroupDivisions;
    }

    /**
     * @return the actionGroupDivisions
     */
    public String[] getActionGroupDivisions()
    {
        if (actionGroupDivisions == null)
        {
            actionGroupDivisions = new String[0];
        }
        return actionGroupDivisions;
    }

    /**
     * @param actionGroupDivisions the actionGroupDivisions to set
     */
    public void setActionGroupDivisions(String[] actionGroupDivisions)
    {
        this.actionGroupDivisions = actionGroupDivisions;
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

    /**
     * @return the auditId
     */
    public Long getSearchAuditId()
    {
        return searchAuditId;
    }

    /**
     * @param auditId the auditId to set
     */
    public void setSearchAuditId(Long auditId)
    {
        this.searchAuditId = auditId;
    }

    /**
     * @return the auditName
     */
    public String getSearchAuditName()
    {
        return searchAuditName;
    }

    /**
     * @param auditName the auditName to set
     */
    public void setSearchAuditName(String auditName)
    {
        this.searchAuditName = auditName;
    }

    /**
     * @return the issuedDateFrom
     */
    public Date getIssuedDateFrom()
    {
        return issuedDateFrom;
    }

    /**
     * @param issuedDateFrom the issuedDateFrom to set
     */
    public void setIssuedDateFrom(Date issuedDateFrom)
    {
        this.issuedDateFrom = issuedDateFrom;
    }

    /**
     * @return the issuedDateTo
     */
    public Date getIssuedDateTo()
    {
        return issuedDateTo;
    }

    /**
     * @param issuedDateTo the issuedDateTo to set
     */
    public void setIssuedDateTo(Date issuedDateTo)
    {
        this.issuedDateTo = issuedDateTo;
    }

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

    /**
     * @return the ratingId
     */
    public Long getRatingId()
    {
        return ratingId;
    }

    /**
     * @param ratingId the ratingId to set
     */
    public void setRatingId(Long ratingId)
    {
        this.ratingId = ratingId;
    }

    /**
     * @return the actionStatusId
     */
    public Long getActionStatusId()
    {
        return actionStatusId;
    }

    /**
     * @param actionStatusId the actionStatusId to set
     */
    public void setActionStatusId(Long actionStatusId)
    {
        this.actionStatusId = actionStatusId;
    }

    /**
     * @return the businessStatusId
     */
    public Long getBusinessStatusId()
    {
        return businessStatusId;
    }

    /**
     * @param businessStatusId the businessStatusId to set
     */
    public void setBusinessStatusId(Long businessStatusId)
    {
        this.businessStatusId = businessStatusId;
    }

    /**
     * @return the reportTypeId
     */
    public Long getReportTypeId()
    {
        return reportTypeId;
    }

    /**
     * @param reportTypeId the reportTypeId to set
     */
    public void setReportTypeId(Long reportTypeId)
    {
        this.reportTypeId = reportTypeId;
    }

}