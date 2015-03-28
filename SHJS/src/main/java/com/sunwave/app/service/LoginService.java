package com.sunwave.app.service;

import java.util.Map;

import com.sunwave.app.model.SysUser;

public interface LoginService{
	public Map<String,Object> initUserData(SysUser user);
	public Map<String,Object> findCCData(SysUser user,String dataType,String ccType,String tType); 
}
