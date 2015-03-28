package com.sunwave.app.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunwave.app.model.SlArea;
import com.sunwave.app.model.SlMapConfig;
import com.sunwave.app.model.SysUser;
import com.sunwave.app.service.MapConfigService;

@Controller
public class MapConfigController {
	
	@Autowired
	private MapConfigService mapConfigService;
	/**
	 * 保持与mxeditors文件夹同名同目录，对应资源相对路径
	 * @return
	 */
	@RequestMapping("/go2MapE.do")
	public ModelAndView go2MapE(){
			ModelAndView modelAndView = new ModelAndView("myworkfloweditor");
			return modelAndView; 
	}
	
	@RequestMapping("/mxeditors/getConfig4User.do")
	@ResponseBody
	public SlMapConfig getConfig4User(Integer areaId){
		SysUser user = (SysUser)SecurityUtils.getSubject().getSession().getAttribute("loginUser");
		if(areaId==null){
			areaId=user.getSlArea().getAreaId();
		}
		SlArea slArea =mapConfigService.getAreaById(areaId);
		SlMapConfig slMapConfig = mapConfigService.getConfig4User(slArea);
		return slMapConfig; 
	}
	
	/**
	 * 保持与mxeditors文件夹同名同目录，对应资源相对路径
	 * @return
	 */
	@RequestMapping("/mxeditors/saveMapConfig.do")
	public @ResponseBody Map<String,Object> saveMapConfig(String xmlContent,HttpServletRequest request){
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		SlMapConfig slMapConfig = new SlMapConfig();
		//admin
		SysUser user = (SysUser)SecurityUtils.getSubject().getSession().getAttribute("loginUser");
		slMapConfig.setConfigUserCode(user.getUsername());
		slMapConfig.setConfigContent("<mxGraphModel>"+xmlContent+"</mxGraphModel>");
		slMapConfig.setConfigType(1);
		Date date = new Date();
		slMapConfig.setConfigDate(new Timestamp(date.getTime()));
		slMapConfig.setInUse(1);
		SlArea slArea =mapConfigService.getAreaById(user.getSlArea().getAreaId());
		slMapConfig.setSlArea(slArea);
		mapConfigService.saveMapConfig4User(slMapConfig);
		return hashMap; 
	}


}
