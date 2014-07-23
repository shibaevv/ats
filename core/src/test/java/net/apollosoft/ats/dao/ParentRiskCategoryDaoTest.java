package net.apollosoft.ats.dao;

import java.util.List;

import junit.framework.Assert;
import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.dao.ParentRiskCategoryDao;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ParentRiskCategory;
import net.apollosoft.ats.audit.domain.refdata.IParentRiskCategory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


public class ParentRiskCategoryDaoTest extends BaseTransactionalTest
{
    /** id */
    /*public*/static Long ID;

    @Autowired
    @Qualifier("parentRiskCategoryDao")
    //@Resource(name="parentRiskCategoryDao")
    private ParentRiskCategoryDao parentRiskCategoryDao;

    @Before
    public void setUp() throws Exception
    {
        try
        {
            ParentRiskCategory entity = ID == null ? parentRiskCategoryDao.findAll().get(0)
                : parentRiskCategoryDao.findById(ID);
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
     * Test method for {@link net.apollosoft.ats.audit.dao.GroupDao#findGroupLike(java.lang.String)}.
     */
    @Test
    public final void testFindParentRiskCategoryLike()
    {
        try
        {
            List<ParentRiskCategory> result = parentRiskCategoryDao.findAll();
            Assert.assertTrue(!result.isEmpty());
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
            ParentRiskCategory result = parentRiskCategoryDao.findById(ID);
            Assert.assertNotNull(result);
            parentRiskCategoryDao.delete(result);
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
            List<ParentRiskCategory> result = parentRiskCategoryDao.findAll();
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
            IParentRiskCategory result = parentRiskCategoryDao.findById(ID);
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
            ParentRiskCategory result = parentRiskCategoryDao.findById(ID);
            Assert.assertNotNull(result);
            result.setName("DivisionDaoTest");
            parentRiskCategoryDao.save(result);
            //            Group resultSaved = groupDao.findById(result.getId());
            Assert.fail("could not save parentRiskCategory");
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