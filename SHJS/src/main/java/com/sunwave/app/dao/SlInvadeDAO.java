package com.sunwave.app.dao;

import java.util.List;
import java.util.Map;

import com.sunwave.app.model.SlInvade;

/**
 * Interface for SlInvadeDAO.
 * @author MyEclipse Persistence Tools
 */
public interface SlInvadeDAO extends BaseDAO<SlInvade>{
	public Map<String,Object> findByPagination(Integer areaId,SlInvade slInvade,Integer page,Integer limit,String order);
	
	public List<SlInvade> findByProperty(SlInvade slInvade);
	
	public List<SlInvade> findInvadeByT2A(Integer areaId,String timeT,Integer areaGrade);
	
	public List<Object[]> findInvadeByM(Integer areaId,String ccType);
}