package net.apollosoft.ats.audit.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.dao.ReportTypeDao;
import net.apollosoft.ats.audit.dao.UserMatrixDao;
import net.apollosoft.ats.audit.dao.security.RoleDao;
import net.apollosoft.ats.domain.hibernate.Auditable;
import net.apollosoft.ats.domain.hibernate.refdata.ReportType;
import net.apollosoft.ats.domain.hibernate.security.Role;
import net.apollosoft.ats.domain.hibernate.security.UserMatrix;
import net.apollosoft.ats.domain.security.IUserMatrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class UserMatrixDaoImpl extends BaseDaoImpl<UserMatrix> implements UserMatrixDao
{

    @Autowired
    @Qualifier("reportTypeDao")
    //@Resource(name="reportTypeDao")
    private ReportTypeDao reportTypeDao;

    @Autowired
    @Qualifier("roleDao")
    //@Resource(name="roleDao")
    private RoleDao roleDao;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.hibernate.BaseDaoImpl#delete(net.apollosoft.ats.audit.domain.hibernate.Auditable)
     */
    @Override
    public void delete(Auditable<?> entity) throws DaoException
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

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.UserMatrixDao#findByUniqueKey(java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)
     */
    public UserMatrix findByUniqueKey(IUserMatrix dto) throws DaoException
    {
        try
        {
            Long groupId = dto.getGroup().getId();
            Long divisionId = dto.getDivision().getId();
            String userId = dto.getUser().getId();
            Long roleId = dto.getRole().getId();
            Long reportTypeId = dto.getReportType().getId();
            
            String sql = "FROM UserMatrix WHERE user.id = :userId AND role.id = :roleId AND reportType.id = :reportTypeId";
            if (groupId == null)
            {
                sql += " AND group.id IS NULL";
            }
            else
            {
                sql += " AND group.id = :groupId";
            }
            if (divisionId == null)
            {
                sql += " AND division.id IS NULL";
            }
            else
            {
                sql += " AND division.id = :divisionId";
            }
            Query qry = getEntityManager().createQuery(sql);
            qry.setParameter("userId", userId);
            qry.setParameter("roleId", roleId);
            qry.setParameter("reportTypeId", reportTypeId);
            if (groupId != null)
            {
                qry.setParameter("groupId", groupId);
            }
            if (divisionId != null)
            {
                qry.setParameter("divisionId", divisionId);
            }
            return (UserMatrix) qry.getSingleResult();
        }
        catch (NoResultException ignore)
        {
            //No entity found for query
            return null;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.UserMatrixDao#findByUserId(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<UserMatrix> findByUserId(String userId) throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createQuery("FROM UserMatrix WHERE user.id = :userId");
            qry.setParameter("userId", userId);
            return (List<UserMatrix>) qry.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.UserMatrixDao#findByRoleId(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public List<UserMatrix> findByRoleId(Long roleId) throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createQuery("FROM UserMatrix WHERE role.id = :roleId");
            qry.setParameter("roleId", roleId);
            return (List<UserMatrix>) qry.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.UserMatrixDao#findReportTypes(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<ReportType> findReportTypes(String userId) throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createQuery(
                "SELECT DISTINCT(reportType.id) FROM UserMatrix WHERE user.id = :userId");
            qry.setParameter("userId", userId);
            List<Long> ids = (List<Long>) qry.getResultList();
            List<ReportType> result = new ArrayList<ReportType>();
            for (Long id : ids)
            {
                result.add(reportTypeDao.findById(id));
            }
            return result;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.UserMatrixDao#findUserRoles(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<Role> findRoles(String userId) throws DaoException
    {
        try
        {
            Query qry = getEntityManager().createQuery(
                "SELECT DISTINCT(role.id) FROM UserMatrix WHERE user.id = :userId ORDER BY role.id DESC");
            qry.setParameter("userId", userId);
            List<Long> ids = (List<Long>) qry.getResultList();
            List<Role> result = new ArrayList<Role>();
            for (Long id : ids)
            {
                result.add(roleDao.findById(id));
            }
            return result;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

}