package com.tapestwitter.domain.dao.impl;

import java.util.List;

import javax.persistence.Query;

import com.tapestwitter.domain.dao.ITweetDAO;
import com.tapestwitter.domain.model.Tweet;

import org.springframework.stereotype.Repository;

import org.apache.tapestry5.ioc.internal.util.Defense;

/**
 * Implementation du DAO {@link ITweetDAO}.
 *  
 * @author lguerin
 *
 */
@Repository("tweetDAO")
public class TweetDAOImpl extends GenericDAOImpl<Tweet, Long> implements ITweetDAO
{

	/* (non-Javadoc)
	 * @see com.tapestwitter.domain.dao.ITweetDAO#findTweetByKeyword(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Tweet> findTweetByKeyword(String keyword)
	{
		Defense.notNull(keyword, "keyword");
		Query query = entityManager.createQuery("SELECT t FROM " + getEntityType() + " t WHERE t.tweet LIKE :keyword");
		query.setParameter("keyword", "%" + keyword + "%");
		return (List<Tweet>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Tweet> listAllByCreationDateDesc()
	{
		Query query = this.entityManager.createQuery("FROM " + getEntityType() + " t ORDER BY t.creationDate DESC");
		List<Tweet> resultList = (List<Tweet>) query.getResultList();
		return resultList;
	}
}
