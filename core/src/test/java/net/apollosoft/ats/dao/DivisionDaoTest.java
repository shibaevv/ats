package net.apollosoft.ats.dao;

import java.util.List;

import junit.framework.Assert;
import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.dao.DivisionDao;
import net.apollosoft.ats.audit.dao.GroupDao;
import net.apollosoft.ats.domain.hibernate.security.Division;
import net.apollosoft.ats.domain.hibernate.security.Group;
import net.apollosoft.ats.domain.security.IDivision;
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
public class DivisionDaoTest extends BaseTransactionalTest
{

    /** id */
    /*public*/static Long ID;
    
    /** group id */
    /*public*/static Long GROUP_ID;

    @Autowired
    @Qualifier("divisionDao")
    //@Resource(name="divisionDao")
    private DivisionDao divisionDao;

    @Autowired
    @Qualifier("groupDao")
    //@Resource(name="groupDao")
    private GroupDao groupDao;

    @Before
    public void setUp() throws Exception
    {
        try
        {
            //
            Group group = GROUP_ID == null ? groupDao.findAll().get(0) : groupDao.findById(GROUP_ID);
            GROUP_ID = group.getId();
            //
            Division entity = ID == null ? divisionDao.findAll().get(0) : divisionDao.findById(ID);
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
     * Test method for {@link net.apollosoft.ats.audit.dao.DivisionDao#findByName(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testFindByNameStringString()
    {
        try
        {
            List<Division> result = divisionDao.findByName("Investment Solutions & Sales", "MFG");
            Assert.assertTrue(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.DivisionDao#findByGroup(net.apollosoft.ats.domain.hibernate.security.Group)}.
     */
    @Test
    public final void testFindByGroup()
    {
        try
        {
            Group group = groupDao.findById(GROUP_ID);
            List<Division> result = divisionDao.findByGroup(group);
            Assert.assertTrue(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.DivisionDao#findByName(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testFindByName()
    {
        try
        {
            List<Division> result = divisionDao.findByName(null, IGroup.GLOBAL_NAME);
            Assert.assertNotNull(result);
            //result = divisionDao.findByName(null, "?");
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
            Division result = divisionDao.findById(ID);
            Assert.assertNotNull(result);
            divisionDao.delete(result);
            //Division resultDeleted = divisionDao.findById(result.getId());
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
            List<Division> result = divisionDao.findAll();
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
            IDivision result = divisionDao.findById(ID);
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
            Division result = divisionDao.findById(ID);
            Assert.assertNotNull(result);
            result.setName("DivisionDaoTest");
            divisionDao.save(result);
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