package com.tapestwitter.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/**
 * Entite representant un utilisateur.
 * 
 * @author karesti
 *
 */
@Entity
public class User implements UserDetails
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6157226898787740763L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, name = "id_user")
	private Long id;

	@Column(nullable = false)
	private String login;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String fullName;

	private String password;

	/**
	 * Relationship owner
	 * Fetch.EAGER because User needs all the authorities
	 * all the time  
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "jnd_usr_auth", joinColumns = @JoinColumn(name = "user_fk"), inverseJoinColumns = @JoinColumn(name = "auth_fk"))
	private List<Authority> authorities;

	public Long getId()
	{
		return id;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getFullName()
	{
		return fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	public void addAuthority(Authority authority)
	{
		Assert.notNull(authority, "authority");
		if (authorities == null)
		{
			authorities = new ArrayList<Authority>();
		}
		authorities.add(authority);
	}

	public void setAuthorities(List<Authority> authorities)
	{
		Assert.notNull(authorities);
		this.authorities = authorities;
	}

	public Collection<GrantedAuthority> getAuthorities()
	{
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>(authorities.size());
		for (Authority authority : authorities)
		{
			GrantedAuthority ga = authority;
			auths.add(ga);
		}
		return auths;
	}

	public boolean isAdmin()
	{
		boolean result = false;
		for (Authority authority : authorities)
		{
			if ("ROLE_ADMIN".equals(authority.getAuthority()))
			{
				result = true;
			}
		}
		return result;
	}

	public String getUsername()
	{
		return login;
	}

	public boolean isAccountNonExpired()
	{
		return true;
	}

	public boolean isAccountNonLocked()
	{
		return true;
	}

	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	public boolean isEnabled()
	{
		return true;
	}

}
