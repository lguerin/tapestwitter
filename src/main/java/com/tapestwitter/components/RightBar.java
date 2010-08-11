package com.tapestwitter.components;

import com.tapestwitter.domain.business.TweetManager;
import com.tapestwitter.domain.model.User;
import com.tapestwitter.services.security.TapestwitterSecurityContext;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * The rightbar menu component
 * 
 * @author lguerin
 */
public class RightBar
{
    @Inject
    private TapestwitterSecurityContext securityContext;

    @Property(write = false)
    private User user;

    @Inject
    private TweetManager tweetManager;

    @SuppressWarnings("unused")
    @Property(write = false)
    private Integer userNbTweets;

    @SetupRender
    public void setup()
    {
        if (securityContext.isLoggedIn())
        {
            user = securityContext.getUser();
            userNbTweets = tweetManager.getNbTweetsByUser(user.getLogin());
        }
    }

}
