package net.apollosoft.ats.audit.validation;

import java.util.ArrayList;
import java.util.List;

import net.apollosoft.ats.audit.dao.ActionDao;
import net.apollosoft.ats.audit.dao.DaoException;
import net.apollosoft.ats.audit.domain.hibernate.Action;
import net.apollosoft.ats.audit.domain.hibernate.ActionGroupDivision;
import net.apollosoft.ats.audit.validation.ValidationException.ValidationMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class ActionGroupDivisionValidator extends BaseValidator<ActionGroupDivision>
{

    @Autowired
    @Qualifier("actionDao")
    //@Resource(name="actionDao")
    private ActionDao actionDao;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.validation.BaseValidator#validate(java.lang.Object)
     */
    @Override
    public List<ValidationMessage> validate(ActionGroupDivision entity)
    {
        List<ValidationMessage> errors = new ArrayList<ValidationMessage>();
        try
        {
            Action action = entity.getAction();
            Long actionId = action.getActionId();
            Long groupId = entity.getGroup().getGroupId();
            Long divisionId = entity.getDivision() == null ? null : entity.getDivision().getDivisionId();
            // test for groupId
            if (groupId == null)
            {
                errors.add(new ValidationMessage("actionGroupDivision", "Group is not selected"));
            }
            // test for unique (if new added, skip for deleted)
            ActionGroupDivision uniqueEntity = actionDao.findByUniqueKey(actionId, groupId,
                divisionId);
            if (uniqueEntity != null) // && !uniqueEntity.equals(entity)
            {
                errors
                    .add(new ValidationMessage("actionGroupDivision", "Not Unique Group/Division"));
            }
            // test for empty groupDivisions (will be tested in ActionValidator)
            //if (action.getGroupDivisions().isEmpty())
            //{
            //    errors.add(new ValidationMessage("actionGroupDivision",
            //        "There must be at least one Group/Division"));
            //}
        }
        catch (DaoException e)
        {
            errors.add(new ValidationMessage(null, e.getMessage()));
        }

        return errors;
    }
}