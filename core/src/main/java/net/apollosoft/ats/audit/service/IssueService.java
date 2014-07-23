package net.apollosoft.ats.audit.service;

import java.util.List;

import net.apollosoft.ats.audit.domain.dto.IssueDto;
import net.apollosoft.ats.audit.search.IssueSearchCriteria;
import net.apollosoft.ats.audit.validation.ValidationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface IssueService
{

    /**
     * 
     * @param criteria
     * @return
     * @throws ServiceException
     */
    List<IssueDto> find(IssueSearchCriteria criteria) throws ServiceException;

    /**
     * 
     * @param issueId
     * @return
     * @throws ServiceException
     */
    IssueDto findById(Long issueId) throws ServiceException;

    /**
     * 
     * @param auditId
     * @param published
     * @return
     * @throws ServiceException
     */
    List<IssueDto> findByAuditId(Long auditId, Boolean published) throws ServiceException;

    /**
     * 
     * @param issueDto
     * @throws ServiceException
     * @throws ValidationException 
     */
    void delete(IssueDto issue) throws ServiceException, ValidationException;

}