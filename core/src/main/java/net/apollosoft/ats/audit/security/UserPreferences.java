package net.apollosoft.ats.audit.security;

import java.io.Serializable;
import java.util.Date;

import net.apollosoft.ats.audit.domain.dto.refdata.ActionStatusDto;
import net.apollosoft.ats.audit.domain.refdata.IActionStatus;
import net.apollosoft.ats.audit.search.ISortBy;
import net.apollosoft.ats.search.IDir;
import net.apollosoft.ats.search.Pagination;
import net.apollosoft.ats.util.DateUtils;
import net.apollosoft.ats.util.Pair;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class UserPreferences implements Serializable
{

    /** serialVersionUID */
    private static final long serialVersionUID = 2073615019751042111L;

    private Pair<Long> audit;

    private Pair<Long> actionStatus;

    private Pair<Long> myActionStatus;

    private Pair<Long> businessStatus;

    private Pair<Long> rating;

    private Pair<Long> reportType;

    private Pair<String> responsibleUser;

    private Date issuedDateTo;

    private Date issuedDateFrom;

    private Date closedDateTo;

    private Date closedDateFrom;

    private Date dueDateTo;

    private Date dueDateFrom;

    private String[] groupDivisions;

    private Pagination auditPagination;

    private Pagination auditAdminPagination;

    private Pagination actionPagination;

    private Pagination commentPagination;

    private Pagination userPagination;

    /**
     * Default ctor.
     */
    public UserPreferences()
    {
        reset();
    }

    /**
     * Reset all properties to default values
     */
    public void reset()
    {
        //last 2 years
        issuedDateTo = null;
        issuedDateFrom = DateUtils.addYears(DateUtils.getCurrentDate(), -2);
        closedDateTo = null;
        closedDateFrom = null;
        dueDateTo = null;
        dueDateFrom = null;
        audit = null;
        actionStatus = null;
        businessStatus = null;
        rating = null;
        reportType = null;
        responsibleUser = null;
        groupDivisions = null;
        auditPagination = null;
        auditAdminPagination = null;
        actionPagination = null;
        commentPagination = null;
        userPagination = null;
    }

    /**
     * @return the audit
     */
    public Pair<Long> getAudit()
    {
        if (audit == null)
        {
            audit = new Pair<Long>();
        }
        return audit;
    }

    /**
     * @return the reportType
     */
    public Pair<Long> getReportType()
    {
        if (reportType == null)
        {
            reportType = new Pair<Long>();
        }
        return reportType;
    }

    /**
     * @return the searchUser
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
     * @return the actionStatus
     */
    public Pair<Long> getActionStatus()
    {
        if (actionStatus == null)
        {
            IActionStatus defaultValue = ActionStatusDto.OPEN;
            actionStatus = new Pair<Long>(defaultValue.getId(), defaultValue.getName());
        }
        return actionStatus;
    }

    /**
     * @return the myActionStatus
     */
    public Pair<Long> getMyActionStatus()
    {
        if (myActionStatus == null)
        {
            IActionStatus defaultValue = ActionStatusDto.OPEN;
            myActionStatus = new Pair<Long>(defaultValue.getId(), defaultValue.getName());
        }
        return myActionStatus;
    }

    /**
     * @return the businessStatus
     */
    public Pair<Long> getBusinessStatus()
    {
        if (businessStatus == null)
        {
            businessStatus = new Pair<Long>();
        }
        return businessStatus;
    }

    /**
     * @return the rating
     */
    public Pair<Long> getRating()
    {
        if (rating == null)
        {
            rating = new Pair<Long>();
        }
        return rating;
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
     * @return the groupDivisions
     */
    public String[] getGroupDivisions()
    {
        return groupDivisions;
    }

    /**
     * @param groupDivisions the groupDivisions to set
     */
    public void setGroupDivisions(String[] groupDivisions)
    {
        this.groupDivisions = groupDivisions;
    }

    /**
     * @return the auditPagination
     */
    public Pagination getAuditPagination()
    {
        if (auditPagination == null)
        {
            auditPagination = new Pagination(25, 0, ISortBy.AUDIT_ISSUED_DATE_SQL, IDir.DESC);
        }
        return auditPagination;
    }

    /**
     * @return the auditAdminPagination
     */
    public Pagination getAuditAdminPagination()
    {
        if (auditAdminPagination == null)
        {
            auditAdminPagination = new Pagination(0, 0, ISortBy.AUDIT_UPDATED_DATE_SQL, IDir.DESC);
        }
        return auditAdminPagination;
    }

    /**
     * @return the actionPagination
     */
    public Pagination getActionPagination()
    {
        if (actionPagination == null)
        {
            actionPagination = new Pagination(25, 0, ISortBy.ACTION_DUE_DATE_SQL, IDir.ASC);
        }
        return actionPagination;
    }

    /**
     * @return the commentPagination
     */
    public Pagination getCommentPagination()
    {
        if (commentPagination == null)
        {
            commentPagination = new Pagination(0, 0, ISortBy.COMMENT_CREATED_DATE, IDir.ASC);
        }
        return commentPagination;
    }

    /**
     * @return the userPagination
     */
    public Pagination getUserPagination()
    {
        if (userPagination == null)
        {
            userPagination = new Pagination(25, 0, ISortBy.USER_FULLNAME, IDir.DESC);
        }
        return userPagination;
    }

}