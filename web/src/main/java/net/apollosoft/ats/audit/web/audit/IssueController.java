package net.apollosoft.ats.audit.web.audit;

import java.util.List;

import net.apollosoft.ats.audit.domain.dto.ActionDto;
import net.apollosoft.ats.audit.domain.dto.IssueDto;
import net.apollosoft.ats.audit.search.ISortBy;
import net.apollosoft.ats.audit.service.ActionService;
import net.apollosoft.ats.audit.service.IssueService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.web.BaseController;
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
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Controller
public class IssueController extends BaseController
{

    /** issueService */
    private final IssueService issueService;

    /** actionService */
    private final ActionService actionService;

    @Autowired
    public IssueController(@Qualifier("beanFactory") BeanFactory beanFactory,
        @Qualifier("issueService") IssueService issueService,
        @Qualifier("actionService") ActionService actionService)
    {
        super(beanFactory);
        this.issueService = issueService;
        this.actionService = actionService;
    }

    @RequestMapping(value = "/issue/main.do", params = "dispatch=find")
    public String find(@RequestParam(value = "auditId", required = true) Long auditId,
        @ModelAttribute("pagination") Pagination pagination,
        ModelMap model) throws ServiceException
    {
        if (StringUtils.isBlank(pagination.getSort()))
        {
            pagination.setSort(ISortBy.ACTION_LIST_INDEX);
        }
        if (StringUtils.isBlank(pagination.getDir()))
        {
            pagination.setDir(IDir.ASC);
        }
        List<ActionDto> entities = actionService.findByAuditId(auditId, pagination, true);
        model.addAttribute("entities", entities);
        model.addAttribute("sort", pagination.getSort());
        model.addAttribute("dir", pagination.getDir());
        return "audit/issue/find";
    }

    @RequestMapping(value = "/issue/view.do")
    public String view(@RequestParam(value = "issueId", required = true) Long issueId,
        ModelMap model) throws ServiceException
    {
        IssueDto entity = issueService.findById(issueId);
        model.addAttribute("entity", entity);
        return "audit/issue/view";
    }

}