package net.apollosoft.ats.domain.security;

import java.util.List;

import net.apollosoft.ats.domain.IAuditable;

import org.apache.commons.lang.ArrayUtils;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface IRole extends IAuditable<Long>
{

    /** Role Enum */
    public enum RoleEnum
    {
        NONE, SYSTEM_ADMIN, REPORT_OWNER, REPORT_TEAM, OVERSIGHT_TEAM, READ_ONLY, DEFAULT
    };

    /** PREFIX for role, eg ROLE_1 */
    String PREFIX = "ROLE_";

    /** FUNCTIONS property name */
    String FUNCTIONS = "functions";

    /** IGNORE property(s) */
    String[] IGNORE = (String[]) ArrayUtils.addAll(IAuditable.IGNORE, new String[]
    {
        FUNCTIONS
    });

    /**
     * @return
     */
    Long getRoleId();

    /** 
     * @return the name    
     */
    String getName();

    /**
     * @return the detail
     */
    String getDetail();

    /**
     * @return
     */
    Byte getPriority();

    /**
     * @return the functions
     */
    List<IFunction> getFunctions();

    /**
     * Calculated
     * @return true if DEFAULT
     */
    boolean isDefault();

}