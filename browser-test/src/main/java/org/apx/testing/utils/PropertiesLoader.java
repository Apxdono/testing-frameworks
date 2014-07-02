package org.apx.testing.utils;

import org.apache.commons.codec.Charsets;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 1/5/14
 * Time: 10:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertiesLoader {

	private PropertiesLoader(){

	}

	public static Properties load(String file) {
		Properties props = new Properties();
		InputStream is = PropertiesLoader.class.getClassLoader().getResourceAsStream(file);

		if (is != null) {
			try {
				InputStreamReader isr = new InputStreamReader(is, Charsets.UTF_8);
				props.load(isr);
				isr.close();
			} catch (IOException e) {
				LoggerFactory.getLogger(PropertiesLoader.class).error("Cannot load browser properties. ",e);
			}
		} else {
			LoggerFactory.getLogger(PropertiesLoader.class).error("No property file resource found with name: {}",file);
		}
		return props;
	}

	public static Map<String,String> propertiesAsMap(Properties props){
		Map<String,String> result = new HashMap<String, String>();
		if(props!=null){
			for (final String name: props.stringPropertyNames())
				result.put(name, props.getProperty(name));
		}
		return result;
	}

}
