package com.tapestwitter.services;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.Request;

/**
 * Implementation {@link URLResolver}
 * 
 * @author karesti
 */
public class DefaultURLResolver implements URLResolver
{

    @Inject
    @Symbol("loginSecurityFilterUrl")
    private String loginUrl;

    @Inject
    @Symbol("logoutSecurityFilterUrl")
    private String logoutUrl;

    @Inject
    private Request request;

    public String resolveLoginUrl()
    {
        return request.getContextPath() + loginUrl;
    }

    public String resolveLogoutUrl()
    {
        return request.getContextPath() + logoutUrl;
    }

}
