/**
 * 
 */
package integration;

import org.testng.Assert;

import org.apache.tapestry5.test.AbstractIntegrationTestSuite;

/**
 * Abstract class for integration tests into TapesTwitter.
 * 
 * @author lguerin
 */
abstract class AbstractTapesTwitterIntegrationTest extends AbstractIntegrationTestSuite
{
    /**
     * Default browser used to launch tests.
     */
    private static String defaultBrowser;

    /**
     * The text for the login link
     */
    protected static final String DEFAULT_LOGIN_TEXT_LINK = "Entrer dans la DEMO Tapestry";

    /**
     * The default title on the home page
     */
    protected static final String DEFAULT_HOME_PAGE_TITLE = "TapesTwitter / Accueil";

    /**
     * The default Welcome index page title
     */
    protected static final String DEFAULT_PAGE_INDEX_TITLE = "TapesTwitter / Index";

    /**
     * The default login for the first user
     */
    protected static final String DEFAULT_FIRST_USER_LOGIN = "laurent";

    /**
     * The default password for the first user
     */
    protected static final String DEFAULT_FIRST_USER_PASSWD = "laurentpass";

    // Set a default browser in function of the OS.
    static
    {
        String os = System.getProperty("os.name");

        if (os.contains("Windows"))
        {
            defaultBrowser = "*googlechrome";
        }
        else if (os.contains("Mac OS"))
        {
            defaultBrowser = "*safari";
        }
        else
        {
            defaultBrowser = "*firefox";
        }
    }

    protected AbstractTapesTwitterIntegrationTest()
    {
        super("src/main/webapp", defaultBrowser);
    }

    protected AbstractTapesTwitterIntegrationTest(String path)
    {
        super(path, defaultBrowser);
    }

    protected void checkTitle(String expected)
    {
        Assert.assertTrue(getTitle().equals(expected));
    }

    protected void loginUser(String login, String password)
    {
        open(BASE_URL);
        waitForPageToLoad();
        click("link=" + DEFAULT_LOGIN_TEXT_LINK);
        waitForPageToLoad();
        type("j_username", login);
        type("j_password", password);
        click("login_submit");
        waitForPageToLoad();
    }

    protected void logoutUser()
    {
        click("link-deconnexion");
        waitForPageToLoad();
        waitForPageToLoad();
    }
}
