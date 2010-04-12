package com.tapestwitter.components;

import java.net.MalformedURLException;
import java.net.URL;

import com.tapestwitter.services.TapestwiterURLResolver;
import com.tapestwitter.services.security.TapestwitterSecurityContext;

import org.slf4j.Logger;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

/**
 * Main Layout component for pages of application tapestwitter.
 */
public class Layout
{
	/** The page title, for the <title> element and the <h1> element. */
	@SuppressWarnings("unused")
	@Property
	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String title;

	@Inject
	private TapestwiterURLResolver urlResolver;

	@Inject
	private TapestwitterSecurityContext securityContext;

	public String getLogoutUrl()
	{
		return urlResolver.getLogoutUrl();
	}

	@Inject
	private Request request;

	@Inject
	private Logger logger;

	@OnEvent(component = "logout")
	public Object onLogout()
	{
		logger.debug(">>> invalidate the current user session");
		request.getSession(false).invalidate();
		securityContext.logout();
		URL external = null;
		try
		{
			external = new URL("http://localhost:8080/tapestwitter/j_spring_security_logout");
		}
		catch (MalformedURLException e)
		{
			throw new RuntimeException(e);
		}
		return external;
	}
}
