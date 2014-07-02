package org.apx.testing.elements;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 25.06.14
 * Time: 17:46
 * To change this template use File | Settings | File Templates.
 */
public class HtmlElement extends Element<HtmlElement,BaseEvent<HtmlElement>>{

    @Override
    protected void initEvents() {
        events = new BaseEvent<>(this);
    }
}
