package com.tapestwitter.services.security.impl;

import com.tapestwitter.domain.model.User;
import com.tapestwitter.services.security.TapestwitterSecurityContext;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Implémentation web du TapestwitterSecurityContext
 * 
 * @author karesti
 */
@Component("tapestwitterSecurityContext")
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
