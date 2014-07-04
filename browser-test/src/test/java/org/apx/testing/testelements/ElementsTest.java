package org.apx.testing.testelements;

import org.apx.testing.browser.Browser;
import org.apx.testing.elements.HtmlElement;
import org.apx.testing.elements.SelectElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 25.06.14
 * Time: 17:35
 * To change this template use File | Settings | File Templates.
 */
public class ElementsTest {

    static Logger LOG = LoggerFactory.getLogger(ElementsTest.class);

    static String FAKE_AJAX = "setTimeout(function(){document.querySelector('.gb_Ta a').click()},4000);";
    static String ELEMENTS_PAGE = "https://www.cs.tut.fi/~jkorpela/www/testel.html";

    Browser b;

    @Before
    public void init(){
        b = Browser.instance();
    }

    @Test
    public void browserTest() throws InterruptedException {
        //Go to google and query for 'Cheese!'
        HtmlElement el = b.get("http://www.google.com").find().untilPresent().byName("q").val("Cheese!").as(SelectElement.class).as(HtmlElement.class);
        assertEquals("q", el.formName());

        //See how many results google suggests
        List<HtmlElement> els = b.find().untilVisible().bySelectorAll(".gsq_a");
        LOG.info("Element selector '{}' . Total suggestions '{}'", el.cssSelector() , els.size());

        //open Google services by pressing tile button
        HtmlElement servicesDiv = b.find().byId("gbwa").find().bySelector(".gb_Ta a").event().click().parent();

        //Fake ajax here. wait to submit result only after services panel is closed (aka ajax update style)
        b.js().executeScript(FAKE_AJAX);
        servicesDiv.child(1).waitUntilElement(6).isHidden();
        el.event().enter();
    }


    @Test
    public void selectElementPlusFrameAndTabTest(){
        //go to page and get single select
        SelectElement se  = b.get(ELEMENTS_PAGE).find().bySelector("#f10").as(SelectElement.class);
        assertEquals(se.children(), se.options());
        assertEquals(se.child(0), se.options().get(0));
        assertEquals(se.selectAt(0).val().trim(), "one");
        LOG.info("Current value selected '{}'", se.val());

        //Go to w3schools at multiselect example page. click 'Try it !' and get the select
         b.get("http://www.w3schools.com/tags/att_select_multiple.asp").find().bySelector(".example a.tryitbtn").event().click();

        SelectElement seMul  = b.switchToTab(1).switchToFrame("iframeResult").find().byName("cars").as(SelectElement.class);
        assertEquals(seMul.selectAllAt(new int[]{0, 1, 3}).val().trim(),"volvo saab audi");
        b.switchBackFromFrame();
    }


    @Test
    @Ignore
    public void frameTest(){
        WebDriver driver = new ChromeDriver();
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
