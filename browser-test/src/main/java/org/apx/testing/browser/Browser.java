package org.apx.testing.browser;

import com.opera.core.systems.OperaDriver;
import org.apx.testing.core.ElementFactory;
import org.apx.testing.core.ICommonElementFactory;
import org.apx.testing.elements.HtmlElement;
import org.apx.testing.utils.Messages;
import org.apx.testing.utils.PropertiesLoader;
import org.apx.testing.utils.Props;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Entry point class for testing. Provides all tools for working with browser and elements.
 */
public class Browser {
    private static final Logger LOG = LoggerFactory.getLogger(Browser.class);

    private final BrowserOptions options;
    private final Properties properties;
    private WebDriver driver;
    private ICommonElementFactory el;

    private static class SingletonHolder {
        public static final Browser HOLDER_INSTANCE = new Browser().init();
    }

    /**
     * Get a default ready to go instance that uses the default constructor
     *
     * @return instance
     */
    @SuppressWarnings("SameReturnValue")
    public static Browser instance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

    /**
     * Create a browser instance that uses 'browser.properties' file as options source
     */
    public Browser() {
        this("browser.properties");
    }


    /**
     * Create a browser instance that uses '&lt;your-custom-name&gt;.properties' file as options source
     *
     * @param propetyFile
     */
    public Browser(String propetyFile) {
        properties = PropertiesLoader.load(propetyFile);
        options = new BrowserOptions(PropertiesLoader.propertiesAsMap(properties));
    }

    /**
     * Navigate to an url
     *
     * @param url where to go
     * @return self-reference
     */
    public Browser get(String url) {
        driver.get(url);
        waitForPageLoad();
        return this;
    }

    /**
     * Wait for a specified amount of seconds
     *
     * @param sec how long to wait
     * @return self-reference
     */
    public Browser hold(int sec) {
        return holdInMillis(sec * 1000);
    }

