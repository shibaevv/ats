package net.apollosoft.ats.audit.dao.hibernate;

import net.apollosoft.ats.audit.dao.AuditGroupDivisionDao;
import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.domain.hibernate.AuditGroupDivision;

public class AuditGroupDivisionDaoImpl extends BaseDaoImpl<AuditGroupDivision> implements
    AuditGroupDivisionDao
{

    public void save(AuditGroupDivision entity) throws DaoException
    {
        super.save(entity);

    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.AuditGroupDivisionDao#delete(net.apollosoft.ats.audit.domain.hibernate.AuditGroupDivision)
     */
    public void delete(AuditGroupDivision entity) throws DaoException
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

}
