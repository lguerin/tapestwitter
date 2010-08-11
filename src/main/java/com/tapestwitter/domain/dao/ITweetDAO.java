package com.tapestwitter.domain.dao;

import java.util.List;

import com.tapestwitter.domain.model.Tweet;
import com.tapestwitter.domain.model.User;

/**
 * DAO responsable de la manipulation des {@link Tweet}.
 * 
 * @author lguerin
 */
public interface ITweetDAO extends IGenericDAO<Tweet, Long>
{
    /**
     * Recuperer l'ensemble des tweet par mot cle.
     * 
     * @param keyword Mot cle a rechercher
     * @return
     */
    List<Tweet> findTweetByKeyword(String keyword);

    /**
     * Recuperer l'ensemble des tweets tries par date de creation, du plus
     * recent au plus ancien.
     * 
     * @return Liste de tweets tries du plus recent au plus ancien
     */
    List<Tweet> listAllByCreationDateDesc();

    /**
     * Get a range of recent tweets
     * 
     * @param id The id of tweet
     * @param range The number of tweets to get
     * @return List of tweets
     */
    List<Tweet> findRecentTweets(Long id, Integer range);

    /**
     * Get the count of tweets for a given user identified by his login.
     * 
     * @param login The login of the {@link User}
     * @return The number of tweets created by a {@link User}
     */
    Integer getNbTweetsByUser(String login);
}
