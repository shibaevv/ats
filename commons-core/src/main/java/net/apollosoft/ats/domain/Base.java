package net.apollosoft.ats.domain;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Base class for all dto objects.
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public abstract class Base<T> implements IBase<T>
{

    /**
     * Have to write this code as alternative to Set<T>
     * @param list
     * @param entity
     */
    @SuppressWarnings("unchecked")
    protected int add(final List list, Base entity)
    {
        int index;
        if (list.contains(entity))
        {
            index = list.indexOf(entity);
            //a different object with the same identifier value was already associated with the session
            list.set(index, entity);
        }
        else
        {
            index = list.size();
            list.add(entity);
        }
        return index;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public boolean equals(Object other)
    {
        if (this == other)
        {
            return true; // super implementation
        }
        if (other != null)
        {
            Base<T> castOther = (Base<T>) other;
            //use "castOther.getId()" as this instance can be hibernate enhancer
            if (getId() != null && castOther.getId() != null)
            {
                return getId().equals(castOther.getId());
            }
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return getId() == null ? super.hashCode() : getId().hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this).append(ID, getId()).toString();
    }

}