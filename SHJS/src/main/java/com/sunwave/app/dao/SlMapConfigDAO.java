package com.sunwave.app.dao;

import java.util.List;

import com.sunwave.app.model.SlArea;
import com.sunwave.app.model.SlMapConfig;

/**
 * Interface for SlMapConfigDAO.
 * 
 * @author MyEclipse Persistence Tools
 */

public interface SlMapConfigDAO extends BaseDAO<SlMapConfig>{
	
	public List<SlMapConfig> getConfigByArea(SlArea slArea,Integer inUse);
}