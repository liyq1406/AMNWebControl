package com.utils;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.utils.http.HttpClientConnectionManager;

public class GetWxOrderno
{
  public static DefaultHttpClient httpclient;

  static
  {
    httpclient = new DefaultHttpClient();
    httpclient = (DefaultHttpClient)HttpClientConnectionManager.getSSLInstance(httpclient);
  }

  public static Map getPayNoMap(String url,String xmlParam) throws JDOMException, IOException{
	  DefaultHttpClient client = new DefaultHttpClient();
	  client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
	  HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
	  String prepay_id = "";
   	 httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
		 HttpResponse response = httpclient.execute(httpost);
	     String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
	    // Map<String, Object> dataMap = new HashMap<String, Object>();
	   //  System.out.println("返回："+jsonStr);
	     
//	    if(jsonStr.indexOf("FAIL")!=-1){
//	    	return null;
//	    }
	    Map map = doXMLParse(jsonStr);
	    return map;
	
  }

//  public static String getPayNo(String url,String xmlParam){
//	  DefaultHttpClient client = new DefaultHttpClient();
//	  client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
//	  HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
//	  String prepay_id = "";
//     try {
//		 httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
//		 HttpResponse response = httpclient.execute(httpost);
//	     String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
//	     Map<String, Object> dataMap = new HashMap<String, Object>();
//	     System.out.println(jsonStr);
//	     
//	    if(jsonStr.indexOf("FAIL")!=-1){
//	    	return prepay_id;
//	    }
//	    Map map = doXMLParse(jsonStr);
//	    String return_code  = (String) map.get("return_code");
//	    prepay_id  = (String) map.get("prepay_id");
//	} catch (Exception e) {
//		
//		e.printStackTrace();
//	}
//	return prepay_id;
//  }
  /**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 * @return
 * @throws JDOMException
	 * @throws IOException
	 */
	public static SortedMap<String, String> doXMLParse(String strxml) throws JDOMException, IOException{
		if(null == strxml || "".equals(strxml)) {
			return null;
		}
		
		SortedMap<String, String> sParaTemp = new TreeMap<String, String>();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if(children.isEmpty()) {
				v = e.getTextNormalize();//new String((e.getTextNormalize()).getBytes(),"UTF-8");
			} else {
				v = getChildrenText(children);
			}
			
			sParaTemp.put(k, v);
		}
		
		//关闭流
		in.close();
		
		return sParaTemp;
	}
	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()) {
			Iterator it = children.iterator();
			while(it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if(!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		
		return sb.toString();
	}
  public static InputStream String2Inputstream(String str) throws UnsupportedEncodingException {
	  	String json = new String(str.getBytes("ISO8859-1"),"UTF-8");
		return new ByteArrayInputStream(str.getBytes("UTF-8"));
	}
  
}