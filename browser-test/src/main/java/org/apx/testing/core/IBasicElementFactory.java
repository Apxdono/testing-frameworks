package org.apx.testing.core;

import org.apx.testing.elements.HtmlElement;

import java.util.List;

/**
 * Basic element factory interface. Contains all necessary methods for finding elements. <br/>
 * Depending on implementation the search context can be either WebDriver's or WebElement's. <br/>
 * has shortcut methods that make using {@link org.openqa.selenium.By} calls unnecessary
 * @see org.openqa.selenium.WebDriver#findElement(org.openqa.selenium.By)
 * @see org.openqa.selenium.WebDriver#findElements(org.openqa.selenium.By)
 * @see org.openqa.selenium.WebElement#findElement(org.openqa.selenium.By)
 * @see org.openqa.selenium.WebElement#findElements(org.openqa.selenium.By)
 */
public interface IBasicElementFactory {

    /**
     * Find element by id
     * @param id selector for search statement
     * @return element if found or null
     */
    HtmlElement byId(String id);


    /**
     * Find element by form name
     * @param name selector for search statement
     * @return element if found or null
     */
    HtmlElement byName(String name);

    /**
     * Find  all elements by form name. (Usually used for radio buttons etc.)
     * @param name selector for search statement
     * @return elements if found or an empty collection
     */
    List<HtmlElement> byNameAll(String name);

    /**
     * Find element by xpath
     * @param xpath selector for search statement
     * @return element if found or null
     */
    HtmlElement byXpath(String xpath);

    /**
     * Find  all elements by xpath.
     * @param xpath selector for search statement
     * @return elements if found or an empty collection
     */
    List<HtmlElement> byXpathAll(String xpath);


    /**
     * Find element by css selector
     * @param selector css selector for search statement
     * @return element if found or null
     */
    HtmlElement bySelector(String selector);


    /**
     * Find  all elements by css selector.
     * @param selector css selector for search statement
     * @return elements if found or an empty collection
     */
    List<HtmlElement> bySelectorAll(String selector);

    /**
     * A backup method which consumes {@link org.openqa.selenium.By} statements which are not wrapped with methods of this class. <br/>
     * Tries to find element mathcing the statement
     * @param predicate instance of {@link org.openqa.selenium.By} statement
     * @return element if found or null
     */
    HtmlElement by(Object predicate);

    /**
     * Tries to find elements mathcing the statement
     * @see org.apx.testing.core.IBasicElementFactory#by(Object)
     * @param predicate instance of {@link org.openqa.selenium.By} statement
     * @return elements if found or an empty collection
     */
    List<HtmlElement> byAll(Object predicate);
}
