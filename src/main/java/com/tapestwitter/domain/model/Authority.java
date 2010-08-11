package com.tapestwitter.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.security.core.GrantedAuthority;

/**
 * Authoritys from Spring Security
 * 
 * @author karesti
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

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false, updatable = false, insertable = false)
    private User user;

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

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

}
