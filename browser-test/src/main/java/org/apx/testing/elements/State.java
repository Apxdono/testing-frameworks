package org.apx.testing.elements;

import org.apx.testing.browser.Browser;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 01.07.14
 * Time: 18:32
 * To change this template use File | Settings | File Templates.
 */
public class State<T extends Element> {

    Browser browser;
    T target;
    int timeout = 0;

    protected State(Browser b, T t){
        browser = b;
        target = t;
    }

    private int timeout() {
        int buf = timeout;
        timeout = 0;
        return buf;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public T isVisible() {
        if(timeout != 0) {
            return browser.getWait(timeout()).until(ExpectedConditions.visibilityOf(target.webElement())) != null ? target : null ;
        }
        return target;
    }

    public T isClickable() {
        if(timeout != 0) {
            return browser.getWait(timeout()).until(ExpectedConditions.elementToBeClickable(target.webElement())) != null ? target : null;
        }
        return target;
    }

    public T isHidden(){
        if(timeout != 0) {
            return browser.getWait(timeout()).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(target.cssSelector()))) ? target : null;
        }
        return target;
    }

    public void isRemoved(){
        if(timeout != 0) {
            browser.getWait(timeout()).until(ExpectedConditions.stalenessOf(target.webElement()));
        }
    }

    public T hasText(String text){
        if(timeout != 0) {
            return browser.getWait(timeout()).until(ExpectedConditions.textToBePresentInElement(target.webElement(),text)) ? target : null;
        }
        return target;
    }

    public T hasValue(String text){
        if(timeout != 0) {
            return browser.getWait(timeout()).until(ExpectedConditions.textToBePresentInElementValue(target.webElement(),text)) ? target : null;
        }
        return target;
    }
}
