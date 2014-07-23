package net.apollosoft.ats.audit.domain.refdata;

import net.apollosoft.ats.domain.IAuditable;

import org.apache.commons.lang.ArrayUtils;


public interface IBusinessStatus extends IAuditable<Long>
{

    /** BusinessStatus Enum */
    public enum BusinessStatusEnum
    {
        //IBusinessStatus.IMPLEMENTED.ordinal()
        NONE, NOT_ACTIONED, IN_PROGRESS, IMPLEMENTED, NO_LONGER_APPLICABLE
    };

    /** ACTION_STATUS property name */
    String ACTION_STATUS = "actionStatus";

    /** IGNORE property(s) */
    String[] IGNORE = (String[]) ArrayUtils.addAll(IAuditable.IGNORE, new String[]
    {
        ACTION_STATUS
    });

    /**
     * @return id
     */
    Long getBusinessStatusId();

    /**
     * @return the name
     */
    String getName();

    /**
     * @return
     */
    IActionStatus getActionStatus();

    /**
     * @return
     */
    boolean isNotActioned();

    /*
     * 
     */
    boolean isInProgress();

    /*
     * 
     */
    boolean isImplemented();

    /*
     * 
     */
    boolean isNoLongerApplicable();

}