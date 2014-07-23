package net.apollosoft.ats.audit.web.audit;

import java.util.Collections;
import java.util.List;

import net.apollosoft.ats.audit.domain.dto.ActionDto;
import net.apollosoft.ats.audit.domain.dto.refdata.BusinessStatusDto;
import net.apollosoft.ats.audit.domain.dto.refdata.RatingDto;
import net.apollosoft.ats.audit.domain.refdata.IBusinessStatus;
import net.apollosoft.ats.audit.domain.refdata.IRating;
import net.apollosoft.ats.audit.search.ISortBy;
import net.apollosoft.ats.audit.search.ReportSearchCriteria;
import net.apollosoft.ats.audit.security.UserPreferences;
import net.apollosoft.ats.audit.service.ActionService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.web.BaseController;
import net.apollosoft.ats.domain.dto.refdata.ReportTypeDto;
import net.apollosoft.ats.search.IDir;
import net.apollosoft.ats.search.Pagination;
import net.apollosoft.ats.util.Formatter;

import org.apache.commons.lang.ArrayUtils;
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


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Controller
public class ActionController extends BaseController
{

    /** actionService */
    private final ActionService actionService;

    @Autowired
    public ActionController(@Qualifier("beanFactory") BeanFactory beanFactory,
        @Qualifier("actionService") ActionService actionService)
    {
        super(beanFactory);
        this.actionService = actionService;
    }

    @RequestMapping(value = "/action/main.do", params = "!dispatch")
    public String main(@RequestParam(value = "reset", required = false) Boolean reset,
        ModelMap model) throws ServiceException
    {
        //update UserPreferences
        UserPreferences prefs = getUserPreferences();
        //filter (default values)
        if (Boolean.TRUE.equals(reset))
        {
            prefs.reset();
        }
        //
        addGroupDivisions(model, "actionGroupDivisions", "availableGroupDivisions");
        //
        model.addAttribute("dueDateTo", Formatter.formatDate(prefs.getDueDateTo()));
        model.addAttribute("dueDateFrom", Formatter.formatDate(prefs.getDueDateFrom()));
        model.addAttribute("closedDateTo", Formatter.formatDate(prefs.getClosedDateTo()));
        model.addAttribute("closedDateFrom", Formatter.formatDate(prefs.getClosedDateFrom()));
        //model.addAttribute("searchAuditId", prefs.getAudit().getId());
        model.addAttribute("searchAuditName", prefs.getAudit().getName());
        model.addAttribute("searchUserId", prefs.getResponsibleUser().getId());
        model.addAttribute("searchUserName", prefs.getResponsibleUser().getName());
        model.addAttribute("actionStatusId", prefs.getActionStatus().getId());
        model.addAttribute("businessStatusId", prefs.getBusinessStatus().getId());
        model.addAttribute("businessStatusName", prefs.getBusinessStatus().getName());
        model.addAttribute("ratingId", prefs.getRating().getId());
        model.addAttribute("ratingName", prefs.getRating().getName());
        model.addAttribute("reportTypeId", prefs.getReportType().getId());
        model.addAttribute("reportTypeName", prefs.getReportType().getName());
        Pagination p = prefs.getActionPagination();
        model.addAttribute("startIndex", p.getStartIndex());
        model.addAttribute("pageSize", p.getPageSize());
        model.addAttribute("sort", p.getSort());
        model.addAttribute("dir", p.getDir());
        model.addAttribute("initialLoad", true);
        return "audit/issue/action/main";
    }

    @RequestMapping(value = "/action/main.do", params = "dispatch=find", method = RequestMethod.GET)
    public String find(ModelMap model) throws ServiceException
    {
        //update UserPreferences
        UserPreferences prefs = getUserPreferences();
        //filter (collection values)
        //all businessStatuses
        List<IBusinessStatus> businessStatuses = getEntityService().findAllBusinessStatus();
        businessStatuses.add(0, BusinessStatusDto.ALL);
        model.addAttribute("businessStatuses", businessStatuses);
        model.addAttribute("businessStatusId", prefs.getBusinessStatus().getId());
        //all ratings
        List<IRating> ratings = getEntityService().findAllRating();
        ratings.add(0, RatingDto.ALL);
        model.addAttribute("ratings", ratings);
        model.addAttribute("ratingId", prefs.getRating().getId());
        //all reportTypes
        List<ReportTypeDto> reportTypes = getSecurityService().findUserReportTypes();
        reportTypes.add(0, ReportTypeDto.ALL);
        model.addAttribute("reportTypes", reportTypes);
        model.addAttribute("reportTypeId", prefs.getReportType().getId());

        return doFind(null, null, null, null, null, true, model);
    }

