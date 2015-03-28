package com.sunwave.app.controller;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunwave.app.model.SysUser;
import com.sunwave.app.service.SysUserService;
import com.sunwave.framework.util.JsonResponse;

@Controller
public class SysUserController {
	@Autowired
	private SysUserService sysUserService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/surveillance.sysUser.initList.do")
	public ModelAndView initList(Integer areaId,SysUser sysUser) {
		ModelAndView mv = new ModelAndView("surveillance/sysUserPage");
		Subject currentUser = SecurityUtils.getSubject();
		SysUser loginUser = (SysUser)currentUser.getSession().getAttribute("loginUser");
		Map<String, Object> initData = (Map<String, Object>)currentUser.getSession().getAttribute("initData");
		mv.addObject("userShowName", loginUser.getUserShowName());
		mv.addObject("userAreaId", loginUser.getSlArea().getAreaId());
		mv.addObject("initData", initData);
		return mv;
	}

	@RequestMapping("/surveillance.sysUser.getList.do")
	public @ResponseBody Object getList(Integer areaId,SysUser sysUser,
			Integer page,Integer limit,String sort) {
		if(areaId==null){
			SysUser user = (SysUser)SecurityUtils.getSubject().getSession().getAttribute("loginUser");
			areaId=user.getSlArea().getAreaId();
		}
		Map<String, Object> findPageInfo = sysUserService.findPageInfo(areaId, sysUser, page, limit, sort);
		Object okWithPaginateDisableRef = JsonResponse.okWithPaginateDisableRef(findPageInfo.get("data"), (Integer)findPageInfo.get("totalCount"));
		return okWithPaginateDisableRef;
	}
	@RequestMapping("/surveillance.sysUser.save.do")
	public @ResponseBody Map<String,Object> save(SysUser sysUser){
		Map<String, Object> save = sysUserService.save(sysUser);
		return save;
	}
	
	@RequestMapping("/surveillance.sysUser.update.do")
	public @ResponseBody Map<String,Object> update(SysUser sysUser){
		Map<String, Object> update = sysUserService.update(sysUser);
		return update;
	}
	
	@RequestMapping("/surveillance.sysUser.remove.do")
	public @ResponseBody void remove(String ids) {
		sysUserService.removeByIds(ids);
	}
}
