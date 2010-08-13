package com.tapestwitter.domain.dao.impl;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.tapestwitter.domain.dao.IAuthorityDAO;
import com.tapestwitter.domain.model.Authority;

/**
 * Implementation du DAO {@link IAuthorityDAO}.
 * 
 * @author karesti
 *
 */
@Repository("authorityDAO")
public class AuthorityDAOImpl extends GenericDAOImpl<Authority, Long> implements IAuthorityDAO
{

	public Authority findByRoleName(String roleName) {
		
		Assert.notNull(roleName, "roleName");

		Query query = entityManager.createQuery("SELECT a FROM " + getEntityType() + " a WHERE a.authority LIKE :role");
		query.setParameter("role", roleName);

		try {
		
			return (Authority) query.getSingleResult();
		
		} catch (RuntimeException re) {
			return null;
		}
	}
}
