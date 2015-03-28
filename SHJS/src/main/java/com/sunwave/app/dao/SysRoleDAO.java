package com.sunwave.app.dao;

import java.util.Set;

import com.sunwave.app.model.SysRole;

public interface SysRoleDAO extends BaseDAO<SysRole>{
	public Set<String> findPermissions(String username);
}
