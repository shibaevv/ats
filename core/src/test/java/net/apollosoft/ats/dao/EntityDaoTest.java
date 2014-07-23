package net.apollosoft.ats.dao;

import java.util.List;

import junit.framework.Assert;
import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.dao.EntityDao;
import net.apollosoft.ats.audit.dao.ParentRiskDao;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ActionFollowupStatus;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ActionStatus;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ParentRisk;
import net.apollosoft.ats.audit.domain.hibernate.refdata.Rating;
import net.apollosoft.ats.audit.domain.refdata.IBusinessStatus;
import net.apollosoft.ats.audit.domain.refdata.IParentRisk;
import net.apollosoft.ats.audit.domain.refdata.IRating;
import net.apollosoft.ats.domain.hibernate.refdata.ReportType;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class EntityDaoTest extends BaseTransactionalTest
{

    /** RATING_NAME */
    /*public*/static final String RATING_NAME = "High";

    /** ACTION_STATUS_NAME */
    /*public*/static final String ACTION_STATUS_NAME = "Open";

    /** ACTION_FOLLOWUP_STATUS_NAME */
    /*public*/static final String ACTION_FOLLOWUP_STATUS = "Open";

    /** BUSINESS_STATUS_NAME */
    /*public*/static final String BUSINESS_STATUS_NAME = "Implemented";

    @Autowired
    @Qualifier("entityDao")
    //@Resource(name="entityDao")
    private EntityDao entityDao;

    @Autowired
    @Qualifier("parentRiskDao")
    //@Resource(name="parentRiskDao")
    private ParentRiskDao parentRiskDao;

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.EntityDao#findRatingByName(java.lang.String)}.
     */
    @Test
    public final void testFindRatingByName()
    {
        try
        {
            IRating result = entityDao.findRatingByName(RATING_NAME);
            Assert.assertNotNull(result);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.EntityDao#findBusinessStatusByName(java.lang.String)}.
     */
    @Test
    public final void testFindBusinessStatusByName()
    {
        try
        {
            IBusinessStatus result = entityDao.findBusinessStatusByName(BUSINESS_STATUS_NAME);
            Assert.assertNotNull(result);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.EntityDao#findActionStatusByName(java.lang.String)}.
     */
    @Test
    public final void testFindActionStatusByName()
    {
        try
        {
            ActionStatus result = entityDao.findActionStatusByName(ACTION_STATUS_NAME);
            Assert.assertNotNull(result);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.EntityDao#findActionFollowupStatusByName(java.lang.String)}.
     */
    @Test
    public final void testFindActionFollowupStatusByName()
    {
        try
        {
            ActionFollowupStatus result = entityDao
                .findActionFollowupStatusByName(ACTION_FOLLOWUP_STATUS);
            Assert.assertNotNull(result);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.EntityDao#findAllActionStatus()}.
     */
    @Test
    public final void testFindAllActionStatus()
    {
        try
        {
            List<ActionStatus> result = entityDao.findAllActionStatus();
            Assert.assertTrue(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.EntityDao#findAllFollowupStatus()}.
     */
    @Test
    public final void testFindAllFollowupStatus()
    {
        try
        {
            List<ActionFollowupStatus> result = entityDao.findAllFollowupStatus();
            Assert.assertTrue(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.EntityDao#findAllRatings()}.
     */
    @Test
    public final void testFindAllRatings()
    {
        try
        {
            List<Rating> result = entityDao.findAllRatings();
            Assert.assertTrue(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.EntityDao#findAllReportTypes()}.
     */
    @Test
    public final void testFindAllReportTypes()
    {
        try
        {
            List<ReportType> result = entityDao.findAllReportTypes();
            Assert.assertTrue(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.EntityDao#findRiskByNameCategory(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testFindRiskByNameCategory()
    {
        try
        {
            ParentRisk entity = parentRiskDao.findAll().get(0);
            IParentRisk result = entityDao.findRiskByNameCategory(entity.getName(), entity
                .getCategory().getName());
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
    public final void testDelete()
    {
        //TODO
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
        // TODO
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#save(java.lang.Object)}.
     */
    @Test
    public final void testSave()
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