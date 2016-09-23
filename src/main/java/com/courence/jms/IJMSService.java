package com.courence.jms;

import java.io.Serializable;


public interface IJMSService {
	public <T extends Serializable> boolean send(final T msg);
	public Object receive();
}
