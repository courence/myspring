/**
 * 
 */
package com.courence.common.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

/**
 * 获取配置文件值
 * date: Sep 23, 2016 5:17:40 PM <br/> 
 * @author jh <br/> 
 */
public class ConfigUtils {
	private static Map<String, String> propMap = new HashMap<String, String>();
    public static ServletContext currentServletContext = null;
    
    static {
        try {
        	InputStreamReader is = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("config/main.properties"),"UTF-8");
            Properties prop = new Properties();
            prop.load(is);
            @SuppressWarnings("rawtypes")
            Enumeration enu = prop.propertyNames();
            while (enu.hasMoreElements()) {
                String key = enu.nextElement().toString();
                propMap.put(key, prop.getProperty(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
    
    /**
     * 获取配置值
     * @param key
     * @return
     */
    public static String getValue(String key) {
        return propMap.get(key);
    }
    
    
}
