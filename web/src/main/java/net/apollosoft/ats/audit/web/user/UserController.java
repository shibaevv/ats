package net.apollosoft.ats.audit.web.user;

import java.util.Collections;
import java.util.List;

import net.apollosoft.ats.audit.search.UserSearchCriteria;
import net.apollosoft.ats.audit.security.UserPreferences;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.service.UserService;
import net.apollosoft.ats.audit.web.BaseController;
import net.apollosoft.ats.domain.dto.security.GroupDto;
import net.apollosoft.ats.domain.dto.security.UserDto;
import net.apollosoft.ats.domain.security.IUser;
import net.apollosoft.ats.search.Pagination;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Controller
public class UserController extends BaseController
{

    /** userService */
    private final UserService userService;

    @Autowired
    public UserController(@Qualifier("beanFactory") BeanFactory beanFactory,
        @Qualifier("userService") UserService userService)
    {
        super(beanFactory);
        this.userService = userService;
    }

    @RequestMapping(value = "/user/main.do", method = RequestMethod.GET)
    public String main(ModelMap model)
    {
        //update UserPreferences
        UserPreferences prefs = getUserPreferences();
        //filter (default values)
        //group/division
        model.addAttribute("searchUserId", prefs.getResponsibleUser().getId());
        model.addAttribute("searchUserName", prefs.getResponsibleUser().getName());
        Pagination p = prefs.getUserPagination();
        model.addAttribute("startIndex", p.getStartIndex());
        model.addAttribute("pageSize", p.getPageSize());
        model.addAttribute("sort", p.getSort());
        model.addAttribute("dir", p.getDir());
        return "user/main";
    }

    @RequestMapping(value = "/user/main.do", params = "dispatch=find", method = RequestMethod.GET)
    public String find(ModelMap model) throws ServiceException
    {
        UserPreferences prefs = getUserPreferences();
        //filter (collection values)
        //all groups
        List<GroupDto> groups = getEntityService().findAllGroup();
        groups.add(0, GroupDto.ALL);
        model.addAttribute("groups", groups);
        //entities (empty)
        model.addAttribute("entities", Collections.EMPTY_LIST);
        //pagination (defaults) - find.jsp
        model.addAttribute("recordsReturned", 0);
        model.addAttribute("totalRecords", 0);
        Pagination p = prefs.getUserPagination();
        model.addAttribute("startIndex", p.getStartIndex());
        model.addAttribute("pageSize", p.getPageSize());
        model.addAttribute("sort", p.getSort());
        model.addAttribute("dir", p.getDir());
        return "user/find";
    }

    @RequestMapping(value = "/user/main.do", params = "dispatch=find", method = RequestMethod.POST)
    public String find(
        @RequestParam(value = "searchUserId", required = false) String searchUserId,
        @RequestParam(value = "searchUserName", required = false) String searchUserName,
        @RequestParam(value = "startIndex", required = false) int startIndex,
        @RequestParam(value = "pageSize", required = false) int pageSize,
        @RequestParam(value = "sort", required = false) String sort,
        @RequestParam(value = "dir", required = false) String dir, ModelMap model)
        throws ServiceException
    {
        //update UserPreferences
        UserPreferences prefs = getUserPreferences();
        if (StringUtils.isBlank(searchUserName))
        {
            searchUserId = null;
            searchUserName = null;
        }
        prefs.getResponsibleUser().setId(searchUserId);
        prefs.getResponsibleUser().setName(searchUserName);
        //
        UserSearchCriteria criteria = new UserSearchCriteria();
        //pagination criteria
        Pagination pagination = new Pagination();
        pagination.setStartIndex(startIndex);
        pagination.setPageSize(pageSize);
        pagination.setSort(sort);
        pagination.setDir(dir);
        criteria.setPagination(pagination);
        //filter criteria
        criteria.setUserName(searchUserName);

        //
        List<UserDto> entities = userService.find(criteria);
        model.addAttribute("entities", entities);
        model.addAttribute("recordsReturned", entities.size());
        model.addAttribute("totalRecords", pagination.getTotalRecords());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("startIndex", pagination.getStartIndex());
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        return "user/find";
    }

    @RequestMapping(value = "/user/edit.do")
    public String edit(@RequestParam(value = "user.userId", required = false) String userId,
        ModelMap model) throws ServiceException
    {
        IUser entity = userService.findById(userId);
        model.addAttribute("entity", entity);
        model.addAttribute("user.userId", entity == null ? null : entity.getId());
        model.addAttribute("searchUserId", entity == null ? null : entity.getId());
        model.addAttribute("searchUserName", entity == null ? null : entity.getName());
        return "user/edit";
    }

    @RequestMapping(value = "/user/view.do", method = RequestMethod.GET)
    public String view(@RequestParam(value = "user.userId", required = false) String userId,
        ModelMap model) throws ServiceException
    {
        IUser entity = userService.findById(userId);
        model.addAttribute("entity", entity);
        model.addAttribute("user.userId", entity == null ? null : entity.getId());
        model.addAttribute("searchUserId", entity == null ? null : entity.getId());
        model.addAttribute("searchUserName", entity == null ? null : entity.getName());
        return "user/view";
    }

}