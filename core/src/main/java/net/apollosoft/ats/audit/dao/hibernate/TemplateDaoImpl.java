package net.apollosoft.ats.audit.dao.hibernate;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.dao.TemplateDao;
import net.apollosoft.ats.domain.hibernate.Template;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class TemplateDaoImpl extends BaseDaoImpl<Template> implements TemplateDao
{

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.hibernate.BaseDaoImpl#save(java.lang.Object)
     */
    @Override
    public void save(Template entity) throws DaoException
    {
        super.save(entity);
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.hibernate.BaseDaoImpl#findAll()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Template> findAll() throws DaoException
    {
        try
        {
            return (List<Template>) getEntityManager().createQuery(
                "FROM Template ORDER BY reference").getResultList();
        }
        catch (Exception e)
        {
            throw new DaoException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.dao.TemplateDao#findByReference(java.lang.String)
     */
    public Template findByReference(String reference) throws DaoException
    {
        try
        {
            Query qry = getEntityManager()
                .createQuery("FROM Template WHERE reference = :reference");
            qry.setParameter("reference", reference);
            return (Template) qry.getSingleResult();
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

}