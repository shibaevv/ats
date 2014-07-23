package net.apollosoft.ats.audit.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import net.apollosoft.ats.audit.dao.BusinessStatusDao;
import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.domain.hibernate.refdata.BusinessStatus;
import net.apollosoft.ats.domain.hibernate.Auditable;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class BusinessStatusDaoImpl extends BaseDaoImpl<BusinessStatus> implements BusinessStatusDao
{

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.hibernate.BaseDaoImpl#findAll()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<BusinessStatus> findAll() throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createQuery("FROM BusinessStatus ORDER BY name");
            return qry.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.hibernate.BaseDaoImpl#delete(net.apollosoft.ats.audit.domain.hibernate.Auditable)
     */
    @Override
    public void delete(Auditable<?> entity) throws DaoException
    {
        throw new DaoException("Not implemented (read only data)");
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.hibernate.BaseDaoImpl#save(java.lang.Object)
     */
    @Override
    public void save(BusinessStatus entity) throws DaoException
    {
        throw new DaoException("Not implemented (read only data)");
    }

}