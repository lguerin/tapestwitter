package com.tapestwitter.domain.dao;

import java.util.List;

import junit.framework.Assert;

import com.tapestwitter.domain.model.Authority;
import com.tapestwitter.domain.model.User;

import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

@SpringApplicationContext("test-config.xml")
public class IUserDAOTest extends UnitilsJUnit4
{

	@SpringBean("userDAO")
	private IUserDAO userDAO;

	@SpringBean("authorizationDAO")
	private IAuthorizationDAO authorizationDAO;

	private int DEFAULT_NB_USERS_DATASET = 2;

	@DataSet
	@Test
	public void testCreateUser()
	{
		User user = new User();
		user.setEmail("pepe@tapestwitter.com");
		user.setLogin("pepe");
		user.setPassword("pepePassword");
		user.setFullName("Pepe pepe");
		userDAO.create(user);
		final List<User> users = userDAO.listAll();
		Assert.assertEquals(DEFAULT_NB_USERS_DATASET + 1, users.size());
	}

	@DataSet
	@Test
	public void testFindUserByUsername()
	{
		User user = userDAO.findByUsername("katia");
		Assert.assertNotNull(user);

	}

	@DataSet
	@Test
	public void testCreateAuthority()
	{
		User user = userDAO.findByUsername("katia");
		Authority authority = new Authority();
		authority.setAuthority("ROLE_USER");
		authority.setUser(user);
		user.addAuthority(authority);
		authorizationDAO.create(authority);
		Assert.assertEquals(user.getAuthorities().size(), 1);
	}
}
