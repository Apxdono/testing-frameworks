package org.apx.testing.testelements;

import org.apx.testing.elements.HtmlElement;
import org.apx.testing.elements.SelectElement;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 25.06.14
 * Time: 17:35
 * To change this template use File | Settings | File Templates.
 */
public class ElementsTest extends AbstractTest {
    static String FAKE_AJAX = "setTimeout(function(){document.querySelector('#gbwa > div > a').click()},4000);";
    static String ELEMENTS_PAGE = "https://www.cs.tut.fi/~jkorpela/www/testel.html";

    @Test
    public void browserTest() throws InterruptedException {
        //Go to google and query for 'Cheese!'
        HtmlElement el = b.get("http://www.google.com").find().untilPresent().byName("q").val("Cheese!").as(SelectElement.class).as(HtmlElement.class);
        assertEquals("q", el.formName());

        //See how many results google suggests
        List<HtmlElement> els = b.find().untilVisible().bySelectorAll(".gsq_a");
        LOG.info("Element selector '{}' . Total suggestions '{}'", el.cssSelector(), els.size());

        //open Google services by pressing tile button
        HtmlElement servicesDiv = b.find().byId("gbwa").find().bySelector("div > a").event().click().parent();

        //Fake ajax here. wait to submit result only after services panel is closed (aka ajax update style). Then clcik the wiki link 4 cheese
        b.js().executeScript(FAKE_AJAX);
        servicesDiv.child(2).waitUntilElement(6).isHidden();
        el.event().enter().browser().find().untilVisible(4).bySelector("#ires a:first-child").event().click();

    }


    @Test
    public void selectElementPlusFrameAndTabTest() {
        //go to page and get single select
        SelectElement se = b.get(ELEMENTS_PAGE).find().bySelector("#f10").as(SelectElement.class);
        assertEquals(se.children(), se.options());
        assertEquals(se.child(1), se.options().get(0));
        assertEquals(se.selectAt(1).val().trim(), "one");
        LOG.info("Current value selected '{}'", se.val());

        //Go to w3schools at multiselect example page. click 'Try it !' and get the select
        b.get("http://www.w3schools.com/tags/att_select_multiple.asp").find().bySelector(".example a.tryitbtn").event().click();
        SelectElement seMul = b.switchToTab(1).switchToFrame("iframeResult").find().byName("cars").as(SelectElement.class);
        assertEquals(seMul.selectAllAt(new int[]{1, 2, 4}).val().trim(), "volvo saab audi");
        b.switchBackFromFrame().prevTab().closeNextTab();
    }


    @Ignore
    public void frameTest() {
        WebDriver driver = b.getDriver();
        driver.get("http://www.w3schools.com/tags/tryit.asp?filename=tryhtml_select_multiple");

        driver.switchTo().frame("iframeResult");

        WebElement we = driver.findElement(By.tagName("select"));
        LOG.info(we.getTagName());
        LOG.info(we.getAttribute("multiple"));
        driver.switchTo().defaultContent();
        WebElement body = driver.findElement(By.id("result"));
        LOG.info(body.getTagName());
        driver.quit();
    }

}
