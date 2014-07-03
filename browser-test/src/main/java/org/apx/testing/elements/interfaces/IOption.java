package org.apx.testing.elements.interfaces;

/**
 * Generalization of element "option" for select menues
 */
public interface IOption<SelfType> {

    /**
     * Select current option
     * @return self reference
     */
    SelfType select();

    /**
     * Deselect current option
     * @return self reference
     */
    SelfType deselect();
}
