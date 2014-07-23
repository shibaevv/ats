package net.apollosoft.ats.audit.task;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Automatic task (no user)
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface NotificationTask
{
    
    /**
     * 
     */
    void oversightTeam();

    /**
     * 
     */
    void personResponsible();

}