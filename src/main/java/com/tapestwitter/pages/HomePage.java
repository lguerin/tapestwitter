package com.tapestwitter.pages;

import java.util.List;

import com.tapestwitter.domain.business.TweetManager;
import com.tapestwitter.domain.model.Tweet;

import org.slf4j.Logger;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.beaneditor.Width;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Start page of application tapestwitter.
 */
public class HomePage
{
	/**
	 * Logger de la classe
	 */
	@Inject
	private Logger logger;

	/**
	 * Liste de tweets charges dans la loop
	 */
	@Property
	private List<Tweet> tweets;

	/**
	 * Element courant de la boucle loop sur la liste de tweets.
	 */
	@SuppressWarnings("unused")
	@Property
	private Tweet currentTweet;

	/**
	 * Contenu du champ textarea dans le formulaire de saisie
	 * du message.
	 */
	@Property
	@Validate("required,maxlength=140")
	@Width(140)
	private String tweetContentInput;

	/**
	 * Service gestionnaire de {@link Tweet}
	 */
	@Inject
	private TweetManager tweetManager;

	@SuppressWarnings("unused")
	@Inject
	private Block displayTweetBox;

	@SetupRender
	public void loadTweets()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug(">>> Chargement de la liste des tweets");
		}
		tweets = tweetManager.listAllTweet();
		if (logger.isDebugEnabled())
		{
			logger.debug("<<< " + tweets.size() + " element(s) charge(s)...");
		}
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "publishTweetForm")
	public void onPublishTweet()
	{
		logger.info(">>> Publication d'un nouveau tweet...");
		tweetManager.createTweet(tweetContentInput);
	}
}
