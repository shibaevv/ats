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
public class TextTag extends SimpleTagSupport
{

    private String text;

    private boolean json = false;

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
        text = StringUtils.replace(text, Formatter.NEW_LINE_CHAR.toString(), "<br/>");
        if (json)
        {
            text = text.replace('"', '\''); //TODO: how to keep double quote (replace with single for now)?
            text = StringUtils.replace(text, "\\", "\\\\");
        }
        out.write(text);
    }

}