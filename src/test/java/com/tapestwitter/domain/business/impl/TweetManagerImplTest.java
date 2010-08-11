/**
 * 
 */
package com.tapestwitter.domain.business.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.easymock.EasyMock;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.unitils.UnitilsTestNG;
import org.unitils.easymock.EasyMockUnitils;
import org.unitils.easymock.annotation.Mock;
import org.unitils.easymock.util.Defaults;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import com.tapestwitter.domain.business.impl.TweetManagerImpl;
import com.tapestwitter.domain.dao.ITweetDAO;
import com.tapestwitter.domain.model.Tweet;
import com.tapestwitter.domain.model.User;
import com.tapestwitter.services.security.TapestwitterSecurityContext;

/**
 * Classe de test du service {@link TweetManagerImpl}.
 * 
 * @author lguerin
 * @author ldoin
 */
public class TweetManagerImplTest extends UnitilsTestNG
{
    @Mock(defaults = Defaults.STRICT)
    @InjectIntoByType
    private ITweetDAO mockTweetDao;

    @Mock
    @InjectIntoByType
    private TapestwitterSecurityContext mockSecurityContext;

    @TestedObject
    private TweetManagerImpl tweetManager;

    /**
     * Auteur du tweet par defaut
     */
    private final static String DEFAULT_AUTHOR = "lguerin";

    /**
     * Message du tweet par defaut
     */
    private final static String DEFAULT_MESSAGE = "Mon tweet a creer";

    /**
     * Mot cle utilise pour les tests
     */
    private final static String DEFAULT_KEYWORD = "keyword";

    /**
     * identifiant du tweet par defaut
     */
    private final static long DEFAULT_ID = 123456789L;

    /**
     * tweet utilise pour les tests
     */
    private Tweet tweet;

    /**
     * autre tweet utilise pour les tests
     */
    private Tweet tweet2;

    /**
     * liste de tweets utilisee pour les tests
     */
    private List<Tweet> tweets;

    @BeforeMethod
    public void setUp()
    {
        tweet = new Tweet(DEFAULT_MESSAGE, DEFAULT_AUTHOR);
        tweet.setCreationDate(Calendar.getInstance().getTime());
        tweet2 = new Tweet(DEFAULT_MESSAGE, DEFAULT_AUTHOR);
        tweet2.setCreationDate(Calendar.getInstance().getTime());
        tweets = Arrays.asList(tweet, tweet2);
    }

    @Test
    public void testListAllTweet()
    {
        EasyMock.expect(mockTweetDao.listAllByCreationDateDesc()).andReturn(tweets).once();
        EasyMockUnitils.replay();

        List<Tweet> actualTweets = tweetManager.listAllTweet();
        Assert.assertEquals(actualTweets, tweets);
    }

    @Test
    public void testCreateTweet()
    {
        // User returned by the mock
        User testUser = new User();
        testUser.setLogin(DEFAULT_AUTHOR);

        EasyMock.expect(mockSecurityContext.getUser()).andReturn(testUser).once();
        mockTweetDao.create(tweet);
        EasyMockUnitils.replay();

        Tweet actualTweet = tweetManager.createTweet(DEFAULT_MESSAGE);
        ReflectionAssert.assertReflectionEquals(tweet, actualTweet, ReflectionComparatorMode.LENIENT_DATES);
    }

    @Test
    public void testFindTweetById()
    {
        EasyMock.expect(mockTweetDao.findById(DEFAULT_ID)).andReturn(tweet).once();
        EasyMockUnitils.replay();

        Tweet result = tweetManager.findTweetById(DEFAULT_ID);
        Assert.assertEquals(result, tweet);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFindTweetByKeywordWithNoResult()
    {
        EasyMock.expect(mockTweetDao.findTweetByKeyword(DEFAULT_KEYWORD)).andReturn(ListUtils.EMPTY_LIST).once();
        EasyMockUnitils.replay();

        List<Tweet> tweets = tweetManager.findTweetByKeyword(DEFAULT_KEYWORD);
        Assert.assertEquals(0, tweets.size());
    }

    @Test
    public void testDeleteTweet()
    {
        EasyMock.expect(mockTweetDao.findById(DEFAULT_ID)).andReturn(tweet).once();
        mockTweetDao.delete(tweet);
        EasyMockUnitils.replay();

        tweetManager.deleteTweet(DEFAULT_ID);
    }

    @Test
    public void testDeleteNonExistentTweet()
    {
        // si aucun tweet ne correspond a l'identifiant
        // la methode delete de ITweetDAO ne doit pas etre appelee
        EasyMock.expect(mockTweetDao.findById(DEFAULT_ID)).andReturn(null).once();
        EasyMockUnitils.replay();

        tweetManager.deleteTweet(DEFAULT_ID);
    }

    @Test
    public void testUpdateTweet()
    {
        mockTweetDao.update(tweet);
        EasyMockUnitils.replay();

        tweetManager.updateTweet(tweet);
    }

    @Test
    public void testFindRecentTweets()
    {
        Integer rangeSize = 2;
        EasyMock.expect(mockTweetDao.findRecentTweets(DEFAULT_ID, rangeSize)).andReturn(tweets).once();
        EasyMockUnitils.replay();

        List<Tweet> actualTweets = tweetManager.findRecentTweets(DEFAULT_ID, rangeSize);
        Assert.assertEquals(actualTweets, tweets);
    }

    @Test
    public void testFindRecentTweetsWithNullId()
    {
        Integer rangeSize = 3;
        EasyMock.expect(mockTweetDao.findRecentTweets(null, rangeSize)).andReturn(tweets).once();
        EasyMockUnitils.replay();

        List<Tweet> actualTweets = tweetManager.findRecentTweets(rangeSize);
        Assert.assertEquals(actualTweets, tweets);
    }
}
