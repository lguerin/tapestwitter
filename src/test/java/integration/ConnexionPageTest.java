/**
 * 
 */
package integration;

import org.junit.Assert;
import org.testng.annotations.Test;

/**
 * Integration tests for connecting a user
 * @author lguerin
 *
 */
public class ConnexionPageTest extends AbstractTapesTwitterIntegrationTest
{
	/**
	 * The default login for the first user
	 */
	private static final String DEFAULT_FIRST_USER_LOGIN = "laurent";

	/**
	 * The default password for the first user
	 */
	private static final String DEFAULT_FIRST_USER_PASSWD = "laurentpass";

	/**
	 * The default login for the second user
	 */
	private static final String DEFAULT_SECOND_USER_LOGIN = "katia";

	/**
	 * The default password for the second user
	 */
	private static final String DEFAULT_SECOND_USER_PASSWD = "katiapass";

	/**
	 * The text for the login link
	 */
	private static final String DEFAULT_LOGIN_TEXT_LINK = "Entrer dans la DEMO";

	/**
	 * The default title on the home page
	 */
	private static final String DEFAULT_HOME_PAGE_TITLE = "TapesTwitter / Accueil";

	/**
	 * The default Welcome index page title
	 */
	private static final String DEFAULT_WELCOME_INDEX_TITLE = "TapesTwitter / Bienvenue";

	@Test(groups = { "integration" })
	public void testConnexionWithOneUser()
	{
		commonTestConnexionUser(DEFAULT_FIRST_USER_LOGIN, DEFAULT_FIRST_USER_PASSWD);
	}

	private void commonTestConnexionUser(String login, String password)
	{
		start(DEFAULT_LOGIN_TEXT_LINK);
		waitForPageToLoad();
		type("j_username", login);
		type("j_password", password);
		click("login_submit");
		waitForPageToLoad();
		checkTitle(DEFAULT_HOME_PAGE_TITLE);
		String loginExpected = getText("rightbar-user-infos-login");
		Assert.assertEquals(loginExpected, login);
		click("link=Déconnexion");
		waitForPageToLoad();
		checkTitle(DEFAULT_WELCOME_INDEX_TITLE);
	}

}
