package net.apollosoft.ats.audit.dao;

import java.util.List;

import net.apollosoft.ats.audit.domain.hibernate.Audit;
import net.apollosoft.ats.audit.domain.hibernate.AuditGroupDivision;
import net.apollosoft.ats.audit.search.ReportSearchCriteria;

import org.springframework.stereotype.Repository;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Repository
public interface AuditDao extends BaseDao<Audit>
{

    /**
     * 
     * @param nameLike
     * @return
     * @throws DaoException
     */
    List<Audit> findAuditByNameLike(String nameLike, Boolean published) throws DaoException;

    /**
     * 
     * @param criteria
     * @return
     * @throws DaoException
     */
    List<Object[]> findByCriteria(ReportSearchCriteria criteria) throws DaoException;

    /**
     * Find by unique key.
     * @param reference
     * @return
     * @throws DaoException
     */
    Audit findByReference(String reference) throws DaoException;

    /**
     * 
     * @return
     * @throws DaoException
     */
    List<String> findAuditReferences() throws DaoException;

    /**
     * For the No. of Open Action Items, the Business Status would be either 'Not implemented'  or 'In Progress',
     * @param auditId
     * @return
     * @throws DaoException
     */
    Integer countActionOpen(Long auditId) throws DaoException;

    /**
     * @param auditId
     * @return
     * @throws DaoException
     */
    Integer countActionTotal(Long auditId) throws DaoException;

    /**
     * 
     * @param auditId
     * @return
     * @throws DaoException
     */
    Integer countActionUnpublished(Long auditId) throws DaoException;

    /**
     * 
     * @param auditId
     * @return
     * @throws DaoException
     */
    Integer countIssueUnpublished(Long auditId) throws DaoException;

    /**
     * 
     * @param criteria
     * @return
     * @throws DaoException
     */
    List<Object[]> findExport(ReportSearchCriteria criteria) throws DaoException;

    /**
     * 
     * @param groupId
     * @param divisionId
     * @return
     * @throws DaoException
     */
    AuditGroupDivision findByUniqueKey(Long auditId, Long groupId, Long divisionId) throws DaoException;

}