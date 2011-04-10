package com.tapestwitter.services.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.tapestwitter.domain.model.User;

/**
 * SecurityContext for Tapestwitter
 * 
 * @author karesti
 */
@Component("tapestwitterSecurityContext")
public class DefaultSecurityContext implements SecurityContext
{

    /**
     * La methode log in l'utilisateur
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
            if ("anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getName())) { return false; }
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

    public void logout()
    {
        SecurityContextHolder.getContext().setAuthentication(null);

        SecurityContextHolder.clearContext();
    }
}
