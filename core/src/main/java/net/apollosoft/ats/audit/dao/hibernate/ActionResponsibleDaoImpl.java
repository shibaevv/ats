package net.apollosoft.ats.audit.dao.hibernate;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.apollosoft.ats.audit.dao.ActionResponsibleDao;
import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.domain.hibernate.ActionResponsible;


public class ActionResponsibleDaoImpl extends BaseDaoImpl<ActionResponsible> implements
    ActionResponsibleDao
{

    public void save(ActionResponsible entity) throws DaoException
    {
        super.save(entity);
    }

    public void delete(ActionResponsible entity) throws DaoException
    {
        if (entity == null)
        {
            return;
        }
        try
        {
            getEntityManager().remove(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }

    }

    public ActionResponsible find(Long actionId, String userId) throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createQuery(
                "FROM ActionResponsible WHERE action.id = :actionId AND user.id = :userId");
            qry.setParameter("actionId", actionId);
            qry.setParameter("userId", userId);
            return (ActionResponsible) qry.getSingleResult();
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
