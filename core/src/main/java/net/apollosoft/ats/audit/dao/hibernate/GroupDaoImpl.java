package net.apollosoft.ats.audit.dao.hibernate;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.dao.GroupDao;
import net.apollosoft.ats.domain.hibernate.Auditable;
import net.apollosoft.ats.domain.hibernate.security.Group;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class GroupDaoImpl extends BaseDaoImpl<Group> implements GroupDao
{

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.GroupDao#findByName(java.lang.String)
     */
    public Group findByName(String name) throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createQuery("FROM Group WHERE name = :name");
            qry.setParameter("name", name);
            return (Group) qry.getSingleResult();
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
     * @see net.apollosoft.ats.audit.dao.hibernate.BaseDaoImpl#findAll()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Group> findAll() throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createQuery(
                "FROM Group WHERE deletedBy IS NULL AND deletedDate IS NULL ORDER BY name");
            return (List<Group>) qry.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.BaseDao#save(java.lang.Object)
     */
    public void save(Group entity) throws DaoException
    {
        throw new DaoException("Not implemented (read only data)");
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.hibernate.BaseDaoImpl#delete(net.apollosoft.ats.audit.domain.hibernate.Auditable)
     */
    @Override
    public void delete(Auditable<?> entity) throws DaoException
    {
        throw new DaoException("Not implemented (read only data)");
    }

}