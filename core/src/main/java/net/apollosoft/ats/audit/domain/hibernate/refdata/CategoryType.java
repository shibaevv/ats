package net.apollosoft.ats.audit.domain.hibernate.refdata;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.apollosoft.ats.audit.domain.refdata.ICategoryType;
import net.apollosoft.ats.domain.hibernate.Auditable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLockType;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_category_type")
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
@Cache(region = "refdata", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class CategoryType extends Auditable<Long> implements ICategoryType
{

    /** serialVersionUID */
    private static final long serialVersionUID = -517338850811260770L;

    /** identity persistent field */
    @Id
    @Column(name = "category_type_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long categoryTypeId;

    /** persistent field */
    @Basic()
    @Column(name = "category_type_name", nullable = false)
    private String name;

    /**
     * 
     */
    public CategoryType()
    {
        super();
    }

    /**
     * @param categoryTypeId
     */
    public CategoryType(Long categoryTypeId)
    {
        setCategoryTypeId(categoryTypeId);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getCategoryTypeId();
    }

    /**
     *
     * @return
     */
    public Long getCategoryTypeId()
    {
        if (categoryTypeId != null && categoryTypeId.longValue() == 0L)
        {
            categoryTypeId = null;
        }
        return categoryTypeId;
    }

    /**
     *
     * @param categoryTypeId
     */
    public void setCategoryTypeId(Long categoryTypeId)
    {
        this.categoryTypeId = categoryTypeId;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.refdata.ICategoryType#getName()
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