package com.tapestwitter.domain.business;

import com.tapestwitter.domain.exception.CreateAuthorityException;
import com.tapestwitter.domain.exception.CreateUserException;
import com.tapestwitter.domain.model.Authority;
import com.tapestwitter.domain.model.User;

/**
 * 
 * @author karesti
 *
 */
public interface UserManager
{

	void addUser(User user) throws CreateUserException, CreateAuthorityException;

	void addAuthority(User user, Authority authority) throws CreateAuthorityException;

	User findByUsername(String username);
	
	boolean isAvailableName(String username);
	
	boolean isAvailableEmail(String email);
}
