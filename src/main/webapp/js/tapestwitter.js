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
 * Constructor 
 */
Tapestwitter.FloatMenu = function(id, target) {
	
	this.id = id;
	
	/**
	 * The target element to display / hide
	 */
	this.target = target;
};

Tapestwitter.FloatMenu.CSS_OPENED = "opened";
Tapestwitter.FloatMenu.CSS_CLOSED = "closed";

/**
 * Show the menu item
 * @param id the id of the item to show
 */
Tapestwitter.FloatMenu.prototype.open = function(id){
	var item = YAHOO.util.Dom.get(id);
	YAHOO.util.Dom.setStyle(item, "visibility", "visible");
	YAHOO.util.Dom.removeClass(item, Tapestwitter.FloatMenu.CSS_CLOSED);
	YAHOO.util.Dom.addClass(item, Tapestwitter.FloatMenu.CSS_OPENED);
};


/**
 * Hides the menu item
 * @param id the id of the item to hide
 */
Tapestwitter.FloatMenu.prototype.close = function(id){
	YAHOO.util.Dom.removeClass(id, Tapestwitter.FloatMenu.CSS_OPENED);
	YAHOO.util.Dom.addClass(id, Tapestwitter.FloatMenu.CSS_CLOSED);
};


/**
 * Determines if an item is opened
 * @param id the id of the item
 * @return boolean
 */
Tapestwitter.FloatMenu.prototype.isOpened = function(id) {
	return YAHOO.util.Dom.hasClass(id, Tapestwitter.FloatMenu.CSS_OPENED);
};

/**
 * Click handler on an item
 * @param e the event
 */
Tapestwitter.FloatMenu.prototype._click = function(e) {
	var target = this.target;
	if (this.isOpened(target)) 
	{
		this.close(target);
	}
	else 
	{
		this.open(target);
	}
};

Tapestry.Initializer.initFloatMenu = function(args) {	
	var menu = new Tapestwitter.FloatMenu(args.id, args.target);
	YAHOO.util.Event.addListener(menu.id, "click", Tapestwitter.FloatMenu.prototype._click, menu, true);
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
 * @param data The JSON data to use
 */
Tapestry.Initializer.rollingItemsBuilder = function(data){
	var rollingItems = new Tapestwitter.RollingItems(data.id, data.height, data.duration, data.period);
	var items = rollingItems.prepareItems(rollingItems.id, rollingItems.height);
	rollingItems.triggerAnimation(rollingItems.id, rollingItems.count, items, rollingItems.period, rollingItems.height, rollingItems.duration);
};

/** 
 * Trends Component
 */

/** 
 * Constructor
 */
Tapestwitter.Trends = function(id, height, period){

	/**
	 * Component Id
	 */
	this.id = id;
			
	/**
	 * Height of the box, in pixel
	 */
	this. height = height;
	
	/**
	 * The speed, in millisecond
	 */
	this.period = period;
	
	/**
	 * The initial position
	 */
	this.initialPosition = 0;
	
	/**
	 * The current position
	 */
	this.currentPosition = 0;
};


/**
 * Trigger the trends animation
 */
Tapestwitter.Trends.prototype.triggerAnimation = function(trends){
	setInterval(function() {Tapestwitter.Trends.prototype._animateTrends(trends); }, trends.period);
};

Tapestwitter.Trends.prototype._animateTrends = function(trends){
	var target = YAHOO.util.Dom.get(trends.id);
	// Get the region of the target element
	var elemRegion = YAHOO.util.Dom.getRegion(target);
	// Work out length of top side
	var rightSide = elemRegion.right;
	var leftSide = elemRegion.left;
	var length = rightSide - leftSide;
	if(trends.currentPosition < (- length))
	{
		trends.currentPosition = trends.initialPosition;
	} 
	else 
	{
		trends.currentPosition += -1;
    }
	YAHOO.util.Dom.setStyle(target, "left", trends.currentPosition + "px");
};

/**
 * Initialization of Trends component on DOM available
 * @param data The JSON data to use
 */
Tapestry.Initializer.trendsBuilder = function(data){
	var trends = new Tapestwitter.Trends(data.id, data.height, data.period);
	// Fix the height of the container
	YAHOO.util.Dom.setStyle(trends.id, "height", trends.height + "px");
	// Trigger animation !
	trends.initialPosition = YAHOO.util.Dom.getX(trends.id);
	trends.currentPosition = trends.initialPosition;
	trends.triggerAnimation(trends);
};