/**
 * 
 */
package integration;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Integration tests for the home page
 * 
 * @author lguerin
 */
public class HomePageTest extends AbstractTapesTwitterIntegrationTest
{

    /**
     * Number of tweets
     */
    private int nbTweets;

    /**
     * Authenticate a user
     */
    @Test(groups =
    { "integration" })
    public void login()
    {
        loginUser(DEFAULT_FIRST_USER_LOGIN, DEFAULT_FIRST_USER_PASSWD);
        checkTitle(DEFAULT_HOME_PAGE_TITLE);
        nbTweets = this.getXpathCount("//div[@id='comp-tweet-infos']").intValue();
    }

    /**
     * Test publishing a Tweet
     */
    @Test(groups =
    { "integration" }, dependsOnMethods =
    { "login" })
    public void checkPublishTweet()
    {
        // Set tweet message "aaa"
        type("tweetContentInput", "aaa");
        click("submit");
        waitForPageToLoad();
        int expectedNbTweets = this.getXpathCount("//div[@id='comp-tweet-infos']").intValue();
        // check the number of tweets
        Assert.assertEquals(nbTweets + 1, expectedNbTweets);
    }

    /**
     * Test on TextCounter component
     */
    @Test(groups =
    { "integration" }, dependsOnMethods =
    { "login" })
    public void checkTextCounter()
    {
        loginUser(DEFAULT_FIRST_USER_LOGIN, DEFAULT_FIRST_USER_PASSWD);
        // Get the initial value of the counter
        Integer initCount = Integer.valueOf(getText("publish-tweet-counter"));
        String msg = "aa";
        // Set tweet message
        type("tweetContentInput", msg);
        // Insert "a" with java.awt.robot => Fix bug for Chrome and IE
        keyPressNative("65");
        Integer actualCount = Integer.valueOf(getText("publish-tweet-counter"));
        Integer expectedCount = initCount - (msg.length() + 1);
        // check the value counter
        boolean ok = expectedCount <= actualCount ? true : false;
        Assert.assertTrue(ok);
    }

    /**
     * Check the content of the rightbar
     */
    @Test(groups =
    { "integration" }, dependsOnMethods =
    { "login" })
    public void checkRightBar()
    {
        String loginExpected = getText("rightbar-user-infos-login");
        Assert.assertEquals(loginExpected, DEFAULT_FIRST_USER_LOGIN);
        logoutUser();
        checkTitle(DEFAULT_PAGE_INDEX_TITLE);
    }

}
