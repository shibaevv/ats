package net.apollosoft.ats.audit.validation;

import java.util.ArrayList;
import java.util.List;

import net.apollosoft.ats.audit.dao.ActionDao;
import net.apollosoft.ats.audit.domain.IAction;
import net.apollosoft.ats.audit.domain.IComment;
import net.apollosoft.ats.audit.domain.hibernate.Action;
import net.apollosoft.ats.audit.domain.refdata.IBusinessStatus;
import net.apollosoft.ats.audit.domain.refdata.IActionStatus.ActionStatusEnum;
import net.apollosoft.ats.audit.validation.ValidationException.ValidationMessage;
import net.apollosoft.ats.domain.dto.security.RoleDto;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class CommentValidator extends BaseValidator<IComment>
{

    @Autowired
    @Qualifier("actionDao")
    //@Resource(name="actionDao")
    private ActionDao actionDao;

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.validation.BaseValidator#validate(java.lang.Object)
     */
    @Override
    public List<ValidationMessage> validate(IComment entity)
    {
        List<ValidationMessage> errors = new ArrayList<ValidationMessage>();
        //validate detail
        String detail = entity.getDetail();
        if (StringUtils.isBlank(detail))
        {
            //errors.add(new ValidationMessage(null, IComment.DETAIL + " - Can not be empty"));
            //TODO: read message by key from Resource
            errors.add(new ValidationMessage(IComment.DETAIL, "Comment Detail can not be empty"));
        }
        //validate businessStatus change (if any)
        IAction action = entity.getAction();
        IBusinessStatus businessStatus = action == null ? null : action.getBusinessStatus();
        if (businessStatus != null)
        {
            try
            {
                Action oldAction = actionDao.findById(action.getId());
                if (!businessStatus.equals(oldAction.getBusinessStatus()))
                {
                    // only REPORT_OWNER, REPORT_TEAM can change status for CLOSED action
                    if (new Long(ActionStatusEnum.CLOSED.ordinal()).equals(oldAction
                        .getBusinessStatus().getActionStatus().getId()))
                    {
                        List<RoleDto> roles = getSecurityService().findUserRoles();
                        if (!roles.contains(RoleDto.REPORT_OWNER)
                            && !roles.contains(RoleDto.REPORT_TEAM))
                        {
                            errors
                                .add(new ValidationMessage(IAction.BUSINESS_STATUS,
                                    "Only Report Owner and Report Team can change status for closed action"));
                        }
                    }
                }
            }
            catch (Exception e)
            {
                errors.add(new ValidationMessage(null, e.getMessage()));
            }
        }
        return errors;
    }

}