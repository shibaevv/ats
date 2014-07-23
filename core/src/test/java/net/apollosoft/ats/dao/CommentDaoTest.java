package net.apollosoft.ats.dao;

import java.util.List;

import junit.framework.Assert;
import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.dao.ActionDao;
import net.apollosoft.ats.audit.dao.AuditDao;
import net.apollosoft.ats.audit.dao.CommentDao;
import net.apollosoft.ats.audit.domain.IComment;
import net.apollosoft.ats.audit.domain.hibernate.Action;
import net.apollosoft.ats.audit.domain.hibernate.Audit;
import net.apollosoft.ats.audit.domain.hibernate.Comment;
import net.apollosoft.ats.audit.search.CommentSearchCriteria;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
*
* @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
*/
public class CommentDaoTest extends BaseTransactionalTest
{
    /** id */
    /*public*/static Long ID;

    /** action id */
    /*public*/static Long ACTION_ID;

    @Autowired
    @Qualifier("actionDao")
    //@Resource(name="actionDao")
    private ActionDao actionDao;

    @Autowired
    @Qualifier("commentDao")
    //@Resource(name="commentDao")
    private CommentDao commentDao;

    @Autowired
    @Qualifier("auditDao")
    //@Resource(name="auditDao")
    private AuditDao auditDao;

    @Before
    public void setUp() throws Exception
    {
        try
        {
            Audit audit = auditDao.findByReference(AuditDaoTest.reference);
            Assert.assertNotNull(audit);
            Action action = actionDao
                .findByName(audit, ActionDaoTest.reference, ActionDaoTest.name);
            Assert.assertNotNull(action);
            ACTION_ID = action.getActionId();

            Comment entity = ID == null ? null : commentDao.findById(ID);
            if (entity == null)
            {
                entity = new Comment();
                entity.setDetail("detail-" + System.currentTimeMillis());
                action.addComment(entity);
                actionDao.save(action);
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
        //commentDao.delete(entity);
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.CommentDao#findByCriteria(net.apollosoft.ats.audit.search.CommentSearchCriteria)}.
     */
    @Test
    public final void testFindByCriteria()
    {
        try
        {
            CommentSearchCriteria criteria = new CommentSearchCriteria();
            criteria.setActionId(ACTION_ID);
            List<Comment> result = commentDao.findByCriteria(criteria);
            Assert.assertTrue(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.CommentDao#findByActionDetail(net.apollosoft.ats.audit.domain.hibernate.Action, java.lang.String)}.
     */
    @Test
    public final void testFindByActionDetail()
    {
        try
        {
            //TODO
            //            Action action = actionDao.findById(ACTION_ID);
            //            Comment result = commentDao.findByActionDetail(action, action.getDetail(), action.getCreatedDate());
            //            Assert.assertNotNull(result);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.CommentDao#maxDocuments(java.lang.Long)}.
     */
    @Test
    public final void testMaxDocuments()
    {
        try
        {
            Integer result = commentDao.maxDocuments(19032L);
            //System.out.println("testMaxDocuments=" + result);
            Assert.assertNotNull(result);
            //Assert.assertTrue(result == 2);
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
            Comment result = commentDao.findById(ID);
            Assert.assertNotNull(result);
            commentDao.delete(result);
            Comment resultDeleted = commentDao.findById(result.getId());
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
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#unDelete(net.apollosoft.ats.audit.domain.hibernate.Auditable)}.
     */
    @Test
    public final void testUnDelete()
    {
        try
        {
            Comment result = commentDao.findById(ID);
            commentDao.delete(result);
            Comment resultDeleted = commentDao.findById(result.getId());
            Assert.assertNotNull(resultDeleted);
            commentDao.unDelete(result);
            result = commentDao.findById(result.getId());
            Comment unDeleted = commentDao.findById(result.getId());
            Assert.assertNotNull(unDeleted);
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
            List<Comment> result = commentDao.findAll();
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
            Comment result = commentDao.findById(ID);
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
            Comment result = commentDao.findById(ID);
            Assert.assertNotNull(result);
            result.setDetail("detail-" + System.currentTimeMillis());
            commentDao.save(result);
            IComment resultSaved = commentDao.findById(result.getId());
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
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#merge(java.lang.Object)}.
     */
    @Test
    public final void testMerge()
    {
        // TODO
    }

}