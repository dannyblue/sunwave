package com.sunwave.app.controller;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunwave.app.model.SlArea;
import com.sunwave.app.model.SysUser;
import com.sunwave.app.service.SlAreaService;
import com.sunwave.framework.util.JsonResponse;

@Controller
public class SlAreaController {
	@Autowired
	private SlAreaService slAreaService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/surveillance.slArea.initList.do")
	public ModelAndView initList(Integer areaId,SlArea slArea) {
		ModelAndView mv = new ModelAndView("surveillance/slAreaPage");
		Subject currentUser = SecurityUtils.getSubject();
		SysUser loginUser = (SysUser)currentUser.getSession().getAttribute("loginUser");
		Map<String, Object> initData = (Map<String, Object>)currentUser.getSession().getAttribute("initData");
		mv.addObject("userShowName", loginUser.getUserShowName());
		mv.addObject("userAreaId", loginUser.getSlArea().getAreaId());
		mv.addObject("initData", initData);
		return mv;
	}

	@RequestMapping("/surveillance.slArea.getList.do")
	public @ResponseBody Object getList(Integer areaId,SlArea slArea,
			Integer page,Integer limit,String sort) {
		if(areaId==null){
			SysUser user = (SysUser)SecurityUtils.getSubject().getSession().getAttribute("loginUser");
			areaId=user.getSlArea().getAreaId();
		}
		Map<String, Object> findPageInfo = slAreaService.findPageInfo(areaId, slArea, page, limit, sort);
		Object okWithPaginateDisableRef = JsonResponse.okWithPaginateDisableRef(findPageInfo.get("data"), (Integer)findPageInfo.get("totalCount"));
		return okWithPaginateDisableRef;
	}
	@RequestMapping("/surveillance.slArea.save.do")
	public @ResponseBody SlArea save(SlArea slArea){
		slAreaService.save(slArea);
		return slArea;
	}
	
	@RequestMapping("/surveillance.slArea.update.do")
	public @ResponseBody SlArea update(SlArea slArea){
		slAreaService.update(slArea);
		return slArea;
	}
	
	@RequestMapping("/surveillance.slArea.remove.do")
	public @ResponseBody void remove(String ids) {
		slAreaService.removeByIds(ids);
	}
}
