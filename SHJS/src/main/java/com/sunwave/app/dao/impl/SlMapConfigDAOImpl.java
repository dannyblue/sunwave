package com.sunwave.app.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.sunwave.app.dao.SlMapConfigDAO;
import com.sunwave.app.model.SlArea;
import com.sunwave.app.model.SlMapConfig;

/**
 * A data access object (DAO) providing persistence and search support for
 * SlMapConfig entities. Transaction control of the save(), update() and
 * delete() operations must be handled externally by senders of these methods or
 * must be manually added to each of these methods for data to be persisted to
 * the JPA datastore.
 * 
 * @see com.sunwave.business.model.SlMapConfig
 * @author MyEclipse Persistence Tools
 */
@Repository("slMapConfigDAO")
public class SlMapConfigDAOImpl extends BaseDAOImpl<SlMapConfig> implements SlMapConfigDAO {


	@SuppressWarnings("unchecked")
	@Override
	public List<SlMapConfig> getConfigByArea(SlArea slArea,Integer inUse) {
		EntityManager entityManager = this.getEntityManager();
		String queryString = "";
		queryString = "SELECT t.* FROM sl_map_config t LEFT JOIN sl_area a ON a.area_id=t.area_id WHERE a.area_id=:areaId";
		if(inUse!=null){
			queryString +=" AND t.in_use="+inUse;
		}
		Query createNativeQuery = entityManager.createNativeQuery(queryString, SlMapConfig.class);
		createNativeQuery.setParameter("areaId", slArea.getAreaId());
		List<SlMapConfig> resultList = createNativeQuery.getResultList();
		return resultList;
	}

}