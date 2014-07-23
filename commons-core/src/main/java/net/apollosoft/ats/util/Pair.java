package net.apollosoft.ats.util;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class Pair<T> implements Serializable
{

    /** serialVersionUID */
    private static final long serialVersionUID = 5185821083610360294L;

    private T id;

    private String name;

    public Pair()
    {
        super();
    }

    public Pair(T id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * @return the id
     */
    public T getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(T id)
    {
        this.id = id;
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
            Pair<T> castOther = (Pair<T>) other;
            if (this.id != null && castOther.id != null)
            {
                return this.id.equals(castOther.id);
            }
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return this.id == null ? super.hashCode() : this.id.hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this).append("id", this.id).toString();
    }

}