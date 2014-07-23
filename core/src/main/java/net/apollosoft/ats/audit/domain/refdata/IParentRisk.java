package net.apollosoft.ats.audit.domain.refdata;

import net.apollosoft.ats.domain.IAuditable;

import org.apache.commons.lang.ArrayUtils;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface IParentRisk extends IAuditable<Long>
{
    /** ACTIONS property name */
    String CATEGORY = "category";

    /** IGNORE property(s) */
    String[] IGNORE = (String[]) ArrayUtils.addAll(IAuditable.IGNORE, new String[]
    {
        CATEGORY
    });

    /**
     * @return id
     */
    Long getParentRiskId();

    /**
     * @return the name
     */
    String getName();

    /**
     * @return the category
     */
    IParentRiskCategory getCategory();

}