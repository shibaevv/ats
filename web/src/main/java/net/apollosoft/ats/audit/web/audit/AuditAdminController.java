package net.apollosoft.ats.audit.web.audit;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.apollosoft.ats.audit.domain.dto.ActionDto;
import net.apollosoft.ats.audit.domain.dto.AuditDto;
import net.apollosoft.ats.audit.domain.dto.IssueDto;
import net.apollosoft.ats.audit.domain.dto.refdata.ParentRiskDto;
import net.apollosoft.ats.audit.domain.refdata.IRating;
import net.apollosoft.ats.audit.search.ISortBy;
import net.apollosoft.ats.audit.search.IssueSearchCriteria;
import net.apollosoft.ats.audit.search.ReportSearchCriteria;
import net.apollosoft.ats.audit.security.UserPreferences;
import net.apollosoft.ats.audit.service.ActionService;
import net.apollosoft.ats.audit.service.AuditService;
import net.apollosoft.ats.audit.service.CommentService;
import net.apollosoft.ats.audit.service.IssueService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.validation.ValidationException;
import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.dto.DocumentDto;
import net.apollosoft.ats.domain.dto.refdata.ReportTypeDto;
import net.apollosoft.ats.domain.dto.security.DivisionDto;
import net.apollosoft.ats.domain.dto.security.GroupDivisionDto;
import net.apollosoft.ats.domain.dto.security.GroupDto;
import net.apollosoft.ats.search.IDir;
import net.apollosoft.ats.search.Pagination;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Controller
public class AuditAdminController extends AdminController
{

    @Autowired
    public AuditAdminController(@Qualifier("beanFactory") BeanFactory beanFactory,
        @Qualifier("auditService") AuditService auditService,
        @Qualifier("issueService") IssueService issueService,
        @Qualifier("actionService") ActionService actionService,
        @Qualifier("commentService") CommentService commentService)
    {
        super(beanFactory, auditService, issueService, actionService, commentService);
    }

    @RequestMapping(value = "/auditAdmin/main.do", params = "!dispatch")
    public String main(ModelMap model) throws ServiceException
    {
        //update UserPreferences
        UserPreferences prefs = getUserPreferences();
        //filter (default values)
        //group/division
        model.addAttribute("reportTypeId", prefs.getReportType().getId());
        model.addAttribute("reportTypeName", prefs.getReportType().getName());
        Pagination p = prefs.getAuditAdminPagination();
        model.addAttribute("sort", p.getSort());
        model.addAttribute("dir", p.getDir());
        return "auditAdmin/main";
    }

    @RequestMapping(value = "/auditAdmin/main.do", params = "dispatch=find")
    public String find(@ModelAttribute("criteria") ReportSearchCriteria criteria,
        @RequestParam(value = "reportTypeId", required = false) Long reportTypeId,
        @RequestParam(value = "reportTypeName", required = false) String reportTypeName,
        ModelMap model) throws ServiceException
    {
        //update UserPreferences
        UserPreferences prefs = getUserPreferences();
        //filter (collection values)

        //all reportTypes
        List<ReportTypeDto> reportTypes = getSecurityService().findUserReportTypes();
        reportTypes.add(0, ReportTypeDto.ALL);
        model.addAttribute("reportTypes", reportTypes);

        //update UserPreferences
        //UserPreferences prefs = getUserPreferences();
        if (StringUtils.isBlank(reportTypeName))
        {
            reportTypeId = null;
            reportTypeName = null;
        }
        prefs.getRating().setId(reportTypeId);
        prefs.getRating().setName(reportTypeName);

        //filter criteria        
        criteria.setReportTypeId(reportTypeId);
        criteria.setPublished(false);

        List<AuditDto> entities = getAuditService().find(criteria);
        model.addAttribute("entities", entities);
        model.addAttribute("sort", criteria.getSort());
        model.addAttribute("dir", criteria.getDir());
        return "auditAdmin/find";
    }

    @RequestMapping(value = "/auditAdmin/view.do")
    public String view(@RequestParam(value = "auditId", required = true) Long auditId,
        ModelMap model) throws ServiceException
    {
        AuditDto entity = getAuditService().findById(auditId);
        initIssues(entity);
        model.addAttribute("entity", entity);
        return "auditAdmin/view";
    }

