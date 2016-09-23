package com.courence.jms;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.courence.BaseTest;

public class JMSServiceTest extends BaseTest{
	@Autowired
	private IJMSService jmsService;
	
	@Test
	public void sendTest(){
		jmsService.send("abc");
		System.out.println(jmsService.receive());
	}
}
