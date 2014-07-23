package net.apollosoft.ats.audit.domain.hibernate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.apollosoft.ats.audit.domain.ITag;
import net.apollosoft.ats.audit.domain.hibernate.refdata.CategoryType;
import net.apollosoft.ats.audit.domain.refdata.ICategoryType;
import net.apollosoft.ats.domain.hibernate.BaseModel;

import org.compass.annotations.SearchableDynamicName;
import org.compass.annotations.SearchableDynamicValue;


@Entity
@Table(name = "ats_tag")
@org.hibernate.annotations.Entity(mutable = true)
//@Cache(region = "compass", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Tag extends BaseModel<Long> implements ITag
{

    /** serialVersionUID */
    private static final long serialVersionUID = 6356795637854124540L;

    /** identity persistent field */
    @Id
    @Column(name = "tag_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tagId;

    @Basic()
    @Column(name = "category_type_id", nullable = false)
    @SearchableDynamicName
    private Long categoryTypeId;

    @Basic()
    @Column(name = "category_value", nullable = false)
    @SearchableDynamicValue
    private String categoryValue;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = CategoryType.class)
    @JoinColumn(name = "category_type_id", nullable = false, insertable = false, updatable = false)
    private ICategoryType categoryType;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.Base#toString()
     */
    @Override
    public String toString()
    {
        return categoryTypeId + ":" + categoryValue;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getTagId();
    }

    /**
     * @return the tagId
     */
    public Long getTagId()
    {
        return tagId;
    }

    /**
     * @param tagId the tagId to set
     */
    public void setTagId(Long tagId)
    {
        this.tagId = tagId;
    }

    /**
     * @return the categoryTypeId
     */
    public Long getCategoryTypeId()
    {
        return categoryTypeId;
    }

    /**
     * @param categoryTypeId the categoryTypeId to set
     */
    public void setCategoryTypeId(Long categoryTypeId)
    {
        this.categoryTypeId = categoryTypeId;
    }

    /**
     * @return the categoryValue
     */
    public String getCategoryValue()
    {
        return categoryValue;
    }

    /**
     * @param categoryValue the categoryValue to set
     */
    public void setCategoryValue(String categoryValue)
    {
        this.categoryValue = categoryValue;
    }

    /**
     * @return the categoryType
     */
    public ICategoryType getCategoryType()
    {
        return categoryType;
    }

    /**
     * @param categoryType the categoryType to set
     */
    public void setCategoryType(ICategoryType categoryType)
    {
        this.categoryType = categoryType;
    }

}