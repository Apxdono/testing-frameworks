package org.apx.testing.testelements;

import org.apx.testing.browser.Browser;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by oleg on 09.07.2014.
 */
public class AbstractTest {

    Browser b;
    Logger LOG;

    @Before
    public void init(){
        b = Browser.instance();
        LOG = LoggerFactory.getLogger(getClass());
    }

    @After
    public void doDestroy(){
        if ( !b.getOptions().isCloseOnJVMStop() ){
            b.close();
        }
    }

}
