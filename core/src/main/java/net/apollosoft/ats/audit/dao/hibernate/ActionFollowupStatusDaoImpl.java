package net.apollosoft.ats.audit.dao.hibernate;

import net.apollosoft.ats.audit.dao.ActionFollowupStatusDao;
import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.domain.hibernate.refdata.ActionFollowupStatus;
import net.apollosoft.ats.domain.hibernate.Auditable;

/**
 *
 * @author dsasmito (Dion SASMITO)
 */
public class ActionFollowupStatusDaoImpl extends BaseDaoImpl<ActionFollowupStatus> implements
    ActionFollowupStatusDao
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
    public void save(ActionFollowupStatus entity) throws DaoException
    {
        throw new DaoException("Not implemented (read only data)");
    }

}