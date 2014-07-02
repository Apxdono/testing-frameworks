package org.apx.testing.core;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apx.testing.browser.Browser;
import org.apx.testing.elements.HtmlElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 25.06.14
 * Time: 18:20
 * To change this template use File | Settings | File Templates.
 */
public class ElementFactory implements ICommonElementFactory {

    static Logger LOG = LoggerFactory.getLogger(ElementFactory.class);
    static int DEFAULT_TIMEOUT = 5;
    Browser browser;
    WebDriver driver;
    LinkedList<QueueAction> actions;


    public ElementFactory(Browser b) {
        browser = b;
        driver = b.getDriver();
        actions = new LinkedList<QueueAction>();
    }

    /*Simple search methods*/

    @Override
    public HtmlElement byId(String id) {
        return by(By.id(id));
    }

    @Override
    public HtmlElement byName(String name) {
        return by(By.name(name));
    }

    @Override
    public List<HtmlElement> byNameAll(String name) {
        return byAll(By.name(name));
    }

    @Override
    public HtmlElement byXpath(String xpath) {
        return by(By.xpath(xpath));
    }

    @Override
    public List<HtmlElement> byXpathAll(String xpath) {
        return byAll(By.xpath(xpath));
    }

    @Override
    public HtmlElement bySelector(String selector) {
        return by(By.cssSelector(selector));
    }

    @Override
    public List<HtmlElement> bySelectorAll(String selector) {
        return byAll(By.cssSelector(selector));
    }

    @Override
    public HtmlElement by(Object predicate) {
        if (!(predicate instanceof By) || predicate == null) {
            LOG.error("Not a valid by expression. Actual type is '{}'. Required type '{}'", predicate != null ? predicate.getClass() : null, By.class);
            return null;
        }
        By by = (By) predicate;
        if(actionsQueued()){
            return wrap(performSingleElementAction(by));
        } else {
            return wrap(driver.findElement(by));
        }
    }

    @Override
    public List<HtmlElement> byAll(Object predicate) {
        if (!(predicate instanceof By) || predicate == null) {
            LOG.error("Not a valid by expression. Actual type is '{}'. Required type '{}'", predicate != null ? predicate.getClass() : null, By.class);
            return null;
        }

        By by = (By) predicate;

        if(actionsQueued()){
            return wrap(performMultipleElementsAction(by));
        } else {
            return wrap(driver.findElements(by));
        }
    }


    /*Search methods with parent objects*/

    @Override
    public HtmlElement byId(String id, EFElement parent) {
        return by(By.id(id), parent);
    }

    @Override
    public HtmlElement byName(String name, EFElement parent) {
        return by(By.name(name), parent);
    }

    @Override
    public List<HtmlElement> byNameAll(String name, EFElement parent) {
        return byAll(By.name(name), parent);
    }

    @Override
    public HtmlElement byXpath(String xpath, EFElement parent) {
        return by(By.xpath(xpath), parent);
    }

    @Override
    public List<HtmlElement> byXpathAll(String xpath, EFElement parent) {
        return byAll(By.xpath(xpath), parent);
    }

    @Override
    public HtmlElement bySelector(String selector, EFElement parent) {
        return by(By.cssSelector(selector), parent);
    }

    @Override
    public List<HtmlElement> bySelectorAll(String selector, EFElement parent) {
        return byAll(By.cssSelector(selector), parent);
    }

    @Override
    public HtmlElement by(Object predicate, EFElement parent) {
        if (!(predicate instanceof By) || predicate == null) {
            LOG.error("Not a valid by expression. Actual type is '{}'. Required type '{}'. PArent element '{}'", predicate != null ? predicate.getClass() : null, By.class, parent);
            return null;
        }

        By by = (By) predicate;
        return wrap(parent.getWebElement().findElement(by));
    }

    @Override
    public List<HtmlElement> byAll(Object predicate, EFElement parent) {
        if (!(predicate instanceof By) || predicate == null || parent == null) {
            LOG.error("Not a valid by expression. Actual type is '{}'. Required type '{}'. Parent element '{}'", predicate != null ? predicate.getClass() : null, By.class, parent);
            return null;
        }

        By by = (By) predicate;
        return wrap(parent.getWebElement().findElements(by));
    }


    @Override
    public ICommonElementFactory untilVisible() {
        return untilVisible(DEFAULT_TIMEOUT);
    }

    @Override
    public ICommonElementFactory untilVisible(int timeout) {
        actions.add(new QueueAction(Condition.VISIBLE, timeout));
        return this;
    }

    @Override
    public ICommonElementFactory untilPresent() {
        return untilPresent(DEFAULT_TIMEOUT);
    }

    @Override
    public ICommonElementFactory untilPresent(int timeout) {
        actions.add(new QueueAction(Condition.PRESENT, timeout));
        return this;
    }

    @Override
    public HtmlElement wrap(WebElement we) {
        return we != null ? ((EFElement<HtmlElement>) new HtmlElement()).init(browser, we) : null;
    }

    @Override
    public List<HtmlElement> wrap(List<WebElement> wes) {
        if (wes != null && !wes.isEmpty()) {
            List<HtmlElement> res = new ArrayList<HtmlElement>(wes.size());
            for (WebElement we : wes) {
                res.add(wrap(we));
            }
            return res;
        }
        return Collections.emptyList();
    }

    protected WebElement performSingleElementAction(By predicate) {
        QueueAction action = actions.getLast();
        actions.removeLast();
        WebDriverWait wait = browser.getWait(action.getTimeout());
        ExpectedCondition<WebElement> ecw;
        switch (action.getCondition()) {
            case PRESENT:
                ecw = ExpectedConditions.presenceOfElementLocated(predicate);
                break;
            default:
                ecw = ExpectedConditions.visibilityOfElementLocated(predicate);
                break;
        }
        return wait.until(ecw);
    }

    protected List<WebElement> performMultipleElementsAction(By predicate) {
        QueueAction action = actions.getLast();
        actions.removeLast();
        WebDriverWait wait = browser.getWait(action.getTimeout());
        ExpectedCondition<List<WebElement>> ecw;
        switch (action.getCondition()) {
            case PRESENT:
                ecw = ExpectedConditions.presenceOfAllElementsLocatedBy(predicate);
                break;
            default:
                ecw = ExpectedConditions.visibilityOfAllElementsLocatedBy(predicate);
                break;
        }
        return wait.until(ecw);
    }


    protected boolean actionsQueued(){
        return !actions.isEmpty();
    }
}
