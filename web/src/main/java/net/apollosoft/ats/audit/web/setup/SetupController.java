package net.apollosoft.ats.audit.web.setup;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.apollosoft.ats.audit.domain.dto.refdata.BusinessStatusDto;
import net.apollosoft.ats.audit.domain.refdata.IBusinessStatus;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.validation.ValidationException;
import net.apollosoft.ats.audit.web.BaseController;
import net.apollosoft.ats.domain.dto.TemplateDto;
import net.apollosoft.ats.domain.dto.security.FunctionDto;
import net.apollosoft.ats.domain.dto.security.RoleDto;
import net.apollosoft.ats.web.WebUtils;

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
public class SetupController extends BaseController
{

    @Autowired
    public SetupController(@Qualifier("beanFactory") BeanFactory beanFactory)
    {
        super(beanFactory);
    }

    @RequestMapping(value = "/setup/main.do", params = "!dispatch")
    public String main(ModelMap model)
    {
        return "setup/main";
    }

    @RequestMapping(value = "/setup/main.do", params = "dispatch=find")
    public String find(ModelMap model) throws ServiceException
    {
        return "setup/find";
    }

    @RequestMapping(value = "/setup/businessStatus/main.do", params = "!dispatch")
    public String mainBusinessStatus(ModelMap model) throws ServiceException
    {
        return "setup/businessStatus/main";
    }

    @RequestMapping(value = "/setup/businessStatus/main.do", params = "dispatch=find")
    public String findBusinessStatus(ModelMap model) throws ServiceException
    {
        List<IBusinessStatus> entities = getEntityService().findAllBusinessStatus();
        model.addAttribute("entities", entities);
        //model.addAttribute("sort", sort);
        //model.addAttribute("dir", dir);
        return "setup/businessStatus/find";
    }

    @RequestMapping(value = "/setup/businessStatus/edit.do", method = RequestMethod.GET)
    public String editBusinessStatus(
        @RequestParam(value = "businessStatusId", required = false) Long businessStatusId,
        ModelMap model) throws ServiceException
    {
        BusinessStatusDto entity = getEntityService().findBusinessStatusById(businessStatusId);
        if (entity == null)
        {
            entity = new BusinessStatusDto();
        }
        model.addAttribute("entity", entity);
        return "setup/businessStatus/edit";
    }

