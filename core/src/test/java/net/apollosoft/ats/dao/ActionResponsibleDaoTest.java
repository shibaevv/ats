package net.apollosoft.ats.dao;

import junit.framework.Assert;
import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.dao.ActionDao;
import net.apollosoft.ats.audit.dao.ActionResponsibleDao;
import net.apollosoft.ats.audit.dao.AuditDao;
import net.apollosoft.ats.audit.dao.UserDao;
import net.apollosoft.ats.audit.domain.hibernate.Action;
import net.apollosoft.ats.audit.domain.hibernate.ActionResponsible;
import net.apollosoft.ats.audit.domain.hibernate.ActionResponsiblePK;
import net.apollosoft.ats.audit.domain.hibernate.Audit;
import net.apollosoft.ats.domain.hibernate.security.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;


/**
 * @author azohdi
 *
 */
public class ActionResponsibleDaoTest extends BaseTransactionalTest
{

    /** id */
    /*public*/static ActionResponsiblePK ID;

    /** action id */
    /*public*/static Long ACTION_ID;

    /** user id */
    /*public*/static String USER_ID;

    @Autowired
    @Qualifier("actionResponsibleDao")
    //@Resource(name="actionResponsibleDao")
    private ActionResponsibleDao actionResponsibleDao;

    @Autowired
    @Qualifier("actionDao")
    //@Resource(name="actionDao")
    private ActionDao actionDao;

    @Autowired
    @Qualifier("auditDao")
    //@Resource(name="auditDao")
    private AuditDao auditDao;

    @Autowired
    @Qualifier("userDao")
    //@Resource(name="userDao")
    private UserDao userDao;

    @Before
    public void setUp() throws Exception
    {
        try
        {
            //
            Audit audit = auditDao.findByReference(AuditDaoTest.reference);
            Action action = actionDao
                .findByName(audit, ActionDaoTest.reference, ActionDaoTest.name);
            ACTION_ID = action.getActionId();

            User user = userDao.findByLogin("dsasmito").get(0);
            USER_ID = user.getUserId();

            ActionResponsible entity = ID == null ? null : actionResponsibleDao.findById(ID);
            if (entity == null)
            {
                entity = new ActionResponsible();
                entity.setAction(action);
                entity.setUser(user);
                actionResponsibleDao.save(entity);
            }
            ID = entity.getId();
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    @After
    public void tearDown() throws Exception
    {
        //issueDao.delete(entity);
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.ActionResponsibleDao#save(net.apollosoft.ats.audit.domain.hibernate.ActionResponsible)}.
     */
    @Test
    @Rollback(value = true)
    public final void testSaveActionResponsible()
    {
        try
        {
            ActionResponsible result = actionResponsibleDao.findById(ID);
            Assert.assertNotNull(result);
            actionResponsibleDao.save(result);
            ActionResponsible resultSaved = actionResponsibleDao.findById(result.getId());
            Assert.assertNotNull(resultSaved);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.ActionResponsibleDao#delete(net.apollosoft.ats.audit.domain.hibernate.ActionResponsible)}.
     */
    @Test
    public final void testDeleteActionResponsible()
    {
        // TODO
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.ActionResponsibleDao#find(java.lang.Long, java.lang.String)}.
     */
    @Test
    public final void testFind()
    {
        try
        {
            ActionResponsible result = actionResponsibleDao.find(ACTION_ID, USER_ID);
            Assert.assertNotNull(result);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#delete(net.apollosoft.ats.audit.domain.hibernate.Auditable)}.
     */
    @Test
    public final void testDeleteAuditableOfQ()
    {
        // TODO
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#unDelete(net.apollosoft.ats.audit.domain.hibernate.Auditable)}.
     */
    @Test
    public final void testUnDelete()
    {
        // TODO
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#findAll()}.
     */
    @Test
    public final void testFindAll()
    {
        //TODO
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#findById(java.io.Serializable)}.
     */
    @Test
    public final void testFindById()
    {
        try
        {
            ActionResponsible result = actionResponsibleDao.findById(ID);
            Assert.assertNotNull(result);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#save(java.lang.Object)}.
     */
    @Test
    public final void testSaveT()
    {
        // TODO
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#merge(java.lang.Object)}.
     */
    @Test
    public final void testMerge()
    {
        // TODO
    }

}
