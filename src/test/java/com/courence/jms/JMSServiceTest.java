package com.courence.jms;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import com.courence.BaseTest;

public class JMSServiceTest extends BaseTest{
	@Autowired
	private IJMSService jmsService;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Test
	public void sendTest(){
		jmsService.send("abc");
		System.out.println(jmsService.receive(String.class));
		
	}
	

}
