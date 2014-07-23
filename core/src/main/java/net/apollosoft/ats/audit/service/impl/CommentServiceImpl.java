package net.apollosoft.ats.audit.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.apollosoft.ats.audit.dao.CommentDao;
import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.dao.DocumentDao;
import net.apollosoft.ats.audit.domain.IComment;
import net.apollosoft.ats.audit.domain.dto.CommentDto;
import net.apollosoft.ats.audit.domain.hibernate.Comment;
import net.apollosoft.ats.audit.domain.hibernate.CommentDocument;
import net.apollosoft.ats.audit.search.CommentSearchCriteria;
import net.apollosoft.ats.audit.service.CommentService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.domain.IDocument;
import net.apollosoft.ats.domain.dto.DocumentDto;
import net.apollosoft.ats.domain.dto.security.UserDto;
import net.apollosoft.ats.domain.hibernate.Document;
import net.apollosoft.ats.util.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class CommentServiceImpl implements CommentService
{

    /** logger. */
    //private static final Log LOG = LogFactory.getLog(CommentServiceImpl.class);

    @Autowired
    @Qualifier("commentDao")
    //@Resource(name="commentDao")
    private CommentDao commentDao;

    @Autowired
    @Qualifier("documentDao")
    //@Resource(name="documentDao")
    private DocumentDao documentDao;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.ActionService#find(net.apollosoft.ats.audit.search.ActionSearchCriteria)
     */
    public List<IComment> find(CommentSearchCriteria criteria) throws ServiceException
    {
        try
        {
            List<Comment> entities = commentDao.findByCriteria(criteria);
            //use dto objects
            List<IComment> result = new ArrayList<IComment>();
            for (Comment entity : entities)
            {
                result.add(convert(entity));
            }
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.CommentService#findById(java.lang.Long)
     */
    public IComment findById(Long commentId) throws ServiceException
    {
        try
        {
            Comment entity = commentDao.findById(commentId);
            IComment result = convert(entity);
            return result;
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.CommentService#addDocuments(net.apollosoft.ats.audit.domain.hibernate.Comment, java.util.List)
     */
    public void addDocuments(Comment comment, List<IDocument> newItems) throws ServiceException
    {
        try
        {
            //all current action responsible users
            List<IDocument> oldItems = new ArrayList<IDocument>();
            List<CommentDocument> linkItems = comment.getCommentDocuments();
            for (CommentDocument linkItem : linkItems)
            {
                if (newItems.contains(linkItem.getDocument()))
                {
                    if (linkItem.isDeleted())
                    {
                        commentDao.unDelete(linkItem);
                    }
                }
                else
                {
                    //logically delete
                    commentDao.delete(linkItem);
                }
                oldItems.add(linkItem.getDocument());
            }
            //add new
            for (IDocument item : newItems)
            {
                if (!oldItems.contains(item))
                {
                    CommentDocument linkItem = new CommentDocument();
                    linkItem.setComment(comment);
                    Document document;
                    if (item instanceof Document)
                    {
                        document = (Document) item;
                    }
                    else
                    {
                        document = convert(item);
                    }
                    linkItem.setDocument(document);
                    if (document.getId() == null)
                    {
                        documentDao.save(document);
                    }
                    linkItems.add(linkItem);
                }
            }
        }
        catch (Exception e)
        {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 
     * @param entity
     * @return
     * @throws DomainException
     * @throws DaoException
     */
    private Document convert(IDocument entity) throws DomainException
    {
        Document result = new Document();
        BeanUtils.copyProperties(entity, result, IDocument.IGNORE);
        result.setContent(entity.getContent()); //ignored
        return result;
    }

    /**
     * 
     * @param entity
     * @return
     * @throws DomainException
     * @throws DaoException 
     */
    private CommentDto convert(Comment entity) throws DomainException, DaoException
    {
        //
        //IssueDto issue = new IssueDto(entity.getAction().getIssue());
        //
        //ActionDto action = new ActionDto(entity.getAction());
        //action.setIssue(issue);
        //
        CommentDto result = new CommentDto(entity);
        //result.setAction(action);
        //
        if (entity.getCreatedBy() != null)
        {
            result.setCreatedBy(new UserDto(entity.getCreatedBy()));
        }
        //copy documents (plain)
        for (IDocument document : entity.getDocuments())
        {
            result.getDocuments().add(new DocumentDto(document));
        }
        return result;
    }

}