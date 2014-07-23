package net.apollosoft.ats.audit.validation;

import java.util.ArrayList;
import java.util.List;

import net.apollosoft.ats.audit.domain.dto.ActionDto;
import net.apollosoft.ats.audit.domain.dto.IssueDto;
import net.apollosoft.ats.audit.service.ActionService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.validation.ValidationException.ValidationMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class IssueValidator extends BaseValidator<IssueDto>
{

    @Autowired
    @Qualifier("actionValidator")
    //@Resource(name="actionValidator")
    private ActionValidator actionValidator;

    @Autowired
    @Qualifier("actionService")
    //@Resource(name="actionService")
    private ActionService actionService;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.validation.BaseValidator#validate(java.lang.Object)
     */
    @Override
    public List<ValidationMessage> validate(IssueDto issue)
    {
        List<ValidationMessage> errors = new ArrayList<ValidationMessage>();
        // non-empty actions
        try
        {
            List<ActionDto> actions = actionService.findByIssueId(issue.getIssueId(), null, null);
            if (actions.isEmpty())
            {
                errors.add(new ValidationMessage("action", "This Issue has no Action"));
            }
            else
            {
                for (int i = 0; i < actions.size(); i++)
                {
                    ActionDto action = actions.get(i);
                    List<ValidationMessage> actionErrors = actionValidator.validate(action);
                    if (!actionErrors.isEmpty())
                    {
                        for (ValidationMessage actionError : actionErrors)
                        {
                            //update name to include parent collection index
                            actionError.setName("action." + i + "." + actionError.getName());
                        }
                        errors.addAll(actionErrors);
                    }
                }
            }
        }
        catch (ServiceException e)
        {
            errors.add(new ValidationMessage(null, e.getMessage()));
        }
        //
        if (issue.getListIndex() == null)
        {
            errors.add(new ValidationMessage("listIndex", "Issue No is empty"));
        }
        //
        if (StringUtils.isBlank(issue.getName()))
        {
            errors.add(new ValidationMessage("name", "Issue Name is empty"));
        }
        //Findings
        if (StringUtils.isBlank(issue.getDetail()))
        {
            errors.add(new ValidationMessage("detail", "Issue Findings is empty"));
        }
        //
        if (issue.getRating().getRatingId() == null)
        {
            errors.add(new ValidationMessage("rating", "Priority is not selected"));
        }
        //
        return errors;
    }

}