package net.apollosoft.ats.util;

import java.util.Date;

import net.apollosoft.ats.domain.security.IUser;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public final class ThreadLocalUtils
{

    /** ThreadLocal storage for date. */
    private static final ThreadLocal<Date> dateStorage = new ThreadLocal<Date>();

    /** ThreadLocal storage for user. */
    private static final ThreadLocal<Pair<String>> userStorage = new ThreadLocal<Pair<String>>();

    /**
     * hide ctor.
     */
    private ThreadLocalUtils()
    {
        super();
    }

    /**
     * @return thread safe Date
     */
    public static Date getDate()
    {
        Date d = dateStorage.get();
        if (d == null)
        {
            d = DateUtils.getCurrentDateTime();
        }
        return d;
    }

    /**
     * Set new thread safe Date.
     * @param date
     */
    public static void setDate(final Date date)
    {
        dateStorage.set(date);
    }

    /**
     * Clear date storage.
     */
    public static void clearDate()
    {
        setDate(null);
    }

    /**
     * @return thread safe User
     */
    public static Pair<String> getUser()
    {
        return userStorage.get();
    }

    /**
     * Set new thread safe User.
     * @param date
     */
    public static void setUser(final Pair<String> user)
    {
        userStorage.set(user);
    }

    /**
     * Set new thread safe User.
     * @param date
     */
    public static void setUser(final IUser user)
    {
        userStorage.set(new Pair<String>(user.getId(), user.getLogin()));
    }

    /**
     * Clear user storage.
     */
    public static void clearUser()
    {
        userStorage.set(null);
    }

    /**
     * Clear thread storage.
     */
    public static void clear()
    {
        dateStorage.set(null);
        userStorage.set(null);
    }

}