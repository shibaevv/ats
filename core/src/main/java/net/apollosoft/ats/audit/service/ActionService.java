package net.apollosoft.ats.audit.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.apollosoft.ats.audit.domain.dto.ActionDto;
import net.apollosoft.ats.audit.domain.dto.CommentDto;
import net.apollosoft.ats.audit.domain.hibernate.Action;
import net.apollosoft.ats.audit.search.ReportSearchCriteria;
import net.apollosoft.ats.audit.validation.ValidationException;
import net.apollosoft.ats.domain.dto.security.GroupDivisionDto;
import net.apollosoft.ats.domain.dto.security.UserDto;
import net.apollosoft.ats.domain.hibernate.Template;
import net.apollosoft.ats.domain.hibernate.security.User;
import net.apollosoft.ats.domain.security.IUser;
import net.apollosoft.ats.search.Pagination;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface ActionService
{
    /**
     * 
     * @param action
     * @param userDto
     * @param clearActionResponsible 
     * @throws ServiceException
     * @throws ValidationException
     */
    void addActionResponsible(ActionDto action, UserDto userDto, boolean clearActionResponsible)
        throws ServiceException, ValidationException;

    /**
     * TODO: Internally used method (using hibernate mapped domain object) - no validation
     * @param action new or existing audit (hibernate mapped)
     * @param groupDivisions
     * @throws ServiceException
     */
    void addGroupDivision(Action action, Set<GroupDivisionDto> groupDivisions)
        throws ServiceException;

    /**
     * With validation
     * @param action
     * @param groupDivisionDto
     * @throws ServiceException
     * @throws ValidationException
     */
    void addGroupDivision(ActionDto action, GroupDivisionDto groupDivisionDto)
        throws ServiceException, ValidationException;

    /**
     * TODO: Internally used method (using hibernate mapped domain object)
     * @param action new or existing action (hibernate mapped)
     * @param users
     * @throws ServiceException
     */
    void addResponsibleUsers(Action action, Set<User> users) throws ServiceException;

    /**
     * To handle onchange events from UI
     * @param actionId
     * @param issueId - optional
     * @param businessStatusId
     * @return
     * @throws ServiceException
     */
    ActionDto businessStatusChanged(Long actionId, Long issueId, Long businessStatusId)
        throws ServiceException;

    /**
     * 
     * @param action
     * @throws ServiceException
     * @throws ValidationException
     */
    void delete(ActionDto action) throws ServiceException, ValidationException;

    /**
     * 
     * @param criteria
     * @return
     * @throws ServiceException
     */
    List<ActionDto> find(ReportSearchCriteria criteria) throws ServiceException;

    /**
     * 
     * @param auditId
     * @param published
     * @return
     * @throws ServiceException
     */
    List<ActionDto> findByAuditId(Long auditId, Pagination pagination, Boolean published) throws ServiceException;

    /**
     * 
     * @param actionId
     * @return
     * @throws ServiceException
     */
    ActionDto findById(Long actionId) throws ServiceException;

    /**
     * 
     * @param issueId
     * @param published
     * @return
     * @throws ServiceException
     */
    List<ActionDto> findByIssueId(Long issueId, Pagination pagination, Boolean published) throws ServiceException;

    /**
     * 
     * @param action
     * @param groupDivisionId
     * @throws ServiceException
     * @throws ValidationException
     */
    void removeGroupDivision(ActionDto action, Long groupDivisionId) throws ServiceException,
        ValidationException;

    /**
     * 
     * @param actionId
     * @param userId
     * @throws ServiceException
     * @throws ValidationException
     */
    void removeActionResponsible(Long actionId, String userId) throws ServiceException,
        ValidationException;

    /**
     * 
     * @param action
     * @throws ServiceException
     * @throws ValidationException
     */
    void save(ActionDto action) throws ServiceException, ValidationException;

    /**
     *
     * @param actionId
     * @param businessStatusId
     * @param comment
     * @throws ServiceException
     * @throws ValidationException 
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveComment(ActionDto action, CommentDto comment) throws ServiceException,
        ValidationException;

    /**
     * 
     * @param actions
     * @param template
     * @throws ServiceException
     */
    void sendEmail(Template template, Map<IUser, Set<Action>> actions) throws ServiceException;

    /**
     * TODO: refactor this
     * @param userId
     * @param action
     * @throws ServiceException
     */
    void sendEmailToPersonResponsible(String userId, Long actionId) throws ServiceException;

}