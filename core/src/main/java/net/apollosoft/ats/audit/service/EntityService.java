package net.apollosoft.ats.audit.service;

import java.util.List;

import net.apollosoft.ats.audit.domain.dto.refdata.BusinessStatusDto;
import net.apollosoft.ats.audit.domain.dto.refdata.ParentRiskCategoryDto;
import net.apollosoft.ats.audit.domain.dto.refdata.ParentRiskDto;
import net.apollosoft.ats.audit.domain.refdata.IActionFollowupStatus;
import net.apollosoft.ats.audit.domain.refdata.IActionStatus;
import net.apollosoft.ats.audit.domain.refdata.IBusinessStatus;
import net.apollosoft.ats.audit.domain.refdata.IRating;
import net.apollosoft.ats.audit.validation.ValidationException;
import net.apollosoft.ats.domain.dto.TemplateDto;
import net.apollosoft.ats.domain.dto.security.DivisionDto;
import net.apollosoft.ats.domain.dto.security.FunctionDto;
import net.apollosoft.ats.domain.dto.security.GroupDto;
import net.apollosoft.ats.domain.dto.security.RoleDto;
import net.apollosoft.ats.domain.refdata.IReportType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface EntityService
{

    /**
     * 
     * @param groupId
     * @return
     * @throws ServiceException
     */
    List<DivisionDto> findDivisionByGroupId(Long groupId) throws ServiceException;

    /**
     * 
     * @return
     * @throws ServiceException
     */
    List<GroupDto> findAllGroup() throws ServiceException;

    /**
     * 
     * @return
     * @throws ServiceException
     */
    List<IActionStatus> findAllActionStatus() throws ServiceException;

    /**
     * 
     * @return
     * @throws ServiceException
     */
    List<IActionFollowupStatus> findAllFollowupStatus() throws ServiceException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    BusinessStatusDto findBusinessStatusById(Long id) throws ServiceException;

    /**
     * 
     * @return
     * @throws ServiceException
     */
    List<IBusinessStatus> findAllBusinessStatus() throws ServiceException;

    /**
     * 
     * @param dto
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void save(BusinessStatusDto dto) throws ServiceException, ValidationException;

    /**
     * 
     * @param dto
     * @param deleted
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void delete(BusinessStatusDto dto, boolean deleted) throws ServiceException,
        ValidationException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    FunctionDto findFunctionById(Long id) throws ServiceException;

    /**
     * 
     * @return
     * @throws ServiceException
     */
    List<FunctionDto> findAllFunction() throws ServiceException;

    /**
     * 
     * @param dto
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void save(FunctionDto dto) throws ServiceException, ValidationException;

    /**
     * 
     * @param dto
     * @param deleted
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void delete(FunctionDto dto, boolean deleted) throws ServiceException, ValidationException;

    /**
     * 
     * @return
     * @throws ServiceException
     */
    List<IRating> findAllRating() throws ServiceException;

    /**
     * 
     * @return
     * @throws ServiceException
     */
    List<IReportType> findAllReportType() throws ServiceException;

    /**
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    RoleDto findRoleById(Long id) throws ServiceException;

    /**
     * 
     * @return
     * @throws ServiceException
     */
    List<RoleDto> findAllRole() throws ServiceException;

    /**
     * 
     * @param role
     * @throws ValidationException
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void save(RoleDto role) throws ServiceException, ValidationException;

    /**
     * 
     * @param roleId
     * @return
     * @throws ServiceException
     */
    List<FunctionDto> findFunctionByRoleId(Long roleId) throws ServiceException;

    /**
     * 
     * @param role
     * @param function
     * @throws ServiceException
     * @throws ValidationException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveFunction(RoleDto role, FunctionDto function) throws ServiceException,
        ValidationException;

    /**
     * 
     * @param dto
     * @param deleted
     * @throws ServiceException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void delete(RoleDto dto, boolean deleted) throws ServiceException, ValidationException;

    /**
     * 
     * @return
     * @throws ServiceException
     */
    List<ParentRiskCategoryDto> findAllParentRiskCategory() throws ServiceException;

    /**
     * 
     * @return
     * @throws ServiceException
     */
    List<ParentRiskDto> findAllParentRisks() throws ServiceException;

    /**
     * 
     * @param riskCategoryId
     * @return
     * @throws ServiceException
     */
    List<ParentRiskDto> findParentRiskByCategoryId(Long riskCategoryId) throws ServiceException;

    /**
     * 
     * @param riskId
     * @return
     * @throws ServiceException
     */
    ParentRiskDto findParentRiskById(Long riskId) throws ServiceException;

    /**
     * 
     * @return
     * @throws ServiceException
     */
    List<TemplateDto> findAllTemplate() throws ServiceException;

    /**
     * 
     * @param templateId
     * @return
     * @throws ServiceException
     */
    TemplateDto findTemplateById(Long templateId) throws ServiceException;

    /**
     * 
     * @param template
     * @throws ServiceException
     * @throws ValidationException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void save(TemplateDto template) throws ServiceException, ValidationException;

}