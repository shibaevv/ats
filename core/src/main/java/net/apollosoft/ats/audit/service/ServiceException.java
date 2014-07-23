package net.apollosoft.ats.audit.service;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class ServiceException extends Exception
{

    /** serialVersionUID */
    private static final long serialVersionUID = 8670068154122180940L;

    /**
     * @param message
     */
    public ServiceException(String message)
    {
        super(message);
    }

    /**
     * @param cause
     */
    public ServiceException(Throwable cause)
    {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public ServiceException(String message, Throwable cause)
    {
        super(message, cause);
    }

}