package com.amani.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

@Component("HttpClientUtil")
public class HttpClientUtil {

	/**
	 * 
	 * 作用描述：GET请求
	 * @throws Exception 
	 * 
	 */
	public static String get(String url) throws Exception {
		String result = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
            	if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK)
            	{
            		throw new Exception(response.getStatusLine().getStatusCode()+"");
            	}
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
            } finally {
            	response.close();
            }
        } finally {
            httpclient.close();
        }
        return result;
	}
	
	
	public static String getHeader(String url,String deviceId) throws Exception {
		String result = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
            httpGet.setConfig(requestConfig);
            httpGet.setHeader("device_id",deviceId);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
            	if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK)
            	{
            		throw new Exception(response.getStatusLine().getStatusCode()+"");
            	}
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
            } finally {
            	response.close();
            }
        } finally {
            httpclient.close();
        }
        return result;
	}
	/**
	 * 
	 * 作用描述：POST请求
	 * @throws Exception 
	 * 
	 */
	public static String postMap(String url,Map<String,String> params) throws Exception {
		String result = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
            Set<String> keySet = params.keySet();  
            for(String key : keySet) {  
                nvps.add(new BasicNameValuePair(key, params.get(key)));  
            }
//            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
            httpPost.setConfig(requestConfig);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
            	if(response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK)
            	{
            		throw new Exception(response.getStatusLine().getStatusCode()+"");
            	}
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
            } finally {
            	response.close();
            }
        } finally {
            httpclient.close();
        }
        return result;
	}
	
}
