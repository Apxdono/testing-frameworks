package org.apx.testing.testelements;

import org.apx.testing.elements.HtmlElement;
import org.apx.testing.elements.primefaces.PFAutocomplete;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by oleg on 09.07.2014.
 */
public class PFAutocompleteTest extends AbstractTest {

    @Test
    public void testAutocomplete(){
        PFAutocomplete ac = b.get("http://www.primefaces.org/showcase/ui/input/autoComplete.xhtml").find().bySelector("span[id$='acSimple']").as(PFAutocomplete.class);
        LOG.info("html: {}",ac.complete("123").panel().html());
        List<HtmlElement> results = ac.results();
        LOG.info("Item count: {}",results.size());
        LOG.info("First item label {} value {}",results.get(0).text(),results.get(0).val());
        ac.selectAt(2);
        assertThat(b.find().bySelector("span[id$='acMaxResults']").as(PFAutocomplete.class).complete("123").results().size(), is(5));
        b.hold(3);

    }
}
