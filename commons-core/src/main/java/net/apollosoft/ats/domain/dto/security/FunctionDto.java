package net.apollosoft.ats.domain.dto.security;

import java.io.Serializable;

import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.dto.AuditableDto;
import net.apollosoft.ats.domain.security.IFunction;
import net.apollosoft.ats.util.BeanUtils;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class FunctionDto extends AuditableDto<Long> implements IFunction, Serializable
{

    /** serialVersionUID */
    private static final long serialVersionUID = 6329592758481131672L;

    private Long functionId;

    private String name;

    private String detail;

    private String module;

    private String query;

    private boolean home;

    /** used in UI to select role home function */
    private transient boolean roleHome = false;

    /** used in UI to select role function */
    private transient boolean add2role = false;

    /**
     * 
     */
    public FunctionDto()
    {
        super();
    }

    /**
     * @param functionId
     */
    public FunctionDto(Long functionId)
    {
        setFunctionId(functionId);
    }

    /**
     * 
     * @param source
     * @throws DomainException
     */
    public FunctionDto(IFunction source) throws DomainException
    {
        BeanUtils.copyProperties(source, this, IFunction.IGNORE);
        //TODO: re-factor to jsp ${fn:replace(entity.name,'"','\'')}
        //name = Formatter.formatJson(name);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.IBase#getId()
     */
    public Long getId()
    {
        return getFunctionId();
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.security.IFunction#getFunctionId()
     */
    public Long getFunctionId()
    {
        return functionId;
    }

    /**
     *
     * @param functionId
     */
    public void setFunctionId(Long functionId)
    {
        this.functionId = functionId;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.hibernate.security.IFunction#getName()
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
     * @see net.apollosoft.ats.audit.domain.security.IFunction#getDetail()
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

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.security.IFunction#getModule()
     */
    public String getModule()
    {
        return module;
    }

    /**
     * @param module the module to set
     */
    public void setModule(String module)
    {
        this.module = module;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.domain.security.IFunction#getQuery()
     */
    public String getQuery()
    {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query)
    {
        this.query = query;
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

    /**
     * @return the roleHome
     */
    public boolean isRoleHome()
    {
        return roleHome;
    }

    /**
     * @param roleHome the roleHome to set
     */
    public void setRoleHome(boolean roleHome)
    {
        this.roleHome = roleHome;
    }

    /**
     * @return the roleSelected
     */
    public boolean isAdd2role()
    {
        return add2role;
    }

    /**
     * @param roleSelected the roleSelected to set
     */
    public void setAdd2role(boolean roleSelected)
    {
        this.add2role = roleSelected;
    }

}