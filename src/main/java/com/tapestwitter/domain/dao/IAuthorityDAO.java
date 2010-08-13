package com.tapestwitter.domain.dao;

import com.tapestwitter.domain.model.Authority;

/**
 * DAO responsable de la manipulation des {@link Authority}.*
 * 
 * @author karesti
 *
 */
public interface IAuthorityDAO extends IGenericDAO<Authority, Long>
{

	Authority findByRoleName(String roleName);
}
