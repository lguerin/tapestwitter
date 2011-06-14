/**
 * 
 */
package com.tapestwitter.common;

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
}
