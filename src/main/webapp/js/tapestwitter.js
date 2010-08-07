 
Tapestry.Initializer.submitOnChange = function()
{
	var args = arguments[0].args;
	var elementId = args[0];
	var formId = args[1];
	$(elementId).observe('change', function(event) {
		
		
		Event.stop(event);
		$(formId).submit();
               
        return false;
		
	});
};
	
	

