// A class that implement the "Twitter-like" char's counter.
// @author Laurent Guerin
var TextCounter = Class.create();
TextCounter.prototype = {
		initialize: function(textareaId, elementId, maxlength, warningLimit, warningStyle)
		{
			// The textarea to inspect
			this.textarea = $(textareaId);
			// The target HTML element to update
			this.element = $(elementId);
			
			// Other params
			this.maxlength = maxlength;
			this.warningLimit = warningLimit;
			this.warningStyle = warningStyle;
			this.element.update(this.maxlength);			
			
			// Events
			Event.observe($(textareaId), 'keyup', this.updateTextCounter.bindAsEventListener(this));			
			Event.observe($(textareaId), 'keydown', this.updateTextCounter.bindAsEventListener(this));
		},
		
		updateTextCounter: function(e)
		{			
			var currentCount = this.maxlength - this.textarea.value.length;
			var isLimit = false;
			if (!this.warningLimit.empty() && currentCount <= this.warningLimit)
			{	
				if (!this.warningStyle.empty())
				{
					isLimit = true;
					this.element.addClassName(this.warningStyle);
				}
			}
			if (isLimit && currentCount >= this.warningLimit)
			{
				isLimit = false;
				this.element.removeClassName(this.warningStyle);
			}
			this.element.update(currentCount);
		}
}