package org.apx.testing.elements.primefaces;

import org.apx.testing.elements.BaseEvent;
import org.apx.testing.elements.Element;
import org.apx.testing.elements.interfaces.Selectable;

/**
 * Created by oleg on 10.07.2014.
 */
public class PFRadio extends Element<PFRadio,BaseEvent<PFRadio>> implements Selectable<PFRadio> {

    @Override
    public PFRadio check() {
        return !hasClass("ui-state-active") ? event().click() : this;
    }

    @Override
    public PFRadio uncheck() {
        throw new UnsupportedOperationException("Radio button can't be unchecked by itself");
    }

    @Override
    public PFRadio toggle() {
        return event().click();
    }

    @Override
    protected void initEvents() {
        events = new BaseEvent<>(this);
    }

}
