package org.apx.testing.elements.primefaces;

import org.apx.testing.elements.BaseEvent;
import org.apx.testing.elements.Select;

import java.util.List;

/**
 * Implementation of primefaces SelectOne menu
 */
public class PFSelectMenu extends Select<PFSelectMenu, BaseEvent<PFSelectMenu>, PFSelectMenuOption> {

    static final String TRIGGER_SELECTOR = ".ui-selectonemenu-trigger";
    static final String OPTION_SELECTOR = ".ui-selectonemenu-item";

    @Override
    protected void initEvents() {
        events = new BaseEvent<>(this);
    }

    @Override
    protected List<PFSelectMenuOption> getOptions() {
        return owner.find().allAs(find().byId(id()+"_panel").find().bySelectorAll(OPTION_SELECTOR),PFSelectMenuOption.class);
    }

    @Override
    public PFSelectMenu expand() {
        find().bySelector(TRIGGER_SELECTOR).event().click();
        return this;
    }

}
