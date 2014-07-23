package net.apollosoft.ats.domain.dto.security;

import java.util.ArrayList;
import java.util.List;

import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.dto.AuditableDto;
import net.apollosoft.ats.domain.security.IRole;
import net.apollosoft.ats.domain.security.IUser;
import net.apollosoft.ats.util.BeanUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class UserDto extends AuditableDto<String> implements IUser
{

    /** SYSTEM for automatic processing (id=null) */
    public static final UserDto SYSTEM = new UserDto("SYSTEM");

    private String userId;

    private String name;

    private String lastName;

    private String firstName;

    private String otherNames;

    private String title;

    private String login;

    private String groupName;

    private Character status;

    private List<UserGroupDivisionDto> groupDivisions;

    private List<IRole> roles;

    /**
     * 
     */
    public UserDto()
    {
        super();
    }

    public UserDto(String userId)
    {
        this.userId = userId;
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public UserDto(IUser source) throws DomainException
    {
        BeanUtils.copyProperties(source, this, IUser.IGNORE);
        //TODO: re-factor to jsp ${fn:replace(entity.name,'"','\'')}
        //name = Formatter.formatJson(name);
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
    public String getId()
    {
        return getUserId();
    }

    /**
     * @return the userId
     */
    public String getUserId()
    {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    /**
     * @return the lastName
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

    /**
     * @return the firstName
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

    /**
     * @return the otherNames
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
     * label to show in jsp
     */
    public String getName()
    {
        if (name == null)
        {
            name = StringUtils.join(new String[]
            {
                getFullName(), groupName, login
            }, " ");
        }
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.security.IUser#getFullName()
     */
    public String getFullName()
    {
        return StringUtils.join(new String[]
        {
            firstName, lastName
        }, " ");
    }

    /**
     * @return the login
     */
    public String getLogin()
    {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login)
    {
        this.login = login;
    }

    /**
     * @return the groupName
     */
    public String getGroupName()
    {
        return groupName;
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    /**
     * @return the status
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
    public List<UserGroupDivisionDto> getGroupDivisions()
    {
        if (groupDivisions == null)
        {
            groupDivisions = new ArrayList<UserGroupDivisionDto>();
        }
        return groupDivisions;
    }

    /**
     * @return the roles
     */
    public List<IRole> getRoles()
    {
        if (roles == null)
        {
            roles = new ArrayList<IRole>();
        }
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<IRole> roles)
    {
        this.roles = roles;
    }

}