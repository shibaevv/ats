package net.apollosoft.ats.audit.dao.hibernate;

import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.dao.DocumentDao;
import net.apollosoft.ats.domain.hibernate.Document;

/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class DocumentDaoImpl extends BaseDaoImpl<Document> implements DocumentDao
{

    public void save(Document entity) throws DaoException
    {
        super.save(entity);
    }

}