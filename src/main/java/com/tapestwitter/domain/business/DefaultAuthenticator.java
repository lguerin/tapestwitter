package com.tapestwitter.domain.business;

import javax.persistence.NoResultException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tapestwitter.domain.dao.CrudDAO;
import com.tapestwitter.domain.dao.QueryParameters;
import com.tapestwitter.domain.exception.BusinessException;
import com.tapestwitter.domain.exception.UserAlreadyExistsException;
import com.tapestwitter.domain.model.Authority;
import com.tapestwitter.domain.model.User;
import com.tapestwitter.services.security.SecurityContext;

/**
 * This class is the Default implementation of the Authenticator Service {@link Authenticator}.
 * 
 * @author karesti
 * @param <CrudService>
 */
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
@Component("authenticator")
public class DefaultAuthenticator implements Authenticator
{

    private static final Logger logger = LoggerFactory.getLogger(DefaultAuthenticator.class);

    @Autowired
    private SecurityContext securityContext;

    @Autowired
    private CrudDAO crudDAO;

    private SaltSource saltSource;

    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = false, rollbackFor =
    { UserAlreadyExistsException.class, BusinessException.class })
    public void createUser(User user) throws UserAlreadyExistsException, BusinessException
    {
        if (!isAvailableName(user.getUsername())) { throw new UserAlreadyExistsException(); }

        if (logger.isDebugEnabled())
        {
            logger.debug("New user: " + ToStringBuilder.reflectionToString(user, ToStringStyle.MULTI_LINE_STYLE));
        }

        String pass = user.getPassword();
        user.setPassword(this.passwordEncoder.encodePassword(pass, this.saltSource.getSalt(user)));

        Authority authority = crudDAO.findUniqueWithNamedQuery(Authority.FIND_BY_ROLE_NAME, QueryParameters.with("role", Authority.ROLE_USER).parameters());

        if (authority == null) { throw new BusinessException("Authority ROLE_USER missing"); }

        user.addAuthority(authority);
        crudDAO.create(user);
    }

    public User findByUsername(String username)
    {
        return crudDAO.findUniqueWithNamedQuery(User.BY_USERNAME, QueryParameters.with("username", username).parameters());
    }

    public User findByEmail(String email)
    {
        return crudDAO.findUniqueWithNamedQuery(User.BY_EMAIL, QueryParameters.with("email", email).parameters());
    }

    public boolean isAvailableName(String username)
    {
        try
        {
            return findByUsername(username) == null;
        }
        catch (NoResultException e)
        {
            logger.info("User identified by username: " + username + " is available");
            return true;
        }
        catch (EmptyResultDataAccessException e)
        {
            logger.info("User identified by username: " + username + " is available");
            return true;
        }
    }

    public boolean isAvailableEmail(String email)
    {
        try
        {
            return findByEmail(email) == null;
        }
        catch (NoResultException e)
        {
            logger.info("User identified by email: " + email + " is available");
            return true;
        }
        catch (EmptyResultDataAccessException e)
        {
            logger.info("User identified by email: " + email + " is available");
            return true;
        }
    }

    public SecurityContext getSecurityContext()
    {
        return securityContext;
    }

    public void setSecurityContext(SecurityContext securityContext)
    {
        this.securityContext = securityContext;
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

    @Transactional(readOnly = false, rollbackFor = BusinessException.class)
    public void addAuthority(Authority authority) throws BusinessException
    {
        Assert.notNull(authority, "authority");

        if (logger.isDebugEnabled())
        {
            logger.debug("Adding authority = " + authority.getAuthority());
        }
        crudDAO.create(authority);
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

        User userToUpdate = findByUsername(user.getUsername());
        if (userToUpdate == null) { throw new BusinessException("User " + user.getUsername() + "does not exist !"); }

        Authority authorityUpdate = crudDAO.find(Authority.class, authority.getId());
        if (authorityUpdate == null) { throw new BusinessException("User " + authority.getId() + " does not exist !"); }

        userToUpdate.addAuthority(authorityUpdate);
        crudDAO.update(userToUpdate);
    }
}
