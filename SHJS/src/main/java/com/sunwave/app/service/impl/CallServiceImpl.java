package com.sunwave.app.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunwave.app.dao.SlCallDAO;
import com.sunwave.app.model.SlCall;
import com.sunwave.app.service.CallService;

@Service("callService")
@Transactional
public class CallServiceImpl implements CallService{
	
	@Autowired
	private SlCallDAO slCallDAO;

	@Override
	public Map<String, Object> findPageInfo(Integer areaId,SlCall slCall, Integer page,
			Integer limit, String sort) {
		if(sort!=null&&sort.split("\\.").length==2){
			String[] split = sort.split("\\.");
			sort=" order by t."+split[0]+" "+split[1];
		}else{
			sort="";
		}
		Map<String, Object> findByPagination = slCallDAO.findByPagination(areaId,slCall,page,limit,sort);
		return findByPagination;
	}

	@Override
	public boolean removeByIds(String ids) {
		String[] idList = ids.split(",");
		for(String id:idList){
			Object slCall = slCallDAO.findById(SlCall.class, Integer.valueOf(id));
			if(slCall!=null)
				slCallDAO.delete(slCall);
		}
		return true;
	}


}
