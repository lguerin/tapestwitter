/**
 * 
 */
package com.tapestwitter.common;

import com.tapestwitter.domain.model.Tweet;
import com.tapestwitter.mixins.AjaxValidation;

/**
 * This class contains the events triggereds by the TapesTwitter applications.
 * 
 * @author lguerin
 */
public class TapesTwitterEventConstants
{
    /**
     * Event triggered when the {@link AjaxValidation} mixin is
     * activated.
     */
    public static final String AJAX_VALIDATE = "ajaxValidate";

    /**
     * Event trigered when user click "More" tweets button.
     */
    public static final String MORE_TWEETS = "moreTweets";

    /**
     * Event trigered when user delete a {@link Tweet}
     */
    public static final String DELETE_TWEET = "deleteTweet";
}
