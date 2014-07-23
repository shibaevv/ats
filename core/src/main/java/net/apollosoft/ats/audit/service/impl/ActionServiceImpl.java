package net.apollosoft.ats.audit.service.impl;

import java.io.ByteArrayOutputStream;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.apollosoft.ats.audit.dao.ActionDao;
import net.apollosoft.ats.audit.dao.ActionFollowupStatusDao;
import net.apollosoft.ats.audit.dao.ActionGroupDivisionDao;
import net.apollosoft.ats.audit.dao.ActionResponsibleDao;
import net.apollosoft.ats.audit.dao.BusinessStatusDao;
import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.dao.DivisionDao;
import net.apollosoft.ats.audit.dao.GroupDao;
import net.apollosoft.ats.audit.dao.TemplateDao;
import net.apollosoft.ats.audit.dao.UserDao;
import net.apollosoft.ats.audit.dao.UserMatrixDao;
import net.apollosoft.ats.audit.domain.IComment;
import net.apollosoft.ats.audit.domain.ITemplate;
import net.apollosoft.ats.audit.domain.dto.ActionDto;
import net.apollosoft.ats.audit.domain.dto.AuditDto;
import net.apollosoft.ats.audit.domain.dto.CommentDto;
import net.apollosoft.ats.audit.domain.dto.IssueDto;
import net.apollosoft.ats.audit.domain.dto.refdata.ActionStatusDto;
import net.apollosoft.ats.audit.domain.dto.refdata.BusinessStatusDto;
import net.apollosoft.ats.audit.domain.dto.refdata.RatingDto;
import net.apollosoft.ats.audit.domain.hibernate.Action;
import net.apollosoft.ats.audit.domain.hibernate.ActionGroupDivision;
import net.apollosoft.ats.audit.domain.hibernate.ActionResponsible;
import net.apollosoft.ats.audit.domain.hibernate.Comment;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ActionFollowupStatus;
import net.apollosoft.ats.audit.domain.hibernate.refdata.BusinessStatus;
import net.apollosoft.ats.audit.domain.refdata.IActionFollowupStatus.ActionFollowupStatusEnum;
import net.apollosoft.ats.audit.search.ReportSearchCriteria;
import net.apollosoft.ats.audit.service.ActionService;
import net.apollosoft.ats.audit.service.CommentService;
import net.apollosoft.ats.audit.service.DocumentService;
import net.apollosoft.ats.audit.service.EmailService;
import net.apollosoft.ats.audit.service.EntityService;
import net.apollosoft.ats.audit.service.IssueService;
import net.apollosoft.ats.audit.service.MailData;
import net.apollosoft.ats.audit.service.SecurityService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.util.ConvertUtils;
import net.apollosoft.ats.audit.validation.ActionGroupDivisionValidator;
import net.apollosoft.ats.audit.validation.ActionResponsibleValidator;
import net.apollosoft.ats.audit.validation.ActionValidator;
import net.apollosoft.ats.audit.validation.CommentValidator;
import net.apollosoft.ats.audit.validation.ValidationException;
import net.apollosoft.ats.audit.validation.ValidationException.ValidationMessage;
import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.dto.refdata.ReportTypeDto;
import net.apollosoft.ats.domain.dto.security.FunctionDto;
import net.apollosoft.ats.domain.dto.security.GroupDivisionDto;
import net.apollosoft.ats.domain.dto.security.UserDto;
import net.apollosoft.ats.domain.dto.security.UserMatrixDto;
import net.apollosoft.ats.domain.hibernate.Template;
import net.apollosoft.ats.domain.hibernate.security.Division;
import net.apollosoft.ats.domain.hibernate.security.Group;
import net.apollosoft.ats.domain.hibernate.security.User;
import net.apollosoft.ats.domain.hibernate.security.UserMatrix;
import net.apollosoft.ats.domain.refdata.IUserType.UserTypeEnum;
import net.apollosoft.ats.domain.security.IDivision;
import net.apollosoft.ats.domain.security.IFunction;
import net.apollosoft.ats.domain.security.IGroup;
import net.apollosoft.ats.domain.security.IGroupDivision;
import net.apollosoft.ats.domain.security.IUser;
import net.apollosoft.ats.domain.security.IRole.RoleEnum;
import net.apollosoft.ats.search.Pagination;
import net.apollosoft.ats.util.BeanUtils;
import net.apollosoft.ats.util.Formatter;
import net.apollosoft.ats.util.ThreadLocalUtils;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class ActionServiceImpl implements ActionService
{

    /** logger. */
    //private static final Log LOG = LogFactory.getLog(ActionServiceImpl.class);

    @Autowired
    @Qualifier("actionDao")
    //@Resource(name="actionDao")
    private ActionDao actionDao;

    @Autowired
    @Qualifier("divisionDao")
    //@Resource(name="divisionDao")
    private DivisionDao divisionDao;

    @Autowired
    @Qualifier("groupDao")
    //@Resource(name="groupDao")
    private GroupDao groupDao;

    @Autowired
    @Qualifier("userDao")
    //@Resource(name="userDao")
    private UserDao userDao;

    @Autowired
    @Qualifier("userMatrixDao")
    //@Resource(name="userMatrixDao")
    private UserMatrixDao userMatrixDao;

    @Autowired
    @Qualifier("actionFollowupStatusDao")
    //@Resource(name="businessStatusDao")
    private ActionFollowupStatusDao actionFollowupStatusDao;

    @Autowired
    @Qualifier("actionGroupDivisionDao")
    //@Resource(name="actionGroupDivisionDao")
    private ActionGroupDivisionDao actionGroupDivisionDao;

    @Autowired
    @Qualifier("actionResponsibleDao")
    //@Resource(name="actionResponsibleDao")
    private ActionResponsibleDao actionResponsibleDao;

    @Autowired
    @Qualifier("businessStatusDao")
    //@Resource(name="businessStatusDao")
    private BusinessStatusDao businessStatusDao;

    @Autowired
    @Qualifier("templateDao")
    //@Resource(name="templateDao")
    private TemplateDao templateDao;

    @Autowired
    @Qualifier("entityService")
    //@Resource(name="entityService")
    private EntityService entityService;

    @Autowired
    @Qualifier("issueService")
    //@Resource(name="issueService")
    private IssueService issueService;

    @Autowired
    @Qualifier("securityService")
    //@Resource(name="securityService")
    private SecurityService securityService;

    @Autowired
    @Qualifier("commentService")
    //@Resource(name="commentService")
    private CommentService commentService;

    @Autowired
    @Qualifier("documentService")
    //@Resource(name="documentService")
    private DocumentService documentService;

    @Autowired
    @Qualifier("emailService")
    //@Resource(name="emailService")
    private EmailService emailService;

    @Autowired
    @Qualifier("commentValidator")
    //@Resource(name="commentValidator")
    private CommentValidator commentValidator;

    @Autowired
    @Qualifier("actionValidator")
    //@Resource(name="actionValidator")
    private ActionValidator actionValidator;

    @Autowired
    @Qualifier("actionGroupDivisionValidator")
    //@Resource(name="actionGroupDivisionValidator")
    private ActionGroupDivisionValidator actionGroupDivisionValidator;

    @Autowired
    @Qualifier("actionResponsibleValidator")
    //@Resource(name="actionResponsibleValidator")
    private ActionResponsibleValidator actionResponsibleValidator;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.ActionService#addActionResponsible(net.apollosoft.ats.audit.domain.dto.ActionDto, net.apollosoft.ats.audit.domain.dto.security.UserDto, boolean)
     */
    public void addActionResponsible(ActionDto actionDto, UserDto userDto,
        boolean clearActionResponsible) throws ServiceException, ValidationException
    {
        ActionResponsible actionResponsible = new ActionResponsible();
        try
        {
            BeanUtils.copyProperties(userDto, actionResponsible, IUser.IGNORE);
            Action entity = actionDao.findById(actionDto.getActionId());
            if (entity == null)
            {
                save(actionDto);
                entity = actionDao.findById(actionDto.getActionId());
            }
            //
            if (clearActionResponsible)
            {
                actionResponsible.setAction(entity);
                actionResponsible.setUser(new User(userDto.getUserId()));

                if (!entity.getActionResponsibles().contains(actionResponsible))
                {
                    actionResponsibleDao.save(actionResponsible);

                    Map<String, Object> params = new HashMap<String, Object>();
                    User user = userDao.findById(actionResponsible.getUser().getUserId());
                    params.put("responsible", user.getFullName());
                    entity.addLog(createAuditLog(ITemplate.A003, params));
                }
            }
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.ActionService#addGroupDivision(net.apollosoft.ats.audit.domain.hibernate.Action, java.util.List)
     */
    public void addGroupDivision(Action action, Set<GroupDivisionDto> newItems)
        throws ServiceException
    {
        try
        {
            //all current action group/division
            List<ActionGroupDivision> linkItems = action.getGroupDivisions();
            for (ActionGroupDivision linkItem : linkItems)
            {
                GroupDivisionDto item = new GroupDivisionDto();
                item.setGroup(linkItem.getGroup());
                item.setDivision(linkItem.getDivision());
                if (newItems.contains(item))
                {
                    if (linkItem.isDeleted())
                    {
                        actionDao.unDelete(linkItem);
                    }
                }
                else
                {
                    //logically delete
                    actionDao.delete(linkItem);
                }
            }
            //add new
            for (GroupDivisionDto item : newItems)
            {
                if (!linkItems.contains(item))
                {
                    ActionGroupDivision linkItem = new ActionGroupDivision();
                    linkItem.setAction(action);
                    IGroup g = item.getGroup();
                    Group group = g instanceof Group ? (Group) g : (g == null ? null : groupDao
                        .findById(g.getId()));
                    linkItem.setGroup(group);
                    IDivision d = item.getDivision();
                    Division division = d instanceof Division ? (Division) d : (d == null ? null
                        : divisionDao.findById(d.getId()));
                    linkItem.setDivision(division);
                    linkItems.add(linkItem);
                }
            }
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.ActionService#addGroupDivision(net.apollosoft.ats.audit.domain.dto.ActionDto, net.apollosoft.ats.audit.domain.dto.security.GroupDivisionDto)
     */
    public void addGroupDivision(ActionDto action, GroupDivisionDto groupDivisionDto)
        throws ServiceException, ValidationException
    {
        ActionGroupDivision actionGroupDivision = new ActionGroupDivision();
        try
        {
            BeanUtils.copyProperties(groupDivisionDto, actionGroupDivision, IGroupDivision.IGNORE);
            Action entity = actionDao.findById(action.getActionId());
            if (entity == null)
            {
                save(action);
                entity = actionDao.findById(action.getActionId());
            }
            actionGroupDivision.setAction(entity);
            actionGroupDivision.setGroup(new Group(groupDivisionDto.getGroup().getGroupId()));
            //
            Division division = null;
            if (groupDivisionDto.getDivision() != null && groupDivisionDto.getDivision().getDivisionId() != null)
            {
                division = new Division(groupDivisionDto.getDivision().getDivisionId());
            }
            actionGroupDivision.setDivision(division);

            //validation
            List<ValidationMessage> errors = actionGroupDivisionValidator
                .validate(actionGroupDivision);
            if (!errors.isEmpty())
            {
                throw new ValidationException(errors);
            }
            actionGroupDivisionDao.save(actionGroupDivision);
            Group group = groupDao.findById(actionGroupDivision.getGroup().getId());
            division = actionGroupDivision.getDivision() != null ? divisionDao
                .findById(actionGroupDivision.getDivision().getId()) : null;

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("group", group);
            params.put("division", division);
            entity.addLog(createAuditLog(ITemplate.A005, params));
        }
        catch (ValidationException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.ActionService#addResponsibleUsers(net.apollosoft.ats.audit.domain.IAction, java.util.List)
     */
    public void addResponsibleUsers(Action action, Set<User> newItems) throws ServiceException
    {
        try
        {
            //all current action responsible users
            List<IUser> oldItems = new ArrayList<IUser>();
            List<ActionResponsible> linkItems = action.getActionResponsibles();
            for (ActionResponsible linkItem : linkItems)
            {
                if (newItems.contains(linkItem.getUser()))
                {
                    if (linkItem.isDeleted())
                    {
                        actionDao.unDelete(linkItem);
                    }
                }
                else
                {
                    //logically delete
                    actionDao.delete(linkItem);
                }
                oldItems.add(linkItem.getUser());
            }
            //add new
            for (IUser item : newItems)
            {
                if (!oldItems.contains(item))
                {
                    ActionResponsible linkItem = new ActionResponsible();
                    linkItem.setAction(action);
                    linkItem.setUser(item);
                    linkItems.add(linkItem);
                }
            }
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.ActionService#businessStatusChanged(java.lang.Long, java.lang.Long, java.lang.Long, org.springframework.ui.ModelMap)
     */
    public ActionDto businessStatusChanged(Long actionId, Long issueId, Long businessStatusId)
        throws ServiceException
    {
        //
        ActionDto actionDto = actionId == null ? new ActionDto() : findById(actionId);
        boolean wasClosed = actionId == null ? false : actionDto.getBusinessStatus()
            .getActionStatus().isClosed();

        //
        BusinessStatusDto businessStatusDto = entityService
            .findBusinessStatusById(businessStatusId);
        boolean nowOpen = businessStatusDto.getActionStatus() == null ? false : businessStatusDto
            .getActionStatus().isOpen();

        if (wasClosed && nowOpen)
        {
            actionDto.setDueDate(null);
        }
        else if (actionId == null && businessStatusDto != null && businessStatusDto.isImplemented())
        {
            IssueDto issueDto = issueService.findById(issueId);
            Date issuedDate = issueDto.getAudit().getIssuedDate();

            //
            actionDto.setDueDate(issuedDate);
            actionDto.setClosedDate(issuedDate);
            actionDto.setFollowupDate(issuedDate);
            actionDto.setFollowupStatus(new ActionFollowupStatus(new Long(
                ActionFollowupStatusEnum.CLOSED_VERIFIED.ordinal())));
            actionDto.setBusinessStatus(businessStatusDto);
        }
        else if (businessStatusDto != null && businessStatusDto.getActionStatus().isOpen())
        {
            actionDto.setBusinessStatus(businessStatusDto);
        }

        return actionDto;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.ActionService#delete(net.apollosoft.ats.audit.domain.dto.ActionDto)
     */
    public void delete(ActionDto actionDto) throws ServiceException, ValidationException
    {
        try
        {
            Action action = actionDao.findById(actionDto.getActionId());
            actionDao.delete(action);
        }
        catch (DaoException e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.ActionService#find(net.apollosoft.ats.audit.search.ReportSearchCriteria)
     */
    public List<ActionDto> find(ReportSearchCriteria criteria) throws ServiceException
    {
        try
        {
            //use dto objects
            List<ActionDto> result = new ArrayList<ActionDto>();
//            //compass search
//            if (StringUtils.isNotBlank(criteria.getCompassQuery()))
//            {
//                List<Action> actions = actionDao.findByCriteriaCompass(criteria);
//                for (Action action : actions)
//                {
//                    ActionDto actionDto = convert(action);
//                    result.add(actionDto);
//                }
//                return result;
//            }
            //jdbc search
            List<Object[]> tableData = actionDao.findByCriteria(criteria);
            for (Object[] action : tableData)
            {
                ActionDto actionDto = convert(action);
                result.add(actionDto);
            }
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.ActionService#findByAuditId(java.lang.Long, boolean)
     */
    public List<ActionDto> findByAuditId(Long auditId, Pagination pagination, Boolean published) throws ServiceException
    {
        try
        {
            //
            List<Action> entities = actionDao.findByAuditId(auditId, pagination, published);
            //use dto objects
            List<ActionDto> result = new ArrayList<ActionDto>();
            for (Action action : entities)
            {
                ActionDto actionDto = convert(action);
                result.add(actionDto);
            }
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.ActionService#findById(java.lang.Long)
     */
    public ActionDto findById(Long actionId) throws ServiceException
    {
        try
        {
            Action entity = actionDao.findById(actionId);
            securityService.checkSecurity(entity);
            ActionDto result = convert(entity);
            return result;
        }
        catch (net.apollosoft.ats.domain.security.SecurityException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.ActionService#findByIssueId(java.lang.Long, boolean)
     */
    public List<ActionDto> findByIssueId(Long issueId, Pagination pagination, Boolean published) throws ServiceException
    {
        try
        {
            //
            List<Action> entities = actionDao.findByIssueId(issueId, pagination, published);
            //use dto objects
            List<ActionDto> result = new ArrayList<ActionDto>();
            for (Action action : entities)
            {
                ActionDto actionDto = convert(action);
                result.add(actionDto);
            }
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.ActionService#removeGroupDivision(net.apollosoft.ats.audit.domain.dto.ActionDto, java.lang.Long)
     */
    public void removeGroupDivision(ActionDto actionDto, Long groupDivisionId)
        throws ServiceException, ValidationException
    {
        if (groupDivisionId == null)
        {
            return;
        }
        try
        {
            ActionGroupDivision actionGroupDivision = actionGroupDivisionDao
                .findById(groupDivisionId);
            Action action = actionGroupDivision.getAction();
            // delete
            action.remove(actionGroupDivision);
            actionGroupDivisionDao.delete(actionGroupDivision);
            // validate if deletion is allowed on parent (after delete)
            List<ValidationMessage> errors = actionGroupDivisionValidator
                .validate(actionGroupDivision);
            if (!errors.isEmpty())
            {
                throw new ValidationException(errors);
            }
            // add audit log to parent
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("group", actionGroupDivision.getGroup());
            params.put("division", actionGroupDivision.getDivision());
            action.addLog(createAuditLog(ITemplate.A006, params));
            // save
            actionDao.save(action);
        }
        catch (ValidationException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.ActionService#removeActionResponsible(net.apollosoft.ats.audit.domain.dto.ActionDto, net.apollosoft.ats.audit.domain.dto.security.UserDto)
     */
    public void removeActionResponsible(Long actionId, String userId) throws ServiceException,
        ValidationException
    {
        try
        {
            ActionResponsible actionResponsible = actionResponsibleDao.find(actionId, userId);
            Action action = (Action) actionResponsible.getAction();
            // delete (TODO: add cascade to action hibernate annotations?)
            action.remove(actionResponsible);
            actionResponsibleDao.delete(actionResponsible);
            // validate if deletion is allowed on parent (after delete)
            List<ValidationMessage> errors = actionResponsibleValidator.validate(actionResponsible);
            if (!errors.isEmpty())
            {
                throw new ValidationException(errors);
            }
            // add audit log to parent
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("responsible", actionResponsible.getUser().getFullName());
            action.addLog(createAuditLog(ITemplate.A004, params));
        }
        catch (ValidationException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.ActionService#save(net.apollosoft.ats.audit.domain.dto.ActionDto)
     */
    public void save(ActionDto dto) throws ServiceException, ValidationException
    {
        if (Boolean.TRUE.equals(dto.getValidate()))
        {
            //validation
            List<ValidationMessage> errors = actionValidator.validate(dto);
            if (!errors.isEmpty())
            {
                throw new ValidationException(errors);
            }
        }

        try
        {
            Action entity = actionDao.findById(dto.getActionId());
            if (entity == null)
            {
                // new
                entity = new Action();
            }
            //Cache the last business status
            boolean wasClosed = entity.getBusinessStatus() == null ? false : entity
                .getBusinessStatus().getActionStatus().isClosed();
            boolean nowOpen = dto.getBusinessStatus().getActionStatus().isOpen();

            List<Comment> auditLogs = null;
            if (entity.isPublished())
            {
                auditLogs = createAuditLog(dto, entity);
            }

            ConvertUtils.convert(dto, entity);

            ConvertUtils.convert(dto.getComments(), entity);

            // if audit log comments created (see above)
            if (auditLogs != null && !auditLogs.isEmpty())
            {
                for (Comment auditLog : auditLogs)
                {
                    entity.addLog(auditLog);
                }
            }

            actionDao.save(entity);
            dto.setActionId(entity.getActionId());

            // should pass validation unless we have empty emails sent
            if (!entity.isPublished() || !dto.getValidate())
            {
                return;
            }

            // send an Email When reopening an action
            if (wasClosed && nowOpen)
            {
                sendEmailWhenActionReopened(entity);
            }
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.ActionService#saveComment(java.lang.Long, java.lang.Long, net.apollosoft.ats.audit.domain.IComment)
     */
    public void saveComment(ActionDto actionDto, CommentDto commentDto) throws ServiceException,
        ValidationException
    {
        //validation 4 Comment
        List<ValidationMessage> errors = commentValidator.validate(commentDto);
        if (!errors.isEmpty())
        {
            throw new ValidationException(errors);
        }

        //save
        try
        {
            //get existing action
            Action action = actionDao.findById(actionDto.getActionId());

            //Cache the new business status
            Long businessStatusId = actionDto.getBusinessStatus().getId();
            BusinessStatus businessStatus = businessStatusDao.findById(businessStatusId);
            boolean nowOpen = businessStatus == null ? false : businessStatus.getActionStatus().isOpen();
            boolean nowClosed = businessStatus == null ? false : businessStatus.getActionStatus().isClosed();
            //Cache the last business status
            boolean wasClosed = action.getBusinessStatus() == null ? false : action.getBusinessStatus().getActionStatus().isClosed();
            boolean wasOpen = action.getBusinessStatus() == null ? false : action.getBusinessStatus().getActionStatus().isOpen();


            //business status changed
            if (businessStatus != null && !businessStatus.equals(action.getBusinessStatus()))
            {
                if (action.isPublished())
                {
                    // can use the actionDto from above
                    actionDto = new ActionDto();
                    actionDto.setBusinessStatus(businessStatusDao.findById(businessStatusId));
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("before", action);
                    params.put("after", actionDto);
                    action.addLog(createAuditLog(ITemplate.A008, params));
                }
                //update BusinessStatus (it is changed)
                action.setBusinessStatus(new BusinessStatus(businessStatusId));
            }

            //create new comment
            Comment comment = new Comment();
            BeanUtils.copyProperties(commentDto, comment, IComment.IGNORE);
            //addDocuments
            commentService.addDocuments(comment, commentDto.getDocuments());
            //add new comment to action
            action.addComment(comment);
            //reset some action data to default
            if (nowOpen)
            {
                action.setClosedDate(null);
            }
            if(wasOpen && nowClosed) 
            {
                action.setClosedDate(ThreadLocalUtils.getDate());
            }

            //save action
            actionDao.save(action);

            // send an Email When reopening an action
            if (wasClosed && nowOpen)
            {
                sendEmailWhenActionReopened(action);
            }
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.ActionService#sendEmail(net.apollosoft.ats.audit.domain.hibernate.Template, java.util.Map)
     */
    public void sendEmail(Template template, Map<IUser, Set<Action>> actions)
        throws ServiceException
    {
        for (Map.Entry<IUser, Set<Action>> entry : actions.entrySet())
        {
            Set<Action> emailActions = entry.getValue();
            if (emailActions.isEmpty())
            {
                continue;
            }
            Map<String, Object> params = new HashMap<String, Object>();
            IUser user = entry.getKey();
            params.put("toUser", user);
            params.put("actions", emailActions);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            documentService.createDocument(template, params, output);
            MailData data = new MailData();
            data.setTo(user);
            data.setSubject(template.getDetail());
            data.setText(output.toString());
            emailService.sendMail(data);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.ActionService#senEmailToPersonResponsible(net.apollosoft.ats.audit.domain.dto.security.UserDto, net.apollosoft.ats.audit.domain.dto.ActionDto)
     */
    public void sendEmailToPersonResponsible(String userId, Long actionId) throws ServiceException
    {
        try
        {
            Map<IUser, Set<Action>> prActions = new HashMap<IUser, Set<Action>>();
            Set<Action> actions = new HashSet<Action>();
            Action action = actionDao.findById(actionId);
            actions.add(action);

            //
            User user = userDao.findById(userId);
            prActions.put(user, actions);

            Template template = templateDao.findByReference(ITemplate.R001);
            sendEmail(template, prActions);
        }
        catch (DaoException e)
        {
            throw new ServiceException(e.getMessage(), e);
        }

    }

    private void sendEmailWhenActionReopened(Action entity) throws DaoException, ServiceException
    {
        //
        Set<Action> actions = new HashSet<Action>();
        actions.add(entity);
        Template r001 = templateDao.findByReference(ITemplate.R001);
        Template r002 = templateDao.findByReference(ITemplate.R002);

        //send eMail to Responsible Users
        Map<IUser, Set<Action>> ruActions = new HashMap<IUser, Set<Action>>();
        for (IUser to : entity.getResponsibleUsers())
        {
            ruActions.put(to, actions);
        }

        if (!ruActions.isEmpty())
        {
            sendEmail(r001, ruActions);
        }

        //send eMail to all relevant oversight users
        List<UserMatrix> userMatrix = userMatrixDao.findByRoleId(new Long(RoleEnum.OVERSIGHT_TEAM
            .ordinal()));
        //prepare map of oversight users
        Map<IUser, Set<Action>> ouActions = new HashMap<IUser, Set<Action>>();
        for (UserMatrix um : userMatrix)
        {
            //
            IUser u = um.getUser();
            if (!ouActions.containsKey(u))
            {
                ouActions.put(u, new HashSet<Action>());
            }
            //
            for (Action a : actions)
            {
                if (!um.getReportType().equals(a.getIssue().getAudit().getReportType()))
                {
                    continue;
                }
                for (ActionGroupDivision agd : a.getGroupDivisions())
                {
                    if (um.getGroup() == null)
                    {
                        ouActions.get(u).add(a);
                    }
                    else
                    {
                        if (um.getGroup().equals(agd.getGroup()))
                        {
                            if (um.getDivision() == null
                                || um.getDivision().equals(agd.getDivision()))
                            {
                                ouActions.get(u).add(a);
                            }

                        }
                    }
                }
            }
        }

        if (!ouActions.isEmpty())
        {
            sendEmail(r002, ouActions);
        }
    }

    private List<Comment> createAuditLog(ActionDto dto, Action entity) throws DaoException,
        ServiceException, DomainException
    {
        List<Comment> result = new ArrayList<Comment>();
        // should we compare ignore case ?
        if (BeanUtils.compareTo(dto.getName(), entity.getName()) != 0)
        {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("before", entity);
            params.put("after", dto);
            result.add(createAuditLog(ITemplate.A002, params));
        }
        //
        if (BeanUtils.compareTo(dto.getDueDate(), entity.getDueDate()) != 0)
        {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("before", entity);
            params.put("after", dto);
            result.add(createAuditLog(ITemplate.A007, params));
        }
        // this does not catch all possible changes
        if (dto.getBusinessStatus() != null
            && !dto.getBusinessStatus().equals(entity.getBusinessStatus()))
        {
            BusinessStatus businessStatus = businessStatusDao.findById(dto.getBusinessStatus()
                .getId());
            dto.setBusinessStatus(new BusinessStatusDto(businessStatus));
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("before", entity);
            params.put("after", dto);
            result.add(createAuditLog(ITemplate.A008, params));
        }
        // this does not catch all possible changes
        // ie. 
        // follow up status changes from something to blank
        // follow up status changed automatically upon save
        if (dto.getFollowupStatus() != null
            && !dto.getFollowupStatus().equals(entity.getFollowupStatus()))
        {
            ActionFollowupStatus actionFollowupStatus = actionFollowupStatusDao.findById(dto
                .getFollowupStatus().getId());
            dto.setFollowupStatus(actionFollowupStatus);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("before", entity);
            params.put("after", dto);
            result.add(createAuditLog(ITemplate.A009, params));
        }
        //
        if (BeanUtils.compareTo(dto.getDetail(), entity.getDetail()) != 0)
        {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("before", entity);
            params.put("after", dto);
            result.add(createAuditLog(ITemplate.A010, params));
        }
        return result;
    }

    /**
     * 
     * @param templateId
     * @param params
     * @return
     * @throws DaoException
     * @throws ServiceException
     */
    private Comment createAuditLog(String templateId, Map<String, Object> params)
        throws DaoException, ServiceException
    {
        Comment result = new Comment();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Template template = templateDao.findByReference(templateId);
        documentService.createDocument(template, params, output);
        result.setDetail(output.toString());
        return result;
    }

    /**
     * 
     * @param entity
     * @return
     * @throws Exception
     */
    private ActionDto convert(Action entity) throws Exception
    {
        // reload (retrieved by compass query)
        if (entity.getIssue() == null)
        {
            entity = actionDao.findById(entity.getActionId());
        }
        //
        ActionDto action = ConvertUtils.convert(entity);
        //
        IssueDto issue = new IssueDto(entity.getIssue());
        action.setIssue(issue);
        //
        AuditDto audit = new AuditDto(entity.getIssue().getAudit());
        issue.setAudit(audit);
        // set overdue flag
        ActionStatusDto actionStatus = (ActionStatusDto) action.getBusinessStatus()
            .getActionStatus();
        action.setOverDue(action.getDaysOverdue() > 0 && actionStatus.isOpen());
        // set security
        convertSecurity(action, entity.getResponsibleUsers(), entity.getGroupDivisions());
        // set comments
        for (IComment comment : entity.getComments())
        {
            //if (comment == null) continue; // non-sequential index
            action.getComments().add(new CommentDto(comment));
        }
        return action;
    }

    /**
     * See "v_ats_action" for full list of columns
           ats_audit_id v_audit_id,
           ats_audit_name audit_name,
           ats_audit.report_type_id v_report_type_id,
           ats_audit_issued_date audit_issued_date,
           ats_audit.published_by v_audit_published_by,
           ats_audit.published_date v_audit_published_date,
           ats_issue.issue_id v_issue_id,
           ats_issue.issue_name issue_name, 
           ats_issue.rating_id v_rating_id,
           ats_rating.rating_name rating_name,
           ats_issue.published_by v_issue_published_by,
           ats_issue.published_date v_issue_published_date,
           ats_action.action_id v_action_id,
           ats_action.action_list_index action_list_index,
           ats_action.action_detail action_detail,
           ats_action.action_due_date action_due_date,
           ats_action.business_status_id v_business_status_id,
           ats_business_status.business_status_name business_status_name, 
           ats_business_status.action_status_id v_action_status_id,
           ats_action.action_closed_date action_closed_date,
           ats_action.published_by v_action_published_by,
           ats_action.published_date v_action_published_date,
           dbo.f_ats_action_last_comment(ats_action.action_id) action_last_comment
     * @param rowData
     * @return
     * @throws Exception 
     */
    private ActionDto convert(Object[] rowData) throws Exception
    {
        ActionDto action = new ActionDto();
        action.setIssue(new IssueDto());
        IssueDto issue = (IssueDto) action.getIssue();
        issue.setAudit(new AuditDto());
        AuditDto audit = (AuditDto) issue.getAudit();
        int i = 0;
        Object value;
        Object value2;
        //audit
        value = rowData[i++];
        audit.setAuditId(Formatter.createLong(value));
        value = rowData[i++];
        audit.setName(Formatter.formatJson((String) value, true));
        value = rowData[i++];
        ReportTypeDto reportType = new ReportTypeDto(Formatter.createLong(value));
        audit.setReportType(reportType);
        value = rowData[i++];
        audit.setIssuedDate((Date) value);
        value = rowData[i++];
        audit.setPublishedBy(new UserDto((String) value));
        value = rowData[i++];
        audit.setPublishedDate((Date) value);
        //issue
        value = rowData[i++];
        issue.setIssueId(Formatter.createLong(value));
        value = rowData[i++];
        issue.setName(Formatter.formatJson((String) value, true));
        value = rowData[i++];
        value2 = rowData[i++];
        RatingDto rating = new RatingDto(Formatter.createLong(value), (String) value2);
        issue.setRating(rating);
        value = rowData[i++];
        issue.setPublishedBy(new UserDto((String) value));
        value = rowData[i++];
        issue.setPublishedDate((Date) value);
        //action
        value = rowData[i++];
        action.setActionId(Formatter.createLong(value));
        value = rowData[i++];
        action.setListIndex(Formatter.createBigDecimal(value));
        value = rowData[i++];
        action.setDetail(Formatter.formatJson(IOUtils.toString(((Clob) value).getAsciiStream()),
            false));
        value = rowData[i++];
        action.setDueDate((Date) value);
        value = rowData[i++];
        value2 = rowData[i++];
        BusinessStatusDto businessStatus = new BusinessStatusDto(Formatter.createLong(value),
            (String) value2);
        value = rowData[i++];
        ActionStatusDto actionStatus = new ActionStatusDto(Formatter.createLong(value));
        businessStatus.setActionStatus(actionStatus);
        action.setBusinessStatus(businessStatus);
        value = rowData[i++];
        action.setClosedDate((Date) value);
        value = rowData[i++];
        action.setPublishedBy(new UserDto((String) value));
        value = rowData[i++];
        action.setPublishedDate((Date) value);
        // set overdue flag
        action.setOverDue(action.getDaysOverdue() > 0 && actionStatus.isOpen());
        // set comment (last)
        String lastComment = (String) rowData[i++];
        if (lastComment != null)
        {
            CommentDto comment = new CommentDto();
            comment.setDetail(Formatter.formatJson(lastComment, false));
            action.getComments().add(comment);
        }
        // set security (for update link)
        Action entity = actionDao.findById(action.getActionId());
        convertSecurity(action, entity.getResponsibleUsers(), entity.getGroupDivisions());
        //
        return action;
    }

    /**
     * 
     * @param dto
     * @param responsibleUsers
     * @param groupDivisions
     * @throws Exception
     */
    private void convertSecurity(ActionDto dto, List<IUser> responsibleUsers,
        List<ActionGroupDivision> groupDivisions) throws Exception
    {
        // by responsible user
        String loggedUserId = ThreadLocalUtils.getUser().getId();
        for (IUser user : responsibleUsers)
        {
            if (!dto.isEditable() && user.getId().equals(loggedUserId))
            {
                dto.setEditable(true);
            }
            dto.getResponsibleUsers().add(new UserDto(user));
        }
        //copy group/division(s)
        for (ActionGroupDivision item : groupDivisions)
        {
            GroupDivisionDto itemDto = new GroupDivisionDto();
            ConvertUtils.convert(item, itemDto);
            //copy group/division borms(s)
            List<User> borms = userDao.findByGroupDivision(item.getGroup(), item.getDivision(),
                new Long(UserTypeEnum.OWNER.ordinal()));
            for (User borm : borms)
            {
                UserDto bormDto = new UserDto(borm);
                if (!dto.getBormResponsibleUsers().contains(bormDto))
                {
                    dto.getBormResponsibleUsers().add(bormDto);
                }
            }
            dto.getGroupDivisions().add(itemDto);
            //
            UserMatrixDto userMatrixDto = new UserMatrixDto();
            userMatrixDto.setUser(new UserDto(loggedUserId));
            //userMatrixDto.setRole(null)); // any role
            userMatrixDto.setReportType(dto.getIssue().getAudit().getReportType());
            userMatrixDto.setGroup(itemDto.getGroup());
            userMatrixDto.setDivision(itemDto.getDivision());
            FunctionDto f = securityService.findUserFunction(userMatrixDto,
                IFunction.QUERY_ADD_COMMENT);
            if (!dto.isEditable() && f != null)
            {
                dto.setEditable(true); // by user security matrix
            }
        }
    }

}