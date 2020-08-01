package com.pci.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.pci.entity.MtUser;
import com.pci.repository.CompanyRepository;
import com.pci.repository.UserRepository;

@Controller
@SessionAttributes("formModel")
public class CompanyController {
	UserDetails userDetail;
	MtUser mtUser;
	
	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	UserRepository userRepository;
	
	@ModelAttribute(value = "formModel")
	public MtCompany setUpMtCompany() {
		return new MtCompany();
	}
	
	@RequestMapping(value = "/companyManagement",method=RequestMethod.GET)
	public ModelAndView companyList(ModelAndView mv,@AuthenticationPrincipal UserDetails userDetail) {
		this.userDetail=userDetail;
		mtUser=userRepository.findByUsercode(this.userDetail.getUsername()).get();
		mv.addObject("userName", mtUser.getLastname()+mtUser.getFirstname());
		Iterable<MtCompany>companyList = companyRepository.findAll();
		mv.addObject("companyList", companyList);
		mv.setViewName("/100admin/120companyList");
		return mv;
	}
	@RequestMapping(value = "/companyUpdate/{companycode}",method=RequestMethod.POST)
	public ModelAndView companyUpdate(@PathVariable String companycode,ModelAndView mv) {
		mv.addObject("userName", mtUser.getLastname()+mtUser.getFirstname());
		Optional<MtCompany> company = companyRepository.findByCompanycode(companycode);
		mv.addObject("formModel", company.get());
		mv.setViewName("/100admin/121companyUpdate");
		return mv;
	}
	
	@RequestMapping(value = "/companyUpdateConf",method=RequestMethod.POST)
	public ModelAndView companyUpdateConf(
			@ModelAttribute("formModel") @Validated MtCompany company,
			BindingResult result,
			ModelAndView mv) {
		mv.addObject("userName", mtUser.getLastname()+mtUser.getFirstname());
		if(!result.hasErrors()) {
			mv.setViewName("/100admin/122companyUpdateConf");
		}else {
			mv.addObject("msg", "入力内容に問題があります");
			mv.setViewName("/100admin/121companyUpdate");
		}
		return mv;
	}
	
	@RequestMapping(value = "/newCompanyCreate",method=RequestMethod.POST)
	public ModelAndView newCompanyCreate(ModelAndView mv) {
		mv.addObject("userName", mtUser.getLastname()+mtUser.getFirstname());
		MtCompany newCompany = new MtCompany();
		mv.addObject("formModel", newCompany);
		mv.setViewName("/100admin/125newCompanyCreate");
		return mv;
	}
	
	@RequestMapping(value = "/newCompanyCreateConf",method=RequestMethod.POST)
	public ModelAndView newCompanyCreateConf(
			@ModelAttribute("formModel")@Validated MtCompany newCompany,
			BindingResult result,
			ModelAndView mv) {
		mv.addObject("userName", mtUser.getLastname()+mtUser.getFirstname());
		Optional<MtCompany> exist = companyRepository.findByCompanycode(newCompany.getCompanycode());
		if(!result.hasErrors()) {
			if(exist.isEmpty()) {
				mv.setViewName("/100admin/126newCompanyCreateConf");
			}else {
				mv.addObject("msg", "その企業コードはすでに使われています");
				mv.setViewName("/100admin/125newCompanyCreate");
			}
		}else {
			mv.addObject("msg", "入力内容に問題があります");
			mv.setViewName("/100admin/125newCompanyCreate");
		}
		return mv;
	}
	
	@RequestMapping(value = "/companyReg",method=RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView companyReg(
			@ModelAttribute("formModel")@Validated MtCompany company,
			ModelAndView mv) {
		mv.addObject("userName", mtUser.getLastname()+mtUser.getFirstname());
		companyRepository.saveAndFlush(company);
		mv.setViewName("redirect:/company?List");
		return mv;
	}
	
	@RequestMapping(value = "/company",method=RequestMethod.GET,params="List")
	public ModelAndView saveComplete(SessionStatus sessionStatus,ModelAndView mv) {
		sessionStatus.setComplete();
		mv.setViewName("redirect:/companyManagement");
		return mv;
	}
}
