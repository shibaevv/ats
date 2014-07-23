package net.apollosoft.ats.audit.web.taglib;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;

/**
 * @see struts logic::notpresent path="role1"
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class NotPresentTag extends PresentTag
{

    /** serialVersionUID */
    private static final long serialVersionUID = -951274569025891233L;

    /* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException
    {
        boolean skipBody = true;
        if (StringUtils.isNotBlank(role))
        {
            skipBody = hasRole();
        }
        else if (StringUtils.isNotBlank(module))
        {
            skipBody = StringUtils.isBlank(path) ? hasModule() : hasModulePath();
        }
        else if (StringUtils.isNotBlank(path))
        {
            skipBody = StringUtils.isBlank(module) ? hasPath() : hasModulePath();
        }
        return skipBody ? SKIP_BODY : EVAL_BODY_INCLUDE;
    }

}