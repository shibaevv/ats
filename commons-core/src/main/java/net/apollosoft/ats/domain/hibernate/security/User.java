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
import net.apollosoft.ats.domain.security.IDivision;
import net.apollosoft.ats.domain.security.IGroup;
import net.apollosoft.ats.domain.security.IUser;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.WhereJoinTable;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Entity
@Table(name = "ats_user")
//@Where(clause = "status != 'T'") //IUser.TERMINATED need to see ALL
@org.hibernate.annotations.Entity(mutable = false)
//@Cache(region = "security", usage = CacheConcurrencyStrategy.READ_ONLY)
public class User extends Auditable<String> implements IUser
{

    /** serialVersionUID */
    private static final long serialVersionUID = 4536416089698014554L;

    /** SYSTEM for automatic processing (id=null) */
    public static final User SYSTEM = new User();

    /** identity persistent field */
    @Id
    @Column(name = "id", nullable = false)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String userId;

    /** persistent field */
    @Basic()
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /** persistent field */
    @Basic()
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /** persistent field */
    @Basic()
    @Column(name = "other_names", nullable = true)
    private String otherNames;

    /** persistent field */
    @Basic()
    @Column(name = "title", nullable = true)
    private String title;

    /** persistent field */
    @Basic()
    @Column(name = "userid", nullable = false)
    private String login;

    /** persistent field */
    @Basic()
    @Column(name = "status", nullable = false)
    private Character status;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade =
    {
        CascadeType.PERSIST
    })
    private List<UserGroupDivision> groupDivisions;

    /** persistent field */
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Group.class)
    @JoinTable(name = "ats_user_group_division", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "group_id"))
    @WhereJoinTable(clause = "deleted_date IS NULL")
    private List<IGroup> groups;

    /** persistent field */
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Division.class)
    @JoinTable(name = "ats_user_group_division", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "division_id", referencedColumnName = "division_id"))
    @WhereJoinTable(clause = "deleted_date IS NULL")
    private List<IDivision> divisions;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade =
    {
        CascadeType.PERSIST
    })
    private List<UserMatrix> userMatrix;

    /**
     * 
     */
    public User()
    {
        super();
    }

    /**
     * @param userId
     */
    public User(String userId)
    {
        setUserId(userId);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.Base#toString()
     */
    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append(ID, getId()).append(getFullName()).toString();
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.security.IUser#getId()
     */
    public String getId()
    {
        return getUserId();
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.security.IUser#getLastName()
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.security.IUser#getFirstName()
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.security.IUser#getOtherNames()
     */
    public String getOtherNames()
    {
        return otherNames;
    }

    /**
     * @param otherNames the otherNames to set
     */
    public void setOtherNames(String otherNames)
    {
        this.otherNames = otherNames;
    }

    /**
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * label to show
     */
    public String getName()
    {
        return getFullName() + " - " + login;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.security.IUser#getFullName()
     */
    public String getFullName()
    {
        return ((firstName == null ? "" : firstName) + " " + (lastName == null ? "" : lastName))
            .trim();
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.security.IUser#getUserId()
     */
    public String getUserId()
    {
        return userId;
    }

    /**
     *
     * @param userId
     */
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.security.IUser#getLogin()
     */
    public String getLogin()
    {
        return this.login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.security.IUser#getStatus()
     */
    public Character getStatus()
    {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Character status)
    {
        this.status = status;
    }

    /**
     * @return the groupDivisions
     */
    public List<UserGroupDivision> getGroupDivisions()
    {
        if (groupDivisions == null)
        {
            groupDivisions = new ArrayList<UserGroupDivision>();
        }
        return groupDivisions;
    }

    /**
     * @return the groups
     */
    public List<IGroup> getGroups()
    {
        if (groups == null)
        {
            groups = new ArrayList<IGroup>();
        }
        return groups;
    }

    /**
     * @return the divisions
     */
    public List<IDivision> getDivisions()
    {
        if (divisions == null)
        {
            divisions = new ArrayList<IDivision>();
        }
        return divisions;
    }

    /**
     * @return the userMatrix
     */
    public List<UserMatrix> getUserMatrix()
    {
        if (userMatrix == null)
        {
            userMatrix = new ArrayList<UserMatrix>();
        }
        return userMatrix;
    }

    /**
     * @param entity
     */
    public void add(UserMatrix entity)
    {
        entity.setUser(this);
        getUserMatrix().add(entity);
    }

}