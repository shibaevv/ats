package net.apollosoft.ats.audit.domain;

import java.util.List;

import net.apollosoft.ats.audit.domain.refdata.IParentRisk;
import net.apollosoft.ats.audit.domain.refdata.IRating;
import net.apollosoft.ats.domain.IPublishable;

import org.apache.commons.lang.ArrayUtils;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface IIssue extends IPublishable<Long>
{

    /** RATING property name */
    String RATING = "rating";

    /** AUDIT property name */
    String AUDIT = "audit";

    /** ACTIONS property name */
    String ACTIONS = "actions";
    
    /** ACTIONS property name */
    String RISK = "risk";

    /** IGNORE property(s) */
    String[] IGNORE = (String[]) ArrayUtils.addAll(IPublishable.IGNORE, new String[]
    {
        RATING, AUDIT, ACTIONS, RISK
    });

    /**
     * @return id
     */
    Long getIssueId();

    /**
     * @return the listIndex
     */
    Byte getListIndex();

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
     * @return the parent risk
     */
    IParentRisk getRisk();

    /**
     * @return the rating
     */
    IRating getRating();

    /**
     * @return
     */
    IAudit getAudit();

    /**
     * @return
     */
    List<IAction> getActions();

}