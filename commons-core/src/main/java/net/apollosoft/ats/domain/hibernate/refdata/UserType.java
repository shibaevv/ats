package net.apollosoft.ats.domain.hibernate.refdata;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.apollosoft.ats.domain.hibernate.Auditable;
import net.apollosoft.ats.domain.refdata.IUserType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLockType;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_user_type")
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
@Cache(region = "refdata", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class UserType extends Auditable<Long> implements IUserType
{

    /** serialVersionUID */
    private static final long serialVersionUID = 4717090892053217393L;

    /** identity persistent field */
    @Id
    @Column(name = "user_type_id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long userTypeId;

    /** persistent field */
    @Basic()
    @Column(name = "user_type_name", nullable = false)
    private String name;

    /**
     * 
     */
    public UserType()
    {
        super();
    }

    /**
     * @param userTypeId
     */
    public UserType(Long userTypeId)
    {
        setUserTypeId(userTypeId);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getUserTypeId();
    }

    /**
     *
     * @return
     */
    public Long getUserTypeId()
    {
        if (userTypeId != null && userTypeId.longValue() == 0L)
        {
            userTypeId = null;
        }
        return userTypeId;
    }

    /**
     *
     * @param userTypeId
     */
    public void setUserTypeId(Long userTypeId)
    {
        this.userTypeId = userTypeId;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.refdata.IUserType#getName()
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