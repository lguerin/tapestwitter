package com.tapestwitter.services;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.annotations.EagerLoad;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.tapestwitter.domain.business.Authenticator;
import com.tapestwitter.domain.business.TweetLoader;
import com.tapestwitter.domain.exception.BusinessException;
import com.tapestwitter.domain.exception.UserAlreadyExistsException;
import com.tapestwitter.domain.model.Authority;
import com.tapestwitter.domain.model.Tweet;
import com.tapestwitter.domain.model.User;

@EagerLoad
public class DemoDataSetLoaderService implements DataSetLoaderService
{

    private static final Logger logger = LoggerFactory.getLogger(DemoDataSetLoaderService.class);

    public DemoDataSetLoaderService(ApplicationContext applicationContext, @Inject
    @Symbol(SymbolConstants.PRODUCTION_MODE)
    boolean productionMode)
    {

        logger.info(">>> START LOAD ... ");

        TweetLoader tweetLoader = (TweetLoader) applicationContext.getBean("tweetLoader");
        Authenticator userService = (Authenticator) applicationContext.getBean("authenticator");

        Authority roleUser = new Authority();
        roleUser.setAuthority(Authority.ROLE_USER);

        Authority roleAdmin = new Authority();
        roleAdmin.setAuthority(Authority.ROLE_ADMIN);

        try
        {
            userService.addAuthority(roleUser);
            userService.addAuthority(roleAdmin);
        }
        catch (BusinessException e)
        {
            logger.error("Test data error on authority creation: ", e);
        }

        User userLaurent = new User();
        userLaurent.setFullName("Laurent Guerin");
        userLaurent.setLogin("laurent");
        userLaurent.setPassword("laurentpass");
        userLaurent.setEmail("laurent@dreamcat.fr");

        User userKatia = new User();
        userKatia.setFullName("Katia Aresti");
        userKatia.setLogin("katia");
        userKatia.setPassword("katiapass");
        userKatia.setEmail("katiaaresti@gmail.com");

        try
        {
            userService.createUser(userLaurent);
            userService.createUser(userKatia);
        }
        catch (UserAlreadyExistsException e)
        {
            logger.error("Test data error on user creation: ", e);
        }
        catch (BusinessException e)
        {
            logger.error("Test data error on user creation: ", e);
        }

        String tweetMsg = "Tapestry 5 rocks !";
        tweetLoader.createTweetFromUser(userLaurent, tweetMsg);
        tweetLoader.createTweetFromUser(userKatia, "A must have : 'Tapestry 5 in Action' book by Igor Drobiazko");
        tweetLoader.createTweetFromUser(userKatia, "RT @laurent " + tweetMsg);
        tweetLoader.createTweetFromUser(userLaurent, "RT @hlship 5.2.5 does quite well in a not-quite-scientific web framework shootout: http://t.co/KiOW7Ny");
        tweetLoader.createTweetFromUser(userLaurent, "RT @karesti A must have : Tapestry 5 in Action by Igor Drobiazko");
        tweetLoader.createTweetFromUser(
                userKatia,
                "RT @hlship #tapestry 5.3.0 will be ready in a couple of weeks; it will be the first alpha version of the 5.3 release");

        List<Tweet> result = tweetLoader.listAllTweet();
        if (logger.isDebugEnabled())
        {
            for (Tweet t : result)
            {
                ToStringBuilder.reflectionToString(t, ToStringStyle.MULTI_LINE_STYLE);
            }
        }
        logger.info("<<< END");
    }
}
