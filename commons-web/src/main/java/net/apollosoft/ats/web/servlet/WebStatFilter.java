package net.apollosoft.ats.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.apollosoft.ats.domain.security.Principal;
import net.apollosoft.ats.util.DateUtils;
import net.apollosoft.ats.web.WebUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class WebStatFilter implements Filter
{

    /** Used for logging. */
    //private static final Logger LOG = Logger.getLogger(WebStatFilter.class);

    /** Used for WEB_STAT logging. */
    private static final Log WEB_STAT = LogFactory.getLog("WEB_STAT");

    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException
    {

    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy()
    {

    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        Date txnStartDate = DateUtils.getCurrentDateTime();

        //execute business logic here
        chain.doFilter(request, response);

        //log performance
        Object p = WebUtils.getPrincipal();
        Principal principal = p instanceof Principal ? (Principal) p : null;
        WEB_STAT.info(getStatisticMessage((HttpServletRequest) request, principal, txnStartDate));
    }

    /**
     * Create message to log performance
     * @param httpRequest
     * @param user
     * @param startDate
     * @return
     */
    private String getStatisticMessage(HttpServletRequest httpRequest, Principal principal,
        Date startDate)
    {
        String method = httpRequest.getMethod();
        String uri = httpRequest.getRequestURI(); // eg /ats/xxx.do
        String query = httpRequest.getQueryString();
        double delta = ((System.currentTimeMillis() - startDate.getTime()) * 1000) / 1000000.;
        return httpRequest.getRemoteHost() + "," + httpRequest.getRemoteAddr() + ","
            + (principal == null ? "" : principal.getUsername()) + "," + method + "," + uri
            + (query == null ? "" : ("?" + query)) + "," + delta;
    }

}