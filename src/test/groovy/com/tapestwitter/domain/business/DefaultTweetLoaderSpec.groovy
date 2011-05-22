package com.tapestwitter.domain.business

import org.unitils.dbunit.annotation.DataSet
import org.unitils.spring.annotation.SpringApplicationContext
import org.unitils.spring.annotation.SpringBean

import spock.lang.Specification
import spock.lang.Unroll
import spock.unitils.UnitilsSupport

import com.tapestwitter.domain.model.Tweet
import com.tapestwitter.domain.model.User
import com.tapestwitter.services.security.SecurityContext


import org.unitils.dbunit.annotation.DataSet
import org.unitils.spring.annotation.SpringApplicationContext
import org.unitils.spring.annotation.SpringBean

import spock.lang.Specification
import spock.lang.Unroll
import spock.unitils.UnitilsSupport

import com.tapestwitter.domain.model.Tweet
import com.tapestwitter.domain.model.User
import com.tapestwitter.services.security.SecurityContext

@SpringApplicationContext("tapestwitterTest-config.xml")
@UnitilsSupport
@DataSet("TapestwitterBusinessTest.xml")
class DefaultTweetLoaderSpec extends Specification
{

    @SpringBean("tweetLoader")
    private TweetLoader tweetLoader

    // expected number of tweets into dataset
    def datasetTweetsNumber = 3



    def "list all the tweets"()
    {
        when: "search all the tweets"
        List<Tweet> result = tweetLoader.listAllTweet()

        then: "we check the expected number of tweets into dataset"
        result.size == datasetTweetsNumber
    }

    def "create a new tweet for an existing user"()
    {
        setup: "a new message to tweet"
        def msg = 'A new tweet'
        Tweet tweet;
        // Mock the user
        User user = Mock();

        when: "it is persisted into database"
        tweet = tweetLoader.createTweetFromUser(user, msg)

        then: "id field is well generated"
        tweet.id != null

        and: "we have now one more tweet"
        List<Tweet> result = tweetLoader.listAllTweet()
        result.size == datasetTweetsNumber + 1

        and: "check the message of the tweet is correct"
        tweet.tweet == msg

        and: "ckeck that user.getLogin() is called only one time"
        1 * user.getLogin()
    }


    @DataSet("TapestwitterTweetTest.xml")
    def "find recent tweets"()
    {
        given: "search recent tweets"
        List<Tweet> recents = tweetLoader.findRecentTweets()
        List<Tweet> allTweets = tweetLoader.listAllTweet()

        expect: "we check the expected number of tweets into dataset"
        recents.size == TweetLoader.DEFAULT_LIMIT_SIZE
        allTweets.size() > recents.size()
    }

    @DataSet("TapestwitterTweetTest.xml")
    @Unroll("got #nbTweets recents tweets when we start on index #start")
    def "find recent tweets with start index"()
    {
        expect: "we check the expected number of tweets into dataset"
        List<Tweet> recents = tweetLoader.findRecentTweets(start, TweetLoader.DEFAULT_LIMIT_SIZE)
        recents.size() == nbTweets

        where:
        start   |nbTweets
        5       |8
        0       |TweetLoader.DEFAULT_LIMIT_SIZE
        13      |0
        12      |1
    }

    def "find my tweets"()
    {
        // Stubs for dependents objects
        SecurityContext securityContext = Mock()
        User user = Mock()

        setup:
        tweetLoader.setSecurityContext(securityContext)
        user.login >> 'laurent'
        securityContext.getUser() >> user

        when: "search all my tweets"
        List<Tweet> myTweets = tweetLoader.findMyRecentTweets();

        then: "check number of elements returned"
        myTweets.size() == 2
    }
}
