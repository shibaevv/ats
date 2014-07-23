package net.apollosoft.ats.dao;

import java.util.List;

import junit.framework.Assert;
import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.dao.AuditDao;
import net.apollosoft.ats.audit.domain.IAudit;
import net.apollosoft.ats.audit.domain.hibernate.Audit;
import net.apollosoft.ats.audit.search.ReportSearchCriteria;
import net.apollosoft.ats.util.DateUtils;
import net.apollosoft.ats.util.ThreadLocalUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class AuditDaoTest extends BaseTransactionalTest
{

    /** id */
    /*public*/static Long ID;

    /** reference */
    public static final String reference = "reference";

    @Autowired
    @Qualifier("auditDao")
    //@Resource(name="auditDao")
    private AuditDao auditDao;

    @Before
    public void setUp() throws Exception
    {
        try
        {
            Audit entity = ID == null ? auditDao.findByReference(reference) : auditDao.findById(ID);
            if (entity == null)
            {
                entity = new Audit();
                entity.setReference(reference);
                entity.setName("AuditDaoTest" + reference);
                entity.setDetail("detail-" + System.currentTimeMillis());
                entity.setIssuedDate(DateUtils.getCurrentDate());
                auditDao.save(entity);
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
        //auditDao.delete(entity);
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#delete(java.lang.Object)}.
     */
    @Test
    @Rollback(value = true)
    public final void testDelete()
    {
        try
        {
            Audit result = auditDao.findById(ID);
            Assert.assertNotNull(result);
            auditDao.delete(result);
            IAudit resultDeleted = auditDao.findById(result.getId());
            //need to be retrieved with different hb session (otherwise using first level cache)
            //Assert.assertNull(resultDeleted);
            Assert.assertNotNull(resultDeleted);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#findAll()}.
     */
    @Test
    public final void testFindAll()
    {
        try
        {
            List<Audit> result = auditDao.findAll();
            Assert.assertTrue("No record(s) found.", !result.isEmpty());
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
            IAudit result = auditDao.findById(ID);
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
    @Rollback(value = false)
    public final void testSave()
    {
        try
        {
            Audit result = auditDao.findById(ID);
            Assert.assertNotNull(result);
            result.setDetail("detail-" + System.currentTimeMillis());
            result.setIssuedDate(DateUtils.getCurrentDateTime());
            auditDao.save(result);
            IAudit resultSaved = auditDao.findById(result.getId());
            Assert.assertNotNull(resultSaved);
            Assert.assertEquals(result.getName(), resultSaved.getName());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.AuditDao#findByReference(java.lang.String)}.
     */
    @Test
    public final void testFindByReference()
    {
        try
        {
            IAudit result = auditDao.findById(ID);
            Assert.assertNotNull(result);
            IAudit result2 = auditDao.findByReference(reference);
            Assert.assertEquals(result, result2);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.AuditDao#findAuditReferences()}.
     */
    @Test
    public final void testFindAuditReferences()
    {
        try
        {
            List<String> result = auditDao.findAuditReferences();
            Assert.assertTrue("No record(s) found.", !result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.ActionDao#countActionOpen(java.lang.Long)}.
     */
    @Test
    public final void testCountActionOpen()
    {
        try
        {
            IAudit audit = auditDao.findById(ID);
            Assert.assertNotNull(audit);
            Integer result = auditDao.countActionTotal(audit.getId());
            Assert.assertTrue(result >= 0);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.ActionDao#countActionTotal(java.lang.Long)}.
     */
    @Test
    public final void testCountActionFollow()
    {
        try
        {
            IAudit audit = auditDao.findById(ID);
            Assert.assertNotNull(audit);
            Integer result = auditDao.countActionTotal(audit.getId());
            Assert.assertTrue(result >= 0);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.ActionDao#findExport(net.apollosoft.ats.audit.search.ActionSearchCriteria)}.
     */
    @Test
    public final void testFindExport()
    {
        try
        {
            ReportSearchCriteria criteria = new ReportSearchCriteria();
            criteria.setIssuedDateTo(ThreadLocalUtils.getDate());
            criteria.setIssuedDateFrom(DateUtils.addMonths(ThreadLocalUtils.getDate(), -3));
            List<Object[]> result = auditDao.findExport(criteria);
            Assert.assertTrue(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public final void testFindAuditByNameLike()
    {
        try
        {
            String nameLike = "";
            boolean published = true;
            List<Audit> result = auditDao.findAuditByNameLike(nameLike, published);
            Assert.assertTrue(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.AuditDao#findByCriteria(net.apollosoft.ats.audit.search.ReportSearchCriteria)}.
     */
    @Test
    public final void testFindByCriteria()
    {
        try
        {
            ReportSearchCriteria criteria = new ReportSearchCriteria();
            criteria.setSearchAuditName(AuditDaoTest.reference);
            List<Object[]> result = auditDao.findByCriteria(criteria);
            //Assert.assertTrue(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.AuditDao#countActionTotal(java.lang.Long)}.
     */
    @Test
    public final void testCountActionTotal()
    {
        try
        {
            int actionTotal = auditDao.countActionTotal(ID);
            Assert.assertNotNull(actionTotal);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.AuditDao#countActionUnpublished(java.lang.Long)}.
     */
    @Test
    public final void testCountActionUnpublished()
    {
        try
        {
            int actionUnpublished = auditDao.countActionUnpublished(ID);
            Assert.assertNotNull(actionUnpublished);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.AuditDao#countIssueUnpublished(java.lang.Long)}.
     */
    @Test
    public final void testCountIssueUnpublished()
    {
        try
        {
            int issueUnpublished = auditDao.countIssueUnpublished(ID);
            Assert.assertNotNull(issueUnpublished);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.AuditDao#findByUniqueKey(java.lang.Long, java.lang.Long, java.lang.Long)}.
     */
    @Test
    public final void testFindByUniqueKey()
    {
       // TODO
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#unDelete(net.apollosoft.ats.audit.domain.hibernate.Auditable)}.
     */
    @Test
    public final void testUnDelete()
    {
        try
        {
            Audit result = auditDao.findById(ID);
            auditDao.delete(result);
            Audit resultDeleted = auditDao.findById(result.getId());
            Assert.assertNotNull(resultDeleted);
            auditDao.unDelete(result);
            result = auditDao.findById(result.getId());
            Audit unDeleted = auditDao.findById(result.getId());
            Assert.assertNotNull(unDeleted);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
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