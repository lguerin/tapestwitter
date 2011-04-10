package com.tapestwitter.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entite representant un message bref (tweet).
 * 
 * @author lguerin
 * @author karesti
 */
@Entity
@NamedQueries(
{ @NamedQuery(name = Tweet.FIND_BY_KEYWORD, query = "SELECT t FROM Tweet t WHERE t.tweet LIKE :keyword"),
        @NamedQuery(name = Tweet.ALL_ORDER_BY_DATE_DESC, query = "SELECT t FROM Tweet t ORDER BY t.creationDate DESC"),
        @NamedQuery(name = Tweet.FIND_ALL_RECENT, query = "SELECT t FROM Tweet t ORDER BY t.creationDate DESC"),
        @NamedQuery(name = Tweet.FIND_ALL_RECENT_WITH_ID, query = "SELECT t FROM Tweet t WHERE t.id < :id ORDER BY t.creationDate DESC"),
        @NamedQuery(name = Tweet.COUNT_TWEETS_FOR_USER, query = "SELECT COUNT(t.author) FROM Tweet t WHERE t.author = :author") })
public class Tweet implements Serializable
{
    public static final String FIND_BY_KEYWORD = "Tweet.searchByKeyword";

    public static final String ALL_ORDER_BY_DATE_DESC = "Tweet.searchAllOrderByDateDesc";

    public static final String FIND_ALL_RECENT = "Tweet.searchAllRecents";

    public static final String FIND_ALL_RECENT_WITH_ID = "Tweet.searchAllRecentsById";

    public static final String COUNT_TWEETS_FOR_USER = "Tweet.countUserTotalTweets";

    private static final long serialVersionUID = -297923216288866711L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String tweet;

    private String author;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public Tweet()
    {
    }

    public Tweet(String tweet, String author)
    {
        this.tweet = tweet;
        this.author = author;
    }

    public Long getId()
    {
        return id;
    }

    public String getTweet()
    {
        return tweet;
    }

    public void setTweet(String tweet)
    {
        this.tweet = tweet;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public Date getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
    }
}
