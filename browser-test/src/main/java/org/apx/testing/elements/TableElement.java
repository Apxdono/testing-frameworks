package org.apx.testing.elements;

import java.util.List;

/**
 * Created by oleg on 08.07.2014.
 */
public class TableElement extends Element<TableElement,BaseEvent<TableElement>> {

    @Override
    protected void initEvents() {
        events = new BaseEvent<>(this);
    }

    public TableRowElement row(int index){
        return find().bySelector(String.format("tr:nth-child(%d)",index)).as(TableRowElement.class);
    }

    public List<HtmlElement> column(int index){
        return find().bySelectorAll(String.format("tr td:nth-child(%d)", index));
    }

    public HtmlElement headerCell(int row, int column){
        return find().bySelector(String.format("tr:nth-child(%d) th:nth-child(%d)", row, column));
    }

    public HtmlElement cell(int row, int column){
        return find().bySelector(String.format("tr:nth-child(%d) td:nth-child(%d)", row, column));
    }

    public List<TableRowElement> rows(){
        return owner.find().allAs(find().bySelectorAll("tr"), TableRowElement.class);
    }




}
