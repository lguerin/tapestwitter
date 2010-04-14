package com.tapestwitter.domain.dao;

import java.util.List;

import com.tapestwitter.domain.model.Authority;
import com.tapestwitter.domain.model.User;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.unitils.UnitilsTestNG;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

@SpringApplicationContext("test-config.xml")
public class IUserDAOTest extends UnitilsTestNG
{

	@SpringBean("userDAO")
	private IUserDAO userDAO;

	@SpringBean("authorizationDAO")
	private IAuthorizationDAO authorizationDAO;

	private int DEFAULT_NB_USERS_DATASET = 2;

	@DataSet
	@Test
	public void testFindUserByUsername()
	{
		User user = userDAO.findByUsername("katia");
		Assert.assertNotNull(user);
	}

	@DataSet
	@Test
	public void testFindUserByEmail()
	{
		User user = userDAO.findByEmail("katiaaresti@gmail.com");
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

	@DataSet
	@Test
	public void testCreateUser()
	{
		User actualUser = new User();
		String username = "pepe";
		actualUser.setEmail("pepe@tapestwitter.com");
		actualUser.setLogin(username);
		actualUser.setPassword("pepePassword");
		actualUser.setFullName("Pepe pepe");
		userDAO.create(actualUser);
		final List<User> users = userDAO.listAll();
		Assert.assertEquals(DEFAULT_NB_USERS_DATASET + 1, users.size());
		User expectedUser = userDAO.findByUsername(username);
		ReflectionAssert.assertReflectionEquals(expectedUser, actualUser, ReflectionComparatorMode.IGNORE_DEFAULTS);
	}
}
