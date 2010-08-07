package com.tapestwitter.components;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * A "Twitter-like" Text Counter component.
 * 
 * @author lguerin
 *
 */
@Import(library="textcounter.js")
public class TextCounter
{
	/**
	 * Default max data length for the textarea
	 */
	private final static String DEFAULT_MAX_LENGTH_TEXT = "140";

	/**
	 * The id of the HTML TextArea to observe
	 */
	@Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
	private String textareaId;

	/**
	 * The allowed max length of the textarea
	 */
	@Parameter(value = DEFAULT_MAX_LENGTH_TEXT, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
	private String maxlength;

	/**
	 * The identifiant of the div element rendered by the component. 
	 * The default value is className.
	 */
	@Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
	private String targetElementId;

	/**
	 * The CSS class for the div element rendered by the component.
	 * The default value is associated to the targetElementId parameter.
	 */
	@Parameter(name = "class", defaultPrefix = BindingConstants.LITERAL)
	private String className;

	/**
	 * The limit length to respect
	 */
	@Parameter(value = StringUtils.EMPTY, defaultPrefix = BindingConstants.LITERAL)
	private String warningLimit;

	/**
	 * The style used to inform the user that the limit isn't so far...
	 */
	@Parameter(value = StringUtils.EMPTY, defaultPrefix = BindingConstants.LITERAL)
	private String warningLimitStyle;

	@Inject
	private JavaScriptSupport javaScriptSupport;

	@BeginRender
	public void beginRender(MarkupWriter writer)
	{
		writer.element("div", "id", targetElementId, "class", StringUtils.isEmpty(className) ? targetElementId : className);
	}

	@AfterRender
	public void afterRender(MarkupWriter writer)
	{
		writer.end(); // div
		javaScriptSupport.addScript(String.format("new TextCounter('%s', '%s', '%s', '%s', '%s');", textareaId, targetElementId, maxlength, warningLimit, warningLimitStyle));
	}
}
