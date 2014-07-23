package net.apollosoft.ats.audit.web.audit;

import net.apollosoft.ats.audit.domain.dto.AuditDto;
import net.apollosoft.ats.audit.service.ActionService;
import net.apollosoft.ats.audit.service.AuditService;
import net.apollosoft.ats.audit.service.CommentService;
import net.apollosoft.ats.audit.service.IssueService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.validation.ValidationException;
import net.apollosoft.ats.audit.web.BaseController;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;


/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Controller
abstract class AdminController extends BaseController
{

    private final AuditService auditService;

    private final IssueService issueService;

    private final ActionService actionService;

    private final CommentService commentService;

    @Autowired
    public AdminController(@Qualifier("beanFactory") BeanFactory beanFactory,
        @Qualifier("auditService") AuditService auditService,
        @Qualifier("issueService") IssueService issueService,
        @Qualifier("actionService") ActionService actionService,
        @Qualifier("commentService") CommentService commentService)
    {
        super(beanFactory);
        this.auditService = auditService;
        this.issueService = issueService;
        this.actionService = actionService;
        this.commentService = commentService;
    }

    /**
     * @return the auditService
     */
    public AuditService getAuditService()
    {
        return auditService;
    }

    /**
     * @return the issueService
     */
    public IssueService getIssueService()
    {
        return issueService;
    }

    /**
     * @return the actionService
     */
    public ActionService getActionService()
    {
        return actionService;
    }

    /**
     * @return the commentService
     */
    public CommentService getCommentService()
    {
        return commentService;
    }

    /**
     * 
     * @param audit
     * @throws ServiceException
     * @throws ValidationException
     */
    protected void doSaveAudit(AuditDto audit) throws ServiceException, ValidationException
    {
        //TODO: improve collection handling logic
        audit.getIssues().addAll(audit.getIssues2());
        audit.getGroupDivisions().addAll(audit.getGroupDivisions2());
        //
        auditService.save(audit);
    }

}