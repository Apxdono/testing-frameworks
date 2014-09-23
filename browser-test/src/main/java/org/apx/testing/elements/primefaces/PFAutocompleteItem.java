package org.apx.testing.elements.primefaces;

import org.apx.testing.elements.HtmlElement;

/**
 * Created by oleg on 22.09.2014.
 */
public class PFAutocompleteItem extends HtmlElement {

    static final String DATA_LABEL = "item-label";
    static final String DATA_VALUE = "item-value";

    @Override
    public String val() {
        return data(DATA_VALUE);
    }

    @Override
    public String text() {
        return data(DATA_LABEL);
    }
}
