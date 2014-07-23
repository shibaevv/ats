package net.apollosoft.ats.audit.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import net.apollosoft.ats.util.Formatter;

import org.apache.commons.lang.StringUtils;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class CropTextTag extends SimpleTagSupport //TextTag
{

    private String text;

    private int limit = 250;

    private boolean json = false;

    public void setLimit(int limit)
    {
        this.limit = limit;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    /**
     * @param json the json to set
     */
    public void setJson(boolean json)
    {
        this.json = json;
    }

    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
     */
    @Override
    public void doTag() throws JspException, IOException
    {
        JspWriter out = getJspContext().getOut();
        if (text.length() > limit)
        {
            text = StringUtils.abbreviate(text, limit);
        }
        text = StringUtils.replace(text, Formatter.NEW_LINE_CHAR.toString(), "<br/>");
        if (json)
        {
            text = text.replace('"', '\''); //TODO: how to keep double quote (replace with single for now)?
            text = StringUtils.replace(text, "\\", "\\\\");
        }
        out.write(text);
    }

}