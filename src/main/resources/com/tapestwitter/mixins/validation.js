var Validation = Class.create({
  
	initialize: function(id, link, config) {
    this.id = $(id);
    this.link = link;
       
    Event.observe(this.id, 'keyup', this.onKeyUp.bindAsEventListener(this));
  
  },
  
  onKeyUp: function() {
	  
	  new Ajax.Request(this.link, { method:'get',
		  parameters: {"value" : $F(this.id)},
		  onSuccess: function(response){
			  var json = response.responseJSON;
			  alert(json.valide);
			     
		    }
		  });

  }
    
});
