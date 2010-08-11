/**
 * 
 */
package com.tapestwitter.components.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.tapestwitter.common.DateIntervalBean;
import com.tapestwitter.common.DateIntervalType;

/**
 * Classe utilitaire permettant de calculer un intervale depuis une date de
 * publication.
 * 
 * @author lguerin
 */
public class DateIntervalUtils
{

    public static DateIntervalBean getDateIntervalBean(Date inputDate) throws ParseException
    {
        Long now = Calendar.getInstance().getTimeInMillis();
        Long creation = inputDate.getTime();
        float elapsedMinutes = (int) (now - creation) / (1000 * 60);
        DateIntervalBean dateIntervalBean = new DateIntervalBean();

        Integer delta = 0;
        DateIntervalType typeInterval = DateIntervalType.SECOND;

        if (elapsedMinutes == 0)
        {
            // Calcul de l'intervale en secondes
            delta = (int) (now - creation) / (1000);
            typeInterval = delta == 0 ? DateIntervalType.SECOND : DateIntervalType.SECONDS;
        }
        else if (elapsedMinutes >= 1 && elapsedMinutes < 60)
        {
            // Calcul de l'intervalle en minutes
            delta = Math.round(elapsedMinutes);
            typeInterval = delta == 1 ? DateIntervalType.MINUTE : DateIntervalType.MINUTES;
        }
        else if ((elapsedMinutes >= 60) && (elapsedMinutes < 60 * 24))
        {
            // Calcul de l'intervale en heures
            delta = Math.round(elapsedMinutes / 60);
            delta = delta == 0 ? 1 : delta;
            typeInterval = delta == 1 ? DateIntervalType.HOUR : DateIntervalType.HOURS;
        }
        else if ((elapsedMinutes >= 60 * 24) && (elapsedMinutes < 60 * 24 * 7))
        {
            // Calcul de l'intervale en jours
            delta = Math.round(((elapsedMinutes / 60)) / 24);
            delta = delta == 0 ? 1 : delta;
            typeInterval = delta == 1 ? DateIntervalType.DAY : DateIntervalType.DAYS;
        }
        else
        {
            delta = 0;
            typeInterval = DateIntervalType.WEEKS;
        }
        dateIntervalBean = computeDateIntervalBean(delta, typeInterval);
        return dateIntervalBean;
    }

    private static DateIntervalBean computeDateIntervalBean(Integer intervalNumber, DateIntervalType intervalType)
    {
        DateIntervalBean result = new DateIntervalBean();
        result.setIntervalNumber(intervalNumber);
        result.setIntervalType(intervalType);
        return result;
    }
}
