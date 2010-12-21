package com.tapestwitter.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.tapestwitter.components.RollingItems;
import com.tapestwitter.domain.business.TweetManager;
import com.tapestwitter.domain.business.UserManager;
import com.tapestwitter.domain.model.Tweet;
import com.tapestwitter.domain.model.User;
import com.tapestwitter.model.DamierItemModel;
import com.tapestwitter.services.security.TapestwitterSecurityContext;

/**
 * Start page of application tapestwitter.
 */
public class Index
{
    @Component(id = "toptweets")
    private RollingItems rollingItems;

    @InjectPage
    private HomePage homePage;

    @InjectPage
    private SearchErrorPage searchErrorPage;

    @Inject
    private TapestwitterSecurityContext securityCtx;

    @Inject
    private UserManager userManager;

    private User user;

    /**
     * Manager of {@link Tweet}
     */
    @Inject
    private TweetManager tweetManager;

    @Property
    private List<Tweet> tweets;

    @Property
    private Tweet current;

    @Property
    private List<DamierItemModel> damierItems;

    @SetupRender
    public void setup()
    {
        // Initialize top tweet items
        tweets = tweetManager.findRecentTweets(5);

        // Initialize Dammier items
        damierItems = new ArrayList<DamierItemModel>();
        damierItems.add(new DamierItemModel("wooki", "Wooki", "Opensource collaboration app", "http://wookicentral.com/", "wooki.png"));
        damierItems.add(new DamierItemModel("seesaw", "Seesaw", "Video Streaming", "http://www.seesaw.com/", "seesaw.png"));
        damierItems.add(new DamierItemModel("yanomo", "Yanomo", "Calendar-based time & task tracking", "http://www.yanomo.com/", "yanomo.png"));
        damierItems.add(new DamierItemModel("recurtrack", "RecurTrack", "Personal Budgetting", "http://recurtrack.com/", "recurtrack.png"));
        damierItems.add(new DamierItemModel("cubiculus", "Cubiculus", "Lego Building Instructions", "http://www.cubiculus.com/", "cubiculus.png"));
        damierItems.add(new DamierItemModel("oed", "Oxford English Dictionary", "The definitive Oxford English Dictionary", "http://www.oed.com/", "oed.png"));
        damierItems.add(new DamierItemModel("mybet", "myBet", "Sports Bets Casino Poker", "http://www.mybet.com/", "mybet.png"));
    }

    public Object onActivate(String userName)
    {
        user = userManager.findByUsername(userName);

        if (user == null) { return searchErrorPage; }
        if (securityCtx.isLoggedIn())
        {
            User userLogged = securityCtx.getUser();
            if (user.getLogin().equals(userLogged.getLogin())) { return homePage; }
        }

        return null;
    }

    /**
     * Redirige l'utilisateur sur la page d'accueil si celui-ci est
     * deja authentifie.
     * 
     * @return Page d'accueil si authentifie
     */
    @SuppressWarnings("unused")
    @OnEvent(value = EventConstants.ACTIVATE)
    private Object redirectToHomePage()
    {
        // Tester par rapport a l'authentification
        boolean isLoggedIn = securityCtx.isLoggedIn();
        if (isLoggedIn) { return homePage; }
        return null;
    }
}
