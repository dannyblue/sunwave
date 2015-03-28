package com.sunwave.app.dao;

import java.util.List;
import java.util.Map;

import com.sunwave.app.model.SlMsg;

/**
 * Interface for SlMsgDAO.
 * @author MyEclipse Persistence Tools
 */

public interface SlMsgDAO extends BaseDAO<SlMsg>{
	public Map<String,Object> findByPagination(Integer areaId,SlMsg slMsg,Integer page,Integer limit,String order);
	public List<SlMsg> findMsgByT2A(Integer areaId,String timeT,Integer areaGrade);
	public List<Object[]> findMsgByM(Integer areaId,String ccType);
}