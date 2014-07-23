package net.apollosoft.ats.audit.domain.hibernate.refdata;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.apollosoft.ats.audit.domain.refdata.IParentRisk;
import net.apollosoft.ats.audit.domain.refdata.IParentRiskCategory;
import net.apollosoft.ats.domain.hibernate.Auditable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLockType;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_parent_risk")
@org.hibernate.annotations.Entity(mutable = false, optimisticLock = OptimisticLockType.VERSION)
@Cache(region = "refdata", usage = CacheConcurrencyStrategy.READ_ONLY)
public class ParentRisk extends Auditable<Long> implements IParentRisk
{

    /** serialVersionUID */
    private static final long serialVersionUID = 2412167353974302080L;

    /** identity persistent field */
    @Id
    @Column(name = "parent_risk_id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long parentRiskId;

    /** persistent field */
    @Basic()
    @Column(name = "parent_risk_name", nullable = false)
    private String name;

    /** persistent field */
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ParentRiskCategory.class)
    @JoinColumn(name = "parent_risk_category_id", nullable = false)
    private IParentRiskCategory category;

    /**
     * 
     */
    public ParentRisk()
    {
        super();
    }

    /**
     * @param parentRiskId
     */
    public ParentRisk(Long parentRiskId)
    {
        setParentRiskId(parentRiskId);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getParentRiskId();
    }

    /**
     * @return
     */
    public Long getParentRiskId()
    {
        if (parentRiskId != null && parentRiskId.longValue() == 0L)
        {
            parentRiskId = null;
        }
        return parentRiskId;
    }

    /**
     *
     * @param parentRiskId
     */
    public void setParentRiskId(Long parentRiskId)
    {
        this.parentRiskId = parentRiskId;
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

    /**
     * @return the category
     */
    public IParentRiskCategory getCategory()
    {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(IParentRiskCategory category)
    {
        this.category = category;
    }

}