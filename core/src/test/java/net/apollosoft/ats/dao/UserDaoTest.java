package net.apollosoft.ats.dao;

import java.util.List;

import junit.framework.Assert;
import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.dao.DivisionDao;
import net.apollosoft.ats.audit.dao.GroupDao;
import net.apollosoft.ats.audit.dao.UserDao;
import net.apollosoft.ats.audit.search.UserSearchCriteria;
import net.apollosoft.ats.domain.hibernate.security.Division;
import net.apollosoft.ats.domain.hibernate.security.Group;
import net.apollosoft.ats.domain.hibernate.security.User;
import net.apollosoft.ats.domain.refdata.IUserType.UserTypeEnum;
import net.apollosoft.ats.domain.security.IGroup;

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
public class UserDaoTest extends BaseTransactionalTest
{
    /** id */
    /*public*/static String ID;

    @Autowired
    @Qualifier("userDao")
    //@Resource(name="userDao")
    private UserDao userDao;

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
            //
            User entity = ID == null ? userDao.findByLogin("dsasmito").get(0) : userDao
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

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.UserDao#findByLogin(java.lang.String)}.
     */
    @Test
    public final void testFindByLogin()
    {
        try
        {
            List<User> users = userDao.findByLogin("dsasmito");
            Assert.assertTrue(!users.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.UserDao#findByNames(java.lang.String, java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testFindByNames()
    {
        try
        {
            List<User> users = userDao.findByNames("dion", "sasmito", null);
            Assert.assertTrue(!users.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.UserDao#findNameLike(java.lang.String, java.lang.String)}.
     */
    @Test
    public final void testFindNameLike()
    {
        try
        {
            List<User> result = userDao.findNameLike("di", "sa", null);
            Assert.assertTrue(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.UserDao#findByCriteria(net.apollosoft.ats.audit.search.UserSearchCriteria)}.
     */
    @Test
    public final void testFindByCriteria()
    {
        try
        {
            UserSearchCriteria criteria = new UserSearchCriteria();
            //criteria.setAuditName("MSG Delta 1 / Derivatives South Africa October 2009");
            List<User> result = userDao.findByCriteria(criteria);
            Assert.assertTrue(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.UserDao#findByGroupDivision(net.apollosoft.ats.domain.security.IGroup, net.apollosoft.ats.audit.domain.security.IDivision, java.lang.Long)}.
     */
    @Test
    public final void testFindByGroupDivision()
    {
        try
        {
            Group group = groupDao.findByName(IGroup.GLOBAL_NAME);
            Division division = null;
            List<User> result = userDao.findByGroupDivision(group, division, new Long(
                UserTypeEnum.OWNER.ordinal()));
            Assert.assertTrue(result.isEmpty());

            group = groupDao.findById(10L);
            division = divisionDao.findById(88L);
            result = userDao.findByGroupDivision(group, division, new Long(UserTypeEnum.OWNER
                .ordinal()));

            group = groupDao.findById(2L);
            division = divisionDao.findById(122L);
            result = userDao.findByGroupDivision(group, division, new Long(UserTypeEnum.OWNER
                .ordinal()));

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
            User result = userDao.findById(ID);
            Assert.assertNotNull(result);
            userDao.delete(result);
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
            //TODO
            //getting out of memory error
            //            List<User> result = userDao.findAll();
            //            Assert.assertTrue("No record(s) found.", !result.isEmpty());
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
            User result = userDao.findById(ID);
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
            User result = userDao.findById(ID);
            Assert.assertNotNull(result);
            result.setFirstName("DivisionDaoTest");
            userDao.save(result);
            Assert.fail("could not save user");
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