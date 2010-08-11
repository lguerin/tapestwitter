package com.tapestwitter.pages;

import com.tapestwitter.domain.business.UserManager;
import com.tapestwitter.domain.model.User;
import com.tapestwitter.services.security.TapestwitterSecurityContext;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Start page of application tapestwitter.
 */
public class Index
{
    @InjectPage
    private HomePage homePage;

    @InjectPage
    private SearchErrorPage searchErrorPage;

    @Inject
    private TapestwitterSecurityContext securityCtx;

    @Inject
    private UserManager userManager;

    private User user;

    public Object onActivate(String userName)
    {
        user = userManager.findByUsername(userName);

        if (user == null) { return searchErrorPage; }
        if (securityCtx.isLoggedIn())
        {
            User userLogged = securityCtx.getUser();
            if (user.getLogin().equals(userLogged.getLogin())) { return homePage; }
        }

        return null;
    }

    /**
     * Redirige l'utilisateur sur la page d'accueil si celui-ci est
     * deja authentifie.
     * 
     * @return Page d'accueil si authentifie
     */
    @SuppressWarnings("unused")
    @OnEvent(value = EventConstants.ACTIVATE)
    private Object redirectToHomePage()
    {
        // Tester par rapport a l'authentification
        boolean isLoggedIn = securityCtx.isLoggedIn();
        if (isLoggedIn) { return homePage; }
        return null;
    }
}
