/**
 * 
 */
package com.tapestwitter.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import com.howardlewisship.tapx.yui.ImportYUI;

/**
 * A "Twitter" like Trends component
 * 
 * @author lguerin
 */
@ImportYUI(
{ "yahoo-dom-event" })
public class Trends
{
    /**
     * The height of the Trends container, in pixel
     */
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL, value = "35")
    private int height;

    /**
     * The speed, in millisecond
     */
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL, value = "50")
    private int period;

    /**
     * Unique client Id
     */
    @Property
    private String clientId;

    @Inject
    private ComponentResources resources;

    @Environmental
    private JavaScriptSupport javascriptSupport;

    @BeginRender
    public void initClientId()
    {
        // Get Unique Client Id
        clientId = javascriptSupport.allocateClientId(resources);
    }

    @AfterRender
    public void initJs()
    {
        JSONObject data = new JSONObject("id", clientId, "height", Integer.toString(height), "period", Integer.toString(period));
        javascriptSupport.addInitializerCall("trendsBuilder", data);
    }
}
