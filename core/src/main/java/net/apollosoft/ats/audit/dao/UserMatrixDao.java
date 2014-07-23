package net.apollosoft.ats.audit.dao;

import java.util.List;

import net.apollosoft.ats.domain.hibernate.refdata.ReportType;
import net.apollosoft.ats.domain.hibernate.security.Role;
import net.apollosoft.ats.domain.hibernate.security.UserMatrix;
import net.apollosoft.ats.domain.security.IUserMatrix;

import org.springframework.stereotype.Repository;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Repository
public interface UserMatrixDao extends BaseDao<UserMatrix>
{

    /**
     * 
     * @param userId
     * @param roleId
     * @param reportTypeId
     * @param groupId
     * @param divisionId
     * @return
     */
    UserMatrix findByUniqueKey(IUserMatrix dto) throws DaoException;

    /**
     * 
     * @param userId
     * @return
     * @throws DaoException
     */
    List<UserMatrix> findByUserId(String userId) throws DaoException;

    /**
     * 
     * @param roleId
     * @return
     * @throws DaoException
     */
    List<UserMatrix> findByRoleId(Long roleId) throws DaoException;

    /**
     * 
     * @param userId
     * @return
     * @throws DaoException
     */
    List<Role> findRoles(String userId) throws DaoException;

    /**
     * 
     * @return
     * @throws DaoException
     */
    List<ReportType> findReportTypes(String userId) throws DaoException;

}