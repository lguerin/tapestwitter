var validationTimerId = null;

var Validation = Class.create({
  
	initialize: function(id, link, config) {
    this.id = $(id);
    this.link = link;
    Event.observe(this.id, 'keyup', this.onKeyUp.bindAsEventListener(this));
    Event.observe(this.id, 'blur', this.onBlur.bindAsEventListener(this));
    Event.observe(this.id, 'focus', this.onFocus.bindAsEventListener(this));
  },
  
  onKeyUp: function() {
	 
	  $(this.id.id + "_good").removeClassName('show');
	  $(this.id.id + "_error").removeClassName('show');
	 	 
	  if (validationTimerId != null){
		  window.clearTimeout( validationTimerId );
	  }
	  validationTimerId = setTimeout(this.sendRequest.bind(this), 1000);
  },
  onFocus : function(){
	  if(! $(this.id.id + "_good").hasClassName('show')){
		  	  
		  $(this.id.id + "_info").addClassName('show');
	  }
  },
  onBlur : function(){
	  $(this.id.id + "_info").removeClassName('show');
  },
  
  sendRequest : function() {
		new Ajax.Request(this.link, { method:'get',
			  parameters: {"value" : $F(this.id), "clientId" : this.id.id},
			  
			  onSuccess: function(response){
				  var json = response.responseJSON;
				  var blockName = String.interpret(json.clientId) + "_info";;
				  $(blockName).addClassName('hide');
				  if (json.valide){
					  blockName = String.interpret(json.clientId) + "_good";
								  
				  }else{
					  blockName = String.interpret(json.clientId) + "_error";
					  $(blockName).update(json.message);
				  }
				 
				  $(blockName).addClassName('show');
			    }
			  
			  });
		
		
	}
	

		


     
});


