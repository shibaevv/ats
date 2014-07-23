package net.apollosoft.ats.web.servlet;

import java.util.Map;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class SetupContextListener extends ContextLoaderListener
{
    /** Used for logging. */
    private static final Logger LOG = Logger.getLogger(SetupContextListener.class);
    static
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            for (Map.Entry<String, String> entry : System.getenv().entrySet())
            {
                sb.append(entry.getKey() + "=" + entry.getValue()).append('\n');
            }
            LOG.info("System Environment:\n" + sb.toString());
        }
        catch (Exception e)
        {
            //java.security.AccessControlException: access denied (java.lang.RuntimePermission getenv.*)
            LOG.warn(e.getMessage());
        }

        sb.setLength(0);
        try
        {
            for (Map.Entry<Object, Object> entry : System.getProperties().entrySet())
            {
                sb.append(entry.getKey() + "=" + entry.getValue()).append('\n');
            }
            //System.setProperty("user.home", "/tmp");
            LOG.info("System Properties:\n" + sb.toString());
        }
        catch (Exception e)
        {
            LOG.warn(e.getMessage());
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.web.context.ContextLoaderListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent event)
    {
        super.contextInitialized(event);
        LOG.info("Application has been initialised.");
        try
        {
            //CompassGps compassGps = (CompassGps) WebUtils.getBean(event.getServletContext(), "compassGps");
            //compassGps.index();
        }
        catch (Exception e)
        {
            LOG.error("FAILED to index all CompassGps devices: " + e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.web.context.ContextLoaderListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent event)
    {
        LOG.info("Application has been destroyed.");
        super.contextDestroyed(event);
    }

}