package org.apx.testing.elements.primefaces;

import org.apx.testing.elements.BaseEvent;
import org.apx.testing.elements.Element;
import org.apx.testing.elements.HtmlElement;
import org.apx.testing.elements.interfaces.IAutocomplete;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleg on 22.09.2014.
 */
public class PFAutocomplete extends Element<PFAutocomplete, BaseEvent<PFAutocomplete>> implements IAutocomplete<PFAutocomplete> {

    static final String ITEM_SELECTOR= ".ui-autocomplete-item";

    int timeout = 200;

    @Override
    protected void initEvents() {
        events = new BaseEvent<>(this);
    }

    @Override
    public PFAutocomplete timeout(int ms) {
        timeout = ms;
        return this;
    }

    @Override
    public PFAutocomplete complete(String val) {
        this.find().bySelector("input").val(val);
        browser().ajaxWait( timeout < 1000 ? 1 : timeout/1000);
        return this;
    }

    @Override
    public PFAutocomplete selectAt(int index) {
        results().get(index - 1).event().click().browser().ajaxWait();
        return this;
    }

    @Override
    public HtmlElement panel() {
        return browser().find().byId(id()+"_panel");
    }

    @Override
    public List<HtmlElement> results() {
        return new ArrayList<HtmlElement>(browser().find().allAs(panel().find().bySelectorAll(ITEM_SELECTOR), PFAutocompleteItem.class));
    }


}
