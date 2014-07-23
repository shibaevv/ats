package net.apollosoft.ats.audit.web.servlet;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.dao.TemplateDao;
import net.apollosoft.ats.audit.domain.ITemplate;
import net.apollosoft.ats.domain.hibernate.Template;
import net.apollosoft.ats.domain.security.IRole;
import net.apollosoft.ats.domain.security.IRole.RoleEnum;
import net.apollosoft.ats.util.DateUtils;
import net.apollosoft.ats.util.Formatter;
import net.apollosoft.ats.web.WebUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class SessionListener implements HttpSessionListener
{

    /** Used for logging. */
    private static final Log LOG = LogFactory.getLog(SessionListener.class);

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent event)
    {
        LOG.debug("Session created.");

        HttpSession session = event.getSession();
        //date/time pattern
        session.setAttribute("datePattern", DateUtils.DEFAULT_DATE_PATTERN);
        session.setAttribute("dateTimePattern", DateUtils.DEFAULT_DATETIME_PATTERN);
        //newLineChar
        session.setAttribute("newLineChar", Formatter.NEW_LINE_CHAR);
        //
        //help url(s)
        TemplateDao templateDao = (TemplateDao) WebUtils.getBean(event.getSession()
            .getServletContext(), "templateDao");
        String link = getLinkByRef(
            templateDao,
            ITemplate.L001,
            "");
        session.setAttribute("applicationHelpUrl", link);
        link = getLinkByRef(
            templateDao,
            ITemplate.L002,
            "");
        session.setAttribute("auditHelpUrl", link);
        link = getLinkByRef(templateDao, ITemplate.L003, "javascript:;");
        session.setAttribute("issueHelpUrl", link);
        link = getLinkByRef(
            templateDao,
            ITemplate.L004,
            "");
        session.setAttribute("actionHelpUrl", link);
        //TODO: move to SetupContextListener (application scope) - menu url(s)
        session.setAttribute("myactionUrl", "myaction/main.do");
        session.setAttribute("actionUrl", "action/main.do");
        session.setAttribute("auditUrl", "audit/main.do");
        session.setAttribute("auditAdminUrl", "auditAdmin/main.do");
        session.setAttribute("userUrl", "user/view.do");
        session.setAttribute("setupUrl", "setup/main.do");
        //TODO: move to SetupContextListener (application scope) - roles
        session.setAttribute("DEFAULT", IRole.PREFIX + RoleEnum.DEFAULT.ordinal()); // soft role (exists for all users) ACTION_OWNER
        session.setAttribute("SYSTEM_ADMIN", IRole.PREFIX + RoleEnum.SYSTEM_ADMIN.ordinal());
        session.setAttribute("REPORT_OWNER", IRole.PREFIX + RoleEnum.REPORT_OWNER.ordinal());
        session.setAttribute("REPORT_TEAM", IRole.PREFIX + RoleEnum.REPORT_TEAM.ordinal());
        session.setAttribute("OVERSIGHT_TEAM", IRole.PREFIX + RoleEnum.OVERSIGHT_TEAM.ordinal());
        session.setAttribute("READ_ONLY", IRole.PREFIX + RoleEnum.READ_ONLY.ordinal());
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent event)
    {
        LOG.debug("Session destroyed.");
    }

    /**
     * 
     * @param templateDao
     * @param ref
     * @param defaultLink
     * @return
     */
    private String getLinkByRef(TemplateDao templateDao, String ref, String defaultLink)
    {
        try
        {
            Template t = templateDao.findByReference(ref);
            return t == null ? defaultLink : new String(t.getContent());
        }
        catch (DaoException ignore)
        {
            LOG.error(ignore.getMessage(), ignore);
            return defaultLink;
        }
    }

}