    @RequestMapping(value = "/auditAdmin/edit.do", params = "!dispatch")
    public String edit(@RequestParam(value = "auditId", required = false) Long auditId,
        ModelMap model) throws ServiceException
    {
        //all groups
        List<GroupDto> groups = getEntityService().findAllGroup();
        //groups.add(0, GroupDto.ALL);
        model.addAttribute("groups", groups);

        //all reportTypes
        List<ReportTypeDto> reportTypes = getSecurityService().findUserReportTypes();
        model.addAttribute("reportTypes", reportTypes);
        //all ratings
        List<IRating> ratings = getEntityService().findAllRating();
        model.addAttribute("ratings", ratings);
        //
        List<ParentRiskDto> parentRisks = getEntityService().findAllParentRisks();
        model.addAttribute("parentRisks", parentRisks);

        AuditDto entity;
        if (auditId == null)
        {
            entity = new AuditDto();
        }
        else
        {
            entity = getAuditService().findById(auditId);
            initIssues(entity);
        }
        model.addAttribute("entity", entity);
        return "auditAdmin/edit";
    }

    @RequestMapping(value = "/auditAdmin/edit.do", params = "dispatch=addAttachment")
    public String addAttachment(@ModelAttribute("audit") AuditDto audit,
        @RequestParam(value = "attachment", required = false) MultipartFile attachment,
        HttpServletResponse response, ModelMap model) throws ServiceException
    {
        //addAttachment
        DocumentDto d = new DocumentDto();
        try
        {
            d.setContentType(attachment.getContentType());
            d.setName(attachment.getOriginalFilename());
            d.setContent(attachment.getBytes());
            audit.setDocument(d);
            getAuditService().addAttachment(audit, d);

            audit.setValidate(false);
            return save(audit, response, model);
        }
        catch (IOException e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
        catch (ValidationException e)
        {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            model.addAttribute("errors", e.getErrors());
            return "common/error";
        }
    }

    @RequestMapping(value = "/auditAdmin/edit.do", params = "dispatch=removeAttachment")
    public String removeAttachment(@ModelAttribute("audit") AuditDto audit,
        HttpServletResponse response, ModelMap model) throws ServiceException, ValidationException
    {
        //removeAttachment
        audit.setDocument(null);
        audit.setValidate(false);
        getAuditService().removeAttachment(audit);
        //save
        return save(audit, response, model);
    }

    ///
    @RequestMapping(value = "/auditAdmin/edit.do", params = "dispatch=addGroupDivision")
    public String addGroupDivision(@ModelAttribute("audit") AuditDto audit,
        @RequestParam(value = "groupId") Long groupId,
        @RequestParam(value = "divisionId") Long divisionId, HttpServletResponse response,
        ModelMap model) throws ServiceException, ValidationException
    {
        try
        {
            GroupDivisionDto groupDivisionDto = new GroupDivisionDto();

            groupDivisionDto.setGroup(new GroupDto(groupId));
            groupDivisionDto.setDivision(new DivisionDto(divisionId));

            audit.getGroupDivisions().add(groupDivisionDto);
            getAuditService().addGroupDivision(audit, groupDivisionDto);
            //save
            audit.setValidate(false);
            return save(audit, response, model);
        }
        catch (ValidationException e)
        {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            model.addAttribute("errors", e.getErrors());
            return "common/error";
        }
        catch (ServiceException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw e;
        }
    }

    @RequestMapping(value = "/auditAdmin/edit.do", params = "dispatch=removeGroupDivision")
    public String removeGroupDivision(@ModelAttribute("audit") AuditDto audit,
        @RequestParam(value = "groupDivisionId") Long groupDivisionId,
        HttpServletResponse response, ModelMap model) throws ServiceException, ValidationException
    {
        getAuditService().removeGroupDivision(audit, groupDivisionId);
        audit.setValidate(false);
        return save(audit, response, model);
    }

    @RequestMapping(value = "/auditAdmin/edit.do", params = "dispatch=save")
    public String save(@ModelAttribute("audit") AuditDto audit, HttpServletResponse response,
        ModelMap model) throws ServiceException
    {
        try
        {
            if (audit.getValidate() == null)
            {
                audit.setValidate(true);
            }
            doSaveAudit(audit);
            Long auditId = audit.getAuditId(); //never be null
            model.addAttribute("redirect", "auditAdmin/edit.do?auditId=" + auditId);
            return "common/redirect";
        }
        catch (ValidationException e)
        {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            model.addAttribute("errors", e.getErrors());
            return "common/error";
        }
        catch (ServiceException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw e;
        }
    }

    @RequestMapping(value = "/auditAdmin/edit.do", params = "dispatch=saveClose")
    public String saveClose(@ModelAttribute("audit") AuditDto audit, HttpServletResponse response,
        ModelMap model) throws ServiceException
    {
        try
        {
            if (audit.getValidate() == null)
            {
                audit.setValidate(true);
            }
            doSaveAudit(audit);
            Long auditId = audit.getAuditId(); //never be null
            model.addAttribute("redirect", "auditAdmin/view.do?auditId=" + auditId);
            return "common/redirect";
        }
        catch (ValidationException e)
        {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            model.addAttribute("errors", e.getErrors());
            return "common/error";
        }
        catch (ServiceException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw e;
        }
    }

    @RequestMapping(value = "/auditAdmin/edit.do", params = "dispatch=publish", method = RequestMethod.GET)
    public String publishRequest(@RequestParam(value = "auditId", required = true) Long auditId,
        ModelMap model) throws ServiceException, DomainException
    {
        AuditDto entity = getAuditService().findById(auditId);
        model.addAttribute("entity", entity);
        return "auditAdmin/publish";
    }

    @RequestMapping(value = "/auditAdmin/edit.do", params = "dispatch=publish", method = RequestMethod.POST)
    public String publishSubmit(@ModelAttribute("audit") AuditDto audit, HttpServletResponse response,
        ModelMap model) throws ServiceException, ValidationException
    {
        try
        {
            getAuditService().publish(audit);
            model.addAttribute("redirect", "auditAdmin/view.do?auditId=" + audit.getId());
            return "common/redirect";
        }
        catch (ValidationException e)
        {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            model.addAttribute("errors", e.getErrors());
            return "common/error";
        }
    }

    @RequestMapping(value = "/auditAdmin/delete.do", method = RequestMethod.GET)
    public String deleteRequest(@RequestParam(value = "auditId", required = true) Long auditId,
        ModelMap model) throws ServiceException, DomainException
    {
        AuditDto entity = getAuditService().findById(auditId);
        model.addAttribute("entity", entity);
        return "auditAdmin/delete";
    }

    @RequestMapping(value = "/auditAdmin/delete.do", method = RequestMethod.POST)
    public String deleteSubmit(@ModelAttribute("audit") AuditDto audit,
        @RequestParam(value = "auditId", required = true) Long auditId,
        HttpServletResponse response, ModelMap model) throws ServiceException, DomainException
    {
        try
        {
            AuditDto entity = getAuditService().findById(auditId);
            getAuditService().delete(entity);
            model.addAttribute("redirect", "auditAdmin/main.do");
            return "common/redirect";
        }
        catch (ValidationException e)
        {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            model.addAttribute("errors", e.getErrors());
            return "common/error";
        }
        catch (ServiceException e)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw e;
        }
    }

