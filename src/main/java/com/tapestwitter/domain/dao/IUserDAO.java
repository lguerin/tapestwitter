package com.tapestwitter.domain.dao;

import com.tapestwitter.domain.model.User;

/**
 * DAO responsable de la manipulation des {@link User}.*
 * 
 * @author karesti
 */
public interface IUserDAO extends IGenericDAO<User, Long>
{

    User findByUsername(String username);

    User findByEmail(String email);

}
