package net.apollosoft.ats.domain.hibernate.security;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.apollosoft.ats.domain.hibernate.Auditable;
import net.apollosoft.ats.domain.security.IDivision;
import net.apollosoft.ats.domain.security.IGroup;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_division")
@org.hibernate.annotations.Entity(mutable = false)
//@Where(clause = "deleted_date IS NULL")
@Cache(region = "security", usage = CacheConcurrencyStrategy.READ_ONLY)
public class Division extends Auditable<Long> implements IDivision
{

    /** serialVersionUID */
    private static final long serialVersionUID = -4805987298209253246L;

    /** Any within group */
    public static final Division ANY = null;

    /** ALL */
    public static final Division ALL = new Division(Long.MAX_VALUE);

    /** GROUP property name */
    public static final String GROUP = "group";

    /** identity persistent field */
    @Id
    @Column(name="division_id", nullable=false)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long divisionId;

    /** persistent field */
    @Basic()
    @Column(name="division_name", nullable=false)
    private String name;

    /** persistent field */
    @ManyToOne(fetch=FetchType.LAZY, targetEntity=Group.class)
    @JoinColumn(name="group_id", nullable=true)
    private IGroup group;

    /**
     * 
     */
    public Division()
    {
        super();
    }

    /**
     * @param divisionId
     */
    public Division(Long divisionId)
    {
        setDivisionId(divisionId);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getDivisionId();
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.security.IDivision#getDivisionId()
     */
    public Long getDivisionId()
    {
        return divisionId;
    }

    /**
     *
     * @param divisionId
     */
    public void setDivisionId(Long divisionId)
    {
        this.divisionId = divisionId;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.security.IDivision#getName()
     */
    public String getName()
    {
        return this.name;
    }

    public void setName(String login)
    {
        this.name = login;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.security.IDivision#getGroup()
     */
    public IGroup getGroup()
    {
        return group;
    }

    public void setGroup(IGroup group)
    {
        this.group = group;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.Base#toString()
     */
    @Override
    public String toString()
    {
        return group + " " + name;
    }

}