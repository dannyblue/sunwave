package com.sunwave.app.service;

import java.util.Map;

import com.sunwave.app.model.SlCall;

public interface CallService {
	public Map<String, Object> findPageInfo(Integer areaId,SlCall slCall,Integer page,Integer limit,String sort);
	public boolean removeByIds(String ids);
}
