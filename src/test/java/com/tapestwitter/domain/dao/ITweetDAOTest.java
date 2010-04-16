package com.tapestwitter.domain.dao;

import java.util.Calendar;
import java.util.List;

import com.tapestwitter.domain.model.Tweet;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.unitils.UnitilsTestNG;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

import org.apache.commons.lang.StringUtils;

/**
 * Classe de test du DAO {@link ITweetDAO} 
 * 	
 * @author lguerin
 * 
 */
@SpringApplicationContext("test-config.xml")
public class ITweetDAOTest extends UnitilsTestNG
{

	@SpringBean("tweetDAO")
	private ITweetDAO tweetDAO;

	/**
	 * Nombre de tweet par defaut dans le jeu de donnees
	 */
	private final static int DEFAULT_NB_TWEETS_DATASET = 3;

	/**
	 * Identifiant du premier tweet dans le jeu de donnees
	 */
	private final static Long DEFAULT_FIRST_TWEET_DATASET = new Long(1001);

	@DataSet
	@Test
	public void testFindTweetById()
	{
		Tweet tweet = tweetDAO.findById(DEFAULT_FIRST_TWEET_DATASET);
		Assert.assertEquals(DEFAULT_FIRST_TWEET_DATASET, tweet.getId());
	}

	@DataSet
	@Test
	public void testFindAllTweet()
	{
		List<Tweet> tweets = tweetDAO.listAll();
		Assert.assertEquals(DEFAULT_NB_TWEETS_DATASET, tweets.size());
	}

	@DataSet
	@Test
	public void testCreateTweet()
	{
		Tweet tweet = new Tweet();
		tweet.setTweet("Mon tweet en creation");
		tweet.setAuthor("lguerin");
		Calendar calendar = Calendar.getInstance();
		tweet.setCreationDate(calendar.getTime());
		tweetDAO.create(tweet);
		final List<Tweet> tweets = tweetDAO.listAll();
		Assert.assertEquals(DEFAULT_NB_TWEETS_DATASET + 1, tweets.size());
	}

	@DataSet
	@Test
	public void testFindTweetByKeyword()
	{
		String keyword = "tapestweet";
		final List<Tweet> tweets = tweetDAO.findTweetByKeyword(keyword);
		Assert.assertEquals(DEFAULT_NB_TWEETS_DATASET, tweets.size());
	}

	@DataSet
	@Test
	public void testFindTweetByKeywordWithNoResult()
	{
		String keyword = "foobar";
		final List<Tweet> tweets = tweetDAO.findTweetByKeyword(keyword);
		Assert.assertEquals(0, tweets.size());
	}

	@DataSet
	@Test
	public void testFindTweetByKeywordWithEmpty()
	{
		String keyword = StringUtils.EMPTY;
		final List<Tweet> tweets = tweetDAO.findTweetByKeyword(keyword);
		Assert.assertEquals(DEFAULT_NB_TWEETS_DATASET, tweets.size());
	}

	@DataSet
	@Test
	public void testRemoveTweet()
	{
		Tweet tweet = tweetDAO.findById(DEFAULT_FIRST_TWEET_DATASET);
		tweetDAO.delete(tweet);
		final List<Tweet> tweets = tweetDAO.listAll();
		Assert.assertEquals(DEFAULT_NB_TWEETS_DATASET - 1, tweets.size());
	}

	@DataSet
	@Test
	public void testFindRecentTweets()
	{
		Integer rangeSize = 2;
		List<Tweet> tweets = tweetDAO.findRecentTweets(DEFAULT_FIRST_TWEET_DATASET + 2, rangeSize);
		Assert.assertEquals(tweets.size(), DEFAULT_NB_TWEETS_DATASET - 1);

		tweets = tweetDAO.findRecentTweets(DEFAULT_FIRST_TWEET_DATASET + 1, rangeSize);
		Assert.assertEquals(tweets.size(), DEFAULT_NB_TWEETS_DATASET - 2);
	}

	@DataSet
	@Test
	public void testGetNbTweetsByUser()
	{
		String author = "lguerin";
		Integer expected = tweetDAO.getNbTweetsByUser(author);
		Assert.assertEquals(new Integer(2), expected);
	}
}
