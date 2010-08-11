package com.tapestwitter.components;

import com.tapestwitter.services.TapestwiterURLResolver;

import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Main Layout component for pages of application tapestwitter.
 */
public class Layout extends SimpleLayout
{
    @Inject
    private TapestwiterURLResolver urlResolver;

    public String getLogoutUrl()
    {
        return urlResolver.getLogoutUrl();
    }
}
