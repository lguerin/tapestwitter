package com.tapestwitter.services.security;

import com.tapestwitter.domain.model.User;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.apache.tapestry5.ioc.internal.util.Defense;

/**
 * Implémentation web du TapestwitterSecurityContext
 * 
 * @author karesti
 *
 */
public class TapestwitterSecurityContextImpl implements TapestwitterSecurityContext
{

	/**
	 * La méthode log in l'utilisateur
	 * Le TOKEN est mis sur le security context
	 * 
	 * @return
	 */
	public void logIn(User user)
	{
		Defense.notNull(user, "user");
		UsernamePasswordAuthenticationToken logged = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(logged);
	}

	public boolean isLoggedIn()
	{
		if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null)
		{
			return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
		}
		return false;
	}

}
