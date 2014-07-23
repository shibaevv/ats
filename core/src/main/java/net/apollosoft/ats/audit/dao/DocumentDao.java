package net.apollosoft.ats.audit.dao;

import net.apollosoft.ats.domain.hibernate.Document;

import org.springframework.stereotype.Repository;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Repository
public interface DocumentDao extends BaseDao<Document>
{

    /**
     * 
     */
    void save(Document document) throws DaoException;

}