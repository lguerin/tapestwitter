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
 *
 */
abstract class AbstractTapesTwitterIntegrationTest extends AbstractIntegrationTestSuite
{
	/**
	 * Default browser used to launch tests.
	 */
	private static String defaultBrowser;

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
}
