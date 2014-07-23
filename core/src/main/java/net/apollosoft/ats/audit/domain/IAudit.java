package net.apollosoft.ats.audit.domain;

import java.util.Date;
import java.util.List;

import net.apollosoft.ats.audit.search.ISortBy;
import net.apollosoft.ats.domain.IDocument;
import net.apollosoft.ats.domain.IPublishable;
import net.apollosoft.ats.domain.refdata.IReportType;

import org.apache.commons.lang.ArrayUtils;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface IAudit extends IPublishable<Long>
{

    /** ATS Reports */
    Character SOURCE_ATS = null;

    /** REPORT_TYPE property name */
    String REPORT_TYPE = "reportType";

    /** DOCUMENT property name */
    String DOCUMENT = "document";
    
    /** DOCUMENT property name */
    String SOURCE = "source";
    
    /** DOCUMENT property name */
    String REFERENCE = "reference";

    /** ISSUES property name */
    String ISSUES = "issues";

    /** GROUP_DIVISION property name */
    String GROUP_DIVISIONS = "groupDivisions";

    /** IGNORE property(s) - !!! ALPHABETICAL ORDER !!! */
    String[] IGNORE = (String[]) ArrayUtils.addAll(IPublishable.IGNORE, new String[]
    {
        DOCUMENT, GROUP_DIVISIONS, ISSUES, REFERENCE, REPORT_TYPE, SOURCE
    });

    /** JAVA_SORT property(s) - !!! ALPHABETICAL ORDER !!! */
    String[] JAVA_SORT = new String[]
    {
        ISortBy.ACTION_OPEN, ISortBy.ACTION_TOTAL
    };

    /**
     * @return id
     */
    Long getAuditId();

    /**
     * @return the reference
     */
    String getReference();

    /**
     * @return the name
     */
    String getName();

    /**
     * @return the detail
     */
    String getDetail();

    /**
     * @return the issuedDate
     */
    Date getIssuedDate();

    /**
     * @return the reportType
     */
    IReportType getReportType();

    /**
     * @return the document
     */
    IDocument getDocument();

    /**
     * @return the source
     */
    Character getSource();

    /**
     * @return the issues
     */
    List<IIssue> getIssues();

    /**
     * @return the group/division(s)
     */
    //List<IGroupDivision> getGroupDivision();

}