package com.sunwave.app.dao;

import java.util.Map;

import com.sunwave.app.model.SlPhone;

/**
 * Interface for SlWListDAO.
 * @author MyEclipse Persistence Tools
 */

public interface SlPhoneDAO extends BaseDAO<SlPhone> {
	public Map<String,Object> findByPagination(Integer areaId,SlPhone slPhone,Integer page,Integer limit,String order);
}