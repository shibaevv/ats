package net.apollosoft.ats.audit.domain.dto;

import java.util.Date;

import net.apollosoft.ats.domain.IPublishable;
import net.apollosoft.ats.domain.dto.AuditableDto;
import net.apollosoft.ats.domain.security.IUser;


/**
 * Base class for all dto Publishable objects.
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public abstract class PublishableDto<T> extends AuditableDto<T> implements IPublishable<T>
{

    private IUser publishedBy;

    private Date publishedDate;

    private Boolean validate;

    /**
     * @return the publishedBy
     */
    public IUser getPublishedBy()
    {
        return publishedBy;
    }

    /**
     * @param publishedBy the publishedBy to set
     */
    public void setPublishedBy(IUser publishedBy)
    {
        this.publishedBy = publishedBy;
    }

    /**
     * @return the publishedDate
     */
    public Date getPublishedDate()
    {
        return publishedDate;
    }

    /**
     * @param publishedDate the publishedDate to set
     */
    public void setPublishedDate(Date publishedDate)
    {
        this.publishedDate = publishedDate;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IPublishable#isPublished()
     */
    public boolean isPublished()
    {
        return getPublishedDate() != null && getPublishedBy() != null;
    }

    /**
     * @return the validate
     */
    public Boolean getValidate()
    {
        return validate;
    }

    /**
     * @param validate the validate to set
     */
    public void setValidate(Boolean validate)
    {
        this.validate = validate;
    }

}