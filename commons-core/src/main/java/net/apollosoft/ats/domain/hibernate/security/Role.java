package net.apollosoft.ats.domain.hibernate.security;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.apollosoft.ats.domain.hibernate.Auditable;
import net.apollosoft.ats.domain.security.IFunction;
import net.apollosoft.ats.domain.security.IRole;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_role")
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
@Where(clause = "")
@Cache(region = "security", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Role extends Auditable<Long> implements IRole
{

    /** serialVersionUID */
    private static final long serialVersionUID = 984887891182198097L;

    /** identity persistent field */
    @Id
    @Column(name = "role_id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;

    /** persistent field */
    @Basic()
    @Column(name = "role_name", nullable = false)
    private String name;

    /** persistent field */
    @Basic()
    @Column(name = "role_detail", nullable = true)
    private String detail;

    /** persistent field */
    @Basic()
    @Column(name = "role_priority", nullable = true)
    private Byte priority;

    /** persistent field */
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Function.class)
    @JoinTable(name = "ats_role_function",
        joinColumns = @JoinColumn(name="role_id", referencedColumnName="role_id"),
        inverseJoinColumns = @JoinColumn(name="function_id", referencedColumnName="function_id"))
    @WhereJoinTable(clause = "deleted_date IS NULL")
    private List<IFunction> functions;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<RoleFunction> roleFunctions;

    /**
     * 
     */
    public Role()
    {
        super();
    }

    /**
     * @param roleId
     */
    public Role(Long roleId)
    {
        setRoleId(roleId);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getRoleId();
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.security.IRole#getRoleId()
     */
    public Long getRoleId()
    {
        return roleId;
    }

    /**
     *
     * @param roleId
     */
    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.security.IRole#getName()
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
     * @see net.apollosoft.ats.audit.domain.security.IRole#getDetail()
     */
    public String getDetail()
    {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    /**
     * @return the priority
     */
    public Byte getPriority()
    {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(Byte priority)
    {
        this.priority = priority;
    }

    /**
     * @return the functions
     */
    public List<IFunction> getFunctions()
    {
        if (functions == null)
        {
            functions = new ArrayList<IFunction>();
        }
        return functions;
    }

    /**
     * @param functions the functions to set
     */
    public void setFunctions(List<IFunction> divisions)
    {
        this.functions = divisions;
    }

    /**
     * @return the roleFunctions
     */
    public List<RoleFunction> getRoleFunctions()
    {
        if (roleFunctions == null)
        {
            roleFunctions = new ArrayList<RoleFunction>();
        }
        return roleFunctions;
    }

    /**
     * @param roleFunctions the roleFunctions to set
     */
    public void setRoleFunctions(List<RoleFunction> roleFunctions)
    {
        this.roleFunctions = roleFunctions;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.security.IRole#isDefault()
     */
    public boolean isDefault()
    {
        return roleId != null && roleId == RoleEnum.DEFAULT.ordinal();
    }

}