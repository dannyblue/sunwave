package com.sunwave.app.dao;

import java.util.Map;
import java.util.Set;

import com.sunwave.app.model.SysUser;

public interface SysUserDAO extends BaseDAO<SysUser>{
	
	public SysUser findByUsername(String username);
	
	public Set<String> findRoles(String username);
	
	public Map<String,Object> findByPagination(Integer areaId,SysUser sysUser,Integer page,Integer limit,String order);
}
