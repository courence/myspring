package com.courence.jms.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerMessageListener implements MessageListener {  
	private static final Logger logger = LoggerFactory.getLogger(ConsumerMessageListener.class);
    public void onMessage(Message message) {  
        //这里我们知道生产者发送的就是一个纯文本消息，所以这里可以直接进行强制转换，或者直接把onMessage方法的参数改成Message的子类TextMessage  
    	ObjectMessage obj = (ObjectMessage) message;  
        System.out.println("接收到一个纯文本消息。");  
        logger.error("asdfsf");
        try {  
            System.out.println("消息内容是：" + obj.getObject());  
        } catch (JMSException e) {  
            e.printStackTrace();  
        }  
    }  
}  
