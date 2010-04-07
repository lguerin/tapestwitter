package com.tapestwitter.mixins;

import org.apache.tapestry5.ComponentEventCallback;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.MixinAfter;
import org.apache.tapestry5.internal.util.Holder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;

import com.tapestwitter.common.EnumValidation;

@IncludeJavaScriptLibrary("validation.js")
@MixinAfter
@Events(EventConstants.VALIDATE)
public class AjaxValidation
{
	private static final String CLIENT_ID = "clientId";
	
	private static final String PARAM_NAME = "value";

    @Inject
    private ComponentResources resources;
    
    @Environmental
    private RenderSupport renderSupport;
    
    @Inject
    private Request request;
    
    @InjectContainer
    private Field field;

   
    
	void afterRender(MarkupWriter writer)
	{
		
		writer.element("div",
						
				"id", field.getClientId() + "_validation",
				
				"class", "col-help");
		
			writer.element("div",
	
					"id", field.getClientId() + "_info",
	
					"class", "t-label-box info");
			
			writer.write(resources.getPage().getComponentResources().getMessages().get(field.getClientId() + "-info"));
				
			writer.end();
			
			
			writer.element("div",

					"id", field.getClientId() + "_good",

					"class", "t-label-box good");

			writer.write(resources.getPage().getComponentResources().getMessages().get(field.getClientId() + "-good"));
			
			writer.end();

			
			writer.element("div",

					"id", field.getClientId() + "_error",

					"class", "t-label-box error");

			writer.end();
								
		writer.end();
		
		
		Link link = resources.createEventLink("validation");
		
		JSONObject config = new JSONObject();
		config.put("paramName", PARAM_NAME);
		config.put("container", field.getClientId() + "_validation");
		
		renderSupport.addScript(String.format("new Validation('%s', '%s', '%s');", field.getClientId(), link.toAbsoluteURI(), config));
	}
	
	@Log
	Object onValidation(){
		return validation();
	}
	
	@SuppressWarnings("unchecked")
	@Log
	protected Object validation(){
		
		String value = request.getParameter(PARAM_NAME);
		String clientId = request.getParameter(CLIENT_ID);
		final Holder<EnumValidation> itemsHolder = Holder.create();
		
		ComponentEventCallback callback = new ComponentEventCallback()
		{
			public boolean handleResult(Object result)
			{
				EnumValidation valide = (EnumValidation)result;
				itemsHolder.put(valide);
				return true;
			}
		};
            		
        resources.triggerEvent(EventConstants.VALIDATE, new Object[] { value }, callback);
        
        JSONObject response = generateResponse(itemsHolder.get(), clientId);
        
        return response;
	}
	
	@Log
	protected JSONObject generateResponse(Object... arguments)
    {
		EnumValidation result = (EnumValidation)arguments[0];
		String clientId = (String)arguments[1];
		
		Boolean valide = Boolean.FALSE;
		String message = "";
		if(result == EnumValidation.OK){
			valide = Boolean.TRUE;
			
		}else{
			message = resources.getPage().getComponentResources().getMessages().get(clientId + "-error");
			
			if(result == EnumValidation.FORMAT){
				message = resources.getPage().getComponentResources().getMessages().get(clientId + "-format");
			}
		}
		
		
		
		JSONObject config = new JSONObject();
		config.put("clientId", clientId);
		config.put("valide", valide);
		config.put("message", message);
		
		return config;
    }
}
