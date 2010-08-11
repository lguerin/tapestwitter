package com.tapestwitter.pages;

import java.util.ArrayList;
import java.util.List;

import com.tapestwitter.components.AjaxMoreResults;
import com.tapestwitter.domain.business.TweetManager;
import com.tapestwitter.domain.model.Tweet;

import org.slf4j.Logger;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.beaneditor.Width;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Home Page for the authenticated user.
 */
public class HomePage
{
    /**
     * Class logger
     */
    @Inject
    private Logger logger;

    /**
     * Liste de tweets charges dans la loop
     */
    private List<Tweet> tweets;

    /**
     * Element courant de la boucle loop sur la liste de tweets.
     */
    @SuppressWarnings("unused")
    @Property
    private Tweet currentTweet;

    /**
     * Contenu du champ textarea dans le formulaire de saisie
     * du message.
     */
    @Property
    @Validate("required,maxlength=140")
    @Width(140)
    private String tweetContentInput;

    /**
     * Manager of {@link Tweet}
     */
    @Inject
    private TweetManager tweetManager;

    @SuppressWarnings("unused")
    @Inject
    private Block displayTweetBox;

    @Persist
    private List<Tweet> ajaxResult;

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
    private boolean displayAjaxMoreResultsLink = false;

    /**
     * The default number of {@link Tweet} to display
     */
    private static final Integer DEFAULT_NUMBER_TWEETS = 5;

    @SuppressWarnings("unused")
    @Property
    private Tweet current;

    @SetupRender
    public void loadTweets()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug(">>> Loading the list of tweets");
        }
        tweets = tweetManager.findRecentTweets(DEFAULT_NUMBER_TWEETS);
        if (ajaxResult == null)
        {
            if (tweets.size() > 0)
            {
                lastTweetId = tweets.get(tweets.size() - 1).getId();
            }
        }

        if (tweets.size() == DEFAULT_NUMBER_TWEETS)
        {
            displayAjaxMoreResultsLink = true;
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("<<< " + tweets.size() + " tweet(s)...");
        }
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "publishTweetForm")
    public void onPublishTweet()
    {
        logger.info(">>> Publish a new tweet...");
        tweetManager.createTweet(tweetContentInput);
    }

    @SuppressWarnings("unused")
    @Property
    private Block ajaxResponse;

    @Log
    public List<Tweet> onProvideMoreResultsFromTest(int range)
    {
        if (ajaxResult == null)
        {
            ajaxResult = new ArrayList<Tweet>();
        }

        if (logger.isDebugEnabled())
        {
            logger.debug(">>> Last ID: " + lastTweetId);
        }

        if (lastTweetId != null)
        {
            List<Tweet> recents = tweetManager.findRecentTweets(lastTweetId, range);
            for (Tweet tweet : recents)
            {
                if (!ajaxResult.contains(tweet))
                {
                    ajaxResult.add(tweet);
                }
            }
        }

        if (ajaxResult.size() > 0)
        {
            // Update the value of lastTweetId
            lastTweetId = ajaxResult.get(ajaxResult.size() - 1).getId();
        }
        return ajaxResult;
    }

    @OnEvent(EventConstants.PASSIVATE)
    public void clean()
    {
        syncTweets();
    }

    /**
     * Synchronise items who are contains into the tweets list.
     */
    private void syncTweets()
    {
        if (ajaxResult != null && tweets != null)
        {
            logger.debug(">>>>> Number of tweets: " + tweets.size());
            tweets.addAll(ajaxResult);
            lastTweetId = tweets.get(tweets.size() - 1).getId();
            ajaxResult.clear();
        }
        this.setTweets(tweets);
    }

    /**
     * @return the result
     */
    public List<Tweet> getAjaxResult()
    {
        return ajaxResult;
    }

    /**
     * @param result the result to set
     */
    public void setAjaxResult(List<Tweet> result)
    {
        this.ajaxResult = result;
    }

    /**
     * @return the tweets
     */
    public List<Tweet> getTweets()
    {
        return tweets;
    }

    /**
     * @param tweets the tweets to set
     */
    public void setTweets(List<Tweet> tweets)
    {
        this.tweets = tweets;
    }
}
