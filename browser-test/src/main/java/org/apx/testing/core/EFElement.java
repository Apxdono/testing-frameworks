package org.apx.testing.core;

import org.apx.testing.browser.Browser;
import org.apx.testing.elements.HtmlElement;
import org.openqa.selenium.WebElement;

/**
  * Faking "orgasms" (IYKWIM) here. (Friendly functions)
  */
public abstract class EFElement<T> {

    protected Browser owner;
    protected WebElement target;
    protected String cssSelector;
    protected HtmlElement parent;

    protected abstract T init(Browser browser,WebElement element);

    protected T setParent(HtmlElement p){
        parent = p;
        return (T) this;
    }

    protected T init(EFElement element){
        if( element == null) return null;
        this.owner = element.owner;
        this.cssSelector = element.cssSelector;
        this.target = element.target;
        return (T) this;
    }

    public abstract WebElement getWebElement();

    public abstract  <E extends EFElement> E as(Class<E> eClass);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EFElement)) return false;

        EFElement efElement = (EFElement) o;

        if (!owner.equals(efElement.owner)) return false;
        if (!target.equals(efElement.target)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = owner.hashCode();
        result = 31 * result + target.hashCode();
        return result;
    }
}
