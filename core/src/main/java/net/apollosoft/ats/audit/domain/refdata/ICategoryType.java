package net.apollosoft.ats.audit.domain.refdata;

import net.apollosoft.ats.domain.IAuditable;

import org.apache.commons.lang.ArrayUtils;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface ICategoryType extends IAuditable<Long>
{
    
//    /** CategoryType Enum */
//    public enum CategoryTypeEnum
//    {
//        NONE, REGION
//    };

    /** IGNORE property(s) */
    String[] IGNORE = (String[]) ArrayUtils.addAll(IAuditable.IGNORE, new String[]
    {});

    /**
     * @return id
     */
    Long getCategoryTypeId();

    /**
     * @return the name
     */
    String getName();

}