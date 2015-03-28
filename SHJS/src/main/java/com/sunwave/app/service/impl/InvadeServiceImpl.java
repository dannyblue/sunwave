package com.sunwave.app.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunwave.app.dao.SlInvadeDAO;
import com.sunwave.app.model.SlInvade;
import com.sunwave.app.service.InvadeService;

@Service("invadeService")
@Transactional
public class InvadeServiceImpl implements
		InvadeService {
	
	@Autowired
	private SlInvadeDAO slInvadeDAO;

	@Override
	public Map<String, Object> findPageInfo(Integer areaId,SlInvade slInvade,Integer page,Integer limit,String sort) {
		if(sort!=null&&sort.split("\\.").length==2){
			String[] split = sort.split("\\.");
			sort=" order by t."+split[0]+" "+split[1];
		}else{
			sort="";
		}
		Map<String, Object> findByPagination = slInvadeDAO.findByPagination(areaId,slInvade,page,limit,sort);
		return findByPagination;
	}

	@Override
	public boolean removeByIds(String ids) {
		String[] idList = ids.split(",");
		for(String id:idList){
			Object slInvade = slInvadeDAO.findById(SlInvade.class, Integer.valueOf(id));
			if(slInvade!=null)
			slInvadeDAO.delete(slInvade);
		}
		return true;
	}
	
}
