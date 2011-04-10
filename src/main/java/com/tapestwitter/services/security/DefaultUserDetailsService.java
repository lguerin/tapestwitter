package com.tapestwitter.services.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tapestwitter.domain.business.Authenticator;
import com.tapestwitter.domain.model.User;
import com.tapestwitter.util.ValidationUtils;

/**
 * UserDetailsService implementation to implement
 * Spring Security Login with the security filter chain
 * 
 * @author karesti
 */
public class DefaultUserDetailsService implements UserDetailsService
{

    private static final Logger logger = LoggerFactory.getLogger(DefaultUserDetailsService.class);

    /**
     * Tapestwitter authenticator
     */
    private final Authenticator authenticator;

    /**
     * Constructor
     * 
     * @param authenticator
     */
    public DefaultUserDetailsService(Authenticator authenticator)
    {
        this.authenticator = authenticator;
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
            user = authenticator.findByEmail(username);
        }
        else
        {
            user = authenticator.findByUsername(username);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("User = " + user);
        }
        return user;
    }
}
