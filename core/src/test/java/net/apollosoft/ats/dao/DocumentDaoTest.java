package net.apollosoft.ats.dao;

import java.io.InputStream;

import junit.framework.Assert;
import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.dao.DocumentDao;
import net.apollosoft.ats.domain.ContentTypeEnum;
import net.apollosoft.ats.domain.IDocument;
import net.apollosoft.ats.domain.hibernate.Document;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * @author azohdi
 *
 */
public class DocumentDaoTest extends BaseTransactionalTest
{
    /** id */
    /*public*/static Long ID;

    @Autowired
    @Qualifier("documentDao")
    //@Resource(name="documentDao")
    private DocumentDao documentDao;

    @Before
    public void setUp() throws Exception
    {
        try
        {
            InputStream input = getClass().getClassLoader().getResourceAsStream("template/R001.vm");

            Document entity = ID == null ? null : documentDao.findById(ID);
            if (entity == null)
            {
                entity = new Document();
                entity.setDetail("detail-" + System.currentTimeMillis());
                entity.setContent(IOUtils.toByteArray(input));
                entity.setContentType(ContentTypeEnum.TEXT_HTML.getContentType());
                entity.setName("DocumentDaoTest");
                documentDao.save(entity);
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
     * Test method for {@link net.apollosoft.ats.audit.dao.DocumentDao#save(net.apollosoft.ats.domain.hibernate.Document)}.
     */
    @Test
    public final void testSaveDocument()
    {
        try
        {
            Document result = documentDao.findById(ID);
            Assert.assertNotNull(result);
            result.setDetail("detail-" + System.currentTimeMillis());
            documentDao.save(result);
            IDocument resultSaved = documentDao.findById(result.getId());
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
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#delete(net.apollosoft.ats.audit.domain.hibernate.Auditable)}.
     */
    @Test
    public final void testDelete()
    {
        try
        {
            Document result = documentDao.findById(ID);
            Assert.assertNotNull(result);
            documentDao.delete(result);
            Document resultDeleted = documentDao.findById(result.getId());
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
            Document result = documentDao.findById(ID);
            documentDao.delete(result);
            Document resultDeleted = documentDao.findById(result.getId());
            Assert.assertNotNull(resultDeleted);
            documentDao.unDelete(result);
            result = documentDao.findById(result.getId());
            Document unDeleted = documentDao.findById(result.getId());
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
//        try
//        {
//            List<Document> result = documentDao.findAll();
//            Assert.assertTrue("No record(s) found.", !result.isEmpty());
//        }
//        catch (Exception e)
//        {
//            LOG.error(e.getMessage(), e);
//            Assert.fail(e.getMessage());
//        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#findById(java.io.Serializable)}.
     */
    @Test
    public final void testFindById()
    {
        try
        {
            Document result = documentDao.findById(ID);
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
