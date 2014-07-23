package net.apollosoft.ats.audit.domain.hibernate.refdata;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.apollosoft.ats.audit.domain.refdata.IRating;
import net.apollosoft.ats.domain.hibernate.Auditable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLockType;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_rating")
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
@Cache(region = "refdata", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Rating extends Auditable<Long> implements IRating
{

    /** serialVersionUID */
    private static final long serialVersionUID = 6227865286692796525L;

    /** identity persistent field */
    @Id
    @Column(name = "rating_id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long ratingId;

    /** persistent field */
    @Basic()
    @Column(name = "rating_name", nullable = false)
    private String name;

    /**
     * 
     */
    public Rating()
    {
        super();
    }

    /**
     * @param ratingId
     */
    public Rating(Long ratingId)
    {
        setRatingId(ratingId);
    }

    /**
     * @param ratingId
     */
    public Rating(int ratingId)
    {
        this(new Long(ratingId));
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getRatingId();
    }

    /**
     *
     * @return
     */
    public Long getRatingId()
    {
        if (ratingId != null && ratingId.longValue() == 0L)
        {
            ratingId = null;
        }
        return ratingId;
    }

    /**
     *
     * @param ratingId
     */
    public void setRatingId(Long ratingId)
    {
        this.ratingId = ratingId;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.refdata.IRating#getName()
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