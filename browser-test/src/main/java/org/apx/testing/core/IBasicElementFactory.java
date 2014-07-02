package org.apx.testing.core;

import org.apx.testing.elements.HtmlElement;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 01.07.14
 * Time: 13:37
 * To change this template use File | Settings | File Templates.
 */
public interface IBasicElementFactory {
    HtmlElement byId(String id);

    HtmlElement byName(String name);

    List<HtmlElement> byNameAll(String name);

    HtmlElement byXpath(String xpath);

    List<HtmlElement> byXpathAll(String xpath);

    HtmlElement bySelector(String selector);

    List<HtmlElement> bySelectorAll(String selector);

    HtmlElement by(Object predicate);

    List<HtmlElement> byAll(Object predicate);
}
