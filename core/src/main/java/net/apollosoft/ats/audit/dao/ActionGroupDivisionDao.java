package net.apollosoft.ats.audit.dao;

import net.apollosoft.ats.audit.domain.hibernate.ActionGroupDivision;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface ActionGroupDivisionDao extends BaseDao<ActionGroupDivision>
{

    /**
     * 
     * @param groupDivision
     * @throws DaoException
     */
    void save(ActionGroupDivision groupDivision) throws DaoException;

    /**
     * 
     * @param groupDivision
     * @throws DaoException
     */
    void delete(ActionGroupDivision groupDivision) throws DaoException;

}