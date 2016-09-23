package com.courence.jms.impl;

import java.io.Serializable;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.courence.jms.IJMSService;


@Service
public class JMSService implements IJMSService {
	
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Destination testDest;

	@Override
	public <T extends Serializable> boolean send(final T msg) {

		jmsTemplate.send(testDest,new MessageCreator(){
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage obj = session.createObjectMessage(msg);
				return obj;
			}
		});

		return true;
	}

	@Override
	public Object receive() {
		ObjectMessage obj = (ObjectMessage) jmsTemplate.receive(testDest);
		try {
			return  obj.getObject();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
