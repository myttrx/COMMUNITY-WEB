package com.jos.community.module.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import com.jos.community.module.vo.LoginUserVo;

@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired
	@Qualifier(value = "loginUserValidator")
	private Validator loginUserValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(loginUserValidator);
	}

	@RequestMapping(value = "login.shtml", method = RequestMethod.GET)
	public String index(Model model) {
		return "/account/login";
	}

	@RequestMapping(value = "login.shtml", method = RequestMethod.POST)
	public String login(@ModelAttribute @Validated LoginUserVo loginUserVo, BindingResult result) {
		if (result.hasErrors()) {
			return "/account/login";
		}
		return "main";
	}
	
	
}
