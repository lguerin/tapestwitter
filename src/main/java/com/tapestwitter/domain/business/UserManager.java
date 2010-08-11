package com.tapestwitter.domain.business;

import com.tapestwitter.domain.exception.CreateAuthorityException;
import com.tapestwitter.domain.exception.CreateUserException;
import com.tapestwitter.domain.model.Authority;
import com.tapestwitter.domain.model.User;

/**
 * User Manager Interface
 * 
 * @author karesti
 */
public interface UserManager
{

    /**
     * This method adds a new user to tapestwitter
     * 
     * @param user
     * @throws CreateUserException
     * @throws CreateAuthorityException
     */
    void addUser(User user) throws CreateUserException, CreateAuthorityException;

    /**
     * This method andd an authory to teh given user
     * 
     * @param user
     * @param authority
     * @throws CreateAuthorityException
     */
    void addAuthority(User user, Authority authority) throws CreateAuthorityException;

    /**
     * Finds one user by his username (login)
     * 
     * @param username
     * @return the user
     */
    User findByUsername(String username);

    /**
     * Finds one user by his e-mail
     * 
     * @param email
     * @return the user
     */
    User findByEmail(String email);

    /**
     * Returns true if the userName is not already
     * in use
     * 
     * @param username
     * @return true if available
     */
    boolean isAvailableName(String username);

    /**
     * Returs tue if the email is not already
     * in use
     * 
     * @param email
     * @return true if available
     */
    boolean isAvailableEmail(String email);
}
