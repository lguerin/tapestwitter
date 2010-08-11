package com.tapestwitter.components.security;

import com.tapestwitter.services.security.TapestwitterSecurityContext;

import org.apache.tapestry5.corelib.base.AbstractConditional;
import org.apache.tapestry5.ioc.annotations.Inject;

public class IsAdmin extends AbstractConditional
{

    @Inject
    private TapestwitterSecurityContext securityCtx;

    @Override
    protected boolean test()
    {
        return securityCtx.getUser() != null && securityCtx.getUser().isAdmin();
    }

}
