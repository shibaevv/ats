package net.apollosoft.ats.audit.security;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;

import net.apollosoft.ats.audit.service.SecurityService;
import net.apollosoft.ats.audit.service.UserService;
import net.apollosoft.ats.domain.dto.security.RoleDto;
import net.apollosoft.ats.domain.dto.security.UserDto;
import net.apollosoft.ats.domain.security.Principal;
import net.apollosoft.ats.util.Pair;
import net.apollosoft.ats.util.ThreadLocalUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


/**
 * Class to verify user
 * Once verified, it can use users info in the database for application specific roles
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider
{

    private static final Log LOG = LogFactory.getLog(AuthenticationProvider.class);

    //@Autowired
    //@Qualifier("ldapTemplate")
    //@Resource(name="ldapTemplate")
    private LdapTemplate ldapTemplate;

    //@Autowired
    //@Qualifier("securityService")
    //@Resource(name="securityService")
    private SecurityService securityService;

    //@Autowired
    //@Qualifier("userService")
    //@Resource(name="userService")
    private UserService userService;

    private boolean ignorePassword;

    /**
     * @param ldapTemplate the ldapTemplate to set
     */
    public void setLdapTemplate(LdapTemplate ldapTemplate)
    {
        this.ldapTemplate = ldapTemplate;
    }

    /**
     * @param ignorePassword the ignorePassword to set
     */
    public void setIgnorePassword(boolean ignorePassword)
    {
        this.ignorePassword = ignorePassword;
    }

    /**
     * @param securityService the securityService to set
     */
    public void setSecurityService(SecurityService securityService)
    {
        this.securityService = securityService;
    }

    /**
     * @param userService the userService to set
     */
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.providers.AuthenticationProvider#authenticate(org.springframework.security.Authentication)
     */
    //TODO: run in txn
    public Authentication authenticate(final Authentication authentication)
        throws AuthenticationException
    {
        String login = authentication.getName();
        String password = (String) authentication.getCredentials();
        if (StringUtils.isBlank(login) || StringUtils.isBlank(password))
        {
            throw new BadCredentialsException("username and password cannot be empty");
        }
        List<String> users = searchUser(login);
        if (users == null || users.size() != 1)
        {
            LOG.warn("Unsuccessful login attempt for userid " + login);
            throw new BadCredentialsException("Invalid username " + login);
        }
        if (!ignorePassword && !checkPassword(users.get(0), password))
        {
            throw new BadCredentialsException("Invalid username / password " + login);
        }

        try
        {
            //get user
            UserDto u = userService.findByLogin(login);
            Pair<String> user = new Pair<String>(u.getUserId(), u.getLogin());
            //get user roles
            ThreadLocalUtils.setUser(user);
            List<RoleDto> roles = securityService.findUserRoles();
            //
            final Principal principal = new Principal(user, roles);
            final Authentication result = new UsernamePasswordAuthenticationToken(principal,
                password, principal.getGrantedAuthority());
            return result;
        }
        catch (Exception e)
        {
            throw new AuthenticationCredentialsNotFoundException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.security.providers.AuthenticationProvider#supports(java.lang.Class)
     */
    public boolean supports(Class clazz)
    {
        return true;
    }

    @SuppressWarnings("unchecked")
    private List<String> searchUser(String login)
    {
        List<String> users;
        if (ignorePassword)
        {
            users = new ArrayList<String>();
            users.add(login);
        }
        else
        {
            AndFilter filter = new AndFilter();
            filter.and(new LikeFilter("cn", login));
            users = ldapTemplate.search("", filter.encode(), new AttributesMapper()
            {
                /* (non-Javadoc)
                 * @see org.springframework.ldap.core.AttributesMapper#mapFromAttributes(javax.naming.directory.Attributes)
                 */
                public Object mapFromAttributes(Attributes attrs) throws NamingException
                {
                    return attrs.get("distinguishedName") != null ? (String) attrs.get(
                        "distinguishedName").get() : null;
                }
            });
            //LOG.debug(users != null ? users.size() + " records for " + userID : "no users found.");
        }
        return users;
    }

    private boolean checkPassword(String dn, String password)
    {
        ContextSource contextSource = ldapTemplate.getContextSource();
        DirContext dirContext = contextSource.getReadOnlyContext();
        try
        {
            Hashtable<?, ?> env = dirContext.getEnvironment();
            //LOG.debug("LDAP env entries - " + env);
            String url = (String) env.get(DirContext.PROVIDER_URL);

            LdapContextSource ldapCtx = new LdapContextSource();
            ldapCtx.setUrl(url);
            ldapCtx.setUserDn(dn);
            ldapCtx.setPassword(password);
            ldapCtx.setPooled(false);
            ldapCtx.afterPropertiesSet();
            //DirContext ldapDirContext = 
            ldapCtx.getReadWriteContext();
            return true;
        }
        catch (Exception e)
        {
            LOG.warn("Authentication failed for user " + dn + "\n" + e.getMessage());
            return false;
        }
    }

}