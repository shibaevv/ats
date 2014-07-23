package net.apollosoft.ats.audit.service;

import java.io.File;

import net.apollosoft.ats.domain.security.IUser;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class MailData
{

    /** from */
    private IUser from;

    /** to */
    private IUser to;

    /** cc */
    private IUser cc;

    /** subject */
    private String subject;

    /** text */
    private String text;

    /** attachments */
    private File[] attachments;

    /**
     * 
     */
    public MailData()
    {
        super();
    }

    /**
     * @return the from
     */
    public IUser getFrom()
    {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(IUser from)
    {
        this.from = from;
    }

    /**
     * @return the to
     */
    public IUser getTo()
    {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(IUser to)
    {
        this.to = to;
    }

    /**
     * @return the cc
     */
    public IUser getCc()
    {
        return cc;
    }

    /**
     * @param cc the cc to set
     */
    public void setCc(IUser cc)
    {
        this.cc = cc;
    }

    /**
     * @return the subject
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    /**
     * @return the text
     */
    public String getText()
    {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text)
    {
        this.text = text;
    }

    /**
     * @return the attachments
     */
    public File[] getAttachments()
    {
        return attachments;
    }

    /**
     * @param attachments the attachments to set
     */
    public void setAttachments(File[] attachments)
    {
        this.attachments = attachments;
    }

}