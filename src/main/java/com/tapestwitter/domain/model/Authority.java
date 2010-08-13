package com.tapestwitter.domain.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

/**
 * Authoritys from Spring Security
 * 
 * @author karesti
 *
 */
@Entity
public class Authority implements GrantedAuthority
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1803599999277426176L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, name = "id_authority")
	private Long id;

	@Column(nullable = false)
	private String authority;

	/** Reverse owner */
	@ManyToMany(mappedBy = "authorities")
	private List<User> users;

	public Authority()
	{
		super();
	}

	public Authority(String authority)
	{
		super();
		this.authority = authority;
	}

	public long getId()
	{
		return id;
	}

	public String getAuthority()
	{
		return authority;
	}

	public void setAuthority(String authority)
	{
		this.authority = authority;
	}

	public List<User> getUsers()
	{
		return users;
	}

	public void setUser(List<User> users)
	{
		this.users = users;
	}

}