package net.apollosoft.ats.util;

import java.text.SimpleDateFormat;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public interface IDateFormat
{

    /** HH:mm time format */
    SimpleDateFormat HH_MM = new SimpleDateFormat("HH:mm");

    /** dd-MMM-yyyy date format */
    SimpleDateFormat DD_MMM_YYYY = new SimpleDateFormat("dd-MMM-yyyy");

    /** dd-MMM-yyyy HHmm dateTime format */
    SimpleDateFormat DD_MMM_YYYY_HHMM = new SimpleDateFormat(
        "dd-MMM-yyyy HHmm");

    /** dd-MMM-yyyy HH:mm dateTime format */
    SimpleDateFormat DD_MMM_YYYY_HH_MM = new SimpleDateFormat(
        "dd-MMM-yyyy HH:mm");

    /** yyyyMMdd'T'HHmmss date-time format */
    SimpleDateFormat YYYYMMDDTHHmmss = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

    /** DEFAULT_DATETIME date format */
    SimpleDateFormat DEFAULT_TIME = HH_MM;

    /** DEFAULT_DATE date format */
    SimpleDateFormat DEFAULT_DATE = DD_MMM_YYYY;

    /** DEFAULT_DATE date pattern */
    String DEFAULT_DATE_PATTERN = DEFAULT_DATE.toPattern();

    /** DEFAULT_DATETIME date format */
    SimpleDateFormat DEFAULT_DATETIME = DD_MMM_YYYY_HH_MM;

    /** DEFAULT_DATETIME date pattern */
    String DEFAULT_DATETIME_PATTERN = DEFAULT_DATETIME.toPattern();

}