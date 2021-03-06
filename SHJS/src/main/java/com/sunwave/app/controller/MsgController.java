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

import com.sunwave.app.model.SlMsg;
import com.sunwave.app.model.SysUser;
import com.sunwave.app.service.MsgService;
import com.sunwave.framework.util.JsonResponse;

@Controller
public class MsgController {

	@Autowired
	private MsgService msgService;
	
	@InitBinder
    public void initBinder(ServletRequestDataBinder binder)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

	@SuppressWarnings("unchecked")
	@RequestMapping("/surveillance.msg.initList.do")
	public ModelAndView initList(Integer areaId,SlMsg slMsg, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("surveillance/msgPage");
		Subject currentUser = SecurityUtils.getSubject();
		SysUser loginUser = (SysUser)currentUser.getSession().getAttribute("loginUser");
		Map<String, Object> initData = (Map<String, Object>)currentUser.getSession().getAttribute("initData");
		mv.addObject("userShowName", loginUser.getUserShowName());
		mv.addObject("userAreaId", loginUser.getSlArea().getAreaId());
		mv.addObject("initData", initData);
		return mv;
	}

	@RequestMapping("/surveillance.msg.getList.do")
	public @ResponseBody Object getList(Integer areaId,SlMsg slMsg,
			Integer page,Integer limit,String sort, HttpServletRequest request) {
		if(areaId==null){
			SysUser user = (SysUser)SecurityUtils.getSubject().getSession().getAttribute("loginUser");
			areaId=user.getSlArea().getAreaId();
		}
		Map<String, Object> findPageInfo = msgService.findPageInfo(areaId,slMsg, page,limit,sort);
		Object okWithPaginateDisableRef = JsonResponse.okWithPaginateDisableRef(findPageInfo.get("data"), (Integer)findPageInfo.get("totalCount"));
		return okWithPaginateDisableRef;
	}

	@RequestMapping("/surveillance.msg.update.do")
	public @ResponseBody void update(SlMsg slMsg) {
//		msgService.update(slMsg);
	}
	
	@RequestMapping("/surveillance.msg.remove.do")
	public @ResponseBody void remove(String ids) {
		msgService.removeByIds(ids);
	}



}
