package com.tapestwitter.services.impl;

import com.tapestwitter.services.TapestwiterURLResolver;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.Request;

/**
 * Implementation {@link TapestwiterURLResolver}
 * 
 * @author karesti
 */
public class TapestwitterURLResolverImpl implements TapestwiterURLResolver
{

    @Inject
    @Symbol("loginSecurityFilterUrl")
    private String loginUrl;

    @Inject
    @Symbol("logoutSecurityFilterUrl")
    private String logoutUrl;

    @Inject
    private Request request;

    public String getLoginUrl()
    {
        return request.getContextPath() + loginUrl;
    }

    public String getLogoutUrl()
    {
        return request.getContextPath() + logoutUrl;
    }

}
