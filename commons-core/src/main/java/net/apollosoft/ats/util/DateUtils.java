package net.apollosoft.ats.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public final class DateUtils implements IDateFormat
{

    public static final long SECONDS_PER_MINUTE = 60;

    public static final long MILLIS_PER_MINUTE = SECONDS_PER_MINUTE * 1000L;

    public static final long SECONDS_PER_HOUR = 60 * 60;

    public static final long MILLIS_PER_HOUR = SECONDS_PER_HOUR * 1000L;

    public static final long SECONDS_PER_DAY = 24 * 60 * 60;

    /** Number of milliseconds in one day. */
    public static final long MILLIS_PER_DAY = SECONDS_PER_DAY * 1000L;

    public static final long MILLIS_PER_WEEK = MILLIS_PER_DAY * 7;

    /** hide ctor. */
    private DateUtils()
    {
    }

    /**
     *
     * @return
     */
    public static Date getCurrentDate()
    {
        return getStartOfDay(getCurrentDateTime());
    }

    /**
     *
     * @return
     */
    public static Date getCurrentDateTime()
    {
        return new Date();
    }

    /**
     * Financial Year start date (AU) 01/07/yyyy
     * @param date
     * @return
     */
    public static Date getFinancialYear(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        if (date == null)
        {
            date = new Date();
        }
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        if (month < Calendar.JULY)
        {
            calendar.roll(Calendar.YEAR, -1);
        }
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Return the 9am of the day for the specified date.
     *
     * @param date a date
     * @param time a time eg 9:00
     * @return 9am of the day of the day for the specified date
    public static Date getTimeOfDay(Date date, String time)
    {
        String[] times = StringUtils.isBlank(time) ? new String[0] : time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, times.length > 0 ? Integer.parseInt(times[0]) : 0);
        calendar.set(Calendar.MINUTE, times.length > 1 ? Integer.parseInt(times[1]) : 0);
        calendar.set(Calendar.SECOND, times.length > 2 ? Integer.parseInt(times[2]) : 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    */

    /**
     * 
     * @param dateString
     * @param timeString 15:00:00, 3:00
     * @param dateTimeFormat
     * @return date
     * @throws ParseException
     */
    public static Date parse(String dateString, String timeString, SimpleDateFormat dateTimeFormat)
        throws ParseException
    {
        return dateTimeFormat.parse(dateString + " " + timeString);
    }

    /**
     * Create future date (relative to supplied date) based on timeString and timeFormat.
     * @see SimpleDateFormat<br/>
     * F  Day of week in month  Number  2<br/>
     * E  Day in week  Text  Tuesday; Tue<br/>
     * H  Hour in day (0-23)  Number  0<br/>
     * m  Minute in hour  Number  30<br/>
     * s  Second in minute  Number  55<br/>
     *
     * @param date
     * @param timeString Tue 15:00:00, 3:00
     * @param timeFormat E HH:mm:ss,   HH:mm
     * @return future date only
     * @throws ParseException
     */
    public static Date getFutureDate(final Date date, String timeString, SimpleDateFormat timeFormat)
        throws ParseException
    {
        Calendar cal = Calendar.getInstance();
        long zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        long dstOffset = cal.get(Calendar.DST_OFFSET);
        long offset = zoneOffset + dstOffset;

        String pattern = timeFormat.toPattern();
        long factor = MILLIS_PER_DAY;
        if (pattern.indexOf('F') >= 0 || pattern.indexOf('E') >= 0)
        {
            factor = MILLIS_PER_WEEK;
        }

        Date time = timeFormat.parse(timeString);
        long days = (date.getTime() + zoneOffset) / MILLIS_PER_DAY;
        long millis = (time.getTime() + zoneOffset) % factor;
        Date result = new Date(days * MILLIS_PER_DAY + millis - offset);

        //return future date only
        if (!result.after(date))
        {
            result = new Date(result.getTime() + factor);
        }

        return result;
    }

    /**
     * Return the start of the day for the specified date.
     *
     * @param date a date
     * @return start of the day of the day for the specified date
     */
    public static boolean isStartOfDay(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY) == 0 && calendar.get(Calendar.MINUTE) == 0
            && calendar.get(Calendar.SECOND) == 0 && calendar.get(Calendar.MILLISECOND) == 0;
    }

    /**
     * Return the start of the day for the specified date.
     *
     * @param date a date
     * @return start of the day of the day for the specified date
     */
    public static Date getStartOfDay(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Return the start of the day for the specified date.
     *
     * @param date a date
     * @return start of the day of the day for the specified date
     */
    public static Date getStartOfMonth(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * Return the end of the day for the specified date.
     *
     * @param date a date
     * @return end of the day of the day for the specified date
     */
    public static Date getEndOfDay(Date date)
    {
        return addDays(getStartOfDay(date), 1);
    }

    /**
     * Return the end of the last day of the month for the specified date.
     *
     * @param date a date
     * @return end of the last day of the month for the specified date
     */
    public static Date getEndOfMonth(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // max day in the month
        calendar.set(Calendar.DAY_OF_MONTH, maxDay);
        return calendar.getTime();
    }

    /**
     * Return the end of the week, where week starts Monday and ends Sunday.
     * @param date a date
     * @return end of the week, where week starts Monday and ends Sunday.
     */
    public static Date getEndOfWeek(Date date)
    {
        Date endDate = date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (Calendar.SUNDAY != calendar.get(Calendar.DAY_OF_WEEK))
        {
            int daysToAdd = Calendar.SATURDAY + 1 - calendar.get(Calendar.DAY_OF_WEEK);
            calendar.add(Calendar.DATE, daysToAdd);
            endDate = calendar.getTime();
        }
        return endDate;
    }

    /**
     * Return the start of the week, where week starts Monday and ends Sunday.
     * @param date a date
     * @return start of the week, where week starts Monday and ends Sunday.
     */
    public static Date getStartOfWeek(Date date)
    {
        Date endDate = date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (Calendar.MONDAY != calendar.get(Calendar.DAY_OF_WEEK))
        {
            int daysToGoBack = 2 - calendar.get(Calendar.DAY_OF_WEEK);
            //Sunday
            if (Calendar.SUNDAY == calendar.get(Calendar.DAY_OF_WEEK))
            {
                daysToGoBack = calendar.get(Calendar.DAY_OF_WEEK) - Calendar.SATURDAY;
            }
            calendar.add(Calendar.DATE, daysToGoBack);
            endDate = calendar.getTime();
        }
        return endDate;
    }

    /**
     * Return the 9am of the day for the specified date.
     *
     * @param date a date
     * @param time a time eg 9:00
     * @return 9am of the day of the day for the specified date
     */
    public static Date getTimeOfDay(Date date, String time)
    {
        String[] times = StringUtils.isBlank(time) ? new String[0] : time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, times.length > 0 ? Integer.parseInt(times[0]) : 0);
        calendar.set(Calendar.MINUTE, times.length > 1 ? Integer.parseInt(times[1]) : 0);
        calendar.set(Calendar.SECOND, times.length > 2 ? Integer.parseInt(times[2]) : 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Add the specified number of days to a Date.
     *
     * @param date The date to add days to.
     * @param days  The number of days to add (specify -ve value to subtract days).
     * @return The Date of date + days.
     */
    public static Date addDays(Date date, Number days)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days.intValue());
        return c.getTime();
    }

    /**
     * Add the specified number of weeks to a Date.
     *
     * @param date  The date to add weeks to.
     * @param weeks The number of weeks to add (specify -ve value to subtract weeks).
     * @return The Date of date + weeks.
     */
    public static Date addWeeks(Date date, Number weeks)
    {
        return addDays(date, 7 * weeks.intValue());
    }

    /**
     * Add the specified number of months to a Date.
     *
     * @param date  The date to add months to.
     * @param months The number of months to add (specify -ve value to subtract months).
     * @return The Date of date + months.
     */
    public static Date addMonths(Date date, Number months)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, months.intValue());
        return c.getTime();
    }

    /**
     * Add the specified number of years to a Date.
     *
     * @param date The date to add years to.
     * @param years The number of years to add (specify -ve value to subtract years).
     * @return The Date of aDate + years.
     */
    public static Date addYears(Date date, Number years)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, years.intValue());
        return c.getTime();
    }

    /**
     * Determines if a date is on a weekend.
     * @param date date given.
     * @return true if date is on a weekend.
     */
    public static boolean isWeekend(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }

    /**
     * Determines if a date is on a Monday.
     * @param date date given.
     * @return true if date is on a Monday.
     */
    public static boolean isMonday(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.MONDAY;
    }

    /**
     * Determines if a date is on a Friday.
     * @param date date given.
     * @return true if date is on a Friday.
     */
    public static boolean isFriday(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.FRIDAY;
    }

    /**
     * Determines if a date is on a Saturday.
     * @param date date given.
     * @return true if date is on a Saturday.
     */
    public static boolean isSaturday(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SATURDAY;
    }

    /**
     * Determines if a date is on a Sunday.
     * @param date date given.
     * @return true if date is on a Sunday.
     */
    public static boolean isSunday(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SUNDAY;
    }

    /**
     * 
     * @param date
     * @param from
     * @param to
     * @return
     */
    public static boolean isWithinRange(Date date, Date from, Date to)
    {
        if (date == null)
        {
            return true;
        }
        if (from == null && to == null)
        {
            return true;
        }
        if (from != null && to == null)
        {
            return !from.after(date);
        }
        if (from == null && to != null)
        {
            return !date.after(to);
        }
        return !from.after(date) && !date.after(to);
    }

    /**
     * 
     * @param from
     * @param to
     * @return
     */
    public static long getDays(Date from, Date to)
    {
        if (from == null || to == null)
        {
            return 0L;
        }
        return (to.getTime() - from.getTime()) / MILLIS_PER_DAY;
    }

    /**
     * Return the Following Monday for the specified date.
     *
     * @param date a date
     * @return Following Monday of the day for the specified date
     */
    public static Date getFollowingMonday(Date date)
    {
        if (isSaturday(date))
        {
            return addDays(date, 2);
        }
        if (isSunday(date))
        {
            return addDays(date, 1);
        }
        return date;
    }

}