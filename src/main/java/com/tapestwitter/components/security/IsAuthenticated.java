package com.tapestwitter.components.security;

import org.apache.tapestry5.corelib.base.AbstractConditional;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.tapestwitter.services.security.TapestwitterSecurityContext;

/**
 * This component helps to know
 * if the current user is connected or not
 * 
 * @author karesti
 */
public class IsAuthenticated extends AbstractConditional
{
    @Inject
    private TapestwitterSecurityContext securityCtx;

    @Override
    protected boolean test()
    {
        return securityCtx.isLoggedIn();
    }

}
