package com.tapestwitter.domain.business;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tapestwitter.domain.dao.CrudDAO;
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
    private CrudDAO crudDAO;

    @Autowired
    private SecurityContext securityContext;

    public List<Tweet> findTweetByKeyword(String keyword)
    {
        return crudDAO.findWithNamedQuery(Tweet.FIND_BY_KEYWORD, QueryParameters.with("keyword", keyword).parameters());
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

        crudDAO.create(tweet);

        return tweet;
    }

    @Transactional(readOnly = false)
    public void deleteTweet(Long tweetId)
    {
        crudDAO.delete(Tweet.class, tweetId);
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

        crudDAO.create(tweet);

        return tweet;
    }

    public Tweet findTweetById(Long tweetId)
    {
        return crudDAO.find(Tweet.class, tweetId);
    }

    public List<Tweet> listAllTweet()
    {
        return crudDAO.findWithNamedQuery(Tweet.ALL_ORDER_BY_DATE_DESC);
    }

    public void updateTweet(Tweet tweet)
    {
        crudDAO.update(tweet);
    }

    public List<Tweet> findRecentTweets(Integer range)
    {
        return crudDAO.findMaxResultsWithNamedQuery(Tweet.FIND_ALL_RECENT, range);
    }

    public List<Tweet> findRecentTweets()
    {
        return findRecentTweets(TweetLoader.DEFAULT_LIMIT_SIZE);
    }

    public Integer getNbTweetsByUser(String login)
    {
        Long res = (Long) crudDAO.findUniqueWithNamedQuery(Tweet.COUNT_TWEETS_FOR_USER, QueryParameters.with("name", login).parameters());
        return res.intValue();
    }

    public void setSecurityContext(SecurityContext securityContext)
    {
        this.securityContext = securityContext;
    }

    public List<Tweet> findMyRecentTweets(Long start, Integer range)
    {
        User user = securityContext.getUser();
        List<Tweet> result;
        if (start != null)
        {
            result = crudDAO.findMaxResultsWithNamedQuery(
                    Tweet.FIND_ALL_RECENT_BY_AUTHOR_WITH_LIMIT,
                    QueryParameters.with("author", user.getLogin()).and("id", start).parameters(),
                    range);
        }
        else
        {
            result = crudDAO.findMaxResultsWithNamedQuery(Tweet.FIND_ALL_RECENT_BY_AUTHOR, QueryParameters.with("author", user.getLogin()).parameters(), range);
        }
        return result;
    }

    public List<Tweet> findMyRecentTweets()
    {
        return this.findMyRecentTweets(null, TweetLoader.DEFAULT_LIMIT_SIZE);
    }

    public String getEmailOfTweetOwner(Long tweetId)
    {
        return (String) crudDAO.findUniqueWithNamedQuery(Tweet.EMAIL_ADDRESS_OF_TWEET_OWNER, QueryParameters.with("tweetId", Long.valueOf(tweetId))
                .parameters());
    }
}
