package net.apollosoft.ats.domain;

import net.apollosoft.ats.domain.IAuditable;

import org.apache.commons.lang.ArrayUtils;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface ITemplate extends IAuditable<Long>
{
    
    /** CONTENT property name */
    String CONTENT = "content";

    /** IGNORE property(s) */
    String[] IGNORE = (String[]) ArrayUtils.addAll(IAuditable.IGNORE, new String[]
    {
        CONTENT
    });

    /**
     * @return id
     */
    Long getTemplateId();

    /**
     * @return the name
     */
    String getName();

    /**
     * @return the reference
     */
    String getReference();

    /**
     * @return the detail
     */
    String getDetail();

    /**
     * @return content
     */
    byte[] getContent();

    /**
     * @return contentType
     */
    String getContentType();

}