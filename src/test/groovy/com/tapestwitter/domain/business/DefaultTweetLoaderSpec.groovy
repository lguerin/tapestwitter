package com.tapestwitter.domain.business

import com.tapestwitter.domain.model.Authority;
import com.tapestwitter.domain.model.Tweet
import com.tapestwitter.domain.model.User

import org.unitils.spring.annotation.SpringApplicationContext
import org.unitils.spring.annotation.SpringBean
import org.unitils.dbunit.annotation.DataSet
import spock.lang.Specification
import spock.unitils.UnitilsSupport
import spock.lang.Unroll


@SpringApplicationContext("tapestwitterTest-config.xml")
@UnitilsSupport
@DataSet("TapestwitterBusinessTest.xml")
class DefaultTweetLoaderSpec extends Specification 
{

  @SpringBean("tweetLoader")
  private TweetLoader tweetLoader

  @SpringBean("authenticator")
  private Authenticator authenticator
  
  def datasetTweetsNumber = 1
  
  def "list of all the tweets"()
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
      def author = 'laurent'
      def user = authenticator.findByUsername(author)
      
      when: "it is persisted into database"
      Tweet tweet = tweetLoader.createTweetFromUser(user, msg)
      
      then: "id field is well generated"
      tweet.id != null
      
      and: "we have now one more tweet"
      List<Tweet> result = tweetLoader.listAllTweet()
      result.size == datasetTweetsNumber + 1
      
      and: "we check other fields of the new created tweet"
      tweet.tweet == msg
      tweet.author == author
  }
  

}
