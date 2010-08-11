package com.tapestwitter.components;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.tapestwitter.common.TapesTwitterEventConstants;
import com.tapestwitter.domain.model.Tweet;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentEventCallback;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.internal.util.Holder;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Provide an Ajax component that implements the Twitter-like "More" event link.
 * 
 * @author lguerin
 */
@Events(TapesTwitterEventConstants.PROVIDE_MORE_RESULTS)
public class AjaxMoreResults
{

    @SuppressWarnings("unused")
    @Parameter(value = "prop:componentResources.id", defaultPrefix = "literal")
    private String clientId;

    /**
     * Items holder
     */
    final private Holder<Collection<?>> itemsHolder = Holder.create();

    /**
     * The number of items to refresh.
     */
    @Parameter("5")
    private Integer batchSize;

    @Inject
    private ComponentResources resources;

    /**
     * The renderable object
     */
    @Parameter(required = true)
    private Block renderable;

    @OnEvent(value = EventConstants.ACTION)
    @Log
    public Object provideMoreResults()
    {
        itemsHolder.put(Collections.emptyList());

        ComponentEventCallback<List<? super Tweet>> callback = new ComponentEventCallback<List<? super Tweet>>()
        {
            public boolean handleResult(List<? super Tweet> result)
            {
                itemsHolder.put(result);
                return true;
            }
        };

        resources.triggerEvent(TapesTwitterEventConstants.PROVIDE_MORE_RESULTS, new Object[]
        { batchSize }, callback);

        return renderable;
    }
}
