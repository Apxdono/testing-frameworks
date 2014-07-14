package org.apx.testing.elements;

import org.apx.testing.elements.interfaces.Selectable;

/**
 * Created by oleg on 10.07.2014.
 */
public class Ckeckbox extends Element<Ckeckbox,BaseEvent<Ckeckbox>> implements Selectable<Ckeckbox> {
    @Override
    protected void initEvents() {
        events = new BaseEvent<>(this);
    }

    @Override
    public Ckeckbox check() {
        if(!webElement().isSelected()) event().click();
        return this;
    }

    @Override
    public Ckeckbox uncheck() {
        if(webElement().isSelected()) event().click();
        return this;
    }

    @Override
    public Ckeckbox toggle() {
        return event().click();
    }
}
