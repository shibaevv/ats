package net.apollosoft.ats.audit.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.dao.IssueDao;
import net.apollosoft.ats.audit.domain.hibernate.Issue;
import net.apollosoft.ats.audit.domain.refdata.IActionStatus;
import net.apollosoft.ats.audit.search.IssueSearchCriteria;
import net.apollosoft.ats.search.Pagination;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class IssueDaoImpl extends BaseDaoImpl<Issue> implements IssueDao
{

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.BaseDao#findAll()
     */
    @SuppressWarnings("unchecked")
    public List<Issue> findAll() throws DaoException
    {
        try
        {
            return (List<Issue>) getEntityManager().createQuery("FROM Issue ORDER BY createdDate")
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.IssueDao#findByCriteria(net.apollosoft.ats.audit.search.IssueSearchCriteria)
     */
    @SuppressWarnings("unchecked")
    public List<Issue> findByCriteria(IssueSearchCriteria criteria) throws DaoException
    {
        try
        {
            Pagination p = criteria.getPagination();
            //base sql
            StringBuffer sb = new StringBuffer("FROM Issue WHERE");

            //where sql
            sb.append(" (audit.id = :auditId AND deletedDate IS NULL)");
            //
            if (criteria.getPublished() != null)
            {
                if (Boolean.TRUE.equals(criteria.getPublished()))
                {
                    sb.append(" AND (publishedBy IS NOT NULL OR publishedDate IS NOT NULL)");
                }
                else
                {
                    sb.append(" AND (publishedBy IS NULL AND publishedDate IS NULL)");
                }
            }

            //
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("auditId", criteria.getAuditId());
            //
            String sql = sb.toString();
            sql = updatePagination(sql, p, parameters);
            //
            Query qry = getEntityManager().createQuery(sql);
            updateQuery(qry, parameters);
            List<Issue> result = (List<Issue>) qry.getResultList();
            return result;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.IssueDao#findByReference(java.lang.String)
     */
    public Issue findByReference(String reference) throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createQuery("FROM Issue WHERE reference = :reference");
            qry.setParameter("reference", reference);
            return (Issue) qry.getSingleResult();
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
     * @see net.apollosoft.ats.audit.dao.IssueDao#findByAuditId(java.lang.Long, java.lang.Boolean)
     */
    @SuppressWarnings("unchecked")
    public List<Issue> findByAuditId(Long auditId, Boolean published) throws DaoException
    {
        try
        {
            //base sql
            StringBuffer sb = new StringBuffer("FROM Issue WHERE");
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
            sb.append(" AND (audit.id = :auditId)");

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("auditId", auditId);

            String sql = sb.toString();
            Query qry = getEntityManager().createQuery(sql);
            updateQuery(qry, parameters);
            return (List<Issue>) qry.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.IssueDao#countActionOpen(java.lang.Long)
     */
    public Integer countActionOpen(Long issueId) throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createNamedQuery("issue.countActionOpen");
            qry.setParameter("issueId", issueId);
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
     * @see net.apollosoft.ats.audit.dao.IssueDao#countActionFollow(java.lang.Long)
     */
    public Integer countActionTotal(Long issueId) throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createNamedQuery("issue.countActionTotal");
            qry.setParameter("issueId", issueId);
            Number result = (Number) qry.getSingleResult();
            return result.intValue();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}