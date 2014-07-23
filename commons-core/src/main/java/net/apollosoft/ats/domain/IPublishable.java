package net.apollosoft.ats.domain;

import java.util.Date;

import net.apollosoft.ats.domain.security.IUser;

import org.apache.commons.lang.ArrayUtils;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface IPublishable<T> extends IBase<T>
{

    /** PUBLISHED_BY property name */
    String PUBLISHED_BY = "publishedBy";

    /** PUBLISHED_DATE property name */
    String PUBLISHED_DATE = "publishedDate";

    /** IGNORE property(s) - has to be set and copied manually */
    String[] IGNORE = (String[]) ArrayUtils.addAll(IAuditable.IGNORE, new String[]
    {
        PUBLISHED_BY, PUBLISHED_DATE
    });

    /**
     * @return
     */
    IUser getPublishedBy();

    /**
     * @return
     */
    Date getPublishedDate();

    /**
     * Calculated
     * @return
     */
    boolean isPublished();

}