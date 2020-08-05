package com.pci.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.pci.entity.MtUser;
import com.pci.entity.TrDailyreport;
import com.pci.repository.DailyreportRepository;
import com.pci.repository.ReportstatusRepository;
import com.pci.repository.UserRepository;

@Controller
@SessionAttributes("formModel")
public class DailyreportController {
	UserDetails userDetail;
	MtUser mtUser;
	
	@Autowired
	DailyreportRepository dailyreportRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ReportstatusRepository reportstatusRepository;
	
	@ModelAttribute(value = "formModel")
	public TrDailyreport setUpTrDailyreport() {
		return new TrDailyreport();
	}
	
	
	@RequestMapping(value = "/editDailyreport",method=RequestMethod.POST)
	public ModelAndView editDailyreport(ModelAndView mv,@AuthenticationPrincipal UserDetails userDetail) {
		this.userDetail=userDetail;
		mtUser=userRepository.findByUsercode(this.userDetail.getUsername()).get();
		mv.addObject("userName", mtUser.getLastname()+mtUser.getFirstname());
		Date today= new Date();
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String todayString = df.format(today);
		Optional<TrDailyreport> exist = dailyreportRepository.findReportOfTodayByUsercode(mtUser.getUsercode(),today);
		if(exist.isPresent()) {
			TrDailyreport report = exist.get();
			report.setRegdateString(df.format(report.getRegdate()));
			mv.addObject("formModel", report);
		}else {
			TrDailyreport newReport = new TrDailyreport(today,mtUser,todayString);
			mv.addObject("formModel", newReport);
		}
		mv.setViewName("/300student/311editDailyreport");
		return mv;
	}
	
	@RequestMapping(value = "/dailyreportReg",method=RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView dailyreportReg(
			@ModelAttribute("formModel")@Validated TrDailyreport report,
			BindingResult result,
			ModelAndView mv) {
		mv.addObject("userName", mtUser.getLastname()+mtUser.getFirstname());
		if(!result.hasErrors()) {
			report.setMtReportstatus(reportstatusRepository.getOne("1"));
			dailyreportRepository.saveAndFlush(report);
			mv.addObject("msg", "日報を登録しました");
			mv.setViewName("redirect:/dailyreportHistory");
		}else {
			mv.addObject("msg", "入力内容に問題があります");
			mv.setViewName("/300student/311editDailyreport");
		}
		return mv;
	}
	
	@RequestMapping(value = "dailyreportHistory",method=RequestMethod.GET)
	public ModelAndView dailyreportHistory(ModelAndView mv,@AuthenticationPrincipal UserDetails userDetail) {
		this.userDetail=userDetail;
		mtUser=userRepository.findByUsercode(this.userDetail.getUsername()).get();
		mv.addObject("userName", mtUser.getLastname()+mtUser.getFirstname());
		
		mv.addObject("dailyreportList", dailyreportRepository.findByUsercode(mtUser.getUsercode()));
		mv.setViewName("/300student/310dailyreportHistory");
		return mv;
	}

}
