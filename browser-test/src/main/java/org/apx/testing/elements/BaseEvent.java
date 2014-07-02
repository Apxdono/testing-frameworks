package org.apx.testing.elements;

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

}
