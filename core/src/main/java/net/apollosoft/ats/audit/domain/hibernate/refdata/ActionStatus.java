package net.apollosoft.ats.audit.domain.hibernate.refdata;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.apollosoft.ats.audit.domain.refdata.IActionStatus;
import net.apollosoft.ats.domain.hibernate.Auditable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLockType;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_action_status")
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
@Cache(region = "refdata", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class ActionStatus extends Auditable<Long> implements IActionStatus
{

    /** serialVersionUID */
    private static final long serialVersionUID = -7723287014379781103L;

    /** identity persistent field */
    @Id
    @Column(name = "action_status_id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long actionStatusId;

    /** persistent field */
    @Basic()
    @Column(name = "action_status_name", nullable = false)
    private String name;

    /**
     * 
     */
    public ActionStatus()
    {
        super();
    }

    /**
     * @param actionStatusId
     */
    public ActionStatus(Long actionStatusId)
    {
        setActionStatusId(actionStatusId);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getActionStatusId();
    }

    /**
     *
     * @return
     */
    public Long getActionStatusId()
    {
        if (actionStatusId != null && actionStatusId.longValue() == 0L)
        {
            actionStatusId = null;
        }
        return actionStatusId;
    }

    /**
     *
     * @param actionStatusId
     */
    public void setActionStatusId(Long actionStatusId)
    {
        this.actionStatusId = actionStatusId;
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
    
    /**
     * @return
     */
    public boolean isClosed()
    {
        return actionStatusId != null && (actionStatusId == ActionStatusEnum.CLOSED.ordinal());
    }

    /**
     * @return
     */
    public boolean isOpen()
    {
        return actionStatusId != null && (actionStatusId == ActionStatusEnum.OPEN.ordinal());
    }

}