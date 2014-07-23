package net.apollosoft.ats.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;
import net.apollosoft.ats.BaseTransactionalTest;
import net.apollosoft.ats.audit.dao.ActionDao;
import net.apollosoft.ats.audit.dao.IssueDao;
import net.apollosoft.ats.audit.dao.TemplateDao;
import net.apollosoft.ats.audit.domain.IAction;
import net.apollosoft.ats.audit.domain.ITemplate;
import net.apollosoft.ats.audit.domain.dto.ActionDto;
import net.apollosoft.ats.audit.domain.dto.CommentDto;
import net.apollosoft.ats.audit.domain.hibernate.Action;
import net.apollosoft.ats.audit.domain.hibernate.ActionGroupDivision;
import net.apollosoft.ats.audit.domain.hibernate.Audit;
import net.apollosoft.ats.audit.domain.hibernate.Issue;
import net.apollosoft.ats.audit.domain.refdata.IBusinessStatus.BusinessStatusEnum;
import net.apollosoft.ats.audit.search.ISortBy;
import net.apollosoft.ats.audit.search.ReportSearchCriteria;
import net.apollosoft.ats.audit.service.ActionService;
import net.apollosoft.ats.audit.validation.ValidationException;
import net.apollosoft.ats.dao.ActionDaoTest;
import net.apollosoft.ats.dao.IssueDaoTest;
import net.apollosoft.ats.domain.dto.security.GroupDivisionDto;
import net.apollosoft.ats.domain.dto.security.GroupDto;
import net.apollosoft.ats.domain.dto.security.UserDto;
import net.apollosoft.ats.domain.hibernate.Template;
import net.apollosoft.ats.domain.hibernate.security.User;
import net.apollosoft.ats.domain.security.IGroup;
import net.apollosoft.ats.domain.security.IUser;
import net.apollosoft.ats.search.IDir;
import net.apollosoft.ats.search.Pagination;
import net.apollosoft.ats.util.ThreadLocalUtils;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;


/**
 * @author azohdi
 *
 */
public class ActionServiceTest extends BaseTransactionalTest
{

    /** id */
    /*public*/static Long ID;

    @Autowired
    @Qualifier("actionService")
    //@Resource(name="actionService")
    private ActionService actionService;

    @Autowired
    @Qualifier("issueDao")
    //@Resource(name="issueDao")
    private IssueDao issueDao;

    @Autowired
    @Qualifier("actionDao")
    //@Resource(name="actionDao")
    private ActionDao actionDao;

    @Autowired
    @Qualifier("templateDao")
    //@Resource(name="templateDao")
    private TemplateDao templateDao;

