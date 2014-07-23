package net.apollosoft.ats.audit.dao;

import net.apollosoft.ats.audit.domain.hibernate.ActionResponsible;

public interface ActionResponsibleDao extends BaseDao<ActionResponsible>
{

    /**
     * 
     */
    void save(ActionResponsible actionResponsible) throws DaoException;

    /**
     * 
     * @param actionResponsible
     * @throws DaoException
     */
    void delete(ActionResponsible actionResponsible) throws DaoException;

    /**
     * 
     * @param actionId
     * @param userId
     * @return
     * @throws DaoException
     */
    ActionResponsible find(Long actionId, String userId) throws DaoException;

}