package net.apollosoft.ats.audit.dao.hibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.apollosoft.ats.audit.dao.CommentDao;
import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.domain.IComment;
import net.apollosoft.ats.audit.domain.hibernate.Action;
import net.apollosoft.ats.audit.domain.hibernate.Comment;
import net.apollosoft.ats.audit.search.CommentSearchCriteria;
import net.apollosoft.ats.search.Pagination;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class CommentDaoImpl extends BaseDaoImpl<Comment> implements CommentDao
{

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.BaseDao#findAll()
     */
    @SuppressWarnings("unchecked")
    public List<Comment> findAll() throws DaoException
    {
        try
        {
            return (List<Comment>) getEntityManager().createQuery(
                "FROM Comment ORDER BY createdDate").getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.CommentDao#findByCriteria(net.apollosoft.ats.audit.search.CommentSearchCriteria)
     */
    @SuppressWarnings("unchecked")
    public List<Comment> findByCriteria(CommentSearchCriteria criteria) throws DaoException
    {
        try
        {
            Pagination p = criteria.getPagination();
            String sql = "FROM Comment comment WHERE comment.action.id = :actionId and comment.auditLog = :auditLog";
            //
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("actionId", criteria.getActionId());
            parameters.put("auditLog", criteria.isAuditLog());
            //
            sql = updatePagination(sql, p, parameters);
            //
            Query qry = getEntityManager().createQuery(sql);
            updateQuery(qry, parameters);
            List<Comment> result = (List<Comment>) qry.getResultList();
            return result;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.CommentDao#findByActionDetail(net.apollosoft.ats.audit.domain.Action, java.lang.String)
     */
    public Comment findByActionDetail(Action action, String detail, Date createdDate) throws DaoException
    {
        try
        {
            //Query qry = getEntityManager().createQuery("FROM Comment WHERE action = :action AND CAST(detail AS string) = :detail");
            //qry.setParameter("action", action);
            //qry.setParameter("detail", detail);
            //return (Comment) qry.getSingleResult();
            for (IComment comment : action.getComments())
            {
                if (comment.getDetail().equals(detail))
                {
                    if (createdDate == null || createdDate.equals(comment.getCreatedDate()))
                    {
                        return (Comment) comment;
                    }
                }
            }
            return null;
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
     * @see net.apollosoft.ats.audit.dao.CommentDao#maxDocuments(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public Integer maxDocuments(Long actionId) throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createNamedQuery("comment.maxDocuments");
            qry.setParameter("actionId", actionId);
            Integer result = 0;
            List<Integer> results = qry.getResultList();
            for (Integer r : results)
            {
                result = Math.max(r, result);
            }
            return result;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}