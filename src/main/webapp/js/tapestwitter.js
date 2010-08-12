 
Tapestry.Initializer.submitOnChange = function(args)
{
	var elementId = args.elementId;
	var formId = args.formId;
	$(elementId).observe('change', function(event) {
		
		
		Event.stop(event);
		$(formId).submit();
               
        return false;
		
	});
};
	
	

