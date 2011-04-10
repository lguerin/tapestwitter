package com.tapestwitter.domain.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tapestwitter.domain.dao.CrudServiceDAO;
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
    private CrudServiceDAO crudDAO;

    @Autowired
    private SaltSource saltSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = false, rollbackFor =
    { UserAlreadyExistsException.class, BusinessException.class })
    public void createUser(User user) throws UserAlreadyExistsException, BusinessException
    {
        if (findByUsername(user.getUsername()) != null) { throw new UserAlreadyExistsException(); }

        if (logger.isDebugEnabled())
        {
            logger.debug("User user = " + user);
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
        return crudDAO.findUniqueWithNamedQuery(User.BY_USERNAME);
    }

    public User findByEmail(String email)
    {
        return crudDAO.findUniqueWithNamedQuery(User.BY_EMAIL);
    }

    public boolean isAvailableName(String username)
    {
        return findByUsername(username) == null;
    }

    public boolean isAvailableEmail(String email)
    {
        return findByEmail(email) == null;
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

}