    /**
     * This is a Thread.sleep wrapper. Nothing interesting...
     *
     * @param millis
     * @return self-reference
     */
    public Browser holdInMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            LOG.error("Error during hold. ", e);
        }
        return this;
    }

    /**
     * Hold for some time or until the condition passes
     *
     * @param sec       max time to wait
     * @param condition breaks the hold
     * @return self-reference
     */
    public Browser hold(int sec, final Check condition) {
        final Browser self = this;
        try {
            getWait(sec).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return condition.check(self);
                }
            });
        } catch (TimeoutException t) {
            LOG.info(Messages.TIMEOUT_EXCEEDED, t);
        }
        return this;
    }

    /**
     * Switch to another browser page, popup, tab
     * @param index 0-based index of the window
     * @return self reference
     */
    public Browser switchToTab(int index) {
        List<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver = driver.switchTo().window(tabs.get(index));
        return this;
    }

    /**
     * Close the tab or page
     * @param index 0-based index of the window
     * @return self reference
     */
    public Browser closeTab(int index){
        driver.switchTo().defaultContent();
        String currentHandle = driver.getWindowHandle();
        List<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(index)).close();
        tabs.remove(index);
        if(tabs.indexOf(currentHandle) != -1) {
            driver = driver.switchTo().window(currentHandle);
        } else {
            if(index > 0){
                prevTab();
            } else if( index < tabs.size()-1){
                nextTab();
            }
        }
        return this;
    }

    /**
     * Switch to next tab relative to tab currently in use.
     * @return self reference
     */
    public Browser nextTab(){
        driver.switchTo().defaultContent();
        List<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        int ind = tabs.indexOf(driver.getWindowHandle());
        if(ind < tabs.size() - 1){
            driver = driver.switchTo().window(tabs.get(ind+1));
            //Sometimes this piece of shit doesn't see that window is opened, so it needs to wait a bit.
            //The window handle is present tho..
            holdInMillis(500);
        }
        return this;
    }

    /**
     * Close next tab relative to tab currently in use.
     * @return self reference
     */
    public Browser closeNextTab(){
        driver.switchTo().defaultContent();
        List<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        int ind = tabs.indexOf(driver.getWindowHandle());
        if(ind < tabs.size() - 1){
            closeTab(ind+1);
        }
        return this;
    }

    /**
     * Switch to previous tab relative to tab currently in use.
     * @return self reference
     */
    public Browser prevTab(){
        driver.switchTo().defaultContent();
        List<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        int ind = tabs.indexOf(driver.getWindowHandle());
        if(ind > 0){
            driver = driver.switchTo().window(tabs.get(ind-1));
            //Sometimes this piece of shit doesn't see that window is opened, so it needs to wait a bit.
            //The window handle is present tho..
            holdInMillis(500);
        }
        return this;
    }

    /**
     * Close previous tab relative to tab currently in use.
     * @return self reference
     */
    public Browser closePrevTab(){
        driver.switchTo().defaultContent();
        List<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        int ind = tabs.indexOf(driver.getWindowHandle());
        if(ind > 0){
            closeTab(ind - 1);
        }
        return this;
    }

    /**
     * Switch to next tab relative to tab currently in use.
     * @return self reference
     */
    public Browser switchToFrame(int index) {
        driver = driver.switchTo().frame(index);
        return this;
    }

    /**
     * Switch to frame using it's id.
     * @param frame id
     * @return self reference
     */
    public Browser switchToFrame(String frame) {
        driver = driver.switchTo().frame(frame);
        return this;
    }

    /**
     * Switch to found frame and start working with it's elements. <br/>
     * Note that opera currently is buggy with switching to frames
     * @param frame to be switched to
     * @return self reference
     * @see  <a href="https://github.com/operasoftware/operadriver/issues/103">Opera frame issue</a>
     *
     */
    public Browser switchToFrame(HtmlElement frame) {
        driver = driver.switchTo().frame(frame.webElement());
        return this;
    }

    /**
     * After manipulating with frame elements call this to switch back to the context of the root window
     * @return self reference
     */
    public Browser switchBackFromFrame() {
        driver = driver.switchTo().defaultContent();
        return this;
    }


    /**
     * Build a web driver wait
     * @param sec how long to wait
     * @return a valid wait object
     */
    public WebDriverWait getWait(int sec) {
        return new WebDriverWait(driver, sec);
    }

    /**
     * Get Element lookuper instance. You can find elements with it. ;)
     *
     * @return element lookuper
     */
    public ICommonElementFactory find() {
        return el;
    }

    /**
     * If you need a WebDriver itself than be my guest
     *
     * @return web driver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Need to push some JS to a page? welcome aboard
     *
     * @return Javascript executor
     */
    public JavascriptExecutor js() {
        return (JavascriptExecutor) driver;
    }

    public WebDriver.Navigation navigate() {
        return driver.navigate();
    }

    public WebDriver.Options manage() {
        return driver.manage();
    }


    public BrowserOptions getOptions() {
        return options;
    }

    /**
     * Wait for ajax on page to perform
     *
     * @return self-reference
     */
    public Browser ajaxWait() {
        return ajaxWait(10);
    }

    /**
     * Wait for ajax on page to perform as long as you desire
     *
     * @param seconds how long to wait
     * @return self-reference
     */
    public Browser ajaxWait(int seconds) {
        holdInMillis(options.ajaxPreWait);
        hold(seconds, new Check() {
            @Override
            public Boolean check(Browser browser) {
                return (Boolean) browser.js().executeScript("return !($.active > 0)");
            }
        });
        return this;
    }

    public Browser waitForPageLoad(){
        return waitForPageLoad(10);
    }

    //TODO test functionality
    public Browser waitForPageLoad(int seconds) {
        final String[] state = {""};
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {

                        try {
                            state[0] = (((JavascriptExecutor) driver).executeScript("return document.readyState")+"").toLowerCase();
                        } catch (Exception e) {
                            //Ignoring bullshit
                        }
                        return "complete".equals(state[0]) || "loaded".equals(state[0]);
                    }
                };

        try {
            getWait(seconds).until(pageLoadCondition);
        } catch (TimeoutException te){
            if(!"interactive".equals(state[0])){
                throw te;
            }
        }
        return this;
    }



    /**
     * Initialize the browser
     *
     * @return self-reference
     */
    public Browser init() {
        LOG.info("Initializing browser. {}", options);
        driver = initDriver();
        switch (options.queryingType) {
            default:
                el = new ElementFactory(this);
                break;
        }
        if (options.maximize && !options.type.equals(BrowserType.OPERA)) {
            driver.manage().window().maximize();
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        manageShutdownOnJVMStop();
        return this;
    }

    WebDriver initDriver() {
        WebDriver result;
        switch (options.type) {
            case CHROME:
                result = new ChromeDriver();
                break;
            case IE:
                result = new InternetExplorerDriver();
                break;
            case OPERA:
                DesiredCapabilities dc = DesiredCapabilities.opera();
                dc.setCapability("opera.idle", true);
                result = new OperaDriver(dc);
                break;
            case SAFARI:
                result = new SafariDriver();
                break;
            default:
                result = new FirefoxDriver();
                break;
        }
        return result;
    }

    /**
     * Correctly close the driver. It's not necessary to call <br/>
     * if you already used 'close.on.jvm.stop=true' in properties file
     */
    public void close() {
        driver.quit();
    }


    void manageShutdownOnJVMStop() {
        if (options.closeOnJVMStop) {
            LOG.info("Shutdown will be managed in auto mode on jvm stop.");
            final Browser browser = this;
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    LOG.info("Closing driver");
                    try {
                        browser.close();
                    } catch (Exception t) {
                        LOG.error("Error during closing driver. Details below:", t);
                    }
                }
            });
        } else {
            LOG.info("Shutdown will be managed in manual mode. You should close the driver as soon as it's no longer needed.");
        }
    }

}
