package com.tapestwitter.domain.dao.impl;

import com.tapestwitter.domain.dao.IAuthorizationDAO;
import com.tapestwitter.domain.model.Authority;

import org.springframework.stereotype.Repository;

/**
 * Implementation du DAO {@link IAuthorizationDAO}.
 * 
 * @author karesti
 */
@Repository("authorizationDAO")
public class AuthorizationDAOImpl extends GenericDAOImpl<Authority, Long> implements IAuthorizationDAO
{

}
