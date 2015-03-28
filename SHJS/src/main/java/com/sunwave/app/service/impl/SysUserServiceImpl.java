package com.sunwave.app.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunwave.app.dao.SysRoleDAO;
import com.sunwave.app.dao.SysUserDAO;
import com.sunwave.app.model.SysRole;
import com.sunwave.app.model.SysUser;
import com.sunwave.app.service.SysUserService;
import com.sunwave.framework.realm.PasswordHelper;

@Transactional
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService{
	
	@Autowired
	private SysUserDAO sysUserDAO;
	@Autowired
	private SysRoleDAO sysRoleDAO;

	@Override
	public Set<String> findRoles(String username) {
		return sysUserDAO.findRoles(username);
	}

	@Override
	public Set<String> findPermissions(String username) {
		return sysRoleDAO.findPermissions(username);
	}

	@Override
	public SysUser findByUsername(String username) {
		return sysUserDAO.findByUsername(username);
	}
	
	@Override
	public Map<String, Object> findPageInfo(Integer areaId, SysUser sysUser,
			Integer page, Integer limit, String sort) {
		if(sort!=null&&sort.split("\\.").length==2){
			String[] split = sort.split("\\.");
			sort=" order by t."+split[0]+" "+split[1];
		}else{
			sort="";
		}
		Map<String, Object> dataMap = sysUserDAO.findByPagination(areaId,sysUser, page, limit, sort);
		return dataMap;
	}

	@Override
	public Map<String,Object> save(SysUser sysUser) {
		Map<String,Object> result= new HashMap<String,Object>();
		SysUser findByUsername = sysUserDAO.findByUsername(sysUser.getUsername());
		if(findByUsername!=null){
			result.put("error", "用户名已存在");
			return result;
		}
		sysUser.setPassword("123456");
		sysUser.setUserType(2);
		PasswordHelper ph= new PasswordHelper();
		ph.encryptPassword(sysUser);
		String roleId = sysUser.getRoleIdQ();
		String[] rId = roleId.split(",");
		for(String r:rId){
			SysRole sysRole = (SysRole)sysRoleDAO.findById(SysRole.class, Integer.valueOf(r));
			sysUser.getSysRoles().add(sysRole);
		}
		sysUserDAO.save(sysUser);
		result.put("data", sysUser);
		return result;
	}

	@Override
	public Map<String,Object> update(SysUser sysUser) {
		Map<String,Object> result= new HashMap<String,Object>();
		SysUser u = (SysUser)sysUserDAO.findById(SysUser.class, sysUser.getId());
		if(u==null){
			result.put("error", "用户已删除！");
			return result;
		}
		String roleId = sysUser.getRoleIdQ();
		String[] rId = roleId.split(",");
		for(String r:rId){
			SysRole sysRole = (SysRole)sysRoleDAO.findById(SysRole.class, Integer.valueOf(r));
			sysUser.getSysRoles().add(sysRole);
		}
		u.setUsername(sysUser.getUsername());
		u.setRoleIdQ(sysUser.getRoleIdQ());
		u.setSysRoles(sysUser.getSysRoles());
		u.setSlArea(sysUser.getSlArea());
		u.setUserShowName(sysUser.getUserShowName());
		u.setRoleId(sysUser.getRoleId());
		u.setLocked(sysUser.getLocked());
		sysUserDAO.update(u);
		result.put("data", u);
		return result;
	}

	@Override
	public boolean removeByIds(String ids) {
		String[] idList = ids.split(",");
		for(String id:idList){
			Object obj = sysUserDAO.findById(SysUser.class, Integer.valueOf(id));
			if(obj!=null)
				sysUserDAO.delete(obj);
		}
		return true;
	}

}
