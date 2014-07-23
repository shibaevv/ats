package net.apollosoft.ats.audit.web.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class WindowOptionTag extends BodyTagSupport
{

    /** serialVersionUID */
    private static final long serialVersionUID = 4649376629986875412L;

    private WindowTag parent;

    private String id = null;

    private String url = null;

    private String target = "";

    private String name = "";

    private String title = "";

    public int doEndTag() throws JspException
    {
        if (parent != null)
        {
            if (url.charAt(0) == '/')
            {
                url = ((HttpServletRequest) pageContext.getRequest()).getContextPath() + url;
            }
            if (bodyContent != null)
            {
                name = bodyContent.getString().trim();
            }
            parent.getOptions().add(new Option(id, url, target, name, title));
        }
        return EVAL_PAGE;
    }

    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.TagSupport#setParent(javax.servlet.jsp.tagext.Tag)
     */
    @Override
    public void setParent(Tag tag)
    {
        while (tag != null && !(tag instanceof WindowTag))
        {
            tag = tag.getParent();
        }
        if (tag != null)
        {
            parent = (WindowTag) tag;
        }
    }

    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the url
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url)
    {
        this.url = url;
    }

    /**
     * @return the target
     */
    public String getTarget()
    {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(String target)
    {
        this.target = target;
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

    /**
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    class Option
    {
        private final String id;

        private final String url;

        private final String target;

        private final String name;

        private final String title;

        public Option(String id, String url, String target, String name, String title)
        {
            this.id = id;
            this.url = url;
            this.target = target;
            this.name = name;
            this.title = title;
        }

        /**
         * @return the id
         */
        public String getId()
        {
            return id;
        }

        /**
         * @return the url
         */
        public String getUrl()
        {
            return url;
        }

        /**
         * @return the target
         */
        public String getTarget()
        {
            return target;
        }

        /**
         * @return the name
         */
        public String getName()
        {
            return name;
        }

        /**
         * @return the title
         */
        public String getTitle()
        {
            return title;
        }

    }

}