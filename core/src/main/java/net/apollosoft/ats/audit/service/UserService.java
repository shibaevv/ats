package net.apollosoft.ats.audit.service;

import java.util.List;

import net.apollosoft.ats.audit.search.UserSearchCriteria;
import net.apollosoft.ats.audit.validation.ValidationException;
import net.apollosoft.ats.domain.dto.security.UserDto;
import net.apollosoft.ats.domain.dto.security.UserMatrixDto;
import net.apollosoft.ats.domain.security.IFunction;
import net.apollosoft.ats.domain.security.IUser;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface UserService
{

    /**
     * 
     * @param criteria
     * @return
     * @throws ServiceException
     */
    List<UserDto> find(UserSearchCriteria criteria) throws ServiceException;

    /**
     * 
     * @param userId
     * @return
     * @throws ServiceException
     */
    UserDto findById(String userId) throws ServiceException;

    /**
     * 
     * @param login
     * @return
     * @throws ServiceException
     */
    UserDto findByLogin(String login) throws ServiceException;

    /**
     * Format: FirstName LastName - Group - Login
     * @param name
     * @param active - non-terminated
     * @return
     * @throws ServiceException
     */
    List<IUser> findUserNameLike(String name, Boolean active) throws ServiceException;

    /**
     * 
     * @param userId
     * @return
     * @throws ServiceException
     */
    IFunction findUserHome(String userId) throws ServiceException;

    /**
     * 
     * @param userId
     * @return
     * @throws ServiceException
     */
    UserMatrixDto findUserMatrixById(Long userMatrixId) throws ServiceException;

    /**
     * 
     * @param userId
     * @return
     * @throws ServiceException
     */
    List<UserMatrixDto> findUserMatrix(String userId) throws ServiceException;

    /**
     * 
     * @param dto
     * @throws ServiceException
     * @throws ValidationException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void save(UserMatrixDto dto) throws ServiceException, ValidationException;

    /**
     * 
     * @param dto
     * @throws ServiceException
     * @throws ValidationException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void delete(UserMatrixDto dto) throws ServiceException, ValidationException;

}