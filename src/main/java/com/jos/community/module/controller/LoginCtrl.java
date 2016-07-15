package com.jos.community.module.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.jos.community.module.vo.LoginUserVo;
import com.jos.security.JosAccessDecisionManager;
import com.jos.security.JosDaoAuthenticationProvider;

@Controller
@RequestMapping("/")
public class LoginCtrl {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	@Qualifier(value = "loginUserValidator")
	private Validator loginUserValidator;

	@Autowired
	private JosDaoAuthenticationProvider authenticationProvider;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(loginUserValidator);
	}

	@RequestMapping(value = "login.shtml", method = RequestMethod.GET)
	public String index(Model model) {
		LoginUserVo loginUserVo = new LoginUserVo();
	     model.addAttribute("loginUserVo", loginUserVo);
		return "/account/login";
	}

	@RequestMapping(value = "login.shtml", method = RequestMethod.POST)
	public String login(@ModelAttribute @Validated LoginUserVo loginUserVo, BindingResult result,SessionStatus status,HttpServletRequest request) {
		if (result.hasErrors()) {
			return "/account/login";
		}
		status.setComplete();
		return "redirect:main.shtml";
	}
	
	@RequestMapping(value = "main.shtml", method = RequestMethod.GET)
	public String main(Model model) {
		logger.info("-----main page-----");
		return "/main";
	}
}
