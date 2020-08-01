package com.pci.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.pci.entity.MtCompany;
import com.pci.entity.MtRole;
import com.pci.entity.MtUser;
import com.pci.repository.CompanyRepository;
import com.pci.repository.RoleRepository;
import com.pci.repository.UserRepository;

@Controller
@SessionAttributes("formModel")
public class UserController {
	UserDetails userDetail;
	MtUser mtUser;

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@ModelAttribute(value = "formModel")
	public MtUser setUpMtUser() {
		return new MtUser();
	}
	
	@RequestMapping(value="/userManagement",method=RequestMethod.GET)
	public ModelAndView usetList(ModelAndView mv,@AuthenticationPrincipal UserDetails userDetail) {
		this.userDetail=userDetail;
		mtUser=userRepository.findByUsercode(this.userDetail.getUsername()).get();
		mv.addObject("userName", mtUser.getLastname()+mtUser.getFirstname());
		Iterable<MtUser>userList = userRepository.findAll();
		mv.addObject("userList", userList);
		mv.setViewName("/100admin/110userList");
		return mv;
	}
	
	@RequestMapping(value = "/userUpdate/{usercode}",method=RequestMethod.POST)
	public ModelAndView userUpdate(
			@PathVariable String usercode,
			ModelAndView mv) {
		mv.addObject("userName", mtUser.getLastname()+mtUser.getFirstname());
		Optional<MtUser> user = userRepository.findByUsercode(usercode);
		mv.addObject("formModel", user.get());
		List<MtCompany> companyList = companyRepository.findAll();
		mv.addObject("companyList", companyList);
		List<MtRole> roleList = roleRepository.findAll();
		mv.addObject("roleList", roleList);
		mv.setViewName("/100admin/111userUpdate");
		return mv;
	}
	
	@RequestMapping(value = "/userUpdateConf",method=RequestMethod.POST)
	public ModelAndView userUpdateConf(
			@ModelAttribute("formModel")MtUser user,
			BindingResult result,
			ModelAndView mv) {
		mv.addObject("userName", mtUser.getLastname()+mtUser.getFirstname());
		if(!result.hasErrors()) {
			mv.setViewName("/100admin/112userUpdateConf");
		}else {
			mv.addObject("msg", "エラーが発生しました");
			mv.setViewName("/100admin/111userUpdate");
		}
		return mv;
	}
	
	@RequestMapping(value = "/newUserCreate",method=RequestMethod.POST)
	public ModelAndView newUserCreate(ModelAndView mv) {
		mv.addObject("userName", mtUser.getLastname()+mtUser.getFirstname());
		MtUser user = new MtUser();
		mv.addObject("formModel", user);
		mv.setViewName("/100admin/115newUserCreate");
		return mv;
	}
	
	@RequestMapping(value = "/newUserCreateConf",method=RequestMethod.POST)
	public ModelAndView newUserCreateConf(
			@ModelAttribute("formModel")@Validated MtUser user,
			BindingResult result,
			ModelAndView mv) {
		mv.addObject("userName", mtUser.getLastname()+mtUser.getFirstname());
		Optional<MtUser> exist = userRepository.findByUsercode(user.getUsercode());
		if(!result.hasErrors()) {
			if(exist.isEmpty()) {
				mv.setViewName("/100admin/116newUserCreateConf");
			}else {
				mv.addObject("msg", "そのユーザコードはすでに使われています");
				mv.setViewName("/100admin/115newUserCreate");
			}
		}else {
			mv.addObject("msg", "入力内容に問題があります");
			mv.setViewName("/100admin/115newUserCreate");
		}
		return mv;
	}

	@RequestMapping(value = "/userReg",method=RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView userReg(
			@ModelAttribute("formModel")@Validated MtUser user,
			ModelAndView mv) {
		mv.addObject("userName", mtUser.getLastname()+mtUser.getFirstname());
		userRepository.saveAndFlush(user);
		mv.setViewName("redirect:/user?List");
		return mv;
	}
	@RequestMapping(value = "/user",method=RequestMethod.GET,params="List")
	public ModelAndView saveComplete(SessionStatus sessionStatus,ModelAndView mv) {
		sessionStatus.setComplete();
		mv.setViewName("redirect:/userManagement");
		return mv;
	}
	
	@RequestMapping(value = "/passwordChange",method=RequestMethod.POST)
	public ModelAndView passwordChange(ModelAndView mv,@AuthenticationPrincipal UserDetails userDetail) {
		this.userDetail=userDetail;
		mtUser=userRepository.findByUsercode(this.userDetail.getUsername()).get();
		mv.addObject("userName", mtUser.getLastname()+mtUser.getFirstname());
		mv.addObject("formModel", mtUser);
		mv.setViewName("/500passwordChange/510changePassword");
		return mv;
	}
	
	@RequestMapping(value = "/passwordReg",method=RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView passwordReg(@ModelAttribute("formModel")@Validated MtUser user,
			BindingResult result,
			ModelAndView mv) {
		mv.addObject("userName", mtUser.getLastname()+mtUser.getFirstname());
		if(!result.hasErrors()) {
			if(passwordEncoder.matches(user.getCurrentPassword(), user.getPassword())) {
				if(user.getNewPassword().equals(user.getNewPasswordAgain())) {
					user.setPassword(passwordEncoder.encode(user.getNewPassword()));
					userRepository.saveAndFlush(user);
					mv.addObject("msg", "パスワードの変更が完了しました");
					mv.setViewName("/500passwordChange/511completePasswordChange");
				}else {
					mv.addObject("msg", "新しいパスワードが一致しません");
					mv.setViewName("/500passwordChange/510changePassword");
				}
			}else {
				mv.addObject("msg", "現在のパスワードが一致しません");
				mv.setViewName("/500passwordChange/510changePassword");
			}
		}
		return mv;
	}
}
