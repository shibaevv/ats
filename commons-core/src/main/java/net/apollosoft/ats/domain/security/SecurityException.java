package net.apollosoft.ats.domain.security;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class SecurityException extends RuntimeException
{

    /** serialVersionUID */
    private static final long serialVersionUID = -494105585513439427L;

    /**
     * @param message
     */
    public SecurityException(String message)
    {
        super(message);
    }

    /**
     * @param cause
     */
    public SecurityException(Throwable cause)
    {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public SecurityException(String message, Throwable cause)
    {
        super(message, cause);
    }

}