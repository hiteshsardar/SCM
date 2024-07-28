package com.contact.scm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.contact.scm.services.EmailService;

@SpringBootTest
class ScmApplicationTests {

	@Autowired
	private EmailService service;

	@Test
	void contextLoads() {
	}

	@Test
	void sendEmail(){
		service.sendEmail(
			"hiteshsardar1998@gmail.com", 
			"Just for test", 
			"This is a testing mail");
	}

}
