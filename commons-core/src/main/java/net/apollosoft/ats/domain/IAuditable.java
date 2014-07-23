package net.apollosoft.ats.domain;

import java.util.Date;

import net.apollosoft.ats.domain.security.IUser;

import org.apache.commons.lang.ArrayUtils;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface IAuditable<T> extends IBase<T>
{

    /** CREATED_BY property name */
    String CREATED_BY = "createdBy";

    /** COMMENT_CREATED_DATE property name */
    String CREATED_DATE = "createdDate";

    /** UPDATED_BY property name */
    String UPDATED_BY = "updatedBy";

    /** UPDATED_DATE property name */
    String UPDATED_DATE = "updatedDate";

    /** DELETED_BY property name */
    String DELETED_BY = "deletedBy";

    /** DELETED_DATE property name */
    String DELETED_DATE = "deletedDate";

    /** IGNORE property(s) */
    String[] IGNORE = (String[]) ArrayUtils.addAll(IBase.IGNORE, new String[]
    {
        CREATED_BY, UPDATED_BY, DELETED_BY
    });

    /**
     * @return
     */
    IUser getCreatedBy();

    /**
     * @return
     */
    Date getCreatedDate();

    /**
     * @return
     */
    IUser getUpdatedBy();

    /**
     * @return
     */
    Date getUpdatedDate();

    /**
     * @return
     */
    IUser getDeletedBy();

    /**
     * @return
     */
    Date getDeletedDate();

    /**
     * Calculated
     * @return
     */
    boolean isDeleted();

    /**
     * @return
     */
    long getLockVersion();

}