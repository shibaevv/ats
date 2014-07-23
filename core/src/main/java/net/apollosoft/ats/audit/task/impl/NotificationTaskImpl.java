package net.apollosoft.ats.audit.task.impl;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.apollosoft.ats.audit.dao.ActionDao;
import net.apollosoft.ats.audit.dao.TemplateDao;
import net.apollosoft.ats.audit.dao.UserMatrixDao;
import net.apollosoft.ats.audit.domain.ITemplate;
import net.apollosoft.ats.audit.domain.hibernate.Action;
import net.apollosoft.ats.audit.service.DocumentService;
import net.apollosoft.ats.audit.service.EmailService;
import net.apollosoft.ats.audit.service.MailData;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.task.NotificationTask;
import net.apollosoft.ats.domain.dto.security.UserDto;
import net.apollosoft.ats.domain.hibernate.security.UserMatrix;
import net.apollosoft.ats.domain.security.IUser;
import net.apollosoft.ats.domain.security.IRole.RoleEnum;
import net.apollosoft.ats.util.DateUtils;
import net.apollosoft.ats.util.ThreadLocalUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class NotificationTaskImpl implements NotificationTask
{

    /** logger. */
    private static final Log LOG = LogFactory.getLog(NotificationTaskImpl.class);

    @Autowired
    @Qualifier("actionDao")
    //@Resource(name="actionDao")
    private ActionDao actionDao;

    @Autowired
    @Qualifier("templateDao")
    //@Resource(name="templateDao")
    private TemplateDao templateDao;

    @Autowired
    @Qualifier("userMatrixDao")
    //@Resource(name="userMatrixDao")
    private UserMatrixDao userMatrixDao;

    @Autowired
    @Qualifier("documentService")
    //@Resource(name="documentService")
    private DocumentService documentService;

    @Autowired
    @Qualifier("emailService")
    //@Resource(name="emailService")
    private EmailService emailService;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.task.DailyTask#oversightTeam()
     */
    public void oversightTeam()
    {
        LOG.info("Task Started [oversightTeam]..");
        try
        {
            ThreadLocalUtils.setDate(DateUtils.getCurrentDateTime());
            ThreadLocalUtils.setUser(UserDto.SYSTEM);
            //
            //Date dueDateTo = DateUtils.getEndOfMonth(ThreadLocalUtils.getDate());
            Date dueDateTo = DateUtils.getStartOfMonth(ThreadLocalUtils.getDate());
            dueDateTo = DateUtils.addMonths(dueDateTo, 1);
            List<UserMatrix> userMatrix = userMatrixDao.findByRoleId(new Long(
                RoleEnum.OVERSIGHT_TEAM.ordinal()));
            //prepare map of oversight users and add overdue actions
            Map<IUser, Set<Action>> userActions = new Hashtable<IUser, Set<Action>>();
            for (UserMatrix um : userMatrix)
            {
                IUser u = um.getUser();
                if (!userActions.containsKey(u))
                {
                    userActions.put(u, new HashSet<Action>());
                }
                List<Action> actions = actionDao.findOpen(dueDateTo, um);
                userActions.get(u).addAll(actions);
            }
            //send email based on R003
            ITemplate r003 = (ITemplate) templateDao.findByReference(ITemplate.R003);
            sendEmail(r003, userActions);
            //
            LOG.info("..Task Finished [oversightTeam]");
        }
        catch (Exception e)
        {
            LOG.error("Task Failed [oversightTeam]: " + e.getMessage(), e);
            //TODO: send email?
        }
        finally
        {
            ThreadLocalUtils.clear();
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.task.DailyTask#personResponsible()
     */
    public void personResponsible()
    {
        LOG.info("Task Started [personResponsible]..");
        try
        {
            ThreadLocalUtils.setDate(DateUtils.getCurrentDateTime());
            ThreadLocalUtils.setUser(UserDto.SYSTEM);
            //
            //Date dueDateTo = DateUtils.getEndOfMonth(ThreadLocalUtils.getDate());
            Date dueDateTo = DateUtils.getStartOfMonth(ThreadLocalUtils.getDate());
            dueDateTo = DateUtils.addMonths(dueDateTo, 1);
            List<Action> actions = actionDao.findOpen(dueDateTo, null);
            //
            Map<IUser, Set<Action>> userActions = new Hashtable<IUser, Set<Action>>();
            for (Action a : actions)
            {
                List<IUser> users = a.getResponsibleUsers();
                for (IUser u : users)
                {
                    if (!userActions.containsKey(u))
                    {
                        userActions.put(u, new HashSet<Action>());
                    }
                    userActions.get(u).add(a);
                }
            }
            //send email based on R004
            ITemplate r004 = (ITemplate) templateDao.findByReference(ITemplate.R004);
            sendEmail(r004, userActions);
            //
            LOG.info("..Task Finished [personResponsible]");
        }
        catch (Exception e)
        {
            LOG.error("Task Failed [personResponsible]: " + e.getMessage(), e);
            //TODO: send email?
        }
        finally
        {
            ThreadLocalUtils.clear();
        }
    }

    /**
     * 
     * @param template
     * @param actions
     * @throws ServiceException
     */
    @SuppressWarnings("unchecked")
    private void sendEmail(ITemplate template, Map<IUser, Set<Action>> actions)
        throws ServiceException
    {
        final Date now = DateUtils.getStartOfDay(ThreadLocalUtils.getDate());
        for (Map.Entry<IUser, Set<Action>> entry : actions.entrySet())
        {
            Set<Action> emailActions = entry.getValue();
            if (emailActions.isEmpty())
            {
                continue;
            }
            Collection<Action> overdueActions = CollectionUtils.select(emailActions, new Predicate()
            {
                public boolean evaluate(Object obj)
                {
                    Action a = (Action) obj;
                    return now.after(a.getDueDate());
                }
            });
            Collection<Action> dueSoonActions = CollectionUtils.select(emailActions, new Predicate()
            {
                public boolean evaluate(Object obj)
                {
                    Action a = (Action) obj;
                    return !now.after(a.getDueDate());
                }
            });
            Map<String, Object> params = new Hashtable<String, Object>();
            IUser user = entry.getKey();
            params.put("toUser", user);
            params.put("overdueActions", overdueActions);
            params.put("dueSoonActions", dueSoonActions);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            documentService.createDocument(template, params, output);
            MailData data = new MailData();
            data.setTo(user);
            data.setSubject(template.getDetail());
            data.setText(output.toString());
            emailService.sendMail(data);
        }
    }

}