package net.apollosoft.ats.dao;

import java.util.List;

import junit.framework.Assert;
import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.dao.AuditDao;
import net.apollosoft.ats.audit.dao.IssueDao;
import net.apollosoft.ats.audit.domain.IAudit;
import net.apollosoft.ats.audit.domain.hibernate.Audit;
import net.apollosoft.ats.audit.domain.hibernate.Issue;
import net.apollosoft.ats.audit.domain.hibernate.refdata.Rating;
import net.apollosoft.ats.audit.domain.refdata.IRating;
import net.apollosoft.ats.audit.search.IssueSearchCriteria;

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
public class IssueDaoTest extends BaseTransactionalTest
{

    /** id */
    /*public*/static Long ID;

    /** reference */
    public static final String reference = "reference";

    @Autowired
    @Qualifier("auditDao")
    //@Resource(name="auditDao")
    private AuditDao auditDao;

    @Autowired
    @Qualifier("issueDao")
    //@Resource(name="issueDao")
    private IssueDao issueDao;

    @Before
    public void setUp() throws Exception
    {
        try
        {
            //has dependency on AuditDaoTest
            Audit audit = auditDao.findByReference(AuditDaoTest.reference);
            Assert.assertNotNull(audit);
            Issue entity = ID == null ? issueDao.findByReference(reference) : issueDao.findById(ID);
            if (entity == null)
            {
                entity = new Issue();
                entity.setReference("reference");
                entity.setName("IssueDaoTest");
                entity.setDetail("detail-" + System.currentTimeMillis());
                entity.setRating(new Rating(new Long(IRating.RatingEnum.MEDIUM.ordinal())));
                audit.add(entity);
                auditDao.save(audit);
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
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#delete(java.lang.Object)}.
     */
    @Test
    @Rollback(value = true)
    public final void testDelete()
    {
        try
        {
            Issue result = issueDao.findById(ID);
            Assert.assertNotNull(result);
            issueDao.delete(result);
            Issue resultDeleted = issueDao.findById(result.getId());
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
            List<Issue> result = issueDao.findAll();
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
            Issue result = issueDao.findById(ID);
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
            Issue result = issueDao.findById(ID);
            Assert.assertNotNull(result);
            result.setDetail("detail-" + System.currentTimeMillis());
            issueDao.save(result);
            Issue resultSaved = issueDao.findById(result.getId());
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
     * Test method for {@link net.apollosoft.ats.audit.dao.IssueDao#findByReferenceName(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testFindByReferenceName()
    {
        try
        {
            Issue result = issueDao.findById(ID);
            Assert.assertNotNull(result);
            Issue result2 = issueDao.findByReference(reference);
            Assert.assertEquals(result, result2);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.IssueDao#findByCriteria(net.apollosoft.ats.audit.search.IssueSearchCriteria)}.
     */
    @Test
    public final void testFindByCriteria()
    {
        try
        {
            IssueSearchCriteria criteria = new IssueSearchCriteria();
            //criteria.setAuditName("MSG Delta 1 / Derivatives South Africa October 2009");
            IAudit audit = issueDao.findById(ID).getAudit();
            criteria.setAuditId(audit.getAuditId());
            List<Issue> result = issueDao.findByCriteria(criteria);
            Assert.assertTrue(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.IssueDao#findByReference(java.lang.String)}.
     */
    @Test
    public final void testFindByReference()
    {
        try
        {
            Issue result = issueDao.findByReference(reference);
            Assert.assertNotNull(result);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.IssueDao#countActionOpen(java.lang.Long)}.
     */
    @Test
    public final void testCountActionOpen()
    {
        try
        {
            int actionOpen = issueDao.countActionOpen(ID);
            Assert.assertNotNull(actionOpen);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.IssueDao#countActionTotal(java.lang.Long)}.
     */
    @Test
    public final void testCountActionTotal()
    {
        try
        {
            int actionTotal = issueDao.countActionTotal(ID);
            Assert.assertNotNull(actionTotal);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
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
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#merge(java.lang.Object)}.
     */
    @Test
    public final void testMerge()
    {
        // TODO
    }

}