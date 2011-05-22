/**
 * 
 */
package com.tapestwitter.pages.home;

import java.util.List;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.beaneditor.Width;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.tapestwitter.components.AjaxMoreResults;
import com.tapestwitter.domain.business.TweetLoader;
import com.tapestwitter.domain.model.Tweet;

/**
 * Dashboard of authenticated user
 * 
 * @author lGuerin
 */
public class Dashboard
{
    /**
     * Class logger
     */
    @Inject
    private Logger logger;

    /**
     * List of tweets
     */
    @Persist(PersistenceConstants.FLASH)
    @Property
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
    private boolean displayAjaxMoreResultsLink = false;

    /**
     * The default number of {@link Tweet} to display
     */
    private static final Integer DEFAULT_NUMBER_TWEETS = 5;

    @SuppressWarnings("unused")
    @Property
    private Tweet current;

    @OnEvent(value = EventConstants.PREPARE)
    public void loadTweets()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug(">>> Loading the list of tweets");
        }
        tweets = tweetLoader.findMyRecentTweets();

        if (tweets.size() > 0)
        {
            lastTweetId = tweets.get(tweets.size() - 1).getId();
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
    void onPublishTweet()
    {
        logger.info(">>> Publish a new tweet...");
        Tweet newTweet = tweetLoader.createTweet(tweetContentInput);
        tweets.add(newTweet);
    }

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
                for (Tweet t : tweets)
                {
                    if (t.getId().equals(id)) { return t; }
                }
                throw new IllegalArgumentException("Unknow id \"" + id + "\" into : " + tweets);
            }
        };
    }

}
