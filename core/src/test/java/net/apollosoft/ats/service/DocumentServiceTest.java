package net.apollosoft.ats.service;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.dao.ActionDao;
import net.apollosoft.ats.audit.dao.TemplateDao;
import net.apollosoft.ats.audit.domain.ITemplate;
import net.apollosoft.ats.audit.domain.hibernate.Action;
import net.apollosoft.ats.audit.service.DocumentService;
import net.apollosoft.ats.audit.service.SecurityService;
import net.apollosoft.ats.domain.dto.security.UserDto;
import net.apollosoft.ats.domain.hibernate.Template;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * @author azohdi
 */
public class DocumentServiceTest extends BaseTransactionalTest
{

    @Autowired
    @Qualifier("actionDao")
    //@Resource(name="actionDao")
    private ActionDao actionDao;

    @Autowired
    @Qualifier("templateDao")
    //@Resource(name="templateDao")
    private TemplateDao templateDao;

    @Autowired
    @Qualifier("documentService")
    //@Resource(name="documentService")
    private DocumentService documentService;

    @Autowired
    @Qualifier("securityService")
    //@Resource(name="securityService")
    private SecurityService securityService;

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.DocumentService#createDocument(net.apollosoft.ats.audit.domain.hibernate.security.User, net.apollosoft.ats.audit.domain.security.IUser, net.apollosoft.ats.audit.domain.IAudit, java.util.List, net.apollosoft.ats.audit.domain.ITemplate, java.io.OutputStream)}.
     */
    @Test
    public final void testCreateDocumentR004()
    {
        try
        {
            //
            Template template = templateDao.findByReference(ITemplate.R004);
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            //
            Map<String, Object> params = new Hashtable<String, Object>();
            params.put("url", "http:\\\\localhost\\mats\\");

            List<Action> openActions = actionDao.findOpen(null, null);
            params.put("actions", openActions);

            UserDto user = securityService.getUser();
            params.put("toUser", user);
            
            //
            documentService.createDocument(template, params, output);
            Assert.assertTrue(output.size() > 0);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

}
