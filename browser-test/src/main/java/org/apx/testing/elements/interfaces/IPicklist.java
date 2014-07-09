package org.apx.testing.elements.interfaces;

import java.util.List;

/**
 * Created by oleg on 09.07.2014.
 */
public interface IPicklist<SelfType, OptType> {

    /**
     * Select this option in picklist
     * @param option to be selected
     * @return self reference
     */
    SelfType select(IOption option);

    /**
     * Deselect this option in picklist
     * @param option to be deselected
     * @return self reference
     */
    SelfType deselect(IOption option);

    /**
     * Select these picklist options
     * @param option to be selected
     * @return self reference
     */
    SelfType selectAll(List<IOption> option);

    /**
     * Deselect these picklist options
     * @param option to be deselected
     * @return self reference
     */
    SelfType deselectAll(List<IOption> option);

    /**
     * Select option at index (from 0)
     * @param index
     * @return self reference
     */
    SelfType selectAt(int index);

    /**
     * Deselect option at index (from 0)
     * @param index
     * @return self reference
     */
    SelfType deselectAt(int index);

    /**
     * Select options at indexes (for selects with multiple attribute)
     * @param indexes
     * @return self reference
     */
    SelfType selectAllAt(int[] indexes);

    /**
     * Deselect options at indexes (for selects with multiple attribute)
     * @param indexes
     * @return self reference
     */
    SelfType deselectAllAt(int[] indexes);

    /**
     * Select option by it's text
     * @param value
     * @return self reference
     */
    SelfType selectByLabel(String value);

    /**
     * Deselect option by it's text
     * @param value
     * @return self reference
     */
    SelfType deselectByLabel(String value);

    /**
     * Select all options
     * @return self reference
     */
    SelfType selectAll();

    /**
     * Deselect all options
     * @return self reference
     */
    SelfType deselectAll();

    /**
     * Get list of available options
     * @return options or emty list
     */
    List<OptType> availableOptions();

    /**
     * Get list of selected options
     * @return options or emty list
     */
    List<OptType> selectedOptions();

}
