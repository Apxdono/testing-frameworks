package org.apx.testing.elements;

import org.apx.testing.elements.interfaces.IOption;
import org.apx.testing.elements.interfaces.ISelect;

import java.util.List;

/**
 * Created by oleg on 15.07.2014.
 */
public abstract class Select<SelfType extends Select<SelfType, EV, OPT>, EV extends BaseEvent<SelfType>, OPT extends IOption> extends Element<SelfType, EV> implements ISelect<SelfType,OPT> {

    protected List<OPT> options;

    @Override
    public SelfType select(IOption option) {
        option.select();
        return (SelfType) this;
    }

    @Override
    public SelfType selectAll(List<IOption> option) {
        for (IOption iOption : option) {
            iOption.select();
        }
        return (SelfType) this;
    }

    @Override
    public SelfType selectAt(int index) {
        options().get(index-1).select();
        return (SelfType) this;
    }

    @Override
    public SelfType selectAllAt(int[] indexes) {
        for (int index : indexes) {
            selectAt(index);
        }
        return (SelfType) this;
    }

    @Override
    public SelfType selectByValue(String value) {
        for (OPT optionElement : options()) {
            if(value.equals(optionElement.val())){
                optionElement.select();
                break;
            }
        }

        return (SelfType) this;
    }

    @Override
    public SelfType selectAllByValue(String[] value) {
        for (String s : value) {
            selectByValue(s);
        }
        return (SelfType) this;
    }

    @Override
    public SelfType selectByLabel(String value) {
        for (OPT optionElement : options()) {
            if(value.equals(optionElement.text())){
                optionElement.select();
                break;
            }
        }

        return (SelfType) this;
    }

    @Override
    public SelfType selectAllByLabel(String[] value) {
        for (String s : value) {
            selectByLabel(s);
        }
        return (SelfType) this;
    }

    @Override
    public SelfType selectAll() {
        for (OPT option : options()) {
            option.select();
        }
        return (SelfType) this;
    }

    @Override
    public SelfType deselectAll() {
        for (OPT option : options()) {
            option.deselect();
        }
        return (SelfType) this;
    }

    @Override
    public List<OPT> options(){
        if( options == null ){
            options = getOptions();
        }
        return options;
    }

    protected abstract List<OPT> getOptions();

    @Override
    public SelfType val(String value) {
        throw new UnsupportedOperationException("Method not supported. Please use select methods provided in Select class");
    }

    @Override
    public SelfType val(String value, boolean append) {
        throw new UnsupportedOperationException("Method not supported. Please use select methods provided in Select class");
    }
}
