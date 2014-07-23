package net.apollosoft.ats.task;

import junit.framework.Assert;
import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.task.NotificationTask;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@org.junit.Ignore
public class NotificationTaskTest extends BaseTransactionalTest
{

    @Autowired
    @Qualifier("notificationTask")
    //@Resource(name="notificationTask")
    private NotificationTask notificationTask;

    /**
     * Test method for {@link net.apollosoft.ats.audit.task.NotificationTask#oversightTeam()}.
     */
    @Test
    public final void testOversightTeam()
    {
        try
        {
            notificationTask.oversightTeam();
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.task.NotificationTask#personResponsible()}.
     */
    @Test
    public final void testPersonResponsible()
    {
        try
        {
            notificationTask.personResponsible();
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

}