package net.apollosoft.ats.service;

import junit.framework.Assert;
import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.dao.UserMatrixDao;
import net.apollosoft.ats.audit.service.UserService;
import net.apollosoft.ats.audit.validation.ValidationException;
import net.apollosoft.ats.domain.dto.refdata.ReportTypeDto;
import net.apollosoft.ats.domain.dto.security.DivisionDto;
import net.apollosoft.ats.domain.dto.security.GroupDto;
import net.apollosoft.ats.domain.dto.security.RoleDto;
import net.apollosoft.ats.domain.dto.security.UserDto;
import net.apollosoft.ats.domain.dto.security.UserMatrixDto;
import net.apollosoft.ats.domain.hibernate.refdata.ReportType;
import net.apollosoft.ats.domain.hibernate.security.Division;
import net.apollosoft.ats.domain.hibernate.security.Group;
import net.apollosoft.ats.domain.hibernate.security.Role;
import net.apollosoft.ats.domain.hibernate.security.User;
import net.apollosoft.ats.domain.hibernate.security.UserMatrix;
import net.apollosoft.ats.domain.refdata.IReportType.ReportTypeEnum;
import net.apollosoft.ats.domain.security.IRole.RoleEnum;
import net.apollosoft.ats.util.ThreadLocalUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
*
* @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
*/
public class UserServiceTest extends BaseTransactionalTest
{

    /** id */
    /*public*/static Long ID;

    @Autowired
    @Qualifier("userService")
    //@Resource(name="userService")
    private UserService userService;

    @Autowired
    @Qualifier("userMatrixDao")
    //@Resource(name="userMatrixDao")
    private UserMatrixDao userMatrixDao;

    @Before
    public void setUp() throws Exception
    {
        String userId = ThreadLocalUtils.getUser().getId();
        Long roleId = new Long(RoleEnum.SYSTEM_ADMIN.ordinal());
        Long reportTypeId = new Long(ReportTypeEnum.AUDIT.ordinal());
        Long groupId = null;
        Long divisionId = null;
        try
        {
            UserMatrixDto userMatrixDto = new UserMatrixDto();
            userMatrixDto.setUser(new UserDto(userId));
            userMatrixDto.setRole(new RoleDto(roleId));
            userMatrixDto.setReportType(new ReportTypeDto(reportTypeId));
            userMatrixDto.setGroup(new GroupDto(groupId));
            userMatrixDto.setDivision(new DivisionDto(divisionId));
            UserMatrix entity = ID == null ? userMatrixDao.findByUniqueKey(userMatrixDto) : userMatrixDao.findById(ID);
            if (entity == null)
            {
                UserMatrix userMatrix = new UserMatrix();
                userMatrix.setUser(new User(userId));
                userMatrix.setGroup(new Group(groupId));
                userMatrix.setDivision(new Division(divisionId));
                userMatrix.setRole(new Role(roleId));
                userMatrix.setReportType(new ReportType(reportTypeId));
                userMatrixDao.save(userMatrix);
                ID = userMatrix.getId();
            }
            else
            {
                ID = entity.getId();
            }
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
     * Test method for {@link net.apollosoft.ats.audit.service.UserService#find(net.apollosoft.ats.audit.search.UserSearchCriteria)}.
     */
    @Test
    public final void testFind()
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
     * Test method for {@link net.apollosoft.ats.audit.service.UserService#findById(java.lang.String)}.
     */
    @Test
    public final void testFindById()
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
     * Test method for {@link net.apollosoft.ats.audit.service.UserService#findUserMatrix(java.lang.String)}.
     */
    @Test
    public final void testFindUserMatrixById()
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
     * Test method for {@link net.apollosoft.ats.audit.service.UserService#saveUserMatrix(net.apollosoft.ats.domain.dto.security.UserMatrixDto, net.apollosoft.ats.domain.dto.security.UserMatrixDto)}.
     */
    @Test
    //@Rollback(value = false)
    public final void testSaveUserMatrix()
    {
        try
        {
            UserMatrix entity = userMatrixDao.findById(ID);
            UserMatrixDto userMatrix = new UserMatrixDto(entity);
            userMatrix.setReportType(new ReportTypeDto(1L)); //changed
            userService.save(userMatrix);
        }
        catch (ValidationException e)
        {
            //not an error
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.UserService#deleteUserMatrix(net.apollosoft.ats.domain.dto.security.UserMatrixDto)}.
     */
    @Test
    public final void testDeleteUserMatrix()
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
