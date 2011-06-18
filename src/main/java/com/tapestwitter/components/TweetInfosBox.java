package com.tapestwitter.components;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.tapestwitter.common.TapesTwitterEventConstants;
import com.tapestwitter.domain.business.TweetLoader;
import com.tapestwitter.domain.model.Tweet;

/**
 * Display the main informations about a {@link Tweet}.
 * 
 * @author lguerin
 */
public class TweetInfosBox
{
    /**
     * Tweet to display
     */
    @SuppressWarnings("unused")
    @Property
    @Parameter(allowNull = false, required = true)
    private Tweet tweet;

    @Inject
    private ComponentResources resources;

    Object onActionFromDeleteFromTweetInfoBox(String tweetId)
    {
        Link link = resources.createEventLink(TapesTwitterEventConstants.DELETE_TWEET, tweetId);
        return link;
    }

    @Inject
    private TweetLoader tweetLoader;

    public String getTweetOwnerEmail()
    {
        return tweetLoader.getEmailOfTweetOwner(tweet.getId());
    }
}
