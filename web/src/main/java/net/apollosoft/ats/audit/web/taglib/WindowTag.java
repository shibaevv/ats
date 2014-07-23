package net.apollosoft.ats.audit.web.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.apollosoft.ats.audit.web.taglib.WindowOptionTag.Option;


/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class WindowTag extends BodyTagSupport
{

    /** serialVersionUID */
    private static final long serialVersionUID = 4763118614043142601L;

    private transient static long toggleId = 0;

    private String id = null;

    private String title = "";

    private String width = null;

    private String iconClass = null;

    private boolean collapse = false;

    private boolean collapsed = false;

    private transient List<Option> options;

    private transient String tagBodyContent = "";

    public int doStartTag() throws JspException
    {
        if (toggleId > 1000)
        {
            toggleId = 0;
        }
        options = new ArrayList<Option>();
        return EVAL_PAGE;
    }

    public int doAfterBody() throws JspException
    {
        tagBodyContent = bodyContent == null ? "" : bodyContent.getString().trim();
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException
    {
        toggleId++;
        try
        {
            JspWriter out = pageContext.getOut();
            out.write("<div" + (id != null ? " id='" + id + "'" : "") + " class='ats-container" + (iconClass != null ? " " + iconClass : "") + "'" + (width != null ? " style='width:" + width + ";'" : "") + ">");
            out.write("<div class='ats-container-hd'>");
            out.write("<h2" + (id != null ? " id='" + id + ".title'" : "") + ">" + title + "</h2>");
            if (collapse)
            {
                out.write("<span id='toggle." + toggleId + "' class='collapse" + (collapsed ? " collapsed" : "") + "'>X</span>");
            }
            out.write("</div>");
            out.write("<div id='toggle." + toggleId + ".container' class='ats-container-bd'" + (collapsed ? " style='display:none;'" : "") + ">");
            if (!getOptions().isEmpty())
            {
                out.write("<div class='ats-container-option'>");
                for (Option o : getOptions())
                {
                    out.write("<a" + (o.getId() != null ? " id='" + o.getId() + "'" : "") + " href='" + o.getUrl() + "' title='" + o.getTitle() + "' target='" + o.getTarget() + "'>" + o.getName() + "</a>&#160;");
                }
                out.write("</div>");
            }
            out.write(tagBodyContent);
            out.write("</div>");
            out.write("</div>");
            out.write("<span></span>"); // to make IE6 happy
        }
        catch (IOException e)
        {
            throw new JspTagException(e.toString());
        }
        return EVAL_PAGE;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(String width)
    {
        this.width = width;
    }

    /**
     * @param iconClass the iconClass to set
     */
    public void setIconClass(String iconClass)
    {
        this.iconClass = iconClass;
    }

    /**
     * @param collapse the collapse to set
     */
    public void setCollapse(boolean collapse)
    {
        this.collapse = collapse;
    }

    /**
     * @param collapsed the collapsed to set
     */
    public void setCollapsed(boolean collapsed)
    {
        this.collapsed = collapsed;
    }

    /**
     * @return the options
     */
    public List<Option> getOptions()
    {
        return options;
    }

}