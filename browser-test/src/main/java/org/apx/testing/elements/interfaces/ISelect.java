package org.apx.testing.elements.interfaces;

import java.util.List;

/**
 * Created by oleg on 02.07.2014.
 */
public interface ISelect<SelfType, OptType> {

    SelfType expand();

    SelfType select(IOption option);

    SelfType selectAll(List<IOption> option);

    SelfType selectAt(int index);

    SelfType selectAllAt(int[] indexes);

    SelfType selectByValue(String value);

    SelfType selectAllByValue(String[] value);

    SelfType selectByLabel(String value);

    SelfType selectAllByLabel(String[] value);

    SelfType selectAll();

    SelfType deselectAll();

    List<OptType> getOptions();

}
