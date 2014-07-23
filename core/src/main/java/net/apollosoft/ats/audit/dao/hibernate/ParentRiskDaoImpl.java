package net.apollosoft.ats.audit.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.dao.ParentRiskDao;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ParentRisk;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ParentRiskCategory;
import net.apollosoft.ats.domain.hibernate.Auditable;


public class ParentRiskDaoImpl extends BaseDaoImpl<ParentRisk> implements ParentRiskDao
{

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.hibernate.BaseDaoImpl#findAll()
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<ParentRisk> findAll() throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createQuery(
                "FROM ParentRisk ORDER BY category.name, name");
            return (List<ParentRisk>) qry.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<ParentRisk> findByCategory(ParentRiskCategory category) throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createQuery(
                "FROM ParentRisk p WHERE (p.category = :category) ORDER BY p.name");
            qry.setParameter("category", category);
            return (List<ParentRisk>) qry.getResultList();
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
    public void save(ParentRisk entity) throws DaoException
    {
        throw new DaoException("Not implemented (read only data)");
    }

}