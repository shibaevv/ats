package net.apollosoft.ats.audit.domain.refdata;

import net.apollosoft.ats.domain.IAuditable;

import org.apache.commons.lang.ArrayUtils;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface IRating extends IAuditable<Long>
{

    /** Rating Enum */
    public enum RatingEnum
    {
        //RatingEnum.VERY_HIGH.ordinal()
        NONE, VERY_HIGH, HIGH, MEDIUM, LOW, VERY_LOW
    };

    /** IGNORE property(s) */
    String[] IGNORE = (String[]) ArrayUtils.addAll(IAuditable.IGNORE, new String[]
    {});

    /**
     * @return id
     */
    Long getRatingId();

    /**
     * @return the name
     */
    String getName();

}