package org.apx.testing.testelements;

import org.apx.testing.browser.Browser;
import org.apx.testing.elements.HtmlElement;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.Keys;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 25.06.14
 * Time: 17:35
 * To change this template use File | Settings | File Templates.
 */
public class ElementsTest {

    static String FAKE_AJAX = "setTimeout(function(){document.querySelector('.gb_Ta a').click()},4000);";

    @Test
    public void browserTest() throws InterruptedException {
        Browser b = Browser.instance().get("http://www.google.com");
        HtmlElement el = b.find().byName("q");
        Assert.assertEquals("q",el.formName());
        el.val("Cheese!");
        List<HtmlElement> els = b.find().untilVisible().bySelectorAll(".gsq_a");
        System.out.println(el.cssSelector());
        System.out.println("Elements present: " + els.size());
        //Google services Div
        HtmlElement servicesDiv = b.find().byId("gbwa");
        servicesDiv.find().bySelector(".gb_Ta a").event().click();
        HtmlElement alala = servicesDiv.children().get(1);
        b.js().executeScript(FAKE_AJAX);
        alala.waitUntilElement(6).isHidden();
        el.getWebElement().sendKeys(Keys.ENTER);
        Thread.sleep(1000);
    }
}
