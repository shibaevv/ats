package net.apollosoft.ats.audit.dao;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class DaoException extends Exception
{

    /** serialVersionUID */
    private static final long serialVersionUID = -2768155675269384562L;

    /**
     * @param message
     */
    public DaoException(String message)
    {
        super(message);
    }

    /**
     * @param cause
     */
    public DaoException(Throwable cause)
    {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public DaoException(String message, Throwable cause)
    {
        super(message, cause);
    }

}