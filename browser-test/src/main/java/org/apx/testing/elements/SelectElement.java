package org.apx.testing.elements;

import org.apx.testing.elements.interfaces.IOption;
import org.apx.testing.elements.interfaces.ISelect;

import java.util.List;

/**
 * Implementation of 'select' element
 */
public class SelectElement extends Element<SelectElement, BaseEvent<SelectElement>> implements ISelect<SelectElement, OptionElement> {

    @Override
    public SelectElement expand() {
        return this.event().click();
    }

    @Override
    public SelectElement select(IOption option) {
        option.select();
        return this;
    }

    @Override
    public SelectElement selectAll(List<IOption> option) {
        for (IOption iOption : option) {
            iOption.select();
        }
        return this;
    }

    @Override
    public SelectElement selectAt(int index) {

        return null;
    }

    @Override
    public SelectElement selectAllAt(int[] indexes) {
        return null;
    }

    @Override
    public SelectElement selectByValue(String value) {
        return null;
    }

    @Override
    public SelectElement selectAllByValue(String[] value) {
        return null;
    }

    @Override
    public SelectElement selectByLabel(String value) {
        return null;
    }

    @Override
    public SelectElement selectAllByLabel(String[] value) {
        return null;
    }

    @Override
    public SelectElement selectAll() {
        return null;
    }

    @Override
    public SelectElement deselectAll() {
        return null;
    }

    @Override
    public List<OptionElement> options() {
        return owner.find().allAs(this.find().bySelectorAll("option"), OptionElement.class);
    }

    @Override
    protected void initEvents() {
        events = new BaseEvent<>(this);
    }
}
