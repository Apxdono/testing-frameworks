package org.apx.testing.testelements;

import org.apx.testing.elements.HtmlElement;
import org.apx.testing.elements.primefaces.PFPickList;
import org.junit.Test;

/**
 * Created by oleg on 09.07.2014.
 */
public class PFPicklistTest extends AbstractTest {

    @Test
    public void testPicklist(){
        HtmlElement container = b.get("http://www.primefaces.org/showcase/ui/data/pickList.xhtml").find().bySelector("div.Implementation form");
        PFPickList pl = container.child(4).as(PFPickList.class);
        pl.selectAll().deselectAll().selectAllAt(new int[]{1, 3});
        PFPickList pl2 = container.child(7).as(PFPickList.class);
        pl2.selectAll().deselectAll().selectAllAt(new int[]{1, 3});
        b.hold(3);
    }
}
