package com.tapestwitter.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.FieldValidator;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.corelib.base.AbstractField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.FieldValidatorSource;
import org.apache.tapestry5.services.Request;

/**
 * Field paired with a {@link KaptchaImage} to ensure that the user has provided
 * the correct value.
 * 
 */
@SupportsInformalParameters
public class KaptchaField extends AbstractField
{

	/**
	 * The image output for this field. The image will display a distorted text
	 * string. The user must decode the distorted text and enter the same value.
	 */
	@Parameter(required = true, defaultPrefix = BindingConstants.COMPONENT)
	private KaptchaImage image;

	@Inject
	private Request request;

	@Inject
	private Messages messages;

	@Inject
	private ComponentResources resources;

	@Environmental
	private ValidationTracker validationTracker;

	@Inject
	private FieldValidatorSource fieldValidatorSource;

	@Override
	public boolean isRequired()
	{
		return true;
	}

	@BeginRender
	boolean renderTextField(MarkupWriter writer)
	{

		writer.element("input",

		"type", "password",

		"id", getClientId(),

		"name", getControlName(),

		"value", "");

		resources.renderInformalParameters(writer);

		@SuppressWarnings("rawtypes")
		FieldValidator fieldValidator = fieldValidatorSource.createValidator(this, "required", null);

		fieldValidator.render(writer);

		writer.end();

		return false;
	}

	@Override
	protected void processSubmission(String elementName)
	{

		String userValue = request.getParameter(elementName);

		if (image.getCaptchaText().equals(userValue))
			return;

		validationTracker.recordError(this, messages.get("incorrect-captcha"));
	}

}
