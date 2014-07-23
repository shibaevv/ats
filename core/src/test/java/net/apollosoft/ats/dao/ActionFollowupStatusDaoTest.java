package net.apollosoft.ats.dao;

import java.util.List;

import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.dao.ActionFollowupStatusDao;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ActionFollowupStatus;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * @author azohdi
 *
 */
public class ActionFollowupStatusDaoTest extends BaseTransactionalTest
{
    /** id */
    /*public*/static Long ID;

    @Autowired
    @Qualifier("actionFollowupStatusDao")
    //@Resource(name="actionFollowupStatusDao")
    private ActionFollowupStatusDao actionFollowupStatusDao;

    @Before
    public void setUp() throws Exception
    {
        try
        {
            ActionFollowupStatus entity = ID == null ? actionFollowupStatusDao.findAll().get(0)
                : actionFollowupStatusDao.findById(ID);
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
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#delete(net.apollosoft.ats.audit.domain.hibernate.Auditable)}.
     */
    @Test
    public final void testDelete()
    {
        try
        {
            ActionFollowupStatus result = actionFollowupStatusDao.findById(ID);
            Assert.assertNotNull(result);
            actionFollowupStatusDao.delete(result);
            Assert.fail("could not be deleted");
        }
        catch (Exception e)
        {
            // success
            LOG.info(e.getMessage(), e);
        }
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
        try
        {
            List<ActionFollowupStatus> result = actionFollowupStatusDao.findAll();
            Assert.assertTrue(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#findById(java.io.Serializable)}.
     */
    @Test
    public final void testFindById()
    {
        try
        {
            ActionFollowupStatus result = actionFollowupStatusDao.findById(ID);
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
    public final void testSave()
    {
        try
        {
            ActionFollowupStatus result = actionFollowupStatusDao.findById(ID);
            Assert.assertNotNull(result);
            result.setName("ActionFollowupStatusDaoTest");
            actionFollowupStatusDao.save(result);
            Assert.fail("could not save group");
        }
        catch (Exception e)
        {
            //success
            LOG.info(e.getMessage(), e);
        }
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
