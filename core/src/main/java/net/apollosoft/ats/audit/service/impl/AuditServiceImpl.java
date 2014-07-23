package net.apollosoft.ats.audit.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.apollosoft.ats.audit.dao.AuditDao;
import net.apollosoft.ats.audit.dao.AuditGroupDivisionDao;
import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.dao.DivisionDao;
import net.apollosoft.ats.audit.dao.DocumentDao;
import net.apollosoft.ats.audit.dao.GroupDao;
import net.apollosoft.ats.audit.dao.TemplateDao;
import net.apollosoft.ats.audit.dao.UserDao;
import net.apollosoft.ats.audit.dao.UserMatrixDao;
import net.apollosoft.ats.audit.domain.IAudit;
import net.apollosoft.ats.audit.domain.IIssue;
import net.apollosoft.ats.audit.domain.ITemplate;
import net.apollosoft.ats.audit.domain.dto.AuditDto;
import net.apollosoft.ats.audit.domain.dto.IssueDto;
import net.apollosoft.ats.audit.domain.dto.AuditDto.AuditDtoComparator;
import net.apollosoft.ats.audit.domain.hibernate.Action;
import net.apollosoft.ats.audit.domain.hibernate.ActionGroupDivision;
import net.apollosoft.ats.audit.domain.hibernate.Audit;
import net.apollosoft.ats.audit.domain.hibernate.AuditGroupDivision;
import net.apollosoft.ats.audit.domain.hibernate.Comment;
import net.apollosoft.ats.audit.domain.hibernate.Issue;
import net.apollosoft.ats.audit.domain.refdata.IBusinessStatus.BusinessStatusEnum;
import net.apollosoft.ats.audit.search.ReportSearchCriteria;
import net.apollosoft.ats.audit.service.ActionService;
import net.apollosoft.ats.audit.service.AuditService;
import net.apollosoft.ats.audit.service.DocumentService;
import net.apollosoft.ats.audit.service.IssueService;
import net.apollosoft.ats.audit.service.SecurityService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.util.ConvertUtils;
import net.apollosoft.ats.audit.validation.AuditGroupDivisionValidator;
import net.apollosoft.ats.audit.validation.AuditValidator;
import net.apollosoft.ats.audit.validation.ValidationException;
import net.apollosoft.ats.audit.validation.ValidationException.ValidationMessage;
import net.apollosoft.ats.domain.ContentTypeEnum;
import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.IDocument;
import net.apollosoft.ats.domain.dto.DocumentDto;
import net.apollosoft.ats.domain.dto.refdata.ReportTypeDto;
import net.apollosoft.ats.domain.dto.security.GroupDivisionDto;
import net.apollosoft.ats.domain.dto.security.UserDto;
import net.apollosoft.ats.domain.hibernate.Document;
import net.apollosoft.ats.domain.hibernate.Template;
import net.apollosoft.ats.domain.hibernate.security.Division;
import net.apollosoft.ats.domain.hibernate.security.Group;
import net.apollosoft.ats.domain.hibernate.security.User;
import net.apollosoft.ats.domain.hibernate.security.UserMatrix;
import net.apollosoft.ats.domain.security.IDivision;
import net.apollosoft.ats.domain.security.IGroup;
import net.apollosoft.ats.domain.security.IGroupDivision;
import net.apollosoft.ats.domain.security.IUser;
import net.apollosoft.ats.domain.security.IRole.RoleEnum;
import net.apollosoft.ats.search.Pagination;
import net.apollosoft.ats.util.BeanUtils;
import net.apollosoft.ats.util.Formatter;
import net.apollosoft.ats.util.ThreadLocalUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@SuppressWarnings("deprecation")
public class AuditServiceImpl implements AuditService
{

    /** logger. */
    //private static final Log LOG = LogFactory.getLog(AuditServiceImpl.class);

    @Autowired
    @Qualifier("auditDao")
    //@Resource(name="auditDao")
    private AuditDao auditDao;

