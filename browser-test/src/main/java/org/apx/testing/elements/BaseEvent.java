package org.apx.testing.elements;

import org.openqa.selenium.Keys;

/**
 * Base events o a common element
 */
public class BaseEvent<T extends Element> {

    T target;

    protected BaseEvent(T el){
        target = el;
    }

    /**
     * Perform a click on an element
     * @return element reference
     */
    public T click(){
        target.webElement().click();
        String tag = target.tagName();
//        if("a".equals(tag) || "button".equals(tag)){
//            System.out.println("Waiting for page load");
//            target.browser().waitForPageLoad();
//        }
        return target;
    }

    /**
     * Perform a press of ENTER on an element
     * @return element reference
     */
    public T enter(){
        target.webElement().sendKeys(Keys.ENTER);
//        System.out.println("Waiting for page load");
//        target.browser().waitForPageLoad();
        return target;
    }

}
