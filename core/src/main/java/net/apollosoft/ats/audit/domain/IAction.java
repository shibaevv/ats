package net.apollosoft.ats.audit.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import net.apollosoft.ats.audit.domain.refdata.IActionFollowupStatus;
import net.apollosoft.ats.audit.domain.refdata.IBusinessStatus;
import net.apollosoft.ats.domain.IPublishable;
import net.apollosoft.ats.domain.security.IUser;

import org.apache.commons.lang.ArrayUtils;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface IAction extends IPublishable<Long>
{

    /** ACTION_BUSINESS_STATUS property name */
    String BUSINESS_STATUS = "businessStatus";

    /** FOLLOWUP_STATUS property name */
    String FOLLOWUP_STATUS = "followupStatus";

    /** ISSUE property name */
    String ISSUE = "issue";

    /** COMMENTS property name */
    String COMMENTS = "comments";

    /** LOGS property name */
    String LOGS = "logs";

    /** RESPONSIBLE_USERS property name */
    String RESPONSIBLE_USERS = "responsibleUsers";

    /** GROUP_DIVISION property name */
    String GROUP_DIVISIONS = "groupDivisions";

    /** IGNORE property(s) - !!! ALPHABETICAL ORDER !!! */
    String[] IGNORE = (String[]) ArrayUtils.addAll(IPublishable.IGNORE, new String[]
    {
        BUSINESS_STATUS, COMMENTS, FOLLOWUP_STATUS, GROUP_DIVISIONS, ISSUE, LOGS,
        RESPONSIBLE_USERS
    });

    /**
     * @return id
     */
    Long getActionId();

    /**
     * @return the reference
     */
    String getReference();

    /**
     * @return the name
     */
    String getName();

    /**
     * @return the listIndex
     */
    BigDecimal getListIndex();

    /**
     * @return the detail
     */
    String getDetail();

    /**
     * @return the dueDate
     */
    Date getDueDate();

    /**
     * @return the closedDate
     */
    Date getClosedDate();

    /**
     * @return the businessStatus
     */
    IBusinessStatus getBusinessStatus();

    /**
     * @return the actionFollowupStatus
     */
    IActionFollowupStatus getFollowupStatus();

    /**
     * @return the closedDate
     */
    Date getFollowupDate();

    /**
     * @return
     */
    IIssue getIssue();

    /**
     * @return
     */
    List<IComment> getComments();

    /**
     * @return
     */
    List<IComment> getLogs();

    /**
     * @return the responsibleUsers
     */
    List<IUser> getResponsibleUsers();

    /**
     * @return the group/division(s)
     */
    //List<IGroupDivision> getGroupDivision();

}