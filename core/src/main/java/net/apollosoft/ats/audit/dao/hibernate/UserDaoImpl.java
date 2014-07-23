package net.apollosoft.ats.audit.dao.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.dao.DivisionDao;
import net.apollosoft.ats.audit.dao.GroupDao;
import net.apollosoft.ats.audit.dao.UserDao;
import net.apollosoft.ats.audit.search.UserSearchCriteria;
import net.apollosoft.ats.domain.hibernate.Auditable;
import net.apollosoft.ats.domain.hibernate.security.Division;
import net.apollosoft.ats.domain.hibernate.security.Group;
import net.apollosoft.ats.domain.hibernate.security.User;
import net.apollosoft.ats.domain.security.IGroup;
import net.apollosoft.ats.domain.security.IUser;
import net.apollosoft.ats.search.Pagination;
import net.apollosoft.ats.search.SearchCriteria;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao
{

    @Autowired
    @Qualifier("groupDao")
    //@Resource(name="actionDao")
    private GroupDao groupDao;

    @Autowired
    @Qualifier("divisionDao")
    //@Resource(name="actionDao")
    private DivisionDao divisionDao;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.hibernate.BaseDaoImpl#findById(java.io.Serializable)
     */
    @Override
    public User findById(Serializable id) throws DaoException
    {
        try
        {
            String userId = (String) id;
            //userId formatted as char(5) pre-pended with 0 (if required)
            if (StringUtils.length(userId) < 5)
            {
                userId = StringUtils.leftPad(userId, 5, '0');
            }
            return super.findById(userId);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.BaseDao#findAll()
     */
    @SuppressWarnings("unchecked")
    public List<User> findAll() throws DaoException
    {
        try
        {
            return (List<User>) getEntityManager().createQuery("FROM User").getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.UserDao#findByLogin(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<User> findByLogin(String login) throws DaoException
    {
        if (StringUtils.isBlank(login))
        {
            return null;
        }
        try
        {
            Query qry = getEntityManager().createQuery("FROM User WHERE login = :login");
            qry.setParameter("login", login);
            return (List<User>) qry.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.UserDao#findByNames(java.lang.String, java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<User> findByNames(String firstName, String lastName, String login)
        throws DaoException
    {
        //no empty search criteria
        if (StringUtils.isBlank(firstName) && StringUtils.isBlank(lastName)
            && StringUtils.isBlank(login))
        {
            return new ArrayList<User>();
        }
        try
        {
            //TODO: use named query  AND (status != 'T')
            Query qry = getEntityManager()
                .createQuery(
                    "FROM User WHERE (:firstName IS NULL OR firstName = :firstName) AND (:lastName IS NULL OR lastName = :lastName) AND (:login IS NULL OR login = :login)");
            qry.setParameter("firstName", nullIfBlank(firstName));
            qry.setParameter("lastName", nullIfBlank(lastName));
            qry.setParameter("login", nullIfBlank(login));
            qry.setMaxResults(SearchCriteria.DEFAULT_MAX);
            return (List<User>) qry.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.UserDao#findNameLike(java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<User> findNameLike(String firstNameLike, String lastNameLike, Boolean active) throws DaoException
    {
        try
        {
            if (StringUtils.isBlank(firstNameLike) && StringUtils.isBlank(lastNameLike))
            {
                return Collections.EMPTY_LIST;
            }
            StringBuffer sb = new StringBuffer("FROM User WHERE 1=1");
            if (StringUtils.isBlank(firstNameLike))
            {
                firstNameLike = lastNameLike;
                sb.append(" AND ((firstName LIKE :firstNameLike) OR (lastName LIKE :lastNameLike))");
            }
            else if (StringUtils.isBlank(lastNameLike))
            {
                lastNameLike = firstNameLike;
                sb.append(" AND ((firstName LIKE :firstNameLike) OR (lastName LIKE :lastNameLike))");
            }
            else
            {
                sb.append(" AND (:firstNameLike IS NULL OR firstName LIKE :firstNameLike) AND (:lastNameLike IS NULL OR lastName LIKE :lastNameLike)");
            }
            //
            if (Boolean.TRUE.equals(active))
            {
                sb.append(" AND (status != '" + IUser.TERMINATED + "')"); //Active or Leave
            }
            else if (Boolean.FALSE.equals(active))
            {
                sb.append(" AND (status = '" + IUser.TERMINATED + "')");
            }
            //
            String sql = sb.toString();
            Query qry = getEntityManager().createQuery(sql);
            qry.setParameter("firstNameLike", nullIfBlankLike(firstNameLike));
            qry.setParameter("lastNameLike", nullIfBlankLike(lastNameLike));
            qry.setMaxResults(SearchCriteria.DEFAULT_MAX);
            return (List<User>) qry.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.UserDao#findByCriteria(net.apollosoft.ats.audit.search.UserSearchCriteria)
     */
    @SuppressWarnings("unchecked")
    public List<User> findByCriteria(UserSearchCriteria criteria) throws DaoException
    {
        try
        {
            Pagination p = criteria.getPagination();
            String sql = "FROM User u WHERE (1 = 1)";
            //
            Map<String, Object> parameters = new HashMap<String, Object>();
            //
            Long groupId = criteria.getGroupId();
            Long divisionId = criteria.getDivisionId();

            Group group = groupDao.findById(groupId);
            Division division = divisionDao.findById(divisionId);

            if (IGroup.GLOBAL_ID.equals(groupId) && division == null)
            {
                sql += " AND (u.groups IS EMPTY) AND (u.divisions IS EMPTY)"; //Thematic - All groups, All divisions
            }
            else if (IGroup.GLOBAL_ID.equals(groupId) && divisionId == null)
            {
                sql += " AND (u.groups IS EMPTY) AND (u.divisions IS NOT EMPTY)"; //Thematic - All groups, Any divisions
            }
            else if (!IGroup.GLOBAL_ID.equals(groupId) && groupId != null && division == null)
            {
                sql += " AND (:group IN ELEMENTS (u.groups)) AND (u.divisions IS EMPTY)";
                parameters.put("group", group);
            }
            else if (!IGroup.GLOBAL_ID.equals(groupId) && groupId != null && divisionId == null)
            {
                sql += " AND (:group IN ELEMENTS (u.groups)) AND (u.divisions IS NOT EMPTY)";
                parameters.put("group", group);
            }
            else
            {
                sql += " AND (:group IS NULL OR :group IN ELEMENTS (u.groups)) AND (:division IS NULL OR :division IN ELEMENTS (u.divisions))";
                parameters.put("group", group);
                parameters.put("division", division);
            }

            //
            if (p != null)
            {
                sql = updatePagination(sql, p, parameters);
            }
            //
            Query qry = getEntityManager().createQuery(sql);
            updateQuery(qry, parameters);
            if (p != null && p.getPageSize() > 0)
            {
                qry.setFirstResult(p.getStartIndex());
                qry.setMaxResults(p.getPageSize());
            }
            else
            {
                //to protect from returning thousends non-terminated users (and crushing server/browser)
                qry.setFirstResult(0);
                qry.setMaxResults(UserSearchCriteria.DEFAULT_MAX);
            }
            List<User> result = (List<User>) qry.getResultList();
            return result;
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.UserDao#findByGroupDivision(net.apollosoft.ats.audit.domain.security.IGroup, net.apollosoft.ats.audit.domain.security.IDivision, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    public List<User> findByGroupDivision(Group group, Division division, Long userTypeId)
        throws DaoException
    {
        try
        {
            String sql = "SELECT ugd.user FROM UserGroupDivision ugd WHERE (ugd.userType.userTypeId = :userTypeId) AND (ugd.group = :group) AND ((:division IS NULL AND ugd.division IS NULL) OR (:division IS NOT NULL AND ugd.division = :division))";
            Query qry = getEntityManager().createQuery(sql);
            qry.setParameter("userTypeId", userTypeId);
            qry.setParameter("group", group);
            qry.setParameter("division", division);
            return (List<User>) qry.getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

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
    public void save(User entity) throws DaoException
    {
        throw new DaoException("Not implemented (read only data)");
    }

}