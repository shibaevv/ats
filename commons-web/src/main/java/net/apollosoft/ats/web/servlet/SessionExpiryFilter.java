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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class SessionExpiryFilter implements Filter
{

    /** Used for logging. */
    private static final Log LOG = LogFactory.getLog(SessionExpiryFilter.class);

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
        //filterConfig = null;
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String uri = httpRequest.getRequestURI(); // eg /webapp/xxx.do
        String query = httpRequest.getQueryString();
        String url = uri + (query == null ? "" : "?" + query);

        if (uri.equals(httpRequest.getContextPath() + "/"))
        {
            if (LOG.isDebugEnabled())
                LOG.debug("Welcome. RequestURI: " + url);
        }
        else if (uri.contains("/login"))
        {
            if (LOG.isDebugEnabled())
                LOG.debug("Login. RequestURI: " + url);
        }
        else if (uri.contains("/logout"))
        {
            if (LOG.isDebugEnabled())
                LOG.debug("Logout. RequestURI: " + url);
        }
        else if (isRequestedSessionExpired(httpRequest))
        {
            LOG.warn("Session expired. RequestURI: " + url);
            if (!uri.contains("/login"))
            {
                LOG.warn("Redirecting to login page..");
                //used to set response failure
                //SC_TEMPORARY_REDIRECT = 307, SC_UNAUTHORIZED = 401, SC_PROXY_AUTHENTICATION_REQUIRED = 407
                httpResponse.setStatus(HttpServletResponse.SC_PROXY_AUTHENTICATION_REQUIRED);
                response.getWriter().write("Your session has expired. Please login again.");

                //response.getWriter().write("<script language=javascript>");
                //response.getWriter().write("top.location.href='" + loginURL + "';");
                //response.getWriter().write("</script>");

                //httpResponse.sendRedirect("javascript:top.location.href='" + welcomeURL + "'");
                //httpResponse.sendRedirect(welcomeURL);
                return;
            }
        }

        //execute business logic here
        //if (LOG.isDebugEnabled())
        //    LOG.debug("[BEGIN] doFilter");
        chain.doFilter(httpRequest, httpResponse);
        //if (LOG.isDebugEnabled())
        //    LOG.debug("[END] doFilter");
    }

    /**
     *
     * @param request
     * @return
     */
    private boolean isRequestedSessionExpired(HttpServletRequest request)
    {
        if (request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid())
        {
            //will never create new session and thus set new session cookie to response
            HttpSession session = request.getSession(false);
            if (session == null)
            {
                return true;
            }
            if (session.isNew())
            {
                return false;
            }
        }
        return false;
    }

}