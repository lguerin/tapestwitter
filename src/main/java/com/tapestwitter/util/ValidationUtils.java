package com.tapestwitter.util;

import java.util.regex.Pattern;

import com.tapestwitter.common.EnumValidation;

import org.springframework.util.Assert;

import org.apache.tapestry5.ioc.Messages;

/**
 * Utility class for the Validation
 * 
 * @author kAresti
 */
public class ValidationUtils
{

    private static final String ATOM = "[^\\x00-\\x1F^\\(^\\)^\\<^\\>^\\@^\\,^\\;^\\:^\\\\^\\\"^\\.^\\[^\\]^\\s]";

    private static final String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)*";

    private static final String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";

    private static final Pattern PATTERN = Pattern.compile("^" + ATOM + "+(\\." + ATOM + "+)*@" + DOMAIN + "|" + IP_DOMAIN + ")$", Pattern.CASE_INSENSITIVE);

    private static final String LABEL_DIVIDER = ".";

    /**
     * Validation email
     * 
     * @param value
     * @return isEmail
     */
    public static boolean isEmail(String value)
    {
        return PATTERN.matcher(value).matches();
    }

    public static boolean isOk(EnumValidation enumValue)
    {
        Assert.notNull(enumValue, "enumValue");
        return EnumValidation.OK.equals(enumValue);
    }

    public static boolean isInfo(EnumValidation enumValue)
    {
        Assert.notNull(enumValue, "enumValue");
        return EnumValidation.INFO.equals(enumValue);
    }

    public static boolean isError(EnumValidation enumValue)
    {
        Assert.notNull(enumValue, "enumValue");
        return !(EnumValidation.OK.equals(enumValue) || EnumValidation.INFO.equals(enumValue));
    }

    /**
     * This method returns the value of the key in the message catalogue
     * for Tapestwitter valiudation
     * 
     * @param fieldId
     * @param enumValue
     * @param messages
     * @return String, message catalogue Key for validation
     */
    public static String getLabel(String fieldId, EnumValidation enumValue, Messages messages)
    {
        Assert.notNull(fieldId, "fieldId");
        Assert.notNull(enumValue, "enumValue");
        Assert.notNull(messages, "messages");

        String labelKey = messages.get(fieldId.concat(LABEL_DIVIDER).concat(enumValue.name().toLowerCase()));
        return labelKey;
    }
}
