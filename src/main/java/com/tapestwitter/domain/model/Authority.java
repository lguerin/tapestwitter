package com.tapestwitter.domain.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.security.core.GrantedAuthority;

/**
 * Authorities - Spring Security
 * 
 * @author karesti
 */
@Entity
@NamedQueries(
{ @NamedQuery(name = Authority.FIND_BY_ROLE_NAME, query = "SELECT a FROM Authority a WHERE a.authority LIKE :role") })
public class Authority implements GrantedAuthority
{
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    public static final String ROLE_USER = "ROLE_USER";

    public static final String FIND_BY_ROLE_NAME = "Authority.findByRoleName";

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
