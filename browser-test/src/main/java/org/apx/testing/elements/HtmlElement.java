package org.apx.testing.elements;

/**
 * Most common implementation of any html element
 */
public class HtmlElement extends Element<HtmlElement,BaseEvent<HtmlElement>>{

    @Override
    protected void initEvents() {
        events = new BaseEvent<>(this);
    }
}
