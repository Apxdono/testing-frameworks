package org.apx.testing.elements;

import org.openqa.selenium.Keys;

/**
 * Created by oleg on 02.07.2014.
 */
public class BaseEvent<T extends Element> {

    T target;

    protected BaseEvent(T el){
        target = el;
    }

    public T click(){
        target.getWebElement().click();
        return target;
    }

    public T enter(){
        target.getWebElement().sendKeys(Keys.ENTER);
        return target;
    }

}
