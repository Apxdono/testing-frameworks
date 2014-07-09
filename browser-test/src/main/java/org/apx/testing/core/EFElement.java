package org.apx.testing.core;

import org.apx.testing.browser.Browser;
import org.apx.testing.elements.HtmlElement;
import org.openqa.selenium.WebElement;

/**
 * A class that is used by Element factories. Provides ways to fake "orgasms" aka "friendly functions" in C. <br/>
 * Contains basic things required for all elements and factories
 */
public abstract class EFElement<T> {

    protected Browser owner;
    protected WebElement target;
    protected String cssSelector;
    protected HtmlElement parent;

    /**
     * Init a new instance of an element
     * @param browser owner browser
     * @param element actual web driver's element
     * @return self reference
     * @see org.openqa.selenium.WebElement
     */
    protected abstract T init(Browser browser, WebElement element);


    /**
     * Used when querying in other element context. Allows to skip further search of parent later on
     * @param p parent element
     * @return self reference
     */
    protected T setParent(HtmlElement p) {
        parent = p;
        return (T) this;
    }

    /**
     * Init a new element using another one's precalculated data
     * @param element donor element
     * @return self reference
     */
    protected T init(EFElement element) {
        if (element == null) return null;
        this.owner = element.owner;
        this.cssSelector = element.cssSelector;
        this.target = element.target;
        return (T) this;
    }

    /**
     * Get the owner browser reference
     * @return browser ref
     */
    public Browser browser(){
        return owner;
    }


    /**
     * Get the actual web drivers' element
     * @return web element instance
     * @see org.openqa.selenium.WebElement
     */
    public WebElement webElement() {
        return target;
    }


    /**
     * Convert current element to another element type. <br/>
     * Useful method gives you a new element instance with given type which provide additional <br/>
     * methods and features that can make element manipulations easies.
     * @param eClass the target element class type
     * @param <E> element type to be returned
     * @return new element with give type or null
     */
    public abstract <E extends EFElement> E as(Class<E> eClass);



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EFElement)) return false;

        EFElement efElement = (EFElement) o;

        if (!owner.equals(efElement.owner)) return false;
        if (!cssSelector.equals(efElement.cssSelector)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = owner.hashCode();
        result = 31 * result + cssSelector.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "{owner=" + owner +
                ", cssSelector='" + cssSelector + '\'' +
                '}';
    }
}
