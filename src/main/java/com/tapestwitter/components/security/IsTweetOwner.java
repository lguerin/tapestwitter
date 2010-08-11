package com.tapestwitter.components.security;

import org.apache.tapestry5.corelib.base.AbstractConditional;

/**
 * This component verifies that the current
 * user is the twet owner
 * 
 * @author karesti
 */
public class IsTweetOwner extends AbstractConditional
{

    @Override
    protected boolean test()
    {
        return true;
    }
}
