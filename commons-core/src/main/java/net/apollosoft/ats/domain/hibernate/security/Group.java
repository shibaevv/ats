package net.apollosoft.ats.domain.hibernate.security;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import net.apollosoft.ats.domain.hibernate.Auditable;
import net.apollosoft.ats.domain.security.IDivision;
import net.apollosoft.ats.domain.security.IGroup;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_group")
@org.hibernate.annotations.Entity(mutable = false)
//@Where(clause = "deleted_date IS NULL")
@Cache(region = "security", usage = CacheConcurrencyStrategy.READ_ONLY)
public class Group extends Auditable<Long> implements IGroup
{

    /** serialVersionUID */
    private static final long serialVersionUID = -956700469032224753L;

    /** identity persistent field */
    @Id
    @Column(name = "group_id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long groupId;

    /** persistent field */
    @Basic()
    @Column(name = "group_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, targetEntity = Division.class)
    @OrderBy("name DESC")
    @Where(clause = "deleted_date IS NULL")
    private List<IDivision> divisions;

    /**
     * 
     */
    public Group()
    {
        super();
    }

    /**
     * @param groupId
     */
    public Group(Long groupId)
    {
        setGroupId(groupId);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getGroupId();
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.security.IGroup#getGroupId()
     */
    public Long getGroupId()
    {
        return groupId;
    }

    /**
     *
     * @param groupId
     */
    public void setGroupId(Long groupId)
    {
        this.groupId = groupId;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.security.IGroup#getName()
     */
    public String getName()
    {
        return this.name;
    }

    public void setName(String login)
    {
        this.name = login;
    }

    /**
     * @return the divisions
     */
    public List<IDivision> getDivisions()
    {
        return divisions;
    }

    /**
     * @param divisions the divisions to set
     */
    public void setDivisions(List<IDivision> divisions)
    {
        this.divisions = divisions;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.Base#toString()
     */
    @Override
    public String toString()
    {
        return name;
    }

}