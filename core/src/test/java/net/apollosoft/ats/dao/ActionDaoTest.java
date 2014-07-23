package net.apollosoft.ats.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;
import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.dao.ActionDao;
import net.apollosoft.ats.audit.dao.AuditDao;
import net.apollosoft.ats.audit.dao.DivisionDao;
import net.apollosoft.ats.audit.dao.GroupDao;
import net.apollosoft.ats.audit.dao.IssueDao;
import net.apollosoft.ats.audit.domain.hibernate.Action;
import net.apollosoft.ats.audit.domain.hibernate.ActionGroupDivision;
import net.apollosoft.ats.audit.domain.hibernate.Audit;
import net.apollosoft.ats.audit.domain.hibernate.Issue;
import net.apollosoft.ats.audit.search.ReportSearchCriteria;
import net.apollosoft.ats.domain.hibernate.security.Division;
import net.apollosoft.ats.domain.hibernate.security.Group;
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
public class ActionDaoTest extends BaseTransactionalTest
{

    /** id */
    /*public*/static Long ID;

    /** group id */
    /*public*/static Long GROUP_ID;

    /** division id */
    /*public*/static Long DIVISION_ID;

    /** issue id */
    /*public*/static Long ISSUE_ID;

    /** audit id */
    /*public*/static Long AUDIT_ID;

    /** reference */
    public static final String reference = "reference";

    /** name */
    public static final String name = "1.01 Action Name";

    @Autowired
    @Qualifier("auditDao")
    //@Resource(name="auditDao")
    private AuditDao auditDao;

    @Autowired
    @Qualifier("issueDao")
    //@Resource(name="issueDao")
    private IssueDao issueDao;

    @Autowired
    @Qualifier("actionDao")
    //@Resource(name="actionDao")
    private ActionDao actionDao;
    
    @Autowired
    @Qualifier("groupDao")
    //@Resource(name="groupDao")
    private GroupDao groupDao;
    
    @Autowired
    @Qualifier("divisionDao")
    //@Resource(name="divisionDao")
    private DivisionDao divisionDao;

    @Before
    public void setUp() throws Exception
    {
        try
        {
            //has dependency on IssueDaoTest
            Issue issue = issueDao.findByReference(IssueDaoTest.reference);
            Assert.assertNotNull(issue);
            //
            Group group = GROUP_ID == null ? groupDao.findAll().get(0) : groupDao
                .findById(GROUP_ID);
            GROUP_ID = group.getId();
            //
            Division division = DIVISION_ID == null ? divisionDao.findAll().get(0) : divisionDao
                .findById(DIVISION_ID);
            DIVISION_ID = division.getId();
            //
            Audit auditEntity = AUDIT_ID == null ? auditDao.findByReference(reference) : auditDao
                .findById(AUDIT_ID);
            AUDIT_ID = auditEntity.getId();
            //
            Issue issueEntity = ISSUE_ID == null ? issueDao.findByReference(reference) : issueDao
                .findById(ISSUE_ID);
            ISSUE_ID = issueEntity.getId();
            //
            Action entity = ID == null ? actionDao.findByName((Audit) issue.getAudit(), reference,
                name) : actionDao.findById(ID);
            if (entity == null)
            {
                entity = new Action();
                entity.setIssue(issue);
                entity.setListIndex(new BigDecimal("1.01"));
                entity.setReference(reference);
                entity.setName(name);
                entity.setDetail("detail-" + System.currentTimeMillis());
                issue.add(entity);
                issueDao.save(issue);
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
        //actionDao.delete(entity);
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
            Action result = actionDao.findById(ID);
            Assert.assertNotNull(result);
            actionDao.delete(result);
            Action resultDeleted = actionDao.findById(result.getId());
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
            List<Action> result = actionDao.findAll();
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
            Action result = actionDao.findById(ID);
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
            Action result = actionDao.findById(ID);
            Assert.assertNotNull(result);
            result.setDetail("detail-" + System.currentTimeMillis());
            actionDao.save(result);
            Action resultSaved = actionDao.findById(result.getId());
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
     * Test method for {@link net.apollosoft.ats.audit.dao.ActionDao#findByName(java.lang.String)}.
     */
    @Test
    public final void testFindByName()
    {
        try
        {
            //has dependency on IssueDaoTest
            Issue issue = issueDao.findByReference(IssueDaoTest.reference);
            Assert.assertNotNull(issue);
            Action result = actionDao.findById(ID);
            Assert.assertNotNull(result);
            Action result2 = actionDao.findByName((Audit) issue.getAudit(), reference, name);
            Assert.assertEquals(result, result2);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.ActionDao#findOpen(net.apollosoft.ats.audit.search.ActionSearchCriteria)}.
     */
    @Test
    public final void testFindOpen()
    {
        try
        {
            //Date dueDateTo = DateUtils.getEndOfMonth(ThreadLocalUtils.getDate());
            Date dueDateTo = DateUtils.getStartOfMonth(ThreadLocalUtils.getDate());
            dueDateTo = DateUtils.addMonths(dueDateTo, 1);
            List<Action> result = actionDao.findOpen(dueDateTo, null);
            Assert.assertTrue(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.ActionDao#findByCriteria(net.apollosoft.ats.audit.search.ReportSearchCriteria)}.
     */
    @Test
    public final void testFindByCriteria()
    {
        try
        {
            ReportSearchCriteria criteria = new ReportSearchCriteria();
            criteria.setSearchAuditName(AuditDaoTest.reference);
            List<Object[]> result = actionDao.findByCriteria(criteria);
            //Assert.assertTrue(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.ActionDao#findByIssueId(java.lang.Long, net.apollosoft.ats.audit.search.Pagination, java.lang.Boolean)}.
     */
    @Test
    public final void testFindByIssueId()
    {
        try
        {
            List<Action> result = actionDao.findByIssueId(ISSUE_ID, null, null);
            Assert.assertNotNull(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.ActionDao#findByAuditId(java.lang.Long, net.apollosoft.ats.audit.search.Pagination, java.lang.Boolean)}.
     */
    @Test
    public final void testFindByAuditId()
    {
        try
        {
            List<Action> result = actionDao.findByAuditId(AUDIT_ID, null, null);
            Assert.assertNotNull(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.ActionDao#findByUniqueKey(java.lang.Long, java.lang.Long, java.lang.Long)}.
     */
    @Test
    public final void testFindByUniqueKey()
    {
        try
        {
            ActionGroupDivision actionGroupDivision = actionDao.findByUniqueKey(ID, GROUP_ID, DIVISION_ID);
            Assert.assertNotNull(actionGroupDivision);
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
        try
        {
            Action result = actionDao.findById(ID);
            actionDao.delete(result);
            Action resultDeleted = actionDao.findById(result.getId());
            Assert.assertNotNull(resultDeleted);
            actionDao.unDelete(result);
            result = actionDao.findById(result.getId());
            Action unDeleted = actionDao.findById(result.getId());
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
        try
        {
            // TODO: AZ
            Action entity = actionDao.findById(ID);
            actionDao.merge(entity);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

}