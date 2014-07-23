package net.apollosoft.ats.audit.web.audit;

import java.util.Collections;
import java.util.List;

import net.apollosoft.ats.audit.domain.dto.ActionDto;
import net.apollosoft.ats.audit.domain.refdata.IActionStatus.ActionStatusEnum;
import net.apollosoft.ats.audit.search.ISortBy;
import net.apollosoft.ats.audit.search.ReportSearchCriteria;
import net.apollosoft.ats.audit.security.UserPreferences;
import net.apollosoft.ats.audit.service.ActionService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.web.BaseController;
import net.apollosoft.ats.search.IDir;
import net.apollosoft.ats.util.ThreadLocalUtils;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Controller
public class MyActionController extends BaseController
{

    /** actionService */
    private final ActionService actionService;

    @Autowired
    public MyActionController(@Qualifier("beanFactory") BeanFactory beanFactory,
        @Qualifier("actionService") ActionService actionService)
    {
        super(beanFactory);
        this.actionService = actionService;
    }

    @RequestMapping(value = "/myaction/main.do", params = "!dispatch", method = RequestMethod.GET)
    public String main(ModelMap model)
    {
        //update UserPreferences
        UserPreferences prefs = getUserPreferences();
        Long actionStatusId = prefs.getMyActionStatus().getId();
        model.addAttribute("actionStatusId", actionStatusId);
        boolean isClosed = actionStatusId != null && actionStatusId.intValue() == ActionStatusEnum.CLOSED.ordinal();
        model.addAttribute("sort", isClosed ? ISortBy.ACTION_CLOSED_DATE_SQL : ISortBy.ACTION_DUE_DATE_SQL);
        model.addAttribute("dir", isClosed ? IDir.DESC : IDir.ASC);
        model.addAttribute("initialLoad", true);
        return "myaction/main";
    }

    @RequestMapping(value = "/myaction/main.do", params = "dispatch=find", method = RequestMethod.GET)
    public String findGet(@ModelAttribute("criteria") ReportSearchCriteria criteria,
        ModelMap model)
        throws ServiceException
    {
        return doFind(criteria, true, model);
    }

    @RequestMapping(value = "/myaction/main.do", params = "dispatch=find", method = RequestMethod.POST)
    public String findPost(@ModelAttribute("criteria") ReportSearchCriteria criteria,
        ModelMap model)
        throws ServiceException
    {
        //set criteria
        criteria.setAuditGroupDivision(false);
        criteria.setMyaction(true);
        criteria.getResponsibleUser().setId(ThreadLocalUtils.getUser().getId());

        //update UserPreferences
        UserPreferences prefs = getUserPreferences();
        prefs.getMyActionStatus().setId(criteria.getActionStatusId());

        return doFind(criteria, false, model);
    }

    @SuppressWarnings("unchecked")
    private String doFind(ReportSearchCriteria criteria,
        boolean initialLoad,
        ModelMap model)
        throws ServiceException
    {
        List<ActionDto> entities = initialLoad ? Collections.EMPTY_LIST : actionService.find(criteria);
        model.addAttribute("entities", entities);
        Long actionStatusId = criteria.getActionStatusId();
        model.addAttribute("actionStatusId", actionStatusId);
        boolean isClosed = actionStatusId != null && actionStatusId.intValue() == ActionStatusEnum.CLOSED.ordinal();
        model.addAttribute("sort", isClosed ? ISortBy.ACTION_CLOSED_DATE_SQL : ISortBy.ACTION_DUE_DATE_SQL);
        model.addAttribute("dir", isClosed ? IDir.DESC : IDir.ASC);
        model.addAttribute("initialLoad", initialLoad);
        return "myaction/find";
    }

}