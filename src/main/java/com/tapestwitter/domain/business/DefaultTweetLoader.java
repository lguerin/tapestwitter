package com.tapestwitter.domain.business;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tapestwitter.domain.dao.CrudServiceDAO;
import com.tapestwitter.domain.dao.QueryParameters;
import com.tapestwitter.domain.model.Tweet;
import com.tapestwitter.domain.model.User;
import com.tapestwitter.services.security.SecurityContext;

/**
 * This class is the Default implementation of the TweetLoader Service {@link TweetLoader}.
 * 
 * @author karesti
 */
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
@Component("tweetLoader")
public class DefaultTweetLoader implements TweetLoader
{

    @Autowired
    private CrudServiceDAO crudServiceDAO;

    @Autowired
    private SecurityContext securityContext;

    public List<Tweet> findTweetByKeyword(String keyword)
    {
        return crudServiceDAO.findWithNamedQuery(Tweet.FIND_BY_KEYWORD, QueryParameters.with("keyword", keyword).parameters());
    }

    @Transactional(readOnly = false)
    public Tweet createTweet(String msg)
    {
        Assert.notNull(msg, "message");
        Tweet tweet = new Tweet();

        tweet.setTweet(msg);
        Date creationDate = Calendar.getInstance().getTime();
        tweet.setCreationDate(creationDate);
        User user = securityContext.getUser();
        String author = user.getLogin();
        tweet.setAuthor(author);

        crudServiceDAO.create(tweet);

        return tweet;
    }

    @Transactional(readOnly = false)
    public void deleteTweet(Long tweetId)
    {
        crudServiceDAO.delete(Tweet.class, tweetId);
    }

    @Transactional(readOnly = false)
    public Tweet createTweetFromUser(User user, String msg)
    {
        Assert.notNull(user, "user");
        Assert.notNull(msg, "message");
        Tweet tweet = new Tweet();

        tweet.setTweet(msg);
        Date creationDate = Calendar.getInstance().getTime();
        tweet.setCreationDate(creationDate);
        String author = user.getLogin();
        tweet.setAuthor(author);

        crudServiceDAO.create(tweet);

        return tweet;
    }

    public Tweet findTweetById(Long tweetId)
    {
        return crudServiceDAO.find(Tweet.class, tweetId);
    }

    public List<Tweet> listAllTweet()
    {
        return crudServiceDAO.findWithNamedQuery(Tweet.ALL_ORDER_BY_DATE_DESC);
    }

    public void updateTweet(Tweet tweet)
    {
        crudServiceDAO.update(tweet);
    }

    public List<Tweet> findRecentTweets(Long id, Integer range)
    {
        return crudServiceDAO.findMaxResultsWithNamedQuery(Tweet.FIND_ALL_RECENT_WITH_ID, QueryParameters.with("id", id).parameters(), range);
    }

    public List<Tweet> findRecentTweets(Integer range)
    {
        return findRecentTweets(null, range);
    }

    public Integer getNbTweetsByUser(String login)
    {
        return (Integer) crudServiceDAO.findUniqueWithNamedQuery(Tweet.COUNT_TWEETS_FOR_USER, QueryParameters.with("name", login).parameters());
    }

    public void setSecurityContext(SecurityContext securityContext)
    {
        this.securityContext = securityContext;
    }

}
