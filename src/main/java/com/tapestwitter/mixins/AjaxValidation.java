package com.tapestwitter.mixins;

import com.tapestwitter.common.EnumValidation;
import com.tapestwitter.common.TapesTwitterEventConstants;
import com.tapestwitter.util.ValidationUtils;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentEventCallback;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.internal.util.Holder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library="validation.js")
@Events(TapesTwitterEventConstants.AJAX_VALIDATE)
public class AjaxValidation
{
	private static final String BLUR = "blur";
	
	private static final String DEFAULT_TIMER = "0";
	
	private static final String CLIENT_ID = "clientId";
	
	private static final String PARAM_NAME = "value";

	@Parameter(required=false, allowNull=true, defaultPrefix=BindingConstants.LITERAL, value=BLUR)
	private String whenValidate;
	
	@Parameter(required=false, allowNull=true, defaultPrefix=BindingConstants.PROP)
	private EnumValidation etat;
	
	@Parameter(required=false, allowNull=true, defaultPrefix=BindingConstants.LITERAL, value=DEFAULT_TIMER)
	private String timer;
	
    @Inject
    private ComponentResources resources;
    
    @Environmental
    private JavaScriptSupport javaScriptSupport;    
    
    @Inject
    private Request request;
    
    @InjectContainer
    private Field field;

    @Inject
    @Path("${tapestry.spacer-image}")
    private Asset spacerImage;
    
       
    @BeginRender
    void begin(MarkupWriter writer){
    
    	writer.element("div",
				
					"class", "col-field");
    }
    
    
    void afterRender(MarkupWriter writer)
	{
		
    	if(etat == null){
			etat = EnumValidation.INFO;
		}
    	
    		writer.end();
    		
    		writer.element("div",
    				
    				"id", field.getClientId() + "_validation",
    				
    				"class", "col-help");
    		
				writer.element("div",
		
						"id", field.getClientId() + "_info",
		
						"class", "t-label-box info");
				
					writer.element("span",
							
							"id", field.getClientId() + "_info_message",
			
							"class", "");
					
					
					writeMessage(writer, EnumValidation.INFO, field.getClientId());
					
					writer.end();
				
					writer.element("span",
							
							"id", field.getClientId() + "_avail_check_indicator",
			
							"class", "hide");
					
					writer.element("img",

		                       "src", spacerImage.toClientURL(),

		                       "class", "t-ajaxvalidation-icon",

		                       "alt", "");
					
					writer.end();
					writer.write(resources.getPage().getComponentResources().getMessages().get(field.getClientId() + "-check"));
					
					writer.end();
					
				writer.end();
			
			
				writer.element("div",
	
						"id", field.getClientId() + "_good",
	
						"class", "t-label-box good");
	
				writeMessage(writer, EnumValidation.OK, field.getClientId());
				
				writer.end();

			
				writer.element("div",
	
						"id", field.getClientId() + "_error",
	
						"class", "t-label-box error");
				
				if(ValidationUtils.isError(etat)){
					writeMessage(writer, etat, field.getClientId());
				}
				
								
				writer.end();
								
			writer.end();
		
		
		
		JSONObject config = new JSONObject();
	    config.put("validateEvent", whenValidate);
	    config.put("etat", etat.name());
	    config.put("timer", timer);	    
	    
		Link link = resources.createEventLink("validation");
		
		javaScriptSupport.addScript(String.format("new Validation('%s', '%s', '%s');", field.getClientId(), link.toAbsoluteURI(), config.toString(true)));
		
	}
	
    
		
	@Log
	Object onValidation(){
		return validation();
	}
	
	@Log
	protected Object validation(){
		
		String value = request.getParameter(PARAM_NAME);
		String clientId = request.getParameter(CLIENT_ID);
		final Holder<EnumValidation> itemsHolder = Holder.create();
		
		ComponentEventCallback<EnumValidation> callback = new ComponentEventCallback<EnumValidation>()
		{
			public boolean handleResult(EnumValidation result)
			{
				itemsHolder.put(result);
				return true;
			}
		};
            		
        resources.triggerEvent(TapesTwitterEventConstants.AJAX_VALIDATE, new Object[] { value }, callback);
        
        JSONObject response = generateResponse(itemsHolder.get(), clientId);
        
        return response;
	}
	
	@Log
	protected JSONObject generateResponse(Object... arguments)
    {
		EnumValidation result = (EnumValidation)arguments[0];
		String clientId = (String)arguments[1];
		
		String message = null;
		
		if(ValidationUtils.isError(result)){
			message = ValidationUtils.getLabel(clientId, result, resources.getPage().getComponentResources().getMessages());
		}
			
		JSONObject config = new JSONObject();
		config.put("clientId", clientId);
		config.put("result", result.name());
		
		if(message != null){
			config.put("message", message);
		}
		
		
		return config;
    }
	
	private void writeMessage(MarkupWriter writer, EnumValidation enumValue, String clientId){
		
		String message = ValidationUtils.getLabel(clientId, 
				   enumValue, 
				   resources.getPage().getComponentResources().getMessages());
		
		writer.write(message);
	}
	
}
