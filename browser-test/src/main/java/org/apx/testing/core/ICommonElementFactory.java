package org.apx.testing.core;

import org.apx.testing.elements.HtmlElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 01.07.14
 * Time: 13:44
 * To change this template use File | Settings | File Templates.
 */
public interface ICommonElementFactory extends IBasicElementFactory {

    HtmlElement byId(String id, EFElement parent);

    HtmlElement byName(String name, EFElement parent);

    List<HtmlElement> byNameAll(String name, EFElement parent);

    HtmlElement byXpath(String xpath, EFElement parent);

    List<HtmlElement> byXpathAll(String xpath, EFElement parent);

    HtmlElement bySelector(String selector, EFElement parent);

    List<HtmlElement> bySelectorAll(String selector, EFElement parent);

    HtmlElement by(Object predicate, EFElement parent);

    List<HtmlElement> byAll(Object predicate, EFElement parent);

    HtmlElement wrap(WebElement we);

    List<HtmlElement> wrap(List<WebElement> wes);

    <E extends EFElement> E as(EFElement element, Class<E> targetClass);

    <E extends EFElement,T extends EFElement> List<E> allAs(List<T> elements, Class<E> targetClass);

    ICommonElementFactory untilVisible();

    ICommonElementFactory untilVisible(int timeout);

    ICommonElementFactory untilPresent();

    ICommonElementFactory untilPresent(int timeout);


}