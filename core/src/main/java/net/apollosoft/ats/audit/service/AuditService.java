package net.apollosoft.ats.audit.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Set;

import net.apollosoft.ats.audit.domain.dto.AuditDto;
import net.apollosoft.ats.audit.domain.hibernate.Audit;
import net.apollosoft.ats.audit.search.ReportSearchCriteria;
import net.apollosoft.ats.audit.validation.ValidationException;
import net.apollosoft.ats.domain.ContentTypeEnum;
import net.apollosoft.ats.domain.dto.DocumentDto;
import net.apollosoft.ats.domain.dto.security.GroupDivisionDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface AuditService
{

    /**
     * Audit names
     * @param nameLike
     * @return
     * @throws ServiceException
     */
    List<AuditDto> findAuditByNameLike(String nameLike, Boolean published) throws ServiceException;

    /**
     * 
     * @param criteria
     * @return
     * @throws ServiceException
     */
    List<AuditDto> find(ReportSearchCriteria criteria) throws ServiceException;

    /**
     * 
     * @param auditId
     * @return
     * @throws ServiceException
     */
    AuditDto findById(Long auditId) throws ServiceException;

    /**
     * TODO: Internally used method (using hibernate mapped domain object)
     * @param audit new or existing audit (hibernate mapped)
     * @param groupDivisions
     * @throws ServiceException
     */
    @Transactional(readOnly = false)
    void addGroupDivision(Audit audit, Set<GroupDivisionDto> groupDivisions) throws ServiceException;

    /**
     * 
     * @param criteria
     * @param output
     * @param contentType 'text/csv' or 'application/vnd.ms-excels'
     * @throws ServiceException
     */
    void export(ReportSearchCriteria criteria, OutputStream output, ContentTypeEnum contentType)
        throws ServiceException;

    /**
     * 
     * @param audit
     * @throws ServiceException
     * @throws ValidationException
     */
    @Transactional(readOnly = false)
    void save(AuditDto audit) throws ServiceException, ValidationException;

    /**
     * 
     * @param entity
     * @throws ServiceException
     * @throws ValidationException
     */
    @Transactional(readOnly = false)
    void delete(AuditDto entity) throws ServiceException, ValidationException;

    /**
     * 
     * @param auditDto
     * @throws ServiceException
     * @throws ValidationException
     */
    @Transactional(readOnly = false)
    void publish(AuditDto auditDto) throws ServiceException, ValidationException;

    /**
     * 
     * @param audit
     * @param groupDivisionDto
     * @throws ServiceException
     * @throws ValidationException
     */
    @Transactional(readOnly = false)
    void addGroupDivision(AuditDto audit, GroupDivisionDto groupDivisionDto)
        throws ServiceException, ValidationException;

    /**
     * 
     * @param audit
     * @param d
     * @throws ServiceException
     * @throws ValidationException
     */
    @Transactional(readOnly = false)
    void addAttachment(AuditDto audit, DocumentDto d) throws ServiceException, ValidationException;

    /**
     * 
     * @param audit
     * @param groupDivisionId
     * @throws ServiceException
     * @throws ValidationException
     */
    @Transactional(readOnly = false)
    void removeGroupDivision(AuditDto audit, Long groupDivisionId) throws ServiceException,
        ValidationException;

    @Transactional(readOnly = false)
    void removeAttachment(AuditDto audit) throws ServiceException, ValidationException;

}