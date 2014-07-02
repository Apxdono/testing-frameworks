package org.apx.testing.elements;

import org.apx.testing.core.IBasicElementFactory;
import org.apx.testing.core.ICommonElementFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 01.07.14
 * Time: 13:45
 * To change this template use File | Settings | File Templates.
 */
class ElementFactoryLocal implements IBasicElementFactory {

    ICommonElementFactory factory;
    Element caller;

    ElementFactoryLocal(ICommonElementFactory f, Element c){
        factory = f;
        caller = c;
    }

    @Override
    public HtmlElement byId(String id) {
        return factory.byId(id,caller);
    }

    @Override
    public HtmlElement byName(String name) {
        return factory.byName(name, caller);
    }

    @Override
    public List<HtmlElement> byNameAll(String name) {
        return factory.byNameAll(name, caller);
    }

    @Override
    public HtmlElement byXpath(String xpath) {
        return factory.byXpath(xpath, caller);
    }

    @Override
    public List<HtmlElement> byXpathAll(String xpath) {
        return factory.byXpathAll(xpath, caller);
    }

    @Override
    public HtmlElement bySelector(String selector) {
        return factory.bySelector(selector, caller);
    }

    @Override
    public List<HtmlElement> bySelectorAll(String selector) {
        return factory.bySelectorAll(selector, caller);
    }

    @Override
    public HtmlElement by(Object predicate) {
        return factory.by(predicate, caller);
    }

    @Override
    public List<HtmlElement> byAll(Object predicate) {
        return factory.byAll(predicate, caller);
    }
}
