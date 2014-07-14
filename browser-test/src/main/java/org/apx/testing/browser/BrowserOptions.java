package org.apx.testing.browser;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Browser initialization options
 */
public class BrowserOptions {

	static final String BROWSER_TYPE_KEY = "browser";
	static final String QUERYING_TYPE_KEY = "browser.query";
	static final String REMOTE_DRIVER_KEY = "use.remote.driver";
	static final String CLOSE_ON_JVM_STOP_KEY = "close.on.jvm.stop";
	static final String MAXIMIZE_WINDOW_KEY = "maximize.window";
	static final String AJAX_PRE_WAIT_KEY = "ajax.initial.wait";
	static final List<String> TRUES = Arrays.asList(new String[]{"true","1","yes","y","t"});

	final BrowserType type;
	final QueryType queryingType;
	boolean remoteDriver = false;
	boolean closeOnJVMStop = false;
	boolean maximize = false;
	long ajaxPreWait = 200L;


    /**
     * Initialize browser options. They will later be used during browser startup.
     * @param properties map of properties
     */
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

    public BrowserType getType() {
        return type;
    }

    public QueryType getQueryingType() {
        return queryingType;
    }

    public boolean isRemoteDriver() {
        return remoteDriver;
    }

    public boolean isCloseOnJVMStop() {
        return closeOnJVMStop;
    }

    public boolean isMaximize() {
        return maximize;
    }

    public long getAjaxPreWait() {
        return ajaxPreWait;
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
