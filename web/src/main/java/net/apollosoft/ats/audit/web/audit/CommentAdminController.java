package net.apollosoft.ats.audit.web.audit;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.apollosoft.ats.audit.domain.dto.ActionDto;
import net.apollosoft.ats.audit.domain.dto.CommentDto;
import net.apollosoft.ats.audit.domain.dto.refdata.ActionStatusDto;
import net.apollosoft.ats.audit.domain.refdata.IBusinessStatus;
import net.apollosoft.ats.audit.service.ActionService;
import net.apollosoft.ats.audit.service.AuditService;
import net.apollosoft.ats.audit.service.CommentService;
import net.apollosoft.ats.audit.service.IssueService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.validation.ValidationException;
import net.apollosoft.ats.domain.dto.DocumentDto;
import net.apollosoft.ats.domain.dto.security.RoleDto;
import net.apollosoft.ats.util.ThreadLocalUtils;

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
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Controller
public class CommentAdminController extends AdminController
{

    @Autowired
    public CommentAdminController(@Qualifier("beanFactory") BeanFactory beanFactory,
        @Qualifier("auditService") AuditService auditService,
        @Qualifier("issueService") IssueService issueService,
        @Qualifier("actionService") ActionService actionService,
        @Qualifier("commentService") CommentService commentService)
    {
        super(beanFactory, auditService, issueService, actionService, commentService);
    }

    @RequestMapping(value = "/commentAdmin/edit.do", method = RequestMethod.GET)
    public String edit(@ModelAttribute("action") ActionDto action,
        @RequestParam(value = "redirect", required = false) String redirect, ModelMap model)
        throws ServiceException
    {
        action = getActionService().findById(action.getActionId());
        CommentDto entity = new CommentDto();
        entity.setAction(action);
        entity.setCreatedBy(getSecurityService().getUser());
        entity.setCreatedDate(ThreadLocalUtils.getDate());

        model.addAttribute("entity", entity);
        //TODO: move to service layer
        // set businessStatusEditable flag
        List<RoleDto> roles = getSecurityService().findUserRoles();
        IBusinessStatus businessStatus = action.getBusinessStatus();
        ActionStatusDto actionStatus = (ActionStatusDto) businessStatus.getActionStatus();
        boolean businessStatusEditable = actionStatus.isOpen()
            || roles.contains(RoleDto.REPORT_OWNER) || roles.contains(RoleDto.REPORT_TEAM);
        model.addAttribute("businessStatusEditable", businessStatusEditable);
        model.addAttribute("businessStatuses", getEntityService().findAllBusinessStatus());
        if (StringUtils.isNotBlank(redirect))
        {
            model.addAttribute("redirect", redirect);
        }

        return "auditAdmin/issue/action/comment/edit";
    }

    @RequestMapping(value = "/commentAdmin/edit.do", method = RequestMethod.POST)
    public String save(@ModelAttribute("action") ActionDto action,
        @RequestParam(value = "commentId", required = true) Long commentId,
        @RequestParam(value = "detail", required = true) String detail,
        @RequestParam(value = "attachments", required = false) MultipartFile[] attachments,
        HttpServletRequest request, HttpServletResponse response,
        //@ModelAttribute("comment") CommentDto entity, BindingResult result, SessionStatus status,
        ModelMap model)
        throws ServiceException
    {
        //TODO: use @ModelAttribute
        CommentDto comment = new CommentDto();
        comment.setCommentId(commentId);
        comment.setDetail(detail);
        //
        boolean isMultipart = request instanceof MultipartHttpServletRequest;
        if (isMultipart)
        {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> files = multiRequest.getFileMap();
            for (MultipartFile file : files.values())
            {
                DocumentDto d = new DocumentDto();
                try
                {
                    d.setContent(file.getBytes());
                }
                catch (IOException e)
                {
                    throw new ServiceException(e.getMessage(), e);
                }
                d.setContentType(file.getContentType());
                d.setName(file.getOriginalFilename());
                comment.add(d);
            }
        }

        try
        {
            getActionService().saveComment(action, comment);
            //model.addAttribute("redirect", "actionAdmin/edit.do?actionId=" + action.getActionId()); // default redirect = reload page
            return "common/redirect";
        }
        catch (ValidationException e)
        {
            //http://developer.yahoo.com/yui/connection/#upload
            if (!isMultipart)
            {
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
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