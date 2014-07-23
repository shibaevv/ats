package net.apollosoft.ats.audit.domain;

import net.apollosoft.ats.domain.IBase;

/**
 * User defined mapping allows to define a Class attribute/property that will be used as the Resource Property name
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface ITag extends IBase<Long>
{

    /**
     * @return id
     */
    Long getTagId();

    /**
     * @return the categoryTypeId
     */
    Long getCategoryTypeId();

    /**
     * @return the categoryValue
     */
    String getCategoryValue();

}