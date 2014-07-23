package net.apollosoft.ats.domain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public enum ContentTypeEnum
{

    /** text/html */
    TEXT_HTML("TEXT_HTML", "text/html", "html"),

    /** text/plain */
    TEXT_PLAIN("TEXT_PLAIN", "text/plain", "txt"),

    /** text/csv */
    TEXT_CSV("TEXT_CSV", "text/csv", "csv"),

    /** application/vnd.ms-excel */
    APPLICATION_EXCEL("APPLICATION_EXCEL", "application/vnd.ms-excel", "xls"),

    /** application/zip */
    APPLICATION_ZIP("APPLICATION_ZIP", "application/zip", "zip"), ;

    /** Logger */
    private static final Log LOG = LogFactory.getLog(ContentTypeEnum.class);

    /** value. */
    private String value;

    /** contentType. */
    private String contentType;

    /** defaultExt. */
    private String defaultExt;

    /**
     * Ctor.
     * @param contentType
     */
    private ContentTypeEnum(String value, String contentType, String defaultExt)
    {
        this.value = value;
        this.contentType = contentType;
        this.defaultExt = defaultExt;
    }

    /**
     * @return the value
     */
    public String getValue()
    {
        return value;
    }

    /**
     * @return contentType
     */
    public String getContentType()
    {
        return contentType;
    }

    /**
     * @return the defaultExt
     */
    public String getDefaultExt()
    {
        return defaultExt;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString()
    {
        return super.toString() + " [" + contentType + "]";
    }

    /**
     *
     * @param contentType
     * @return
     */
    public static ContentTypeEnum findByContentType(String contentType)
    {
        for (ContentTypeEnum item : values())
        {
            if (item.contentType.equals(contentType))
            {
                return item;
            }
        }
        LOG.warn("Unhandled contentType [" + contentType + "]");
        return null;
    }

}