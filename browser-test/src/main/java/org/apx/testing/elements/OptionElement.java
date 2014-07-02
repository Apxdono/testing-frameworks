package org.apx.testing.elements;

import org.apx.testing.elements.interfaces.IOption;

/**
 * Created by oleg on 02.07.2014.
 */
public class OptionElement extends Element<OptionElement, BaseEvent<OptionElement>> implements IOption<OptionElement> {

    @Override
    public OptionElement select() {
        if(!this.getWebElement().isSelected()){
            this.event().click();
        }
        return this;
    }

    @Override
    public OptionElement deselect() {

        if(this.getWebElement().isSelected()){
            return this.event().click();
        }
        return this;
    }

    @Override
    protected void initEvents() {
        events = new BaseEvent<>(this);
    }
}
