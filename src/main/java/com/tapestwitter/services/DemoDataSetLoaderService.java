package com.tapestwitter.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.annotations.EagerLoad;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.tapestwitter.domain.business.TweetLoader;
import com.tapestwitter.domain.dao.CrudServiceDAO;
import com.tapestwitter.domain.model.Authority;
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
        CrudServiceDAO serviceDAO = (CrudServiceDAO) applicationContext.getBean("crudServiceDAO");

        Authority roleUser = new Authority();
        roleUser.setAuthority(Authority.ROLE_USER);
        serviceDAO.create(roleUser);

        Authority roleAdmin = new Authority();
        roleAdmin.setAuthority(Authority.ROLE_ADMIN);
        serviceDAO.create(roleAdmin);

        User userLaurent = new User();
        userLaurent.setFullName("Laurent Guerin");
        userLaurent.setLogin("laurent");
        userLaurent.setPassword("laurentpass");
        userLaurent.setEmail("laurent@tapestweetter.org");
        userLaurent.addAuthority(roleUser);
        serviceDAO.create(userLaurent);

        User userKatia = new User();
        userKatia.setFullName("Katia Aresti");
        userKatia.setLogin("katia");
        userKatia.setPassword("katiapass");
        userKatia.setEmail("katia@tapestweetter.org");
        userKatia.addAuthority(roleAdmin);
        serviceDAO.create(userKatia);

        String tweetMsg = "Tapestry 5 rocks";
        tweetLoader.createTweetFromUser(userLaurent, tweetMsg);
        tweetLoader.createTweetFromUser(userKatia, tweetMsg);

        logger.info("<<< END");
    }
}
