package net.apollosoft.ats.domain.hibernate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import net.apollosoft.ats.domain.IAuditable;
import net.apollosoft.ats.domain.hibernate.security.User;
import net.apollosoft.ats.domain.security.IUser;

import org.hibernate.annotations.Where;


/**
 * Base class for all auditable domain objects.
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@EntityListeners({AuditableListener.class})
@MappedSuperclass
@Where(clause = "deleted_date IS NULL")
//@Filter(name="deleted", condition="deleted_date IS NULL")
public abstract class Auditable<T> extends BaseModel<T> implements IAuditable<T>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 6925094922770564452L;

    /** persistent field */
    @ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
    @JoinColumn(name="created_by", nullable=true)
    private IUser createdBy;

    /** nullable persistent field */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_date", nullable=true)
    private Date createdDate;

    /** nullable persistent field */
    @ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
    @JoinColumn(name="updated_by", nullable=true)
    private IUser updatedBy;

    /** nullable persistent field */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_date", nullable=true)
    private Date updatedDate;

    /** nullable persistent field */
    @ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
    @JoinColumn(name="deleted_by", nullable=true)
    private IUser deletedBy;

    /** nullable persistent field */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="deleted_date", nullable=true)
    private Date deletedDate;

    /** persistent field */
    @Version
    @Column(name="lock_version", nullable=false)
    private long lockVersion;

    /**
     *
     */
    public IUser getCreatedBy()
    {
        return this.createdBy;
    }

    protected void setCreatedBy(IUser createdBy)
    {
        this.createdBy = createdBy;
    }

    /**
     *
     */
    public Date getCreatedDate()
    {
        return this.createdDate;
    }

    protected void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    /**
     *
     */
    public IUser getUpdatedBy()
    {
        return this.updatedBy;
    }

    protected void setUpdatedBy(IUser updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    /**
     *
     */
    public Date getUpdatedDate()
    {
        return this.updatedDate;
    }

    protected void setUpdatedDate(Date updatedDate)
    {
        this.updatedDate = updatedDate;
    }

    /**
     *
     */
    public IUser getDeletedBy()
    {
        return deletedBy;
    }

    public void setDeletedBy(IUser deletedBy)
    {
        this.deletedBy = deletedBy;
    }

    /**
     *
     */
    public Date getDeletedDate()
    {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate)
    {
        this.deletedDate = deletedDate;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IAuditable#isDeleted()
     */
    public boolean isDeleted()
    {
        return deletedDate != null || deletedBy != null;
    }

    /**
     *
     */
    public long getLockVersion()
    {
        return this.lockVersion;
    }

    public void setLockVersion(long lockVersion)
    {
        this.lockVersion = lockVersion;
    }

}