/**
 * 
 */
package com.tapestwitter.domain.business;

import java.util.List;

import com.tapestwitter.domain.model.Tweet;
import com.tapestwitter.domain.model.User;
import com.tapestwitter.services.security.SecurityContext;

/**
 * @author lguerin
 */
public interface TweetLoader
{
    /**
     * The default size limit of the range of tweets to load
     */
    public static final Integer DEFAULT_LIMIT_SIZE = 10;

    /**
     * Recupere un {@link Tweet} a partir de son identifiant
     * 
     * @param tweetId
     *            Identifiant du tweet a recuperer
     * @return {@link Tweet}
     */
    Tweet findTweetById(Long tweetId);

    /**
     * Creation d'un tweet
     * 
     * @param msg
     *            Message du tweet
     * @return Objet {@link Tweet} créé.
     */
    Tweet createTweet(String msg);

    /**
     * Met a jour le {@link Tweet}
     * 
     * @param tweet
     *            Tweet a mettre a jour
     */
    void updateTweet(Tweet tweet);

    /**
     * Suppression d'un tweet
     * 
     * @param tweetId
     *            Identifiant du tweet a supprimer
     */
    void deleteTweet(Long tweetId);

    /**
     * Recuperer l'ensemble des tweet par mot cle.
     * 
     * @param keyword
     *            Mot cle a rechercher
     * @return
     */
    List<Tweet> findTweetByKeyword(String keyword);

    /**
     * Liste l'ensemble des {@link Tweet}.
     * 
     * @return Liste de {@link Tweet}
     */
    List<Tweet> listAllTweet();

    /**
     * Find a range of recent tweets
     * 
     * @param start
     *            The start id off the research
     * @param range
     *            The number of tweets to get
     * @return List of tweets
     */
    List<Tweet> findRecentTweets(Integer start, Integer range);

    /**
     * Find a range of recent tweets
     * 
     * @return List of tweets
     */
    List<Tweet> findRecentTweets();

    /**
     * Get the count of tweets for a given user identified by his login.
     * 
     * @param login
     *            The login of the {@link User}
     * @return The number of tweets created by a {@link User}
     */
    Integer getNbTweetsByUser(String login);

    /**
     * Set the security context
     * 
     * @param securityContext
     *            the security context to set
     */
    void setSecurityContext(SecurityContext securityContext);

    Tweet createTweetFromUser(User user, String msg);
}
