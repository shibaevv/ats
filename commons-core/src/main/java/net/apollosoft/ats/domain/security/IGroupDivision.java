package net.apollosoft.ats.domain.security;

import net.apollosoft.ats.domain.IAuditable;

import org.apache.commons.lang.ArrayUtils;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface IGroupDivision extends IAuditable<Long>
{

    /** GROUP property name */
    String GROUP = "group";

    /** DIVISION property name */
    String DIVISION = "division";

    /** IGNORE property(s) */
    String[] IGNORE = (String[]) ArrayUtils.addAll(IAuditable.IGNORE, new String[]
    {
        GROUP, DIVISION
    });

    /**
     * @return
     */
    IGroup getGroup();

    /**
     * @return
     */
    IDivision getDivision();

}