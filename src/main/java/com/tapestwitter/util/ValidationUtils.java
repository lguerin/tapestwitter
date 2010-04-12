package com.tapestwitter.util;

import java.util.regex.Pattern;

import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.internal.util.Defense;

import com.tapestwitter.common.EnumValidation;

/**
 * Utility class for the Validation
 * 
 * @author kAresti
 *
 */
public class ValidationUtils {

	private static final String ATOM = "[^\\x00-\\x1F^\\(^\\)^\\<^\\>^\\@^\\,^\\;^\\:^\\\\^\\\"^\\.^\\[^\\]^\\s]";

    private static final String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)*";

    private static final String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";

    private static final Pattern PATTERN = Pattern
            .compile("^" + ATOM + "+(\\." + ATOM + "+)*@" + DOMAIN + "|" + IP_DOMAIN + ")$", Pattern.CASE_INSENSITIVE);
    
    public static boolean isEmail(String value){
    	return PATTERN.matcher(value).matches();
    }
    
	public static boolean isOk(EnumValidation enumValue){
		Defense.notNull(enumValue, "enumValue");
		return EnumValidation.OK.equals(enumValue);
	}
	
	public static boolean isInfo(EnumValidation enumValue){
		Defense.notNull(enumValue, "enumValue");
		return EnumValidation.INFO.equals(enumValue);
	}

	public static boolean isError(EnumValidation enumValue){
		Defense.notNull(enumValue, "enumValue");
		return ! (EnumValidation.OK.equals(enumValue) || EnumValidation.INFO.equals(enumValue));
	}
	
	public static String getLabel(String fieldId, EnumValidation enumValue, Messages messages)
    {
        Defense.notNull(enumValue, "enumValue");
        Defense.notNull(messages, "messages");
        
        return TapestryInternalUtils.getLabelForEnum(messages, fieldId, enumValue);

    }
}
