package com.courence.common.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;

public class DynamicBeanUtils implements ApplicationContextAware,ApplicationListener{  
	  
    private ConfigurableApplicationContext app;  
  
  
    public void setApplicationContext(ApplicationContext app) throws BeansException {  
        this.app = (ConfigurableApplicationContext)app;  
    }  
  
      
    public void onApplicationEvent(ApplicationEvent event) {  
        // 如果是容器刷新事件OR Start Event  
        if (event instanceof ContextRefreshedEvent) {  
					regDynamicBean();
        }  
          
    }  
  
    private void regDynamicBean() {  
        // 把bean注册到容器中  
        addSourceBeanToApp();  
    }  
  
    /** 
     * 功能说明：根据DataSource创建bean并注册到容器中 
     *  
     * @param acf 
     * @param mapCustom 
     */  
    private void addSourceBeanToApp() {  
        DefaultListableBeanFactory acf = (DefaultListableBeanFactory) app  
                .getAutowireCapableBeanFactory();  
  
        String DATASOURCE_BEAN_CLASS = "com.mchange.v2.c3p0.ComboPooledDataSource";  
        BeanDefinitionBuilder bdb;  
          
//        Iterator<String> iter = mapCustom.keySet().iterator();  
          
        Map<Object,Object> targetDataSources = new LinkedHashMap<Object, Object>();  
          
//        baseBeanComm = new ChildBeanDefinition("dataSource");  
        //  将默认数据源放入 targetDataSources map中  
        targetDataSources.put("abctest1", app.getBean("runDataSource"));  
  
        // 根据数据源得到数据，动态创建数据源bean 并将bean注册到applicationContext中去  
//        while (iter.hasNext()) {  
              
            //  bean ID  
            String beanKey = "test";  
            //  创建bean  
            /**
            bdb = BeanDefinitionBuilder.genericBeanDefinition(ComboPooledDataSource.class);  
            bdb.getBeanDefinition().setAttribute("id", beanKey);  
            bdb.addPropertyValue("driverClass", "com.mysql.jdbc.Driver");  
            bdb.addPropertyValue("jdbcUrl", "jdbc:mysql://192.168.1.214:3306/tmp_qzj");  
            bdb.addPropertyValue("user", "root");  
            bdb.addPropertyValue("password", "server_VM14.DB");  
            
//            bdb.addPropertyValue("timeBetweenEvictionRunsMillis", 3600000);  
//            bdb.addPropertyValue("minEvictableIdleTimeMillis", 3600000);  
            //  注册bean  
            acf.registerBeanDefinition(beanKey, bdb.getBeanDefinition());  
              
            //  放入map中，注意一定是刚才创建bean对象  
            targetDataSources.put(beanKey, app.getBean(beanKey));  
            **/
          
    }  

}  