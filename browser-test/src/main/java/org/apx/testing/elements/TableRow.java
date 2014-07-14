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
        return find().bySelectorAll("td");
    }
    public List<HtmlElement> headerCells(){
        return find().bySelectorAll("th");
    }

    public HtmlElement cell(int index){
        return find().bySelector(String.format("td:nth-child(%d)",index));
    }
    public HtmlElement headerCell(int index){
        return find().bySelector(String.format("th:nth-child(%d)",index));
    }
}
