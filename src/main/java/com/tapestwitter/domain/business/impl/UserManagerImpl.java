package com.tapestwitter.domain.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tapestwitter.domain.business.UserManager;
import com.tapestwitter.domain.dao.IAuthorityDAO;
import com.tapestwitter.domain.dao.IUserDAO;
import com.tapestwitter.domain.exception.BusinessException;
import com.tapestwitter.domain.exception.UserAlreadyExistsException;
import com.tapestwitter.domain.model.Authority;
import com.tapestwitter.domain.model.User;
import com.tapestwitter.services.security.TapestwitterSecurityContext;


/**
 * Implementation of UserManager {@link UserManager}
 * @author karesti
 * @param <CrudService>
 *
 */
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
@Component("userManager")
public class UserManagerImpl implements UserManager
{

	private static final Logger logger = LoggerFactory.getLogger(UserManagerImpl.class);

	@Autowired
	private TapestwitterSecurityContext securityContext;

	@Autowired
	private IUserDAO userDao;
	
	@Autowired
	private IAuthorityDAO authorityDao;

	private SaltSource saltSource;

	private PasswordEncoder passwordEncoder;

	
	@Transactional(readOnly = false, rollbackFor = {UserAlreadyExistsException.class, BusinessException.class})
	public void addUser(User user) throws UserAlreadyExistsException, BusinessException
	{
		Assert.notNull(user, "user");

		if (findByUsername(user.getUsername()) != null) {
			throw new UserAlreadyExistsException();
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("User user = " + user);
		}
		String pass = user.getPassword();
		user.setPassword(this.passwordEncoder.encodePassword(pass, this.saltSource.getSalt(user)));
		
		Authority authority = authorityDao.findByRoleName("ROLE_USER");
		
		if(authority == null){
			throw new BusinessException("Authority ROLE_USER missing");
		}
		
		user.addAuthority(authority);
		userDao.create(user);
	}
	
	@Transactional(readOnly = false , rollbackFor = BusinessException.class)
	public void addAuthority(Authority authority) throws BusinessException
	{
		Assert.notNull(authority, "authority");
		
		if (logger.isDebugEnabled())
		{
			logger.debug("Authority authority = " + authority);
		}
		
		if (authorityDao.findByRoleName(authority.getAuthority()) != null) {
			throw new BusinessException("Already existing ROLE " + authority.getAuthority() );
		}
		authorityDao.create(authority);
			
	}

	@Transactional(readOnly = false, rollbackFor = BusinessException.class)
	public void addAuthorityToUser(User user, Authority authority) throws BusinessException
	{
		Assert.notNull(user, "user");
		Assert.notNull(authority, "authority");

		if (logger.isDebugEnabled())
		{
			logger.debug("User = " + user);
			logger.debug("Authority = " + authority);
		}

		User userToUpdate = userDao.findById(user.getId());
		if(userToUpdate == null){
			throw new BusinessException("User " + user.getUsername() + "does not exist !");
		}
		
		Authority authorityUpdate = authorityDao.findById(authority.getId());
		if(userToUpdate == null){
			throw new BusinessException("User " + authority.getId() + " does not exist !");
		}
		
		userToUpdate.addAuthority(authorityUpdate);
		userDao.update(userToUpdate);
	}

	public User findByUsername(String username)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("UserName = " + username);
		}
		return userDao.findByUsername(username);
	}

	public User findByEmail(String email)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Email = " + email);
		}
		return userDao.findByEmail(email);
	}

	public boolean isAvailableName(String username)
	{
		User user = userDao.findByUsername(username);
		return user == null;
	}

	public boolean isAvailableEmail(String email)
	{
		User user = userDao.findByEmail(email);
		return user == null;
	}

	public TapestwitterSecurityContext getSecurityContext()
	{
		return securityContext;
	}

	public void setSecurityContext(TapestwitterSecurityContext securityContext)
	{
		this.securityContext = securityContext;
	}

	public IUserDAO getUserDao()
	{
		return userDao;
	}

	public void setUserDao(IUserDAO userDao)
	{
		this.userDao = userDao;
	}

	public SaltSource getSaltSource()
	{
		return saltSource;
	}

	public void setSaltSource(SaltSource saltSource)
	{
		this.saltSource = saltSource;
	}

	public PasswordEncoder getPasswordEncoder()
	{
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder)
	{
		this.passwordEncoder = passwordEncoder;
	}

}
