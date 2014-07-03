package org.apx.testing.browser;

import org.slf4j.LoggerFactory;

/**
 * Represents the type of querying for web elements. Currently only WEBDRIVER is supported.<br/>
 * But this is an extension that can provide other methods for element search.
 */
public enum QueryType {
	WEBDRIVER,
	JQUERY;

    /**
     * Calculate the querying type
     * @param name string representation of type name
     * @return actual type
     */
	public static QueryType getType(String name) {
		QueryType bt = QueryType.WEBDRIVER;
		try {
			bt = QueryType.valueOf((name + "").trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			LoggerFactory.getLogger(QueryType.class).error("No querying type found for '{}'. Using webdriver as default", name,e);
		}
		return bt;
	}
}
