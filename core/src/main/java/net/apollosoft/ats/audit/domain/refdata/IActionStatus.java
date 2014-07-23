package net.apollosoft.ats.audit.domain.refdata;

import net.apollosoft.ats.domain.IAuditable;

import org.apache.commons.lang.ArrayUtils;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface IActionStatus extends IAuditable<Long>
{

    public enum ActionStatusEnum
    {
        ALL, OPEN, CLOSED
    };

    /** OPEN - (for hsql only) */
    Long OPEN = new Long(ActionStatusEnum.OPEN.ordinal());

    /** IGNORE property(s) */
    String[] IGNORE = (String[]) ArrayUtils.addAll(IAuditable.IGNORE, new String[]
    {});

    /**
     * @return id
     */
    Long getActionStatusId();

    /**
     * @return the name
     */
    String getName();

    /**
     * Calculated
     * @return
     */
    boolean isClosed();

    /**
     * Calculated
     * @return
     */
    boolean isOpen();

}