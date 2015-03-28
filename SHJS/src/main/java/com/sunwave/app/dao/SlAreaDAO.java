package com.sunwave.app.dao;

import java.util.Map;

import com.sunwave.app.model.SlArea;


public interface SlAreaDAO extends BaseDAO<SlArea>{
	
	public Map<String,Object> findByPagination(Integer areaId,SlArea slArea,Integer page,Integer limit,String order);

}
