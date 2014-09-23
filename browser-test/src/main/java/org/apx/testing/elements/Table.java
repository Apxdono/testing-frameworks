package org.apx.testing.elements;

import java.util.Arrays;
import java.util.List;

/**
 * Created by oleg on 08.07.2014.
 */
public class Table extends Element<Table,BaseEvent<Table>> {

    private static final List<String> bodyParts = Arrays.asList(new String[]{"tbody", "thead", "tfoot"});

    Context context = Context.TBODY;
    int contextIndex = -1;

    @Override
    protected void initEvents() {
        events = new BaseEvent<>(this);
    }


    /**
     * Used to specify the part of table to search. Like tbody, tfoot, or thead.
     * Pass an empty string if you don't care where to search
     * @param q
     * @return
     */
    public Table searchContext(Context q){
        context = q;
        return this;
    }

    /**
     * 1-based index for context search.   <br/>
     * Used only fot tbody part of table cause it's allowed to have multiple tbody tags inside table. <br/>
     * By default contextIndex is ignored. If you want to ignore it manually pass a value of 0 or less.
     * @param index
     * @return
     */
    public Table contextIndex(int index){
        contextIndex = index;
        return this;
    }

    public TableRow row(int index){
        return find().bySelector(String.format(makeSelector("tr:nth-child(%d)"), index)).as(TableRow.class);
    }

    public List<HtmlElement> column(int index){
        return find().bySelectorAll(String.format(makeSelector("tr > td:nth-child(%d)"), index));
    }


    /**
     * Looks for a 'td' cell in a given search context that mathces index
     * @param row
     * @param column
     * @return
     */
    public HtmlElement cell(int row, int column){
        return find().bySelector(String.format(makeSelector("tr:nth-child(%d) > td:nth-child(%d)"), row, column));
    }

    public HtmlElement captionCell(int row, int column){
        return find().bySelector(String.format(makeSelector("tr:nth-child(%d) > th:nth-child(%d)"), row, column));
    }

    public List<TableRow> rows(){
        return owner.find().allAs(find().bySelectorAll("tr"), TableRow.class);
    }

    protected String makeSelector(String suffix){
        StringBuilder result = new StringBuilder(cssSelector());
        if(!Context.NONE.equals(context)){
            result.append(" > ");
            if(Context.TBODY.equals(context) && contextIndex > 0){
                result.append(context.name().toLowerCase()).append(String.format(":nth-child(%d)",contextIndex));
            } else {
                result.append(context.name().toLowerCase());
            }
        }
        result.append(" > ").append(suffix);

        return result.toString();
    }

    public static enum Context {
        NONE,
        TBODY,
        THEAD,
        TFOOT;
    }

}
