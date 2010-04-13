/**
 * 
 */
package integration;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Integration tests for the welcome page
 * @author lguerin
 *
 */
public class WelcomeIndexPageTest extends AbstractTapesTwitterIntegrationTest
{
	/**
	 * The default Index page title
	 */
	private static final String DEFAULT_INDEX_TITLE = "TapesTwitter / Bienvenue";

	/**
	 * Text who is present into presentation
	 */
	private static final String DEFAULT_TEXT = "Tapestry 5.1";

	/**
	 * Check welcome page.
	 */
	@Test(groups = { "integration" })
	public void testIndexTitle()
	{
		open(BASE_URL);
		waitForPageToLoad();
		Assert.assertTrue(isTextPresent(DEFAULT_TEXT));
		checkTitle(DEFAULT_INDEX_TITLE);
	}
}
