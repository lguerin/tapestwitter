/**
 * 
 */
package com.tapestwitter.components;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.tapestwitter.common.DateIntervalBean;
import com.tapestwitter.common.DateIntervalType;
import com.tapestwitter.components.utils.DateIntervalUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Permet de retourner l'intervale écoulé depuis une date de publication.
 * 
 * @author lguerin
 */
public class PublicationIntervalRenderer
{
    @Parameter(allowNull = false, required = true)
    private String publicationDate;

    @Inject
    private Messages messages;

    @Inject
    private Locale locale;

    /**
     * Date Formater
     */
    private static final SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @BeginRender
    public void beginPublicationInterval(MarkupWriter writer) throws ParseException
    {
        writer.element("span");
        writer.write(getInterval());
    }

    @AfterRender
    public void endPublicationInterval(MarkupWriter writer)
    {
        writer.end();
    }

    /**
     * Return the elapsed time interval between now and an input date.
     * 
     * @return spending time interval
     * @throws ParseException
     */
    private String getInterval() throws ParseException
    {
        Date inputDate = dateFormater.parse(publicationDate);
        DateIntervalBean interval = DateIntervalUtils.getDateIntervalBean(inputDate);
        String result = StringUtils.EMPTY;
        if (interval.getIntervalType().equals(DateIntervalType.WEEKS))
        {
            DateFormat dateFormatLocale = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
            result = messages.format("date-time-msg-date-formatter", dateFormatLocale.format(inputDate));
        }
        else
        {
            result = messages.format("standard-msg-date-formatter", String.valueOf(interval.getIntervalNumber()), messages.get(interval.getIntervalType()
                    .toString().toLowerCase()));
        }
        return result;
    }
}
