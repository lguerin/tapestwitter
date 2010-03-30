package com.tapestwitter.domain.dao;

import java.util.List;

import com.tapestwitter.domain.model.Tweet;

/**
 * DAO responsable de la manipulation des {@link Tweet}.
 * 
 * @author lguerin
 *
 */
public interface ITweetDAO extends IGenericDAO<Tweet, Long>
{
	/**
	 * Recuperer l'ensemble des tweet par mot cle.
	 * @param keyword	Mot cle a rechercher
	 * @return
	 */
	List<Tweet> findTweetByKeyword(String keyword);

	/**
	 * Recuperer l'ensemble des tweets tries par date de creation, du plus
	 * recent au plus ancien.
	 * @return			Liste de tweets tries du plus recent au plus ancien
	 */
	List<Tweet> listAllByCreationDateDesc();
}