    @RequestMapping(value = "/auditAdmin/edit.do", params = "dispatch=cancel", method = RequestMethod.GET)
    public String cancelRequest(@RequestParam(value = "auditId", required = true) Long auditId,
        ModelMap model) throws ServiceException, DomainException
    {
        model.addAttribute("auditId", auditId);
        return "auditAdmin/cancel";
    }

    @RequestMapping(value = "/auditAdmin/edit.do", params = "dispatch=cancel", method = RequestMethod.POST)
    public String cancelSubmit(@RequestParam(value = "auditId", required = false) Long auditId,
        HttpServletResponse response, ModelMap model) throws ServiceException, DomainException
    {
        if (auditId != null)
        {
            model.addAttribute("redirect", "auditAdmin/view.do?auditId=" + auditId);
        }
        else
        {
            model.addAttribute("redirect", "auditAdmin/main.do");
        }

        return "common/redirect";

    }

    /**
     * Helper method to initialise issue/action collections for this UI
     * @param audit
     * @throws ServiceException
     */
    private void initIssues(AuditDto audit) throws ServiceException
    {
        //
        IssueSearchCriteria issueCriteria = new IssueSearchCriteria();
        Pagination issuePagination = new Pagination();
        issuePagination.setSort(ISortBy.ISSUE_LIST_INDEX);
        issuePagination.setDir(IDir.ASC);
        issueCriteria.setPagination(issuePagination);
        issueCriteria.setAuditId(audit.getId());

        //
        List<IssueDto> issues = getIssueService().find(issueCriteria);
        for (IssueDto issue : issues)
        {
            List<ActionDto> actions = getActionService().findByIssueId(issue.getIssueId(), null, null);
            if (issue.getActions().isEmpty())
            {
                IssueDto issueDto = (IssueDto) issue;
                for (ActionDto action : actions)
                {
                    issueDto.getActions().add(action);
                }
            }
            audit.getIssues().add(issue);
        }
    }

}