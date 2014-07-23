package net.apollosoft.ats.domain;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class DomainException extends Exception
{

    /** serialVersionUID */
    private static final long serialVersionUID = -3146729835686874162L;

    /**
     * @param message
     */
    public DomainException(String message)
    {
        super(message);
    }

    /**
     * @param cause
     */
    public DomainException(Throwable cause)
    {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public DomainException(String message, Throwable cause)
    {
        super(message, cause);
    }

}