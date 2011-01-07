package com.tapestwitter.components;

import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import com.howardlewisship.tapx.yui.ImportYUI;
import com.tapestwitter.domain.model.User;
import com.tapestwitter.services.TapestwiterURLResolver;
import com.tapestwitter.services.security.TapestwitterSecurityContext;

/**
 * Main Layout component for pages of application tapestwitter.
 */
@ImportYUI(
{ "yahoo-dom-event" })
public class Layout extends SimpleLayout
{
    @Inject
    private TapestwiterURLResolver urlResolver;

    @Inject
    private TapestwitterSecurityContext securityContext;

    @Property(write = false)
    private User user;

    public String getLogoutUrl()
    {
        return urlResolver.getLogoutUrl();
    }

    @SetupRender
    public void setup()
    {
        if (securityContext.isLoggedIn())
        {
            user = securityContext.getUser();
        }
    }

    @Environmental
    private JavaScriptSupport javascriptSupport;

    @AfterRender
    public void initJs()
    {
        JSONObject args = new JSONObject("id", "userMenu", "target", "userMenuItems");
        javascriptSupport.addInitializerCall("initFloatMenu", args);
    }
}
