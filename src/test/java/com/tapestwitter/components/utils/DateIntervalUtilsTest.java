package com.tapestwitter.components.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.tapestwitter.common.DateIntervalBean;
import com.tapestwitter.common.DateIntervalType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.unitils.UnitilsTestNG;

/**
 * Test class for {@link DateIntervalUtils}
 * 
 * @author lguerin
 */
public class DateIntervalUtilsTest extends UnitilsTestNG
{

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DateIntervalUtilsTest.class);

    /**
     * The bean to test
     */
    private DateIntervalBean expected;

    /**
     * The calendar instance
     */
    private Calendar calendar;

    @BeforeMethod
    public void before()
    {
        calendar = Calendar.getInstance();
        expected = new DateIntervalBean();
    }

    @Test
    public void testGetDateIntervalBeanWithSecond() throws ParseException
    {
        calendar.add(Calendar.SECOND, 0);
        Date inputDate = calendar.getTime();
        expected = DateIntervalUtils.getDateIntervalBean(inputDate);
        logDebugInfos(inputDate, expected);
        Assert.assertEquals(expected.getIntervalNumber(), new Integer(0));
        Assert.assertEquals(expected.getIntervalType(), DateIntervalType.SECOND);
    }

    @Test
    public void testGetDateIntervalBeanWithSeconds() throws ParseException
    {
        calendar.add(Calendar.SECOND, -10);
        Date inputDate = calendar.getTime();
        expected = DateIntervalUtils.getDateIntervalBean(inputDate);
        logDebugInfos(inputDate, expected);
        Assert.assertEquals(expected.getIntervalNumber(), new Integer(10));
        Assert.assertEquals(expected.getIntervalType(), DateIntervalType.SECONDS);
    }

    @Test
    public void testGetDateIntervalBeanWithMinute() throws ParseException
    {
        calendar.add(Calendar.MINUTE, -1);
        Date inputDate = calendar.getTime();
        expected = DateIntervalUtils.getDateIntervalBean(inputDate);
        logDebugInfos(inputDate, expected);
        Assert.assertEquals(expected.getIntervalNumber(), new Integer(1));
        Assert.assertEquals(expected.getIntervalType(), DateIntervalType.MINUTE);

        calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 0);
        inputDate = calendar.getTime();
        expected = DateIntervalUtils.getDateIntervalBean(inputDate);
        logDebugInfos(inputDate, expected);
        Assert.assertEquals(expected.getIntervalNumber(), new Integer(0));
        Assert.assertEquals(expected.getIntervalType(), DateIntervalType.SECOND);

        calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, -70);
        inputDate = calendar.getTime();
        expected = DateIntervalUtils.getDateIntervalBean(inputDate);
        logDebugInfos(inputDate, expected);
        Assert.assertEquals(expected.getIntervalNumber(), new Integer(1));
        Assert.assertEquals(expected.getIntervalType(), DateIntervalType.MINUTE);
    }

    @Test
    public void testGetDateIntervalBeanWithMinutes() throws ParseException
    {
        calendar.add(Calendar.MINUTE, -10);
        Date inputDate = calendar.getTime();
        expected = DateIntervalUtils.getDateIntervalBean(inputDate);
        logDebugInfos(inputDate, expected);
        Assert.assertEquals(expected.getIntervalNumber(), new Integer(10));
        Assert.assertEquals(expected.getIntervalType(), DateIntervalType.MINUTES);

        calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -70);
        inputDate = calendar.getTime();
        expected = DateIntervalUtils.getDateIntervalBean(inputDate);
        logDebugInfos(inputDate, expected);
        Assert.assertEquals(expected.getIntervalNumber(), new Integer(1));
        Assert.assertEquals(expected.getIntervalType(), DateIntervalType.HOUR);
    }

    @Test
    public void testGetDateIntervalBeanWithHour() throws ParseException
    {
        calendar.add(Calendar.HOUR, -1);
        Date inputDate = calendar.getTime();
        expected = DateIntervalUtils.getDateIntervalBean(inputDate);
        logDebugInfos(inputDate, expected);
        Assert.assertEquals(expected.getIntervalNumber(), new Integer(1));
        Assert.assertEquals(expected.getIntervalType(), DateIntervalType.HOUR);
    }

    @Test
    public void testGetDateIntervalBeanWithDay() throws ParseException
    {
        calendar.add(Calendar.DAY_OF_WEEK, -1);
        Date inputDate = calendar.getTime();
        expected = DateIntervalUtils.getDateIntervalBean(inputDate);
        logDebugInfos(inputDate, expected);
        Assert.assertEquals(expected.getIntervalNumber(), new Integer(1));
        Assert.assertEquals(expected.getIntervalType(), DateIntervalType.DAY);
    }

    @Test
    public void testGetDateIntervalBeanWithDays() throws ParseException
    {
        calendar.add(Calendar.DAY_OF_WEEK, -3);
        Date inputDate = calendar.getTime();
        expected = DateIntervalUtils.getDateIntervalBean(inputDate);
        logDebugInfos(inputDate, expected);
        Assert.assertEquals(expected.getIntervalNumber(), new Integer(3));
        Assert.assertEquals(expected.getIntervalType(), DateIntervalType.DAYS);
    }

    @Test
    public void testGetDateIntervalBeanWithWeeks() throws ParseException
    {
        calendar.add(Calendar.DAY_OF_WEEK, -6);
        Date inputDate = calendar.getTime();
        expected = DateIntervalUtils.getDateIntervalBean(inputDate);
        logDebugInfos(inputDate, expected);
        Assert.assertEquals(expected.getIntervalType(), DateIntervalType.DAYS);

        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, -8);
        inputDate = calendar.getTime();
        expected = DateIntervalUtils.getDateIntervalBean(inputDate);
        logDebugInfos(inputDate, expected);
        Assert.assertEquals(expected.getIntervalType(), DateIntervalType.WEEKS);
    }

    private void logDebugInfos(Date inputDate, DateIntervalBean expected)
    {
        String parentMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        this.logDebugInfos(parentMethodName, inputDate, expected);
    }

    private void logDebugInfos(String method, Date inputDate, DateIntervalBean expected)
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug(">>> " + method);
            LOGGER.debug("input date: " + inputDate);
            Date now = Calendar.getInstance().getTime();
            LOGGER.debug("actual date: " + now);
            LOGGER.debug("intervalNumber: " + expected.getIntervalNumber());
            LOGGER.debug("intervalUnit: " + expected.getIntervalType().toString().toLowerCase());
            LOGGER.debug("");
        }
    }
}
