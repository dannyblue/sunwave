package com.sunwave.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunwave.app.dao.SlAreaDAO;
import com.sunwave.app.dao.SlMapConfigDAO;
import com.sunwave.app.model.SlArea;
import com.sunwave.app.model.SlMapConfig;
import com.sunwave.app.service.MapConfigService;

@Service("mapConfigService")
@Transactional
public class MapConfigServiceImpl implements MapConfigService{

	@Autowired
	private SlMapConfigDAO slMapConfigDAO;
	
	@Autowired
	private SlAreaDAO slAreaDAO;
	
	@Override
	public SlMapConfig getConfig4User(SlArea slArea) {
		List<SlMapConfig> configByArea = slMapConfigDAO.getConfigByArea(slArea, 1);
		if(configByArea.size()==1){
			return configByArea.get(0);
		}else{
			return null;
		}
	}

	@Override
	public boolean saveMapConfig4User(SlMapConfig slMapConfig) {
		SlArea slArea = slMapConfig.getSlArea();
		List<SlMapConfig> configByAreaOld = slMapConfigDAO.getConfigByArea(slArea,0);
		for(SlMapConfig sm:configByAreaOld){
			slMapConfigDAO.delete(sm);
		}
		List<SlMapConfig> configByArea = slMapConfigDAO.getConfigByArea(slArea,1);
		for(SlMapConfig sm:configByArea){
			sm.setInUse(0);
			slMapConfigDAO.update(sm);
		}
		slMapConfigDAO.save(slMapConfig);
		return true;
	}

	@Override
	public SlArea getAreaById(Integer areaId) {
		SlArea slArea = (SlArea)slAreaDAO.findById(SlArea.class, areaId);
		return slArea;
	}

}
