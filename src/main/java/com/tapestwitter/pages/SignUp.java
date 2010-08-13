package com.tapestwitter.pages;

import com.tapestwitter.common.EnumValidation;
import com.tapestwitter.common.TapesTwitterEventConstants;
import com.tapestwitter.domain.business.UserManager;
import com.tapestwitter.domain.exception.BusinessException;
import com.tapestwitter.domain.exception.UserAlreadyExistsException;
import com.tapestwitter.domain.model.User;
import com.tapestwitter.services.security.TapestwitterSecurityContext;
import com.tapestwitter.util.ValidationUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Mixins;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * This page creates a new user and connect him to Tapestwitter
 * 
 * @author karesti
 */
public class SignUp
{

    private static final Logger logger = LoggerFactory.getLogger(SignUp.class);

    @Property
    @Persist(PersistenceConstants.FLASH)
    private String fullName;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private String login;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private String password;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private String email;

    @InjectComponent
    private Form signupForm;

    @SuppressWarnings("unused")
    @Component(id = "fullName", parameters = "AjaxValidation.etat=valideFullName")
    @Mixins("AjaxValidation")
    private TextField fullNameText;

    @SuppressWarnings("unused")
    @Component(id = "login", parameters =
    { "AjaxValidation.etat=valideLogin", "AjaxValidation.whenValidate=keyup", "AjaxValidation.timer=1000" })
    @Mixins("AjaxValidation")
    private TextField loginText;

    @SuppressWarnings("unused")
    @Component(id = "password", parameters = "AjaxValidation.etat=validePassword")
    @Mixins("AjaxValidation")
    private PasswordField passwordText;

    @SuppressWarnings("unused")
    @Component(id = "email", parameters = "AjaxValidation.etat=valideEmail")
    @Mixins("AjaxValidation")
    private TextField emailText;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private EnumValidation valideFullName;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private EnumValidation valideLogin;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private EnumValidation valideEmail;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private EnumValidation validePassword;

    @Inject
    private UserManager userManager;

    @Inject
    private TapestwitterSecurityContext securityCtx;

    @InjectPage
    private HomePage homePage;

    /**
     * Ajax event that validates the login field
     * 
     * @param login
     * @return Corresponding {@link EnumValidation}
     */
    @Log
    @OnEvent(value = TapesTwitterEventConstants.AJAX_VALIDATE, component = "login")
    public EnumValidation ajaxLoginValidation(String userLogin)
    {
        validateLogin(userLogin);
        return valideLogin;

    }

    /**
     * Ajax event that validates the fullName field
     * 
     * @param fullName
     * @return Corresponding {@link EnumValidation}
     */
    @Log
    @OnEvent(value = TapesTwitterEventConstants.AJAX_VALIDATE, component = "fullName")
    public EnumValidation ajaxFullNameValidation(String fullName)
    {
        validateFullName(fullName);
        return valideFullName;

    }

    /**
     * Ajax event that validates the password field
     * 
     * @param password
     * @return Corresponding {@link EnumValidation}
     */
    @Log
    @OnEvent(value = TapesTwitterEventConstants.AJAX_VALIDATE, component = "password")
    public EnumValidation ajaxPasswordValidation(String password)
    {
        validatePassword(password);
        return validePassword;

    }

    /**
     * Ajax event that validates the email field
     * 
     * @param email
     * @return Corresponding {@link EnumValidation}
     */
    @Log
    @OnEvent(value = TapesTwitterEventConstants.AJAX_VALIDATE, component = "email")
    public EnumValidation ajaxEmailValidation(String email)
    {
        validationEmail(email);
        return valideEmail;

    }

    /**
     * This method is executed when the user submits the form. It validates the form. If validation
     * passes tapestry will execute "onSuccess" event
     */
    @OnEvent(value = EventConstants.VALIDATE, component = "signupForm")
    public void onValidate()
    {
        validationEmail(email);
        validateFullName(fullName);
        validateLogin(login);
        validatePassword(password);

        boolean result = EnumValidation.OK.equals(valideEmail);
        result = result && EnumValidation.OK.equals(valideFullName);
        result = result && EnumValidation.OK.equals(valideLogin);
        result = result && EnumValidation.OK.equals(validePassword);

        if (!result)
        {
            signupForm.recordError("Validation not succeded");
        }

    }

    /**
     * This method is executed when the user submits the form and the validation is ok Creates a new
     * user Connects the user Redirects to HomePage
     * 
     * @return HomePage if success SignUp if error (this)
     */
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
        }
        catch (UserAlreadyExistsException e)
        {
            logger.error("User not created", e);
            signupForm.recordError("Validation not succeded");
            return this;
        }
        catch (BusinessException e)
        {
            logger.error("User not created", e);
            signupForm.recordError("Validation not succeded");
            return this;
        }
        securityCtx.logIn(user);
        return homePage;
    }

    /**
     * Validates the email
     * 
     * @param email
     */
    private void validationEmail(String email)
    {

        valideEmail = EnumValidation.INVALIDE;

        if (StringUtils.isEmpty(email))
        {
            valideEmail = EnumValidation.EMPTY;

        }
        else if (!ValidationUtils.isEmail(email))
        {
            valideEmail = EnumValidation.FORMAT;

        }
        else
        {
            Boolean result = userManager.isAvailableEmail(email);

            if (result)
            {
                valideEmail = EnumValidation.OK;
            }

        }
    }

    /**
     * Validates the password
     * 
     * @param password
     */
    private void validatePassword(String password)
    {
        validePassword = EnumValidation.EMPTY;

        if (StringUtils.isEmpty(password))
        {
            validePassword = EnumValidation.EMPTY;
        }
        else if (StringUtils.isEmpty(password.trim()) || password.trim().length() < 6)
        {
            validePassword = EnumValidation.FORMAT;

        }
        else
        {
            validePassword = EnumValidation.OK;
        }
    }

    /**
     * Validates the fullName
     * 
     * @param fullName
     */
    private void validateFullName(String fullName)
    {

        valideFullName = EnumValidation.EMPTY;

        if (StringUtils.isEmpty(fullName))
        {
            valideFullName = EnumValidation.EMPTY;
        }
        else if (StringUtils.isEmpty(fullName.trim()))
        {
            valideFullName = EnumValidation.INVALIDE;

        }
        else
        {
            valideFullName = EnumValidation.OK;
        }
    }

    /**
     * Validates login
     * 
     * @param userLogin
     */
    private void validateLogin(String userLogin)
    {
        valideLogin = EnumValidation.INVALIDE;

        if (StringUtils.isEmpty(userLogin))
        {
            valideLogin = EnumValidation.EMPTY;
        }
        else
        {

            Boolean result = userManager.isAvailableName(userLogin);

            if (result)
            {
                valideLogin = EnumValidation.OK;
            }
        }
    }

}
