package net.apollosoft.ats.dao;

import java.io.InputStream;
import java.util.List;

import junit.framework.Assert;
import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.dao.TemplateDao;
import net.apollosoft.ats.audit.domain.ITemplate;
import net.apollosoft.ats.domain.ContentTypeEnum;
import net.apollosoft.ats.domain.hibernate.Template;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class TemplateDaoTest extends BaseTransactionalTest
{

    @Autowired
    @Qualifier("templateDao")
    //@Resource(name="templateDao")
    private TemplateDao templateDao;

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.TemplateDao#save(net.apollosoft.ats.domain.hibernate.Template)}.
     */
    @Test
    @Rollback(value = false)
    public final void testSaveTemplateR001()
    {
        InputStream input = null;
        try
        {
            input = getClass().getClassLoader().getResourceAsStream("template/R001.vm");
            Assert.assertNotNull(input);

            Template result = templateDao.findByReference(ITemplate.R001);
            if (result == null)
            {
                result = new Template();
                result.setReference(ITemplate.R001);
            }
            result.setName("Email notification to person responsible when report/action published");
            result.setDetail("Actions assigned to you in Actions"); // used as email subject
            result.setContent(IOUtils.toByteArray(input));
            result.setContentType(ContentTypeEnum.TEXT_HTML.getContentType());
            templateDao.save(result);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
        finally
        {
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.TemplateDao#save(net.apollosoft.ats.domain.hibernate.Template)}.
     */
    @Test
    @Rollback(value = false)
    public final void testSaveTemplateR002()
    {
        InputStream input = null;
        try
        {
            input = getClass().getClassLoader().getResourceAsStream("template/R002.vm");
            Assert.assertNotNull(input);

            Template result = templateDao.findByReference(ITemplate.R002);
            if (result == null)
            {
                result = new Template();
                result.setReference(ITemplate.R002);
            }
            result.setName("Email notification to oversight team when report/action published");
            result.setDetail("Actions assigned to you in Actions"); // used as email subject
            result.setContent(IOUtils.toByteArray(input));
            result.setContentType(ContentTypeEnum.TEXT_HTML.getContentType());
            templateDao.save(result);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
        finally
        {
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.TemplateDao#save(net.apollosoft.ats.domain.hibernate.Template)}.
     */
    @Test
    @Rollback(value = false)
    public final void testSaveTemplateR003()
    {
        InputStream input = null;
        try
        {
            input = getClass().getClassLoader().getResourceAsStream("template/R003.vm");
            Assert.assertNotNull(input);

            Template result = templateDao.findByReference(ITemplate.R003);
            if (result == null)
            {
                result = new Template();
                result.setReference(ITemplate.R003);
            }
            result.setName("Email to users with Oversight Team role day 1 and day 15 each month");
            result.setDetail("Update - actions assigned to your team in Actions"); // used as email subject
            result.setContent(IOUtils.toByteArray(input));
            result.setContentType(ContentTypeEnum.TEXT_HTML.getContentType());
            templateDao.save(result);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
        finally
        {
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.TemplateDao#save(net.apollosoft.ats.domain.hibernate.Template)}.
     */
    @Test
    @Rollback(value = false)
    public final void testSaveTemplateR004()
    {
        InputStream input = null;
        try
        {
            input = getClass().getClassLoader().getResourceAsStream("template/R004.vm");
            Assert.assertNotNull(input);

            Template result = templateDao.findByReference(ITemplate.R004);
            if (result == null)
            {
                result = new Template();
                result.setReference(ITemplate.R004);
            }
            result.setName("Email to person responsible on day 1 each month");
            result.setDetail("Monthly update - actions assigned to you in Actions"); // used as email subject
            result.setContent(IOUtils.toByteArray(input));
            result.setContentType(ContentTypeEnum.TEXT_HTML.getContentType());
            templateDao.save(result);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
        finally
        {
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.TemplateDao#save(net.apollosoft.ats.domain.hibernate.Template)}.
     */
    @Test
    @Rollback(value = false)
    public final void testSaveTemplateA()
    {
        saveTemplateA(ITemplate.A001, "Action published");
        saveTemplateA(ITemplate.A002, "Action title change");
        saveTemplateA(ITemplate.A003, "Action responsible person added");
        saveTemplateA(ITemplate.A004, "Action responsible person removed");
        saveTemplateA(ITemplate.A005, "Action group / division added");
        saveTemplateA(ITemplate.A006, "Action group / division removed");
        saveTemplateA(ITemplate.A007, "Action due date change");
        saveTemplateA(ITemplate.A008, "Action business status change");
        saveTemplateA(ITemplate.A009, "Action follow up status change");
        saveTemplateA(ITemplate.A010, "Action description change");
    }

    public final void saveTemplateA(String ref, String name)
    {
        InputStream input = null;
        try
        {
            input = getClass().getClassLoader().getResourceAsStream("template/" + ref + ".vm");
            Assert.assertNotNull(input);

            Template result = templateDao.findByReference(ref);
            if (result == null)
            {
                result = new Template();
                result.setReference(ref);
            }
            result.setName(name);
            result.setDetail(name);
            result.setContent(IOUtils.toByteArray(input));
            result.setContentType(ContentTypeEnum.TEXT_PLAIN.getContentType());
            templateDao.save(result);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
        finally
        {
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.TemplateDao#save(net.apollosoft.ats.domain.hibernate.Template)}.
     */
    @Test
    @Rollback(value = false)
    public final void testSaveTemplateL()
    {
        saveTemplateL(ITemplate.L001, "Application Help Link");
        saveTemplateL(ITemplate.L002, "Search Reports Help Link");
        saveTemplateL(ITemplate.L003, "View Report Issue/Action Help Link");
        saveTemplateL(ITemplate.L004, "Search Actions Help Link");
    }

    public final void saveTemplateL(String ref, String name)
    {
        InputStream input = null;
        try
        {
            input = getClass().getClassLoader().getResourceAsStream("template/" + ref + ".txt");
            Assert.assertNotNull(input);

            Template result = templateDao.findByReference(ref);
            if (result == null)
            {
                result = new Template();
                result.setReference(ref);
            }
            result.setName(name);
            result.setDetail(name);
            result.setContent(IOUtils.toByteArray(input));
            result.setContentType(ContentTypeEnum.TEXT_PLAIN.getContentType());
            templateDao.save(result);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
        finally
        {
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.TemplateDao#save(net.apollosoft.ats.domain.hibernate.Template)}.
     */
    @Test
    public final void testSaveTemplate()
    {
        // TODO
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.dao.TemplateDao#findByReference(java.lang.String)}.
     */
    @Test
    public final void testFindByReference()
    {
        try
        {
            Template result = templateDao.findByReference("R003");
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
        try
        {

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
     * Test method for {@link net.apollosoft.ats.audit.dao.BaseDao#findAll()}.
     */
    @Test
    public final void testFindAll()
    {
        try
        {
            List<Template> result = templateDao.findAll();
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