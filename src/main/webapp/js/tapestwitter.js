 
Tapestry.Initializer.submitOnChange = function(elementId, formId)
{
	$(elementId).observe('change', function(event) {
		
		
		Event.stop(event);
		$(formId).submit();
               
        return false;
		
	});
};
	
	

