package net.apollosoft.ats.domain.refdata;

import net.apollosoft.ats.domain.IAuditable;

import org.apache.commons.lang.ArrayUtils;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface IReportType extends IAuditable<Long>
{
    
    /** ReportType Enum */
    public enum ReportTypeEnum
    {
        NONE, AUDIT, COMPLIANCE
    };

    /** IGNORE property(s) */
    String[] IGNORE = (String[]) ArrayUtils.addAll(IAuditable.IGNORE, new String[]
    {});

    /**
     * @return id
     */
    Long getReportTypeId();

    /**
     * @return the name
     */
    String getName();

}