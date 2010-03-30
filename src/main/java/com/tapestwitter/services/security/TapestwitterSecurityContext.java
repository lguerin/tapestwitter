package com.tapestwitter.services.security;

import com.tapestwitter.domain.model.User;

/**
 * Inteface qui contient l'ensemble de l'authorization
 * sur Tapestwitter
 * 
 * @author karesti
 *
 */
public interface TapestwitterSecurityContext {

	/**
	 * 
	 * @param user
	 */
	void logIn(User user);
	
	/**
	 * Vérification si l'utilisateur est connecté
	 * 
	 * @return
	 */
	boolean isLoggedIn();

}
