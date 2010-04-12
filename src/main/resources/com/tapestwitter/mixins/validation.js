var validationTimerId = null;

var Validation = Class.create({
  
	initialize: function(id, link, config) {
	var configuration = config.evalJSON();
	this.element = $(id);
    this.link = link;
    this.timer = new Number(configuration.timer);
    this.currentValue = $F(this.element);
    this.validationEvent = configuration.validateEvent;
    this.etat = configuration.etat;
    
    Event.observe(this.element,  this.validationEvent , this.validate.bindAsEventListener(this));
    Event.observe(this.element, 'focus', this.onFocus.bindAsEventListener(this));
    Event.observe(this.element, 'blur', this.onBlur.bindAsEventListener(this));
    
    if(this.etat != "INFO"){
    	this.init();
    }
  },
  
  validate: function() {
	 
	  if($F(this.element)== "" || (this.currentValue != $F(this.element))){
		  
		  if( this.validationEvent == 'keyup'){
			  $(this.element.id + "_info_message").addClassName("hide");
			  $(this.element.id + "_avail_check_indicator").removeClassName("hide");
			  $(this.element.id + "_info").addClassName('show');
			  			  
		  }else{
			  $(this.element.id + "_info").removeClassName('show');
		  }
		  	 
		  $(this.element.id + "_good").removeClassName('show');
		  $(this.element.id + "_error").removeClassName('show');
		  	 
		  if (validationTimerId != null){
			  window.clearTimeout( validationTimerId );
		  }
		  validationTimerId = setTimeout(this.sendRequest.bind(this), this.timer);
		  this.currentValue =  $F(this.element);
	  }
	  
  },
  
  onBlur : function(){
	  $(this.element.id + "_info").removeClassName('show');
  },
  
  onFocus : function(){
	  if(! ($(this.element.id + "_good").hasClassName('show') || $(this.element.id + "_error").hasClassName('show'))){
		 
		  $(this.element.id + "_info_message").removeClassName("hide");
		  $(this.element.id + "_avail_check_indicator").addClassName("hide");
		  $(this.element.id + "_info").addClassName('show');
	  }
  },

  init : function(){
	  var blockName = String.interpret(this.element.id) + "_error";
	  if(this.etat == 'OK'){
		  blockName = String.interpret(this.element.id) + "_good";;
	  }
	  $(blockName).addClassName('show');
  },
  
  sendRequest : function() {
		new Ajax.Request(this.link, { method:'get',
			  parameters: {"value" : $F(this.element), "clientId" : this.element.id},
			  
			  onSuccess: function(response){
				  var json = response.responseJSON;
				  var blockName = String.interpret(json.clientId) + "_info";;
				  $(blockName).removeClassName('show');
				  
				  if (json.result == 'OK'){
					  blockName = String.interpret(json.clientId) + "_good";
								  
				  }else{
					  blockName = String.interpret(json.clientId) + "_error";
					  $(blockName).update(json.message);
				  }
				  $(json.clientId + "_info").removeClassName('show');
				  $(blockName).addClassName('show');
			    }
			  
			  });
	}
	
});


