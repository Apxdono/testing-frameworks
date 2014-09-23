package org.apx.testing.elements;

import java.util.List;

/**
 * Created by oleg on 08.07.2014.
 */
public class TableRow extends Element<TableRow,BaseEvent<TableRow>> {
    @Override
    protected void initEvents() {
        events = new BaseEvent<>(this);
    }

    public List<HtmlElement> cells(){
        return children();
    }

    public HtmlElement cell(int index){
        return child(index);
    }
}
