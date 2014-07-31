package org.apx.testing.elements;

import org.apx.testing.browser.ScriptLoader;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

/**
 * Base events o a common element
 */
public class BaseEvent<T extends Element> {

    private final T target;

    public BaseEvent(T el) {
        target = el;
    }

    /**
     * Perform a click on an element
     *
     * @return element reference
     */
    public T click() {
        target.webElement().click();
        return target;
    }

    /**
     * Perform a double click on an element
     *
     * @return element reference
     */
    public T dblclick() {
        ScriptLoader.loadFunction(target.browser(), "triggerEvent");
        target.browser().js().executeScript("triggerEvent(arguments[0],'dblclick')", target.webElement());
        return target;
    }


    public T ctrlclick() {
        new Actions(target.browser().getDriver()).keyDown(Keys.CONTROL).click(target.webElement()).keyUp(Keys.CONTROL).build().perform();
        return target;
    }

    /**
     * Perform a press of ENTER on an element
     *
     * @return element reference
     */
    public T enter() {
        target.webElement().sendKeys(Keys.ENTER);
        return target;
    }

}
