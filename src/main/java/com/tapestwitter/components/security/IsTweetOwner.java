package com.tapestwitter.components.security;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.base.AbstractConditional;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.tapestwitter.domain.business.TweetLoader;
import com.tapestwitter.domain.model.Tweet;
import com.tapestwitter.services.security.SecurityContext;

/**
 * This component verifies that the current
 * user is the tweet owner
 * 
 * @author karesti
 */
public class IsTweetOwner extends AbstractConditional
{
    @Parameter(required = true, allowNull = false)
    private String tweetId;

    @Inject
    private TweetLoader tweetLoader;

    @Inject
    private SecurityContext securityCtx;

    @Override
    protected boolean test()
    {
        Tweet current = tweetLoader.findTweetById(Long.valueOf(tweetId));
        return securityCtx.getUser() != null && securityCtx.getUser().getLogin().equals(current.getAuthor());
    }
}
