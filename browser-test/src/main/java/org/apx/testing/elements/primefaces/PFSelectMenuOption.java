package org.apx.testing.elements.primefaces;

import org.apx.testing.elements.BaseEvent;
import org.apx.testing.elements.Element;
import org.apx.testing.elements.interfaces.IOption;

/**
 * Created by oleg on 15.07.2014.
 */
public class PFSelectMenuOption extends Element<PFSelectMenuOption, BaseEvent<PFSelectMenuOption>> implements IOption<PFSelectMenuOption> {
    @Override
    protected void initEvents() {
        events = new BaseEvent<>(this);
    }

    @Override
    public PFSelectMenuOption select() {
        return !hasClass(".ui-state-highlight")? event().click():this;
    }

    @Override
    public PFSelectMenuOption deselect() {
        return hasClass(".ui-state-highlight")? event().ctrlclick():this;
    }
}
