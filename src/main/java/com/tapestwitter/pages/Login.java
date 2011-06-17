package com.tapestwitter.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.tapestwitter.pages.home.Dashboard;
import com.tapestwitter.services.URLResolver;
import com.tapestwitter.services.security.SecurityContext;

@Import(stylesheet = "context:/layout/css/forms.css")
public class Login
{
    @Inject
    private SecurityContext securityContext;

    @Inject
    private URLResolver urlResolver;

    private boolean error;

    @OnEvent(value = EventConstants.ACTIVATE)
    public Object checkSecurityCtx()
    {
        if (securityContext.isLoggedIn()) { return Dashboard.class; }
        return null;
    }

    @OnEvent(value = EventConstants.ACTIVATE)
    public void checkError(String extra)
    {
        if (extra.equals("error"))
        {
            error = true;
        }
    }

    public String getLoginUrl()
    {
        return urlResolver.resolveLoginUrl();
    }

    public boolean isError()
    {
        return error;
    }

}
