package net.apollosoft.ats.audit.dao.hibernate;

import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.dao.ReportTypeDao;
import net.apollosoft.ats.domain.hibernate.Auditable;
import net.apollosoft.ats.domain.hibernate.refdata.ReportType;

public class ReportTypeDaoImpl extends BaseDaoImpl<ReportType> implements ReportTypeDao
{

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
    public void save(ReportType entity) throws DaoException
    {
        throw new DaoException("Not implemented (read only data)");
    }

}
