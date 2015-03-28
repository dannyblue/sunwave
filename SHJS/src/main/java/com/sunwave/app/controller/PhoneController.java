package com.sunwave.app.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunwave.app.model.SlPhone;
import com.sunwave.app.model.SysUser;
import com.sunwave.app.service.PhoneService;
import com.sunwave.framework.util.JsonResponse;

@Controller
public class PhoneController {

	@Autowired
	private PhoneService phoneService;

	@SuppressWarnings("unchecked")
	@RequestMapping("/surveillance.phone.initList.do")
	public ModelAndView initList(Integer areaId,SlPhone slPhone, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("surveillance/phonePage");
		Subject currentUser = SecurityUtils.getSubject();
		SysUser loginUser = (SysUser)currentUser.getSession().getAttribute("loginUser");
		Map<String, Object> initData = (Map<String, Object>)currentUser.getSession().getAttribute("initData");
		mv.addObject("userShowName", loginUser.getUserShowName());
		mv.addObject("userAreaId", loginUser.getSlArea().getAreaId());
		mv.addObject("initData", initData);
		return mv;
	}


	@RequestMapping("/surveillance.phone.getList.do")
	public @ResponseBody Object getList(Integer areaId,SlPhone slPhone,
			Integer page,Integer limit,String sort, HttpServletRequest request) {
		if(areaId==null){
			SysUser user = (SysUser)SecurityUtils.getSubject().getSession().getAttribute("loginUser");
			areaId=user.getSlArea().getAreaId();
		}
		Map<String, Object> findPageInfo = phoneService.findPageInfo(areaId,slPhone, page,limit,sort);
		Object okWithPaginateDisableRef = JsonResponse.okWithPaginateDisableRef(findPageInfo.get("data"), (Integer)findPageInfo.get("totalCount"));
		return okWithPaginateDisableRef;
	}
	
	@RequestMapping("/surveillance.phone.save.do")
	public @ResponseBody Map<String, Object> save(SlPhone slPhone) {
		Map<String, Object> save = phoneService.save(slPhone);
		return save;
	}
	
	@RequestMapping("/surveillance.phone.update.do")
	public @ResponseBody Map<String, Object> update(SlPhone slPhone) {
		Map<String, Object> update = phoneService.update(slPhone);
		return update;
	}
	
	@RequestMapping("/surveillance.phone.remove.do")
	public @ResponseBody void remove(String ids) {
		phoneService.removeByIds(ids);
	}

	@RequestMapping("/surveillance.phone.set.do")
	public @ResponseBody Map<String, Object> set() {
		Map<String, Object> set = phoneService.set();
		return set;
	}


}
