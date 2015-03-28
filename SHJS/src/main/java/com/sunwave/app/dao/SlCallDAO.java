package com.sunwave.app.dao;

import java.util.List;
import java.util.Map;

import com.sunwave.app.model.SlCall;


/**
 * Interface for SlTelDAO.
 * @author MyEclipse Persistence Tools
 */

public interface SlCallDAO extends BaseDAO<SlCall>{
	public List<SlCall> findCallByT2A(Integer areaId,String timeT,Integer areaGrade);
	public List<Object[]> findCallByM(Integer areaId,String ccType);
	public Map<String,Object> findByPagination(Integer areaId,SlCall slCall,Integer page,Integer limit,String order);
}