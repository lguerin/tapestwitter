package com.tapestwitter.pages;

import com.tapestwitter.domain.business.UserManager;
import com.tapestwitter.domain.exception.CreateAuthorityException;
import com.tapestwitter.domain.exception.CreateUserException;
import com.tapestwitter.domain.model.User;
import com.tapestwitter.services.security.TapestwitterSecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * This page creates one user
 * 
 * @author karesti
 *
 */
public class SignUp
{

	private static final Logger logger = LoggerFactory.getLogger(SignUp.class);

	@Property
	private String fullName;

	@Property
	private String login;

	@Property
	private String password;

	@Property
	private String email;

	@InjectComponent
	private Form signupForm;

	@SuppressWarnings("unused")
	@InjectComponent(value = "fullName")
	private TextField fullNameText;

	@SuppressWarnings("unused")
	@InjectComponent(value = "login")
	private TextField loginText;

	@SuppressWarnings("unused")
	@InjectComponent(value = "password")
	private PasswordField passwordText;

	@SuppressWarnings("unused")
	@InjectComponent(value = "email")
	private TextField emailText;

	@Inject
	private UserManager userManager;

	@Inject
	private TapestwitterSecurityContext securityCtx;

	@InjectPage
	private SignUpSuccess signSuccess;

	
	
	public boolean onCocoFromLogin(String userLogin)
	{
		Boolean result = userManager.isAvailableName(userLogin);
		
		return result;
	}
	
	@OnEvent(value = EventConstants.VALIDATE_FORM, component = "signupForm")
	public void onValidate()
	{
		if (StringUtils.isEmpty(fullName) || StringUtils.isEmpty(login) || StringUtils.isEmpty(password) || StringUtils.isEmpty(email))
		{
			signupForm.recordError("Problemo!!!");
		}
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "signupForm")
	public Object onSuccess()
	{
		User user = new User();
		user.setFullName(fullName);
		user.setLogin(login);
		user.setPassword(password);
		user.setEmail(email);
		try
		{
			userManager.addUser(user);
			securityCtx.logIn(user);
		}
		catch (CreateUserException e)
		{
			logger.error("User not created", e);
			signupForm.recordError("Problemo!!!");
			return this;
		}
		catch (CreateAuthorityException e)
		{
			logger.error("User not created", e);
			signupForm.recordError("Problemo!!!");
			return this;
		}

		signSuccess.setEmail(email);

		return signSuccess;
	}
}
