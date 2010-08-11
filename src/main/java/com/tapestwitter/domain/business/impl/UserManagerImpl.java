package com.tapestwitter.domain.business.impl;

import com.tapestwitter.domain.business.UserManager;
import com.tapestwitter.domain.dao.IAuthorizationDAO;
import com.tapestwitter.domain.dao.IUserDAO;
import com.tapestwitter.domain.exception.CreateAuthorityException;
import com.tapestwitter.domain.exception.CreateUserException;
import com.tapestwitter.domain.model.Authority;
import com.tapestwitter.domain.model.User;
import com.tapestwitter.services.security.TapestwitterSecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Implementation of UserManager {@link UserManager}
 * 
 * @author karesti
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
    private IAuthorizationDAO authorityDao;

    private SaltSource saltSource;

    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = false, rollbackFor = CreateUserException.class)
    public void addUser(User user) throws CreateUserException, CreateAuthorityException
    {
        Assert.notNull(user, "user");

        if (logger.isDebugEnabled())
        {
            logger.debug("User user = " + user);
        }
        String pass = user.getPassword();
        user.setPassword(this.passwordEncoder.encodePassword(pass, this.saltSource.getSalt(user)));
        userDao.create(user);
        Authority authority = new Authority();
        authority.setAuthority("ROLE_USER");
        addAuthority(user, authority);
    }

    @Transactional(readOnly = false, rollbackFor = CreateUserException.class)
    public void addAuthority(User user, Authority authority) throws CreateAuthorityException
    {
        Assert.notNull(user, "user");
        Assert.notNull(authority, "authority");

        if (logger.isDebugEnabled())
        {
            logger.debug("User = " + user);
            logger.debug("Authority = " + authority);
        }

        user = userDao.findById(user.getId());
        user.addAuthority(authority);
        authority.setUser(user);
        authorityDao.create(authority);
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
