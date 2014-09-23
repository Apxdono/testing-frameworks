package org.apx.testing.testelements;

import org.apx.testing.elements.HtmlElement;
import org.apx.testing.elements.Table;
import org.apx.testing.elements.TableRow;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by oleg on 09.07.2014.
 */
public class TableTests extends AbstractTest {


    @Test
    public void tableTests() {
        b.get("http://www.w3schools.com/tags/tag_table.asp").find().bySelector(".example a.tryitbtn").event().click();
        Table t = b.nextTab().switchToFrame("iframeResult").find().bySelector("table").as(Table.class);
        assertEquals(t.searchContext(Table.Context.TBODY).contextIndex(1).captionCell(1, 1).text(), "Month");

        assertThat("January", allOf(equalTo(t.row(2).cell(1).text()), equalTo(t.cell(2, 1).text())));
        assertEquals(t.cell(2, 1), t.row(2).cell(1));
        LOG.info("January savings: {} bucks", t.cell(2, 2).text().replace("$", ""));

        int saved = 0;
        for (HtmlElement col : t.column(2)) {
            saved += Integer.parseInt(col.text().substring(1));
        }

        assertThat(saved, equalTo(180));
        LOG.info("Saved {} big ones",saved);
        LOG.info("Printing table");

        int i = 0;
        for (TableRow tr : t.rows()) {
            i++;
            List<HtmlElement> cols = tr.cells();
            LOG.info("{}\t{}\t{}",i,cols.get(0).text(),cols.get(1).text());
        }
	    b.switchBackFromFrame().prevTab().closeNextTab();

    }
}
