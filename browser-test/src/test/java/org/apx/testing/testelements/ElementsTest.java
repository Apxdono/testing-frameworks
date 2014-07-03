package org.apx.testing.testelements;

import org.apx.testing.browser.Browser;
import org.apx.testing.elements.HtmlElement;
import org.apx.testing.elements.SelectElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


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
        HtmlElement el = b.get("http://www.google.com").find().byName("q").val("Cheese!");
        Assert.assertEquals("q", el.formName());

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
    public void selectElementTests(){
        SelectElement se  = b.get(ELEMENTS_PAGE).find().bySelector("#f10").as(SelectElement.class);
        Assert.assertEquals(se.children(), se.options());
        Assert.assertEquals(se.child(0), se.options().get(0));
    }


}
