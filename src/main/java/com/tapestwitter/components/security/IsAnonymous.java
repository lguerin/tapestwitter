package com.tapestwitter.components.security;

import com.tapestwitter.services.security.TapestwitterSecurityContext;

import org.apache.tapestry5.corelib.base.AbstractConditional;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * This component helps to know
 * if the current user is connected or not
 * 
 * @author karesti
 */
public class IsAnonymous extends AbstractConditional
{
    @Inject
    private TapestwitterSecurityContext securityCtx;

    @Override
    protected boolean test()
    {
        return !securityCtx.isLoggedIn();
    }

}
