package net.apollosoft.ats.domain.refdata;

import net.apollosoft.ats.domain.IAuditable;

import org.apache.commons.lang.ArrayUtils;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface IUserType extends IAuditable<Long>
{
    
    /** UserType Enum */
    public enum UserTypeEnum
    {
        NONE, OWNER
    };

    /** IGNORE property(s) */
    String[] IGNORE = (String[]) ArrayUtils.addAll(IAuditable.IGNORE, new String[]
    {});

    /**
     * @return id
     */
    Long getUserTypeId();

    /**
     * @return the name
     */
    String getName();

}