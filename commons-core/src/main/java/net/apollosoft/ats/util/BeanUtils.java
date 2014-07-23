package net.apollosoft.ats.util;

import net.apollosoft.ats.domain.DomainException;
import net.apollosoft.ats.search.IDir;

/**
 * 
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public final class BeanUtils
{

    /** hide ctor. */
    private BeanUtils()
    {
    }

    /**
     * 
     * @param source
     * @param target
     * @param ignoreProperties
     * @throws DomainException
     */
    public static void copyProperties(Object source, Object target, String[] ignoreProperties)
        throws DomainException
    {
        try
        {
            org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
        }
        catch (Exception e)
        {
            throw new DomainException(e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public static int compareTo(Comparable o1, Comparable o2)
    {
        return compareTo(o1, o2, IDir.ASC);
    }

    @SuppressWarnings("unchecked")
    public static int compareTo(Comparable o1, Comparable o2, String dir)
    {
        if (o1 == null && o2 == null)
        {
            return 0;
        }

        //no dir, or IDir.ASC
        int result;// = compare(o2, o1);
        if (o1 == null)
        {
            result = 1;
        }
        else if (o2 == null)
        {
            result = -1;
        }
        else
        {
            result = o2.compareTo(o1);
        }
        return IDir.DESC.equals(dir) ? -result : result;
    }

}