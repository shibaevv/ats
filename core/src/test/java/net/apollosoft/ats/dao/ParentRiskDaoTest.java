package net.apollosoft.ats.dao;

import java.util.List;

import junit.framework.Assert;
import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.dao.ParentRiskDao;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ParentRisk;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ParentRiskCategory;
import net.apollosoft.ats.audit.domain.refdata.IParentRisk;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


public class ParentRiskDaoTest extends BaseTransactionalTest
{
    /** id */
    /*public*/static Long ID;

    @Autowired
    @Qualifier("parentRiskDao")
    //@Resource(name="parentRiskDao")
    private ParentRiskDao parentRiskDao;

    @Before
    public void setUp() throws Exception
    {
        try
        {
            ParentRisk entity = ID == null ? parentRiskDao.findAll().get(0) : parentRiskDao
                .findById(ID);
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

    @Test
    public final void testFindByCategory()
    {
        try
        {
            ParentRiskCategory category = new ParentRiskCategory();
            category.setParentRiskCategoryId(2L);
            List<ParentRisk> result = parentRiskDao.findByCategory(category);
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
            ParentRisk result = parentRiskDao.findById(ID);
            Assert.assertNotNull(result);
            parentRiskDao.delete(result);
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
            List<ParentRisk> result = parentRiskDao.findAll();
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
            IParentRisk result = parentRiskDao.findById(ID);
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
            ParentRisk result = parentRiskDao.findById(ID);
            Assert.assertNotNull(result);
            result.setName("DivisionDaoTest");
            parentRiskDao.save(result);
            //            Group resultSaved = groupDao.findById(result.getId());
            Assert.fail("could not save parentRisk");
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
