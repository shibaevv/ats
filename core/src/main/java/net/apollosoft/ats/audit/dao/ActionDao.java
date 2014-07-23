package net.apollosoft.ats.audit.dao;

import java.util.Date;
import java.util.List;

import net.apollosoft.ats.audit.domain.hibernate.Action;
import net.apollosoft.ats.audit.domain.hibernate.ActionGroupDivision;
import net.apollosoft.ats.audit.domain.hibernate.Audit;
import net.apollosoft.ats.audit.search.ReportSearchCriteria;
import net.apollosoft.ats.domain.hibernate.security.UserMatrix;
import net.apollosoft.ats.search.Pagination;

import org.springframework.stereotype.Repository;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Repository
public interface ActionDao extends BaseDao<Action>
{

    /**
     * Find all opened/published actions
     * @param dueDateTo optional
     * @param userMatrix optional
     * @return
     * @throws DaoException
     */
    List<Action> findOpen(Date dueDateTo, UserMatrix userMatrix) throws DaoException;

    /**
     * 
     * @param criteria
     * @return
     * @throws DaoException
     */
    List<Action> findByCriteriaCompass(ReportSearchCriteria criteria) throws DaoException;

    /**
     * Finder: native sql (fast)
     * @param criteria
     * @return
     * @throws DaoException
     */
    List<Object[]> findByCriteria(ReportSearchCriteria criteria) throws DaoException;

    /**
     * 
     * @param issueId
     * @param published
     * @return
     * @throws DaoException
     */
    List<Action> findByIssueId(Long issueId, Pagination pagination, Boolean published) throws DaoException;

    /**
     * 
     * @param auditId
     * @param published
     * @return
     * @throws DaoException
     */
    List<Action> findByAuditId(Long auditId, Pagination pagination, Boolean published) throws DaoException;

    /**
     * 
     * @param audit
     * @param actionReference
     * @param actionName
     * @return
     * @throws DaoException
     */
    Action findByName(Audit audit, String actionReference, String actionName) throws DaoException;

    /**
     * 
     * @param groupId
     * @param divisionId
     * @param actionGroupDivisionId
     * @return
     * @throws DaoException
     */
    ActionGroupDivision findByUniqueKey(Long actionId, Long groupId, Long divisionId) throws DaoException;

}