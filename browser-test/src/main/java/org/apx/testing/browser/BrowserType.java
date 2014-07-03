package org.apx.testing.browser;

import org.slf4j.LoggerFactory;

/**
 * Represents supported browser types
 */
public enum BrowserType {
	FIREFOX,
	SAFARI,
	OPERA,
	CHROME,
	IE;

    /**
     * Get browser type by string name
     * @param name browser name
     * @return type of browser
     */
	public static BrowserType getType(String name) {
		BrowserType bt = BrowserType.FIREFOX;
		try {
			bt = BrowserType.valueOf((name + "").trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			LoggerFactory.getLogger(BrowserType.class).error("No browser type found for '{}'. Using firefox as default", name,e);
		}
		return bt;
	}
}
