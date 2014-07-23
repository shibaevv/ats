package net.apollosoft.ats.util;

import java.util.Date;

import junit.framework.Assert;


import net.apollosoft.ats.util.DateUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * @author Valeri SHIBAEV (mailto:shibaevv@gmail.com)
 */
public class DateUtilsTest
{

    /** logger. */
    private static final Log LOG = LogFactory.getLog(DateUtilsTest.class);

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#getCurrentDate()}.
     */
    @Test
    public final void testGetCurrentDate()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#getCurrentDateTime()}.
     */
    @Test
    public final void testGetCurrentDateTime()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#getFinancialYear(java.util.Date)}.
     */
    @Test
    public final void testGetFinancialYear()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#parse(java.lang.String, java.lang.String, java.text.SimpleDateFormat)}.
     */
    @Test
    public final void testParse()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#getFutureDate(java.util.Date, java.lang.String, java.text.SimpleDateFormat)}.
     */
    @Test
    public final void testGetFutureDate()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#isStartOfDay(java.util.Date)}.
     */
    @Test
    public final void testIsStartOfDay()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#getStartOfDay(java.util.Date)}.
     */
    @Test
    public final void testGetStartOfDay()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#getEndOfDay(java.util.Date)}.
     */
    @Test
    public final void testGetEndOfDay()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#getEndOfMonth(java.util.Date)}.
     */
    @Test
    public final void testGetEndOfMonth()
    {
        try
        {
            Date now = DateUtils.getCurrentDateTime();
            Date endOfMonth = DateUtils.getEndOfMonth(now);
            Assert.assertTrue(now.before(endOfMonth));
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#getEndOfWeek(java.util.Date)}.
     */
    @Test
    public final void testGetEndOfWeek()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#getStartOfWeek(java.util.Date)}.
     */
    @Test
    public final void testGetStartOfWeek()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#addDays(java.util.Date, java.lang.Number)}.
     */
    @Test
    public final void testAddDays()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#addWeeks(java.util.Date, java.lang.Number)}.
     */
    @Test
    public final void testAddWeeks()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#addMonths(java.util.Date, java.lang.Number)}.
     */
    @Test
    public final void testAddMonths()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#addYears(java.util.Date, java.lang.Number)}.
     */
    @Test
    public final void testAddYears()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#isWeekend(java.util.Date)}.
     */
    @Test
    public final void testIsWeekend()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#isMonday(java.util.Date)}.
     */
    @Test
    public final void testIsMonday()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#isFriday(java.util.Date)}.
     */
    @Test
    public final void testIsFriday()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#isSaturday(java.util.Date)}.
     */
    @Test
    public final void testIsSaturday()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#isSunday(java.util.Date)}.
     */
    @Test
    public final void testIsSunday()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#isWithinRange(java.util.Date, java.util.Date, java.util.Date)}.
     */
    @Test
    public final void testIsWithinRange()
    {
        try
        {
            Date date = DateUtils.DEFAULT_DATE.parse("01-Dec-2009");
            Date from = DateUtils.DEFAULT_DATE.parse("01-Jan-2008");
            Date to = null;
            boolean result = DateUtils.isWithinRange(date, from, to);
            Assert.assertTrue(result);
        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#getDays(java.util.Date, java.util.Date)}.
     */
    @Test
    public final void testGetDays()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#getFollowingMonday(java.util.Date)}.
     */
    @Test
    public final void testGetFollowingMonday()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test method for {@link net.apollosoft.ats.util.DateUtils#parseExcelDate(double)}.
     */
    @Test
    public final void testParseExcelDate()
    {
        try
        {

        }
        catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            Assert.fail(e.getMessage());
        }
    }

}