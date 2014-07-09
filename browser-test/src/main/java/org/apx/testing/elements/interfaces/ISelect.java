package org.apx.testing.elements.interfaces;

import java.util.List;

/**
 * Generalization of select menu on page
 */
public interface ISelect<SelfType, OptType> {

    /**
     * Expand the menu
     * @return self reference
     */
    SelfType expand();

    /**
     * Select this option of menu
     * @param option to be selected
     * @return self reference
     */
    SelfType select(IOption option);

    /**
     * Select these menu options
     * @param option to be selected
     * @return self reference
     */
    SelfType selectAll(List<IOption> option);

    /**
     * Select option at index
     * @param index  1 based
     * @return self reference
     */
    SelfType selectAt(int index);

    /**
     * Select options at indexes (for selects with multiple attribute)
     * @param indexes 1 based
     * @return self reference
     */
    SelfType selectAllAt(int[] indexes);

    /**
     * Select option by it's value
     * @param value option value
     * @return self reference
     */
    SelfType selectByValue(String value);

    /**
     * Select options with values (for selects with multiple attribute)
     * @param value
     * @return self reference
     */
    SelfType selectAllByValue(String[] value);

    /**
     * Select option by it's text
     * @param value
     * @return self reference
     */
    SelfType selectByLabel(String value);

    /**
     * Select options by their text (for selects with multiple attribute)
     * @param value
     * @return self reference
     */
    SelfType selectAllByLabel(String[] value);

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
     * Get list of options
     * @return options or emty list
     */
    List<OptType> options();

}
