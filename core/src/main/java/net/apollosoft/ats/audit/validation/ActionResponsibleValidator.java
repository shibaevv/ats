package net.apollosoft.ats.audit.validation;

import java.util.ArrayList;
import java.util.List;

import net.apollosoft.ats.audit.domain.hibernate.ActionResponsible;
import net.apollosoft.ats.audit.validation.ValidationException.ValidationMessage;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class ActionResponsibleValidator extends BaseValidator<ActionResponsible>
{

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.validation.BaseValidator#validate(java.lang.Object)
     */
    @Override
    public List<ValidationMessage> validate(ActionResponsible entity)
    {
        List<ValidationMessage> errors = new ArrayList<ValidationMessage>();
        //TODO: no rules
        return errors;
    }

}