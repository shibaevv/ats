package net.apollosoft.ats.domain.dto.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.dto.AuditableDto;
import net.apollosoft.ats.domain.security.IFunction;
import net.apollosoft.ats.domain.security.IRole;
import net.apollosoft.ats.util.BeanUtils;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class RoleDto extends AuditableDto<Long> implements IRole, Serializable
{

    /** serialVersionUID */
    private static final long serialVersionUID = 7081573532814207320L;

    public static final RoleDto SYSTEM_ADMIN = new RoleDto(RoleEnum.SYSTEM_ADMIN.ordinal());

    public static final RoleDto REPORT_OWNER = new RoleDto(RoleEnum.REPORT_OWNER.ordinal());

    public static final RoleDto REPORT_TEAM = new RoleDto(RoleEnum.REPORT_TEAM.ordinal());

    public static final RoleDto OVERSIGHT_TEAM = new RoleDto(RoleEnum.OVERSIGHT_TEAM.ordinal());

    public static final RoleDto READ_ONLY = new RoleDto(RoleEnum.READ_ONLY.ordinal());

    public static final RoleDto DEFAULT = new RoleDto(RoleEnum.DEFAULT.ordinal());

    private Long id;

    private String name;

    private String detail;

    private Byte priority;

    private List<IFunction> functions;

    /**
     * 
     */
    public RoleDto()
    {
        super();
    }

    /**
     * @param id
     */
    public RoleDto(Long id)
    {
        this.id = id;
    }

    /**
     * @param id
     */
    public RoleDto(int id)
    {
        this(new Long(id));
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public RoleDto(IRole source) throws DomainException
    {
        BeanUtils.copyProperties(source, this, IRole.IGNORE);
        //TODO: re-factor to jsp ${fn:replace(entity.name,'"','\'')}
        //name = Formatter.formatJson(name);
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
        return id;
    }

    /**
     *
     * @param id
     */
    public void setRoleId(Long id)
    {
        this.id = id;
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

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.security.IRole#isDefault()
     */
    public boolean isDefault()
    {
        return DEFAULT.equals(this);
    }

}