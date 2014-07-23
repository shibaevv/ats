package net.apollosoft.ats.audit.web.audit;

import javax.servlet.http.HttpServletResponse;

import net.apollosoft.ats.audit.domain.IIssue;
import net.apollosoft.ats.audit.domain.dto.AuditDto;
import net.apollosoft.ats.audit.domain.dto.IssueDto;
import net.apollosoft.ats.audit.service.ActionService;
import net.apollosoft.ats.audit.service.AuditService;
import net.apollosoft.ats.audit.service.CommentService;
import net.apollosoft.ats.audit.service.IssueService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.validation.ValidationException;
import net.apollosoft.ats.domain.DomainException;

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
public class IssueAdminController extends AdminController
{

    @Autowired
    public IssueAdminController(@Qualifier("beanFactory") BeanFactory beanFactory,
        @Qualifier("auditService") AuditService auditService,
        @Qualifier("issueService") IssueService issueService,
        @Qualifier("actionService") ActionService actionService,
        @Qualifier("commentService") CommentService commentService)
    {
        super(beanFactory, auditService, issueService, actionService, commentService);
    }

    @RequestMapping(value = "/issueAdmin/edit.do", params = "dispatch=addIssue", method = RequestMethod.POST)
    public String addIssue(@ModelAttribute("audit") AuditDto audit, HttpServletResponse response,
        ModelMap model) throws ServiceException
    {
        //
        IssueDto issue = new IssueDto();
        //addIssue
        audit.add(issue);
        //save
        audit.setValidate(false);
        return save(audit, response, model);
    }

    @RequestMapping(value = "/issueAdmin/edit.do", params = "dispatch=save", method = RequestMethod.POST)
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

    @RequestMapping(value = "/issueAdmin/delete.do", method = RequestMethod.GET)
    public String deleteIssue(@RequestParam(value = "issueId", required = true) Long issueId,
        ModelMap model) throws ServiceException, DomainException
    {
        IIssue entity = getIssueService().findById(issueId);
        IssueDto issue = new IssueDto(entity);
        model.addAttribute("entity", issue);
        return "auditAdmin/issue/delete";
    }

    @RequestMapping(value = "/issueAdmin/delete.do", method = RequestMethod.POST)
    public String deleteIssue(@ModelAttribute("audit") AuditDto audit,
        @RequestParam(value = "issueId", required = true) Long issueId,
        HttpServletResponse response, ModelMap model) throws ServiceException, DomainException
    {
        try
        {
            IssueDto issue = getIssueService().findById(issueId);
            getIssueService().delete(issue);
            //Long auditId = issue.getAudit().getAuditId();
            //model.addAttribute("redirect", "auditAdmin/edit.do?auditId=" + auditId);
            //return "common/redirect";
            model.addAttribute("id", issueId);
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

}