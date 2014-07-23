package net.apollosoft.ats.domain.hibernate;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import net.apollosoft.ats.domain.hibernate.security.User;
import net.apollosoft.ats.util.Pair;
import net.apollosoft.ats.util.ThreadLocalUtils;


/**
 *
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class AuditableListener
{

    /**
     * Set required Auditable properties (id == null)
     * @param auditable
     */
    @PrePersist
    public void prePersist(Auditable<?> auditable)
    {
        final Date now = ThreadLocalUtils.getDate();
        final Pair<String> user = ThreadLocalUtils.getUser();
        //
        if (auditable.getCreatedDate() == null)
        {
            auditable.setCreatedDate(now);
        }
        //
        if (auditable.getCreatedBy() == null)
        {
            auditable.setCreatedBy(new User(user.getId()));
        }
        else if (auditable.getCreatedBy() == User.SYSTEM)
        {
            auditable.setCreatedBy(null);
        }
    }

    /**
     * Set required Auditable properties (id != null)
     * @param auditable
     */
    @PreUpdate
    public void preUpdate(Auditable<?> auditable)
    {
        final Date now = ThreadLocalUtils.getDate();
        final Pair<String> user = ThreadLocalUtils.getUser();
        auditable.setUpdatedDate(now);
        auditable.setUpdatedBy(new User(user.getId()));
    }
/*
    @PreRemove
    public void preRemove(Auditable<?> auditable)
    {
        final Date now = ThreadLocalUtils.getDate();
        final Pair<String> user = ThreadLocalUtils.getUser();
        auditable.setDeletedDate(now);
        auditable.setDeletedBy(new User(user.getId()));
        //baseDao.delete(auditable);
    }
//*/
}