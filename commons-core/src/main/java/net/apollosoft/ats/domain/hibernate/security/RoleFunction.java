package net.apollosoft.ats.domain.hibernate.security;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.apollosoft.ats.domain.hibernate.Auditable;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@IdClass(RoleFunctionPK.class)
@Table(name = "ats_role_function")
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
@Where(clause = "")
//@Cache(region = "security", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class RoleFunction extends Auditable<RoleFunctionPK>
{

    /** serialVersionUID */
    private static final long serialVersionUID = 5805377895987059299L;

    /** persistent field */
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    /** persistent field */
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "function_id", nullable = false)
    private Function function;

    /** persistent field */
    @Basic()
    @Column(name = "home", nullable = false)
    @Type(type = "yes_no")
    private boolean home;

    public RoleFunction()
    {
        super();
    }

    public RoleFunction(Role role, Function function)
    {
        this.role = role;
        this.function = function;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public RoleFunctionPK getId()
    {
        return new RoleFunctionPK(getRole(), getFunction());
    }

    /**
     * @return the function
     */
    public Function getFunction()
    {
        return function;
    }

    /**
     * @param function the function to set
     */
    public void setFunction(Function function)
    {
        this.function = function;
    }

    /**
     * @return the role
     */
    public Role getRole()
    {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(Role role)
    {
        this.role = role;
    }

    /**
     * @return the home
     */
    public boolean isHome()
    {
        return home;
    }

    /**
     * @param home the home to set
     */
    public void setHome(boolean home)
    {
        this.home = home;
    }

}