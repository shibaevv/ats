package net.apollosoft.ats.audit.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.apollosoft.ats.audit.dao.BaseDao;
import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.domain.hibernate.Auditable;
import net.apollosoft.ats.domain.hibernate.refdata.ReportType;
import net.apollosoft.ats.domain.hibernate.security.GroupDivision;
import net.apollosoft.ats.domain.hibernate.security.User;
import net.apollosoft.ats.domain.hibernate.security.UserMatrix;
import net.apollosoft.ats.domain.security.IDivision;
import net.apollosoft.ats.domain.security.IGroup;
import net.apollosoft.ats.search.Pagination;
import net.apollosoft.ats.util.Pair;
import net.apollosoft.ats.util.ThreadLocalUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * TODO: <T extends BaseModel<ID>>
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public abstract class BaseDaoImpl<T> implements BaseDao<T>
{

    /** logger. */
    protected final Log LOG = LogFactory.getLog(getClass());

    /** persistentClass. */
    private final Class<T> persistentClass;

    /** EntityManager. */
    @PersistenceContext//(unitName = "ats")
    private EntityManager em;

    /**
     * 
     */
    @SuppressWarnings("unchecked")
    protected BaseDaoImpl()
    {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType)
        {
            ParameterizedType pType = (ParameterizedType) type;
            this.persistentClass = (Class<T>) pType.getActualTypeArguments()[0];
        }
        else
        {
            this.persistentClass = null;
        }
    }

    /**
     * @return the em
     */
    protected EntityManager getEntityManager()
    {
        return em;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.BaseDao#delete(net.apollosoft.ats.audit.domain.hibernate.Auditable)
     */
    public void delete(Auditable<?> entity) throws DaoException
    {
        if (entity == null)
        {
            return;
        }
        try
        {
            //get current user/date
            final Date now = ThreadLocalUtils.getDate();
            final Pair<String> user = ThreadLocalUtils.getUser();
            entity.setDeletedDate(now);
            entity.setDeletedBy(new User(user.getId()));
            getEntityManager().persist(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.BaseDao#unDelete(net.apollosoft.ats.audit.domain.hibernate.Auditable)
     */
    public void unDelete(Auditable<?> entity) throws DaoException
    {
        if (entity == null)
        {
            return;
        }
        try
        {
            entity.setDeletedDate(null);
            entity.setDeletedBy(null);
            getEntityManager().persist(entity);
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
    public List<T> findAll() throws DaoException
    {
        try
        {
            return (List<T>) getEntityManager().createQuery("FROM " + persistentClass.getName())
                .getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.BaseDao#findById(java.io.Serializable)
     */
    public T findById(Serializable id) throws DaoException
    {
        try
        {
            return id == null ? null : getEntityManager().find(persistentClass, id);
        }
        catch (EntityNotFoundException ignore)
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
     * @see net.apollosoft.ats.audit.dao.BaseDao#save(java.lang.Object)
     */
    public void save(T entity) throws DaoException
    {
        try
        {
            getEntityManager().persist(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.BaseDao#merge(java.lang.Object)
     */
    public void merge(T entity) throws DaoException
    {
        try
        {
            getEntityManager().merge(entity);
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    protected String updatePagination(String hsql, Pagination p, Map<String, Object> parameters)
    {
        return updatePagination(hsql, "SELECT count(id) ", p, parameters);
    }

    /**
     * set totalRecords and orderBy clause (if applicable)
     * @param hsql Main hsql (eg FROM Domain WHERE field = :value), no sorting
     * @param p ()
     * @param parameters
     * @return updated hsql (if modified) or original hsql (if not modified)
     */
    protected String updatePagination(String hsql, String countSql, Pagination p,
        Map<String, Object> parameters)
    {
        if (p == null)
        {
            return hsql;
        }

        //set totalRecords
        Query qry = getEntityManager().createQuery(countSql + hsql);
        updateQuery(qry, parameters);
        Number count = (Number) qry.getSingleResult();
        p.setTotalRecords(count.intValue());

        //set orderBy clause
        return updateSorting(hsql, p);
    }

    /**
     * set orderBy clause (if applicable)
     * @param hsql Main hsql (eg FROM Domain WHERE field = :value), no sorting
     * @param p ()
     * @return updated hsql (if modified) or original hsql (if not modified)
     */
    protected String updateSorting(String hsql, Pagination p)
    {
        //set orderBy clause
        String sort = p.getSort();
        if (StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(p.getDir()))
        {
            hsql += " ORDER BY";
            // sort format: 'alias_property1__alias_property2'
            String[] sorts = sort.split("__");
            for (int i = 0; i < sorts.length; i++)
            {
                hsql += i == 0 ? " " : ",";
                String s = sorts[i];
                s = s.replace('_', '.');
                hsql += s + " " + p.getDir();
            }
        }
        return hsql;
    }

    /**
     * set query parameters
     * @param qry
     * @param parameters
     */
    protected void updateQuery(Query qry, Map<String, Object> parameters)
    {
        Iterator<Entry<String, Object>> iter = parameters.entrySet().iterator();
        while (iter.hasNext())
        {
            Entry<String, Object> entry = iter.next();
            qry.setParameter(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 
     * @param userMatrix
     * @param reportAlias
     * @param parameters
     * @return
     */
    protected String whereSecurity(UserMatrix userMatrix, String reportAlias,
        Map<String, Object> parameters)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(" AND (");
        int idx = parameters.size();
        sb.append("((:r").append(idx).append(" IS NULL OR ").append(reportAlias).append(" = :r")
            .append(idx).append(")");
        sb.append(" AND (:g").append(idx).append(" IS NULL OR :g").append(idx).append(
            " IN ELEMENTS (groups))");
        sb.append(" AND (:d").append(idx).append(" IS NULL OR :d").append(idx).append(
            " IN ELEMENTS (divisions)))");
        parameters.put("r" + idx, userMatrix.getReportType());
        parameters.put("g" + idx, userMatrix.getGroup());
        parameters.put("d" + idx, userMatrix.getDivision());
        sb.append(")");
        return sb.toString();
    }

    /**
     * Thats pretty big SQL (but fast and generic)
     * @param groupDivisions
     * @param userReportTypes
     * @param userMatrix
     * @param reportAlias - different name for audit/action queries
     * @param parameters
     * @return
     * @throws DaoException
     */
    protected String whereSecurity(List<GroupDivision> groupDivisions,
        List<ReportType> userReportTypes, List<UserMatrix> userMatrix, String reportAlias,
        Map<String, Object> parameters) throws DaoException
    {
        StringBuffer sb = new StringBuffer();
        //apply security matrix rules
        if (groupDivisions == null || groupDivisions.isEmpty())
        {
            int size = userMatrix.size();
            for (int i = 0; i < size; i++)
            {
                UserMatrix item = userMatrix.get(i);
                sb.append(i == 0 ? " AND (" : " OR "); // first or second+
                int idx = parameters.size();
                sb.append("((:r").append(idx).append(" IS NULL OR ").append(reportAlias).append(
                    " = :r").append(idx).append(")");
                sb.append(" AND (:g").append(idx).append(" IS NULL OR :g").append(idx).append(
                    " IN ELEMENTS (groups))");
                sb.append(" AND (:d").append(idx).append(" IS NULL OR :d").append(idx).append(
                    " IN ELEMENTS (divisions)))");
                parameters.put("r" + idx, item.getReportType());
                parameters.put("g" + idx, item.getGroup());
                parameters.put("d" + idx, item.getDivision());
                sb.append(i == size - 1 ? ")" : ""); // last
            }
        }
        //apply search criteria (plus reportType security matrix rules)
        else
        {
            List<IGroup> groupAllDivisions = new ArrayList<IGroup>();
            int size = groupDivisions.size();
            for (int i = 0; i < size; i++)
            {
                GroupDivision item = groupDivisions.get(i);
                IGroup g = item.getGroup();
                IDivision d = item.getDivision();
                // optimisation
                // if all division(s) within group, eg BFS All (has to be first) - ignore group's individual divisions
                if (d == null)
                {
                    groupAllDivisions.add(g); // add group - all division(s)
                }
                if (d != null && groupAllDivisions.contains(g))
                {
                    continue; // we already have group - all division(s), no need for specific division
                }
                //
                sb.append(i == 0 ? " AND (" : " OR "); // first or second+
                int idx = parameters.size();
                sb.append("((:g").append(idx).append(" IS NULL OR :g").append(idx).append(
                    " IN ELEMENTS (groups))");
                sb.append(" AND (:d").append(idx).append(" IS NULL OR :d").append(idx).append(
                    " IN ELEMENTS (divisions)))");
                parameters.put("g" + idx, g);
                parameters.put("d" + idx, d);
                sb.append(i == size - 1 ? ")" : ""); // last
            }
            //
            size = userReportTypes.size();
            for (int i = 0; i < size; i++)
            {
                ReportType item = userReportTypes.get(i);
                sb.append(i == 0 ? " AND (" : " OR "); // first or second+
                int idx = parameters.size();
                sb.append("(:r").append(idx).append(" IS NULL OR ").append(reportAlias).append(
                    " = :r").append(idx).append(")");
                parameters.put("r" + idx, item);
                sb.append(i == size - 1 ? ")" : ""); // last
            }
        }
        return sb.toString();
    }

    /**
     * 
     * @param groupDivisions - array of string "groupId,divisionId"
     * @param userReportTypes
     * @param userMatrix
     * @param alias - audit or action
     * @param parameters
     * @return
     * @throws DaoException
     */
    protected String whereSecurityRaw(String[] groupDivisionIds,
        List<ReportType> userReportTypes, List<UserMatrix> userMatrix, String alias,
        Map<String, Object> parameters) throws DaoException
    {
        //search audit group/division (selected from user security matrix)
        List<Long[]> groupDivisions = new ArrayList<Long[]>();
        List<Long> groupAllDivisions = new ArrayList<Long>();
        for (String groupDivision : groupDivisionIds)
        {
            String[] ids = StringUtils.split(groupDivision, ',');
            Long groupId = StringUtils.isBlank(ids[0]) ? null : NumberUtils.createLong(ids[0]);
            Long divisionId = ids.length < 2 || StringUtils.isBlank(ids[1]) ? null : NumberUtils
                .createLong(ids[1]);
            // optimisation
            // if all division(s) within group, eg BFS All (has to be first) - ignore group's individual divisions
            if (divisionId == null)
            {
                groupAllDivisions.add(groupId); // add group - all division(s)
            }
            if (divisionId != null && groupAllDivisions.contains(groupId))
            {
                continue; // we already have group - all division(s), no need for specific division
            }
            //
            groupDivisions.add(new Long[]
            {
                groupId, divisionId
            });
        }

        StringBuffer sb = new StringBuffer();
        if (groupDivisions.isEmpty())
        {
            int size = userMatrix.size();
            for (int i = 0; i < size; i++)
            {
                UserMatrix item = userMatrix.get(i);
                sb.append(i == 0 ? " AND (" : " OR "); // first or second+
                int idx = parameters.size();
                sb.append("((:r").append(idx).append(" IS NULL OR v_report_type_id = :r").append(
                    idx).append(")");
                sb.append(" AND (:g").append(idx).append(" IS NULL OR :g").append(idx).append(
                    " IN (SELECT group_id FROM ats_").append(alias).append(
                    "_group_division gd WHERE gd.").append(alias).append("_id = v_").append(alias)
                    .append("_id))");
                sb.append(" AND (:d").append(idx).append(" IS NULL OR :d").append(idx).append(
                    " IN (SELECT division_id FROM ats_").append(alias).append(
                    "_group_division gd WHERE gd.").append(alias).append("_id = v_").append(alias)
                    .append("_id)))");
                parameters.put("r" + idx, item.getReportType() == null ? null : item
                    .getReportType().getId());
                parameters.put("g" + idx, item.getGroup() == null ? null : item.getGroup().getId());
                parameters.put("d" + idx, item.getDivision() == null ? null : item.getDivision()
                    .getId());
                sb.append(i == size - 1 ? ")" : ""); // last
            }
        }
        //apply search criteria (plus reportType security matrix rules)
        else
        {
            int size = groupDivisions.size();
            for (int i = 0; i < size; i++)
            {
                Long[] item = groupDivisions.get(i);
                sb.append(i == 0 ? " AND (" : " OR "); // first or second+
                int idx = parameters.size();
                sb.append("((:g").append(idx).append(" IS NULL OR :g").append(idx).append(
                    " IN (SELECT group_id FROM ats_").append(alias).append(
                    "_group_division gd WHERE gd.").append(alias).append("_id = v_").append(alias)
                    .append("_id))");
                sb.append(" AND (:d").append(idx).append(" IS NULL OR :d").append(idx).append(
                    " IN (SELECT division_id FROM ats_").append(alias).append(
                    "_group_division gd WHERE gd.").append(alias).append("_id = v_").append(alias)
                    .append("_id)))");
                parameters.put("g" + idx, item[0]);
                parameters.put("d" + idx, item[1]);
                sb.append(i == size - 1 ? ")" : ""); // last
            }
            size = userReportTypes.size();
            for (int i = 0; i < size; i++)
            {
                ReportType item = userReportTypes.get(i);
                sb.append(i == 0 ? " AND (" : " OR "); // first or second+
                int idx = parameters.size();
                sb.append("(:r").append(idx).append(" IS NULL OR v_report_type_id = :r")
                    .append(idx).append(")");
                parameters.put("r" + idx, item == null ? null : item.getId());
                sb.append(i == size - 1 ? ")" : ""); // last
            }
        }
        return sb.toString();
    }

    /**
     * 
     * @param s
     * @return
     */
    protected static String nullIfBlank(String s)
    {
        return StringUtils.isBlank(s) ? null : s;
    }

    /**
     * 
     * @param s
     * @return
     */
    protected static String nullIfBlankLike(String s)
    {
        return StringUtils.isBlank(s) ? null : s + "%";
    }

}