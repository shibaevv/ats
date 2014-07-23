package net.apollosoft.ats.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public final class Formatter
{

    /** logger. */
    private static final Log LOG = LogFactory.getLog(Formatter.class);

    public static final Character NEW_LINE_CHAR = '\n';

    private Formatter()
    {
        super();
    }

    /**
     * Format a <code>Date</code> into a <code>String</code> in the format
     * 'dd/mm/yyyy'. If <code>date</code> is <code>null</code>, then an
     * empty string will be returned.
     *
     * @param date
     *            <code>Date</code> to be converted.
     * @return
     */
    public static String formatDate(Date date)
    {
        return date == null ? "" : DateUtils.DEFAULT_DATE.format(date);
    }

    public static String formatTime(Date date)
    {
        return date == null ? "" : DateUtils.DEFAULT_TIME.format(date);
    }

    public static String formatDateTime(Date date)
    {
        return date == null ? "" : DateUtils.DEFAULT_DATETIME.format(date);
    }

    /**
     *
     * @param value
     * @return
     */
    public static String formatNumber(Number value)
    {
        if (value == null)
            return "";
        NumberFormat formatter = NumberFormat.getNumberInstance();
        return formatter.format(value);
    }

    /**
     *
     * @param value
     * @return
     */
    public static String formatCurrency(Number value)
    {
        if (value == null)
            return "";
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(value);
    }

    /**
     *
     * @param value
     * @return
     */
    public static String formatPercent(Number value)
    {
        if (value == null)
            return "";
        NumberFormat formatter = NumberFormat.getPercentInstance();
        return formatter.format(value);
    }

    public static Integer createInteger(Object obj)
    {
        return obj == null ? null : NumberUtils.createInteger(obj.toString());
    }

    public static Long createLong(Object obj)
    {
        return obj == null ? null : NumberUtils.createLong(obj.toString());
    }

    public static BigDecimal createBigDecimal(Object obj)
    {
        return obj == null ? null : NumberUtils.createBigDecimal(obj.toString());
    }

    /**
     * Format for ms-office data (encode unsafe character(s)).
     * @param s
     * @return
     */
    public static String format(String s, boolean replaceNewLine)
    {
        if (StringUtils.isBlank(s))
        {
            return "";
        }
        //
        StringBuffer sb = new StringBuffer(s.length());
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (c == NEW_LINE_CHAR)
            {
                if (replaceNewLine)
                {
                    sb.append(' ');
                }
                else
                {
                    sb.append(c); // to preserve CR (will be replaced with <br/> for html UI)
                }
            }
            else if (Character.isWhitespace(c))
            {
                sb.append(' '); // to remove ms-office crap
            }
            else
            {
                sb.append(c);
            }
        }
        s = sb.toString();
        return s.trim();
    }

    /**
     * Format for json data (encode unsafe json character(s)).
     * @param s
     * @return
     */
    public static String formatJson(String s, boolean replaceNewLine)
    {
        if (StringUtils.isBlank(s))
        {
            return "";
        }
        s = s.replace('"', '\''); //TODO: how to keep double quote (replace with single for now)?
        return format(s, replaceNewLine);
    }

    /**
     * 
     * @param currentListIndex
     * @return
     */
    public static BigDecimal nextListIndex(BigDecimal currentListIndex)
    {
        if (currentListIndex == null)
        {
            return BigDecimal.ONE;
        }
        try
        {
            return currentListIndex.add(BigDecimal.ONE);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 
     * @param text
     * @param maxLength
     * @return
     */
    public static String[] split(String text, int maxLength)
    {
        List<String> texts = new ArrayList<String>();
        String tmp = "";
        for (String s : text.split(" "))
        {
            if (tmp.length() + s.length() > maxLength)
            {
                texts.add(tmp.trim());
                tmp = "";
            }
            tmp += " " + s;
        }
        if (tmp.length() > 0)
        {
            texts.add(tmp.trim());
        }
        return texts.toArray(new String[0]);
    }

    /**
     * 
     * @param html
     * @return
     */
    public static String htmlToText(String html)
    {
        if (StringUtils.isBlank(html))
        {
            return null;
        }
        int pos = -1;
        while ((pos = html.indexOf('<', pos)) >= 0)
        {
            int pos2 = html.indexOf('>', pos);
            if (pos2 >= 0)
            {
                html = html.substring(0, pos) + html.substring(pos2 + 1);
            }
            else
            {
                break;
            }
        }
        return html;
    }

}