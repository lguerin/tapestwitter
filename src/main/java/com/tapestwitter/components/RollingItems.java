package com.tapestwitter.components;

import java.util.List;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
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
 * A "Twitter" like component representing top tweets list.
 * 
 * @author lGuerin
 */
@ImportYUI(
{ "yahoo-dom-event", "animation" })
public class RollingItems
{
    /**
     * The height of the rolling items container, in pixel
     */
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL, value = "200")
    private int height;

    /**
     * Duration of the animation when incoming item is displayed
     */
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL, value = "1.0")
    private float duration;

    /**
     * The rolling period, in millisecond
     */
    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL, value = "5000")
    private int period;

    /**
     * A list of items
     */
    @Property
    @Parameter(required = true)
    private List<?> items;

    @SuppressWarnings("unused")
    @Inject
    private Block renderableItem;

    @Property
    @Parameter(required = true)
    private Object current;

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
        JSONObject data = new JSONObject("id", clientId, "height", Integer.toString(height), "duration", Float.toString(duration), "period", Integer
                .toString(period));
        javascriptSupport.addInitializerCall("rollingItemsBuilder", data);
    }
}
