package net.apollosoft.ats.audit.service;

import java.util.List;
import java.util.Set;

import net.apollosoft.ats.audit.domain.hibernate.Action;
import net.apollosoft.ats.audit.domain.hibernate.Audit;
import net.apollosoft.ats.audit.domain.hibernate.Issue;
import net.apollosoft.ats.domain.dto.refdata.ReportTypeDto;
import net.apollosoft.ats.domain.dto.security.FunctionDto;
import net.apollosoft.ats.domain.dto.security.GroupDivisionDto;
import net.apollosoft.ats.domain.dto.security.RoleDto;
import net.apollosoft.ats.domain.dto.security.UserDto;
import net.apollosoft.ats.domain.dto.security.UserMatrixDto;
import net.apollosoft.ats.domain.security.IFunction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface SecurityService
{

    /**
     * Currently login user
     * @return
     * @throws ServiceException
     */
    UserDto getUser() throws ServiceException;

    /**
     * 
     * @return
     * @throws ServiceException
     */
    IFunction getUserHome() throws ServiceException;

    /**
     * 
     * @return
     * @throws ServiceException
     */
    List<ReportTypeDto> findUserReportTypes() throws ServiceException;

    /**
     * 
     * @return
     * @throws ServiceException
     */
    List<RoleDto> findUserRoles() throws ServiceException;

    /**
     * 
     * @param highPriority
     * @return
     * @throws ServiceException
     */
    Set<GroupDivisionDto> findUserGroupDivisions(Boolean highPriority) throws ServiceException;

    /**
     * 
     * @param userMatrixDto
     * @param functionQuery
     * @return
     * @throws ServiceException
     */
    FunctionDto findUserFunction(UserMatrixDto userMatrixDto, String functionQuery) throws ServiceException;

    /**
     * 
     * @param audit
     * @throws ServiceException
     * @throws net.apollosoft.ats.domain.security.SecurityException
     */
    void checkSecurity(Audit audit)
        throws ServiceException, net.apollosoft.ats.domain.security.SecurityException;

    /**
     * 
     * @param issue
     * @throws ServiceException
     * @throws net.apollosoft.ats.domain.security.SecurityException
     */
    void checkSecurity(Issue issue)
        throws ServiceException, net.apollosoft.ats.domain.security.SecurityException;

    /**
     * 
     * @param action
     * @throws ServiceException
     * @throws net.apollosoft.ats.domain.security.SecurityException
     */
    void checkSecurity(Action action)
        throws ServiceException, net.apollosoft.ats.domain.security.SecurityException;

}