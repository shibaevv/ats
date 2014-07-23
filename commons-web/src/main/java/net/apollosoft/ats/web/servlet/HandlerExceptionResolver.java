package net.apollosoft.ats.web.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class HandlerExceptionResolver extends SimpleMappingExceptionResolver
{

    /** Used for logging. */
    private static final Log LOG = LogFactory.getLog(HandlerExceptionResolver.class);

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.handler.SimpleMappingExceptionResolver#doResolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception e)
    {
        //log error
        LOG.error(e.getMessage(), e);
        //
        ModelAndView model = super.doResolveException(request, response, handler, e);
        model.addObject("message", ExceptionUtils.getMessage(e));
        model.addObject("stackTrace", ExceptionUtils.getStackTrace(e));
        //
        if (e instanceof net.apollosoft.ats.domain.security.SecurityException)
        {
            //Principal principal = (Principal) WebUtils.getPrincipal();
            //SECURITY_LOG.error(getStatisticMessage(response, principal));
            try
            {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            }
            catch (IOException ignore)
            {
                LOG.info(ignore.getMessage(), ignore);
            }
        }
        //
        return model;
    }

}