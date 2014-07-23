package net.apollosoft.ats.audit.dao;

import java.util.List;

import net.apollosoft.ats.audit.domain.hibernate.Issue;
import net.apollosoft.ats.audit.search.IssueSearchCriteria;

import org.springframework.stereotype.Repository;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Repository
public interface IssueDao extends BaseDao<Issue>
{

    /**
     * Finder:
     * @param criteria
     * @return
     * @throws DaoException
     */
    List<Issue> findByCriteria(IssueSearchCriteria criteria) throws DaoException;

    /**
     * Find by unique key.
     * @param reference
     * @return
     * @throws DaoException
     */
    Issue findByReference(String reference) throws DaoException;

    /**
     * 
     * @param auditId
     * @param published
     * @return
     * @throws DaoException
     */
    List<Issue> findByAuditId(Long auditId, Boolean published) throws DaoException;

    /**
     * For the No. of Open Action Items, the Business Status would be either 'Not implemented'  or 'In Progress',
     * @param issueId
     * @return
     * @throws DaoException
     */
    Integer countActionOpen(Long issueId) throws DaoException;

    /**
     * @param issueId
     * @return
     * @throws DaoException
     */
    Integer countActionTotal(Long issueId) throws DaoException;

}