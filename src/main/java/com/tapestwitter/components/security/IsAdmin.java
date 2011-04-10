package com.tapestwitter.components.security;

import org.apache.tapestry5.corelib.base.AbstractConditional;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.tapestwitter.services.security.SecurityContext;

public class IsAdmin extends AbstractConditional
{

    @Inject
    private SecurityContext securityCtx;

    @Override
    protected boolean test()
    {
        return securityCtx.getUser() != null && securityCtx.getUser().isAdmin();
    }

}
