package com.tapestwitter.services.security.impl;

import com.tapestwitter.domain.model.User;
import com.tapestwitter.services.security.TapestwitterSecurityContext;

import org.slf4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

/**
 * Implémentation web du TapestwitterSecurityContext
 * 
 * @author karesti
 *
 */
@Component("tapestwitterSecurityContext")
public class TapestwitterSecurityContextImpl implements TapestwitterSecurityContext
{
	@Inject
	private Logger logger;

	@Inject
	private Request request;

	/**
	 * La méthode log in l'utilisateur
	 * Le TOKEN est mis sur le security context
	 * 
	 * @return
	 */
	public void logIn(User user)
	{
		Assert.notNull(user, "user");
		UsernamePasswordAuthenticationToken logged = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(logged);
	}

	public boolean isLoggedIn()
	{
		if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null)
		{
			if ("anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getName()))
			{
				return false;
			}
			return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
		}
		return false;
	}

	public User getUser()
	{
		User user = null;
		if (isLoggedIn())
		{
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User)
			{
				user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			}
		}
		return user;
	}

	@Log
	public void logout()
	{
		// Invalidate the current Tapestry user session
		//logger.info("Invalidate the Tapestry session of the current user: " + getUser().getLogin());
		//Session currentSession = request.getSession(true);
		SecurityContextHolder.getContext().setAuthentication(null);
		SecurityContextHolder.clearContext();
	}
}