    @RequestMapping(value = "/action/main.do", params = "dispatch=find", method = RequestMethod.POST)
    public String find(@ModelAttribute("criteria") ReportSearchCriteria criteria,
        @RequestParam(value = "reportTypeName", required = false) String reportTypeName,
        @RequestParam(value = "searchUserName", required = false) String searchUserName,
        @RequestParam(value = "businessStatusName", required = false) String businessStatusName,
        @RequestParam(value = "ratingName", required = false) String ratingName,
        ModelMap model)
        throws ServiceException
    {
        //set criteria
        criteria.setAuditGroupDivision(false);
        //reset id, if no name selected
        if (StringUtils.isBlank(reportTypeName))
        {
            criteria.setReportTypeId(null);
        }
        if (StringUtils.isBlank(searchUserName))
        {
            criteria.getResponsibleUser().setId(null);
        }
        if (StringUtils.isBlank(businessStatusName))
        {
            criteria.setBusinessStatusId(null);
        }
        if (StringUtils.isBlank(ratingName))
        {
            criteria.setRatingId(null);
        }
        criteria.setPublished(true);

        //update UserPreferences
        UserPreferences prefs = getUserPreferences();
        prefs.setGroupDivisions(criteria.getActionGroupDivisions());
        prefs.setDueDateTo(criteria.getDueDateTo());
        prefs.setDueDateFrom(criteria.getDueDateFrom());
        prefs.setClosedDateTo(criteria.getClosedDateTo());
        prefs.setClosedDateFrom(criteria.getClosedDateFrom());
        //prefs.getAudit().setId(criteria.getAuditId()); //can be partial search
        prefs.getAudit().setName(criteria.getSearchAuditName());
        prefs.getReportType().setId(criteria.getReportTypeId());
        prefs.getReportType().setName(reportTypeName);
        prefs.getResponsibleUser().setId(criteria.getResponsibleUser().getId());
        prefs.getResponsibleUser().setName(searchUserName);
        prefs.getBusinessStatus().setId(criteria.getBusinessStatusId());
        prefs.getBusinessStatus().setName(businessStatusName);
        prefs.getActionStatus().setId(criteria.getActionStatusId());
        prefs.getRating().setId(criteria.getRatingId());
        prefs.getRating().setName(ratingName);
        Pagination p = prefs.getActionPagination();
        p.setSort(criteria.getSort());
        p.setDir(criteria.getDir());

        //LOG.info(ArrayUtils.toString(criteria.getActionGroupDivisions()));
        if (ArrayUtils.isEmpty(criteria.getAvailableGroupDivisions()))
        {
            criteria.setActionGroupDivisions(null); // ALL
        }

        return doFind(criteria, reportTypeName, searchUserName, businessStatusName,
            ratingName, false, model);
    }

    @SuppressWarnings("unchecked")
    private String doFind(ReportSearchCriteria criteria, String reportTypeName,
        String responsibleUserName, String businessStatusName, String ratingName,
        boolean initialLoad, ModelMap model) throws ServiceException
    {
        UserPreferences prefs = getUserPreferences();
        Pagination p = prefs.getActionPagination();
        List<ActionDto> entities = initialLoad ? Collections.EMPTY_LIST : actionService.find(criteria);
        model.addAttribute("entities", entities);
        model.addAttribute("recordsReturned", entities.size());
        model.addAttribute("totalRecords", criteria == null ? 0 : criteria.getTotalRecords());
        model.addAttribute("pageSize", criteria == null ? p.getPageSize() : criteria.getPageSize());
        model.addAttribute("startIndex", criteria == null ? p.getStartIndex() : criteria.getStartIndex());
        model.addAttribute("sort", criteria == null ? p.getSort() : criteria.getSort());
        model.addAttribute("dir", criteria == null ? p.getDir() : criteria.getDir());
        model.addAttribute("initialLoad", initialLoad);
        return "audit/issue/action/find";
    }

    @RequestMapping(value = "/action/main.do", params = "dispatch=findByIssue")
    public String findByIssue(@RequestParam(value = "issueId", required = true) Long issueId,
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
        List<ActionDto> entities = actionService.findByIssueId(issueId, pagination, true);
        model.addAttribute("entities", entities);
        model.addAttribute("sort", pagination.getSort());
        model.addAttribute("dir", pagination.getDir());
        return "audit/issue/action/findByIssue";
    }

    @RequestMapping(value = "/action/view.do", method = RequestMethod.GET)
    public String view(@RequestParam(value = "actionId") Long actionId, ModelMap model)
        throws ServiceException
    {
        ActionDto entity = actionService.findById(actionId);
        model.addAttribute("entity", entity);
        return "audit/issue/action/view";
    }

}