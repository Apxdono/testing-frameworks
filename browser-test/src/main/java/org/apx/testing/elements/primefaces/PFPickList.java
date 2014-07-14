package org.apx.testing.elements.primefaces;

import org.apx.testing.elements.BaseEvent;
import org.apx.testing.elements.Element;
import org.apx.testing.elements.interfaces.IOption;
import org.apx.testing.elements.interfaces.IPicklist;

import java.util.List;

/**
 * Created by oleg on 09.07.2014.
 */
public class PFPickList extends Element<PFPickList, BaseEvent<PFPickList>> implements IPicklist<PFPickList,PFPicklistOption> {

    static String SOURCE_SELECTOR = ".ui-picklist-source";
    static String TARGET_SELECTOR = ".ui-picklist-target";


    @Override
    protected void initEvents() {
        events = new BaseEvent<>(this);
    }

    @Override
    public PFPickList select(IOption option) {
        option.select();
        return this;
    }

    @Override
    public PFPickList deselect(IOption option) {
        option.deselect();
        return this;
    }

    @Override
    public PFPickList selectAll(List<IOption> option) {
        for (IOption iOption : option) {
            iOption.select();
        }
        return this;
    }

    @Override
    public PFPickList deselectAll(List<IOption> option) {
        for (IOption iOption : option) {
            iOption.deselect();
        }
        return this;
    }

    @Override
    public PFPickList selectAt(int index) {
        find().bySelector(SOURCE_SELECTOR).child(index).as(PFPicklistOption.class).select();
        return this;
    }

    @Override
    public PFPickList deselectAt(int index) {
        find().bySelector(TARGET_SELECTOR).child(index).as(PFPicklistOption.class).select();
        return this;
    }

    @Override
    public PFPickList selectAllAt(int[] indexes) {
        for (int index : indexes) {
            selectAt(index);
        }
        return this;
    }

    @Override
    public PFPickList deselectAllAt(int[] indexes) {
        for (int index : indexes) {
            deselectAt(index);
        }
        return this;
    }

    @Override
    public PFPickList selectByLabel(String value) {
        for (PFPicklistOption option : availableOptions()) {
            if(option.text().equals(value)){
                option.select();
            }
        }
        return this;
    }

    @Override
    public PFPickList deselectByLabel(String value) {
        for (PFPicklistOption option : selectedOptions()) {
            if(option.text().equals(value)){
                option.deselect();
            }
        }
        return this;
    }

    @Override
    public PFPickList selectAll() {
        find().bySelector(SOURCE_SELECTOR).parent().next().child(2).event().click().browser().holdInMillis(600).ajaxWait();
        return this;
    }

    @Override
    public PFPickList deselectAll() {
        find().bySelector(SOURCE_SELECTOR).parent().next().child(4).event().click().browser().holdInMillis(600).ajaxWait();
        return this;
    }

    @Override
    public List<PFPicklistOption> availableOptions() {
        return browser().find().allAs(find().bySelector(SOURCE_SELECTOR).children(), PFPicklistOption.class);
    }

    @Override
    public List<PFPicklistOption> selectedOptions() {
        return browser().find().allAs(find().bySelector(TARGET_SELECTOR).children(), PFPicklistOption.class);
    }
}
