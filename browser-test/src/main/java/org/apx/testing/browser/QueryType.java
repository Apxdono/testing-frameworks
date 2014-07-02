package org.apx.testing.browser;

import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 1/5/14
 * Time: 10:23 PM
 * To change this template use File | Settings | File Templates.
 */
public enum QueryType {
	WEBDRIVER,
	JQUERY;
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
