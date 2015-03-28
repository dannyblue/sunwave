package com.sunwave.app.service;

import java.util.Map;

import com.sunwave.app.model.SlPhone;


public interface PhoneService {
	public Map<String, Object> findPageInfo(Integer areaId,SlPhone slPhone,Integer page,Integer limit,String sort);
	public Map<String, Object> set();
	public boolean removeByIds(String ids);
	public Map<String, Object> save(SlPhone slPhone);
	public Map<String, Object> update(SlPhone slPhone);
}
