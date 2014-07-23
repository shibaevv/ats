package net.apollosoft.ats.audit.web.audit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.apollosoft.ats.audit.domain.dto.ActionDto;
import net.apollosoft.ats.audit.domain.dto.AuditDto;
import net.apollosoft.ats.audit.domain.dto.IssueDto;
import net.apollosoft.ats.audit.domain.dto.refdata.ActionFollowupStatusDto;
import net.apollosoft.ats.audit.domain.dto.refdata.BusinessStatusDto;
import net.apollosoft.ats.audit.domain.refdata.IActionFollowupStatus;
import net.apollosoft.ats.audit.domain.refdata.IBusinessStatus;
import net.apollosoft.ats.audit.domain.refdata.IActionFollowupStatus.ActionFollowupStatusEnum;
import net.apollosoft.ats.audit.domain.refdata.IBusinessStatus.BusinessStatusEnum;
import net.apollosoft.ats.audit.service.ActionService;
import net.apollosoft.ats.audit.service.AuditService;
import net.apollosoft.ats.audit.service.CommentService;
import net.apollosoft.ats.audit.service.IssueService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.validation.ValidationException;
import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.dto.security.DivisionDto;
import net.apollosoft.ats.domain.dto.security.GroupDivisionDto;
import net.apollosoft.ats.domain.dto.security.GroupDto;
import net.apollosoft.ats.domain.dto.security.UserDto;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ActionAdminController extends AdminController
{

    @Autowired
    public ActionAdminController(@Qualifier("beanFactory") BeanFactory beanFactory,
        @Qualifier("auditService") AuditService auditService,
        @Qualifier("issueService") IssueService issueService,
        @Qualifier("actionService") ActionService actionService,
        @Qualifier("commentService") CommentService commentService)
    {
        super(beanFactory, auditService, issueService, actionService, commentService);
    }

    @RequestMapping(value = "/actionAdmin/edit.do", params = "!dispatch")
    public String edit(@RequestParam(value = "actionId") Long actionId, ModelMap model)
        throws ServiceException
    {
        ActionDto entity = (ActionDto) getActionService().findById(actionId);

        //all groups
        List<GroupDto> groups = getEntityService().findAllGroup();
        //groups.add(0, GroupDto.ALL);
        model.addAttribute("groups", groups);

        //all businessStatuses
        List<IBusinessStatus> businessStatuses = getEntityService().findAllBusinessStatus();
        model.addAttribute("businessStatuses", businessStatuses);

        //all followupStatuses
        List<IActionFollowupStatus> followupStatuses = getEntityService().findAllFollowupStatus();
        model.addAttribute("followupStatuses", followupStatuses);

        model.addAttribute("entity", entity);
        return "auditAdmin/issue/action/edit";
    }

    @RequestMapping(value = "/actionAdmin/edit.do", params = "dispatch=save")
    public String saveAudit(@ModelAttribute("audit") AuditDto audit, Long issueId, Long actionId,
        HttpServletResponse response, ModelMap model) throws ServiceException
    {
        try
        {
            doSaveAudit(audit);
            if (actionId == null)
            {
                model.addAttribute("redirect",
                    "actionAdmin/edit.do?readOnly=false&dispatch=addAction&issueId=" + issueId);
            }
            else
            {
                model.addAttribute("redirect", "actionAdmin/edit.do?readOnly=false&actionId="
                    + actionId);

            }
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

    @RequestMapping(value = "/actionAdmin/edit.do", params = "dispatch=saveClose")
    public String saveCloseAction(@RequestParam(value = "issueId", required = true) Long issueId,
        @ModelAttribute("actionForm") ActionDto action, HttpServletResponse response, ModelMap model)
        throws ServiceException
    {
        try
        {
            action.setValidate(true);
            doSave(issueId, action);
            Long auditId = action.getIssue().getAudit().getAuditId();

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

    @RequestMapping(value = "/actionAdmin/edit.do", params = "dispatch=cancel", method = RequestMethod.GET)
    public String cancelRequest(@RequestParam(value = "auditId", required = true) Long auditId,
        ModelMap model) throws ServiceException, DomainException
    {
        model.addAttribute("auditId", auditId);
        return "auditAdmin/issue/action/cancel";
    }

    @RequestMapping(value = "/actionAdmin/edit.do", params = "dispatch=cancel", method = RequestMethod.POST)
    public String cancelSubmit(@RequestParam(value = "auditId", required = false) Long auditId,
        HttpServletResponse response, ModelMap model) throws ServiceException, DomainException
    {
        if (auditId != null)
        {
            model.addAttribute("redirect", "auditAdmin/edit.do?auditId=" + auditId);
        }
        else
        {
            model.addAttribute("redirect", "auditAdmin/view.do?auditId=" + auditId);
        }

        return "common/redirect";

    }

    @RequestMapping(value = "/actionAdmin/edit.do", params = "dispatch=businessStatusChanged")
    public String businessStatusChanged(
        @RequestParam(value = "actionId", required = false) Long actionId,
        @RequestParam(value = "issueId", required = false) Long issueId,
        @RequestParam(value = "businessStatusId", required = false) Long businessStatusId,
        ModelMap model) throws ServiceException
    {
        ActionDto actionDto = getActionService().businessStatusChanged(actionId, issueId, businessStatusId);
        model.addAttribute("action", actionDto);
        return "auditAdmin/issue/action/businessStatusChanged";
    }


    //internal redirect (from java)
    @RequestMapping(value = "/actionAdmin/edit.do", params = "dispatch=addAction", method = RequestMethod.POST)
    public String addActionPost(@ModelAttribute("audit") AuditDto audit,
        HttpServletResponse response,
        @RequestParam(value = "issueId", required = false) Long issueId,
        @RequestParam(value = "actionId", required = false) Long actionId, ModelMap model)
        throws ServiceException
    {
        //save
        audit.setValidate(false);
        return saveAudit(audit, issueId, actionId, response, model);
    }

    @RequestMapping(value = "/actionAdmin/edit.do", params = "dispatch=addAction", method = RequestMethod.GET)
    public String addAction(@RequestParam(value = "issueId", required = true) Long issueId,
        HttpServletResponse response, ModelMap model) throws ServiceException
    {
        ActionDto entity = new ActionDto();

        // default values
        BusinessStatusDto businessStatus = new BusinessStatusDto();
        businessStatus.setBusinessStatusId(Long.valueOf(BusinessStatusEnum.NOT_ACTIONED.ordinal()));
        entity.setBusinessStatus(businessStatus);

        ActionFollowupStatusDto followupStatus = new ActionFollowupStatusDto();
        followupStatus.setActionFollowupStatusId(Long.valueOf(ActionFollowupStatusEnum.OPEN
            .ordinal()));
        entity.setFollowupStatus(followupStatus);

        IssueDto issue = getIssueService().findById(issueId);
        entity.setIssue(issue);

        //TODO: move to service layer
        getActionListIndex(issueId, entity, issue);

        //all groups
        List<GroupDto> groups = getEntityService().findAllGroup();
        //groups.add(0, GroupDto.ALL);
        model.addAttribute("groups", groups);

        //all businessStatuses
        List<IBusinessStatus> businessStatuses = getEntityService().findAllBusinessStatus();
        model.addAttribute("businessStatuses", businessStatuses);

        //all followupStatuses
        List<IActionFollowupStatus> followupStatuses = getEntityService().findAllFollowupStatus();
        model.addAttribute("followupStatuses", followupStatuses);

        model.addAttribute("entity", entity);
        return "auditAdmin/issue/action/edit";
    }

    private void getActionListIndex(Long issueId, ActionDto entity, IssueDto issue)
        throws ServiceException
    {
        List<ActionDto> actionList = getActionService().findByIssueId(issueId, null, null);
        Byte issueIndex = issue.getListIndex() == null ? 1 : issue.getListIndex();
        if (actionList.isEmpty())
        {
            entity.setListIndex(new BigDecimal(issueIndex + ".01"));
        }
        else
        {
            ActionDto lastAction = actionList.get(actionList.size() - 1);
            BigDecimal actionIndex = lastAction.getListIndex() == null ? BigDecimal.ONE
                : lastAction.getListIndex();
            actionIndex = actionIndex.multiply(new BigDecimal(100)).add(BigDecimal.ONE).divide(
                new BigDecimal(100), 2, RoundingMode.HALF_UP);
            entity.setListIndex(actionIndex);
        }
    }

    @RequestMapping(value = "/actionAdmin/delete.do", method = RequestMethod.GET)
    public String deleteActionRequest(@RequestParam(value = "actionId", required = true) Long actionId,
        ModelMap model) throws ServiceException, DomainException
    {
        ActionDto action = getActionService().findById(actionId);
        model.addAttribute("entity", action);
        return "auditAdmin/issue/action/delete";
    }

    @RequestMapping(value = "/actionAdmin/delete.do")
    public String deleteActionSubmit(@RequestParam(value = "actionId", required = true) Long actionId,
        HttpServletResponse response, ModelMap model) throws ServiceException
    {
        try
        {
            ActionDto action = (ActionDto) getActionService().findById(actionId);
            getActionService().delete(action);
            //Long auditId = action.getIssue().getAudit().getAuditId();
            //model.addAttribute("redirect", "auditAdmin/edit.do?auditId=" + auditId);
            //return "common/redirect";
            model.addAttribute("id", actionId);
            return "common/delete";
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

    @RequestMapping(value = "/actionAdmin/edit.do", params = "dispatch=saveAction")
    public String saveAction(@RequestParam(value = "issueId", required = true) Long issueId,
        @ModelAttribute("actionForm") ActionDto action, HttpServletResponse response, ModelMap model)
        throws ServiceException
    {
        try
        {
            if (action.getValidate() == null)
            {
                action.setValidate(true);
            }
            doSave(issueId, action);
            Long actionId = action.getActionId();

            model.addAttribute("redirect", "actionAdmin/edit.do?&actionId=" + actionId);

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

    private void doSave(Long issueId, ActionDto action) throws ServiceException,
        ValidationException
    {
        IssueDto issue = getIssueService().findById(issueId);
        action.setIssue(issue);
        action.getGroupDivisions().addAll(action.getGroupDivisions2());
        action.getResponsibleUsers().addAll(action.getResponsibleUsers2());
        //
        getActionService().save(action);
    }

    @RequestMapping(value = "/actionAdmin/edit.do", params = "dispatch=addPerson")
    public String addPersonResponsible(@ModelAttribute("actionForm") ActionDto action,
        @RequestParam(value = "userId", required = true) String userId,
        @RequestParam(value = "issueId") Long issueId, HttpServletResponse response, ModelMap model)
        throws ServiceException
    {
        try
        {
            UserDto userDto = new UserDto(userId);

            //
            IssueDto issue = getIssueService().findById(issueId);
            action.setIssue(issue);

            getActionService().addActionResponsible(action, userDto, true);

            //
            action.setValidate(false);
            String result = saveAction(issueId, action, response, model);

            //send an Email to new Person Responsible
            if (action.isPublished())
            {
                getActionService().sendEmailToPersonResponsible(userId, action.getActionId());
            }

            return result;
        }
        catch (ValidationException e)
        {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            model.addAttribute("errors", e.getErrors());
            return "common/error";
        }

    }

    @RequestMapping(value = "/actionAdmin/edit.do", params = "dispatch=removePerson")
    public String removePersonResponsible(@ModelAttribute("actionForm") ActionDto action,
        @RequestParam(value = "userId", required = true) String userId,
        @RequestParam(value = "issueId") Long issueId, HttpServletResponse response, ModelMap model)
        throws ServiceException
    {
        try
        {
            UserDto userDto = new UserDto(userId);

            //
            IssueDto issue = getIssueService().findById(issueId);
            action.setIssue(issue);

            getActionService().removeActionResponsible(action.getActionId(), userDto.getUserId());
            //
            action.setValidate(false);
            return saveAction(issueId, action, response, model);
        }
        catch (ValidationException e)
        {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            model.addAttribute("errors", e.getErrors());
            return "common/error";
        }

    }

    @RequestMapping(value = "/actionAdmin/edit.do", params = "dispatch=addGroupDivision")
    public String addGroupDivision(@ModelAttribute("actionForm") ActionDto action,
        @RequestParam(value = "issueId") Long issueId,
        @RequestParam(value = "groupId") Long groupId,
        @RequestParam(value = "divisionId") Long divisionId, HttpServletResponse response,
        ModelMap model) throws ServiceException
    {

        try
        {
            GroupDivisionDto groupDivisionDto = new GroupDivisionDto();
            groupDivisionDto.setGroup(new GroupDto(groupId));
            groupDivisionDto.setDivision(new DivisionDto(divisionId));
            //
            IssueDto issue = getIssueService().findById(issueId);
            action.setIssue(issue);

            getActionService().addGroupDivision(action, groupDivisionDto);
            action.getGroupDivisions().add(groupDivisionDto);

            //
            action.setValidate(false);
            return saveAction(issueId, action, response, model);
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

    @RequestMapping(value = "/actionAdmin/edit.do", params = "dispatch=removeGroupDivision")
    public String removeGroupDivision(@ModelAttribute("actionForm") ActionDto action,
        @RequestParam(value = "issueId") Long issueId,
        @RequestParam(value = "groupDivisionId") Long groupDivisionId,
        HttpServletResponse response, ModelMap model) throws ServiceException, ValidationException
    {
        getActionService().removeGroupDivision(action, groupDivisionId);
        action.setValidate(false);
        return saveAction(issueId, action, response, model);
    }

}