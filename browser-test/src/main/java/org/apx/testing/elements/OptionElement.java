package org.apx.testing.elements;

import org.apx.testing.elements.interfaces.IOption;

/**
 * Implementation for html elements with tag 'option'
 */
public class OptionElement extends Element<OptionElement, BaseEvent<OptionElement>> implements IOption<OptionElement> {

    @Override
    public OptionElement select() {
        if(!this.webElement().isSelected()){
            this.event().click();
        }
        return this;
    }

    @Override
    public OptionElement deselect() {

        if(this.webElement().isSelected()){
            return this.event().click();
        }
        return this;
    }

    @Override
    protected void initEvents() {
        events = new BaseEvent<>(this);
    }
}
