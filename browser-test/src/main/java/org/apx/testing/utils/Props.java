package org.apx.testing.utils;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by oleg on 22.09.2014.
 */
public class Props {

    static Logger LOG = LoggerFactory.getLogger(Props.class);
    static final String ignoreRegex = "(sun|com/sun|javafx|apache|sun/rmi|maven|org/cyberneko|org/eclipse|net/sourceforge|javax|trax|com/steadystate|MediaErrors|RuntimeBundle.properties|log4j.properties|logger.properties)";

    Map<Object, Object> props = new HashMap<>();



    public static Props getInstance(){
        return SingletonHolder.HOLDER_INSTANCE;
    }


    private static class SingletonHolder {
        public static final Props HOLDER_INSTANCE = new Props();
    }

    private Props(){
        Set<String> properties = new Reflections(new ResourcesScanner()).getResources(Pattern.compile(".*\\.properties"));
        Set<String> ignored = new HashSet<>();
        Pattern p = Pattern.compile(ignoreRegex);

        for (String property : properties) {
            Matcher m = p.matcher(property);
            if(m.find()){
                ignored.add(property);
            }
        }
        properties.removeAll(ignored);
        LOG.debug("All properties :");
        for (String property : properties) {
            LOG.debug("{}",property);
            try {
                Properties pr = new Properties();
                pr.load(Props.class.getClassLoader().getResourceAsStream(property));
                props.putAll(pr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOG.info("Properties loaded");
    }

    public static String property(String key){
        return String.valueOf(getInstance().props.get(key));
    }


    public static Integer intProperty(String key){
        return Integer.valueOf(String.valueOf(getInstance().props.get(key)));
    }

    public static Boolean boolProperty(String key){
        return Boolean.valueOf(String.valueOf(getInstance().props.get(key)));
    }


}
