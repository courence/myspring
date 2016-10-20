package com.courence.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
public class HttpClientTools {
    private static final String GATE_CENTER_ADDRESS = "gate_center_address";
    private static final String PAY_GATE_ADDRESS = "pay_gate_address";
    private static final Logger logger = LoggerFactory.getLogger(HttpClientTools.class);
    //org.apache.http.params.HttpParams params
    private static String domain = null;
    private static String payDomain = null;
    private static HttpParams httpParams;
    private static PoolingClientConnectionManager cm;
    private static HttpClient client;
    /**
     * 最大连接数
     */
    public static final int MAX_TOTAL_CONNECTIONS = 200;
    /**
     * 获取连接的最大等待时间
     */
    public static final int WAIT_TIMEOUT = 30000;
    /**
     * 每个路由最大连接数
     */
    public static final int MAX_ROUTE_CONNECTIONS = 300;
    /**
     * 连接超时时间
     */
    public static final int CONNECT_TIMEOUT = 60000;
    /**
     * 读取超时时间
     */
    public static final int READ_TIMEOUT = 60000;

    static {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(
                new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(
                new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
        
        cm = new PoolingClientConnectionManager(schemeRegistry);
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(80);
        HttpParams httpParams = new BasicHttpParams();
        httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECT_TIMEOUT);
        httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, READ_TIMEOUT);
    }

    public static synchronized HttpClient getHttpClient() {
        if (null == client) {
            client = new DefaultHttpClient(cm, httpParams);
        }
        return client;
    }
    public static String postJson(String url, JSONObject param) {
        long t1 = System.currentTimeMillis();
        String addrUrl =  url;
        HttpClient httpclient = getHttpClient();
        HttpPost post = new HttpPost(addrUrl);    
        String result = null;
        try {
            @SuppressWarnings("unchecked")
			Iterator<String> it = param.keys();  
            List<NameValuePair> nvps = new ArrayList<NameValuePair>(); 
            while (it.hasNext()) {  
                String key = it.next();
                nvps.add(new BasicNameValuePair(key, param.getString(key)));
            }
            post.setEntity(new UrlEncodedFormEntity(nvps));
            
            HttpResponse httpResponse = httpclient.execute(post);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity en = httpResponse.getEntity();
                result = EntityUtils.toString(en, "UTF-8");
                EntityUtils.consume(en);
            } else {
                String msg = "接http返回错误码, error=" + httpResponse.getStatusLine().toString() 
                        + ", url=" + addrUrl + ", param=" + param;
                logger.error(msg);
                result = "1";
            }
        } catch (Exception e) {
            logger.error("url=" + addrUrl, e);
            result = "2";
        } finally {
            if (null != post) {
                post.releaseConnection();
            }
        }
        long t2 = System.currentTimeMillis();
        logger.trace("url={}, inParam={}, outParam={},, cost times={}",addrUrl,param,result,(t2 - t1));
        System.out.println(result);
        return result;
    }
    
    public static void main(String[] args){
		String url = "http://172.21.2.154:9001/sms/batchSend.html";
		JSONObject json = new JSONObject();
		JSONArray mobiles = new JSONArray();
		mobiles.add("13547821050");
		json.put("content", "123456");
		json.put("mobiles", mobiles);
		json.put("channel", "test");
		json.put("smsCode", "1");
		HttpClientTools.postJson(url,json);
	}
    
}
