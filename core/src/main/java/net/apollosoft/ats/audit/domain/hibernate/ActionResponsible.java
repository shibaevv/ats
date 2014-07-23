package net.apollosoft.ats.audit.domain.hibernate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.apollosoft.ats.audit.domain.IAction;
import net.apollosoft.ats.domain.hibernate.Auditable;
import net.apollosoft.ats.domain.hibernate.security.User;
import net.apollosoft.ats.domain.security.IUser;

import org.hibernate.annotations.OptimisticLockType;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@IdClass(ActionResponsiblePK.class)
@Table(name = "ats_action_responsible")
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
//@Cache(region = "audit", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class ActionResponsible extends Auditable<ActionResponsiblePK>
{

    /** serialVersionUID */
    private static final long serialVersionUID = -2822399030569190253L;

    /** persistent field */
    @Id
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Action.class)
    @JoinColumn(name = "action_id", nullable = false)
    private IAction action;

    /** persistent field */
    @Id
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private IUser user;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public ActionResponsiblePK getId()
    {
        return new ActionResponsiblePK(getAction(), getUser());
    }

    /**
     * @return the action
     */
    public IAction getAction()
    {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(IAction action)
    {
        this.action = action;
    }

    /**
     * @return the user
     */
    public IUser getUser()
    {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(IUser user)
    {
        this.user = user;
    }

}