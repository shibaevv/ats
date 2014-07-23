package net.apollosoft.ats.audit.web.audit;

import java.util.List;

import net.apollosoft.ats.audit.domain.IComment;
import net.apollosoft.ats.audit.search.CommentSearchCriteria;
import net.apollosoft.ats.audit.security.UserPreferences;
import net.apollosoft.ats.audit.service.CommentService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.web.BaseController;
import net.apollosoft.ats.search.Pagination;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Controller
public class CommentController extends BaseController
{

    /** commentService */
    private final CommentService commentService;

    @Autowired
    public CommentController(@Qualifier("beanFactory") BeanFactory beanFactory,
        @Qualifier("commentService") CommentService commentService)
    {
        super(beanFactory);
        this.commentService = commentService;
    }

    @RequestMapping(value = "/comment/main.do", params = "dispatch=find")
    public String find(@RequestParam(value = "actionId", required = true) Long actionId,
        @RequestParam(value = "sort", required = false) String sort,
        @RequestParam(value = "dir", required = false) String dir, ModelMap model)
        throws ServiceException
    {
        //update UserPreferences
        UserPreferences prefs = getUserPreferences();
        //pagination criteria
        Pagination p = prefs.getCommentPagination();
        //
        CommentSearchCriteria criteria = new CommentSearchCriteria();
        criteria.setPagination(p);
        //filter criteria
        criteria.setActionId(actionId);
        criteria.setAuditLog(false);
        //
        List<IComment> entities = commentService.find(criteria);
        model.addAttribute("entities", entities);
        int maxDocuments = 0;
        for (IComment c : entities)
        {
            maxDocuments = Math.max(maxDocuments, c.getDocuments().size());
        }
        model.addAttribute("maxDocuments", maxDocuments);
        model.addAttribute("sort", p.getSort());
        model.addAttribute("dir", p.getDir());
        return "audit/issue/action/comment/find";
    }
    
    @RequestMapping(value = "/comment/main.do", params = "dispatch=findAuditLog")
    public String findAuditLog(@RequestParam(value = "actionId", required = true) Long actionId,
        @RequestParam(value = "sort", required = false) String sort,
        @RequestParam(value = "dir", required = false) String dir, ModelMap model)
        throws ServiceException
    {        
        Pagination p = new Pagination();
        CommentSearchCriteria criteria = new CommentSearchCriteria();
        // sort and dir is not working yet
        criteria.setPagination(p);
        //filter criteria
        criteria.setActionId(actionId);
        criteria.setAuditLog(true);
        //
        List<IComment> entities = commentService.find(criteria);
        model.addAttribute("entities", entities);     
        model.addAttribute("sort", p.getSort());
        model.addAttribute("dir", p.getDir());
        return "audit/issue/action/comment/findAuditLog";
    }

    @RequestMapping(value = "/comment/view.do", method = RequestMethod.GET)
    public String view(@RequestParam(value = "commentId", required = true) Long commentId,
        ModelMap model) throws ServiceException
    {
        IComment entity = commentService.findById(commentId);
        model.addAttribute("entity", entity);
        return "audit/issue/action/comment/view";
    }

}