package net.apollosoft.ats.audit.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.dao.UserDao;
import net.apollosoft.ats.audit.dao.UserMatrixDao;
import net.apollosoft.ats.audit.search.UserSearchCriteria;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.service.UserService;
import net.apollosoft.ats.audit.util.ConvertUtils;
import net.apollosoft.ats.audit.validation.UserMatrixValidator;
import net.apollosoft.ats.audit.validation.ValidationException;
import net.apollosoft.ats.audit.validation.ValidationException.ValidationMessage;
import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.dto.security.FunctionDto;
import net.apollosoft.ats.domain.dto.security.UserDto;
import net.apollosoft.ats.domain.dto.security.UserGroupDivisionDto;
import net.apollosoft.ats.domain.dto.security.UserMatrixDto;
import net.apollosoft.ats.domain.hibernate.refdata.ReportType;
import net.apollosoft.ats.domain.hibernate.security.Division;
import net.apollosoft.ats.domain.hibernate.security.Group;
import net.apollosoft.ats.domain.hibernate.security.Role;
import net.apollosoft.ats.domain.hibernate.security.RoleFunction;
import net.apollosoft.ats.domain.hibernate.security.User;
import net.apollosoft.ats.domain.hibernate.security.UserGroupDivision;
import net.apollosoft.ats.domain.hibernate.security.UserMatrix;
import net.apollosoft.ats.domain.security.IFunction;
import net.apollosoft.ats.domain.security.IUser;
import net.apollosoft.ats.util.BeanUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class UserServiceImpl implements UserService
{

    /** logger. */
    //private static final Log LOG = LogFactory.getLog(UserServiceImpl.class);

    @Autowired
    @Qualifier("userDao")
    //@Resource(name="userDao")
    private UserDao userDao;

    @Autowired
    @Qualifier("userMatrixDao")
    //@Resource(name="userMatrixDao")
    private UserMatrixDao userMatrixDao;

    @Autowired
    @Qualifier("userMatrixValidator")
    //@Resource(name="userMatrixValidator")
    UserMatrixValidator userMatrixValidator;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.UserService#find(net.apollosoft.ats.audit.search.UserSearchCriteria)
     */
    public List<UserDto> find(UserSearchCriteria criteria) throws ServiceException
    {
        try
        {
            //
            List<User> entities = userDao.findByCriteria(criteria);
            //use dto objects
            List<UserDto> result = new ArrayList<UserDto>();
            for (User entity : entities)
            {
                UserDto userDto = convert(entity);
                result.add(userDto);
            }
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.UserService#findUserNameLike(java.lang.String, boolean)
     */
    public List<IUser> findUserNameLike(String name, Boolean active) throws ServiceException
    {
        try
        {
            String[] names = StringUtils.split(name);
            String firstNameLike = names != null && names.length >= 1 ? names[0] : null;
            String lastNameLike = names != null && names.length >= 2 ? names[1] : null;
            List<User> entities = userDao.findNameLike(firstNameLike, lastNameLike, active);
            List<IUser> result = new ArrayList<IUser>();
            for (User entity : entities)
            {
                result.add(new UserDto(entity));
            }
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.UserService#findById(java.lang.String)
     */
    public UserDto findById(String userId) throws ServiceException
    {
        try
        {
            User entity = userDao.findById(userId);
            UserDto result = convert(entity);
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.UserService#findByLogin(java.lang.String)
     */
    public UserDto findByLogin(String login) throws ServiceException
    {
        try
        {
            List<User> users = userDao.findByLogin(login);
            if (users.isEmpty())
            {
                throw new ServiceException("No user found for username " + login);
            }
            if (users.size() > 1)
            {
                throw new ServiceException("Multiple users found for username " + login);
            }
            User user = users.get(0);
            UserDto result = convert(user);
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.UserService#findUserHome(java.lang.String)
     */
    public IFunction findUserHome(String userId) throws ServiceException
    {
        try
        {
            List<Role> roles = userMatrixDao.findRoles(userId);
            Role role = null; // first one has highest priority = 1
            for (Role r : roles)
            {
                if (role == null || BeanUtils.compareTo(role.getPriority(), r.getPriority()) < 0)
                {
                    role = r;
                }
            }
            if (role != null)
            {
                for (RoleFunction rf : role.getRoleFunctions())
                {
                    if (rf.isHome())
                    {
                        return new FunctionDto(rf.getFunction());
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
     * @see net.apollosoft.ats.audit.service.UserService#findUserMatrixById(java.lang.Long)
     */
    public UserMatrixDto findUserMatrixById(Long userMatrixId) throws ServiceException
    {
        try
        {
            UserMatrix entity = userMatrixDao.findById(userMatrixId);
            UserMatrixDto result = entity == null ? null : new UserMatrixDto(entity);
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.UserService#findUserMatrix(net.apollosoft.ats.audit.search.UserMatrixSearchCriteria)
     */
    public List<UserMatrixDto> findUserMatrix(String userId)
        throws ServiceException
    {
        try
        {
            List<UserMatrix> entities = userMatrixDao.findByUserId(userId);
            List<UserMatrixDto> result = new ArrayList<UserMatrixDto>();
            for (UserMatrix entity : entities)
            {
                result.add(new UserMatrixDto(entity));
            }
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.UserService#save(net.apollosoft.ats.audit.domain.dto.security.UserMatrixDto)
     */
    public void save(UserMatrixDto dto) throws ServiceException, ValidationException
    {
        //validation
        List<ValidationMessage> errors = userMatrixValidator.validate(dto);
        if (!errors.isEmpty())
        {
            throw new ValidationException(errors);
        }

        try
        {
            UserMatrix entity = userMatrixDao.findById(dto.getId());
            if (entity == null)
            {
                //new
                entity = new UserMatrix();
                entity.setUser(new User(dto.getUser().getId()));
                entity.setRole(new Role(dto.getRole().getId()));
                entity.setReportType(new ReportType(dto.getReportType().getId()));
            }
            else
            {
                //existing (never change user!!!)
                //entity.setUser(new User(dto.getUser().getId()));
                if (dto.getRole().getId() != null)
                {
                    entity.setRole(new Role(dto.getRole().getId()));
                }
                if (dto.getReportType().getId() != null)
                {
                    entity.setReportType(new ReportType(dto.getReportType().getId()));
                }
            }
            // group can be null
            entity.setGroup(dto.getGroup().getId() == null ? null : new Group(dto.getGroup()
                .getId()));
            // division can be null
            entity.setDivision(dto.getDivision().getId() == null ? null : new Division(dto
                .getDivision().getId()));

            //save
            userMatrixDao.save(entity);
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.UserService#delete(net.apollosoft.ats.audit.domain.dto.security.UserMatrixDto)
     */
    public void delete(UserMatrixDto dto) throws ServiceException, ValidationException
    {
        try
        {
            UserMatrix entity = userMatrixDao.findById(dto.getId());
            if (entity != null)
            {
                //delete physically
                userMatrixDao.delete(entity);
            }
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
    private UserDto convert(User entity) throws DomainException, DaoException
    {
        if (entity == null)
        {
            return null;
        }
        //
        UserDto result = new UserDto(entity);
        //copy group/division(s)
        for (UserGroupDivision item : entity.getGroupDivisions())
        {
            UserGroupDivisionDto itemDto = new UserGroupDivisionDto();
            ConvertUtils.convert(item, itemDto);
            result.getGroupDivisions().add(itemDto);
        }
        //
        return result;
    }

}