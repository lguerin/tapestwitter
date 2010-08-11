/**
 * 
 */
package com.tapestwitter.common;

/**
 * Simple Bean permettant de representer un intervalle de temps.
 * 
 * @author lguerin
 */
public class DateIntervalBean
{
    /**
     * Numeric value of the interval (0,1, 10, ...)
     */
    private Integer intervalNumber;

    /**
     * Unit value of the interval (seconds, minuts, ...)
     */
    private DateIntervalType intervalType;

    /**
     * Default constructor
     */
    public DateIntervalBean()
    {
    }

    /**
     * @return the intervalNumber
     */
    public Integer getIntervalNumber()
    {
        return intervalNumber;
    }

    /**
     * @param intervalNumber the intervalNumber to set
     */
    public void setIntervalNumber(Integer intervalNumber)
    {
        this.intervalNumber = intervalNumber;
    }

    /**
     * @return the intervalType
     */
    public DateIntervalType getIntervalType()
    {
        return intervalType;
    }

    /**
     * @param intervalType the intervalType to set
     */
    public void setIntervalType(DateIntervalType intervalType)
    {
        this.intervalType = intervalType;
    }

}
