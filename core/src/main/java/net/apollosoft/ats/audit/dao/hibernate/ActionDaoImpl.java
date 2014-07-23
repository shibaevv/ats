package net.apollosoft.ats.audit.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import net.apollosoft.ats.audit.dao.ActionDao;
import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.dao.UserDao;
import net.apollosoft.ats.audit.dao.UserMatrixDao;
import net.apollosoft.ats.audit.domain.hibernate.Action;
import net.apollosoft.ats.audit.domain.hibernate.ActionGroupDivision;
import net.apollosoft.ats.audit.domain.hibernate.Audit;
import net.apollosoft.ats.audit.domain.refdata.IActionStatus.ActionStatusEnum;
import net.apollosoft.ats.audit.domain.refdata.IBusinessStatus.BusinessStatusEnum;
import net.apollosoft.ats.audit.search.ReportSearchCriteria;
import net.apollosoft.ats.domain.hibernate.refdata.ReportType;
import net.apollosoft.ats.domain.hibernate.security.User;
import net.apollosoft.ats.domain.hibernate.security.UserMatrix;
import net.apollosoft.ats.search.Pagination;
import net.apollosoft.ats.util.DateUtils;
import net.apollosoft.ats.util.ThreadLocalUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class ActionDaoImpl extends BaseDaoImpl<Action> implements ActionDao
{

    //@CompassContext
    //private CompassSession compassSession;

    @Autowired
    @Qualifier("userDao")
    //@Resource(name="userDao")
    private UserDao userDao;

    @Autowired
    @Qualifier("userMatrixDao")
    //@Resource(name="userMatrixDao")
    private UserMatrixDao userMatrixDao;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.BaseDao#findAll()
     */
    @SuppressWarnings("unchecked")
    public List<Action> findAll() throws DaoException
    {
        try
        {
            return (List<Action>) getEntityManager()
                .createQuery("FROM Action ORDER BY createdDate").getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.ActionDao#findOpen()
     */
    @SuppressWarnings("unchecked")
    public List<Action> findOpen(Date dueDateTo, UserMatrix userMatrix) throws DaoException
    {
        try
        {
            StringBuffer sb = new StringBuffer("FROM Action WHERE (deletedDate IS NULL)");
            //published
            sb.append(" AND (publishedBy IS NOT NULL OR publishedDate IS NOT NULL)");
            //opened
            sb.append(" AND (businessStatus.actionStatus.id = :actionStatusId)");
            //overdue or due in next month
            sb.append(" AND ((dueDate < :now) OR (:dueDateTo IS NULL OR dueDate < :dueDateTo))");
            //
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("actionStatusId", new Long(ActionStatusEnum.OPEN.ordinal()));
            parameters.put("now", DateUtils.getStartOfDay(ThreadLocalUtils.getDate()));
            parameters.put("dueDateTo", dueDateTo);
            ///////////////////////////////////////////////////////////////////////////
            // FILTER RECORDS BY USER SECURITY PROFILE (IN SQL)
            if (userMatrix != null)
            {
                sb.append(whereSecurity(userMatrix, "issue.audit.reportType", parameters));
            }
            // BUILD SECURITY FILTER (IN SQL!)
            ///////////////////////////////////////////////////////////////////////////
            //
            Query qry = getEntityManager().createQuery(sb.toString());
            updateQuery(qry, parameters);
            //
            List<Action> result = (List<Action>) qry.getResultList();
            return result;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.ActionDao#findByCriteriaCompass(net.apollosoft.ats.audit.search.ReportSearchCriteria)
     */
    public List<Action> findByCriteriaCompass(ReportSearchCriteria criteria) throws DaoException
    {
        try
        {
            List<Action> result = new ArrayList<Action>();
//            CompassHits hits = compassSession.find(criteria.getCompassQuery());
//            for (int i = 0; i < hits.length(); i++)
//            {
//                Object data = hits.data(i);
//                result.add((Action) data);
//            }
            return result;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.ActionDao#findByCriteriaRaw(net.apollosoft.ats.audit.search.ReportSearchCriteria)
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> findByCriteria(ReportSearchCriteria criteria) throws DaoException
    {
        try
        {
            //base sql
            StringBuffer sb = new StringBuffer("FROM v_ats_action");
            //where sql
            Map<String, Object> parameters = new HashMap<String, Object>();
            sb.append(" WHERE (:reportTypeId IS NULL OR v_report_type_id = :reportTypeId)");
            sb
                .append(" AND (:issuedDateFrom IS NULL OR (audit_issued_date IS NOT NULL AND audit_issued_date >= :issuedDateFrom))");
            sb
                .append(" AND (:issuedDateTo IS NULL OR (audit_issued_date IS NOT NULL AND audit_issued_date <= :issuedDateTo))");
            sb
                .append(" AND (:dueDateFrom IS NULL OR (action_due_date IS NOT NULL AND action_due_date >= :dueDateFrom))");
            sb
                .append(" AND (:dueDateTo IS NULL OR (action_due_date IS NOT NULL AND action_due_date <= :dueDateTo))");
            sb
                .append(" AND (:closedDateFrom IS NULL OR (action_closed_date IS NOT NULL AND action_closed_date >= :closedDateFrom))");
            sb
                .append(" AND (:closedDateTo IS NULL OR (action_closed_date IS NOT NULL AND action_closed_date <= :closedDateTo))");
            sb
                .append(" AND (:responsibleUser IS NULL OR :responsibleUser IN (SELECT user_id FROM ats_action_responsible WHERE v_action_id = ats_action_responsible.action_id))");
            sb.append(" AND (:ratingId IS NULL OR v_rating_id = :ratingId)");
            sb
                .append(" AND (:businessStatusId IS NULL OR v_business_status_id = :businessStatusId)");
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
            parameters.put("auditNameLike", StringUtils.isBlank(auditNameLike) ? null : ("%"
                + auditNameLike + "%"));
            //
            if (criteria.getPublished() != null)
            {
                if (criteria.isAuditGroupDivision())
                {
                    if (criteria.getPublished())
                    {
                        sb
                            .append(" AND (v_audit_published_by IS NOT NULL OR v_audit_published_date IS NOT NULL)");
                    }
                    else
                    {
                        sb
                            .append(" AND (v_audit_published_by IS NULL OR v_audit_published_date IS NULL)");
                    }
                }
                else
                {
                    if (criteria.getPublished())
                    {
                        sb
                            .append(" AND (v_action_published_by IS NOT NULL OR v_action_published_date IS NOT NULL)");
                    }
                    else
                    {
                        sb
                            .append(" AND (v_action_published_by IS NULL OR v_action_published_date IS NULL)");
                    }
                }
            }

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

            // for myaction ui - filter by responsibleUser enough
            if (!criteria.isMyaction())
            {
                /////////////////////////////////////////////////////////////////
                // FILTER RECORDS BY USER SECURITY PROFILE (IN SQL)
                String[] groupDivisionIds = criteria.getActionGroupDivisions();
                String userId = ThreadLocalUtils.getUser().getId();
                List<UserMatrix> userMatrix = userMatrixDao.findByUserId(userId);
                List<ReportType> userReportTypes = userMatrixDao.findReportTypes(userId);
                sb.append(whereSecurityRaw(groupDivisionIds, userReportTypes, userMatrix, "action",
                    parameters));
                // BUILD SECURITY FILTER (IN SQL!)
                /////////////////////////////////////////////////////////////////
            }

            //
            Pagination p = criteria.getPagination();
            String sql = sb.toString();
            if (p != null)
            {
                //set totalRecords
                Query qry = getEntityManager()
                    .createNativeQuery("SELECT count(v_action_id) " + sql);
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
            //
            List<Object[]> result = (List<Object[]>) qry.getResultList();
            return result;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }
    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.ActionDao#findByIssueId(java.lang.Long, boolean)
     */
    @SuppressWarnings("unchecked")
    public List<Action> findByIssueId(Long issueId, Pagination pagination, Boolean published)
        throws DaoException
    {
        try
        {
            //base sql
            StringBuffer sb = new StringBuffer("FROM Action WHERE");
            //where sql
            sb.append(" (deletedDate IS NULL)");
            if (Boolean.TRUE.equals(published))
            {
                sb.append(" AND (publishedBy IS NOT NULL OR publishedDate IS NOT NULL)");
            }
            else if (Boolean.FALSE.equals(published))
            {
                sb.append(" AND (publishedBy IS NULL AND publishedDate IS NULL)");
            }
            sb.append(" AND (issue.id = :issueId)");

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("issueId", issueId);

            String sql;
            if (pagination == null)
            {
                sb.append(" ORDER BY listIndex");
                sql = sb.toString();
            }
            else
            {
                sql = updateSorting(sb.toString(), pagination);
            }

            Query qry = getEntityManager().createQuery(sql);
            updateQuery(qry, parameters);
            List<Action> result = (List<Action>) qry.getResultList();
            return result;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.ActionDao#findByAuditId(java.lang.Long, boolean)
     */
    @SuppressWarnings("unchecked")
    public List<Action> findByAuditId(Long auditId, Pagination pagination, Boolean published)
        throws DaoException
    {
        try
        {
            //base sql
            StringBuffer sb = new StringBuffer("FROM Action WHERE");
            //where sql
            sb.append(" (deletedDate IS NULL)");
            if (Boolean.TRUE.equals(published))
            {
                sb.append(" AND (publishedBy IS NOT NULL OR publishedDate IS NOT NULL)");
            }
            else if (Boolean.FALSE.equals(published))
            {
                sb.append(" AND (publishedBy IS NULL AND publishedDate IS NULL)");
            }
            sb.append(" AND (issue.audit.id = :auditId)");

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("auditId", auditId);

            String sql;
            if (pagination == null)
            {
                sb.append(" ORDER BY listIndex");
                sql = sb.toString();
            }
            else
            {
                sql = updateSorting(sb.toString(), pagination);
            }

            Query qry = getEntityManager().createQuery(sql);
            updateQuery(qry, parameters);
            List<Action> result = (List<Action>) qry.getResultList();
            return result;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.ActionDao#findByName(java.lang.String)
     */
    public Action findByName(Audit audit, String actionReference, String actionName)
        throws DaoException
    {
        try
        {
            Query qry = getEntityManager()
                .createQuery(
                    "FROM Action WHERE issue.audit = :audit AND (:actionReference IS NULL OR reference = :actionReference) AND (:actionName IS NULL OR name = :actionName)");
            qry.setParameter("audit", audit);
            qry.setParameter("actionReference", actionReference);
            qry.setParameter("actionName", actionName);
            return (Action) qry.getSingleResult();
        }
        catch (NoResultException ignore)
        {
            //No entity found for query
            return null;
        }
        catch (NonUniqueResultException ignore)
        {
            //multiple entities found for query
            return null;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public ActionGroupDivision findByUniqueKey(Long actionId, Long groupId, Long divisionId)
        throws DaoException
    {
        try
        {
            String sql = "FROM ActionGroupDivision WHERE action.id = :actionId AND group.id = :groupId";
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
            qry.setParameter("actionId", actionId);
            if (divisionId != null)
            {
                qry.setParameter("divisionId", divisionId);
            }
            return (ActionGroupDivision) qry.getSingleResult();
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

}