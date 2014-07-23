package net.apollosoft.ats.audit.domain.hibernate;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import net.apollosoft.ats.audit.domain.IAction;
import net.apollosoft.ats.domain.hibernate.security.User;
import net.apollosoft.ats.domain.security.IUser;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Embeddable
public class ActionResponsiblePK implements Serializable
{

    /** serialVersionUID */
    private static final long serialVersionUID = 7380391027990489438L;

    /** persistent field */
    //@Id
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Action.class)
    @JoinColumn(name = "action_id", nullable = false)
    private IAction action;

    /** persistent field */
    //@Id
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private IUser user;

    public ActionResponsiblePK()
    {
        super();
    }

    public ActionResponsiblePK(IAction action, IUser user)
    {
        this.action = action;
        this.user = user;
    }

    /**
     * @return the action
     */
    public IAction getAction()
    {
        return action;
    }

//    /**
//     * @param action the action to set
//     */
//    public void setAction(IAction action)
//    {
//        this.action = action;
//    }

    /**
     * @return the user
     */
    public IUser getUser()
    {
        return user;
    }

//    /**
//     * @param user the user to set
//     */
//    public void setUser(IUser user)
//    {
//        this.user = user;
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
                ActionResponsiblePK castOther = (ActionResponsiblePK) other;
                //use "castOther.getXxxId()" as this instance can be hibernate enhancer
                if (getAction() != null && castOther.getAction() != null && getUser() != null
                    && castOther.getUser() != null)
                {
                    return new EqualsBuilder().append(getAction(), castOther.getAction())
                        .append(getUser(), castOther.getUser()).isEquals();
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
        return getAction() == null || getUser() != null ? super.hashCode()
            : new HashCodeBuilder().append(getAction()).append(getUser()).toHashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this).append("action", getAction()).append("user",
            getUser()).toString();
    }

}