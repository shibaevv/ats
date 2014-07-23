package net.apollosoft.ats.domain.dto;

import java.util.Date;

import net.apollosoft.ats.domain.Base;
import net.apollosoft.ats.domain.IAuditable;
import net.apollosoft.ats.domain.security.IUser;


/**
 * Base class for all dto auditable objects.
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public abstract class AuditableDto<T> extends Base<T> implements IAuditable<T>
{

    private IUser createdBy;

    private Date createdDate;

    private IUser updatedBy;

    private Date updatedDate;

    private IUser deletedBy;

    private Date deletedDate;

    private long lockVersion;

    /**
     * @return the createdBy
     */
    public IUser getCreatedBy()
    {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(IUser createdBy)
    {
        this.createdBy = createdBy;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate()
    {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    /**
     * @return the updatedBy
     */
    public IUser getUpdatedBy()
    {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(IUser updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate()
    {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(Date updatedDate)
    {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the deletedBy
     */
    public IUser getDeletedBy()
    {
        return deletedBy;
    }

    /**
     * @param deletedBy the deletedBy to set
     */
    public void setDeletedBy(IUser deletedBy)
    {
        this.deletedBy = deletedBy;
    }

    /**
     * @return the deletedDate
     */
    public Date getDeletedDate()
    {
        return deletedDate;
    }

    /**
     * @param deletedDate the deletedDate to set
     */
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
     * @return the lockVersion
     */
    public long getLockVersion()
    {
        return lockVersion;
    }

    /**
     * @param lockVersion the lockVersion to set
     */
    public void setLockVersion(long lockVersion)
    {
        this.lockVersion = lockVersion;
    }

}