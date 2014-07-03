package org.apx.testing.elements;

import org.apache.commons.lang3.StringUtils;
import org.apx.testing.browser.Browser;
import org.apx.testing.browser.ScriptLoader;
import org.apx.testing.core.EFElement;
import org.apx.testing.core.IBasicElementFactory;
import org.openqa.selenium.WebElement;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * An abstraction of Web element with handy methods and stuff. Used by elements only
 */
abstract class Element<SelfType extends Element<SelfType, EV>, EV extends BaseEvent<SelfType>> extends EFElement<SelfType> {

    protected IBasicElementFactory localFactory;
    protected State<SelfType> state;
    protected EV events;

    @Override
    public WebElement webElement() {
        return target;
    }

    /**
     * Get an element's factory for finding elements using current element as a base of search
     * @return element factory
     */
    public IBasicElementFactory find() {
        if (localFactory == null) {
            localFactory = new ElementFactoryLocal(owner.find(), this);
        }
        return localFactory;
    }

    /**
     * Create a delay for element's state to change to specified later, via call of method in {@link org.apx.testing.elements.State}
     * @param timeout max delay time
     * @return reference to element states
     */
    public State<SelfType> waitUntilElement(int timeout) {
        if (state == null) {
            state = new State<SelfType>(owner, (SelfType) this);
        }
        state.setTimeout(timeout);
        return state;
    }

    /**
     * Get element events
     * @return reference to element events
     */
    public EV event() {
        return events;
    }

    /* ATTRIBUTES AND STUFF*/

    /**
     * Get element id
     * @return id of an element
     */
    public String id() {
        return attr(Attributes.ID);
    }

    /**
     * Get element form name
     * @return name of an element
     */
    public String formName() {
        return attr(Attributes.NAME);
    }

    /**
     * Get calculated element css selector
     * @return selector
     */
    public String cssSelector() {
        if (StringUtils.isBlank(cssSelector)) {
            cssSelector = evalSelector();
        }
        return cssSelector;
    }

    /**
     * Get all element css classes
     * @return list of classes
     */
    public List<String> classes() {
        return Arrays.asList((attr(Attributes.CLASS) + "").split(" "));
    }

    /**
     * Check if element has class
     * @param className to be found in element classes
     * @return true - if class is present, false otherwise
     */
    public boolean hasClass(String className) {
        return classes().contains(className);
    }

    /**
     * Get element attribute
     * @param attrName attribute name
     * @return attribute value
     */
    public String attr(String attrName) {
        return target.getAttribute(attrName);
    }

    /**
     * Get html text of current element
     * @return html text
     */
    public String html() {
        return (String) jsRet(Attributes.INNER_HTML);
    }

    /**
     * Get only text of current element (not html)<br/>
     * more info at <a href="http://www.w3schools.com/dom/prop_element_textcontent.asp">Text content</a>
     * @return text
     */
    public String text() {
        return (String) jsRet(Attributes.TEXT_CONTENT);
    }

    /**
     * Get element value
     * @return value if present
     */
    public String val() {
        return attr(Attributes.VALUE);
    }

    /**
     * Set value attribute of current element to value
     * @param value to be set
     * @return self reference
     */
    public SelfType val(String value) {
        return val(value, false);
    }

    /**
     * Set or append value to value attribute of current element
     * @param value to be set
     * @param append true - append, false - set
     * @return self reference
     */
    public SelfType val(String value, boolean append) {
        if (!append) target.clear();
        target.sendKeys(value);
        return (SelfType) this;
    }


    /*ELEMENTS AROUND AND STUFF*/

    /**
     * Get parent of current element
     * @return parent element or null
     */
    public HtmlElement parent() {
        if (parent == null) {
            parent = owner.find().wrap((WebElement) jsRet(Attributes.PARENT_NODE));
        }
        return parent;
    }

    /**
     * Get previous element
     * @return element or null
     */
    public HtmlElement prev() {
        return owner.find().wrap((WebElement) jsRet(Attributes.PREVIOUS_NODE));
    }

    /**
     * Get next element
     * @return element or null
     */
    public HtmlElement next() {
        return owner.find().wrap((WebElement) jsRet(Attributes.NEXT_NODE));
    }

    /**
     * Get direct children of this element
     * @return list of elements or empty collection
     */
    public List<HtmlElement> children() {
        return find().byXpathAll("*");
    }

    /**
     * Get specific child of this element
     * @param index child position
     * @return element or null
     */
    public HtmlElement child(int index) {
        return find().byXpath(String.format("*[position()=%d]", index + 1));
    }

    /**
     * Get all elements (children and their children incluided)
     * @return list of elements or empty collection
     */
    public List<HtmlElement> siblings() {
        return find().byXpathAll(".//*");
    }

    /**
     * If the element war rerendered or somethind it becomes stale in WebDriver. This will make element usable again.
     * @return
     */
    public SelfType revive() {
        return (SelfType) owner.find().bySelector(cssSelector()).as(getClass());
    }

    @Override
    public <E extends EFElement> E as(Class<E> eClass) {
        return owner.find().as(this, eClass);
    }


     /* INNER METHODS AND CLASSES*/

    protected String evalSelector() {
        LoggerFactory.getLogger(getClass()).info("evaluating css selector");
        ScriptLoader.loadFunction(owner, "fullPath");
        return (String) owner.js().executeScript("return fullPath(arguments[0]);", this.target);
    }

    protected Object jsRet(String function) {
        if (StringUtils.isNotBlank(function)) {
            return owner.js().executeScript("return arguments[0][arguments[1]]", target, function);
        } else {
            return target;
        }
    }

    @Override
    protected SelfType init(Browser browser, WebElement element) {
        LoggerFactory.getLogger(getClass()).info("initialized");
        owner = browser;
        target = element;
        cssSelector = evalSelector();
        initEvents();
        return (SelfType) this;
    }

    @Override
    protected SelfType init(EFElement element) {
        super.init(element);
        initEvents();
        return (SelfType) this;
    }

    protected abstract void initEvents();

    protected static class Attributes {
        public static final String CLASS = "className";
        public static final String NAME = "name";
        public static final String ID = "id";
        public static final String INNER_HTML = "innerHTML";
        public static final String PARENT_NODE = "parentNode";
        public static final String TEXT_CONTENT = "textContent";
        public static final String NEXT_NODE = "nextElementSibling";
        public static final String PREVIOUS_NODE = "previousElementSibling";
        public static final String VALUE = "value";

        private Attributes() {

        }
    }


}
