package net.apollosoft.ats.audit.web.taglib;

import java.util.Arrays;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import net.apollosoft.ats.domain.security.IFunction;
import net.apollosoft.ats.domain.security.IRole;
import net.apollosoft.ats.domain.security.Principal;
import net.apollosoft.ats.web.WebUtils;

import org.apache.commons.lang.StringUtils;


/**
 * @see struts logic::present module="/file" path="/find"
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class PresentTag extends TagSupport
{

    /** serialVersionUID */
    private static final long serialVersionUID = 404433346755993439L;

    /** Security Group name(s) - this user belong to */
    protected String role;

    private String[] roles;

    /** module */
    protected String module;

    /** path/query - unique */
    protected String path;

    /**
     * @return the role
     */
    public String getRole()
    {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role)
    {
        this.role = role;
        this.roles = StringUtils.split(role);
        Arrays.sort(this.roles);
    }

    /**
     * @return the module
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

    /**
     * @return the roles
     */
    public String getPath()
    {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String role)
    {
        this.path = role;
    }

    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException
    {
        boolean includeBody = false;
        //role - first
        if (StringUtils.isNotBlank(role))
        {
            includeBody = hasRole();
        }
        //module - second
        else if (StringUtils.isNotBlank(module))
        {
            includeBody = StringUtils.isBlank(path) ? hasModule() : hasModulePath();
        }
        //path - third
        else if (StringUtils.isNotBlank(path))
        {
            includeBody = StringUtils.isBlank(module) ? hasPath() : hasModulePath();
        }
        return includeBody ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }

    /**
     * 
     * @return
     */
    protected boolean hasRole()
    {
        IRole[] items = getRoles();
        //boolean ignoreDefault = items.length > 1; // eg Report Owner, Default (Action Owner)
        for (int i = 0; items != null && i < items.length; i++)
        {
            IRole role = items[i];
            String roleCode = IRole.PREFIX + role.getId();
            if (Arrays.binarySearch(roles, roleCode) >= 0)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if user security profile can access this module.
     * @return
     */
    protected boolean hasModule()
    {
        IRole[] items = getRoles();
        //boolean ignoreDefault = items.length > 1; // eg Report Owner, Default (Action Owner)
        for (int i = 0; items != null && i < items.length; i++)
        {
            IRole role = items[i];
            if (hasModule(role, module))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if user security profile can access this module (DEFAULT role is added to any user profile by default).
     * @return
     */
    protected boolean hasPath()
    {
        IRole[] items = getRoles();
        boolean ignoreDefault = items.length > 1; // eg Report Owner, Default (Action Owner)
        for (int i = 0; items != null && i < items.length; i++)
        {
            IRole role = items[i];
            if (hasQuery(role, path))
            {
                if (!role.isDefault())
                {
                    return true;
                }
                if (ignoreDefault)
                {
                    //special case: ReadOnly and Default (Action Owner) - allow to edit comment
                }
                else
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 
     * @return
     */
    protected boolean hasModulePath()
    {
        IRole[] items = getRoles();
        for (int i = 0; items != null && i < items.length; i++)
        {
            if (hasModuleQuery(items[i], module, path))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @return
     */
    private IRole[] getRoles()
    {
        Object p = WebUtils.getPrincipal(); // anonymousRole (String)
        if (!(p instanceof Principal))
        {
            return null;
        }

        Principal principal = (Principal) p;
        return principal.getRoles();
    }

    /**
     * @param role
     * @param module
     * @return
     */
    private boolean hasModule(IRole role, String module)
    {
        for (IFunction f : role.getFunctions())
        {
            if (module.equals(f.getModule()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @param role
     * @param query
     * @return
     */
    private boolean hasQuery(IRole role, String query)
    {
        for (IFunction f : role.getFunctions())
        {
            if (query.equals(f.getQuery()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @param role
     * @param module
     * @param query
     * @return
     */
    private boolean hasModuleQuery(IRole role, String module, String query)
    {
        for (IFunction f : role.getFunctions())
        {
            if (module.equals(f.getModule()) && query.equals(f.getQuery()))
            {
                return true;
            }
        }
        return false;
    }

}