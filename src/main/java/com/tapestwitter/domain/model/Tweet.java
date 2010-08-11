package com.tapestwitter.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entite representant un message bref (tweet).
 * 
 * @author lguerin
 */
@Entity
public class Tweet implements Serializable
{
    /**
     * serialUid
     */
    private static final long serialVersionUID = -297923216288866711L;

    /**
     * Identifiant du tweet
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Message a publier
     */
    private String tweet;

    /**
     * Auteur du tweet
     */
    private String author;

    /**
     * Date de creation du tweet
     */
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    /**
     * Constructeur par defaut
     */
    public Tweet()
    {
    }

    /**
     * @param tweet
     * @param author
     */
    public Tweet(String tweet, String author)
    {
        this.tweet = tweet;
        this.author = author;
    }

    /**
     * @return the id
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @return the tweet
     */
    public String getTweet()
    {
        return tweet;
    }

    /**
     * @param tweet the tweet to set
     */
    public void setTweet(String tweet)
    {
        this.tweet = tweet;
    }

    /**
     * @return the author
     */
    public String getAuthor()
    {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author)
    {
        this.author = author;
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate()
    {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
    }
}
