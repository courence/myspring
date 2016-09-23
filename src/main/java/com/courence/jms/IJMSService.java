package com.courence.jms;

import java.io.Serializable;

import javax.jms.Destination;

/**
 * jms公共接口
 * 
 * date: Sep 23, 2016 4:27:48 PM <br/> 
 * @author jh <br/>
 */
public interface IJMSService {
	/**
	 * 发送对象数据
	 * 每个类一个队列
	 * @param obj
	 * @return
	 */
	public <T extends Serializable> void send(final T obj);
	
	/**
	 * 向queueName队列发送对象数据
	 * @param obj
	 * @param queueName
	 * @return
	 */
	public <T extends Serializable> void send(final T obj,String queueName);
	
	/**
	 * 向destination发送对象数据
	 * @param obj
	 * @param queueName
	 * @return
	 */
	public <T extends Serializable> void send(final T obj,Destination destination);
	
	/**
	 * 从类队列中获取对象数据
	 * 阻塞操作
	 * @param cls
	 * @return
	 */
	public <T> T receive(Class<T> cls);
	
	/**
	 * 从destination获取数据
	 * @param queueName
	 * @return
	 */
	public Object receive(Destination destination);
	
	public Object receive(String queueName);
}
