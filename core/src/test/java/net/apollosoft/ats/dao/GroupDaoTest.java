package net.apollosoft.ats.dao;

import java.util.List;

import junit.framework.Assert;
import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.dao.GroupDao;
import net.apollosoft.ats.domain.hibernate.security.Group;
import net.apollosoft.ats.domain.security.IGroup;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class GroupDaoTest extends BaseTransactionalTest
{
    /** id */
    /*public*/static Long ID;

    @Autowired
    @Qualifier("groupDao")
    //@Resource(name="groupDao")
    private GroupDao groupDao;

    @Before
    public void setUp() throws Exception
    {
        try
        {
            Group entity = ID == null ? groupDao.findAll().get(0) : groupDao.findById(ID);
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
     * Test method for {@link net.apollosoft.ats.audit.dao.GroupDao#findAll()}.
     */
    @Test
    public final void testFindAll()
    {
        try
        {
            List<Group> result = groupDao.findAll();
            Assert.assertTrue(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.GroupDao#findByName()}.
     */
    @Test
    public final void testFindByName()
    {
        try
        {
            Group result = groupDao.findByName(IGroup.GLOBAL_NAME);
            Assert.assertNotNull(result);
            //result = groupDao.findByName("?");
            //Assert.assertNotNull(result);
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
    public final void testDelete()
    {
        try
        {
            Group result = groupDao.findById(ID);
            Assert.assertNotNull(result);
            groupDao.delete(result);
            //            Group resultDeleted = groupDao.findById(result.getId());
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
        //TODO
        //        try
        //        {
        //            Group result = groupDao.findById(ID);
        //            groupDao.delete(result);
        //            Group resultDeleted = groupDao.findById(result.getId());
        //            Assert.assertNotNull(resultDeleted);
        //            groupDao.unDelete(result);
        //            result = groupDao.findById(result.getId());
        //            Group unDeleted = groupDao.findById(result.getId());
        //            Assert.assertNotNull(unDeleted);
        //        }
        //        catch (Exception e)
        //        {
        //            LOG.error(e.getMessage(), e);
        //            Assert.fail(e.getMessage());
        //        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#findById(java.io.Serializable)}.
     */
    @Test
    public final void testFindById()
    {
        try
        {
            IGroup result = groupDao.findById(ID);
            Assert.assertNotNull(result);
            Assert.assertEquals(ID, result.getId());
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
            Group result = groupDao.findById(ID);
            Assert.assertNotNull(result);
            result.setName("GroupDaoTest");
            groupDao.save(result);
            //            Group resultSaved = groupDao.findById(result.getId());
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