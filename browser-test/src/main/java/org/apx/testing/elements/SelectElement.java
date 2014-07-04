package org.apx.testing.elements;

import org.apx.testing.browser.ScriptLoader;
import org.apx.testing.elements.interfaces.IOption;
import org.apx.testing.elements.interfaces.ISelect;

import java.util.List;

/**
 * Implementation of 'select' element
 */
public class SelectElement extends Element<SelectElement, BaseEvent<SelectElement>> implements ISelect<SelectElement, OptionElement> {

    List<OptionElement> options;

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
        options().get(index).select();
        return this;
    }

    @Override
    public SelectElement selectAllAt(int[] indexes) {
        for (int index : indexes) {
            options().get(index).select();
        }
        return this;
    }

    @Override
    public SelectElement selectByValue(String value) {
        for (OptionElement optionElement : options()) {
            if(value.equals(optionElement.val())){
                optionElement.select();
                break;
            }
        }

        return this;
    }

    @Override
    public SelectElement selectAllByValue(String[] value) {
        for (String s : value) {
            for (OptionElement optionElement : options()) {
                if(s.equals(optionElement.val())){
                    optionElement.select();
                    break;
                }
            }
        }
        return this;
    }

    @Override
    public SelectElement selectByLabel(String value) {
        for (OptionElement optionElement : options()) {
            if(value.equals(optionElement.text())){
                optionElement.select();
                break;
            }
        }

        return this;
    }

    @Override
    public SelectElement selectAllByLabel(String[] value) {
        for (String s : value) {
            for (OptionElement optionElement : options()) {
                if(s.equals(optionElement.text())){
                    optionElement.select();
                    break;
                }
            }
        }
        return this;
    }

    @Override
    public SelectElement selectAll() {
        for (OptionElement optionElement : options()) {
            optionElement.select();
        }

        return this;
    }

    @Override
    public SelectElement deselectAll() {
        for (OptionElement optionElement : options()) {
            optionElement.deselect();
        }

        return this;
    }

    @Override
    public List<OptionElement> options() {
        if(options == null){
            options = owner.find().allAs(this.find().bySelectorAll("option"), OptionElement.class);
        }
        return options;
    }

    @Override
    protected void initEvents() {
        events = new BaseEvent<>(this);
    }

    @Override
    public String val() {
        //Sometimes in multiselects value attribute returns only first selected option. this is a workaround
        if("true".equalsIgnoreCase((attr("multiple") + "").trim())){
            ScriptLoader.loadFunction(owner, "multiSelectValue");
            return (String) owner.js().executeScript("return multiSelectValue(arguments[0]);",this.webElement());
        }
        return super.val();
    }
}
