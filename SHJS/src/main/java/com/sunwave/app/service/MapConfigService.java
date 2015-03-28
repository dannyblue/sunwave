package com.sunwave.app.service;

import com.sunwave.app.model.SlArea;
import com.sunwave.app.model.SlMapConfig;

public interface MapConfigService {
	
	public SlMapConfig getConfig4User(SlArea slArea);
	
	public boolean saveMapConfig4User(SlMapConfig slMapConfig);
	
	public SlArea getAreaById(Integer areaId);

}
