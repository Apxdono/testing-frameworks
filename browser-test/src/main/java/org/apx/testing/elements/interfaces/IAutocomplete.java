package org.apx.testing.elements.interfaces;

import org.apx.testing.elements.Element;
import org.apx.testing.elements.HtmlElement;

import java.util.List;

/**
 * Created by oleg on 22.09.2014.
 */
public interface IAutocomplete<SelfType> {

    /**
     * Max timeout for data to load
     * @param ms time in millis
     * @return self reference
     */
    SelfType timeout(int ms);


    /**
     * Search the value using autocomplete
     * @param val value for autocomplete
     * @return self reference
     */
    SelfType complete(String val);

    /**
     * Select value by index ( 1-based )
     * @param index to select
     * @return self reference
     */
    SelfType selectAt(int index);


    /**
     * Get the popup panel for this element
     * @return
     */
    HtmlElement panel();

    /**
     * List of all results
     * @return list of results
     */
    List<HtmlElement> results();

}
