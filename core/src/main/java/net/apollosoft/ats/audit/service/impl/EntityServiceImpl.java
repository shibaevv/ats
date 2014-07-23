package net.apollosoft.ats.audit.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.apollosoft.ats.audit.dao.BusinessStatusDao;
import net.apollosoft.ats.audit.dao.DivisionDao;
import net.apollosoft.ats.audit.dao.EntityDao;
import net.apollosoft.ats.audit.dao.GroupDao;
import net.apollosoft.ats.audit.dao.ParentRiskCategoryDao;
import net.apollosoft.ats.audit.dao.ParentRiskDao;
import net.apollosoft.ats.audit.dao.TemplateDao;
import net.apollosoft.ats.audit.dao.security.FunctionDao;
import net.apollosoft.ats.audit.dao.security.RoleDao;
import net.apollosoft.ats.audit.domain.dto.refdata.ActionFollowupStatusDto;
import net.apollosoft.ats.audit.domain.dto.refdata.ActionStatusDto;
import net.apollosoft.ats.audit.domain.dto.refdata.BusinessStatusDto;
import net.apollosoft.ats.audit.domain.dto.refdata.ParentRiskCategoryDto;
import net.apollosoft.ats.audit.domain.dto.refdata.ParentRiskDto;
import net.apollosoft.ats.audit.domain.dto.refdata.RatingDto;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ActionFollowupStatus;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ActionStatus;
import net.apollosoft.ats.audit.domain.hibernate.refdata.BusinessStatus;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ParentRisk;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ParentRiskCategory;
import net.apollosoft.ats.audit.domain.hibernate.refdata.Rating;
import net.apollosoft.ats.audit.domain.refdata.IActionFollowupStatus;
import net.apollosoft.ats.audit.domain.refdata.IActionStatus;
import net.apollosoft.ats.audit.domain.refdata.IBusinessStatus;
import net.apollosoft.ats.audit.domain.refdata.IRating;
import net.apollosoft.ats.audit.service.EntityService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.util.ConvertUtils;
import net.apollosoft.ats.audit.validation.ValidationException;
import net.apollosoft.ats.domain.dto.TemplateDto;
import net.apollosoft.ats.domain.dto.refdata.ReportTypeDto;
import net.apollosoft.ats.domain.dto.security.DivisionDto;
import net.apollosoft.ats.domain.dto.security.FunctionDto;
import net.apollosoft.ats.domain.dto.security.GroupDto;
import net.apollosoft.ats.domain.dto.security.RoleDto;
import net.apollosoft.ats.domain.hibernate.Template;
import net.apollosoft.ats.domain.hibernate.refdata.ReportType;
import net.apollosoft.ats.domain.hibernate.security.Division;
import net.apollosoft.ats.domain.hibernate.security.Function;
import net.apollosoft.ats.domain.hibernate.security.Group;
import net.apollosoft.ats.domain.hibernate.security.Role;
import net.apollosoft.ats.domain.hibernate.security.RoleFunction;
import net.apollosoft.ats.domain.refdata.IReportType;
import net.apollosoft.ats.domain.security.IFunction;
import net.apollosoft.ats.util.BeanUtils;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class EntityServiceImpl implements EntityService
{

    /** logger. */
    //private static final Log LOG = LogFactory.getLog(EntityServiceImpl.class);

    @Autowired
    @Qualifier("entityDao")
    //@Resource(name="entityDao")
    private EntityDao entityDao;

    @Autowired
    @Qualifier("businessStatusDao")
    //@Resource(name="businessStatusDao")
    private BusinessStatusDao businessStatusDao;

    @Autowired
    @Qualifier("divisionDao")
    //@Resource(name="divisionDao")
    private DivisionDao divisionDao;

    @Autowired
    @Qualifier("functionDao")
    //@Resource(name="functionDao")
    private FunctionDao functionDao;

    @Autowired
    @Qualifier("groupDao")
    //@Resource(name="groupDao")
    private GroupDao groupDao;

    @Autowired
    @Qualifier("roleDao")
    //@Resource(name="roleDao")
    private RoleDao roleDao;

    @Autowired
    @Qualifier("parentRiskCategoryDao")
    //@Resource(name="parentRiskCategoryDao")
    private ParentRiskCategoryDao parentRiskCategoryDao;

    @Autowired
    @Qualifier("parentRiskDao")
    //@Resource(name="parentRiskDao")
    private ParentRiskDao parentRiskDao;

    @Autowired
    @Qualifier("templateDao")
    //@Resource(name="templateDao")
    private TemplateDao templateDao;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#findDivisionByGroupId(java.lang.Long)
     */
    public List<DivisionDto> findDivisionByGroupId(Long groupId) throws ServiceException
    {
        try
        {
            Group group = groupDao.findById(groupId);
            List<Division> entities = divisionDao.findByGroup(group);
            List<DivisionDto> result = ConvertUtils.convertDivision(entities);
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#findAllGroup()
     */
    public List<GroupDto> findAllGroup() throws ServiceException
    {
        try
        {
            List<Group> entities = groupDao.findAll();
            List<GroupDto> result = ConvertUtils.convertGroup(entities);
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#findAllParentRiskCategory()
     */
    public List<ParentRiskCategoryDto> findAllParentRiskCategory() throws ServiceException
    {
        try
        {
            List<ParentRiskCategory> entities = parentRiskCategoryDao.findAll();
            List<ParentRiskCategoryDto> result = ConvertUtils.convertParentRiskCategory(entities);
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#findAllParentRisks()
     */
    public List<ParentRiskDto> findAllParentRisks() throws ServiceException
    {
        try
        {
            List<ParentRisk> entities = parentRiskDao.findAll();
            List<ParentRiskDto> result = ConvertUtils.convertParentRisk(entities);
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#findParentRiskByCategoryId(java.lang.Long)
     */
    public List<ParentRiskDto> findParentRiskByCategoryId(Long riskCategoryId)
        throws ServiceException
    {
        try
        {
            ParentRiskCategory parentRiskCategory = parentRiskCategoryDao.findById(riskCategoryId);
            List<ParentRisk> entities = parentRiskDao.findByCategory(parentRiskCategory);
            List<ParentRiskDto> result = ConvertUtils.convertParentRisk(entities);
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public ParentRiskDto findParentRiskById(Long riskId) throws ServiceException
    {
        try
        {
            ParentRisk parentRisk = parentRiskDao.findById(riskId);
            ParentRiskDto parentRiskDto = ConvertUtils.convert(parentRisk);
            return parentRiskDto;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#findUserNameLike(java.lang.String)
     */
    public BusinessStatusDto findBusinessStatusById(Long id) throws ServiceException
    {
        try
        {
            BusinessStatus entity = businessStatusDao.findById(id);
            BusinessStatusDto result = entity == null ? null : new BusinessStatusDto(entity);
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.ActionService#findAllBusinessStatus()
     */
    public List<IBusinessStatus> findAllBusinessStatus() throws ServiceException
    {
        try
        {
            List<BusinessStatus> entities = businessStatusDao.findAll();
            List<IBusinessStatus> result = new ArrayList<IBusinessStatus>();
            for (BusinessStatus entity : entities)
            {
                result.add(new BusinessStatusDto(entity));
            }
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#save(net.apollosoft.ats.audit.domain.dto.refdata.BusinessStatusDto)
     */
    public void save(BusinessStatusDto dto) throws ServiceException, ValidationException
    {
        //validation
        //List<ValidationMessage> errors = businessStatusValidator.validate(dto);
        //if (!errors.isEmpty())
        //{
        //    throw new ValidationException(errors);
        //}

        try
        {
            BusinessStatus entity = businessStatusDao.findById(dto.getId());
            if (entity == null)
            {
                entity = new BusinessStatus();
            }
            BeanUtils.copyProperties(dto, entity, IBusinessStatus.IGNORE);

            //save
            businessStatusDao.save(entity);
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#delete(net.apollosoft.ats.audit.domain.dto.refdata.BusinessStatusDto, boolean)
     */
    public void delete(BusinessStatusDto dto, boolean deleted) throws ServiceException,
        ValidationException
    {
        try
        {
            BusinessStatus entity = businessStatusDao.findById(dto.getId());
            if (entity != null)
            {
                //delete/undelete logically
                if (deleted)
                {
                    businessStatusDao.delete(entity);
                }
                else
                {
                    businessStatusDao.unDelete(entity);
                }
            }
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#findFunctionById(java.lang.Long)
     */
    public FunctionDto findFunctionById(Long functionId) throws ServiceException
    {
        try
        {
            Function entity = functionDao.findById(functionId);
            FunctionDto result = entity == null ? null : new FunctionDto(entity);
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#findAllFunction()
     */
    public List<FunctionDto> findAllFunction() throws ServiceException
    {
        try
        {
            List<Function> entities = functionDao.findAll();
            List<FunctionDto> result = new ArrayList<FunctionDto>();
            for (Function entity : entities)
            {
                result.add(new FunctionDto(entity));
            }
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#save(net.apollosoft.ats.audit.domain.dto.security.FunctionDto)
     */
    public void save(FunctionDto dto) throws ServiceException, ValidationException
    {
        //validation
        //List<ValidationMessage> errors = functionValidator.validate(dto);
        //if (!errors.isEmpty())
        //{
        //    throw new ValidationException(errors);
        //}

        try
        {
            Function entity = functionDao.findById(dto.getId());
            if (entity == null)
            {
                entity = new Function();
            }
            //check for partial update (eg name only)
            if (StringUtils.isBlank(dto.getDetail()))
            {
                dto.setDetail(entity.getDetail());
            }
            if (StringUtils.isBlank(dto.getModule()))
            {
                dto.setModule(entity.getModule());
            }
            if (StringUtils.isBlank(dto.getName()))
            {
                dto.setName(entity.getName());
            }
            if (StringUtils.isBlank(dto.getQuery()))
            {
                dto.setQuery(entity.getQuery());
            }
            //
            BeanUtils.copyProperties(dto, entity, IFunction.IGNORE);

            //save
            functionDao.save(entity);
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#delete(net.apollosoft.ats.audit.domain.dto.security.FunctionDto, boolean)
     */
    public void delete(FunctionDto dto, boolean deleted) throws ServiceException,
        ValidationException
    {
        try
        {
            Function entity = functionDao.findById(dto.getId());
            if (entity != null)
            {
                //delete/undelete logically
                if (deleted)
                {
                    functionDao.delete(entity);
                }
                else
                {
                    functionDao.unDelete(entity);
                }
            }
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#findAllActionStatus()
     */
    public List<IActionStatus> findAllActionStatus() throws ServiceException
    {
        try
        {
            List<ActionStatus> entities = entityDao.findAllActionStatus();
            List<IActionStatus> result = new ArrayList<IActionStatus>();
            for (ActionStatus entity : entities)
            {
                result.add(new ActionStatusDto(entity));
            }
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#findAllFollowupStatus()
     */
    public List<IActionFollowupStatus> findAllFollowupStatus() throws ServiceException
    {
        try
        {
            List<ActionFollowupStatus> entities = entityDao.findAllFollowupStatus();
            List<IActionFollowupStatus> result = new ArrayList<IActionFollowupStatus>();
            for (ActionFollowupStatus entity : entities)
            {
                result.add(new ActionFollowupStatusDto(entity));
            }
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.ActionService#findAllRatings()
     */
    public List<IRating> findAllRating() throws ServiceException
    {
        try
        {
            List<Rating> entities = entityDao.findAllRatings();
            List<IRating> result = new ArrayList<IRating>();
            for (Rating entity : entities)
            {
                result.add(new RatingDto(entity));
            }
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#findAllReportTypes()
     */
    public List<IReportType> findAllReportType() throws ServiceException
    {
        try
        {
            List<ReportType> entities = entityDao.findAllReportTypes();
            List<IReportType> result = new ArrayList<IReportType>();
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
     * @see net.apollosoft.ats.audit.service.EntityService#findRoleById(java.lang.Long)
     */
    public RoleDto findRoleById(Long roleId) throws ServiceException
    {
        try
        {
            Role entity = roleDao.findById(roleId);
            RoleDto result = new RoleDto(entity);
            for (IFunction function : entity.getFunctions())
            {
                result.getFunctions().add(new FunctionDto(function));
            }
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#findAllRoles()
     */
    public List<RoleDto> findAllRole() throws ServiceException
    {
        try
        {
            List<Role> entities = roleDao.findAll();
            List<RoleDto> result = new ArrayList<RoleDto>();
            for (Role entity : entities)
            {
                result.add(new RoleDto(entity));
            }
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#save(net.apollosoft.ats.audit.domain.dto.security.RoleDto)
     */
    public void save(RoleDto role) throws ServiceException, ValidationException
    {
        //validation
        //List<ValidationMessage> errors = roleValidator.validate(role);
        //if (!errors.isEmpty())
        //{
        //    throw new ValidationException(errors);
        //}

        try
        {
            Role entity = roleDao.findById(role.getId());
            if (entity == null)
            {
                entity = new Role();
            }
            ConvertUtils.convert(role, entity);

            //save
            roleDao.save(entity);
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#findFunctionByRoleId(java.lang.Long)
     */
    public List<FunctionDto> findFunctionByRoleId(Long roleId) throws ServiceException
    {
        try
        {
            final Role role = roleDao.findById(roleId);
            // mark selected role functions
            final List<IFunction> functions = role.getFunctions();
            final List<RoleFunction> roleFunctions = role.getRoleFunctions();
            List<FunctionDto> result = findAllFunction();
            CollectionUtils.forAllDo(result, new Closure()
            {
                /* (non-Javadoc)
                 * @see org.apache.commons.collections.Closure#execute(java.lang.Object)
                 */
                public void execute(Object obj)
                {
                    FunctionDto f = (FunctionDto) obj;
                    if (functions.contains(f))
                    {
                        //selected
                        f.setAdd2role(true);
                        //home
                        int idx = roleFunctions.indexOf(new RoleFunction(role, new Function(f
                            .getId())));
                        RoleFunction roleFunction = roleFunctions.get(idx);
                        f.setRoleHome(roleFunction.isHome());
                    }
                }
            });
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#saveFunction(net.apollosoft.ats.audit.domain.dto.security.RoleDto, java.lang.Long, boolean)
     */
    public void saveFunction(RoleDto role, FunctionDto function) throws ServiceException
    {
        try
        {
            Role entity = roleDao.findById(role.getId());
            List<RoleFunction> roleFunctions = entity.getRoleFunctions();
            //find
            RoleFunction roleFunction = null;
            for (RoleFunction linkItem : roleFunctions)
            {
                if (linkItem.getFunction().equals(function))
                {
                    roleFunction = linkItem;
                    break;
                }
            }
            //update selected
            if (function.isAdd2role())
            {
                if (roleFunction == null)
                {
                    roleFunction = new RoleFunction();
                    roleFunction.setRole(entity);
                    roleFunction.setFunction(new Function(function.getId()));
                    roleFunctions.add(roleFunction);
                }
                else if (roleFunction.isDeleted())
                {
                    roleDao.unDelete(roleFunction);
                }
            }
            else
            {
                if (roleFunction != null && !roleFunction.isDeleted())
                {
                    roleDao.delete(roleFunction);
                }
            }
            //update home
            if (function.isRoleHome())
            {
                for (RoleFunction linkItem : roleFunctions)
                {
                    linkItem.setHome(false);
                }
                roleFunction.setHome(function.isRoleHome());
            }
            //save
            roleDao.save(entity);
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#delete(net.apollosoft.ats.audit.domain.dto.security.RoleDto, boolean)
     */
    public void delete(RoleDto dto, boolean deleted) throws ServiceException, ValidationException
    {
        try
        {
            Role entity = roleDao.findById(dto.getId());
            if (entity != null)
            {
                //delete/undelete logically
                if (deleted)
                {
                    roleDao.delete(entity);
                }
                else
                {
                    roleDao.unDelete(entity);
                }
            }
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#findAllTemplate()
     */
    public List<TemplateDto> findAllTemplate() throws ServiceException
    {
        try
        {
            List<Template> entities = templateDao.findAll();
            List<TemplateDto> result = ConvertUtils.convertTemplate(entities);
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#findTemplateById(java.lang.Long)
     */
    public TemplateDto findTemplateById(Long templateId) throws ServiceException
    {
        try
        {
            Template entity = templateDao.findById(templateId);
            TemplateDto result = ConvertUtils.convert(entity);
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EntityService#save(net.apollosoft.ats.audit.domain.dto.TemplateDto)
     */
    public void save(TemplateDto template) throws ServiceException, ValidationException
    {
        //validation
        //List<ValidationMessage> errors = templateValidator.validate(template);
        //if (!errors.isEmpty())
        //{
        //    throw new ValidationException(errors);
        //}

        try
        {
            Template entity = templateDao.findById(template.getId());
            if (entity == null)
            {
                entity = new Template();
            }
            ConvertUtils.convert(template, entity);

            //save
            templateDao.save(entity);
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}