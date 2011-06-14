package com.tapestwitter.components.security;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.corelib.base.AbstractConditional;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.tapestwitter.domain.business.TweetLoader;
import com.tapestwitter.domain.model.Tweet;
import com.tapestwitter.services.security.SecurityContext;

/**
 * This component verifies that the current
 * user is the twet owner
 * 
 * @author karesti
 */
public class IsTweetOwner extends AbstractConditional
{

    @Inject
    private ComponentResources resources;

    @Inject
    private TweetLoader tweetLoader;

    @Inject
    private SecurityContext securityCtx;

    @Override
    protected boolean test()
    {
        String tweetId = resources.getInformalParameter("tweetId", String.class);
        Tweet current = tweetLoader.findTweetById(new Long(tweetId));
        return securityCtx.getUser() != null && securityCtx.getUser().getLogin().equals(current.getAuthor());
    }
}
