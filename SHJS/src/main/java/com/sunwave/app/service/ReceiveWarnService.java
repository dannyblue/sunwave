package com.sunwave.app.service;

import java.util.Map;

public interface ReceiveWarnService {
	
	public Map<String, Object> saveWarn(String msg);
	
	public Map<String,Object> cancelWarn(Integer id,Integer otype,String cancelCause,String cancelPassword);

}
