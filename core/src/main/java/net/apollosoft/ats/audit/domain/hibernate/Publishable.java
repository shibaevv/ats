package net.apollosoft.ats.audit.domain.hibernate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.apollosoft.ats.domain.IPublishable;
import net.apollosoft.ats.domain.hibernate.Auditable;
import net.apollosoft.ats.domain.hibernate.security.User;
import net.apollosoft.ats.domain.security.IUser;


/**
 * Base class for all auditable domain objects.
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@MappedSuperclass
//@Filter(name="deleted", condition="deleted_date IS NULL")
public abstract class Publishable<T> extends Auditable<T> implements IPublishable<T>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -4959691517955932164L;

    /** persistent field */
    @ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
    @JoinColumn(name="published_by", nullable=true)
    private IUser publishedBy;

    /** nullable persistent field */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="published_date", nullable=true)
    private Date publishedDate;

    /**
     *
     */
    public IUser getPublishedBy()
    {
        return this.publishedBy;
    }

    public void setPublishedBy(IUser publishedBy)
    {
        this.publishedBy = publishedBy;
    }

    /**
     *
     */
    public Date getPublishedDate()
    {
        return this.publishedDate;
    }

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

}