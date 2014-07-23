package net.apollosoft.ats.audit.dao.hibernate;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.dao.EntityDao;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ActionFollowupStatus;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ActionStatus;
import net.apollosoft.ats.audit.domain.hibernate.refdata.BusinessStatus;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ParentRisk;
import net.apollosoft.ats.audit.domain.hibernate.refdata.Rating;
import net.apollosoft.ats.domain.hibernate.refdata.ReportType;

import org.apache.commons.lang.StringUtils;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@SuppressWarnings("unchecked")
public class EntityDaoImpl extends BaseDaoImpl implements EntityDao
{

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.EntityDao#findRatingByName(java.lang.String)
     */
    public Rating findRatingByName(String name) throws DaoException
    {
        if (StringUtils.isBlank(name))
        {
            return null;
        }
        try
        {
            Query qry = getEntityManager().createQuery("FROM Rating WHERE name = :name");
            qry.setParameter("name", name);
            return (Rating) qry.getSingleResult();
        }
        catch (NoResultException ignore)
        {
            LOG.warn("No Rating found for [" + name + "]");
            return null;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.EntityDao#findRiskByNameCategory(java.lang.String, java.lang.String)
     */
    public ParentRisk findRiskByNameCategory(String name, String category) throws DaoException
    {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(category))
        {
            return null;
        }
        try
        {
            Query qry = getEntityManager().createQuery(
                "FROM ParentRisk WHERE name = :name AND category.name = :category");
            qry.setParameter("name", name);
            qry.setParameter("category", category);
            return (ParentRisk) qry.getSingleResult();
        }
        catch (NoResultException ignore)
        {
            LOG.warn("No ParentRisk found for [" + name + "][" + category + "]");
            return null;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.EntityDao#findActionStatusByName(java.lang.String)
     */
    public ActionStatus findActionStatusByName(String name) throws DaoException
    {
        if (StringUtils.isBlank(name))
        {
            return null;
        }
        try
        {
            Query qry = getEntityManager().createQuery("FROM ActionStatus WHERE name = :name");
            qry.setParameter("name", name);
            return (ActionStatus) qry.getSingleResult();
        }
        catch (NoResultException ignore)
        {
            LOG.warn("No BusinessStatus found for [" + name + "]");
            return null;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.EntityDao#findActionFollowupStatusByName(java.lang.String)
     */
    public ActionFollowupStatus findActionFollowupStatusByName(String name) throws DaoException
    {
        if (StringUtils.isBlank(name))
        {
            return null;
        }
        try
        {
            Query qry = getEntityManager().createQuery("FROM ActionFollowupStatus WHERE name = :name");
            qry.setParameter("name", name);
            return (ActionFollowupStatus) qry.getSingleResult();
        }
        catch (NoResultException ignore)
        {
            LOG.warn("No BusinessStatus found for [" + name + "]");
            return null;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.EntityDao#findAllActionStatus()
     */
    public List<ActionStatus> findAllActionStatus() throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createQuery("FROM ActionStatus ORDER BY name");
            return qry.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.EntityDao#findAllFollowupStatus()
     */
    public List<ActionFollowupStatus> findAllFollowupStatus() throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createQuery("FROM ActionFollowupStatus ORDER BY name");
            return qry.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.EntityDao#findBusinessStatusByName(java.lang.String)
     */
    public BusinessStatus findBusinessStatusByName(String name) throws DaoException
    {
        if (StringUtils.isBlank(name))
        {
            return null;
        }
        try
        {
            Query qry = getEntityManager().createQuery("FROM BusinessStatus WHERE name = :name");
            qry.setParameter("name", name);
            return (BusinessStatus) qry.getSingleResult();
        }
        catch (NoResultException ignore)
        {
            LOG.warn("No BusinessStatus found for [" + name + "]");
            return null;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.EntityDao#findAllRatings()
     */
    public List<Rating> findAllRatings() throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createQuery("FROM Rating");
            return qry.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.EntityDao#findAllReportTypes()
     */
    public List<ReportType> findAllReportTypes() throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createQuery("FROM ReportType ORDER BY name");
            return qry.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}