package net.apollosoft.ats.audit.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.dao.IssueDao;
import net.apollosoft.ats.audit.domain.IAction;
import net.apollosoft.ats.audit.domain.dto.ActionDto;
import net.apollosoft.ats.audit.domain.dto.AuditDto;
import net.apollosoft.ats.audit.domain.dto.IssueDto;
import net.apollosoft.ats.audit.domain.hibernate.Issue;
import net.apollosoft.ats.audit.search.IssueSearchCriteria;
import net.apollosoft.ats.audit.service.ActionService;
import net.apollosoft.ats.audit.service.IssueService;
import net.apollosoft.ats.audit.service.SecurityService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.util.ConvertUtils;
import net.apollosoft.ats.audit.validation.ValidationException;
import net.apollosoft.ats.domain.DomainException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class IssueServiceImpl implements IssueService
{

    /** logger. */
    //private static final Log LOG = LogFactory.getLog(IssueServiceImpl.class);

    @Autowired
    @Qualifier("issueDao")
    //@Resource(name="issueDao")
    private IssueDao issueDao;

    @Autowired
    @Qualifier("actionService")
    //@Resource(name="actionService")
    private ActionService actionService;

    @Autowired
    @Qualifier("securityService")
    //@Resource(name="securityService")
    private SecurityService securityService;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.IssueService#find(net.apollosoft.ats.audit.search.IssueSearchCriteria)
     */
    public List<IssueDto> find(IssueSearchCriteria criteria) throws ServiceException
    {
        try
        {
            List<Issue> entities = issueDao.findByCriteria(criteria);
            //use dto objects
            List<IssueDto> result = new ArrayList<IssueDto>();
            for (Issue entity : entities)
            {
                result.add(convert(entity));
            }
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.IssueService#delete(net.apollosoft.ats.audit.domain.dto.IssueDto)
     */
    public void delete(IssueDto issueDto) throws ServiceException, ValidationException
    {
        try
        {
            Issue issue = issueDao.findById(issueDto.getIssueId());
            issueDao.delete(issue);
            for (IAction action : issue.getActions()) // delete the children
            {
                ActionDto actionDto = new ActionDto(action);
                actionService.delete(actionDto);
            }
        }
        catch (DaoException e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
        catch (DomainException e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.IssueService#findById(java.lang.Long)
     */
    public IssueDto findById(Long issueId) throws ServiceException
    {
        try
        {
            Issue entity = issueDao.findById(issueId);
            //check every single action within issue (TODO: improve)
            securityService.checkSecurity(entity);
            IssueDto result = convert(entity);
            //
            return result;
        }
        catch (net.apollosoft.ats.domain.security.SecurityException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.IssueService#findByAuditId(java.lang.Long, java.lang.Boolean)
     */
    public List<IssueDto> findByAuditId(Long auditId, Boolean published) throws ServiceException
    {
        try
        {
            //
            List<Issue> entities = issueDao.findByAuditId(auditId, published);
            //use dto objects
            List<IssueDto> result = new ArrayList<IssueDto>();
            for (Issue issue : entities)
            {
                IssueDto issueDto = convert(issue);
                result.add(issueDto);
            }
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 
     * @param entity
     * @return
     * @throws DomainException
     * @throws DaoException 
     */
    private IssueDto convert(Issue entity) throws DomainException, DaoException
    {
        //copy plain properties
        IssueDto result = new IssueDto(entity);
        ConvertUtils.convertPublishable(entity, result);
        //set parent
        AuditDto audit = new AuditDto(entity.getAudit());
        result.setAudit(audit);
        //calculate aggregated values
        Integer actionOpen = issueDao.countActionOpen(entity.getId());
        result.setActionOpen(actionOpen);
        Integer actionFollow = issueDao.countActionTotal(entity.getId());
        result.setActionTotal(actionFollow);
        return result;
    }

}