package net.apollosoft.ats.domain;

import org.apache.commons.lang.ArrayUtils;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface IDocument extends IAuditable<Long>
{

    /** DETAIL property name */
    String DETAIL = "detail";

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
    Long getDocumentId();

    /**
     * @return the name
     */
    String getName();

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
     * eg application/vnd.openxmlformats-officedocument.wordprocessingml.document
     */
    String getContentType();

}