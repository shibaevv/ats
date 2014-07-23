package net.apollosoft.ats.audit.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public interface EmailService
{

    /**
     * Logic to send email, check email service provider.
     * @param data The MailData that you want to send.
     * @throws ServiceException
     */
    void sendMail(MailData data) throws ServiceException;

}