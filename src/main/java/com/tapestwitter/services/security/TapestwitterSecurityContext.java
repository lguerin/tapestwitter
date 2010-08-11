package com.tapestwitter.services.security;

import com.tapestwitter.domain.model.User;

/**
 * Inteface qui contient l'ensemble de l'authorization
 * sur Tapestwitter
 * 
 * @author karesti
 */
public interface TapestwitterSecurityContext
{

    /**
     * Authenticate the user
     * 
     * @param user
     */
    void logIn(User user);

    /**
     * Test if the user is connected.
     * 
     * @return true if the user is connected
     */
    boolean isLoggedIn();

    /**
     * Get the authenticated {@link User}
     * 
     * @return current authenticated user
     */
    User getUser();

    /**
     * Explicit logout the current user
     */
    void logout();
}
