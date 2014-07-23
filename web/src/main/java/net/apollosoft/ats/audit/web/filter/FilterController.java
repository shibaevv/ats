package net.apollosoft.ats.audit.web.filter;

import java.util.List;

import net.apollosoft.ats.audit.domain.dto.AuditDto;
import net.apollosoft.ats.audit.domain.dto.refdata.ParentRiskDto;
import net.apollosoft.ats.audit.service.AuditService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.service.UserService;
import net.apollosoft.ats.audit.web.BaseController;
import net.apollosoft.ats.domain.dto.security.DivisionDto;
import net.apollosoft.ats.domain.security.IUser;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Controller
public class FilterController extends BaseController
{

    /** auditService */
    private final AuditService auditService;

    /** userService */
    private final UserService userService;

    @Autowired
    public FilterController(@Qualifier("beanFactory") BeanFactory beanFactory,
        @Qualifier("auditService") AuditService auditService,
        @Qualifier("userService") UserService userService)
    {
        super(beanFactory);
        this.auditService = auditService;
        this.userService = userService;
    }

    //{"name":"auditName","label":"Name","valuesUrl":"filter/audit/name.do"}
    @RequestMapping(value = "/filter/audit/name.do")
    public String auditName(@RequestParam(value = "searchAuditName", required = false) String searchAuditName,
        @RequestParam(value = "published", required = false) Boolean published, ModelMap model)
        throws ServiceException
    {
        List<AuditDto> entities = auditService.findAuditByNameLike(searchAuditName, published);
        model.addAttribute("entities", entities);
        return "filter/label_value";
    }

    //{"name":"userName","label":"User Name","valuesUrl":"filter/userName.do"}, eg [a Köb]
    @RequestMapping(value = "/filter/userName.do")
    public String userName(
        @RequestParam(value = "searchUserName", required = false) String searchUserName,
        @RequestParam(value = "activeUser", required = false) Boolean activeUser,
        @RequestParam(value = "query", required = false) String query, ModelMap model)
        throws ServiceException
    {
        if (StringUtils.isBlank(searchUserName))
        {
            searchUserName = query;
        }
        List<IUser> entities = userService.findUserNameLike(searchUserName, activeUser);
        model.addAttribute("entities", entities);
        return "filter/label_value";
    }

    //{"name":"division","label":"Division","valuesUrl":"filter/division.do"},
    @RequestMapping(value = "/filter/division.do")
    public String division(@RequestParam(value = "group.groupId", required = false) Long groupId,
        @RequestParam(value = "group.name", required = false) String groupName, ModelMap model)
        throws ServiceException
    {
        if (StringUtils.isBlank(groupName))
        {
            groupId = null;
            groupName = null;
        }
        List<DivisionDto> entities = getEntityService().findDivisionByGroupId(groupId);
        //add ALL divisions first
        entities.add(0, DivisionDto.ALL);
        model.addAttribute("entities", entities);
        return "filter/label_value";
    }

    @RequestMapping(value = "/filter/parentRisk.do", params = "dispatch=category")
    public String parentRiskCategory(
        @RequestParam(value = "categoryId", required = false) Long categoryId,
        @RequestParam(value = "categoryName", required = false) String categoryName,
        @RequestParam(value = "index", required = false) Long index, ModelMap model)
        throws ServiceException
    {
        if (StringUtils.isBlank(categoryName))
        {
            categoryId = null;
            categoryName = null;
        }
        List<ParentRiskDto> entities = getEntityService().findParentRiskByCategoryId(categoryId);
        model.addAttribute("entities", entities);
        return "filter/label_value";
    }

    @RequestMapping(value = "/filter/parentRisk.do", params = "dispatch=risk")
    public String parentRisk(@RequestParam(value = "riskId", required = false) Long riskId,
        @RequestParam(value = "riskName", required = false) String riskName,
        @RequestParam(value = "index", required = false) Long index, ModelMap model)
        throws ServiceException
    {
        if (riskId == null)
        {
            model.addAttribute("entity", null);
        }
        else
        {
            ParentRiskDto entity = getEntityService().findParentRiskById(riskId);
            model.addAttribute("entity", entity.getCategory().getName());
        }
        return "filter/label";
    }

}