package com.sunwave.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunwave.app.service.ReceiveWarnService;

@Controller
public class ReceiveWarnController {
	
	@Autowired
	private ReceiveWarnService receiveWarnService;
	
	@RequestMapping("/warn.receive.do")
	@ResponseBody
	public Map<String, Object> receive(String para){
		Map<String, Object> saveWarn = receiveWarnService.saveWarn(para);
		return saveWarn;
	}
	
	@RequestMapping("/warn.cancel.do")
	@ResponseBody
	public Map<String, Object> cancel(Integer id,Integer otype,String cancelCause,String cancelPassword){
		Map<String, Object> cancelWarn = receiveWarnService.cancelWarn(id,otype,cancelCause,cancelPassword);
		return cancelWarn;
	}
	
}
