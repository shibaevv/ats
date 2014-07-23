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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.apollosoft.ats.domain.security.Principal;
import net.apollosoft.ats.util.DateUtils;
import net.apollosoft.ats.util.Pair;
import net.apollosoft.ats.util.ThreadLocalUtils;
import net.apollosoft.ats.web.WebUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class TransactionFilter implements Filter
{

    /** Used for logging. */
    private static final Logger LOG = Logger.getLogger(TransactionFilter.class);

    /** filterConfig */
    private FilterConfig filterConfig;

    private PlatformTransactionManager transactionManager;

    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException
    {
        this.filterConfig = filterConfig;
        this.transactionManager = (PlatformTransactionManager) WebUtils.getBean(this.filterConfig
            .getServletContext(), "transactionManager");
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy()
    {
        this.transactionManager = null;
        this.filterConfig = null;
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        Date txnStartDate = DateUtils.getCurrentDateTime();

        Object p = WebUtils.getPrincipal();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);
        //eg /mats/login.do
        if (session == null || !httpRequest.isRequestedSessionIdValid()
            || !(p instanceof Principal))
        {
            //execute business logic here
            chain.doFilter(request, response);
            return;
        }

        //user logged in already (has valid authentication)
        //get/set propogationBehavior
        Integer propogationBehavior = WebUtils.getPropogationBehavior(httpRequest);
        if (propogationBehavior == null)
        {
            propogationBehavior = TransactionDefinition.PROPAGATION_REQUIRED; //default
        }
        DefaultTransactionDefinition txnDef = new DefaultTransactionDefinition(propogationBehavior);

        //set readOnly flag
        boolean readOnly = WebUtils.isReadOnly(httpRequest);
        if (LOG.isDebugEnabled())
            LOG.debug("Transaction readOnly=" + readOnly);
        txnDef.setReadOnly(readOnly);

        // Return a currently active transaction or create a new one,
        // according to the specified propagation behaviour.
        TransactionStatus txnStatus = transactionManager.getTransaction(txnDef);
        if (readOnly)
        {
            //set rollbackOnly for readOnly transaction
            txnStatus.setRollbackOnly();
        }
        //set transaction current date/user (will be used in AuditableListener for Auditable data)
        ThreadLocalUtils.setDate(txnStartDate);
        //get/set user
        Principal principal = (Principal) p;
        Pair<String> user = StringUtils.isBlank(principal.getUserId()) ? null : new Pair<String>(
            principal.getUserId(), principal.getUsername());
        ThreadLocalUtils.setUser(user);

        String uri = httpRequest.getRequestURI(); // eg /mats/xxx.do
        try
        {
            //execute business logic here
            chain.doFilter(request, response);
            //
            if (txnStatus.isRollbackOnly())
            {
                transactionManager.rollback(txnStatus);
                if (LOG.isDebugEnabled())
                    LOG.debug("... Transaction rollback [" + uri + "].");
            }
            else if (txnStatus.isNewTransaction()) // && txnStatus.isCompleted()
            {
                transactionManager.commit(txnStatus);
                if (LOG.isDebugEnabled())
                    LOG.debug("... Transaction commit [" + uri + "].");
            }
        }
        catch (Throwable e) // Exception or Error
        {
            LOG.error("... Unable to doFilter in transaction: " + e.getMessage(), e);
            if (txnStatus.isNewTransaction())
            {
                transactionManager.rollback(txnStatus);
                String query = httpRequest.getQueryString();
                LOG.error("... Transaction rollback [" + uri + (query == null ? "" : ("?" + query))
                    + "].");
            }
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Unable to doFilter in transaction: " + e.getMessage());
        }
        finally
        {
            //cleanup thread
            ThreadLocalUtils.clear();
        }
    }

}