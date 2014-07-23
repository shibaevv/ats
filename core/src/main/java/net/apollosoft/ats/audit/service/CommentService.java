package net.apollosoft.ats.audit.service;

import java.util.List;

import net.apollosoft.ats.audit.domain.IComment;
import net.apollosoft.ats.audit.domain.hibernate.Comment;
import net.apollosoft.ats.audit.search.CommentSearchCriteria;
import net.apollosoft.ats.domain.IDocument;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface CommentService
{

    /**
     * 
     * @param criteria
     * @return
     * @throws ServiceException
     */
    List<IComment> find(CommentSearchCriteria criteria) throws ServiceException;

    /**
     * 
     * @param commentId
     * @return
     * @throws ServiceException
     */
    IComment findById(Long commentId) throws ServiceException;

    /**
     * TODO: Internally used method (using hibernate mapped domain object)
     * @param comment
     * @param newItems
     * @throws ServiceException
     */
    void addDocuments(Comment comment, List<IDocument> newItems) throws ServiceException;

}