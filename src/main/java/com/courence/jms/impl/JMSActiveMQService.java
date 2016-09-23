package com.courence.jms.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.courence.common.util.ConfigUtils;
import com.courence.jms.IJMSService;


@Service
public class JMSActiveMQService implements IJMSService {
	
	@Autowired
	private JmsTemplate jmsTemplate;

	
	private static Map<String,Destination> destinationMap = new HashMap<String,Destination>();
	
	/**
	 * 获取/生成Destination
	 * @param name
	 * @return
	 */
	private Destination getDestinationByName(String name){
		Destination destination = destinationMap.get(name);
		if(destination==null){
			destination = new ActiveMQQueue(name);
			destinationMap.put(name, destination);
		}
		return destination;
	}
	
	/**
	 * 生成类队列名称
	 * @param cls
	 * @return
	 */
	private String getClassQueueName(Class<?> cls){
		return ConfigUtils.getValue("MAIN.systemName")+cls.getSimpleName();
	}

	@Override
	public <T extends Serializable> void send(final T obj) {
		send(obj,getClassQueueName(obj.getClass()));
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public <T> T receive(Class<T> cls) {
		return (T) receive(getClassQueueName(cls));
	}

	@Override
	public <T extends Serializable> void send(final T obj, String queueName) {
		send(obj, getDestinationByName(queueName));
	}

	@Override
	public Object receive(String queueName) {
		return receive(getDestinationByName(queueName));
	}

	@Override
	public <T extends Serializable> void send(final T objMsg, Destination destination) {
		jmsTemplate.send(destination,new MessageCreator(){
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage obj = session.createObjectMessage(objMsg);
				return obj;
			}
		});
	}

	@Override
	public Object receive(Destination destination) {
		
		ObjectMessage obj = (ObjectMessage) jmsTemplate.receive(destination);
		try {
			return  obj.getObject();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return null;
	}

}
