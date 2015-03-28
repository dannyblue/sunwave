package com.sunwave.app.service;

import java.util.Map;
import java.util.Set;

import com.sunwave.app.model.SysUser;

public interface SysUserService {
	/**
     * 根据用户名查找其角色
     * @param username
     * @return
     */
    public Set<String> findRoles(String username);

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username);
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public SysUser findByUsername(String username);
    
    public Map<String, Object> findPageInfo(Integer areaId,SysUser sysUser,
			Integer page, Integer limit, String sort);
	
	public Map<String,Object> save(SysUser sysUser);
	
	public Map<String,Object> update(SysUser sysUser);
	
	public boolean removeByIds(String ids);
}
