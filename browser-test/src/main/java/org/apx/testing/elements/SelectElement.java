package org.apx.testing.elements;

import org.apx.testing.browser.ScriptLoader;
import org.apx.testing.elements.interfaces.IOption;
import org.apx.testing.elements.interfaces.ISelect;

import java.util.List;

/**
 * Implementation of 'select' element
 */
public class SelectElement extends Select<SelectElement, BaseEvent<SelectElement>, OptionElement>{


    @Override
    protected void initEvents() {
        events = new BaseEvent<>(this);
    }

    @Override
    public String val() {
        //Sometimes in multiselects value attribute returns only first selected option. The reason is not clear to me so this is a workaround 4 now
        if("true".equalsIgnoreCase((attr("multiple") + "").trim())){
            ScriptLoader.loadFunction(owner, "multiSelectValue");
            return (String) owner.js().executeScript("return multiSelectValue(arguments[0]);",this.webElement());
        }
        return super.val();
    }



    @Override
    protected List<OptionElement> getOptions() {
        return owner.find().allAs(find().bySelectorAll("option"),OptionElement.class);
    }

    @Override
    public SelectElement expand() {
        return event().click();
    }
}
