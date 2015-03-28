package com.sunwave.app.dao.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Repository;

import com.sunwave.app.dao.SlCallDAO;
import com.sunwave.app.model.SlCall;

/**
 	* A data access object (DAO) providing persistence and search support for SlTel entities.
 	 		* Transaction control of the save(), update() and delete() operations must be handled externally by senders of these methods 
 		  or must be manually added to each of these methods for data to be persisted to the JPA datastore.	
 	 * @see com.sunwave.business.model.SlTel
  * @author MyEclipse Persistence Tools 
 */
@Repository("slCallDAO")
public class SlCallDAOImpl extends BaseDAOImpl<SlCall>  implements SlCallDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<SlCall> findCallByT2A(Integer areaId, String timeT,Integer areaGrade) {
		String nsql = "";
		if(areaGrade==1){
			 nsql = "SELECT t.* FROM sl_call t LEFT JOIN sl_area a ON a.area_id=t.area_id LEFT JOIN  sl_area b ON b.area_id=a.parent_id WHERE b.area_id=:areaId";
		}else if(areaGrade==2){
			 nsql = "SELECT t.* FROM sl_call t LEFT JOIN sl_area a ON a.area_id=t.area_id WHERE a.area_id=:areaId";
		}
		if("M".equals(timeT)){
			nsql += " AND YEAR(t.record_date) = YEAR(NOW()) AND MONTH(t.record_date) = MONTH(NOW()) ";
		}else if("Y".equals(timeT)){
			nsql+=" AND YEAR(t.record_date) = YEAR(NOW())";
		}else if("A".equals(timeT)){
			
		}
		nsql+=" order by t.record_date desc";
		Query query = getEntityManager().createNativeQuery(nsql, SlCall.class);
		query.setParameter("areaId", areaId);
		List<SlCall> resultList = query.getResultList();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findCallByM(Integer areaId,String ccType) {
		String nsql="";
		if("T".equals(ccType)){
			nsql = "SELECT MONTH(t.record_date),COUNT(t.call_id) "
					+ "FROM sl_call t LEFT JOIN sl_area a ON t.area_id=a.area_id "
					+ "LEFT JOIN sl_area b ON b.area_id =a.parent_id "
					+ "WHERE b.area_id =:areaId OR a.area_id=:areaId AND YEAR(t.record_date) = YEAR(NOW()) "
					+ "GROUP BY MONTH(t.record_date)";
		}else if("A".equals(ccType)){
			nsql = "SELECT t.area_id,a.area_name,COUNT(t.call_id) "
					+ "FROM sl_call t LEFT JOIN sl_area a ON t.area_id=a.area_id "
					+ "LEFT JOIN sl_area b ON b.area_id =a.parent_id "
					+ "WHERE b.area_id =:areaId OR a.area_id=:areaId AND YEAR(t.record_date) = YEAR(NOW()) "
					+ "GROUP BY t.area_id";
		}else if("O".equals(ccType)){
			nsql = "SELECT t.operator,COUNT(t.call_id) "
					+ "FROM sl_call t LEFT JOIN sl_area a ON t.area_id=a.area_id "
					+ "LEFT JOIN sl_area b ON b.area_id =a.parent_id "
					+ "WHERE b.area_id =:areaId OR a.area_id=:areaId AND YEAR(t.record_date) = YEAR(NOW()) "
					+ "GROUP BY t.operator";
		}
		Query query = getEntityManager()
				.createNativeQuery(nsql);
		query.setParameter("areaId", areaId);
		List<Object[]> resultList = query.getResultList();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findByPagination(Integer areaId,SlCall slCall,
			Integer page, Integer limit, String order) {
		Map<String,Object> map = new HashMap<String,Object>();
		SimpleDateFormat fm=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String queryHead = "select t.* ";
		String queryCountHead = "select count(t.call_id) ";
		String queryContext = " FROM sl_call t "
				+ "LEFT JOIN sl_area a ON a.area_id = t.area_id "
				+ "LEFT JOIN sl_area b ON a.parent_id = b.area_id ";
		String where ="";
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.hasRole("admin")){
			where = "WHERE 1=1 ";
		}else{
			where = "WHERE (b.area_id = :areaId or a.area_id=:areaId) ";
		}
		if (!"".equals(slCall.getImsi())&&slCall.getImsi() != null) {
			where += " and t.imsi like '%" + slCall.getImsi()+"%'";
		}
		if (!"".equals(slCall.getPosinfo())&&slCall.getPosinfo() != null) {
			where += " and t.posinfo like '%" + slCall.getPosinfo()+"%'";
		}
		if (slCall.getRecordDateS() != null) {
			where += " and t.record_Date >= '" + fm.format(slCall.getRecordDateS())+"'";
		}
		if (slCall.getRecordDateE() != null) {
			where += " and t.record_Date <= '" + fm.format(slCall.getRecordDateE())+"'";
		}
		String sql = queryHead + queryContext + where + order;
		Query query = getEntityManager().createNativeQuery(sql,SlCall.class);
		if(!currentUser.hasRole("admin")){//非ADMIN加区域权限控制
			query.setParameter("areaId", areaId);
		}
		if(page!=null&&limit!=null){
			query.setFirstResult((page-1)*limit);
			query.setMaxResults(page*limit);
		}
		List<SlCall> resultList = query.getResultList();
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