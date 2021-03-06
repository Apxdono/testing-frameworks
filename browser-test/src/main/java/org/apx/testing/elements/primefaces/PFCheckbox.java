package org.apx.testing.elements.primefaces;

import org.apx.testing.elements.BaseEvent;
import org.apx.testing.elements.Element;
import org.apx.testing.elements.interfaces.Selectable;

/**
 * Created by oleg on 10.07.2014.
 */
public class PFCheckbox extends Element<PFCheckbox, BaseEvent<PFCheckbox>> implements Selectable<PFCheckbox> {

    @Override
    public PFCheckbox check() {
        return !hasClass("ui-state-active") ? event().click() : this;
    }

    @Override
    public PFCheckbox uncheck() {
        return hasClass("ui-state-active") ? event().click() : this;
    }

    @Override
    public PFCheckbox toggle() {
        return event().click();
    }

    @Override
    protected void initEvents() {
        events = new BaseEvent<>(this);
    }
}
