package org.apx.testing.core;

import org.apx.testing.browser.Browser;
import org.openqa.selenium.WebElement;

/**
  * Faking "orgasms" (IYKWIM) here. (Friendly functions)
  */
public abstract class EFElement<T> {
    protected abstract T init(Browser browser,WebElement element);

    public abstract WebElement getWebElement();
}
