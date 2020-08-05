package com.pci.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pci.entity.MtUser;
import com.pci.repository.UserRepository;

@Controller
public class LoginController {
	@Autowired
	UserRepository userRepository;
	
	UserDetails userDetails;
	MtUser mtUser;
	
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv) {
		mv.addObject("iserror", false);
		mv.setViewName("/000login");
		return mv;
	}
	@RequestMapping(value = "/login-error",method = RequestMethod.GET)
	public ModelAndView loginError(ModelAndView mv) {
		mv.addObject("iserror", true);
		mv.setViewName("/000login");
		return mv;
	}
	
	@RequestMapping(value = "/index",method=RequestMethod.GET)
	public ModelAndView index(ModelAndView mv,Authentication auth,@AuthenticationPrincipal UserDetails userDetails) {
		mv.addObject("iserror", false);
		this.userDetails=userDetails;
		if(userDetails.isEnabled()) {
			mtUser=userRepository.findByUsercode(userDetails.getUsername()).get();
			mv.addObject("userName", mtUser.getLastname()+mtUser.getFirstname());
			Collection<? extends GrantedAuthority> auths=auth.getAuthorities();
			for(GrantedAuthority ga:auths) {
				if(ga.getAuthority().equals("ROLE_ADMIN")) {
					mv.setViewName("forward:/userManagement");
					break;
				}else if(ga.getAuthority().equals("ROLE_TRAINER")) {
					//mv.setViewName(viewName);
					break;
				}else if(ga.getAuthority().equals("ROLE_STUDENT")) {
					mv.setViewName("forward:/dailyreportHistory");
					break;
				}else if(ga.getAuthority().equals("ROLE_CUSTOMER")){
					//mv.setViewName(viewName);
					break;
				}
			}
		}else {
			mv.setViewName("/login-error");
		}
		return mv;
	}
}
