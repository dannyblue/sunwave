package com.sunwave.app.dao.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Repository;

import com.sunwave.app.dao.SysUserDAO;
import com.sunwave.app.model.SysUser;

@Repository("sysUserDAO")
public class SysUserDAOImpl extends BaseDAOImpl<SysUser> implements SysUserDAO{

	@SuppressWarnings("unchecked")
	@Override
	public SysUser findByUsername(String username) {
		String sql = "select t from SysUser t where t.username=:username";
		Query createQuery = this.getEntityManager().createQuery(sql);
		createQuery.setParameter("username", username);
		List<SysUser> resultList = createQuery.getResultList();
		if(resultList.size()==1){
			return resultList.get(0);
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> findRoles(String username) {
		String sql = "SELECT t.role_name FROM sys_role t "
				+ "LEFT JOIN sys_user_role r ON r.role_id = t.id "
				+ "LEFT JOIN sys_user u ON u.id = r.user_id "
				+ "WHERE u.username=:username";
		Query createQuery = this.getEntityManager().createNativeQuery(sql);
		createQuery.setParameter("username", username);
		List<String> resultList = createQuery.getResultList();
		Set<String> roleNames = new HashSet<String>();
		for(String roleName : resultList){
			roleNames.add(roleName);
		}
		return roleNames;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> findByPagination(Integer areaId,
			SysUser sysUser, Integer page, Integer limit, String order) {
		Map<String,Object> map = new HashMap<String,Object>();
		String queryHead = "select t.* ";
		String queryCountHead = "select count(t.id) ";
		String queryContext = " FROM sys_user t "
				+ "LEFT JOIN sl_area a ON a.area_id = t.area_id "
				+ "LEFT JOIN sl_area b ON a.parent_id = b.area_id ";
		String where ="";
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.hasRole("admin")){
			where = "WHERE 1=1 ";
		}else{
			where = "WHERE (a.area_id = :areaId or b.area_id=:areaId) ";
		}
		if (sysUser.getUsername()!=null&&!"".equals(sysUser.getUsername())) {
			where += " and t.username like '%" + sysUser.getUsername()+"%'";
		}
		if (sysUser.getUserShowName()!=null&&!"".equals(sysUser.getUserShowName())) {
			where += " and t.user_show_name like '%" + sysUser.getUserShowName()+"%'";
		}
		
		String sql = queryHead + queryContext + where + order;
		Query query = getEntityManager().createNativeQuery(sql,SysUser.class);
		if(!currentUser.hasRole("admin")){
			query.setParameter("areaId", areaId);
		}
		query.setFirstResult((page-1)*limit);
		query.setMaxResults(page*limit);
		List<SysUser> resultList = query.getResultList();
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
