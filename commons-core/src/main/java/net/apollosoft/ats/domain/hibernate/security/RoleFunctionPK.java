package net.apollosoft.ats.domain.hibernate.security;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Embeddable
public class RoleFunctionPK implements Serializable
{

    /** serialVersionUID */
    private static final long serialVersionUID = 2741603770348177979L;

    /** persistent field */
    //@Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    /** persistent field */
    //@Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "function_id", nullable = false)
    private Function function;

    public RoleFunctionPK()
    {
        super();
    }

    public RoleFunctionPK(Role role, Function user)
    {
        this.role = role;
        this.function = user;
    }

    /**
     * @return the function
     */
    public Function getFunction()
    {
        return function;
    }

    //    /**
    //     * @param function the function to set
    //     */
    //    public void setFunction(FunctionDto function)
    //    {
    //        this.user = function;
    //    }

    /**
     * @return the role
     */
    public Role getRole()
    {
        return role;
    }

    //    /**
    //     * @param role the role to set
    //     */
    //    public void setRole(RoleDto role)
    //    {
    //        this.role = role;
    //    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public boolean equals(Object other)
    {
        if (this == other)
        {
            return true; // super implementation
        }
        if (other != null)
        {
            Class clazz = getClass();
            Class otherClazz = other.getClass();
            if (clazz.isAssignableFrom(otherClazz) || otherClazz.isAssignableFrom(clazz))
            {
                RoleFunctionPK castOther = (RoleFunctionPK) other;
                //use "castOther.getXxxId()" as this instance can be hibernate enhancer
                if (getFunction() != null && castOther.getFunction() != null && getRole() != null
                    && castOther.getRole() != null)
                {
                    return new EqualsBuilder().append(getRole(), castOther.getRole()).append(
                        getFunction(), castOther.getFunction()).isEquals();
                }
            }
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return getFunction() == null || getRole() != null ? super.hashCode()
            : new HashCodeBuilder().append(getRole()).append(getFunction()).toHashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this).append("role", getRole())
            .append("function", getFunction()).toString();
    }

}