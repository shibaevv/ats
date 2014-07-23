package net.apollosoft.ats.audit.dao.hibernate;

import net.apollosoft.ats.audit.dao.ActionGroupDivisionDao;
import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.domain.hibernate.ActionGroupDivision;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class ActionGroupDivisionDaoImpl extends BaseDaoImpl<ActionGroupDivision> implements
    ActionGroupDivisionDao
{

    public void save(ActionGroupDivision entity) throws DaoException
    {
        super.save(entity);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.ActionGroupDivisionDao#delete(net.apollosoft.ats.audit.domain.hibernate.ActionGroupDivision)
     */
    public void delete(ActionGroupDivision entity) throws DaoException
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