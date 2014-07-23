package net.apollosoft.ats.dao;

import java.util.List;

import junit.framework.Assert;
import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.dao.ReportTypeDao;
import net.apollosoft.ats.audit.dao.UserDao;
import net.apollosoft.ats.audit.dao.UserMatrixDao;
import net.apollosoft.ats.audit.dao.security.RoleDao;
import net.apollosoft.ats.domain.dto.security.UserMatrixDto;
import net.apollosoft.ats.domain.hibernate.refdata.ReportType;
import net.apollosoft.ats.domain.hibernate.security.Role;
import net.apollosoft.ats.domain.hibernate.security.User;
import net.apollosoft.ats.domain.hibernate.security.UserMatrix;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * @author azohdi
 *
 */
public class UserMatrixDaoTest extends BaseTransactionalTest
{
    /** id */
    /*public*/static Long ID;

    /** user id */
    /*public*/static String USER_ID;

    /** role id */
    /*public*/static Long ROLE_ID;

    /** reference */
    public static final String reference = "reference";

    @Autowired
    @Qualifier("userDao")
    //@Resource(name="userDao")
    private UserDao userDao;

    @Autowired
    @Qualifier("roleDao")
    //@Resource(name="roleDao")
    private RoleDao roleDao;

    @Autowired
    @Qualifier("reportTypeDao")
    //@Resource(name="reportTypeDao")
    private ReportTypeDao reportTypeDao;

    @Autowired
    @Qualifier("userMatrixDao")
    //@Resource(name="userMatrixDao")
    private UserMatrixDao userMatrixDao;

    @Before
    public void setUp() throws Exception
    {
        try
        {
            User user = userDao.findById("36094");
            Assert.assertNotNull(user);
            USER_ID = user.getUserId();

            Role role = roleDao.findAll().get(0);
            Assert.assertNotNull(role);
            ROLE_ID = role.getId();

            ReportType reportType = reportTypeDao.findAll().get(0);
            Assert.assertNotNull(reportType);

            UserMatrix entity = ID == null ? null : userMatrixDao.findById(ID);
            if (entity == null)
            {
                entity = new UserMatrix();
                entity.setUser(user);
                entity.setRole(role);
                entity.setReportType(reportType);
                userMatrixDao.save(entity);
            }
            ID = entity.getUserMatrixId();
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
     * Test method for {@link net.apollosoft.ats.audit.dao.UserMatrixDao#findByUniqueKey(java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)}.
     */
    @Test
    public final void testFindByUniqueKey()
    {
        try
        {
            UserMatrix entity = userMatrixDao.findById(ID);
            entity = userMatrixDao.findByUniqueKey(new UserMatrixDto(entity));
            Assert.assertNotNull(entity);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.UserMatrixDao#findByUserId(java.lang.String)}.
     */
    @Test
    public final void testFindByUserId()
    {
        try
        {
            List<UserMatrix> userMatrix = userMatrixDao.findByUserId(USER_ID);
            Assert.assertTrue(!userMatrix.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.UserMatrixDao#findByRoleId(java.lang.Long)}.
     */
    @Test
    public final void testFindByRoleId()
    {
        try
        {
            List<UserMatrix> userMatrix = userMatrixDao.findByRoleId(ROLE_ID);
            Assert.assertTrue(!userMatrix.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.UserMatrixDao#findRoles(java.lang.String)}.
     */
    @Test
    public final void testFindRoles()
    {
        try
        {
            List<Role> roles = userMatrixDao.findRoles(USER_ID);
            Assert.assertTrue(!roles.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.UserMatrixDao#findReportTypes(java.lang.String)}.
     */
    @Test
    public final void testFindReportTypes()
    {
        try
        {
            List<ReportType> reportTypes = userMatrixDao.findReportTypes(USER_ID);
            Assert.assertTrue(!reportTypes.isEmpty());
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
            UserMatrix result = userMatrixDao.findById(ID);
            Assert.assertNotNull(result);
            userMatrixDao.delete(result);
            UserMatrix resultDeleted = userMatrixDao.findById(result.getId());
            Assert.assertNull(resultDeleted);
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
        //TODO
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#findAll()}.
     */
    @Test
    public final void testFindAll()
    {
        try
        {
            List<UserMatrix> result = userMatrixDao.findAll();
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
            UserMatrix result = userMatrixDao.findById(ID);
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
            UserMatrix result = userMatrixDao.findById(ID);
            Assert.assertNotNull(result);
            userMatrixDao.save(result);
            UserMatrix resultSaved = userMatrixDao.findById(result.getId());
            Assert.assertNotNull(resultSaved);
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
