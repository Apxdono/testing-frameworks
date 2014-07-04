package org.apx.testing.browser;

import com.opera.core.systems.OperaDriver;
import org.apx.testing.core.ElementFactory;
import org.apx.testing.core.ICommonElementFactory;
import org.apx.testing.elements.HtmlElement;
import org.apx.testing.utils.Messages;
import org.apx.testing.utils.PropertiesLoader;
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

/**
 *  Entry point class for testing. Provides all tools for working with browser and elements.
 */
public class Browser {
    static Logger LOG = LoggerFactory.getLogger(Browser.class);

    BrowserOptions options;
    Properties properties;
    WebDriver driver;
    ICommonElementFactory el;

    private static class SingletonHolder {
        public static final Browser HOLDER_INSTANCE = new Browser().init();
    }

    /**
     * Get a default ready to go instance that uses the default constructor
     *
     * @return instance
     */
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

    public Browser switchToTab(int index){
        List<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(index));
        return this;
    }

    public Browser switchToFrame(int index){
        driver.switchTo().frame(index);
        return this;
    }

    public Browser switchToFrame(String frame){
        driver.switchTo().frame(frame);
        return this;
    }

    public Browser switchToFrame(HtmlElement frame){
        driver.switchTo().frame(frame.webElement());
        return this;
    }

    public Browser switchBackFromFrame(){
        driver.switchTo().defaultContent();
        return this;
    }


    /**
     * Build a web driver wait
     *
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
        manageShutdownOnJVMStop();
        return this;
    }

    protected WebDriver initDriver() {
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
//                dc.setCapability("opera.idle", true);
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


    protected void manageShutdownOnJVMStop() {
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
