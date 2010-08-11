package com.tapestwitter.services.security.impl;

import com.tapestwitter.domain.business.UserManager;
import com.tapestwitter.domain.model.User;
import com.tapestwitter.util.ValidationUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * UserDetailsService implementation to implement
 * Spring Security Login with the security filter chain
 * 
 * @author karesti
 */
public class UserDetailsServiceImpl implements UserDetailsService
{

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    /**
     * Tapestwitter UserManager
     */
    private final UserManager userManager;

    /**
     * Constructor
     * 
     * @param userManager
     */
    public UserDetailsServiceImpl(UserManager userManager)
    {
        this.userManager = userManager;
    }

    /**
     * @see UserDetailsService
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("loadUserByUsername userName=" + username);
        }
        User user = null;
        if (ValidationUtils.isEmail(username))
        {
            user = userManager.findByEmail(username);
        }
        else
        {
            user = userManager.findByUsername(username);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("User = " + user);
        }
        return user;
    }
}
