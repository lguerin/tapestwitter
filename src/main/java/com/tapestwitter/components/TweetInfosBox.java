package com.tapestwitter.components;

import com.tapestwitter.domain.model.Tweet;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * Display the main informations about a {@link Tweet}.
 * 
 * @author lguerin
 */
public class TweetInfosBox
{
    /**
     * Tweet to display
     */
    @SuppressWarnings("unused")
    @Property
    @Parameter(allowNull = false, required = true)
    private Tweet tweet;
}
