/**
 * 
 */
package net.apollosoft.ats.service;

import java.util.List;

import junit.framework.Assert;
import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.service.EntityService;
import net.apollosoft.ats.domain.dto.security.DivisionDto;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
*
* @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
*/
public class EntityServiceTest extends BaseTransactionalTest
{

    @Autowired
    @Qualifier("entityService")
    //@Resource(name="entityService")
    private EntityService entityService;

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#findDivisionByGroupId(java.lang.Long)}.
     */
    @Test
    public final void testFindDivisionByGroupId()
    {
        try
        {
            List<DivisionDto> result = entityService.findDivisionByGroupId(1L);
            Assert.assertTrue("No record(s) found.", !result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#findAllGroup()}.
     */
    @Test
    public final void testFindAllGroup()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#findAllActionStatus()}.
     */
    @Test
    public final void testFindAllActionStatus()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }       
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#findAllFollowupStatus()}.
     */
    @Test
    public final void testFindAllFollowupStatus()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#findBusinessStatusById(java.lang.Long)}.
     */
    @Test
    public final void testFindBusinessStatusById()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#findAllBusinessStatus()}.
     */
    @Test
    public final void testFindAllBusinessStatus()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#save(net.apollosoft.ats.audit.domain.dto.refdata.BusinessStatusDto)}.
     */
    @Test
    public final void testSaveBusinessStatusDto()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#delete(net.apollosoft.ats.audit.domain.dto.refdata.BusinessStatusDto, boolean)}.
     */
    @Test
    public final void testDeleteBusinessStatusDtoBoolean()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#findFunctionById(java.lang.Long)}.
     */
    @Test
    public final void testFindFunctionById()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#findAllFunction()}.
     */
    @Test
    public final void testFindAllFunction()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#save(net.apollosoft.ats.audit.domain.dto.security.FunctionDto)}.
     */
    @Test
    public final void testSaveFunctionDto()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#delete(net.apollosoft.ats.audit.domain.dto.security.FunctionDto, boolean)}.
     */
    @Test
    public final void testDeleteFunctionDtoBoolean()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#findAllRating()}.
     */
    @Test
    public final void testFindAllRating()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#findAllReportType()}.
     */
    @Test
    public final void testFindAllReportType()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#findRoleById(java.lang.Long)}.
     */
    @Test
    public final void testFindRoleById()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#findAllRole()}.
     */
    @Test
    public final void testFindAllRole()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#save(net.apollosoft.ats.audit.domain.dto.security.RoleDto)}.
     */
    @Test
    public final void testSaveRoleDto()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#findFunctionByRoleId(java.lang.Long)}.
     */
    @Test
    public final void testFindFunctionByRoleId()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#saveFunction(net.apollosoft.ats.audit.domain.dto.security.RoleDto, net.apollosoft.ats.audit.domain.dto.security.FunctionDto)}.
     */
    @Test
    public final void testSaveFunction()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#delete(net.apollosoft.ats.audit.domain.dto.security.RoleDto, boolean)}.
     */
    @Test
    public final void testDeleteRoleDtoBoolean()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#findAllParentRiskCategory()}.
     */
    @Test
    public final void testFindAllParentRiskCategory()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#findAllParentRisks()}.
     */
    @Test
    public final void testFindAllParentRisks()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#findParentRiskByCategoryId(java.lang.Long)}.
     */
    @Test
    public final void testFindParentRiskByCategoryId()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#findParentRiskById(java.lang.Long)}.
     */
    @Test
    public final void testFindParentRiskById()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#findAllTemplate()}.
     */
    @Test
    public final void testFindAllTemplate()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#findTemplateById(java.lang.Long)}.
     */
    @Test
    public final void testFindTemplateById()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.EntityService#save(net.apollosoft.ats.audit.domain.dto.TemplateDto)}.
     */
    @Test
    public final void testSaveTemplateDto()
    {
        try
        {
            //fail("Not yet implemented"); // TODO
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

}