package net.apollosoft.ats.domain.security;

import net.apollosoft.ats.domain.IAuditable;
import net.apollosoft.ats.domain.IBase;

import org.apache.commons.lang.ArrayUtils;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface IUser extends IBase<String> // IAuditable
{

    /**
     * A (active), L (leave), T (terminated)
     * User status
     */
    Character ACTIVE = 'A';

    Character LEAVE = 'L';

    Character TERMINATED = 'T';

    /** GROUP_DIVISION property name */
    String GROUP_DIVISIONS = "groupDivisions";

    /** IGNORE property(s) */
    String[] IGNORE = (String[]) ArrayUtils.addAll(IAuditable.IGNORE, new String[]
    {
        GROUP_DIVISIONS
    });

    /**
     * PK
     * @return
     */
    String getUserId();

    /**
     * @return the lastName
     */
    String getLastName();

    /**
     * @return the firstName
     */
    String getFirstName();

    /**
     * @return the otherNames
     */
    String getOtherNames();
    
    /**
     * @return the title
     */
    String getTitle();

    /**
     * @return the firstName + lastName + login
     */
    String getName();

    /**
     * @return the firstName + lastName
     */
    String getFullName();

    /** 
     *         
     */
    String getLogin();

    /**
     * A (active), L (leave), T (terminated)
     * @return the status
     */
    Character getStatus();

    /**
     * @return the group/division(s)
     */
    //List<IGroupDivision> getGroupDivision();

}