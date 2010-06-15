package com.tapestwitter.services.impl;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.annotations.EagerLoad;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.tapestwitter.domain.business.TweetManager;
import com.tapestwitter.domain.business.UserManager;
import com.tapestwitter.domain.exception.BusinessException;
import com.tapestwitter.domain.exception.UserAlreadyExistsException;
import com.tapestwitter.domain.model.Authority;
import com.tapestwitter.domain.model.User;
import com.tapestwitter.services.DataSetLoaderService;

@EagerLoad
public class DataSetLoaderServiceImpl implements DataSetLoaderService
{
	/**
	 * Logger de la classe
	 */
	private static final Logger logger = LoggerFactory.getLogger(DataSetLoaderServiceImpl.class);

	public DataSetLoaderServiceImpl(ApplicationContext applicationContext, @Inject @Symbol(SymbolConstants.PRODUCTION_MODE) boolean productionMode)
	{

		TweetManager tweetManager = (TweetManager) applicationContext.getBean("tweetManager");
		UserManager userManager = (UserManager) applicationContext.getBean("userManager");
	
		// Bypass creation if already done or if productionMode
		if (productionMode || tweetManager.findTweetByKeyword("TapesTwitter").size() >= 1)
		{
			logger.info("Le jeu de donnees est deja charge, on sort sans rien faire.");
			return;
		}

		logger.info(">>> Chargement du jeu de donnees par defaut...");
		logger.info(">>> Users : Chargement du jeu de donnees par defaut...");
			
		try
		{
			Authority roleUser = new Authority();
			roleUser.setAuthority("ROLE_USER");
			userManager.addAuthority(roleUser);
			
			Authority roleAdmin = new Authority();
			roleAdmin.setAuthority("ROLE_ADMIN");
			userManager.addAuthority(roleAdmin);
			
			User userLaurent = new User();
			userLaurent.setFullName("Laurent Guerin");
			userLaurent.setLogin("laurent");
			userLaurent.setPassword("laurentpass");
			userLaurent.setEmail("laurent@tapestwitter.org");
			userLaurent.addAuthority(roleUser);
			userManager.addUser(userLaurent);
			
			User userKatia = new User();
			userKatia.setFullName("Katia Aresti");
			userKatia.setLogin("katia");
			userKatia.setPassword("katiapass");
			userKatia.setEmail("katia@tapestwitter.org");
			userKatia.addAuthority(roleAdmin);
			userManager.addUser(userKatia);
			
			String tweetMsg = "TapesTwitter, une application de demo 'Twitter like' ecrite avec Tapestry 5";
			tweetManager.createTweetFromUser(userLaurent, tweetMsg);
			tweetManager.createTweetFromUser(userKatia, tweetMsg);

		}
		catch (BusinessException e)
		{
			logger.error("Test data error", e);
		} catch (UserAlreadyExistsException e) {
			
			logger.error("Test data error", e);
		}
		
		logger.info("<<< Chargement termine.");
	}
}