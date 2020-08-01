package com.pci;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pci.security.UserAccountService;

@SpringBootTest
class ReportManagementSystemApplicationTests {
	@Autowired
	private UserAccountService sv;

	@Test
	void contextLoads() {
		sv.registerAdmin("00999900", "システム", "管理者", "admin", "9999");
		sv.registerTrainer("00999901", "講師", "1", "trainer", "9999");
		sv.registerTrainer("00999902", "講師", "2", "trainer", "9999");
		sv.registerStudent("21999901", "PSOL", "受講者1", "student", "9999");
		sv.registerStudent("21999902", "PSOL", "受講者2", "student", "9999");
		sv.registerStudent("21999701", "SAMPLE1", "受講者1", "student", "9997");
		sv.registerStudent("21999702", "SAMPLE1", "受講者2", "student", "9997");
		sv.registerStudent("21999801", "SAMPLE2", "受講者1", "student", "9998");
		sv.registerStudent("21999802", "SAMPLE2", "受講者2", "student", "9998");
		sv.registerCustomer("00999700", "SAMPLE1", "担当者", "customer", "9997");
		sv.registerCustomer("00999800", "SAMPLE2", "担当者", "customer", "9998");	
	}
}
