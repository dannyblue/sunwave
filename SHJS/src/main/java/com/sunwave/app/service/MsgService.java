package com.sunwave.app.service;

import java.util.Map;

import com.sunwave.app.model.SlMsg;

public interface MsgService {
	public Map<String, Object> findPageInfo(Integer areaId,SlMsg slMsg,Integer page,Integer limit,String sort);
	public boolean removeByIds(String ids);
}
