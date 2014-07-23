package net.apollosoft.ats.audit.service.impl;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import net.apollosoft.ats.audit.service.EmailService;
import net.apollosoft.ats.audit.service.MailData;
import net.apollosoft.ats.audit.service.ServiceException;
import net.apollosoft.ats.domain.security.IUser;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class EmailServiceImpl implements EmailService
{

    /** logger. */
    private static final Log LOG = LogFactory.getLog(EmailServiceImpl.class);

    /** logger for email activity. */
    private static final Log LOG_EMAIL = LogFactory.getLog("EMAIL_LOG");

    /** smtpHost */
    private String smtpHost;

    /** smtpPort */
    private String smtpPort;

    /** email.ext */
    private String emailExt;

    /** userName */
    private String userName;

    /** password */
    private String password;

    /** from - for auto-email send by system (eg monthly notification) */
    private String from;

    /** to - non-production environments (to keep users not affected by test emails) */
    private String to;

    /**
     * @param smtpHost the smtpHost to set
     */
    public void setSmtpHost(String smtpHost)
    {
        this.smtpHost = smtpHost;
    }

    /**
     * @param smtpPort the smtpPort to set
     */
    public void setSmtpPort(String smtpPort)
    {
        this.smtpPort = smtpPort;
    }

    /**
     * @param emailExt the emailExt to set
     */
    public void setEmailExt(String emailExt)
    {
        this.emailExt = emailExt;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from)
    {
        this.from = from;
    }

    /**
     * @param to the to to set
     */
    public void setTo(String to)
    {
        this.to = to;
    }

    /* (non-Javadoc)
     * @see net.apollosoft.ats.audit.service.EmailService#sendMail(net.apollosoft.ats.audit.service.MailData)
     */
    public void sendMail(MailData data) throws ServiceException
    {
        if (StringUtils.isBlank(smtpHost))
        {
            LOG.warn("No [mailSmtpHost] set");
            return;
        }

        try
        {
            Properties props = System.getProperties();
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.port", smtpPort);

            Authenticator auth = new Authenticator()
            {
                /* (non-Javadoc)
                 * @see javax.mail.Authenticator#getPasswordAuthentication()
                 */
                @Override
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    //return null;
                    return new PasswordAuthentication(userName, password);
                }
            };

            Session session = Session.getInstance(props, auth);
            //Session session = Session.getDefaultInstance(props, auth);
            Message msg = new MimeMessage(session);
            MimeMultipart mimeMultipart = null;
            java.io.File[] attachments = data.getAttachments();
            if (attachments != null && attachments.length > 0)
            {
                mimeMultipart = new MimeMultipart("mixed");
                try
                {
                    for (java.io.File file : attachments)
                    {
                        MimeBodyPart part = new MimeBodyPart();
                        part.attachFile(file);
                        mimeMultipart.addBodyPart(part);
                    }
                }
                catch (Exception e)
                {
                    throw new ServiceException("Exception occured while attaching file", e);
                }
            }

            Multipart body = null;
            String text = data.getText();
            //eg <element>text</element>
            boolean isHtml = text.indexOf("<") >= 0 && text.indexOf("</") > 0;
            if (StringUtils.isNotBlank(text))
            {
                body = new MimeMultipart("alternative");
                if (mimeMultipart != null)
                {
                    MimeBodyPart aux = new MimeBodyPart();
                    aux.setContent(body);
                    mimeMultipart.addBodyPart(aux);
                }
            }

            addTextPart(msg, mimeMultipart, body, text, (isHtml ? "text/html;charset=\"utf-8\"" : "text/plain;charset=\"utf-8\""));

            if (mimeMultipart != null)
            {
                //text and attachments
                msg.setContent(mimeMultipart);
            }
            else if (body != null)
            {
                //text only
                msg.setContent(body);
            }

            String subject = data.getSubject();
            if (StringUtils.isNotBlank(subject))
            {
                msg.setSubject(subject);
            }
            else
            {
                throw new ServiceException("Email Subject is blank.");
            }

            String from = convert(data.getFrom());
            if (StringUtils.isNotBlank(this.from))
            {
                from = this.from;
            }
            msg.addFrom(InternetAddress.parse(from));

            String to = convert(data.getTo());
            if (StringUtils.isNotBlank(this.to))
            {
                to = this.to;
            }
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            //String cc = convert(data.getCc());
            //if (StringUtils.isNotBlank(cc))
            //{
            //    msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
            //}

            //String bcc = convert(data.getBcc());
            //if (StringUtils.isNotBlank(bcc))
            //{
            //    msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));
            //}

            Transport.send(msg);
            LOG_EMAIL.info("from:" + from + ", to:" + to + ", subject:" + subject + ", text:" + text);
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new ServiceException("Failed to send email: " + e.getMessage(), e);
        }
    }

    /**
     * TODO: check email creation logic
     * @param user
     * @return
     */
    private String convert(IUser user)
    {
        return user == null ? null : user.getLogin() + "@" + emailExt;
    }

    /**
     * 
     * @param msg
     * @param multipart
     * @param body
     * @param text
     * @param contentType
     * @throws MessagingException
     */
    private void addTextPart(Message msg, Multipart multipart, Multipart body, String text,
        String contentType) throws MessagingException
    {
        if (body != null)
        {
            body.addBodyPart(createBodyPart(text, contentType));
        }
        else if (multipart != null)
        {
            multipart.addBodyPart(createBodyPart(text, contentType));
        }
        else
        {
            msg.setContent(text, contentType);
        }
    }

    private MimeBodyPart createBodyPart(Object content, String contentType)
        throws MessagingException
    {
        MimeBodyPart part = new MimeBodyPart();
        part.setContent(content, contentType);
        part.setHeader("Content-Transfer-Encoding", "quoted-printable");
        return part;
    }

}