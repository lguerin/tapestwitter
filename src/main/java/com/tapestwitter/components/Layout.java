package com.tapestwitter.components;

import com.tapestwitter.services.TapestwiterURLResolver;
import com.tapestwitter.services.security.TapestwitterSecurityContext;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

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

	@Log
	@OnEvent(component = "logout")
	public void onLogout()
	{
		securityContext.logout();
	}
}
