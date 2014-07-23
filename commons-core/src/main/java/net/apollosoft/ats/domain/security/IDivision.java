package net.apollosoft.ats.domain.security;

import net.apollosoft.ats.domain.IBase;

import org.apache.commons.lang.ArrayUtils;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface IDivision extends IBase<Long>
{

    /** All valid divisions */
    String ALL_NAME = "All";

    /** GROUP property name */
    String GROUP = "group";

    /** IGNORE property(s) */
    String[] IGNORE = (String[]) ArrayUtils.addAll(IBase.IGNORE, new String[]
    {
        GROUP
    });

    /**
     *
     * @return
     */
    Long getDivisionId();

    /** 
     *         
     */
    String getName();

    /** 
     *         
     */
    IGroup getGroup();

}