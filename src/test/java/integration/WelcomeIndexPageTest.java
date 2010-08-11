/**
 * 
 */
package integration;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Integration tests for the welcome page
 * 
 * @author lguerin
 */
public class WelcomeIndexPageTest extends AbstractTapesTwitterIntegrationTest
{
    /**
     * The default Index page title
     */
    private static final String DEFAULT_INDEX_TITLE = "TapesTwitter / Index";

    /**
     * Text include into presentation
     */
    private static final String DEFAULT_TEXT = "Tapestry";

    /**
     * Check welcome page.
     */
    @Test(groups =
    { "integration" })
    public void testIndexTitle()
    {
        open(BASE_URL);
        waitForPageToLoad();
        Assert.assertTrue(isTextPresent(DEFAULT_TEXT));
        checkTitle(DEFAULT_INDEX_TITLE);
    }
}
