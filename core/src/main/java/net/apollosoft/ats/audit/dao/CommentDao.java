package net.apollosoft.ats.audit.dao;

import java.util.Date;
import java.util.List;

import net.apollosoft.ats.audit.domain.hibernate.Action;
import net.apollosoft.ats.audit.domain.hibernate.Comment;
import net.apollosoft.ats.audit.search.CommentSearchCriteria;

import org.springframework.stereotype.Repository;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Repository
public interface CommentDao extends BaseDao<Comment>
{

    /**
     * Finder:
     * @param criteria
     * @return
     * @throws DaoException
     */
    List<Comment> findByCriteria(CommentSearchCriteria criteria) throws DaoException;

    /**
     * 
     * @param action
     * @param detail
     * @return
     * @throws DaoException
     */
    Comment findByActionDetail(Action action, String detail, Date createdDate) throws DaoException;

    /**
     * 
     * @param actionId
     * @return
     * @throws DaoException
     */
    Integer maxDocuments(Long actionId) throws DaoException;

}