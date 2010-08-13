package com.tapestwitter.util;

import java.util.regex.Pattern;

/**
 * 
 * @author karesti
 *
 */
public final class EmailUtils {

	private static final String ATOM = "[^\\x00-\\x1F^\\(^\\)^\\<^\\>^\\@^\\,^\\;^\\:^\\\\^\\\"^\\.^\\[^\\]^\\s]";

	private static final String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)*";

	private static final String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";

	private static final Pattern PATTERN = Pattern.compile("^" + ATOM + "+(\\." + ATOM + "+)*@" + DOMAIN + "|" + IP_DOMAIN + ")$", Pattern.CASE_INSENSITIVE);
	
	public static boolean isEmail(String value)
	{
		return PATTERN.matcher(value).matches();
	}
}
