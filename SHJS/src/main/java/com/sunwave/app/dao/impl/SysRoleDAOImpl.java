package com.sunwave.app.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.sunwave.app.dao.SysRoleDAO;
import com.sunwave.app.model.SysRole;

@Repository("sysRoleDAO")
public class SysRoleDAOImpl extends BaseDAOImpl<SysRole> implements SysRoleDAO{

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> findPermissions(String username) {
		String sql = "SELECT t.permission_name FROM sys_permission t LEFT JOIN sys_role_permission p ON p.permission_id=t.id LEFT JOIN sys_role r ON r.id= p.role_id LEFT JOIN sys_user_role u ON u.role_id=r.id LEFT JOIN sys_user o ON o.id = u.user_id WHERE o.username=:username";
		Query createQuery = this.getEntityManager().createNativeQuery(sql);
		createQuery.setParameter("username", username);
		List<String> resultList = createQuery.getResultList();
		Set<String> perssisionNames = new HashSet<String>();
		for(String perssionName : resultList){
			perssisionNames.add(perssionName);
		}
		return perssisionNames;
	}

}
