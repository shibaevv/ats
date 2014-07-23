package net.apollosoft.ats.audit.domain.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.apollosoft.ats.audit.domain.IAction;
import net.apollosoft.ats.audit.domain.IComment;
import net.apollosoft.ats.audit.domain.IIssue;
import net.apollosoft.ats.audit.domain.dto.refdata.ActionFollowupStatusDto;
import net.apollosoft.ats.audit.domain.dto.refdata.BusinessStatusDto;
import net.apollosoft.ats.audit.domain.refdata.IActionFollowupStatus;
import net.apollosoft.ats.audit.domain.refdata.IBusinessStatus;
import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.dto.security.GroupDivisionDto;
import net.apollosoft.ats.domain.dto.security.UserDto;
import net.apollosoft.ats.domain.security.IGroupDivision;
import net.apollosoft.ats.domain.security.IUser;
import net.apollosoft.ats.util.BeanUtils;
import net.apollosoft.ats.util.DateUtils;
import net.apollosoft.ats.util.Formatter;
import net.apollosoft.ats.util.ThreadLocalUtils;

import org.springframework.util.AutoPopulatingList;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class ActionDto extends PublishableDto<Long> implements IAction
{

    private Long actionId;

    private String reference;

    private String name;

    private String detail;

    private Date dueDate;

    private Date closedDate;

    private Date followupDate;

    private IBusinessStatus businessStatus;

    private IActionFollowupStatus followupStatus;

    private boolean editable;

    private List<IUser> responsibleUsers;

    private AutoPopulatingList<UserDto> responsibleUsers2;

    private List<IUser> bormResponsibleUsers;

    private IIssue issue;

    private List<IComment> comments;

    private IComment comment;

    private List<IComment> logs;

    private BigDecimal listIndex;

    private boolean overDue; // this is a calculated property

    private List<IGroupDivision> groupDivisions;

    private AutoPopulatingList<GroupDivisionDto> groupDivisions2;

    /**
     * 
     */
    public ActionDto()
    {
        super();
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public ActionDto(IAction source) throws DomainException
    {
        BeanUtils.copyProperties(source, this, IAction.IGNORE);
        businessStatus = source.getBusinessStatus() == null ? null : new BusinessStatusDto(source
            .getBusinessStatus());
        followupStatus = source.getFollowupStatus() == null ? null : new ActionFollowupStatusDto(
            source.getFollowupStatus());
        //TODO: re-factor to jsp ${fn:replace(entity.name,'"','\'')}
        name = Formatter.formatJson(name, true);
        detail = Formatter.formatJson(detail, false);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getActionId();
    }

    /**
     * @return the id
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
     * @return the dueDate
     */
    public Date getDueDate()
    {
        return dueDate;
    }

    /**
     * Calculated
     * @return
     */
    public long getDaysOverdue()
    {
        return DateUtils.getDays(getDueDate(), ThreadLocalUtils.getDate());
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(Date dueDate)
    {
        this.dueDate = dueDate;
    }

    /**
     * @return the closedDate
     */
    public Date getClosedDate()
    {
        return closedDate;
    }

    /**
     * @param closedDate the closedDate to set
     */
    public void setClosedDate(Date closedDate)
    {
        this.closedDate = closedDate;
    }

    /**
     * @return the followupDate
     */
    public Date getFollowupDate()
    {
        return followupDate;
    }

    /**
     * @param followupDate the followupDate to set
     */
    public void setFollowupDate(Date followupDate)
    {
        this.followupDate = followupDate;
    }

    /**
     * @return the businessStatus
     */
    public IBusinessStatus getBusinessStatus()
    {
        if (businessStatus == null)
        {
            businessStatus = new BusinessStatusDto();
        }
        return businessStatus;
    }

    /**
     * @param businessStatus the businessStatus to set
     */
    public void setBusinessStatus(IBusinessStatus businessStatus)
    {
        this.businessStatus = businessStatus;
    }

    /**
     * @return the followupStatus
     */
    public IActionFollowupStatus getFollowupStatus()
    {
        if (followupStatus == null)
        {
            followupStatus = new ActionFollowupStatusDto();
        }
        return followupStatus;
    }

    /**
     * @param followupStatus the followupStatus to set
     */
    public void setFollowupStatus(IActionFollowupStatus followupStatus)
    {
        this.followupStatus = followupStatus;
    }

    /**
     * @return the issue
     */
    public IIssue getIssue()
    {
        return issue;
    }

    /**
     * @param issue the issue to set
     */
    public void setIssue(IIssue issue)
    {
        this.issue = issue;
    }

    /**
     * @return the comments
     */
    public List<IComment> getComments()
    {
        if (comments == null)
        {
            comments = new ArrayList<IComment>();
        }
        return comments;
    }

    /**
     * Get last comment
     * @return the comment
     */
    public IComment getComment()
    {
        if (getComments().isEmpty())
        {
            comment = new CommentDto();
        }
        else
        {
            comment = getComments().get(getComments().size() - 1);
        }
        return comment;
    }

    /**
     * @return the logs
     */
    public List<IComment> getLogs()
    {
        if (logs == null)
        {
            logs = new ArrayList<IComment>();
        }
        return logs;
    }

    /**
     * @return the editable
     * true - if logged user is also responsible user for this action or has this group/division in security profile
     */
    public boolean isEditable()
    {
        return editable;
    }

    /**
     * @param editable the editable to set
     */
    public void setEditable(boolean editable)
    {
        this.editable = editable;
    }

    /**
     * @return the first responsibleUser
     */
    public IUser getFirstResponsibleUser()
    {
        return getResponsibleUsers().isEmpty() ? null : getResponsibleUsers().get(0);
    }

    /**
     * @return the responsibleUsers
     */
    public List<IUser> getResponsibleUsers()
    {
        if (responsibleUsers == null)
        {
            responsibleUsers = new ArrayList<IUser>();
        }
        return responsibleUsers;
    }

    /**
     * @return the groupDivisions2
     */
    public AutoPopulatingList<UserDto> getResponsibleUsers2()
    {
        if (responsibleUsers2 == null)
        {
            responsibleUsers2 = new AutoPopulatingList<UserDto>(UserDto.class);
        }
        return responsibleUsers2;
    }

    /**
     * @return the bormResponsibleUsers
     */
    public List<IUser> getBormResponsibleUsers()
    {
        if (bormResponsibleUsers == null)
        {
            bormResponsibleUsers = new ArrayList<IUser>();
        }
        return bormResponsibleUsers;
    }

    /**
     * @return the listIndex
     */
    public BigDecimal getListIndex()
    {
        return listIndex;
    }

    /**
     * @param listIndex the listIndex to set
     */
    public void setListIndex(BigDecimal listIndex)
    {
        this.listIndex = listIndex;
    }

    /**
     * @return the overDue
     */
    public boolean isOverDue()
    {
        return overDue;
    }

    /**
     * @param overDue the overDue to set
     */
    public void setOverDue(boolean overDue)
    {
        this.overDue = overDue;
    }

    /**
     * @return the groupDivisions
     */
    public List<IGroupDivision> getGroupDivisions()
    {
        if (groupDivisions == null)
        {
            groupDivisions = new ArrayList<IGroupDivision>();
        }
        return groupDivisions;
    }

    /**
     * @return the groupDivisions2
     */
    public AutoPopulatingList<GroupDivisionDto> getGroupDivisions2()
    {
        if (groupDivisions2 == null)
        {
            groupDivisions2 = new AutoPopulatingList<GroupDivisionDto>(GroupDivisionDto.class);
        }
        return groupDivisions2;
    }

}