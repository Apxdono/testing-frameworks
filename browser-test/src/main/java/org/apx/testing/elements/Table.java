package org.apx.testing.elements;

import java.util.List;

/**
 * Created by oleg on 08.07.2014.
 */
public class Table extends Element<Table,BaseEvent<Table>> {

    @Override
    protected void initEvents() {
        events = new BaseEvent<>(this);
    }

    public TableRow row(int index){
        return find().bySelector(String.format("tr:nth-child(%d)",index)).as(TableRow.class);
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

    public List<TableRow> rows(){
        return owner.find().allAs(find().bySelectorAll("tr"), TableRow.class);
    }




}
