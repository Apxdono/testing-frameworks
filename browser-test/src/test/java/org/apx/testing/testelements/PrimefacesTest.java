package org.apx.testing.testelements;

import org.apx.testing.elements.HtmlElement;
import org.apx.testing.elements.primefaces.PFSelectMenu;
import org.junit.Test;

/**
 * Created by oleg on 15.07.2014.
 */
public class PrimefacesTest extends AbstractTest {

    @Test
    public void selectOneMenuTest(){
        HtmlElement content = b.get("http://www.primefaces.org/showcase/ui/input/oneMenu.xhtml").find().bySelector(".Implementation");
        PFSelectMenu basicMenu = content.find().bySelector(".ui-selectonemenu").as(PFSelectMenu.class).expand();
        b.hold(2);
        basicMenu.selectAt(2);
        b.hold(2);



    }

}


