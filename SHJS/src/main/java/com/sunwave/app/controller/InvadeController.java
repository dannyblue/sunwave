package com.sunwave.app.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunwave.app.model.SlInvade;
import com.sunwave.app.model.SysUser;
import com.sunwave.app.service.InvadeService;
import com.sunwave.framework.util.JsonResponse;

@Controller
public class InvadeController {
	@Autowired
	private InvadeService invadeService;
	
	@InitBinder
    public void initBinder(ServletRequestDataBinder binder)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

	@SuppressWarnings("unchecked")
	@RequestMapping("/surveillance.invade.initList.do")
	public ModelAndView initList(Integer areaId,SlInvade slInvade, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("surveillance/invadePage");
		Subject currentUser = SecurityUtils.getSubject();
		SysUser loginUser = (SysUser)currentUser.getSession().getAttribute("loginUser");
		Map<String, Object> initData = (Map<String, Object>)currentUser.getSession().getAttribute("initData");
		mv.addObject("userShowName", loginUser.getUserShowName());
		mv.addObject("userAreaId", loginUser.getSlArea().getAreaId());
		mv.addObject("initData", initData);
		return mv;
	}

	@RequestMapping("/surveillance.invade.getList.do")
	public @ResponseBody Object getList(Integer areaId,SlInvade slInvade,
			Integer page,Integer limit,String sort, HttpServletRequest request) {
		if(areaId==null){
			SysUser user = (SysUser)SecurityUtils.getSubject().getSession().getAttribute("loginUser");
			areaId=user.getSlArea().getAreaId();
		}
		Map<String, Object> findPageInfo = invadeService.findPageInfo(areaId,slInvade, page,limit,sort);
		Object okWithPaginateDisableRef = JsonResponse.okWithPaginateDisableRef(findPageInfo.get("data"), (Integer)findPageInfo.get("totalCount"));
		return okWithPaginateDisableRef;
	}
	
	@RequestMapping("/surveillance.invade.update.do")
	public @ResponseBody void update(SlInvade slInvade){
//		invadeService.update(slInvade);
	}

	@RequestMapping("/surveillance.invade.remove.do")
	public @ResponseBody void remove(String ids) {
		invadeService.removeByIds(ids);
	}
}
