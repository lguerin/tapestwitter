package com.tapestwitter.domain.business.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.tapestwitter.domain.business.TweetManager;
import com.tapestwitter.domain.dao.ITweetDAO;
import com.tapestwitter.domain.model.Tweet;
import com.tapestwitter.domain.model.User;
import com.tapestwitter.services.security.TapestwitterSecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Implementation de la classe de service permettant de gerer
 * les {@link Tweet}.
 * 
 * @author lguerin
 */
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
@Component("tweetManager")
public class TweetManagerImpl implements TweetManager
{

    @Autowired
    private ITweetDAO tweetDAO;

    @Autowired
    private TapestwitterSecurityContext securityContext;

    /*
     * (non-Javadoc)
     * @see com.tapestwitter.domain.business.TweetManager#findTweetByKeyword(java.lang.String)
     */
    public List<Tweet> findTweetByKeyword(String keyword)
    {
        return tweetDAO.findTweetByKeyword(keyword);
    }

    @Transactional(readOnly = false)
    public Tweet createTweet(String msg)
    {
        Assert.notNull(msg, "message");
        Tweet tweet = new Tweet();

        // Remplissage des proprietes
        tweet.setTweet(msg);
        Date creationDate = Calendar.getInstance().getTime();
        tweet.setCreationDate(creationDate);
        User user = securityContext.getUser();
        String author = user.getLogin();
        tweet.setAuthor(author);

        // Sauvegarde du tweet
        tweetDAO.create(tweet);

        return tweet;
    }

    @Transactional(readOnly = false)
    public void deleteTweet(Long tweetId)
    {
        Assert.notNull(tweetId, "tweetId");

        // Recuperation du tweet a supprimer
        Tweet t = tweetDAO.findById(tweetId);

        if (t != null)
        {
            tweetDAO.delete(t);
        }
    }

    public Tweet findTweetById(Long tweetId)
    {
        return tweetDAO.findById(tweetId);
    }

    public List<Tweet> listAllTweet()
    {
        return tweetDAO.listAllByCreationDateDesc();
    }

    public void updateTweet(Tweet tweet)
    {
        Assert.notNull(tweet, "tweet");
        tweetDAO.update(tweet);
    }

    public List<Tweet> findRecentTweets(Long id, Integer range)
    {
        Assert.notNull(range, "range");
        return tweetDAO.findRecentTweets(id, range);
    }

    public List<Tweet> findRecentTweets(Integer range)
    {
        return findRecentTweets(null, range);
    }

    public Integer getNbTweetsByUser(String login)
    {
        return tweetDAO.getNbTweetsByUser(login);
    }

    public void setSecurityContext(TapestwitterSecurityContext securityContext)
    {
        this.securityContext = securityContext;
    }
}
