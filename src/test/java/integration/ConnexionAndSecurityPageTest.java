package integration;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Integration tests for connexion and security access.
 * 
 * @author lguerin
 */
@Test(groups = "integration")
public class ConnexionAndSecurityPageTest extends AbstractTapesTwitterIntegrationTest
{
    /**
     * The default login for the second user
     */
    private static final String DEFAULT_SECOND_USER_LOGIN = "katia";

    /**
     * The default login for the second user with administrator role
     */
    private static final String DEFAULT_SECOND_USER_LOGIN_ADMIN = "Administrator (katia)";

    /**
     * The default password for the second user
     */
    private static final String DEFAULT_SECOND_USER_PASSWD = "katiapass";

    /**
     * Title for the login page
     */
    @SuppressWarnings("unused")
    private static final String LOGIN_PAGE_TITLE = "TapesTwitter / Login";

    @Test(groups = "integration")
    public void testConnexionWithOneUser()
    {
        loginUser(DEFAULT_FIRST_USER_LOGIN, DEFAULT_FIRST_USER_PASSWD);

        String loginExpected = getText("rightbar-user-infos-login");
        Assert.assertEquals(loginExpected, DEFAULT_FIRST_USER_LOGIN);
        logoutUser();

    }

    @Test(groups = "integration")
    public void testConnexionWithTwoDifferentUsers()
    {
        // First User
        loginUser(DEFAULT_FIRST_USER_LOGIN, DEFAULT_FIRST_USER_PASSWD);

        String loginExpected = getText("rightbar-user-infos-login");
        Assert.assertEquals(loginExpected, DEFAULT_FIRST_USER_LOGIN);
        logoutUser();

        // Second User
        loginUser(DEFAULT_SECOND_USER_LOGIN, DEFAULT_SECOND_USER_PASSWD);

        loginExpected = getText("rightbar-user-infos-login");
        Assert.assertEquals(loginExpected, DEFAULT_SECOND_USER_LOGIN_ADMIN);
        logoutUser();

    }

    @Test(groups = "integration")
    public void testAccesToProtectedHomePage()
    {
        // anonymous
        open("homepage");

        // log with wrong passwd
        loginUser(DEFAULT_FIRST_USER_LOGIN, DEFAULT_SECOND_USER_PASSWD);

        // log with good passwd
        loginUser(DEFAULT_FIRST_USER_LOGIN, DEFAULT_FIRST_USER_PASSWD);

        // logout
        logoutUser();

    }
}
