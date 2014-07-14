package org.apx.testing.browser;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Loads scripts from classpath into browser 'window' DOM element.
 */
public final class ScriptLoader {

    static final Logger LOG = LoggerFactory.getLogger(ScriptLoader.class);
    static final String JS_EXT = ".js";
    static final String CHECK_FUNCTION_EXISTS = "return window[arguments[0]] ? true : false;";

    /**
     * Load a js file with name equal to the function name, that is going to be used later.
     * @param browser browser instance to load script
     * @param functionName name of the function to be used ( also the file name without extension)
     */
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

    /**
     * Read a javascript file as a single string
     * @param functionName file name
     * @return string with js code.
     * @throws IOException if file could not be read or missing
     */
    protected static String getScriptAsString(String functionName) throws IOException {
        File f = new File(ScriptLoader.class.getResource("/" + functionName + JS_EXT).getPath());
        LOG.debug("File found: {}", f != null);
        return FileUtils.readFileToString(f, "UTF-8");
    }

}
