package com.tapestwitter.pages;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.tapestwitter.components.RollingItems;
import com.tapestwitter.domain.business.TweetManager;
import com.tapestwitter.domain.business.UserManager;
import com.tapestwitter.domain.model.Tweet;
import com.tapestwitter.domain.model.User;
import com.tapestwitter.model.DamierItemModel;
import com.tapestwitter.pages.home.Dashboard;
import com.tapestwitter.services.security.TapestwitterSecurityContext;

/**
 * Start page of TapesTwitter.
 */
public class Index
{
    @SuppressWarnings("unused")
    @Component(id = "toptweets")
    private RollingItems rollingItems;

    @InjectPage
    private Dashboard dashboard;

    @InjectPage
    private ErrorPage errorPage;

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

    @SuppressWarnings("unused")
    @Property
    private List<Tweet> tweets;

    @SuppressWarnings("unused")
    @Property
    private Tweet current;

    @Property
    private List<DamierItemModel> damierItems;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private String query;

    @SuppressWarnings("unused")
    @Property
    @Persist
    private List<Tweet> result;

    @Inject
    private Block topTweetsBlock;

    @Inject
    private Block searchResultBlock;

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

    @OnEvent(value = EventConstants.ACTIVATE)
    public Object checkUser(String userName)
    {
        user = userManager.findByUsername(userName);

        if (user == null) { return errorPage; }
        if (securityCtx.isLoggedIn())
        {
            User userLogged = securityCtx.getUser();
            if (user.getLogin().equals(userLogged.getLogin())) { return dashboard; }
        }

        return null;
    }

    /**
     * Redirect user to his dashboard page if already authenticated.
     * 
     * @return User Dashboard Page if already authenticated.
     */
    @SuppressWarnings("unused")
    @OnEvent(value = EventConstants.ACTIVATE)
    private Object redirectToDashboardPage()
    {
        // If the user is logged in, redirect to dashboard page
        boolean isLoggedIn = securityCtx.isLoggedIn();
        if (isLoggedIn) { return dashboard; }
        return null;
    }

    @Log
    @OnEvent(value = EventConstants.SUCCESS, component = "")
    public void onSearchResults()
    {
        result = new LinkedList<Tweet>();
        if (!StringUtils.isEmpty(query))
        {
            result = tweetManager.findTweetByKeyword(query);
        }
    }

    /**
     * Return the active block in the content wrapper of the page
     * 
     * @return active block to display
     */
    public Object getActiveBlock()
    {
        if (!StringUtils.isEmpty(query)) { return searchResultBlock; }
        return topTweetsBlock;
    }
}
