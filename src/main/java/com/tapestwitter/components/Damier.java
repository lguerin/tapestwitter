/**
 * 
 */
package com.tapestwitter.components;

import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Loop;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.AssetSource;

import com.tapestwitter.model.DamierItemModel;

/**
 * Tapestry References sites component.
 * 
 * @author lGuerin
 */
public class Damier
{
    /**
     * The displayed references
     */
    @Parameter(required = true)
    @Property
    private List<DamierItemModel> items;

    @Property
    private DamierItemModel current;

    /**
     * Reference items loop
     */
    @SuppressWarnings("unused")
    @Component(parameters =
    { "source=items", "value=current" })
    private Loop damierLoop;

    @Inject
    private AssetSource assetSource;

    /**
     * @return The icon of {@link DamierItemModel}
     */
    public Asset getDamierItemIcon()
    {
        return assetSource.getUnlocalizedAsset("context:components/damier/images/" + current.getIcon());
    }
}