    @RequestMapping(value = "/setup/businessStatus/edit.do", method = RequestMethod.POST)
    public String saveBusinessStatus(
        @ModelAttribute("businessStatus") BusinessStatusDto businessStatus,
        @RequestParam(value = "deleted", required = false) Boolean deleted,
        HttpServletResponse response, ModelMap model) throws ServiceException
    {
        try
        {
            if (deleted != null)
            {
                getEntityService().delete(businessStatus, deleted);
            }
            else
            {
                getEntityService().save(businessStatus);
            }
            model.addAttribute("redirect", "setup/businessStatus/main.do");
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

    @RequestMapping(value = "/setup/function/main.do", params = "!dispatch")
    public String mainFunction(ModelMap model) throws ServiceException
    {
        return "setup/function/main";
    }

    @RequestMapping(value = "/setup/function/main.do", params = "dispatch=find")
    public String findFunction(ModelMap model) throws ServiceException
    {
        List<FunctionDto> entities = getEntityService().findAllFunction();
        model.addAttribute("entities", entities);
        //model.addAttribute("sort", sort);
        //model.addAttribute("dir", dir);
        return "setup/function/find";
    }

    @RequestMapping(value = "/setup/function/edit.do", method = RequestMethod.GET)
    public String editFunction(
        @RequestParam(value = "functionId", required = false) Long functionId, ModelMap model)
        throws ServiceException
    {
        FunctionDto entity = getEntityService().findFunctionById(functionId);
        if (entity == null)
        {
            entity = new FunctionDto();
        }
        model.addAttribute("entity", entity);
        return "setup/function/edit";
    }

    @RequestMapping(value = "/setup/function/edit.do", method = RequestMethod.POST)
    public String saveFunction(@ModelAttribute("function") FunctionDto function,
        HttpServletResponse response, ModelMap model) throws ServiceException
    {
        try
        {
            getEntityService().save(function);
            model.addAttribute("redirect", "setup/function/main.do");
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

    @RequestMapping(value = "/setup/role/main.do", params = "!dispatch")
    public String mainRole(ModelMap model) throws ServiceException
    {
        return "setup/role/main";
    }

    @RequestMapping(value = "/setup/role/main.do", params = "dispatch=find")
    public String findRole(ModelMap model) throws ServiceException
    {
        List<RoleDto> entities = getEntityService().findAllRole();
        model.addAttribute("entities", entities);
        //model.addAttribute("sort", sort);
        //model.addAttribute("dir", dir);
        return "setup/role/find";
    }

    @RequestMapping(value = "/setup/role/edit.do", params = "!dispatch", method = RequestMethod.GET)
    public String editRole(@RequestParam(value = "roleId", required = true) Long roleId,
        ModelMap model) throws ServiceException
    {
        RoleDto entity = getEntityService().findRoleById(roleId);
        if (entity == null)
        {
            entity = new RoleDto();
        }
        model.addAttribute("entity", entity);
        return "setup/role/edit";
    }

    @RequestMapping(value = "/setup/role/edit.do", params = "dispatch=findFunction")
    public String findRoleFunction(@RequestParam(value = "roleId") Long roleId, ModelMap model)
        throws ServiceException
    {
        List<FunctionDto> entities = getEntityService().findFunctionByRoleId(roleId);
        model.addAttribute("entities", entities);
        return "setup/role/findFunction";
    }

    @RequestMapping(value = "/setup/role/edit.do", params = "!dispatch", method = RequestMethod.POST)
    public String saveRole(@ModelAttribute("role") RoleDto role,
        @RequestParam(value = "deleted", required = false) Boolean deleted,
        HttpServletResponse response, ModelMap model) throws ServiceException
    {
        try
        {
            if (deleted != null)
            {
                getEntityService().delete(role, deleted);
            }
            else
            {
                getEntityService().save(role);
            }
            model.addAttribute("redirect", "setup/role/main.do");
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

    @RequestMapping(value = "/setup/role/edit.do", params = "dispatch=saveFunction", method = RequestMethod.POST)
    public String saveRoleFunction(@ModelAttribute("role") RoleDto role,
        @ModelAttribute(value = "function") FunctionDto function, HttpServletResponse response,
        ModelMap model) throws ServiceException
    {
        try
        {
            getEntityService().saveFunction(role, function);
            model.addAttribute("redirect", "setup/role/edit.do?roleId=" + role.getRoleId());
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

    @RequestMapping(value = "/setup/template/main.do", params = "!dispatch")
    public String mainTemplate(ModelMap model) throws ServiceException
    {
        return "setup/template/main";
    }

    @RequestMapping(value = "/setup/template/main.do", params = "dispatch=find")
    public String findTemplate(ModelMap model) throws ServiceException
    {
        List<TemplateDto> entities = getEntityService().findAllTemplate();
        model.addAttribute("entities", entities);
        //model.addAttribute("sort", sort);
        //model.addAttribute("dir", dir);
        return "setup/template/find";
    }

    @RequestMapping(value = "/setup/template/main.do", params = "dispatch=download")
    public String downloadTemplate(
        @RequestParam(value = "templateId", required = true) Long templateId,
        HttpServletResponse response) throws ServiceException, IOException
    {
        TemplateDto entity = getEntityService().findTemplateById(templateId);
        WebUtils.downloadContent(response, entity.getContent(), entity.getContentType(), entity
            .getReference());
        return null;
    }

    @RequestMapping(value = "/setup/template/edit.do", method = RequestMethod.GET)
    public String editTemplate(
        @RequestParam(value = "templateId", required = false) Long templateId, ModelMap model)
        throws ServiceException
    {
        TemplateDto entity = getEntityService().findTemplateById(templateId);
        if (entity == null)
        {
            entity = new TemplateDto();
        }
        model.addAttribute("entity", entity);
        return "setup/template/edit";
    }

    @RequestMapping(value = "/setup/template/edit.do", method = RequestMethod.POST)
    public String saveTemplate(@ModelAttribute("template") TemplateDto template,
        @RequestParam(value = "attachment", required = false) MultipartFile attachment,
        HttpServletResponse response, ModelMap model) throws ServiceException
    {
        try
        {
            try
            {
                if (attachment == null)
                {
                    String contentText = template.getContentText();
                    template.setContent(StringUtils.isBlank(contentText) ? new byte[0] : contentText.getBytes());
                    //template.setContentType(StringUtils.isBlank(contentText) ? "plain/text" : template.getContentType());
                    //template.setName("?");
                }
                else
                {
                    template.setContent(attachment.getBytes());
                    template.setContentType(attachment.getContentType());
                    //template.setName(attachment.getOriginalFilename());
                }
            }
            catch (IOException e)
            {
                throw new ServiceException(e.getMessage(), e);
            }
            getEntityService().save(template);
            model.addAttribute("redirect", "setup/template/main.do");
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

}