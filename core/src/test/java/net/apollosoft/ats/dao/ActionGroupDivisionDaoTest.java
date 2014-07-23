package net.apollosoft.ats.dao;

import junit.framework.Assert;
import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.dao.ActionDao;
import net.apollosoft.ats.audit.dao.ActionGroupDivisionDao;
import net.apollosoft.ats.audit.dao.AuditDao;
import net.apollosoft.ats.audit.dao.DivisionDao;
import net.apollosoft.ats.audit.dao.GroupDao;
import net.apollosoft.ats.audit.domain.hibernate.Action;
import net.apollosoft.ats.audit.domain.hibernate.ActionGroupDivision;
import net.apollosoft.ats.audit.domain.hibernate.Audit;
import net.apollosoft.ats.domain.hibernate.security.Division;
import net.apollosoft.ats.domain.hibernate.security.Group;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;


/**
 * @author azohdi
 *
 */
public class ActionGroupDivisionDaoTest extends BaseTransactionalTest
{
    /** id */
    /*public*/static Long ID;

    @Autowired
    @Qualifier("actionGroupDivisionDao")
    //@Resource(name="actionGroupDivisionDao")
    private ActionGroupDivisionDao actionGroupDivisionDao;

    @Autowired
    @Qualifier("divisionDao")
    //@Resource(name="divisionDao")
    private DivisionDao divisionDao;

    @Autowired
    @Qualifier("groupDao")
    //@Resource(name="groupDao")
    private GroupDao groupDao;

    @Autowired
    @Qualifier("actionDao")
    //@Resource(name="actionDao")
    private ActionDao actionDao;
    
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
            Action action = actionDao.findByName(audit, ActionDaoTest.reference, ActionDaoTest.name);
            Group group = groupDao.findAll().get(0);
            Division division = divisionDao.findAll().get(0);
            
            ActionGroupDivision entity = ID == null ? null : actionGroupDivisionDao.findById(ID);
            if (entity == null)
            {
                entity = new ActionGroupDivision();
                entity.setAction(action);
                entity.setGroup(group);
                entity.setDivision(division);
                actionGroupDivisionDao.save(entity);
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
     * Test method for {@link net.apollosoft.ats.audit.dao.ActionGroupDivisionDao#save(net.apollosoft.ats.audit.domain.hibernate.ActionGroupDivision)}.
     */
    @Test
    @Rollback(value = false)
    public final void testSaveActionGroupDivision()
    {
        try
        {
            ActionGroupDivision result = actionGroupDivisionDao.findById(ID);
            Assert.assertNotNull(result);
            actionGroupDivisionDao.save(result);
            ActionGroupDivision resultSaved = actionGroupDivisionDao.findById(result.getId());
            Assert.assertNotNull(resultSaved);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.ActionGroupDivisionDao#delete(net.apollosoft.ats.audit.domain.hibernate.ActionGroupDivision)}.
     */
    @Test
    @Rollback(value = true)
    public final void testDeleteActionGroupDivision()
    {
        //TODO
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#delete(net.apollosoft.ats.audit.domain.hibernate.Auditable)}.
     */
    @Test
    public final void testDeleteAuditableOfQ()
    {
        //TODO
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#unDelete(net.apollosoft.ats.audit.domain.hibernate.Auditable)}.
     */
    @Test
    public final void testUnDelete()
    {
       //TODO
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
            ActionGroupDivision result = actionGroupDivisionDao.findById(ID);
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
