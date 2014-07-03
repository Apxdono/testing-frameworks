package org.apx.testing.browser;

/**
 * Interface that provides ability to wait for some events to occur. Wraps around Expected condition in WebDriver
 */
public interface Check {

    /**
     * Check some condition and provide a boolean result
     * @param browser caller browser instance
     * @return true or false depending on the checks
     */
	Boolean check(Browser browser);
}
