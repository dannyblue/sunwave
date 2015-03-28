package com.sunwave.app.dao.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Repository;

import com.sunwave.app.dao.SlPhoneDAO;
import com.sunwave.app.model.SlPhone;

/**
 	* A data access object (DAO) providing persistence and search support for SlWList entities.
 	 		* Transaction control of the save(), update() and delete() operations must be handled externally by senders of these methods 
 		  or must be manually added to each of these methods for data to be persisted to the JPA datastore.	
 	 * @see com.sunwave.business.model.SlWList
  * @author MyEclipse Persistence Tools 
 */
@Repository("slPhoneDAO")
public class SlPhoneDAOImpl extends BaseDAOImpl<SlPhone>  implements SlPhoneDAO{

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findByPagination(Integer areaId,SlPhone slPhone, Integer page,
			Integer limit, String order) {
		Map<String,Object> map = new HashMap<String,Object>();
		String queryHead = "select t.* ";
		String queryCountHead = "select count(t.phone_id) ";
		String queryContext = " FROM sl_phone t "
				+ "LEFT JOIN sl_area a ON a.area_id = t.area_id "
				+ "LEFT JOIN sl_area b ON a.parent_id = b.area_id ";
		String where ="";
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.hasRole("admin")){
			where = "WHERE 1=1 ";
		}else{
			where = "WHERE (b.area_id = :areaId or a.area_id=:areaId) ";
		}
		if (!"".equals(slPhone.getPhoneNum())&&slPhone.getPhoneNum() != null) {
			where += " and t.phone_num like '%" + slPhone.getPhoneNum()+"%'";
		}
		if (slPhone.getIsWhite() != null) {
			where += " and t.is_white =" + slPhone.getIsWhite();
		}
		String sql = queryHead + queryContext + where + order;
		Query query = getEntityManager().createNativeQuery(sql,SlPhone.class);
		if(!currentUser.hasRole("admin")){//非ADMIN加区域权限控制
			query.setParameter("areaId", areaId);
		}
		if(page!=null&&limit!=null){
			query.setFirstResult((page-1)*limit);
			query.setMaxResults(page*limit);
		}
		List<SlPhone> resultList = query.getResultList();
		String sqlCount = queryCountHead + queryContext + where;
		Query queryCount = getEntityManager().createNativeQuery(sqlCount);
		if(!currentUser.hasRole("admin")){//非ADMIN加区域权限控制
			queryCount.setParameter("areaId", areaId);
		}
		BigInteger singleResult = (BigInteger)queryCount.getSingleResult();
		map.put("data", resultList);
		map.put("totalCount", singleResult.intValue());
		return map;
	}
}