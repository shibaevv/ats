package net.apollosoft.ats.audit.domain.hibernate.refdata;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.apollosoft.ats.audit.domain.refdata.IActionFollowupStatus;
import net.apollosoft.ats.domain.hibernate.Auditable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLockType;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_action_followup_status")
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
@Cache(region = "refdata", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class ActionFollowupStatus extends Auditable<Long> implements IActionFollowupStatus
{

    /** serialVersionUID */
    private static final long serialVersionUID = -3543868784470980054L;

    /** identity persistent field */
    @Id
    @Column(name = "action_followup_status_id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long actionFollowupStatusId;

    /** persistent field */
    @Basic()
    @Column(name = "action_followup_status_name", nullable = false)
    private String name;

    /**
     * 
     */
    public ActionFollowupStatus()
    {
        super();
    }

    /**
     * @param actionStatusId
     */
    public ActionFollowupStatus(Long actionFollowupStatusId)
    {
        setActionFollowupStatusId(actionFollowupStatusId);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getActionFollowupStatusId();
    }

    /**
     *
     * @return
     */
    public Long getActionFollowupStatusId()
    {
        if (actionFollowupStatusId != null && actionFollowupStatusId.longValue() == 0L)
        {
            actionFollowupStatusId = null;
        }
        return actionFollowupStatusId;
    }

    /**
     *
     * @param actionFollowupStatusId
     */
    public void setActionFollowupStatusId(Long actionFollowupStatusId)
    {
        this.actionFollowupStatusId = actionFollowupStatusId;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.refdata.IActionStatus#getName()
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

}