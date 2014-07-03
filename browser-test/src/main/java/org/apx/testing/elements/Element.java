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
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 25.06.14
 * Time: 17:32
 * To change this template use File | Settings | File Templates.
 */
abstract class Element<SelfType extends Element<SelfType, EV>, EV extends BaseEvent<SelfType>> extends EFElement<SelfType> {

    protected IBasicElementFactory localFactory;
    protected State<SelfType> state;
    protected EV events;

    @Override
    public WebElement getWebElement(){
        return target;
    }

    public IBasicElementFactory find(){
        if(localFactory == null){
            localFactory = new ElementFactoryLocal(owner.find(),this);
        }
        return localFactory;
    }

    public State<SelfType> waitUntilElement(int timeout){
        if(state == null){
            state = new State<SelfType>(owner, (SelfType) this);
        }
        state.setTimeout(timeout);
        return state;
    }

    public EV event(){
        return events;
    }

    /* ATTRIBUTES AND STUFF*/

    public String id(){
        return attr(Attributes.ID);
    }

    public String formName(){
        return attr(Attributes.NAME);
    }

    public String cssSelector(){
        if(StringUtils.isBlank(cssSelector)){
            cssSelector = evalSelector();
        }
        return cssSelector;
    }

    public List<String> classes(){
        return Arrays.asList((attr(Attributes.CLASS) + "").split(" "));
    }

    public boolean hasClass(String className){
        return classes().contains(className);
    }

    public String attr(String attrName){
        return target.getAttribute(attrName);
    }

    public String html(){
        return (String) jsRet(Attributes.INNER_HTML);
    }

    public String text(){
        return (String) jsRet(Attributes.TEXT_CONTENT);
    }

    public String val(){
        return attr(Attributes.VALUE);
    }

    public SelfType val(String value){
        return val(value, false);
    }

    public SelfType val(String value,boolean append){
        if(!append)target.clear();
        target.sendKeys(value);
        return (SelfType) this;
    }


    /*ELEMENTS AROUND AND STUFF*/


    public HtmlElement parent(){
        if(parent == null){
            parent = owner.find().wrap((WebElement) jsRet(Attributes.PARENT_NODE));
        }
        return parent;
    }

    public HtmlElement prev(){
        return owner.find().wrap((WebElement) jsRet(Attributes.PREVIOUS_NODE));
    }

    public HtmlElement next(){
        return owner.find().wrap((WebElement) jsRet(Attributes.NEXT_NODE));
    }

    public List<HtmlElement> children(){
        return find().byXpathAll("*");
    }

    public HtmlElement child(int index) {
        return  find().byXpath(String.format("*[position()=%d]",index+1));
    }

    public List<HtmlElement> siblings(){
        return Collections.emptyList();
    }

    public SelfType revive(){
        return (SelfType) owner.find().bySelector(cssSelector()).as(getClass());
    }

    @Override
    public <E extends EFElement> E as(Class<E> eClass){
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
    protected SelfType init(Browser browser, WebElement element){
        LoggerFactory.getLogger(getClass()).info("initialized");
        owner = browser;
        target = element;
        cssSelector = evalSelector();
        initEvents();
        return (SelfType) this;
    }

    @Override
    protected SelfType init(EFElement element){
        super.init(element);
        initEvents();
        return (SelfType) this;
    }

    protected abstract void initEvents();

    protected static class Attributes{
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
