package com.tapestwitter.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * Simple Layout pour les pages tapestwitter
 * <ul>
 * <li>Index</li>
 * <li>Signup</li>
 * </ul>
 * 
 * @author karesti
 */
public class SimpleLayout
{
    /** The page title, for the <title> element and the <h1>element. */
    @SuppressWarnings("unused")
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

}
