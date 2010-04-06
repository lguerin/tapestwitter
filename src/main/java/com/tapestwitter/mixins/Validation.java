package com.tapestwitter.mixins;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentEventCallback;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ContentType;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.MixinAfter;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.internal.util.Holder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.Coercion;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.MarkupWriterFactory;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.ResponseRenderer;
import org.apache.tapestry5.util.TextStreamResponse;

import com.tapestwitter.domain.business.UserManager;

@IncludeJavaScriptLibrary("validation.js")
@MixinAfter
public class Validation
{
	private static final String PARAM_NAME = "value";

    @Inject
    private ComponentResources resources;
    
    @Environmental
    private RenderSupport renderSupport;
    
    @Inject
    private Request request;
    
    @Inject 
    private TypeCoercer coercer;
    
    @Inject
    private ResponseRenderer responseRenderer;

    @Inject
    private MarkupWriterFactory factory;
    
	void afterRender(MarkupWriter writer)
	{

		Element element = writer.getElement();
		
		writer.element("div",
						
				"id", element.getAttribute("id")+ "_validation",
				
				"class", "col-help");
		
			writer.element("div",
	
					"id", element.getAttribute("id")+ "_info",
	
					"class", "t-label-box info");
				
			writer.end();
			
			
			writer.element("div",

					"id", element.getAttribute("id")+ "_good",

					"class", "t-label-box good");

			writer.end();

			
			writer.element("div",

					"id", element.getAttribute("id")+ "_error",

					"class", "t-label-box error");

			writer.end();
								
		writer.end();
		
		
		Link link = resources.createEventLink("validation");
		JSONObject config = new JSONObject();
		
		config.put("paramName", PARAM_NAME);
		config.put("container", element.getAttribute("id")+ "_validation");
		
		renderSupport.addScript(String.format("new Validation('%s', '%s', '%s');", element.getAttribute("id"), link.toAbsoluteURI(), config));
	}
	
	JSONObject onValidation(){
		
		String value = request.getParameter(PARAM_NAME);

		final Holder<Boolean> validation = Holder.create();
		
		validation.put(Boolean.TRUE);

        ComponentEventCallback callback = new ComponentEventCallback()
        {
            public boolean handleResult(Object result)
            {
            	Boolean resultValide = coercer.coerce(result, Boolean.class);
            	validation.put(resultValide);

                return true;
            }
        };
        		
        resources.triggerEvent("coco", new Object[] { value }, callback);
       
            

        JSONObject response = generateResponse(validation);
        
        return response;
	}
	
	protected JSONObject generateResponse(Holder<Boolean> validation)
    {
		JSONObject config = new JSONObject();
		
		config.put("valide", validation.get());
		
		return config;
    }
}
