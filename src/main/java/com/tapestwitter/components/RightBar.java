package com.tapestwitter.components;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.tapestwitter.domain.business.TweetLoader;
import com.tapestwitter.domain.model.User;
import com.tapestwitter.services.security.SecurityContext;

/**
 * The rightbar menu component
 * 
 * @author lguerin
 */
public class RightBar
{
    @Inject
    private SecurityContext securityContext;

    @Property(write = false)
    private User user;

    @Inject
    private TweetLoader tweetLoader;

    @SuppressWarnings("unused")
    @Property(write = false)
    private Integer userNbTweets;

    @SetupRender
    public void setup()
    {
        if (securityContext.isLoggedIn())
        {
            user = securityContext.getUser();
            userNbTweets = tweetLoader.getNbTweetsByUser(user.getLogin());
        }
    }

}
