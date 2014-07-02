package org.apx.testing.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 2/2/14
 * Time: 8:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReflectionUtils {
	static Logger LOG = LoggerFactory.getLogger(ReflectionUtils.class);
    static Map<Class<?>,Constructor> constructors = new HashMap<Class<?>, Constructor>();


	/**
	 * A wrapper for creation of an object using getDeclaredConstructor.
	 * The rest is obvious
	 * @param targetClass
	 * @param argClasses
	 * @param args
	 * @return
	 */
	public static Object newInstance(Class<?> targetClass,Class<?>[] argClasses,Object... args){
		try {
            Constructor c = constructors.get(targetClass);
            if(c == null){
                c = targetClass.getDeclaredConstructor(argClasses);
                c.setAccessible(true);
                constructors.put(targetClass,c);
            } else {
                LOG.debug("Found constructor in cache for {}",targetClass);
            }
            return c.newInstance(args);
		} catch (Exception e) {
			LOG.error("Error occurred during creation of instance. Type '{}'",targetClass,e);
		}
		return null;
	}

}
