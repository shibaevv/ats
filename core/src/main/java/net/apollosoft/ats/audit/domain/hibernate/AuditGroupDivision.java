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
@Table(name = "ats_audit_group_division")
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
//@Cache(region = "audit", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class AuditGroupDivision extends GroupDivision
{

    /** serialVersionUID */
    private static final long serialVersionUID = -8585997604049433150L;

    /** identity persistent field */
    @Id
    @Column(name = "audit_group_division_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long auditGroupDivisionId;

    /** persistent field */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audit_id", nullable = false)
    private Audit audit;

    /**
     * 
     */
    public AuditGroupDivision()
    {
        super();
    }

    /**
     * 
     * @param group
     * @param division
     */
    public AuditGroupDivision(Group group, Division division)
    {
        super(group, division);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getAuditGroupDivisionId();
    }

    /**
     * @return the auditGroupDivisionId
     */
    public Long getAuditGroupDivisionId()
    {
        if (auditGroupDivisionId != null && auditGroupDivisionId.longValue() == 0L)
        {
            auditGroupDivisionId = null;
        }
        return auditGroupDivisionId;
    }

    /**
     * @param auditGroupDivisionId the auditGroupDivisionId to set
     */
    public void setAuditGroupDivisionId(Long auditGroupDivisionId)
    {
        this.auditGroupDivisionId = auditGroupDivisionId;
    }

    /**
     * @return the audit
     */
    public Audit getAudit()
    {
        return audit;
    }

    /**
     * @param audit the audit to set
     */
    public void setAudit(Audit audit)
    {
        this.audit = audit;
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
            AuditGroupDivision castOther = (AuditGroupDivision) other;
            return new EqualsBuilder().append(getAudit(), castOther.getAudit()).append(getGroup(),
                castOther.getGroup()).append(getDivision(), castOther.getDivision()).isEquals();
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return new HashCodeBuilder().append(getAudit()).append(getGroup()).append(getDivision())
            .toHashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this).append("audit", getAudit()).append("group", getGroup())
            .append("division", getDivision()).toString();
    }

    public Long getGroupDivisionId()
    {
        // TODO Auto-generated method stub
        return null;
    }

}