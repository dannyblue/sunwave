package com.sunwave.app.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sunwave.app.model.SlArea;
import com.sunwave.app.model.SysUser;
import com.sunwave.app.service.LoginService;
import com.sunwave.app.service.SysUserService;
import com.sunwave.framework.util.JsonResponse;
import com.sunwave.framework.util.RandomCodeTools;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 
	 * @return 登陆页面
	 */
	@RequestMapping(value="/login.do",method=RequestMethod.GET)  
    public ModelAndView loginForm(String message){
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("sysUser", new SysUser());
		mv.addObject("message", message);
        return mv;  
    }
	
	/**
	 * 
	 * @return 登陆验证后跳转
	 */
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public ModelAndView login(SysUser sysUser) {
    	String username = sysUser.getUsername();
    	String password = sysUser.getPassword();
        Subject currentUser = SecurityUtils.getSubject();  
        String result = "login";  
        ModelAndView mv = new ModelAndView();
        //shiro 登陆验证
        result = loginCheck(currentUser,username,password);
        //验证返回后跳转
		if("success".equals(result)){
			SysUser loginUser = sysUserService.findByUsername(username);
			//使用shiro自带的session存储用户信息 独立于httpSession
			currentUser.getSession().setAttribute("loginUser", loginUser);
			Map<String, Object> initData = loginService.initUserData(loginUser);
			currentUser.getSession().setAttribute("initData", initData);
			mv.addObject("userShowName", loginUser.getUserShowName());
			mv.addObject("username", loginUser.getUsername());
			mv.addObject("userAreaId", loginUser.getSlArea().getAreaId());
			mv.addObject("initData", initData);
			mv.setViewName("index_0");
        }else{
        	mv.addObject("message", result);
        	mv.setViewName("login");
        }  
		return mv;
    }
	
	/**
	 * 
	 * @param currentUser
	 * @param username
	 * @param password
	 * @return 登陆验证返回
	 */
	private String loginCheck(Subject currentUser,String username,String password){
		String result = "login";
        UsernamePasswordToken token = new UsernamePasswordToken(username,  
                password);  
        token.setRememberMe(false);  
        try {  
            currentUser.login(token);  
            result = "success";  
        } catch (UnknownAccountException uae) {
        	//账户不存在
            result = "账户不存在";  
        } catch (IncorrectCredentialsException ice) {
        	//验证失败
            result = "密码错误";  
        } catch (LockedAccountException lae) {
        	//账户锁定
            result = "账户已锁定";  
        } catch (AuthenticationException ae) {
        	//其他异常
            result = "未知异常";  
        }  
        return result;  
	}
	
	/**
	 * 
	 * @return 首页跳转
	 */
	@RequestMapping(value = "/index.do")
    public ModelAndView index() {
			ModelAndView mv = new ModelAndView("index_0");
			Subject currentUser = SecurityUtils.getSubject();  
			//使用shiro自带的session存储用户信息 独立于httpSession
			SysUser loginUser = (SysUser)currentUser.getSession().getAttribute("loginUser");
			Map<String, Object> initData = loginService.initUserData(loginUser);
			currentUser.getSession().setAttribute("initData", initData);
			mv.addObject("userShowName", loginUser.getUserShowName());
			mv.addObject("username", loginUser.getUsername());
			mv.addObject("userAreaId", loginUser.getSlArea().getAreaId());
			mv.addObject("initData", initData);
			//刷新主页石，更新seesion里面的数据初始化信息
			currentUser.getSession().setAttribute("initData",initData);
			return mv;
    }
	
	
	/**
	 * 
	 * @return logout
	 */
	@RequestMapping("/logout.do")
    public ModelAndView logout(){
		SecurityUtils.getSubject().logout();  
    	ModelAndView mv = new ModelAndView("login");
    	mv.addObject("message", "安全退出");
    	return mv;
    }
	
	/**
	 * 
	 * @return 自定义404页面
	 */
	@RequestMapping("/404.do")
    public ModelAndView error404(){
    	ModelAndView mv = new ModelAndView("404");
    	mv.addObject("message", "404");
    	return mv;
    }
    
	/**
	 * 
	 * @return 自定义500页面
	 */
    @RequestMapping("/500.do")
    public ModelAndView error500(){
    	ModelAndView mv = new ModelAndView("500");
    	mv.addObject("message", "500");
    	return mv;
    }
    /**
	 * 获得验证码
	 * TODO
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getRandomCode.do")
	public void getRandomCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		RandomCodeTools.RandomCode(session, response);
	}
	
	
	@RequestMapping("/cc.findCCdata.do")
	@ResponseBody
	public Map<String, Object> findCCData(String dataType,String ccType,String tType){
		SysUser user = (SysUser)SecurityUtils.getSubject().getSession().getAttribute("loginUser");
		return loginService.findCCData(user,dataType, ccType, tType);
		
	}
	//@RequiresRoles("manager")
	@RequestMapping("/go2AreaPage.do")
	public ModelAndView go2AreaPage(Integer areaId){
			ModelAndView modelAndView = new ModelAndView("index_2");
			SysUser go2User = new SysUser();
			SlArea slArea = new SlArea();
			slArea.setAreaId(areaId);
			slArea.setAreaGrade(2);
			go2User.setSlArea(slArea);
			go2User.setUserType(2);
			//获取用户初始化数据
			Map<String, Object> initData = loginService.initUserData(go2User);
			modelAndView.addObject("initData", initData);
			modelAndView.addObject("go2User", go2User);
			return modelAndView;
	}
	
	@RequestMapping("/getAreaData.do")
	@ResponseBody
	public Object getAreaData(String areaId){
		Integer curAreaId=null;
		SlArea slArea = new SlArea();
		SysUser go2User = new SysUser();
		if("home".equals(areaId)){
			SysUser user = (SysUser)SecurityUtils.getSubject().getSession().getAttribute("loginUser");
			curAreaId=user.getSlArea().getAreaId();
			slArea.setAreaGrade(1);
			go2User.setUserType(1);
		}else{
			curAreaId= Integer.valueOf(areaId);
			slArea.setAreaGrade(2);
			go2User.setUserType(2);
		}
		slArea.setAreaId(curAreaId);
		go2User.setSlArea(slArea);
		//获取用户初始化数据
		Map<String, Object> initData = loginService.initUserData(go2User);
		return JsonResponse.ok(initData);
	}
	
}
