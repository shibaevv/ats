package net.apollosoft.ats.domain.security;

import net.apollosoft.ats.domain.IBase;

import org.apache.commons.lang.ArrayUtils;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface IGroup extends IBase<Long>
{

    /** GLOBAL_ID */
    Long GLOBAL_ID = Long.MAX_VALUE;

    /** GLOBAL_NAME */
    String GLOBAL_NAME = "Global";

    /** IGNORE property(s) */
    String[] IGNORE = (String[]) ArrayUtils.addAll(IBase.IGNORE, new String[]
    {});

    /**
     *
     * @return
     */
    Long getGroupId();

    /** 
     *         
     */
    String getName();

}