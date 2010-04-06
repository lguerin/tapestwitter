/**
 * 
 */
package com.tapestwitter.domain.business;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.unitils.UnitilsTestNG;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

import com.tapestwitter.domain.business.TweetManager;
import com.tapestwitter.domain.model.Tweet;

/**
 * Classe de test du service {@link TweetManager}. 
 * @author lguerin
 *
 */
@SpringApplicationContext("test-config.xml")
public class TweetManagerTest extends UnitilsTestNG
{
	@SpringBean("tweetManager")
	private TweetManager tweetManager;

	/**
	 * Auteur du tweet par defaut
	 */
	private final static String DEFAULT_AUTHOR = "lguerin";

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
	public void testListAllTweet()
	{
		List<Tweet> tweets = tweetManager.listAllTweet();
		Assert.assertEquals(DEFAULT_NB_TWEETS_DATASET, tweets.size());
	}

	@DataSet
	@Test
	public void testCreateTweet()
	{
		String msg = "Mon tweet a creer";
		Tweet result = tweetManager.createTweet(msg);
		// Test du message
		Assert.assertEquals(result.getTweet(), msg);
		// Test de l'auteur
		Assert.assertEquals(DEFAULT_AUTHOR, result.getAuthor());
		// Test du nombre de tweet
		List<Tweet> tweets = tweetManager.listAllTweet();
		Assert.assertEquals(DEFAULT_NB_TWEETS_DATASET + 1, tweets.size());
	}

	@DataSet
	@Test
	public void testFindTweetById()
	{
		Tweet result = tweetManager.findTweetById(DEFAULT_FIRST_TWEET_DATASET);
		// Test sur l'auteur
		Assert.assertEquals(DEFAULT_AUTHOR, result.getAuthor());
		// Test sur l'id
		Assert.assertEquals(DEFAULT_FIRST_TWEET_DATASET, result.getId());
	}

	@DataSet
	@Test
	public void testFindTweetByKeywordWithNoResult()
	{
		String keyword = "foobar";
		List<Tweet> tweets = tweetManager.findTweetByKeyword(keyword);
		Assert.assertEquals(0, tweets.size());
	}

	@DataSet
	@Test
	public void testDeleteTweet()
	{
		tweetManager.deleteTweet(DEFAULT_FIRST_TWEET_DATASET);
		List<Tweet> tweets = tweetManager.listAllTweet();
		Assert.assertEquals(DEFAULT_NB_TWEETS_DATASET - 1, tweets.size());
	}

	@DataSet
	@Test
	public void testUpdateTweet()
	{
		Tweet actual = tweetManager.findTweetById(DEFAULT_FIRST_TWEET_DATASET);
		String newAuthor = "foobar";
		actual.setAuthor(newAuthor);
		tweetManager.updateTweet(actual);
		actual = tweetManager.findTweetById(DEFAULT_FIRST_TWEET_DATASET);
		Assert.assertEquals(newAuthor, actual.getAuthor());
	}

	@DataSet
	@Test
	public void testFindRecentTweets()
	{
		Integer rangeSize = 2;
		List<Tweet> tweets = tweetManager.findRecentTweets(DEFAULT_FIRST_TWEET_DATASET + 2, rangeSize);
		Assert.assertEquals(tweets.size(), DEFAULT_NB_TWEETS_DATASET - 1);
	}

	@DataSet
	@Test
	public void testFindRecentTweetsWithNullId()
	{
		Integer rangeSize = 3;
		List<Tweet> tweets = tweetManager.findRecentTweets(rangeSize);
		Assert.assertEquals(tweets.size(), DEFAULT_NB_TWEETS_DATASET);
	}
}
