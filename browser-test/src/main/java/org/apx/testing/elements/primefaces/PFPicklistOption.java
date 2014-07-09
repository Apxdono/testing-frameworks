package org.apx.testing.elements.primefaces;

import org.apx.testing.elements.BaseEvent;
import org.apx.testing.elements.Element;
import org.apx.testing.elements.interfaces.IOption;

/**
 * Created by oleg on 09.07.2014.
 */
public class PFPicklistOption extends Element<PFPicklistOption, BaseEvent<PFPicklistOption>> implements IOption<PFPicklistOption> {
    @Override
    protected void initEvents() {
        events = new BaseEvent<>(this);
    }

    @Override
    public PFPicklistOption select() {
        if (parent().hasClass("ui-picklist-source")) {
            event().dblclick();
        }
        return this;
    }

    @Override
    public PFPicklistOption deselect() {
        if (parent().hasClass("ui-picklist-target")) {
            event().dblclick();
        }
        return this;
    }
}
