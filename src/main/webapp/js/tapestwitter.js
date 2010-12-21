/** Global item repository */
if(!Tapestwitter){
	var Tapestwitter = {};
} 


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
	
/** 
 * RollingItems Component
 */

/** 
 * Constructor
 */
Tapestwitter.RollingItems = function(id, height, duration, period){

	/**
	 * Component Id
	 */
	this.id = id;
	
	/**
	 * Duration of animation
	 */
	this.duration = duration;

	/**
	 * Counter
	 */
	this.count = 0;
		
	/**
	 * Height of the box, in pixel
	 */
	this. height = height;
	
	/**
	 * The rolling period in millisecond
	 */
	this.period = period;
};

Tapestwitter.RollingItems.prototype.getRollingListById = function(id){
	return "rolling-list_" + id;
};

/**
 * Prepare the items before rolling
 * @return items	The items to animate
 */
Tapestwitter.RollingItems.prototype.prepareItems = function(id, boxHeight){	
	var items = new Array();
	
	// Hide Rolling List
	YAHOO.util.Dom.setStyle(this.getRollingListById(id), "display", "none");

	// Fix the size of the rolling box
	YAHOO.util.Dom.setStyle(id, "height", boxHeight + "px");
	YAHOO.util.Dom.setStyle(id, "overflow", "hidden");
	
	// Remove all children and store into array
	var children = YAHOO.util.Dom.getChildren(this.getRollingListById(id));
	for (x = 0; x < children.length; x++) {
		children[x].parentNode.removeChild(children[x]);
		items.push(children[x]);
	}
	items.reverse();
	return items;
};

Tapestwitter.RollingItems.prototype._animateRollingList = function(id, count, items, boxHeight, duration){
	// Get rolling list element
	var rollingList = YAHOO.util.Dom.get(this.getRollingListById(id));
	// Show container
	YAHOO.util.Dom.setStyle(this.getRollingListById(id), "display", "block");
	YAHOO.util.Dom.setStyle(this.getRollingListById(id), "top", "0");

	// Add incomming item
	var batchSize = items.length;
	var index = (count - 1) % batchSize;
	var item = items[index];
	var isFirstChild = (YAHOO.util.Dom.getFirstChild(rollingList) == null);
	if (isFirstChild)
	{
		rollingList.appendChild(item);
	}
	else
	{
		YAHOO.util.Dom.insertBefore(item, YAHOO.util.Dom.getFirstChild(rollingList));
	}

	// The height of the current item
	height = item.offsetHeight;

	var move = new YAHOO.util.Anim(rollingList, {
		top: {to: height, unit: 'px'}
	}, duration, YAHOO.util.Easing.easeOut);
	
	if (YAHOO.util.Dom.getChildren(this.getRollingListById(id)).length > 1)
	{		
		YAHOO.util.Dom.setStyle(item, "display", "none");
		move.animate();
	}

	move.onComplete.subscribe(function() {
		// Display incoming item with fade effect
		YAHOO.util.Dom.setStyle(item, 'opacity', .1);
		var fadeIn = new YAHOO.util.Anim(item, {
		         opacity: {from: .1, to: 1 }
		         }, 1);
		fadeIn.animate();
	
		// Display / Hide elements
		YAHOO.util.Dom.setStyle(rollingList, "display", "none");
		YAHOO.util.Dom.setStyle(rollingList, "top", "0");
		YAHOO.util.Dom.setStyle(item, "display", "block");	
		YAHOO.util.Dom.setStyle(rollingList, "display", "block");
	});
	
	// Clean items if necessary
	var lastChild = YAHOO.util.Dom.getLastChild(rollingList);
	var lastChildHeight = lastChild.offsetHeight;
	var cumulativeHeight = rollingList.offsetHeight - lastChildHeight;
	if (cumulativeHeight >= boxHeight)
	{
		rollingList.removeChild(lastChild);
	}
};

/**
 * Trigger the items animation
 * @return items	The items to animate
 */
Tapestwitter.RollingItems.prototype.triggerAnimation = function(id, count, items, period, duration){
	// 	Periodical rolling animation
	setInterval(function() {count = count + 1; Tapestwitter.RollingItems.prototype._animateRollingList(id, count, items, duration); }, period);
};

/**
 * Initialization of RollingItems component on DOM available
 * @param data The JSON data tu use
 */
Tapestry.Initializer.rollingItemsBuilder = function(data){
	var rollingItems = new Tapestwitter.RollingItems(data.id, data.height, data.duration, data.period);
	var items = rollingItems.prepareItems(rollingItems.id, rollingItems.height);
	rollingItems.triggerAnimation(rollingItems.id, rollingItems.count, items, rollingItems.period, rollingItems.height, rollingItems.duration);
};