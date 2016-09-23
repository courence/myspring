package com.courence.demo;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.courence.jms.IJMSService;

@Controller
public class HomeController {
	
	@Autowired
	private IJMSService jmsService;
	
    @RequestMapping({"/","/index"})
    public String showHomePage(Map<String,Object> model)
    {
//        model.put("WelcomeWords", "欢迎你，老大");
        //System.out.println("showHomepage executed, using stock");
//        jmsService.send(new T(1));
//        T a = (T) jmsService.receive();
//        a.print();
        return "index";
    }
}

class T implements Serializable{
	private static final long serialVersionUID = -9034204983696300653L;
	int a;
	T(int a){this.a = a;}
	public void print(){
		System.out.println(a);
	}
}