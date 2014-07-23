package net.apollosoft.ats.audit.domain.hibernate.refdata;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.apollosoft.ats.audit.domain.refdata.IParentRiskCategory;
import net.apollosoft.ats.domain.hibernate.Auditable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLockType;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_parent_risk_category")
@org.hibernate.annotations.Entity(mutable = false, optimisticLock = OptimisticLockType.VERSION)
@Cache(region = "refdata", usage = CacheConcurrencyStrategy.READ_ONLY)
public class ParentRiskCategory extends Auditable<Long> implements IParentRiskCategory
{

    /** serialVersionUID */
    private static final long serialVersionUID = 978009667042639198L;

    /** identity persistent field */
    @Id
    @Column(name = "parent_risk_category_id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long parentRiskCategoryId;

    /** persistent field */
    @Basic()
    @Column(name = "parent_risk_category_name", nullable = false)
    private String name;

    /**
     * 
     */
    public ParentRiskCategory()
    {
        super();
    }

    /**
     * @param parentRiskCategoryId
     */
    public ParentRiskCategory(Long parentRiskCategoryId)
    {
        setParentRiskCategoryId(parentRiskCategoryId);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getParentRiskCategoryId();
    }

    /**
     * @return
     */
    public Long getParentRiskCategoryId()
    {
        if (parentRiskCategoryId != null && parentRiskCategoryId.longValue() == 0L)
        {
            parentRiskCategoryId = null;
        }
        return parentRiskCategoryId;
    }

    /**
     *
     * @param parentRiskCategoryId
     */
    public void setParentRiskCategoryId(Long parentRiskCategoryId)
    {
        this.parentRiskCategoryId = parentRiskCategoryId;
    }

    /**
     * @return
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