    @Autowired
    @Qualifier("auditGroupDivisionDao")
    //@Resource(name="auditGroupDivisionDao")
    private AuditGroupDivisionDao auditGroupDivisionDao;

    @Autowired
    @Qualifier("divisionDao")
    //@Resource(name="divisionDao")
    private DivisionDao divisionDao;

    @Autowired
    @Qualifier("documentDao")
    //@Resource(name="documentDao")
    private DocumentDao documentDao;

    @Autowired
    @Qualifier("groupDao")
    //@Resource(name="groupDao")
    private GroupDao groupDao;

    @Autowired
    @Qualifier("templateDao")
    //@Resource(name="templateDao")
    private TemplateDao templateDao;

    @Autowired
    @Qualifier("userDao")
    //@Resource(name="userDao")
    private UserDao userDao;

    @Autowired
    @Qualifier("userMatrixDao")
    //@Resource(name="userMatrixDao")
    private UserMatrixDao userMatrixDao;

    @Autowired
    @Qualifier("actionService")
    //@Resource(name="actionService")
    private ActionService actionService;

    @Autowired
    @Qualifier("issueService")
    //@Resource(name="issueService")
    private IssueService issueService;

    @Autowired
    @Qualifier("documentService")
    //@Resource(name="documentService")
    private DocumentService documentService;

    @Autowired
    @Qualifier("securityService")
    //@Resource(name="securityService")
    private SecurityService securityService;

    @Autowired
    @Qualifier("auditValidator")
    //@Resource(name="auditValidator")
    private AuditValidator auditValidator;

