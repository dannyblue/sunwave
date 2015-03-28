package com.sunwave.app.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunwave.app.dao.SlMsgDAO;
import com.sunwave.app.model.SlMsg;
import com.sunwave.app.service.MsgService;

@Service("msgService")
@Transactional
public class MsgServiceImpl implements MsgService{
	
	@Autowired
	private SlMsgDAO slMsgDAO;

	@Override
	public Map<String, Object> findPageInfo(Integer areaId,SlMsg slMsg, Integer page,
			Integer limit, String sort) {
		if(sort!=null&&sort.split("\\.").length==2){
			String[] split = sort.split("\\.");
			sort=" order by t."+split[0]+" "+split[1];
		}else{
			sort="";
		}
		Map<String, Object> findByPagination = slMsgDAO.findByPagination(areaId,slMsg,page,limit,sort);
		return findByPagination;
	}

	@Override
	public boolean removeByIds(String ids) {
		String[] idList = ids.split(",");
		for(String id:idList){
			Object slMsg = slMsgDAO.findById(SlMsg.class, Integer.valueOf(id));
			if(slMsg!=null)
				slMsgDAO.delete(slMsg);
		}
		return true;
	}


}
