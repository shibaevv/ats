package net.apollosoft.ats.audit.dao.hibernate.security;

import java.util.List;

import javax.persistence.Query;

import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.dao.hibernate.BaseDaoImpl;
import net.apollosoft.ats.audit.dao.security.FunctionDao;
import net.apollosoft.ats.domain.hibernate.security.Function;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements FunctionDao
{

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.hibernate.BaseDaoImpl#findAll()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Function> findAll() throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createQuery("FROM Function ORDER BY module, name");
            return qry.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}