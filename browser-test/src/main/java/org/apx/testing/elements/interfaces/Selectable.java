package org.apx.testing.elements.interfaces;

/**
 * Basic interface that represents element methods which allow to toggle it's state. Eg. checkboxes or radiobuttons.
 */
public interface Selectable<T> {

    /**
     * Select element
     * @return self reference
     */
    T check();

    /**
     * Deselect element
     * @return self reference
     */
    T uncheck();

    /**
     * Toggle between selected and deselected states of the element
     * @return self reference
     */
    T toggle();
}
