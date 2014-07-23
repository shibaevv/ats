package net.apollosoft.ats.web.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.apollosoft.ats.domain.security.IFunction;
import net.apollosoft.ats.domain.security.IRole;
import net.apollosoft.ats.domain.security.Principal;
import net.apollosoft.ats.web.WebUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class SecurityFilter implements Filter
{

    /** Used for ACCESS_DENY logging. */
    private static final Log SECURITY_LOG = LogFactory.getLog("SECURITY_LOG");

    /** filterConfig */
    //private FilterConfig filterConfig;

    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException
    {
        //this.filterConfig = filterConfig;
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy()
    {
        //this.filterConfig = null;
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        Object p = WebUtils.getPrincipal();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);
        //eg /mats/login.do
        String uri = httpRequest.getRequestURI(); // eg /mats/xxx.do
        String query = httpRequest.getQueryString();
        if (StringUtils.isNotBlank(query))
        {
            uri = uri + '?' + query;
        }
        if (session == null || !httpRequest.isRequestedSessionIdValid()
            || !(p instanceof Principal) || (uri.endsWith("/login.do")))
        {
            chain.doFilter(request, response);
            return;
        }

        //get user principal
        Principal principal = (Principal) p;
        try
        {
            //
            IRole[] roles = principal.getRoles();
            if (!hasQuery(roles, uri))
            {
                throw new SecurityException("ACCESS DENIED: " + uri);
            }

            if (SECURITY_LOG.isDebugEnabled())
                SECURITY_LOG.debug(getStatisticMessage(httpRequest, principal));

            //execute business logic here
            chain.doFilter(request, response);
        }
        catch (net.apollosoft.ats.domain.security.SecurityException e)
        {
            SECURITY_LOG.error(getStatisticMessage(httpRequest, principal));
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
        catch (Throwable e) // Exception or Error
        {
            SECURITY_LOG.error(getStatisticMessage(httpRequest, principal));
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * Check if user security profile can access this uri.
     * @param roles
     * @param uri
     * @return
     */
    private boolean hasQuery(final IRole[] roles, final String uri)
    {
        //eg /mats/action/main.do
        //lets cut /mats/ part
        int idx = uri.indexOf('/');
        String action = uri.substring(idx + 1);
        idx = action.indexOf('/');
        action = action.substring(idx + 1);
        for (int i = 0; roles != null && i < roles.length; i++)
        {
            for (IFunction f : roles[i].getFunctions())
            {
                String path = f.getQuery();
                // can use wildcard, eg setup/*
                path = path.replace('*', ' ').trim();
                if (action.startsWith(path))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Create message to log
     * @param httpRequest
     * @param user
     * @return
     */
    private String getStatisticMessage(HttpServletRequest httpRequest, Principal principal)
    {
        String method = httpRequest.getMethod();
        String uri = httpRequest.getRequestURI(); // eg /mats/xxx.do
        String query = httpRequest.getQueryString();
        return httpRequest.getRemoteHost() + "," + httpRequest.getRemoteAddr() + ","
            + (principal == null ? "" : principal.getUsername()) + "," + method + "," + uri
            + (query == null ? "" : ("?" + query));
    }

}