package com.tapestwitter.domain.dao.impl;

import javax.persistence.Query;

import com.tapestwitter.domain.dao.IUserDAO;
import com.tapestwitter.domain.model.User;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Implementation du DAO {@link IUserDAO}.
 * 
 * @author karesti
 *
 */
@Repository("userDAO")
public class UserDAOImpl extends GenericDAOImpl<User, Long> implements IUserDAO
{

	public User findByUsername(String username)
	{
		Assert.notNull(username, "username");

		Query query = entityManager.createQuery("SELECT u FROM " + getEntityType() + " u WHERE u.login LIKE :un");
		query.setParameter("un", username.toLowerCase());

		try {
			return (User) query.getSingleResult();
		} catch (RuntimeException re) {
			return null;
		}
	}

	public User findByEmail(String email) {
		Assert.notNull(email, "email");

		Query query = entityManager.createQuery("SELECT u FROM " + getEntityType() + " u WHERE u.email LIKE :em");
		query.setParameter("em", email.toLowerCase());
		try {
			return (User) query.getSingleResult();
		} catch (RuntimeException re) {
			return null;
		}
		
	}

}
