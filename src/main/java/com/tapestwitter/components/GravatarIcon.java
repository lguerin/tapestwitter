package com.tapestwitter.components;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;

import com.tapestwitter.domain.model.Tweet;

/**
 * Display the gravatar icon of the {@link Tweet} owner.
 * 
 * @author lguerin
 */
public class GravatarIcon
{
    private static final String GRAVATAR_URL = "http://www.gravatar.com/avatar/";

    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String email;

    @Parameter(defaultPrefix = BindingConstants.ASSET, value = "context:components/tweetinfosbox/images/tapestry_logo.gif")
    private Asset defaultImage;

    @Parameter(defaultPrefix = BindingConstants.LITERAL, value = "50")
    private Integer size;

    void beginRender(MarkupWriter writer)
    {
        writer.element("img", "src", buildGravatarUrl());
    }

    void afterRender(MarkupWriter writer)
    {
        writer.end();
    }

    private String buildGravatarUrl()
    {
        StringBuilder builder = new StringBuilder(GRAVATAR_URL);
        builder.append(DigestUtils.md5Hex(email));
        builder.append("?size=");
        builder.append(size);
        return builder.toString();
    }
}
