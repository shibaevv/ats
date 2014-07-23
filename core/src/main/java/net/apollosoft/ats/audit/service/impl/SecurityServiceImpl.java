package net.apollosoft.ats.audit.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.dao.GroupDao;
import net.apollosoft.ats.audit.dao.UserMatrixDao;
import net.apollosoft.ats.audit.dao.security.RoleDao;
import net.apollosoft.ats.audit.domain.IAction;
import net.apollosoft.ats.audit.domain.IIssue;
import net.apollosoft.ats.audit.domain.hibernate.Action;
import net.apollosoft.ats.audit.domain.hibernate.ActionGroupDivision;
import net.apollosoft.ats.audit.domain.hibernate.ActionResponsible;
import net.apollosoft.ats.audit.domain.hibernate.Audit;
import net.apollosoft.ats.audit.domain.hibernate.AuditGroupDivision;
import net.apollosoft.ats.audit.domain.hibernate.Issue;
import net.apollosoft.ats.audit.service.SecurityService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.service.UserService;
import net.apollosoft.ats.audit.util.ConvertUtils;
import net.apollosoft.ats.domain.dto.refdata.ReportTypeDto;
import net.apollosoft.ats.domain.dto.security.FunctionDto;
import net.apollosoft.ats.domain.dto.security.GroupDivisionDto;
import net.apollosoft.ats.domain.dto.security.GroupDto;
import net.apollosoft.ats.domain.dto.security.RoleDto;
import net.apollosoft.ats.domain.dto.security.UserDto;
import net.apollosoft.ats.domain.dto.security.UserMatrixDto;
import net.apollosoft.ats.domain.hibernate.refdata.ReportType;
import net.apollosoft.ats.domain.hibernate.security.Division;
import net.apollosoft.ats.domain.hibernate.security.Group;
import net.apollosoft.ats.domain.hibernate.security.GroupDivision;
import net.apollosoft.ats.domain.hibernate.security.Role;
import net.apollosoft.ats.domain.hibernate.security.UserMatrix;
import net.apollosoft.ats.domain.refdata.IReportType;
import net.apollosoft.ats.domain.security.IDivision;
import net.apollosoft.ats.domain.security.IFunction;
import net.apollosoft.ats.domain.security.IGroup;
import net.apollosoft.ats.domain.security.IRole;
import net.apollosoft.ats.domain.security.IRole.RoleEnum;
import net.apollosoft.ats.util.ThreadLocalUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class SecurityServiceImpl implements SecurityService
{

    /** logger. */
    private static final Log LOG = LogFactory.getLog(SecurityServiceImpl.class);

    @Autowired
    @Qualifier("groupDao")
    //@Resource(name="groupDao")
    private GroupDao groupDao;

    @Autowired
    @Qualifier("roleDao")
    //@Resource(name="roleDao")
    private RoleDao roleDao;

    @Autowired
    @Qualifier("userMatrixDao")
    //@Resource(name="userMatrixDao")
    private UserMatrixDao userMatrixDao;

    @Autowired
    @Qualifier("userService")
    //@Resource(name="userService")
    private UserService userService;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.SecurityService#getUser()
     */
    public UserDto getUser() throws ServiceException
    {
        try
        {
            String userId = ThreadLocalUtils.getUser() == null ? null : ThreadLocalUtils.getUser()
                .getId();
            UserDto result = userService.findById(userId);
            if (result == null)
            {
                result = UserDto.SYSTEM;   
            }
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.SecurityService#getUserHome()
     */
    public IFunction getUserHome() throws ServiceException
    {
        try
        {
            String userId = ThreadLocalUtils.getUser().getId();
            return userService.findUserHome(userId);
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.SecurityService#findUserReportTypes()
     */
    public List<ReportTypeDto> findUserReportTypes() throws ServiceException
    {
        try
        {
            String userId = ThreadLocalUtils.getUser().getId();
            List<ReportType> entities = userMatrixDao.findReportTypes(userId);
            List<ReportTypeDto> result = new ArrayList<ReportTypeDto>();
            for (ReportType entity : entities)
            {
                result.add(new ReportTypeDto(entity));
            }
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.SecurityService#findUserRoles()
     */
    public List<RoleDto> findUserRoles() throws ServiceException
    {
        try
        {
            String userId = ThreadLocalUtils.getUser().getId();
            try
            {
                List<Role> entities = userMatrixDao.findRoles(userId);
                // DEFAULT role - by default exists for all users
                Role defaultRole = roleDao.findById(new Long(RoleEnum.DEFAULT.ordinal()));
                if (!entities.contains(defaultRole))
                {
                    entities.add(defaultRole);
                }
                //
                List<RoleDto> result = new ArrayList<RoleDto>();
                // assigned role(s)
                for (Role entity : entities)
                {
                    RoleDto r = new RoleDto(entity);
                    for (IFunction f : entity.getFunctions())
                    {
                        r.getFunctions().add(new FunctionDto(f));
                    }
                    result.add(r);
                }
                return result;
            }
            catch (Exception e)
            {
                throw new ServiceException(e.getMessage(), e);
            }
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.SecurityService#findUserGroupDivisions(Boolean)
     */
    public Set<GroupDivisionDto> findUserGroupDivisions(Boolean highPriority)
        throws ServiceException
    {
        try
        {
            String userId = ThreadLocalUtils.getUser().getId();
            try
            {
                //find highest priority role (=1)
                Role highPriorityRole = null;
                if (Boolean.TRUE.equals(highPriority))
                {
                    List<Role> roles = userMatrixDao.findRoles(userId);
                    for (Role r : roles)
                    {
                        if (highPriorityRole == null
                            || highPriorityRole.getPriority().compareTo(r.getPriority()) > 0)
                        {
                            highPriorityRole = r;
                        }
                    }
                }
                List<UserMatrix> matrix = userMatrixDao.findByUserId(userId);
                //
                SortedSet<GroupDivisionDto> result = new TreeSet<GroupDivisionDto>();
                // user assigned group/division(s)
                for (UserMatrix m : matrix)
                {
                    if (highPriorityRole != null && !m.getRole().equals(highPriorityRole))
                    {
                        continue;
                    }

                    Group group = (Group) m.getGroup();

                    //all groups (all divisions)
                    if (group == null)
                    {
                        for (Group g : groupDao.findAll())
                        {
                            GroupDto groupDto = ConvertUtils.convert(g);
                            List<IDivision> divisions = g.getDivisions();
                            if (divisions.isEmpty()) // eg Thematic
                            {
                                GroupDivisionDto item = new GroupDivisionDto();
                                item.setGroup(groupDto);
                                result.add(item);
                            }
                            else
                            {
                                GroupDivisionDto itemAll = new GroupDivisionDto();
                                itemAll.setGroup(groupDto);
                                result.add(itemAll);
                                for (IDivision d : divisions)
                                {
                                    GroupDivisionDto item = new GroupDivisionDto();
                                    item.setGroup(groupDto);
                                    item.setDivision(ConvertUtils.convert(d));
                                    result.add(item);
                                }
                            }
                        }
                        break;
                    }

                    //do not include deleted group(s)
                    if (group != null && group.isDeleted())
                    {
                        LOG.warn("Deleted Group can not be used: " + group.toString());
                        continue;
                    }

                    //selected group
                    GroupDto groupDto = ConvertUtils.convert(group);
                    Division division = (Division) m.getDivision();

                    //do not include deleted division(s)
                    if (division != null && division.isDeleted())
                    {
                        LOG.warn("Deleted Division can not be used: " + division.toString());
                        continue;
                    }

                    //all divisions
                    if (division == null)
                    {
                        GroupDivisionDto itemAll = new GroupDivisionDto();
                        itemAll.setGroup(groupDto);
                        result.add(itemAll);
                        for (IDivision d : group.getDivisions())
                        {
                            GroupDivisionDto item = new GroupDivisionDto();
                            item.setGroup(groupDto);
                            item.setDivision(ConvertUtils.convert(d));
                            result.add(item);
                        }
                        continue;
                    }

                    //selected division
                    GroupDivisionDto item = new GroupDivisionDto();
                    item.setGroup(groupDto);
                    item.setDivision(ConvertUtils.convert(division));
                    result.add(item);
                }
                return result;
            }
            catch (Exception e)
            {
                throw new ServiceException(e.getMessage(), e);
            }
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.SecurityService#findUserFunction(net.apollosoft.ats.audit.domain.dto.security.GroupDivisionDto, java.lang.String)
     */
    public FunctionDto findUserFunction(UserMatrixDto userMatrixDto, String functionQuery)
        throws ServiceException
    {
        //TODO: optimise - use sql query
        try
        {
            List<UserMatrix> userMatrixList = userMatrixDao.findByUserId(userMatrixDto.getUser().getId());
            for (UserMatrix m : userMatrixList)
            {
                GroupDivisionDto groupDivision = new GroupDivisionDto();
                groupDivision.setGroup(m.getGroup());
                groupDivision.setDivision(m.getDivision());
                if (!m.getReportType().equals(userMatrixDto.getReportType()))
                {
                    continue;
                }
                if (m.getGroup() == null
                    || (m.getGroup().equals(userMatrixDto.getGroup()) && m.getDivision() == null)
                    || userMatrixDto.equals(groupDivision))
                {
                    List<IFunction> functions = m.getRole().getFunctions();
                    for (IFunction f : functions)
                    {
                        if (IFunction.QUERY_ADD_COMMENT.equals(f.getQuery()))
                        {
                            return new FunctionDto(f);
                        }
                    }
                }
            }
            return null;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.SecurityService#checkSecurity(net.apollosoft.ats.audit.domain.hibernate.Audit)
     */
    public void checkSecurity(Audit audit) throws ServiceException,
        net.apollosoft.ats.domain.security.SecurityException
    {
        try
        {
            // check user security rules (for audit)
            IReportType reportType = audit.getReportType();
            List<AuditGroupDivision> groupDivisions = audit.getGroupDivisions();
            checkSecurity(reportType, groupDivisions);
        }
        catch (net.apollosoft.ats.domain.security.SecurityException eAudit)
        {
            // check user security rules (for all actions)
            for (IIssue issue : audit.getIssues())
            {
                try
                {
                    checkSecurity((Issue) issue);
                    return; // success - at least one issue found
                }
                catch (net.apollosoft.ats.domain.security.SecurityException ignore)
                {
                    // ignore
                }
            }
            // no action found - throw original exception
            throw eAudit;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.SecurityService#checkSecurity(net.apollosoft.ats.audit.domain.hibernate.Issue)
     */
    public void checkSecurity(Issue issue) throws ServiceException,
        net.apollosoft.ats.domain.security.SecurityException
    {
        try
        {
            List<IAction> actions = issue.getActions();
            // any user can see it
            if (actions.isEmpty())
            {
                return;
            }
            // check user security rules (for issue's actions)
            for (IAction action : actions)
            {
                try
                {
                    checkSecurity((Action) action);
                    return; // success - at least one action found
                }
                catch (net.apollosoft.ats.domain.security.SecurityException ignore)
                {
                    // ignore
                }
            }
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
        // no action found within this issue - where user has access
        throw new net.apollosoft.ats.domain.security.SecurityException("Access Denied to this issue");
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.SecurityService#checkSecurity(net.apollosoft.ats.audit.domain.hibernate.Action)
     */
    public void checkSecurity(Action action) throws ServiceException,
        net.apollosoft.ats.domain.security.SecurityException
    {
        try
        {
            // check action responsible first
            String userId = ThreadLocalUtils.getUser().getId();
            for (ActionResponsible ar : action.getActionResponsibles())
            {
                if (userId.equals(ar.getUser().getId()))
                {
                    return;
                }
            }
            // check user security rules
            IReportType reportType = action.getIssue().getAudit().getReportType();
            List<ActionGroupDivision> groupDivisions = action.getGroupDivisions();
            checkSecurity(reportType, groupDivisions);
        }
        catch (net.apollosoft.ats.domain.security.SecurityException actionAccessDenied)
        {
            throw actionAccessDenied;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 
     * @param reportType
     * @param groupDivisions
     * @throws DaoException
     */
    private void checkSecurity(IReportType reportType, List<? extends GroupDivision> groupDivisions)
        throws net.apollosoft.ats.domain.security.SecurityException, DaoException
    {
        String userId = ThreadLocalUtils.getUser().getId();
        List<UserMatrix> userMatrix = userMatrixDao.findByUserId(userId);
        for (UserMatrix um : userMatrix)
        {
            IRole r = um.getRole();
            //check special role - SYSTEM_ADMIN (TODO: hard coded role.. re-factor.. how?)
            if (r.getId() == RoleEnum.SYSTEM_ADMIN.ordinal())
            {
                continue;
            }
            IGroup g = um.getGroup();
            IDivision d = um.getDivision();
            if (um.getReportType().equals(reportType))
            {
                if (g == null)
                {
                    //all group(s)/division(s)
                    return;
                }
                for (GroupDivision gd : groupDivisions)
                {
                    //this group (gd.getGroup() not null)
                    if (g.equals(gd.getGroup()))
                    {
                        if (d == null)
                        {
                            //all division(s)
                            return;
                        }
                        if (gd.getDivision() == null || d.equals(gd.getDivision()))
                        {
                            //this division
                            return;
                        }
                    }
                }
            }
        }
        throw new net.apollosoft.ats.domain.security.SecurityException("Access Denied to this entity");
    }

}