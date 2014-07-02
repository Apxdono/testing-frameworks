package org.apx.testing.browser;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 1/5/14
 * Time: 10:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class BrowserOptions {

	static final String BROWSER_TYPE_KEY = "browser";
	static final String QUERYING_TYPE_KEY = "browser.query";
	static final String REMOTE_DRIVER_KEY = "use.remote.driver";
	static final String CLOSE_ON_JVM_STOP_KEY = "close.on.jvm.stop";
	static final String MAXIMIZE_WINDOW_KEY = "maximize.window";
	static final String AJAX_PRE_WAIT_KEY = "ajax.initial.wait";
	static final List<String> TRUES = Arrays.asList(new String[]{"true","1","yes","y","t"});

	BrowserType type;
	QueryType queryingType;
	boolean remoteDriver = false;
	boolean closeOnJVMStop = false;
	boolean maximize = false;
	long ajaxPreWait = 200L;

	public BrowserOptions(Map<String,String> properties){
		type = BrowserType.getType(properties.get(BROWSER_TYPE_KEY));
		queryingType = QueryType.getType(properties.get(QUERYING_TYPE_KEY));
		if(TRUES.contains((properties.get(REMOTE_DRIVER_KEY)+"").toLowerCase())){
			remoteDriver = true;
		}
		if(TRUES.contains((properties.get(CLOSE_ON_JVM_STOP_KEY)+"").toLowerCase())){
			closeOnJVMStop = true;
		}
		if(TRUES.contains((properties.get(MAXIMIZE_WINDOW_KEY)+"").toLowerCase())){
			maximize = true;
		}
		String preWait = properties.get(AJAX_PRE_WAIT_KEY)+"";
		if(StringUtils.isNotBlank(preWait)){
			ajaxPreWait = Long.parseLong(preWait);
		}
	}

	@Override
	public String toString() {
		return "Browser options{" +
				"type=" + type +
				", queryingType=" + queryingType +
				", remoteDriver=" + remoteDriver +
				'}';
	}
}