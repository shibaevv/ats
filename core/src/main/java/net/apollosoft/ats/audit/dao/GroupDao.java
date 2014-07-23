package net.apollosoft.ats.audit.dao;

import net.apollosoft.ats.domain.hibernate.security.Group;

import org.springframework.stereotype.Repository;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Repository
public interface GroupDao extends BaseDao<Group>
{

    /**
     * 
     * @param name
     * @return
     * @throws DaoException
     */
    Group findByName(String name) throws DaoException;

}