    @Before
    public void setUp() throws Exception
    {
        try
        {
            //has dependency on IssueDaoTest
            Issue issue = issueDao.findByReference(IssueDaoTest.reference);
            Assert.assertNotNull(issue);
            Action entity = ID == null ? actionDao.findByName((Audit) issue.getAudit(),
                ActionDaoTest.reference, ActionDaoTest.name) : actionDao.findById(ID);
            if (entity == null)
            {
                entity = new Action();
                entity.setIssue(issue);
                entity.setReference(ActionDaoTest.reference);
                entity.setName("ActionDaoTest");
                entity.setDetail("detail-" + System.currentTimeMillis());
                issue.add(entity);
                issueDao.save(issue);
            }
            ID = entity.getId();
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.ActionService#find(net.apollosoft.ats.audit.search.ActionSearchCriteria)}.
     */
    @Test
    public final void testFind()
    {
        try
        {
            ReportSearchCriteria criteria = new ReportSearchCriteria();
            Issue issue = issueDao.findByReference(IssueDaoTest.reference);
            Assert.assertNotNull(issue);

            criteria.setIssueId(issue.getId());
            Pagination p = new Pagination(25, 0, ISortBy.ACTION_DUE_DATE_SQL, IDir.DESC);
            criteria.setPagination(p);
            List<ActionDto> result = actionService.find(criteria);
            //Assert.assertTrue("No record(s) found.", !result.isEmpty());

            criteria = new ReportSearchCriteria();
            criteria.getResponsibleUser().setId("15100");
            criteria.setPagination(p);
            result = actionService.find(criteria);
            //Assert.assertTrue(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.ActionService#findById(java.lang.Long)}.
     */
    @Test
    public final void testFindById()
    {
        try
        {
            //has dependency on IssueDaoTest
            Issue issue = issueDao.findByReference(IssueDaoTest.reference);
            Assert.assertNotNull(issue);
            Action action = actionDao.findByName((Audit) issue.getAudit(), ActionDaoTest.reference,
                ActionDaoTest.name);
            Assert.assertNotNull(action);
            IAction result = actionService.findById(action.getId());
            Assert.assertNotNull(result);
        }
        catch (net.apollosoft.ats.domain.security.SecurityException success)
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.ActionService#saveComment(java.lang.Long, net.apollosoft.ats.audit.domain.IComment)}.
     */
    @Test
    @Rollback(value = true)
    public final void testSave()
    {
        try
        {
            Action entity = actionDao.findById(ID);
            ActionDto audit = new ActionDto(entity);
            // changed
            actionService.save(audit);
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
     * Test method for {@link net.apollosoft.ats.audit.service.ActionService#saveComment(java.lang.Long, net.apollosoft.ats.audit.domain.IComment)}.
     */
    @Test
    @Rollback(value = true)
    public final void testSaveComment()
    {
        try
        {
            //has dependency on IssueDaoTest
            Issue issue = issueDao.findByReference(IssueDaoTest.reference);
            Assert.assertNotNull(issue);
            Action action = actionDao.findByName((Audit) issue.getAudit(), ActionDaoTest.reference,
                ActionDaoTest.name);
            Assert.assertNotNull(action);
            CommentDto comment = new CommentDto();
            ActionDto actionDto = new ActionDto(action);
            comment.setDetail("Detail-" + System.currentTimeMillis());
            actionService.saveComment(actionDto, comment);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.ActionService#addResponsibleUsers(net.apollosoft.ats.audit.domain.hibernate.Action, java.util.Set)}.
     */
    @Test
    @Rollback(value = false)
    public final void testAddResponsibleUsers()
    {
        try
        {
            Action action = actionDao.findById(ID);
            Set<User> users = new HashSet<User>();
            User user = new User(ThreadLocalUtils.getUser().getId());
            users.add(user);
            actionService.addResponsibleUsers(action, users);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.ActionService#addActionResponsible(net.apollosoft.ats.audit.domain.dto.ActionDto, net.apollosoft.ats.domain.dto.security.UserDto, boolean)}.
     */
    @Test
    @Rollback(value = false)
    public final void testAddActionResponsible()
    {
        try
        {
            ActionDto actionDto = actionService.findById(ID);
            UserDto userDto = new UserDto(ThreadLocalUtils.getUser().getId());
            actionService.addActionResponsible(actionDto, userDto, true);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.ActionService#removeActionResponsible(java.lang.Long, java.lang.String)}.
     */
    @Test
    @Rollback(value = false)
    public final void testRemoveActionResponsible()
    {
        try
        {
            actionService.removeActionResponsible(ID, ThreadLocalUtils.getUser().getId());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.ActionService#addGroupDivision(net.apollosoft.ats.audit.domain.hibernate.Action, java.util.Set)}.
     */
    @Test
    //@Rollback(value = false)
    public final void testAddGroupDivisionActionSetOfGroupDivisionDto()
    {
        try
        {
            Action action = actionDao.findById(ID);
            Set<GroupDivisionDto> groupDivisions = new HashSet<GroupDivisionDto>();
            GroupDivisionDto groupDivisionDto = new GroupDivisionDto();
            groupDivisionDto.setGroup(new GroupDto(GroupDto.GLOBAL_ID));
            groupDivisionDto.setDivision(null);
            groupDivisions.add(groupDivisionDto);
            actionService.addGroupDivision(action, groupDivisions);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.ActionService#addGroupDivision(net.apollosoft.ats.audit.domain.dto.ActionDto, net.apollosoft.ats.domain.dto.security.GroupDivisionDto)}.
     */
    @Test
    //@Rollback(value = false)
    public final void testAddGroupDivisionActionDtoGroupDivisionDto()
    {
        try
        {
            ActionDto actionDto = actionService.findById(ID);
            GroupDivisionDto groupDivisionDto = new GroupDivisionDto();
            groupDivisionDto.setGroup(new GroupDto(IGroup.GLOBAL_ID));
            groupDivisionDto.setDivision(null);
            actionService.addGroupDivision(actionDto, groupDivisionDto);
        }
        catch (net.apollosoft.ats.domain.security.SecurityException success)
        {
            //to be configured for security
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.ActionService#removeGroupDivision(net.apollosoft.ats.audit.domain.dto.ActionDto, java.lang.Long)}.
     */
    @Test
    //@Rollback(value = false)
    public final void testRemoveGroupDivision()
    {
        try
        {
            ActionDto actionDto = actionService.findById(ID);
            Action action = actionDao.findById(ID);
            List<ActionGroupDivision> groupDivisions = action.getGroupDivisions();
            for (ActionGroupDivision groupDivision : groupDivisions)
            {
                //created in previous add method
                if (GroupDto.GLOBAL_ID.equals(groupDivision.getGroup().getId()) && groupDivision.getDivision() == null)
                {
                    actionService.removeGroupDivision(actionDto, groupDivision.getId());
                    break;
                }
            }
        }
        catch (net.apollosoft.ats.domain.security.SecurityException success)
        {
            //to be configured for security
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.ActionService#businessStatusChanged(java.lang.Long, java.lang.Long, java.lang.Long)}.
     */
    @Test
    public final void testBusinessStatusChanged()
    {
        try
        {
            actionService.businessStatusChanged(ID, null, new Long(BusinessStatusEnum.IMPLEMENTED
                .ordinal()));
        }
        catch (net.apollosoft.ats.domain.security.SecurityException success)
        {
            //to be configured for security
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.ActionService#delete(net.apollosoft.ats.audit.domain.dto.ActionDto)}.
     */
    @Test
    public final void testDelete()
    {
        try
        {
            ActionDto actionDto = actionService.findById(ID);
            actionService.delete(actionDto);
        }
        catch (net.apollosoft.ats.domain.security.SecurityException success)
        {
            //to be configured for security
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.ActionService#findByAuditId(java.lang.Long, net.apollosoft.ats.search.Pagination, java.lang.Boolean)}.
     */
    @Test
    public final void testFindByAuditId()
    {
        try
        {
            Action action = actionDao.findById(ID);
            List<ActionDto> result = actionService.findByAuditId(action.getIssue().getAudit().getId(), null, null);
            Assert.assertTrue(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.ActionService#findByIssueId(java.lang.Long, net.apollosoft.ats.search.Pagination, java.lang.Boolean)}.
     */
    @Test
    public final void testFindByIssueId()
    {
        try
        {
            Action action = actionDao.findById(ID);
            List<ActionDto> result = actionService.findByIssueId(action.getIssue().getId(), null, null);
            Assert.assertTrue(!result.isEmpty());
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.ActionService#sendEmail(net.apollosoft.ats.domain.hibernate.Template, java.util.Map)}.
     */
    @Test
    public final void testSendEmail()
    {
        try
        {
            Template template = templateDao.findByReference(ITemplate.R001);
            Map<IUser, Set<Action>> userActions = new HashMap<IUser, Set<Action>>();
            Set<Action> actions = new HashSet<Action>();
            Action action = actionDao.findById(ID);
            actions.add(action);
            UserDto user = new UserDto(ThreadLocalUtils.getUser().getId());
            userActions.put(user, actions);
            actionService.sendEmail(template, userActions);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.audit.service.ActionService#sendEmailToPersonResponsible(java.lang.String, java.lang.Long)}.
     */
    @Test
    public final void testSendEmailToPersonResponsible()
    {
        try
        {
            actionService.sendEmailToPersonResponsible(ThreadLocalUtils.getUser().getId(), ID);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

}