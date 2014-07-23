package net.apollosoft.ats.audit.dao;

import java.util.List;

import net.apollosoft.ats.domain.hibernate.security.Division;
import net.apollosoft.ats.domain.hibernate.security.Group;

import org.springframework.stereotype.Repository;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Repository
public interface DivisionDao extends BaseDao<Division>
{

    /**
     * 
     * @param groupId
     * @return
     * @throws DaoException
     */
    List<Division> findByGroup(Group group) throws DaoException;

    /**
     * 
     * @param name
     * @return
     * @throws DaoException
     */
    List<Division> findByName(String division, String group) throws DaoException;

}