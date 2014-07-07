package org.apx.testing.core;

import org.apx.testing.browser.Browser;
import org.apx.testing.elements.HtmlElement;
import org.openqa.selenium.By;
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
 * The implementation of {@link org.apx.testing.core.ICommonElementFactory} that uses {@link org.openqa.selenium.WebDriver} to query for elements
 */
public class ElementFactory implements ICommonElementFactory {

    static Logger LOG = LoggerFactory.getLogger(ElementFactory.class);
    static int DEFAULT_TIMEOUT = 5;
    Browser browser;
//    WebDriver driver;
    LinkedList<QueueAction> actions;

    /**
     * Initialization
     *
     * @param b browser to be attached to
     */
    public ElementFactory(Browser b) {
        browser = b;
        actions = new LinkedList<>();
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
        if (actionsQueued()) {
            return wrap(performSingleElementAction(by));
        } else {
            return wrap(browser.getDriver().findElement(by));
        }
    }

    @Override
    public List<HtmlElement> byAll(Object predicate) {
        if (!(predicate instanceof By) || predicate == null) {
            LOG.error("Not a valid by expression. Actual type is '{}'. Required type '{}'", predicate != null ? predicate.getClass() : null, By.class);
            return null;
        }

        By by = (By) predicate;

        if (actionsQueued()) {
            return wrap(performMultipleElementsAction(by));
        } else {
            return wrap(browser.getDriver().findElements(by));
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
        return wrap(parent.webElement().findElement(by), parent);
    }

    @Override
    public List<HtmlElement> byAll(Object predicate, EFElement parent) {
        if (!(predicate instanceof By) || predicate == null || parent == null) {
            LOG.error("Not a valid by expression. Actual type is '{}'. Required type '{}'. Parent element '{}'", predicate != null ? predicate.getClass() : null, By.class, parent);
            return null;
        }

        By by = (By) predicate;
        return wrap(parent.webElement().findElements(by), parent);
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
        return wrap(we, null);
    }

    protected HtmlElement wrap(WebElement we, EFElement parent) {
        HtmlElement p = parent != null ? (HtmlElement) parent.as(HtmlElement.class) : null;
        return we != null ? ((EFElement<HtmlElement>) new HtmlElement()).init(browser, we).setParent(p) : null;
    }

    @Override
    public List<HtmlElement> wrap(List<WebElement> wes) {
        return wrap(wes, null);
    }

    protected List<HtmlElement> wrap(List<WebElement> wes, EFElement parent) {
        if (wes != null && !wes.isEmpty()) {
            List<HtmlElement> res = new ArrayList<HtmlElement>(wes.size());
            for (WebElement we : wes) {
                res.add(wrap(we, parent));
            }
            return res;
        }
        return Collections.emptyList();
    }

    @Override
    public <E extends EFElement> E as(EFElement element, Class<E> targetClass) {
        try {
            return (E) targetClass.newInstance().init(element);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <E extends EFElement, T extends EFElement> List<E> allAs(List<T> elements, Class<E> targetClass) {
        if (elements == null || elements.isEmpty()) return Collections.emptyList();
        List<E> result = new ArrayList<>(elements.size());
        for (EFElement element : elements) {
            result.add(this.as(element, targetClass));
        }
        return result;
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

    protected boolean actionsQueued() {
        return !actions.isEmpty();
    }
}
