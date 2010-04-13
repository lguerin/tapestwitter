package com.tapestwitter.components;

import java.util.Locale;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.internal.util.LocaleUtils;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.ThreadLocale;
import org.apache.tapestry5.services.PersistentLocale;
import org.apache.tapestry5.util.EnumSelectModel;

import com.tapestwitter.common.EnumLocale;



/**
 * This component changes current user's 
 * locale
 * 
 * @author kAresti
 *
 */
public class ChangeLocale {


	@Inject
	private Messages messages;
	
	private SelectModel model = new EnumSelectModel(EnumLocale.class, messages);
	
	@Inject
	private PersistentLocale persistentLocale;
	
	@Property
	private EnumLocale selectedLocale;
	
	@Log
	public void onSuccess(){
		if(selectedLocale != null){
			persistentLocale.set(LocaleUtils.toLocale(selectedLocale.name()));
		}
	}
		
	public SelectModel getModel() {
		return model;
	}
	
}
