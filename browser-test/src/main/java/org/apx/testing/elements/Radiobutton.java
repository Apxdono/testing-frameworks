package org.apx.testing.elements;

import org.apx.testing.elements.interfaces.Selectable;

/**
 * Created by oleg on 10.07.2014.
 */
public class Radiobutton extends Element<Radiobutton,BaseEvent<Radiobutton>> implements Selectable<Radiobutton> {

    @Override
    protected void initEvents() {
        events = new BaseEvent<>(this);
    }

    @Override
    public Radiobutton check() {
        if(!webElement().isSelected()) event().click();
        return this;
    }

    @Override
    public Radiobutton uncheck() {
        if(!webElement().isSelected()) event().click();
        return this;
    }

    @Override
    public Radiobutton toggle() {
        if(!webElement().isSelected()) event().click();
        return this;
    }
}
