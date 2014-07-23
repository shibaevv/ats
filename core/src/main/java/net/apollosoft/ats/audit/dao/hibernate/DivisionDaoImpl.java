package net.apollosoft.ats.audit.dao.hibernate;

import java.util.List;

import javax.persistence.Query;

import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.dao.DivisionDao;
import net.apollosoft.ats.domain.hibernate.Auditable;
import net.apollosoft.ats.domain.hibernate.security.Division;
import net.apollosoft.ats.domain.hibernate.security.Group;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class DivisionDaoImpl extends BaseDaoImpl<Division> implements DivisionDao
{

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.DivisionDao#findByGroup(Group)
     */
    @SuppressWarnings("unchecked")
    public List<Division> findByGroup(Group group) throws DaoException
    {
        try
        {
            Query qry = getEntityManager()
                .createQuery(
                    "FROM Division d WHERE (d.group = :group) AND (d.deletedBy IS NULL) AND (d.deletedDate IS NULL) ORDER BY d.name");
            qry.setParameter("group", group);
            return (List<Division>) qry.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.DivisionDao#findByName(java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<Division> findByName(String division, String group) throws DaoException
    {
        try
        {
            String sql = "FROM Division WHERE name = :division AND group.name = :group";
            Query qry = getEntityManager().createQuery(sql);
            qry.setParameter("division", division);
            qry.setParameter("group", group);
            return (List<Division>) qry.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.BaseDao#save(java.lang.Object)
     */
    public void save(Division entity) throws DaoException
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