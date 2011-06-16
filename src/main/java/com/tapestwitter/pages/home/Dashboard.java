/**
 * 
 */
package com.tapestwitter.pages.home;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.beaneditor.Width;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;

import com.tapestwitter.common.TapesTwitterEventConstants;
import com.tapestwitter.domain.business.TweetLoader;
import com.tapestwitter.domain.model.Tweet;
import com.tapestwitter.domain.model.User;
import com.tapestwitter.services.security.SecurityContext;

/**
 * Dashboard of authenticated user
 * 
 * @author lguerin
 */
public class Dashboard
{
    @Inject
    private Logger logger;

    /**
     * List of tweets
     */
    @Persist
    @Property
    private List<Tweet> tweets;

    /**
     * Current tweet into the loop
     */
    @SuppressWarnings("unused")
    @Property
    private Tweet currentTweet;

    /**
     * Textarea field for tweet message
     */
    @Property
    @Validate("required,maxlength=140")
    @Width(140)
    private String tweetContentInput;

    @Inject
    private TweetLoader tweetLoader;

    @SuppressWarnings("unused")
    @Inject
    private Block displayTweetBox;

    @InjectComponent
    private Zone tweetsZone;

    /**
     * A pointer to the last id af the tweets list
     */
    @Persist
    private Long lastTweetId;

    /**
     * A boolean that indicate if we could display the {@link AjaxMoreResults} link.
     */
    @SuppressWarnings("unused")
    @Property
    @Persist(PersistenceConstants.FLASH)
    private boolean displayAjaxMoreResultsLink;

    @Property(write = false)
    private User user;

    @Property(write = false)
    private Integer nbTweets;

    @Inject
    private SecurityContext securityContext;

    @SuppressWarnings("unused")
    @Property
    private Tweet current;

    @Inject
    private Request request;

    @OnEvent(value = EventConstants.ACTIVATE)
    public void loadTweets()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug(">>> Loading the list of tweets");
        }

        // Init
        if (tweets == null)
        {
            tweets = new ArrayList<Tweet>();
        }

        // Clear when adding new Tweet
        if (!request.isXHR())
        {
            tweets.clear();
        }

        // Get recents tweets
        List<Tweet> recents = tweetLoader.findMyRecentTweets();
        for (Tweet tweet : recents)
        {
            if (!tweets.contains(tweet))
            {
                tweets.add(tweet);
            }
        }

        // Set lastTweetId
        if (!request.isXHR())
        {
            lastTweetId = tweets.isEmpty() ? 0 : tweets.get(tweets.size() - 1).getId();
        }

        // Handle "More Result" link display status
        user = securityContext.getUser();
        nbTweets = tweetLoader.getNbTweetsByUser(user.getLogin());
        displayAjaxMoreResultsLink = tweets.size() < nbTweets ? true : false;

        if (logger.isDebugEnabled())
        {
            logger.debug("<<< " + tweets.size() + " tweet(s)...");
        }
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "publishTweetForm")
    void onPublishTweet()
    {
        logger.info(">>> Publish a new tweet...");
        Tweet t = tweetLoader.createTweet(tweetContentInput);
        lastTweetId = t.getId();
    }

    @Log
    @OnEvent(value = TapesTwitterEventConstants.MORE_TWEETS)
    Object provideMoreTweets()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug(">>> Last ID: " + lastTweetId);
        }
        if (lastTweetId != null)
        {
            List<Tweet> recents = tweetLoader.findMyRecentTweets(lastTweetId, TweetLoader.DEFAULT_LIMIT_SIZE);
            for (Tweet tweet : recents)
            {
                if (!tweets.contains(tweet))
                {
                    tweets.add(tweet);
                    lastTweetId = tweet.getId();
                }
            }
        }

        displayAjaxMoreResultsLink = tweets.size() < nbTweets ? true : false;
        return tweetsZone.getBody();
    }

    @OnEvent(value = TapesTwitterEventConstants.DELETE_TWEET)
    void deleteTweet(String tweetId)
    {
        logger.info(">>> Delete tweet identified by: " + tweetId);
        tweetLoader.deleteTweet(Long.valueOf(tweetId));
    }

    /**
     * Encoder for {@link Loop} component
     * 
     * @return encoder of {@link Tweet}
     */
    @SuppressWarnings("rawtypes")
    public ValueEncoder getEncoder()
    {
        return new ValueEncoder<Tweet>()
        {
            public String toClient(Tweet value)
            {
                Long id = value.getId();
                return id.toString();
            }

            public Tweet toValue(String clientId)
            {
                Long id = new Long(clientId);
                return tweetLoader.findTweetById(id);
            }
        };
    }

}
