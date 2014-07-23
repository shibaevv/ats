package net.apollosoft.ats.audit.domain.refdata;

import net.apollosoft.ats.domain.IAuditable;

import org.apache.commons.lang.ArrayUtils;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface IActionFollowupStatus extends IAuditable<Long>
{

    public enum ActionFollowupStatusEnum
    {
        NONE, OPEN, CLOSED_VERIFIED, CLOSED_REPRESENTATION_ACCEPTED
    }
    /** IGNORE property(s) */
    String[] IGNORE = (String[]) ArrayUtils.addAll(IAuditable.IGNORE, new String[]
    {});

    /**
     * @return id
     */
    Long getActionFollowupStatusId();

    /**
     * @return the name
     */
    String getName();

}