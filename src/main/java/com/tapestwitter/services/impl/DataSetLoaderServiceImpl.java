package com.tapestwitter.services.impl;

import com.tapestwitter.domain.business.TweetManager;
import com.tapestwitter.domain.business.UserManager;
import com.tapestwitter.domain.exception.CreateAuthorityException;
import com.tapestwitter.domain.exception.CreateUserException;
import com.tapestwitter.domain.model.Authority;
import com.tapestwitter.domain.model.User;
import com.tapestwitter.services.DataSetLoaderService;
import com.tapestwitter.services.security.TapestwitterSecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.annotations.EagerLoad;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

@EagerLoad
public class DataSetLoaderServiceImpl implements DataSetLoaderService
{
    /**
     * Logger de la classe
     */
    private static final Logger logger = LoggerFactory.getLogger(DataSetLoaderServiceImpl.class);

    public DataSetLoaderServiceImpl(ApplicationContext applicationContext, @Inject
    @Symbol(SymbolConstants.PRODUCTION_MODE)
    boolean productionMode)
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
        User user = new User();
        Authority authority = new Authority();
        TapestwitterSecurityContext securityCtx = (TapestwitterSecurityContext) applicationContext.getBean("tapestwitterSecurityContext");
        try
        {
            user.setFullName("Laurent Guerin");
            user.setLogin("laurent");
            user.setPassword("laurentpass");
            user.setEmail("laurent@tapestwitter.org");
            userManager.addUser(user);
            securityCtx.logIn(user);

            user = new User();
            user.setFullName("Katia Aresti");
            user.setLogin("katia");
            user.setPassword("katiapass");
            user.setEmail("katia@tapestwitter.org");
            userManager.addUser(user);

            authority = new Authority();
            authority.setAuthority("ROLE_ADMIN");
            userManager.addAuthority(user, authority);

        }
        catch (CreateUserException e)
        {
            logger.error("Erreur sur le chargement des données de test " + user, e);
        }
        catch (CreateAuthorityException e)
        {
            logger.error("Erreur sur le chargement des données de test " + authority, e);
        }

        String tweetMsg = "TapesTwitter, une application de demo 'Twitter like' ecrite avec Tapestry 5";
        tweetManager.createTweet(tweetMsg);

        logger.info("<<< Chargement termine.");
    }
}
