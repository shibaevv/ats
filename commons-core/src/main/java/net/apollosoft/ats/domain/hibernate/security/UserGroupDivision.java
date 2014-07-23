package net.apollosoft.ats.domain.hibernate.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.apollosoft.ats.domain.hibernate.refdata.UserType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.OptimisticLockType;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_user_group_division")
@org.hibernate.annotations.Entity(mutable = true, optimisticLock = OptimisticLockType.VERSION)
//@Cache(region = "security", usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class UserGroupDivision extends GroupDivision
{

    /** serialVersionUID */
    private static final long serialVersionUID = 4012446705275851339L;

    /** identity persistent field */
    @Id
    @Column(name="user_group_division_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userGroupDivisionId;

    /** persistent field */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** persistent field */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_type_id", nullable = false)
    private UserType userType;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getUserGroupDivisionId();
    }

    /**
     * @return the userGroupDivisionId
     */
    public Long getUserGroupDivisionId()
    {
        return userGroupDivisionId;
    }

    /**
     * @param userGroupDivisionId the userGroupDivisionId to set
     */
    public void setUserGroupDivisionId(Long userGroupDivisionId)
    {
        this.userGroupDivisionId = userGroupDivisionId;
    }

    /**
     * @return the user
     */
    public User getUser()
    {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user)
    {
        this.user = user;
    }

    /**
     * @return the userType
     */
    public UserType getUserType()
    {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(UserType userType)
    {
        this.userType = userType;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object other)
    {
        if (this == other)
        {
            return true; // super implementation
        }
        if (other != null)
        {
            UserGroupDivision castOther = (UserGroupDivision) other;
            return new EqualsBuilder().append(getUser(), castOther.getUser()).append(
                getGroup(), castOther.getGroup()).append(getDivision(), castOther.getDivision())
                .isEquals();
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return new HashCodeBuilder().append(getUser()).append(getGroup()).append(getDivision())
            .toHashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this).append("user", getUser()).append("group", getGroup())
            .append("division", getDivision()).toString();
    }

}