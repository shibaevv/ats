package net.apollosoft.ats.domain.security;

import net.apollosoft.ats.domain.IAuditable;
import net.apollosoft.ats.domain.refdata.IReportType;

import org.apache.commons.lang.ArrayUtils;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface IUserMatrix
{

    /** USER property name */
    String USER = "user";

    /** ROLE property name */
    String ROLE = "role";

    /** AUDIT_REPORT_TYPE property name */
    String REPORT_TYPE = "reportType";

    /** GROUP property name */
    String GROUP = "group";

    /** DIVISION property name */
    String DIVISION = "division";

    /** IGNORE property(s) */
    String[] IGNORE = (String[]) ArrayUtils.addAll(IAuditable.IGNORE, new String[]
    {
        USER, ROLE, REPORT_TYPE, GROUP, DIVISION
    });

    /**
     * @return the id
     */
    Long getUserMatrixId();

    /**
     * @return the user
     */
    IUser getUser();

    /**
     * @return the role
     */
    IRole getRole();

    /**
     * @return the reportType
     */
    IReportType getReportType();

    /**
     * @return the group
     */
    IGroup getGroup();

    /**
     * @return the division
     */
    IDivision getDivision();

}