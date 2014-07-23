package net.apollosoft.ats.audit.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends Exception
{

    /** serialVersionUID */
    private static final long serialVersionUID = 3238460747950065320L;

    private List<ValidationMessage> errors;

    /**
     * Default ctor.
     */
    public ValidationException()
    {
        errors = new ArrayList<ValidationMessage>();
    }

    /**
     * @param errors
     */
    public ValidationException(List<ValidationMessage> errors)
    {
        this.errors = errors;
    }

    /* (non-Javadoc)
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage()
    {
        return errors == null || errors.isEmpty() ? super.getMessage() : errors.toString();
    }

    /**
     * @return
     */
    public List<ValidationMessage> getErrors()
    {
        return errors;
    }

    public void add(Exception e)
    {
        errors.add(new ValidationMessage(null, e.getMessage()));
    }

    public void add(String name, String message)
    {
        errors.add(new ValidationMessage(name, message));
    }

    /**
     * 
     */
    public static class ValidationMessage
    {

        private String name;

        private final String message;

        public ValidationMessage(String name, String message)
        {
            this.name = name;
            this.message = message;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString()
        {
            return name + "=" + message;
        }

        /**
         * @return the name
         */
        public String getName()
        {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name)
        {
            this.name = name;
        }

        /**
         * @return the message
         */
        public String getMessage()
        {
            return message;
        }

    }

}