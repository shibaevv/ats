package net.apollosoft.ats.audit.dao.hibernate.security;

import java.util.List;

import javax.persistence.Query;

import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.dao.hibernate.BaseDaoImpl;
import net.apollosoft.ats.audit.dao.security.RoleDao;
import net.apollosoft.ats.domain.hibernate.security.Role;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao
{

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.hibernate.BaseDaoImpl#findAll()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Role> findAll() throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createQuery("FROM Role ORDER BY name");
            return qry.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}