package net.apollosoft.ats.web;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public final class WebUtils
{

    /** logger. */
    //private static final Log LOG = LogFactory.getLog(WebUtils.class);

    private WebUtils()
    {
    }

    /**
     * 
     * @param servletContext
     * @return
     */
    public static WebApplicationContext getApplicationContext(ServletContext servletContext)
    {
        return WebApplicationContextUtils.getWebApplicationContext(servletContext);
    }

    /**
     *
     * @param servletContext
     * @param name Spring bean name.
     * @return Spring bean instance.
     */
    public static Object getBean(ServletContext servletContext, String name)
    {
        return getApplicationContext(servletContext).getBean(name);
    }

    /**
     * Get security principal
     * @return
     */
    public static Object getPrincipal()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null ? null : authentication.getPrincipal(); // anonymousRole (String)
    }

    /**
     *
     * @param request
     * @return
     */
    public static Integer getPropogationBehavior(HttpServletRequest request)
    {
        String value = request.getParameter("propogationBehavior");
        return StringUtils.isBlank(value) ? null : Integer.valueOf(value);
    }

    /**
     * Default readOnly = true
     * @param request
     * @return
     */
    public static boolean isReadOnly(HttpServletRequest request)
    {
        String value = request.getParameter("readOnly");
        return StringUtils.isBlank(value) || Boolean.valueOf(value).booleanValue();
    }

    /**
     *
     * @param response
     * @param content
     * @param contentType
     * @param contentName
     * @throws IOException 
     * @throws Exception
     */
    public static void downloadContent(HttpServletResponse response, byte[] content,
        String contentType, String contentName) throws IOException
    {
        //set expires in 1 second
        response.setDateHeader("Expires", System.currentTimeMillis() + 100 * 1000L);
        //response.setHeader("Cache-Control", "no-cache, must-revalidate, post-check=0, pre-check=0"); // does not work with https
        response.setHeader("Cache-Control", "cache, post-check=0, pre-check=0"); // works with https
        response.setHeader("Pragma", "public");
        //if attachement (instead of inline) is used, IE will not close window after download is finished
        //response.setHeader("Content-Disposition", "attachment; filename=\"" + contentName + "\";");
        response.setHeader("Content-Disposition", "inline; filename=\"" + contentName + "\";");
        //response.setHeader("Content-Disposition", "filename=\"" + contentName + "\";"); // works as well
        response.setContentType(contentType);
        response.setContentLength(content.length);
        //PrintWriter writer = response.getWriter();
        ServletOutputStream output = response.getOutputStream();
        output.write(content);
        //output.flush();
    }

}