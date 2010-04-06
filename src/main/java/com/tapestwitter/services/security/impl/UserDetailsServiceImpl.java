package com.tapestwitter.services.security.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import com.tapestwitter.domain.business.UserManager;
import com.tapestwitter.domain.model.User;

/**
 * 
 * @author karesti
 *
 */
public class UserDetailsServiceImpl implements UserDetailsService
{

	private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	private final UserManager userManager;

	public UserDetailsServiceImpl(UserManager userManager)
	{
		this.userManager = userManager;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("loadUserByUsername userName=" + username);
		}
		User user = userManager.findByUsername(username);

		if (logger.isDebugEnabled())
		{
			logger.debug("User = " + user);
		}
		return user;
	}

}
