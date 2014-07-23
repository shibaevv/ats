package net.apollosoft.ats.audit.dao;

import net.apollosoft.ats.domain.hibernate.Template;

import org.springframework.stereotype.Repository;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Repository
public interface TemplateDao extends BaseDao<Template>
{

    /**
     * 
     * @param entity
     * @throws DaoException
     */
    void save(Template entity) throws DaoException;

    /**
     * 
     * @param reference - unique
     * @return
     * @throws DaoException
     */
    Template findByReference(String reference) throws DaoException;

}