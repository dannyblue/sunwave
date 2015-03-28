package com.sunwave.app.dao.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Repository;

import com.sunwave.app.dao.SlAreaDAO;
import com.sunwave.app.model.SlArea;

@Repository("slAreaDAO")
public class SlAreaDAOImpl extends BaseDAOImpl<SlArea> implements SlAreaDAO{

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findByPagination(Integer areaId, SlArea slArea,
			Integer page, Integer limit, String order) {
		Map<String,Object> map = new HashMap<String,Object>();
		String queryHead = "select t.* ";
		String queryCountHead = "select count(t.area_id) ";
		String queryContext = " FROM sl_area t "
				+ "LEFT JOIN sl_area b ON t.parent_id = b.area_id ";
		String where ="";
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.hasRole("admin")){
			where = "WHERE 1=1 ";
		}else{
			where = "WHERE (t.area_id = :areaId or b.area_id=:areaId) ";
		}
		if (slArea.getAreaName()!=null&&!"".equals(slArea.getAreaName())) {
			where += " and t.area_name like '%" + slArea.getAreaName()+"%'";
		}
		
		String sql = queryHead + queryContext + where + order;
		Query query = getEntityManager().createNativeQuery(sql,SlArea.class);
		if(!currentUser.hasRole("admin")){
			query.setParameter("areaId", areaId);
		}
		query.setFirstResult((page-1)*limit);
		query.setMaxResults(page*limit);
		List<SlArea> resultList = query.getResultList();
		String sqlCount = queryCountHead + queryContext + where;
		Query queryCount = getEntityManager().createNativeQuery(sqlCount);
		if(!currentUser.hasRole("admin")){
			queryCount.setParameter("areaId", areaId);
		}
		BigInteger singleResult = (BigInteger)queryCount.getSingleResult();
		map.put("data", resultList);
		map.put("totalCount", singleResult.intValue());
		return map;
	}

}
