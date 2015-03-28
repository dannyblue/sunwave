package com.sunwave.app.service;

import java.util.Map;

import com.sunwave.app.model.SlInvade;

public interface InvadeService {

	public Map<String, Object> findPageInfo(Integer areaId,SlInvade slInvade,Integer page,Integer limit,String sort);
	
	public boolean removeByIds(String ids);

}
