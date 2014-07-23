package net.apollosoft.ats.domain.hibernate;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import net.apollosoft.ats.domain.Base;


/**
 * Base class for all domain objects.
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@MappedSuperclass
public abstract class BaseModel<T> extends Base<T> implements Serializable//, Cloneable
{

    /** serialVersionUID */
    private static final long serialVersionUID = -1532823220752174268L;

    //    /* (non-Javadoc)
    //     * @see java.lang.Object#clone()
    //     */
    //    @SuppressWarnings("unchecked")
    //    @Override
    //    protected Object clone() throws CloneNotSupportedException
    //    {
    //        BaseModel<T> baseModel = (BaseModel<T>) super.clone();
    //        baseModel.setId(null);
    //        return baseModel;
    //    }

}