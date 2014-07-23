package net.apollosoft.ats.audit.dao;

import java.util.List;

import net.apollosoft.ats.audit.search.UserSearchCriteria;
import net.apollosoft.ats.domain.hibernate.security.Division;
import net.apollosoft.ats.domain.hibernate.security.Group;
import net.apollosoft.ats.domain.hibernate.security.User;

import org.springframework.stereotype.Repository;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Repository
public interface UserDao extends BaseDao<User>
{

    /**
     * 
     * @param login
     * @return
     * @throws DaoException
     */
    List<User> findByLogin(String login) throws DaoException;

    /**
     * Find by names.
     * @param firstName
     * @param lastName
     * @param login (optional)
     * @return
     * @throws DaoException
     */
    List<User> findByNames(String firstName, String lastName, String login) throws DaoException;

    /**
     * Format: FirstName LastName - Group - Login
     * @param firstNameLike
     * @param lastNameLike
     * @param active - non-terminated
     * @return
     * @throws DaoException
     */
    List<User> findNameLike(String firstNameLike, String lastNameLike, Boolean active) throws DaoException;

    /**
     * 
     * @param criteria
     * @return
     * @throws DaoException
     */
    List<User> findByCriteria(UserSearchCriteria criteria) throws DaoException;

    /**
     * 
     * @param group
     * @param division
     * @param userTypeId
     * @return
     * @throws DaoException
     */
    List<User> findByGroupDivision(Group group, Division division, Long userTypeId) throws DaoException;

}