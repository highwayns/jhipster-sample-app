package com.activiti6.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.activiti6.pagemodel.MSG;
import com.activiti6.service.LoginService;
import io.swagger.annotations.Api;


@Api(value = "登录登出接口")
@Controller
public class Login {
	@Autowired
	LoginService loginservice;

	@RequestMapping(value="/index",method = RequestMethod.GET)
	public String index(){
		return "index";
	}

	@RequestMapping(value="/company/about",method = RequestMethod.GET)
	public String about(){
		return "company/about/index";
	}

	@RequestMapping(value="/post",method = RequestMethod.GET)
	public String post(){
		return "post/index";
	}

	@RequestMapping(value="/loginvalidate",method = RequestMethod.POST)
	public String loginvalidate(@RequestParam("username") String username,
								@RequestParam("password") String pwd,
								@RequestParam("redirecturl") String redirecturl,
								@RequestParam("fromurl") String fromurl,
								HttpSession httpSession){
		if(username==null)
			return fromurl;
		String realpwd=loginservice.getpwdbyname(username);
		if(realpwd!=null&&pwd.equals(realpwd))
		{
			httpSession.setAttribute("username", username);
			return redirecturl;
		}else
			return "fail";
	}
	
	@RequestMapping(value="/login/user_login",method = RequestMethod.GET)
	public String user_login(){
		return "login/user_login/index";
	}

	@RequestMapping(value="/login/system_manager_login",method = RequestMethod.GET)
	public String system_manager_login(){
		return "login/system_manager_login/index";
	}

	@RequestMapping(value="/login/manager_login",method = RequestMethod.GET)
	public String manager_login(){
		return "login/manager_login/index";
	}

	@RequestMapping(value="/login/senior_manager_login",method = RequestMethod.GET)
	public String senior_manager_login(){
		return "login/senior_manager_login/index";
	}

	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public String logout(HttpSession httpSession){
		httpSession.removeAttribute("username");
		return "/";
	}
	
	@RequestMapping(value="/currentuser",method = RequestMethod.GET)
	@ResponseBody
	public MSG currentuser(HttpSession httpSession){
		String userid=(String) httpSession.getAttribute("username");
		return new MSG(userid);
	}	
	
  }
