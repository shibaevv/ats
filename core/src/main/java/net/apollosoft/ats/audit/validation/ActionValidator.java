package net.apollosoft.ats.audit.validation;

import java.util.ArrayList;
import java.util.List;

import net.apollosoft.ats.audit.domain.dto.ActionDto;
import net.apollosoft.ats.audit.service.ActionService;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.audit.validation.ValidationException.ValidationMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


public class ActionValidator extends BaseValidator<ActionDto>
{

    @Autowired
    @Qualifier("actionService")
    //@Resource(name="actionService")
    private ActionService actionService;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.validation.BaseValidator#validate(java.lang.Object)
     */
    @Override
    public List<ValidationMessage> validate(ActionDto entity)
    {
        List<ValidationMessage> errors = new ArrayList<ValidationMessage>();
        try
        {
            //
            List<ActionDto> actions = actionService.findByIssueId(entity.getIssue().getIssueId(),
                null, null);
            for (ActionDto item : actions)
            {
                if (entity.getListIndex().equals(item.getListIndex())
                    && entity.getActionId() == null)
                {
                    errors.add(new ValidationMessage("listIndex", "Action number is duplicated"));
                }
            }
            //
            if (entity.getListIndex() == null)
            {
                errors.add(new ValidationMessage("listIndex", "Action Number is empty"));
            }
            //
            if (StringUtils.isEmpty(entity.getName()))
            {
                errors.add(new ValidationMessage("name", "Action Title is empty"));
            }
            //
            if (entity.getDueDate() == null)
            {
                errors.add(new ValidationMessage("dueDate", "Due Date is empty"));
            }
            //
            if (StringUtils.isEmpty(entity.getDetail()))
            {
                errors.add(new ValidationMessage("detail", "Agreed Action is empty"));
            }
            //
            if (entity.getBusinessStatus().getBusinessStatusId() == null)
            {
                errors.add(new ValidationMessage("businessStatus",
                    "Business Status is not selected"));
            }
            // test for empty groupDivisions
            if (entity.getGroupDivisions().isEmpty())
            {
                errors.add(new ValidationMessage("actionGroupDivision",
                    "There must be at least one Group/Division"));
            }
            //
            if (entity.getResponsibleUsers().isEmpty())
            {
                errors.add(new ValidationMessage("user", "Person Responsible is not selected"));
            }
            //
            if (entity.getBusinessStatus().isImplemented() && entity.getClosedDate() == null)
            {
                errors.add(new ValidationMessage("closeDate", "Business Date Closed is empty"));
            }
        }
        catch (ServiceException e)
        {
            errors.add(new ValidationMessage(null, e.getMessage()));
        }
        //
        return errors;
    }

}