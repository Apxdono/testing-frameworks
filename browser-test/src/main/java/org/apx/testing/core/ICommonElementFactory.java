package org.apx.testing.core;

import org.apx.testing.elements.HtmlElement;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * An extension for {@link org.apx.testing.core.IBasicElementFactory}. <br/>
 * Has additional search methods with search context of element and a couple of handy ones for manipulating elements
 */
public interface ICommonElementFactory extends IBasicElementFactory {

    /**
     * Find element by id
     * @param id selector for search statement
     * @param parent as a search context
     * @return element if found or null
     */
    HtmlElement byId(String id, EFElement parent);

    /**
     * Find element by id
     * @param name selector for search statement
     * @param parent as a search context
     * @return element if found or null
     */
    HtmlElement byName(String name, EFElement parent);

    /**
     * Find  all elements by form name. (Usually used for radio buttons etc.)
     * @param name selector for search statement
     * @param parent as a search context
     * @return elements if found or an empty collection
     */
    List<HtmlElement> byNameAll(String name, EFElement parent);

    /**
     * Find element by xpath
     * @param xpath selector for search statement
     * @param parent as a search context
     * @return element if found or null
     */
    HtmlElement byXpath(String xpath, EFElement parent);

    /**
     * Find  all elements by xpath.
     * @param xpath selector for search statement
     * @param parent as a search context
     * @return elements if found or an empty collection
     */
    List<HtmlElement> byXpathAll(String xpath, EFElement parent);

    /**
     * Find element by css selector
     * @param selector css selector for search statement
     * @param parent as a search context
     * @return element if found or null
     */
    HtmlElement bySelector(String selector, EFElement parent);

    /**
     * Find  all elements by css selector.
     * @param selector css selector for search statement
     * @param parent as a search context
     * @return elements if found or an empty collection
     */
    List<HtmlElement> bySelectorAll(String selector, EFElement parent);

    /**
     * @param predicate instance of {@link org.openqa.selenium.By} statement
     * @param parent as a search context
     * @return element if found or null
     * @see org.apx.testing.core.IBasicElementFactory#by(Object)
     */
    HtmlElement by(Object predicate, EFElement parent);

    /**
     * @param predicate instance of {@link org.openqa.selenium.By} statement
     * @param parent as a search context
     * @return elements if found or an empty collection
     * @see org.apx.testing.core.IBasicElementFactory#byAll(Object)
     */
    List<HtmlElement> byAll(Object predicate, EFElement parent);

    /**
     * Wrap web driver's web element
     * @param we instance of {@link org.openqa.selenium.WebElement}
     * @return element wrapped or null
     */
    HtmlElement wrap(WebElement we);

    /**
     * Wrap a collection of web driver's web elements
     * @param wes instances of {@link org.openqa.selenium.WebElement}
     * @return elements wrapped or empty collection
     */
    List<HtmlElement> wrap(List<WebElement> wes);

    /**
     * Cast current wrapper element to a desired one
     * @param element target of cast
     * @param targetClass class to be cast to
     * @param <E> type arg of a desired class
     * @return element with given type or null
     */
    <E extends EFElement> E as(EFElement element, Class<E> targetClass);

    /**
     * Cast a list of wrapper elements to a list of given type
     * @param elements targets of cast
     * @param targetClass class to be cast to
     * @param <E> type arg of a desired class
     * @param <T> type arg of current targets
     * @return list of elements with given type or an empty collection
     */
    <E extends EFElement,T extends EFElement> List<E> allAs(List<T> elements, Class<E> targetClass);

    /**
     * A shorter version of {@link org.apx.testing.core.ICommonElementFactory#untilVisible(int)} with default timeout
     * @see org.apx.testing.core.ICommonElementFactory#untilVisible(int)
     * @return self reference
     */
    ICommonElementFactory untilVisible();

    /**
     * Create a delay that will be executed the next time you are going to find element(s) on the page. <br/>
     * Querying for elements by some criteria will trigger a delay that waits for elements to be found within given timeout specifying that <br/>
     * must be visible on the page. If none found within timeout a {@link org.openqa.selenium.TimeoutException} will be thrown. <br/>
     * <b>Keep in mind that this delay execution is stored in a stack (LIFO). Calling it multiple times will provide a queue of delays one per search request.</b>
     * @param timeout the max time the queriyng for elements should occur
     * @return self reference
     */
    ICommonElementFactory untilVisible(int timeout);

    /**
     * A shorter version of {@link org.apx.testing.core.ICommonElementFactory#untilPresent(int)} with default timeout
     * @return
     */
    ICommonElementFactory untilPresent();

    /**
     * Same as {@link ICommonElementFactory#untilVisible(int)} except this checks for element's being attached to DOM <br/>
     * (in other words it's present on the page but may be hidden). Uses default timeout
     * @param timeout the max time the queriyng for elements should occur
     * @return self reference
     * @see ICommonElementFactory#untilVisible(int)
     */
    ICommonElementFactory untilPresent(int timeout);


}