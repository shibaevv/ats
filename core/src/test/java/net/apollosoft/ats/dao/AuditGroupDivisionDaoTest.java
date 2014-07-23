package net.apollosoft.ats.dao;

import junit.framework.Assert;
import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.dao.AuditDao;
import net.apollosoft.ats.audit.dao.AuditGroupDivisionDao;
import net.apollosoft.ats.audit.dao.DivisionDao;
import net.apollosoft.ats.audit.dao.GroupDao;
import net.apollosoft.ats.audit.domain.hibernate.Audit;
import net.apollosoft.ats.audit.domain.hibernate.AuditGroupDivision;
import net.apollosoft.ats.domain.hibernate.security.Division;
import net.apollosoft.ats.domain.hibernate.security.Group;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * @author azohdi
 *
 */
public class AuditGroupDivisionDaoTest extends BaseTransactionalTest
{
    /** id */
    /*public*/static Long ID;

    @Autowired
    @Qualifier("auditGroupDivisionDao")
    //@Resource(name="auditGroupDivisionDao")
    private AuditGroupDivisionDao auditGroupDivisionDao;

    @Autowired
    @Qualifier("divisionDao")
    //@Resource(name="divisionDao")
    private DivisionDao divisionDao;

    @Autowired
    @Qualifier("groupDao")
    //@Resource(name="groupDao")
    private GroupDao groupDao;

    @Autowired
    @Qualifier("auditDao")
    //@Resource(name="auditDao")
    private AuditDao auditDao;

    @Before
    public void setUp() throws Exception
    {
        try
        {
            //
            Audit audit = auditDao.findByReference(AuditDaoTest.reference);
            Group group = groupDao.findAll().get(0);
            Division division = divisionDao.findAll().get(0);

            AuditGroupDivision entity = ID == null ? null : auditGroupDivisionDao.findById(ID);
            if (entity == null)
            {
                entity = new AuditGroupDivision();
                entity.setAudit(audit);
                entity.setGroup(group);
                entity.setDivision(division);
                auditGroupDivisionDao.save(entity);
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
     * Test method for {@link net.apollosoft.ats.audit.dao.AuditGroupDivisionDao#save(net.apollosoft.ats.audit.domain.hibernate.AuditGroupDivision)}.
     */
    @Test
    public final void testSaveAuditGroupDivision()
    {
        try
        {
            AuditGroupDivision result = auditGroupDivisionDao.findById(ID);
            Assert.assertNotNull(result);
            auditGroupDivisionDao.save(result);
            AuditGroupDivision resultSaved = auditGroupDivisionDao.findById(result.getId());
            Assert.assertNotNull(resultSaved);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.AuditGroupDivisionDao#delete(net.apollosoft.ats.audit.domain.hibernate.AuditGroupDivision)}.
     */
    @Test
    public final void testDeleteAuditGroupDivision()
    {
        //TODO: Physical Delete
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
        // TODO
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#findById(java.io.Serializable)}.
     */
    @Test
    public final void testFindById()
    {
        try
        {
            AuditGroupDivision result = auditGroupDivisionDao.findById(ID);
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
