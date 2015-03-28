package com.sunwave.app.service;

import java.util.Map;

import com.sunwave.app.model.SlArea;


public interface SlAreaService {
	
	public Map<String, Object> findPageInfo(Integer areaId,SlArea slArea,
			Integer page, Integer limit, String sort);
	
	public boolean save(SlArea slArea);
	
	public boolean update(SlArea slArea);
	
	public boolean removeByIds(String ids);
	
}
