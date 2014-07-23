package net.apollosoft.ats.domain.security;

import net.apollosoft.ats.domain.IAuditable;

import org.apache.commons.lang.ArrayUtils;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface IFunction extends IAuditable<Long>
{

    /** QUERY ADD_COMMENT, UPDATE_STATUS */
    String QUERY_ADD_COMMENT = "commentAdmin/edit.do";

    /** IGNORE property(s) */
    String[] IGNORE = (String[]) ArrayUtils.addAll(IAuditable.IGNORE, new String[]
    {});

    /**
     *
     * @return
     */
    Long getFunctionId();

    /** 
     * @return the name    
     */
    String getName();

    /**
     * @return the detail
     */
    String getDetail();

    /**
     * @return the module
     */
    String getModule();

    /**
     * @return the query
     */
    String getQuery();

    /**
     * @return the home
     */
    boolean isHome();

}