    @Autowired
    @Qualifier("auditGroupDivisionValidator")
    //@Resource(name="auditGroupDivisionValidator")
    private AuditGroupDivisionValidator auditGroupDivisionValidator;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.AuditService#findNameLike(java.lang.String)
     */
    public List<AuditDto> findAuditByNameLike(String nameLike, Boolean published)
        throws ServiceException
    {
        try
        {
            List<Audit> entities = auditDao.findAuditByNameLike(nameLike, published);
            List<AuditDto> result = new ArrayList<AuditDto>();
            for (Audit entity : entities)
            {
                //only immutable properties required here (no collections)
                result.add(new AuditDto(entity));
            }
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.AuditService#find(net.apollosoft.ats.audit.search.ReportSearchCriteria)
     */
    public List<AuditDto> find(ReportSearchCriteria criteria) throws ServiceException
    {
        try
        {
            //due to complex sorting (action count) - do not sort/paginate in dao (sql)
            final Pagination p = criteria.getPagination();
            String sort = p.getSort();
            boolean javaSort = sort != null && Arrays.binarySearch(IAudit.JAVA_SORT, sort) >= 0;
            if (javaSort)
            {
                criteria.setPagination(null);
            }

            //use dto objects
            List<AuditDto> result = new ArrayList<AuditDto>();
            List<Object[]> tableData = auditDao.findByCriteria(criteria);
            for (Object[] audit : tableData)
            {
                result.add(convert(audit));
            }

            if (javaSort)
            {
                //do sort here (java)
                Collections.sort(result, new AuditDtoComparator(sort, p.getDir()));
                //update pagination
                p.setTotalRecords(result.size());
                if (p.getPageSize() > 0)
                {
                    int fromIndex = p.getStartIndex();
                    int toIndex = fromIndex + p.getPageSize();
                    toIndex = Math.min(toIndex, p.getTotalRecords());
                    if (fromIndex <= toIndex)
                    {
                        result = result.subList(fromIndex, toIndex);
                    }
                }
                criteria.setPagination(p);
            }
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.AuditService#delete(net.apollosoft.ats.audit.domain.dto.AuditDto)
     */
    public void delete(AuditDto entity) throws ServiceException, ValidationException
    {
        try
        {
            Audit audit = auditDao.findById(entity.getAuditId());
            auditDao.delete(audit);
            for (IIssue issue : audit.getIssues())
            {
                IssueDto issueDto = new IssueDto(issue);
                issueService.delete(issueDto);
            }
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.AuditService#publish(net.apollosoft.ats.audit.domain.dto.AuditDto)
     */
    public void publish(AuditDto auditDto) throws ServiceException, ValidationException
    {
        Long auditId = auditDto.getId();
        //validate audit, issue(s) and action(s) first
        auditDto = findById(auditId); // get data required for validation
        List<ValidationMessage> errors = auditValidator.validate(auditDto);
        if (!errors.isEmpty())
        {
            throw new ValidationException(errors);
        }
        
        try
        {
            //STEP1 - generate email body
            Audit audit = auditDao.findById(auditId);

            Set<IUser> responsibleUsers = new HashSet<IUser>();

            //STEP2 - set publish info
            User user = userDao.findById(ThreadLocalUtils.getUser().getId());
            Date currentDate = ThreadLocalUtils.getDate();
            boolean issuePublished = false;
            List<Action> actions = new ArrayList<Action>();
            List<IIssue> issues = audit.getIssues();
            for (IIssue issue : issues)
            {
                boolean actionPublished = false;
                for (int i = 0; i < issue.getActions().size(); i++)
                {
                    Action action = (Action) issue.getActions().get(i);
                    if (!action.isPublished())
                    {
                        action.setPublishedBy(user);
                        action.setPublishedDate(currentDate);

                        // write audit log
                        Template template = templateDao.findByReference(ITemplate.A001);
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("action", action);
                        ByteArrayOutputStream output = new ByteArrayOutputStream();
                        documentService.createDocument(template, params, output);

                        Comment auditLog = new Comment();
                        auditLog.setDetail(output.toString());
                        action.addLog(auditLog);

                        actionPublished = true;
                        responsibleUsers.addAll(action.getResponsibleUsers());
                        // no notification when action is already closed
                        if (BusinessStatusEnum.IMPLEMENTED.ordinal() != action.getBusinessStatus()
                            .getId())
                        {
                            actions.add(action);
                        }
                    }
                }
                if (actionPublished)
                {
                    Issue entity = (Issue) issue;
                    entity.setPublishedBy(user);
                    entity.setPublishedDate(currentDate);
                    issuePublished = true;
                }
            }
            if (issuePublished || issues.isEmpty()) // we can publish reports without issues/actions
            {
                audit.setPublishedBy(user);
                audit.setPublishedDate(currentDate);
            }

            // sending eMails
            Template r001 = templateDao.findByReference(ITemplate.R001);
            Template r002 = templateDao.findByReference(ITemplate.R002);

            //responsible users
            Map<IUser, Set<Action>> ruActions = new Hashtable<IUser, Set<Action>>();
            for (IUser to : responsibleUsers)
            {
                ruActions.put(to, new HashSet<Action>());
                for (Action item : actions)
                {
                    if (item.getResponsibleUsers().contains(to))
                    {
                        ruActions.get(to).add(item);
                    }
                }
            }
            if (!ruActions.isEmpty())
            {
                actionService.sendEmail(r001, ruActions);
            }

            //relevant oversight users
            List<UserMatrix> userMatrix = userMatrixDao.findByRoleId(new Long(
                RoleEnum.OVERSIGHT_TEAM.ordinal()));
            //prepare map of oversight users
            Map<IUser, Set<Action>> ouActions = new Hashtable<IUser, Set<Action>>();
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
                actionService.sendEmail(r002, ouActions);
            }
        }
        catch (DaoException e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.AuditService#addGroupDivision(net.apollosoft.ats.audit.domain.hibernate.Audit, java.util.List)
     */
    public void addGroupDivision(Audit audit, Set<GroupDivisionDto> newItems)
        throws ServiceException
    {
        try
        {
            //all current action responsible users
            List<AuditGroupDivision> linkItems = audit.getGroupDivisions();
            for (AuditGroupDivision linkItem : linkItems)
            {
                GroupDivisionDto item = new GroupDivisionDto();
                item.setGroup(linkItem.getGroup());
                item.setDivision(linkItem.getDivision());
                if (newItems.contains(item))
                {
                    if (linkItem.isDeleted())
                    {
                        auditDao.unDelete(linkItem);
                    }
                }
                else
                {
                    //logically delete
                    auditDao.delete(linkItem);
                }
            }
            //add new
            for (GroupDivisionDto item : newItems)
            {
                if (!linkItems.contains(item))
                {
                    AuditGroupDivision linkItem = new AuditGroupDivision();
                    linkItem.setAudit(audit);
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
     * @see net.apollosoft.ats.audit.service.AuditService#addGroupDivision(net.apollosoft.ats.audit.domain.dto.AuditDto, net.apollosoft.ats.audit.domain.dto.security.GroupDivisionDto)
     */
    public void addGroupDivision(AuditDto auditDto, GroupDivisionDto groupDivisionDto)
        throws ServiceException, ValidationException
    {
        AuditGroupDivision auditGroupDivision = new AuditGroupDivision();
        try
        {
            BeanUtils.copyProperties(groupDivisionDto, auditGroupDivision, IGroupDivision.IGNORE);
            Audit audit = auditDao.findById(auditDto.getId());
            if (audit == null)
            {
                //auditDto.setValidate(false);
                save(auditDto);
                audit = auditDao.findById(auditDto.getId());
            }
            auditGroupDivision.setAudit(audit);
            auditGroupDivision.setGroup(new Group(groupDivisionDto.getGroup().getGroupId()));
            //
            Division division = null;
            if (groupDivisionDto.getDivision().getDivisionId() != null)
            {
                division = new Division(groupDivisionDto.getDivision().getDivisionId());
            }
            auditGroupDivision.setDivision(division);

            //validation
            List<ValidationMessage> errors = auditGroupDivisionValidator
                .validate(auditGroupDivision);
            if (!errors.isEmpty())
            {
                throw new ValidationException(errors);
            }
            auditGroupDivisionDao.save(auditGroupDivision);
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

    public void save(AuditDto dto) throws ServiceException, ValidationException
    {
        if (Boolean.TRUE.equals(dto.getValidate()))
        {
            List<ValidationMessage> errors = auditValidator.validate(dto);
            if (!errors.isEmpty())
            {
                throw new ValidationException(errors);
            }
        }

        try
        {
            Audit entity = auditDao.findById(dto.getAuditId());
            if (entity == null)
            {
                 entity = new Audit();
            }

            //copy base properties
            ConvertUtils.convert(dto, entity);
            ConvertUtils.convert(dto.getIssues(), entity);

            auditDao.save(entity);
            dto.setAuditId(entity.getAuditId());
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.AuditService#export(net.apollosoft.ats.audit.search.ReportSearchCriteria, java.io.OutputStream)
     */
    public void export(ReportSearchCriteria criteria, OutputStream output, ContentTypeEnum contentType)
        throws ServiceException
    {
        try
        {
            List<Object[]> result = auditDao.findExport(criteria);

            if (ContentTypeEnum.TEXT_CSV.equals(contentType))
            {
                documentService.exportCsv(result, output);
            }
            else if (ContentTypeEnum.APPLICATION_EXCEL.equals(contentType))
            {
                documentService.exportExcel(result, output);
            }
            else
            {
                throw new ServiceException("Unhandled contentType: [" + contentType + "]");
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
     * @see net.apollosoft.ats.audit.service.AuditService#findById(java.lang.Long)
     */
    public AuditDto findById(Long auditId) throws ServiceException
    {
        try
        {
            Audit entity = auditDao.findById(auditId);
            //check every single action within report (TODO: improve)
            securityService.checkSecurity(entity);
            AuditDto result = convert(entity);
            //detailed view
            ConvertUtils.convertPublishable(entity, result);
            //copy document (plain)
            if (entity.getDocument() != null)
            {
                result.setDocument(new DocumentDto(entity.getDocument()));
            }
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

    /**
     * 
     * @param entity
     * @return
     * @throws DomainException
     * @throws DaoException 
     */
    private AuditDto convert(Audit entity) throws DomainException, DaoException
    {
        if (entity == null)
        {
            return null;
        }
        //copy plain properties
        AuditDto result = new AuditDto(entity);
        //copy group/division(s)
        for (IGroupDivision item : entity.getGroupDivisions())
        {
            GroupDivisionDto itemDto = new GroupDivisionDto();
            ConvertUtils.convert(item, itemDto);
            result.getGroupDivisions().add(itemDto);
        }
        //calculate aggregated values
        Integer actionOpen = auditDao.countActionOpen(entity.getId());
        result.setActionOpen(actionOpen);
        Integer actionFollow = auditDao.countActionTotal(entity.getId());
        result.setActionTotal(actionFollow);
        //
        Integer unpublishedIssues = auditDao.countIssueUnpublished(entity.getId());
        result.setIssueUnpublished(unpublishedIssues);
        Integer unpublishedActions = auditDao.countActionUnpublished(entity.getId());
        result.setActionUnpublished(unpublishedActions);
        //
        return result;
    }

    /**
           a.audit_id v_audit_id,
           a.audit_name audit_name,
           a.report_type_id v_report_type_id,
           a.audit_issued_date audit_issued_date,
           a.updated_date audit_updated_date,
           a.published_by v_audit_published_by,
           a.published_date v_audit_published_date,
           dbo.f_ats_audit_group_division(a.audit_id) audit_group_division,
           dbo.f_ats_audit_actionOpen(a.audit_id) audit_actionOpen,
           dbo.f_ats_audit_actionTotal(a.audit_id) audit_actionTotal,
           dbo.f_ats_audit_issueUnpublished(a.audit_id) audit_issueUnpublished,
           dbo.f_ats_audit_actionUnpublished(a.audit_id) audit_actionUnpublished
     * 
     * @param rowData
     * @return
     * @throws Exception
     */
    private AuditDto convert(Object[] rowData) throws Exception
    {
        //copy plain properties
        AuditDto audit = new AuditDto();
        int i = 0;
        Object value;
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
        audit.setUpdatedDate((Date) value);
        value = rowData[i++];
        audit.setPublishedBy(new UserDto((String) value));
        value = rowData[i++];
        audit.setPublishedDate((Date) value);
        value = rowData[i++];
        audit.setGroupDivisionAll((String) value);
        value = rowData[i++];
        audit.setActionOpen(Formatter.createInteger(value));
        value = rowData[i++];
        audit.setActionTotal(Formatter.createInteger(value));
        value = rowData[i++];
        audit.setIssueUnpublished(Formatter.createInteger(value));
        value = rowData[i++];
        audit.setActionUnpublished(Formatter.createInteger(value));
        //
        return audit;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.AuditService#addAttachment(net.apollosoft.ats.audit.domain.dto.AuditDto, net.apollosoft.ats.audit.domain.dto.DocumentDto)
     */
    public void addAttachment(AuditDto audit, DocumentDto d) throws ServiceException,
        ValidationException
    {
        Document document = new Document();
        try
        {
            BeanUtils.copyProperties(d, document, IDocument.IGNORE);
            document.setContent(d.getContent());
            documentDao.save(document);
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void removeGroupDivision(AuditDto audit, Long groupDivisionId) throws ServiceException,
        ValidationException
    {
        try
        {
            AuditGroupDivision auditGroupDivision = auditGroupDivisionDao.findById(groupDivisionId);
            auditGroupDivisionDao.delete(auditGroupDivision);
            List<ValidationMessage> errors = auditGroupDivisionValidator
                .validate(auditGroupDivision);
            if (!errors.isEmpty())
            {
                throw new ValidationException(errors);
            }
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

    public void removeAttachment(AuditDto audit) throws ServiceException, ValidationException
    {
        try
        {
            Audit entity = auditDao.findById(audit.getAuditId());
            entity.setDocument(null);
            auditDao.save(entity);
        }
        catch (DaoException e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}