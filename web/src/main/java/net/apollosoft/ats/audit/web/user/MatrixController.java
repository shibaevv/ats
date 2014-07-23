package net.apollosoft.ats.audit.web.user;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.service.UserService;
import net.apollosoft.ats.audit.validation.ValidationException;
import net.apollosoft.ats.audit.web.BaseController;
import net.apollosoft.ats.domain.dto.security.DivisionDto;
import net.apollosoft.ats.domain.dto.security.GroupDto;
import net.apollosoft.ats.domain.dto.security.RoleDto;
import net.apollosoft.ats.domain.dto.security.UserMatrixDto;
import net.apollosoft.ats.domain.refdata.IReportType;
import net.apollosoft.ats.domain.security.IUser;
import net.apollosoft.ats.domain.security.IUserMatrix;
import net.apollosoft.ats.search.IDir;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Controller
public class MatrixController extends BaseController
{

    private final UserService userService;

    @Autowired
    public MatrixController(@Qualifier("beanFactory") BeanFactory beanFactory,
        @Qualifier("userService") UserService userService)
    {
        super(beanFactory);
        this.userService = userService;
    }

    @RequestMapping(value = "/user/matrix/main.do", method = RequestMethod.GET)
    public String main(ModelMap model)
    {
        return "user/main";
    }

    @RequestMapping(value = "/user/matrix/main.do", params = "dispatch=find")
    public String find(@RequestParam(value = "user.userId", required = true) String userId,
        @RequestParam(value = "sort", required = false) String sort,
        @RequestParam(value = "dir", required = false) String dir, ModelMap model)
        throws ServiceException
    {
        //all groups
        List<GroupDto> groups = getEntityService().findAllGroup();
        groups.add(0, GroupDto.ALL);
        model.addAttribute("groups", groups);
        //dynamically retrieved
        model.addAttribute("divisions", Collections.EMPTY_LIST);
        //DO NOT USE: user can assign ONLY roles assigned to him (SECURITY feature)
        //List<RoleDto> roles = getSecurityService().findUserRoles();
        List<RoleDto> roles = getEntityService().findAllRole();
        roles.remove(RoleDto.DEFAULT);
        model.addAttribute("roles", roles);
        //
        List<IReportType> reportTypes = getEntityService().findAllReportType();
        model.addAttribute("reportTypes", reportTypes);

        //
        List<UserMatrixDto> entities = userService.findUserMatrix(userId);
        model.addAttribute("entities", entities);
        model.addAttribute("sort", IUserMatrix.ROLE);
        model.addAttribute("dir", IDir.ASC);
        return "user/matrix/find";
    }

    @RequestMapping(value = "/user/matrix/edit.do", params = "!dispatch")
    public String edit(@ModelAttribute("userMatrix") UserMatrixDto userMatrix,
        ModelMap model) throws ServiceException
    {
        UserMatrixDto entity = userService.findUserMatrixById(userMatrix.getId());
        if (entity == null)
        {
            //add new
            entity = new UserMatrixDto();
            IUser user = userService.findById(userMatrix.getUser().getId());
            entity.setUser(user);
        }
        else
        {
            //edit existing
            
        }
        model.addAttribute("entity", entity);

        List<GroupDto> groups = getEntityService().findAllGroup();
        groups.add(0, GroupDto.ALL);
        model.addAttribute("groups", groups);
        Long groupId = entity.getGroup().getId();
        if (groupId == null)
        {
            model.addAttribute("divisions", Collections.EMPTY_LIST);
        }
        else
        {
            List<DivisionDto> divisions = getEntityService().findDivisionByGroupId(groupId);
            //add ALL divisions first
            divisions.add(0, DivisionDto.ALL);
            model.addAttribute("divisions", divisions);
        }
        //
        List<RoleDto> roles = getEntityService().findAllRole();
        roles.remove(RoleDto.DEFAULT);
        model.addAttribute("roles", roles);
        //
        List<IReportType> reportTypes = getEntityService().findAllReportType();
        model.addAttribute("reportTypes", reportTypes);

        return "user/matrix/edit";
    }

    @RequestMapping(value = "/user/matrix/edit.do", params = "dispatch=save")
    public String save(@ModelAttribute("userMatrix") UserMatrixDto userMatrix,
        @RequestParam(value = "deleted", required = false) Boolean deleted,
        HttpServletResponse response, ModelMap model) throws ServiceException
    {
        try
        {
            if (Boolean.TRUE.equals(deleted))
            {
                userService.delete(userMatrix);
            }
            else
            {
                userService.save(userMatrix);
            }
            model.addAttribute("redirect", "user/view.do?user.userId=" + userMatrix.getUser().getId());
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