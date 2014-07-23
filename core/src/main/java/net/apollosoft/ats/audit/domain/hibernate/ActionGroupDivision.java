package net.apollosoft.ats.audit.domain.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.apollosoft.ats.domain.hibernate.security.Division;
import net.apollosoft.ats.domain.hibernate.security.Group;
import net.apollosoft.ats.domain.hibernate.security.GroupDivision;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.OptimisticLockType;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_action_group_division")
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
//@Cache(region = "action", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class ActionGroupDivision extends GroupDivision
{

    /** serialVersionUID */
    private static final long serialVersionUID = 3896190258326865496L;

    /** identity persistent field */
    @Id
    @Column(name = "action_group_division_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long actionGroupDivisionId;

    /** persistent field */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_id", nullable = false)
    private Action action;

    /**
     * 
     */
    public ActionGroupDivision()
    {
        super();
    }

    /**
     * 
     * @param group
     * @param division
     */
    public ActionGroupDivision(Group group, Division division)
    {
        super(group, division);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getActionGroupDivisionId();
    }

    /**
     * @return the actionGroupDivisionId
     */
    public Long getActionGroupDivisionId()
    {
        return actionGroupDivisionId;
    }

    /**
     * @param actionGroupDivisionId the actionGroupDivisionId to set
     */
    public void setActionGroupDivisionId(Long actionGroupDivisionId)
    {
        this.actionGroupDivisionId = actionGroupDivisionId;
    }

    /**
     * @return the action
     */
    public Action getAction()
    {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(Action action)
    {
        this.action = action;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object other)
    {
        if (this == other)
        {
            return true; // super implementation
        }
        if (other != null)
        {
            ActionGroupDivision castOther = (ActionGroupDivision) other;
            return new EqualsBuilder().append(getAction(), castOther.getAction()).append(
                getGroup(), castOther.getGroup()).append(getDivision(), castOther.getDivision())
                .isEquals();
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return new HashCodeBuilder().append(getAction()).append(getGroup()).append(getDivision())
            .toHashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this).append("action", getAction()).append("group", getGroup())
            .append("division", getDivision()).toString();
    }

}