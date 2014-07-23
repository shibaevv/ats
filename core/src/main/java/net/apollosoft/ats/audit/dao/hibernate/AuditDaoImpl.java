package net.apollosoft.ats.audit.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.apollosoft.ats.audit.dao.AuditDao;
import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.dao.UserDao;
import net.apollosoft.ats.audit.dao.UserMatrixDao;
import net.apollosoft.ats.audit.domain.hibernate.Audit;
import net.apollosoft.ats.audit.domain.hibernate.AuditGroupDivision;
import net.apollosoft.ats.audit.domain.refdata.IActionStatus;
import net.apollosoft.ats.audit.domain.refdata.IActionStatus.ActionStatusEnum;
import net.apollosoft.ats.audit.domain.refdata.IBusinessStatus.BusinessStatusEnum;
import net.apollosoft.ats.audit.search.ReportSearchCriteria;
import net.apollosoft.ats.domain.hibernate.refdata.ReportType;
import net.apollosoft.ats.domain.hibernate.security.User;
import net.apollosoft.ats.domain.hibernate.security.UserMatrix;
import net.apollosoft.ats.search.Pagination;
import net.apollosoft.ats.util.ThreadLocalUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class AuditDaoImpl extends BaseDaoImpl<Audit> implements AuditDao
{

    @Autowired
    @Qualifier("userDao")
    //@Resource(name="userDao")
    private UserDao userDao;

    @Autowired
    @Qualifier("userMatrixDao")
    //@Resource(name="userMatrixDao")
    private UserMatrixDao userMatrixDao;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.AuditDao#findNameLike(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<Audit> findAuditByNameLike(String auditNameLike, Boolean published) throws DaoException
    {
        try
        {
            //base sql
            StringBuffer sb = new StringBuffer("FROM Audit audit");
            //where sql
            sb.append(" WHERE (:auditNameLike IS NULL OR audit.name LIKE :auditNameLike) AND audit.deletedDate IS NULL");

            //base parameters
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("auditNameLike", StringUtils.isBlank(auditNameLike) ? null
                : ("%" + auditNameLike + "%"));
            //
            if (published != null)
            {
                if (published)
                {
                    sb.append(" AND (audit.publishedBy IS NOT NULL AND audit.publishedDate IS NOT NULL)");
                }
                else
                {
                    sb.append(" AND (audit.publishedBy IS NULL AND audit.publishedDate IS NULL)");
                }
            }

            /////////////////////////////////////////////////////////////////
            // FILTER RECORDS BY USER SECURITY PROFILE (IN SQL)
            String userId = ThreadLocalUtils.getUser().getId();
            //List<ReportType> userReportTypes = userMatrixDao.findReportTypes(userId);
            List<UserMatrix> userMatrix = userMatrixDao.findByUserId(userId);
            sb.append(whereSecurity(null, null, userMatrix, "reportType", parameters));
            // BUILD SECURITY FILTER (IN SQL!)
            /////////////////////////////////////////////////////////////////

            sb.append(" ORDER BY audit.name");
            String sql = sb.toString();
            //
            Query qry = getEntityManager().createQuery(sql);
            updateQuery(qry, parameters);
            return (List<Audit>) qry.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.BaseDao#findAll()
     */
    @SuppressWarnings("unchecked")
    public List<Audit> findAll() throws DaoException
    {
        try
        {
            return (List<Audit>) getEntityManager().createQuery("FROM Audit ORDER BY createdDate")
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.AuditDao#findByCriteriaRaw(net.apollosoft.ats.audit.search.ReportSearchCriteria)
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> findByCriteria(ReportSearchCriteria criteria) throws DaoException
    {
        try
        {
            //base sql
            StringBuffer sb = new StringBuffer("FROM v_ats_audit");
            //where sql
            sb.append(" WHERE (:reportTypeId IS NULL OR v_report_type_id = :reportTypeId)");
            sb.append(" AND (:issuedDateFrom IS NULL OR audit_issued_date >= :issuedDateFrom)");
            sb.append(" AND (:issuedDateTo IS NULL OR audit_issued_date <= :issuedDateTo)");
            sb.append(" AND (:auditId IS NULL OR v_audit_id = :auditId)");
            sb.append(" AND (:auditNameLike IS NULL OR audit_name LIKE :auditNameLike)");

            //base parameters
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("reportTypeId", criteria.getReportTypeId());
            parameters.put("issuedDateFrom", criteria.getIssuedDateFrom());
            parameters.put("issuedDateTo", criteria.getIssuedDateTo());
            parameters.put("auditId", criteria.getSearchAuditId());
            String auditNameLike = criteria.getSearchAuditName();
            parameters.put("auditNameLike", StringUtils.isBlank(auditNameLike) ? null
                : ("%" + auditNameLike + "%"));
            //
            if (criteria.getPublished() != null)
            {
                if (criteria.getPublished())
                {
                    sb.append(" AND (v_audit_published_by IS NOT NULL OR v_audit_published_date IS NOT NULL)");
                }
                else
                {
                    sb.append(" AND (v_audit_published_by IS NULL OR v_audit_published_date IS NULL)");
                }
            }

            /////////////////////////////////////////////////////////////////
            // FILTER RECORDS BY USER SECURITY PROFILE (IN SQL)
            String[] groupDivisionIds = criteria.getAuditGroupDivisions();
            String userId = ThreadLocalUtils.getUser().getId();
            List<UserMatrix> userMatrix = userMatrixDao.findByUserId(userId);
            List<ReportType> userReportTypes = userMatrixDao.findReportTypes(userId);
            sb.append(whereSecurityRaw(groupDivisionIds, userReportTypes, userMatrix, "audit", parameters));
            // BUILD SECURITY FILTER (IN SQL!)
            /////////////////////////////////////////////////////////////////

            Pagination p = criteria.getPagination();
            String sql = sb.toString();
            if (p != null)
            {
                //set totalRecords
                Query qry = getEntityManager()
                    .createNativeQuery("SELECT count(v_audit_id) " + sql);
                updateQuery(qry, parameters);
                Number count = (Number) qry.getSingleResult();
                p.setTotalRecords(count.intValue());
                //set orderBy clause
                String sort = p.getSort();
                String dir = p.getDir();
                if (StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(dir))
                {
                    sql += " ORDER BY " + sort + " " + dir;
                }
            }
            //
            Query qry = getEntityManager().createNativeQuery("SELECT * " + sql);
            updateQuery(qry, parameters);
            if (p != null && p.getPageSize() > 0)
            {
                qry.setFirstResult(p.getStartIndex());
                qry.setMaxResults(p.getPageSize());
            }
            List<Object[]> result = (List<Object[]>) qry.getResultList();
            return result;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.AuditDao#findByReference(java.lang.String)
     */
    public Audit findByReference(String reference) throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createQuery("FROM Audit WHERE reference = :reference");
            qry.setParameter("reference", reference);
            return (Audit) qry.getSingleResult();
        }
        catch (NoResultException ignore)
        {
            //No entity found for query
            return null;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /**
     * 
     */
    public AuditGroupDivision findByUniqueKey(Long auditId, Long groupId, Long divisionId)
        throws DaoException
    {
        try
        {
            String sql = "FROM AuditGroupDivision WHERE audit.id = :auditId AND group.id = :groupId";
            if (divisionId == null)
            {
                sql += " AND division.id IS NULL";
            }
            else
            {
                sql += " AND division.id = :divisionId";
            }
            Query qry = getEntityManager().createQuery(sql);
            qry.setParameter("groupId", groupId);
            qry.setParameter("auditId", auditId);
            if (divisionId != null)
            {
                qry.setParameter("divisionId", divisionId);
            }
            return (AuditGroupDivision) qry.getSingleResult();
        }
        catch (NoResultException ignore)
        {
            //No entity found for query
            return null;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.AuditDao#findAuditReferences()
     */
    @SuppressWarnings("unchecked")
    public List<String> findAuditReferences() throws DaoException
    {
        try
        {
            Query qry = getEntityManager()
                .createQuery(
                    "SELECT reference FROM Audit WHERE reference IS NOT NULL ORDER BY issuedDate, reference");
            return (List<String>) qry.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.AuditDao#countActionOpen(java.lang.Long)
     */
    public Integer countActionOpen(Long auditId) throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createNamedQuery("audit.countActionOpen");
            qry.setParameter("auditId", auditId);
            qry.setParameter("actionStatusId", IActionStatus.OPEN);
            Number result = (Number) qry.getSingleResult();
            return result.intValue();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.AuditDao#countActionFollow(java.lang.Long)
     */
    public Integer countActionTotal(Long auditId) throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createNamedQuery("audit.countActionTotal");
            qry.setParameter("auditId", auditId);
            Number result = (Number) qry.getSingleResult();
            return result.intValue();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.AuditDao#countUnpublishedActions(java.lang.Long)
     */
    public Integer countActionUnpublished(Long auditId) throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createNamedQuery("audit.countActionUnpublished");
            qry.setParameter("auditId", auditId);
            Number result = (Number) qry.getSingleResult();
            return result.intValue();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.AuditDao#countUnpublishedIssues(java.lang.Long)
     */
    public Integer countIssueUnpublished(Long auditId) throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createNamedQuery("audit.countIssueUnpublished");
            qry.setParameter("auditId", auditId);
            Number result = (Number) qry.getSingleResult();
            return result.intValue();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.ActionDao#findExport(net.apollosoft.ats.audit.search.ReportSearchCriteria)
     */
    //TODO: move to actionDao
    @SuppressWarnings("unchecked")
    public List<Object[]> findExport(ReportSearchCriteria criteria) throws DaoException
    {
        try
        {
            //base sql
            StringBuffer sb = new StringBuffer("SELECT * FROM v_ats_action_export");
            //where sql
            Map<String, Object> parameters = new HashMap<String, Object>();
            sb.append(" WHERE (:reportTypeId IS NULL OR v_report_type_id = :reportTypeId)");
            sb.append(" AND (:issuedDateFrom IS NULL OR (audit_issued_date IS NOT NULL AND audit_issued_date >= :issuedDateFrom))");
            sb.append(" AND (:issuedDateTo IS NULL OR (audit_issued_date IS NOT NULL AND audit_issued_date <= :issuedDateTo))");
            sb.append(" AND (:dueDateFrom IS NULL OR (action_due_date IS NOT NULL AND action_due_date >= :dueDateFrom))");
            sb.append(" AND (:dueDateTo IS NULL OR (action_due_date IS NOT NULL AND action_due_date <= :dueDateTo))");
            sb.append(" AND (:closedDateFrom IS NULL OR (action_closed_date IS NOT NULL AND action_closed_date >= :closedDateFrom))");
            sb.append(" AND (:closedDateTo IS NULL OR (action_closed_date IS NOT NULL AND action_closed_date <= :closedDateTo))");
            sb.append(" AND (:responsibleUser IS NULL OR :responsibleUser IN (SELECT user_id FROM ats_action_responsible WHERE v_action_id = ats_action_responsible.action_id))");
            sb.append(" AND (:ratingId IS NULL OR v_rating_id = :ratingId)");
            sb.append(" AND (:businessStatusId IS NULL OR v_business_status_id = :businessStatusId)");
            sb.append(" AND (:auditId IS NULL OR v_audit_id = :auditId)");
            sb.append(" AND (:auditNameLike IS NULL OR audit_name LIKE :auditNameLike)");
            //parameters
            parameters.put("reportTypeId", criteria.getReportTypeId());
            parameters.put("issuedDateFrom", criteria.getIssuedDateFrom());
            parameters.put("issuedDateTo", criteria.getIssuedDateTo());
            parameters.put("dueDateFrom", criteria.getDueDateFrom());
            parameters.put("dueDateTo", criteria.getDueDateTo());
            parameters.put("closedDateFrom", criteria.getClosedDateFrom());
            parameters.put("closedDateTo", criteria.getClosedDateTo());
            User responsibleUser = userDao.findById(criteria.getResponsibleUser().getId());
            parameters.put("responsibleUser", responsibleUser);
            parameters.put("ratingId", criteria.getRatingId());
            parameters.put("businessStatusId", criteria.getBusinessStatusId());
            parameters.put("auditId", criteria.getSearchAuditId());
            String auditNameLike = criteria.getSearchAuditName();
            parameters.put("auditNameLike", StringUtils.isBlank(auditNameLike) ? null : ("%" + auditNameLike + "%"));

            //TODO: use businessStatus.actionStatus
            Long actionStatusId = criteria.getActionStatusId();
            if (actionStatusId != null && actionStatusId > 0)
            {
                if (ActionStatusEnum.OPEN.ordinal() == actionStatusId)
                {
                    sb.append(" AND (v_business_status_id IN ("
                        + BusinessStatusEnum.IN_PROGRESS.ordinal() + ","
                        + BusinessStatusEnum.NOT_ACTIONED.ordinal() + "))"); // for OPEN
                }
                else if (ActionStatusEnum.CLOSED.ordinal() == actionStatusId)
                {
                    sb.append(" AND (v_business_status_id IN ("
                        + BusinessStatusEnum.IMPLEMENTED.ordinal() + ","
                        + BusinessStatusEnum.NO_LONGER_APPLICABLE.ordinal() + "))"); // for CLOSED
                }
            }

            //to see report without issue/action 
            if (criteria.isAuditGroupDivision())
            {
                sb.append(" AND (v_issue_id IS NULL OR (v_issue_published_by IS NOT NULL OR v_issue_published_date IS NOT NULL))");
                sb.append(" AND (v_action_id IS NULL OR (v_action_published_by IS NOT NULL OR v_action_published_date IS NOT NULL))");
            }
            else
            {
                //sb.append(" AND (v_issue_published_by IS NOT NULL OR v_issue_published_date IS NOT NULL)");
                sb.append(" AND (v_action_published_by IS NOT NULL OR v_action_published_date IS NOT NULL)");
            }

            /////////////////////////////////////////////////////////////////
            // FILTER RECORDS BY USER SECURITY PROFILE (IN SQL)
            String[] groupDivisionIds = criteria.isAuditGroupDivision() ? criteria
                .getAuditGroupDivisions() : criteria.getActionGroupDivisions();
            String userId = ThreadLocalUtils.getUser().getId();
            List<UserMatrix> userMatrix = userMatrixDao.findByUserId(userId);
            List<ReportType> userReportTypes = userMatrixDao.findReportTypes(userId);
            String alias = criteria.isAuditGroupDivision() ? "audit" : "action";
            sb.append(whereSecurityRaw(groupDivisionIds, userReportTypes, userMatrix, alias, parameters));
            // BUILD SECURITY FILTER (IN SQL!)
            /////////////////////////////////////////////////////////////////

            //
            Pagination p = criteria.getPagination();
            String sql = sb.toString();
            if (p != null)
            {
                //set orderBy clause
                String sort = p.getSort();
                String dir = p.getDir();
                if (StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(dir))
                {
                    sql += " ORDER BY " + sort + " " + dir;
                }
            }

            //
            Query qry = getEntityManager().createNativeQuery(sql);
            updateQuery(qry, parameters);
            List<Object[]> result = (List<Object[]>) qry.getResultList();
            return result;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}