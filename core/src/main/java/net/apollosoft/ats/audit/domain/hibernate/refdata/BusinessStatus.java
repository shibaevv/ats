package net.apollosoft.ats.audit.domain.hibernate.refdata;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.apollosoft.ats.audit.domain.refdata.IActionStatus;
import net.apollosoft.ats.audit.domain.refdata.IBusinessStatus;
import net.apollosoft.ats.domain.hibernate.Auditable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLockType;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_business_status")
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
@Cache(region = "refdata", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class BusinessStatus extends Auditable<Long> implements IBusinessStatus
{

    /** serialVersionUID */
    private static final long serialVersionUID = -7434529217553682949L;

    /** identity persistent field */
    @Id
    @Column(name = "business_status_id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long businessStatusId;

    /** persistent field */
    @Basic()
    @Column(name = "business_status_name", nullable = false)
    private String name;

    /** persistent field */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ActionStatus.class)
    @JoinColumn(name = "action_status_id", nullable = false)
    private IActionStatus actionStatus;

    /**
     * 
     */
    public BusinessStatus()
    {
        super();
    }

    /**
     * @param businessStatusId
     */
    public BusinessStatus(Long businessStatusId)
    {
        setBusinessStatusId(businessStatusId);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getBusinessStatusId();
    }

    /**
     *
     * @return
     */
    public Long getBusinessStatusId()
    {
        if (businessStatusId != null && businessStatusId.longValue() == 0L)
        {
            businessStatusId = null;
        }
        return businessStatusId;
    }

    /**
     *
     * @param businessStatusId
     */
    public void setBusinessStatusId(Long businessStatusId)
    {
        this.businessStatusId = businessStatusId;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.refdata.IBusinessStatus#getName()
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

    /**
     * @return the actionStatus
     */
    public IActionStatus getActionStatus()
    {
        if (actionStatus == null)
        {
            actionStatus = new ActionStatus();
        }
        return actionStatus;
    }

    /**
     * @param actionStatus the actionStatus to set
     */
    public void setActionStatus(IActionStatus actionStatus)
    {
        this.actionStatus = actionStatus;
    }

    /**
     * @return
     */
    public boolean isNotActioned()
    {
        return businessStatusId != null
            && (businessStatusId == BusinessStatusEnum.NOT_ACTIONED.ordinal());
    }

    /**
     * @return
     */
    public boolean isInProgress()
    {
        return businessStatusId != null
            && (businessStatusId == BusinessStatusEnum.IN_PROGRESS.ordinal());
    }

    /**
     * @return
     */
    public boolean isImplemented()
    {
        return businessStatusId != null
            && (businessStatusId == BusinessStatusEnum.IMPLEMENTED.ordinal());
    }

    /**
     * @return
     */
    public boolean isNoLongerApplicable()
    {
        return businessStatusId != null
            && (businessStatusId == BusinessStatusEnum.NO_LONGER_APPLICABLE.ordinal());
    }

}