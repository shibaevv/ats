package net.apollosoft.ats.audit.validation;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import net.apollosoft.ats.audit.service.SecurityService;
import net.apollosoft.ats.audit.validation.ValidationException.ValidationMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public abstract class BaseValidator<T> implements Validator
{

    /** validatorClass. */
    private final Class<T> validatorClass;

    @Autowired
    @Qualifier("securityService")
    //@Resource(name="securityService")
    private SecurityService securityService;

    @SuppressWarnings("unchecked")
    protected BaseValidator()
    {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType)
        {
            ParameterizedType pType = (ParameterizedType) type;
            this.validatorClass = (Class<T>) pType.getActualTypeArguments()[0];
        }
        else
        {
            this.validatorClass = null;
        }
    }

    /**
     * @return the securityService
     */
    protected SecurityService getSecurityService()
    {
        return securityService;
    }

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    public boolean supports(Class clazz)
    {
        return validatorClass.isAssignableFrom(clazz);
    }

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    @SuppressWarnings("unchecked")
    public void validate(Object target, Errors errors)
    {
        T entity = (T) target;
        List<ValidationMessage> messages = validate(entity);
        // TODO: add messages to errors (BindingResult)
        for (ValidationMessage message : messages)
        {
            //errors.add(..);
        }
    }

    public abstract List<ValidationMessage> validate(T entity);

}