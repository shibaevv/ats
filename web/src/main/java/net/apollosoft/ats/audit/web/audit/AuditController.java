package net.apollosoft.ats.audit.web.audit;

import java.util.Collections;
import java.util.List;

import net.apollosoft.ats.audit.domain.IAudit;
import net.apollosoft.ats.audit.domain.dto.AuditDto;
import net.apollosoft.ats.audit.search.ReportSearchCriteria;
import net.apollosoft.ats.audit.security.UserPreferences;
import net.apollosoft.ats.audit.service.AuditService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.web.BaseController;
import net.apollosoft.ats.domain.dto.refdata.ReportTypeDto;
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
public class AuditController extends BaseController
{

    private final AuditService auditService;

    @Autowired
    public AuditController(@Qualifier("beanFactory") BeanFactory beanFactory,
        @Qualifier("auditService") AuditService auditService)
    {
        super(beanFactory);
        this.auditService = auditService;
    }

    @RequestMapping(value = "/audit/main.do", params = "!dispatch")
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
        addGroupDivisions(model, "auditGroupDivisions", "availableGroupDivisions");
        //
        model.addAttribute("issuedDateTo", Formatter.formatDate(prefs.getIssuedDateTo()));
        model.addAttribute("issuedDateFrom", Formatter.formatDate(prefs.getIssuedDateFrom()));
        //model.addAttribute("searchAuditId", prefs.getAudit().getId());
        model.addAttribute("searchAuditName", prefs.getAudit().getName());
        model.addAttribute("reportTypeId", prefs.getReportType().getId());
        model.addAttribute("reportTypeName", prefs.getReportType().getName());
        Pagination p = prefs.getAuditPagination();
        model.addAttribute("startIndex", p.getStartIndex());
        model.addAttribute("pageSize", p.getPageSize());
        model.addAttribute("sort", p.getSort());
        model.addAttribute("dir", p.getDir());
        model.addAttribute("initialLoad", true);
        return "audit/main";
    }

    @RequestMapping(value = "/audit/main.do", params = "dispatch=find", method = RequestMethod.GET)
    public String find(ModelMap model)
        throws ServiceException
    {
        //update UserPreferences
        UserPreferences prefs = getUserPreferences();
        //filter (collection values)
        //all reportTypes
        List<ReportTypeDto> reportTypes = getSecurityService().findUserReportTypes();
        reportTypes.add(0, ReportTypeDto.ALL);
        model.addAttribute("reportTypes", reportTypes);
        model.addAttribute("reportTypeId", prefs.getReportType().getId());

        return doFind(null, null, true, model);
    }

    @RequestMapping(value = "/audit/main.do", params = "dispatch=find", method = RequestMethod.POST)
    public String find(@ModelAttribute("criteria") ReportSearchCriteria criteria,
        @RequestParam(value = "reportTypeName", required = false) String reportTypeName,        
        ModelMap model)
        throws ServiceException
    {
        //set criteria
        criteria.setPublished(true);
        criteria.setAuditGroupDivision(true);
        //reset id, if no name selected
        if (StringUtils.isBlank(reportTypeName))
        {
            criteria.setReportTypeId(null);
        }

        //update UserPreferences
        UserPreferences prefs = getUserPreferences();
        prefs.setGroupDivisions(criteria.getAuditGroupDivisions());
        prefs.setIssuedDateTo(criteria.getIssuedDateTo());
        prefs.setIssuedDateFrom(criteria.getIssuedDateFrom());
        //prefs.getAudit().setId(criteria.getAuditId()); //can be partial search
        prefs.getAudit().setName(criteria.getSearchAuditName());
        prefs.getReportType().setId(criteria.getReportTypeId());
        prefs.getReportType().setName(reportTypeName);
        Pagination p = prefs.getAuditPagination();
        p.setSort(criteria.getSort());
        p.setDir(criteria.getDir());

        //LOG.info(ArrayUtils.toString(criteria.getAuditGroupDivisions()));
        if (ArrayUtils.isEmpty(criteria.getAvailableGroupDivisions()))
        {
            criteria.setAuditGroupDivisions(null); // ALL
        }

        return doFind(criteria, reportTypeName, false, model);
    }

    @SuppressWarnings("unchecked")
    private String doFind(
    	ReportSearchCriteria criteria,
        String reportTypeName,        
        boolean initialLoad,
        ModelMap model)
        throws ServiceException
    {
        UserPreferences prefs = getUserPreferences();
        Pagination p = prefs.getAuditPagination();
        List<AuditDto> entities = initialLoad ? Collections.EMPTY_LIST : auditService.find(criteria);
        model.addAttribute("entities", entities);
        model.addAttribute("recordsReturned", entities.size());
        model.addAttribute("totalRecords", criteria == null ? 0 : criteria.getTotalRecords());
        model.addAttribute("pageSize", criteria == null ? p.getPageSize() : criteria.getPageSize());
        model.addAttribute("startIndex", criteria == null ? p.getStartIndex() : criteria.getStartIndex());
        model.addAttribute("sort", criteria == null ? p.getSort() : criteria.getSort());
        model.addAttribute("dir", criteria == null ? p.getDir() : criteria.getDir());
        model.addAttribute("initialLoad", initialLoad);
        return "audit/find";
    }

    @RequestMapping(value = "/audit/view.do", method = RequestMethod.GET)
    public String view(@RequestParam(value = "auditId", required = true) Long auditId,
        ModelMap model) throws ServiceException
    {
        IAudit entity = auditService.findById(auditId);
        model.addAttribute("entity", entity);
        return "audit/view";
    }

}