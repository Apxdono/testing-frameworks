package org.apx.testing.browser;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Loads scripts from classpath. Used internally.
 */
public final class ScriptLoader {

    static Logger LOG = LoggerFactory.getLogger(ScriptLoader.class);
    static String JS_EXT = ".js";
    static String CHECK_FUNCTION_EXISTS = "return window[arguments[0]] ? true : false;";


    public static void loadFunction(Browser browser, String functionName) {
        Boolean b = (Boolean) browser.js().executeScript(CHECK_FUNCTION_EXISTS, functionName);
        if (b != null && b.equals(Boolean.TRUE)) {
            LOG.debug("Script for function '{}' is already present",functionName);
            return;
        }

        try {
            String s = getScriptAsString(functionName);
            browser.js().executeScript(s);
            LOG.debug("Script for  function '{}' is loaded", functionName);
        } catch (IOException e) {
            LOG.error("Error loading script for function '{}'", functionName, e);
        }
    }

    protected static String getScriptAsString(String functionName) throws IOException {
        File f = new File(ScriptLoader.class.getResource("/" + functionName + JS_EXT).getPath());
        LOG.debug("File found: {}", f != null);
        return FileUtils.readFileToString(f, "UTF-8");
    }

}
