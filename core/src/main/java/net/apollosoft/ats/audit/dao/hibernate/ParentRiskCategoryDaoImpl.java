package net.apollosoft.ats.audit.dao.hibernate;

import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.dao.ParentRiskCategoryDao;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ParentRiskCategory;
import net.apollosoft.ats.domain.hibernate.Auditable;

public class ParentRiskCategoryDaoImpl extends BaseDaoImpl<ParentRiskCategory> implements
    ParentRiskCategoryDao
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
    public void save(ParentRiskCategory entity) throws DaoException
    {
        throw new DaoException("Not implemented (read only data)");
    }

}