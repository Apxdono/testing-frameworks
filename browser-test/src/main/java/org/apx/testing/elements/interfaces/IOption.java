package org.apx.testing.elements.interfaces;

/**
 * Created by oleg on 02.07.2014.
 */
public interface IOption<SelfType> {

    SelfType select();

    SelfType deselect();
}
