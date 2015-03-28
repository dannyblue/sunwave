package com.sunwave.app.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunwave.app.dao.SlAreaDAO;
import com.sunwave.app.model.SlArea;
import com.sunwave.app.service.SlAreaService;

@Transactional
@Service("sysAreaService")
public class SlAreaServiceImpl implements SlAreaService{
	
	@Autowired
	private SlAreaDAO slAreaDAO;

	@Override
	public Map<String, Object> findPageInfo(Integer areaId, SlArea slArea,
			Integer page, Integer limit, String sort) {
		if(sort!=null&&sort.split("\\.").length==2){
			String[] split = sort.split("\\.");
			sort=" order by t."+split[0]+" "+split[1];
		}else{
			sort="";
		}
		Map<String, Object> dataMap = slAreaDAO.findByPagination(areaId,slArea, page, limit, sort);
		return dataMap;
	}

	@Override
	public boolean save(SlArea slArea) {
		slArea.setAreaGrade(2);//监区
		slArea.setAreaType(1);//地区，非建筑物
		slAreaDAO.save(slArea);
		return true;
	}

	@Override
	public boolean update(SlArea slArea) {
		slAreaDAO.update(slArea);
		return true;
	}

	@Override
	public boolean removeByIds(String ids) {
		String[] idList = ids.split(",");
		for(String id:idList){
			Object obj = slAreaDAO.findById(SlArea.class, Integer.valueOf(id));
			if(obj!=null)
				slAreaDAO.delete(obj);
		}
		return true;
	}

}
