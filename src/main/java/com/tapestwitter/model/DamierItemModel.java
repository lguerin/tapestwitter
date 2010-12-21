/**
 * 
 */
package com.tapestwitter.model;

import com.tapestwitter.components.Damier;

/**
 * Model for {@link Damier} Component Item.
 * 
 * @author lGuerin
 */
public class DamierItemModel
{
    private String id;

    private String title;

    private String description;

    private String url;

    private String icon;

    public DamierItemModel()
    {
    }

    /**
     * @param id
     * @param title
     * @param description
     * @param icon
     */
    public DamierItemModel(String id, String title, String description, String url, String icon)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.icon = icon;
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the url
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url)
    {
        this.url = url;
    }

    /**
     * @return the icon
     */
    public String getIcon()
    {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon)
    {
        this.icon = icon;
    }

}
