/**
 * 
 */
package com.tapestwitter.domain.business;

import java.util.List;

import com.tapestwitter.domain.model.Tweet;
import com.tapestwitter.domain.model.User;
import com.tapestwitter.services.security.TapestwitterSecurityContext;

/**
 * @author lguerin
 */
public interface TweetManager
{
    /**
     * Recupere un {@link Tweet} a partir de son identifiant
     * 
     * @param tweetId Identifiant du tweet a recuperer
     * @return {@link Tweet}
     */
    Tweet findTweetById(Long tweetId);

    /**
     * Création d'un tweet
     * 
     * @param msg Message du tweet
     * @return Objet {@link Tweet} créé.
     */
    Tweet createTweet(String msg);

    /**
     * Met a jour le {@link Tweet}
     * 
     * @param tweet Tweet a mettre a jour
     */
    void updateTweet(Tweet tweet);

    /**
     * Suppression d'un tweet
     * 
     * @param tweetId Identifiant du tweet a supprimer
     */
    void deleteTweet(Long tweetId);

    /**
     * Recuperer l'ensemble des tweet par mot cle.
     * 
     * @param keyword Mot cle a rechercher
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
     * @param id The start id of tweet for the research
     * @param range The number of tweets to get
     * @return List of tweets
     */
    List<Tweet> findRecentTweets(Long id, Integer range);

    /**
     * Find a range of recent tweets
     * 
     * @param range The number of tweets to get
     * @return List of tweets
     */
    List<Tweet> findRecentTweets(Integer range);

    /**
     * Get the count of tweets for a given user identified by his login.
     * 
     * @param login The login of the {@link User}
     * @return The number of tweets created by a {@link User}
     */
    Integer getNbTweetsByUser(String login);

    /**
     * Set the security context
     * 
     * @param securityContext the security context to set
     */
    void setSecurityContext(TapestwitterSecurityContext securityContext);
}
