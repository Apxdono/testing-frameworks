package org.apx.testing.browser;

import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 1/5/14
 * Time: 10:22 PM
 * To change this template use File | Settings | File Templates.
 */
public enum BrowserType {
	FIREFOX,
	SAFARI,
	OPERA,
	CHROME,
	IE;

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
