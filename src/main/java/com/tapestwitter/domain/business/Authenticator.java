package com.tapestwitter.domain.business;

import com.tapestwitter.domain.exception.BusinessException;
import com.tapestwitter.domain.exception.UserAlreadyExistsException;
import com.tapestwitter.domain.model.Authority;
import com.tapestwitter.domain.model.User;

/**
 * Authenticator Interface
 * 
 * @author karesti
 */
public interface Authenticator
{

    /**
     * This method adds a new user to tapestwitter
     * 
     * @param user
     * @throws UserAlreadyExistsException
     * @throws BusinessException
     */
    void createUser(User user) throws UserAlreadyExistsException, BusinessException;

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
     * Returns true if the userName is not already in use
     * 
     * @param username
     * @return true if available
     */
    boolean isAvailableName(String username);

    /**
     * Returns true if the email is not already in use
     * 
     * @param email
     * @return true if available
     */
    boolean isAvailableEmail(String email);

    /**
     * Creates an authority
     * 
     * @param authority
     * @throws BusinessException
     */
    void addAuthority(Authority authority) throws BusinessException;

    /**
     * This method andd an authory to teh given user
     * 
     * @param user
     * @param authority
     * @throws BusinessException
     */
    void addAuthorityToUser(User user, Authority authority) throws BusinessException;
}
