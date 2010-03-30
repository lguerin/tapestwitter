package com.tapestwitter.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;

/**
 * Start page of application tapestwitter.
 */
public class Index
{
	@InjectPage
	private HomePage homePage;

	/**
	 * Redirige l'utilisateur sur la page d'accueil si celui-ci est 
	 * deja authentifie.
	 * 
	 * @return	Page d'accueil si authentifie
	 */
	@SuppressWarnings("unused")
	@OnEvent(value = EventConstants.ACTIVATE)
	private Object redirectToHomePage()
	{
		// Tester par rapport a l'authentification
		boolean isLoggedIn = true;
		if (isLoggedIn)
		{
			return homePage;
		}
		return null;
	}
}
