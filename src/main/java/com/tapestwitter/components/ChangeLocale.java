package com.tapestwitter.components;

import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.internal.util.LocaleUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.ioc.services.ThreadLocale;
import org.apache.tapestry5.services.PersistentLocale;
import org.springframework.util.CollectionUtils;

/**
 * This component changes current user's 
 * locale
 * 
 * @author kAresti
 *
 */
public class ChangeLocale {

	@Inject
	private PersistentLocale persistentLocale;

	@Inject
	private ThreadLocale threadLocale;
	 
	@Inject
	@Symbol(SymbolConstants.SUPPORTED_LOCALES)
	private String locales;
	
	private List<String> model = CollectionFactory.newList();

	@Property
	private String selectedLocale;
	
	@SetupRender
	public void init(){
		String[] supportedLocales = locales.split(",");
		
		model = CollectionFactory.newList(supportedLocales);
		
		if( persistentLocale.get() == null){
		   persistentLocale.set(threadLocale.getLocale());
			
		}
		selectedLocale = persistentLocale.get().getLanguage();
	}
	
	public void onActionFromSubmit(){
		
		Locale locale = LocaleUtils.toLocale(selectedLocale);
		persistentLocale.set(locale);
		
	}
	
	public List<String> getModel() {
		
		return model;
	}
	
}
