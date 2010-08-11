/**
 * 
 */
package com.tapestwitter.common;

import com.tapestwitter.components.AjaxMoreResults;
import com.tapestwitter.mixins.AjaxValidation;

/**
 * This class contains the events triggereds by the TapesTwitter applications.
 * 
 * @author lguerin
 */
public class TapesTwitterEventConstants
{
    /**
     * Event triggered when the {@link AjaxMoreResults} component link is
     * clicked.
     */
    public static final String PROVIDE_MORE_RESULTS = "provideMoreResults";

    /**
     * Event triggered when the {@link AjaxValidation} mixin is
     * activated.
     */
    public static final String AJAX_VALIDATE = "ajaxValidate";
}
