package com.amani.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jdom.JDOMException;
import org.springframework.stereotype.Service;

import com.alipay.config.AlipayConfig;
import com.alipay.util.UtilDate;
import com.amani.model.Consumepayment;
import com.amani.tools.CommonTool;
import com.utils.GetWxOrderno;
import com.utils.MD5Util;
import com.utils.RequestHandler;
import com.utils.http.HttpClientConnectionManager;

/**
 * 微信支付服务
 */
@Service
public class WechatPayService extends AMN_ModuleService{
	/**系统常量**/
	public static final String SHCD_APPID="wx580d7f9293940740";
	public static final String SHCD_MCHID="1243988302";
	public static final String SHCD_MCHIDFILE="1243988302.p12";
	public static final String SHCD_WEKEY="dh38571lcjyhghdx0981mhalzka1kdjd";
	public static final String WECHAT_TITLE="微信订单号  ";
	
	/**微信支付的时候，下单函数
	 * auth_code:扫码客户条形码
	 * totalAmt：支付金额
	 * @throws Exception 
	**/
	public Map<String,String> requestWebChatPaypalBill(String csbillid, String auth_code, BigDecimal totalAmt, String compid, String compname) throws Exception{
		Map<String,String> returnValue = new HashMap<String, String>();
		if(auth_code.equalsIgnoreCase("")==true){
			returnValue.put("state", "获取条形码扫描为空，不可以操作!");
			return returnValue;
		}
		/**商户号，申请成功后微信分配给商户的**/
		String sub_mch_id = "1259687401";
		/**业务系统中商户订单号**/
		String outTradeNo =UtilDate.getOrderNum()+getRandomLenght(10);
		returnValue.put("outTradeNo", outTradeNo);
		//System.out.println("WechatOutTradeNo："+ outTradeNo);
		/**随机数**/
	 	String radom= MD5Util.MD5Encode(getRandomLenght(10), AlipayConfig.input_charset);
		//获得本机IP
		InetAddress addr = InetAddress.getLocalHost();
	 	String ip=addr.getHostAddress().toString();
		/**参数分装到map中**/
		SortedMap<String, String> sParaTemp = new TreeMap<String, String>();
	 	sParaTemp.put("appid", SHCD_APPID);
        sParaTemp.put("mch_id", SHCD_MCHID);
        sParaTemp.put("sub_mch_id", sub_mch_id);
        sParaTemp.put("nonce_str", radom);
        sParaTemp.put("out_trade_no",outTradeNo);//微信消费金额单位为：分，所以需要乘以100
		sParaTemp.put("total_fee", String.valueOf(totalAmt.multiply(new BigDecimal(100)).intValue()));
		sParaTemp.put("spbill_create_ip", ip);
		sParaTemp.put("auth_code", auth_code);
		sParaTemp.put("body", ("阿玛尼 "+ compname +" 消费"));
        /**进行签名*/
		String sign = RequestHandler.createSignKey(sParaTemp,SHCD_WEKEY);
        String xml="<xml>"+
				"<appid>"+SHCD_APPID+"</appid>"+
				"<mch_id>"+SHCD_MCHID+"</mch_id>"+
				"<sub_mch_id>"+sub_mch_id+"</sub_mch_id>"+
				"<nonce_str>"+radom+"</nonce_str>"+
				"<sign>"+sign+"</sign>"+
				"<body><![CDATA["+sParaTemp.get("body")+"]]></body>"+
				"<out_trade_no>"+outTradeNo+"</out_trade_no>"+
				"<total_fee>"+String.valueOf(totalAmt.multiply(new BigDecimal(100)).intValue())+"</total_fee>"+
				"<spbill_create_ip>"+ip+"</spbill_create_ip>"+
				"<auth_code>"+auth_code+"</auth_code>"+
				"</xml>";
		/**向微信发起支付请求*/
        Map map = new GetWxOrderno().getPayNoMap("https://api.mch.weixin.qq.com/pay/micropay", xml);
		/**返回支付结果*/
        if(map!=null && (map.get("return_code")).toString().equalsIgnoreCase("SUCCESS")){
			if((map.get("result_code")).toString().equalsIgnoreCase("SUCCESS")){
				/***微信支付成功，可以进行返回操作的，因为微信支付的时候如果小于300元的话，是不需要输入密码的，
				这个时候是直接扣客户微信的钱，*/
				returnValue.put("state", "OK");
			}else {
				if((map.get("err_code")).toString().equalsIgnoreCase("USERPAYING")){
					/***等待客户输入微信支付的密码，这个时候业务系统就需要轮询微信支付订单状态了***/
					returnValue.put("state", "WAITING");
				}else{
					returnValue.put("state", "支付失败！请保留微信订单号 "+ outTradeNo +" 联系总部财务，原因："+(map.get("err_code_des")).toString());
				}
			}
        }else{
        	returnValue.put("state", "支付失败！请保留微信订单号 "+ outTradeNo +" 联系总部财务，原因："+(map.get("return_msg")).toString());
        }
        savePayment(compid, csbillid, outTradeNo, totalAmt, 1);
        return returnValue;
	}
	
	/**微信退款
	outTradeNo:向微信提交支付的时候，订单号
	totalAmt：退款金额
	**/
	public String webChatRefundPaypalBillStatus(String compid, String csbillid, String outTradeNo, BigDecimal totalAmt) throws Exception{
		/**微信退款的时候，必须是SSL提交的，会有一个密钥文件的*/
		KeyStore keyStore  = KeyStore.getInstance("PKCS12");
		/**密钥文件路径*/
        FileInputStream instream = new FileInputStream(new File("C:"+File.separator+"wechatkey"+File.separator+SHCD_MCHIDFILE));
        try {
            keyStore.load(instream, SHCD_MCHID.toCharArray());
        } finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore,  SHCD_MCHID.toCharArray()).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
				
		String sub_mch_id="1259687401";
        try {
        	String radom= MD5Util.MD5Encode(getRandomLenght(10), AlipayConfig.input_charset);
		 	SortedMap<String, String> sParaTemp = new TreeMap<String, String>();
		 	sParaTemp.put("appid", SHCD_APPID);
	        sParaTemp.put("mch_id", SHCD_MCHID);
	        sParaTemp.put("sub_mch_id", sub_mch_id);
	        sParaTemp.put("nonce_str", radom);
	        sParaTemp.put("out_trade_no",outTradeNo);
	        sParaTemp.put("total_fee", String.valueOf(totalAmt.multiply(new BigDecimal(100)).intValue()));
	        sParaTemp.put("refund_fee", String.valueOf(totalAmt.multiply(new BigDecimal(100)).intValue()));
	        sParaTemp.put("out_refund_no",outTradeNo);
	        sParaTemp.put("op_user_id",sub_mch_id);
	        
	        String sign = RequestHandler.createSignKey(sParaTemp,SHCD_WEKEY);
			String xmlParam="<xml>"+
					"<appid>"+SHCD_APPID+"</appid>"+
					"<mch_id>"+SHCD_MCHID+"</mch_id>"+
					"<sub_mch_id>"+sub_mch_id+"</sub_mch_id>"+
					"<nonce_str>"+radom+"</nonce_str>"+
					"<sign>"+sign+"</sign>"+
					"<out_trade_no>"+outTradeNo+"</out_trade_no>"+
					"<out_refund_no>"+outTradeNo+"</out_refund_no>"+
					"<op_user_id>"+sub_mch_id+"</op_user_id>"+
					"<total_fee>"+String.valueOf(totalAmt.multiply(new BigDecimal(100)).intValue())+"</total_fee>"+
					"<refund_fee>"+String.valueOf(totalAmt.multiply(new BigDecimal(100)).intValue())+"</refund_fee>"+
					"</xml>";
			HttpPost httpost= HttpClientConnectionManager.getPostMethod("https://api.mch.weixin.qq.com/secapi/pay/refund");
        	try {
        		httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
 	       		CloseableHttpResponse response = httpclient.execute(httpost);
 	       		String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
 	       		Map map = new GetWxOrderno().doXMLParse(jsonStr);
 	       		if(map!=null && (map.get("return_code")).toString().equalsIgnoreCase("SUCCESS")){
 	       			if((map.get("result_code")).toString().equalsIgnoreCase("SUCCESS")){
 	       				savePayment(compid, csbillid, outTradeNo, totalAmt, 3);
 	       				return "OK";
 	       			}else{
 	       				savePayment(compid, csbillid, outTradeNo, totalAmt, 3);
 	       				return "退款失败！请保留微信订单号 "+ outTradeNo +" 联系总部财务，原因："+(map.get("err_code_des")).toString();
 	       			}
 	       		}else{
 	       			savePayment(compid, csbillid, outTradeNo, totalAmt, 3);
 	       			return "退款失败！请保留微信订单号 "+ outTradeNo +" 联系总部财务，原因："+(map.get("return_msg")).toString();
 	       		}
 	       	 
            } catch (Exception e) {
       			e.printStackTrace();
            }
        } finally {
            httpclient.close();
        }
        savePayment(compid, csbillid, outTradeNo, totalAmt, 1);
        throw new Exception(WECHAT_TITLE+outTradeNo+" 单据退款失败！");
	}
	
	/**
	 * 微信查询支付单据状态
	 * @param outTradeNo
	 * @return
	 * @throws Exception
	 */
	public String queryWebChatBillStatusReturnString(String outTradeNo) throws Exception{
		/***验证是否有微信信息*/
		String sub_mch_id="1259687401";
	 	String radom= MD5Util.MD5Encode(getRandomLenght(10), AlipayConfig.input_charset);
		SortedMap<String, String> sParaTemp = new TreeMap<String, String>();
	 	sParaTemp.put("appid", SHCD_APPID);
        sParaTemp.put("mch_id", SHCD_MCHID);
        sParaTemp.put("sub_mch_id", sub_mch_id);
        sParaTemp.put("nonce_str", radom);
        sParaTemp.put("out_trade_no",outTradeNo);
        String sign = RequestHandler.createSignKey(sParaTemp,SHCD_WEKEY);
        String xml="<xml>"+
				"<appid>"+SHCD_APPID+"</appid>"+
				"<mch_id>"+SHCD_MCHID+"</mch_id>"+
				"<sub_mch_id>"+sub_mch_id+"</sub_mch_id>"+
				"<nonce_str>"+radom+"</nonce_str>"+
				"<sign>"+sign+"</sign>"+
				"<out_trade_no>"+outTradeNo+"</out_trade_no>"+
				"</xml>";
        Map map = new GetWxOrderno().getPayNoMap("https://api.mch.weixin.qq.com/pay/orderquery", xml);
        if(map!=null && (map.get("return_code")).toString().equalsIgnoreCase("SUCCESS")){
			if((map.get("result_code")).toString().equalsIgnoreCase("SUCCESS")){
				if((map.get("trade_state")).toString().equalsIgnoreCase("SUCCESS")){
					return "OK";
				}else if((map.get("trade_state")).toString().equalsIgnoreCase("USERPAYING")){
					return "WAITING";
				}else if((map.get("trade_state")).toString().equalsIgnoreCase("REFUND")){
					return "单据转入退款！请保留微信订单号 "+ outTradeNo +" 联系总部财务。";
				}else if((map.get("trade_state")).toString().equalsIgnoreCase("NOTPAY")){
					return "单据未支付！请保留微信订单号 "+ outTradeNo +" 联系总部财务。";
				}else if((map.get("trade_state")).toString().equalsIgnoreCase("CLOSED")){
					return "单据已关闭！请保留微信订单号 "+ outTradeNo +" 联系总部财务。";
				}else if((map.get("trade_state")).toString().equalsIgnoreCase("REVOKED")){
					return "单据已撤销！请保留微信订单号 "+ outTradeNo +" 联系总部财务。";
				}else if((map.get("trade_state")).toString().equalsIgnoreCase("NOPAY")){
					return "单据未支付(确认支付超时)！请保留微信订单号 "+ outTradeNo +" 联系总部财务。";
				}else if((map.get("trade_state")).toString().equalsIgnoreCase("PAYERROR")){
					return "单据支付失败(其他原因，如银行返回失败)！请保留微信订单号 "+ outTradeNo +" 联系总部财务。";
				}
			}else{
				return "单据查询失败！请保留微信订单号 "+ outTradeNo +" 联系总部财务，原因："+(map.get("err_code_des")).toString();
			}
		}else{
			return "单据查询失败！请保留微信订单号 "+ outTradeNo +" 联系总部财务，原因："+(map.get("return_msg")).toString();
		}
        throw new Exception(WECHAT_TITLE+outTradeNo+" 支付单据状态查询失败！");
	}
	
	/**
	 * 微信撤销单据
	 * @param outTradeNo
	 * @return
	 */
	public String webChatReverse(String compid, String csbillid, String outTradeNo, BigDecimal totalAmt) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException, UnrecoverableKeyException, JDOMException {
		String sub_mch_id="1259687401";
		KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File("C:"+File.separator+"wechatkey"+File.separator+SHCD_MCHIDFILE));
        try {
            keyStore.load(instream, SHCD_MCHID.toCharArray());
        } finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, SHCD_MCHID.toCharArray()).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        try {
        	String radom= MD5Util.MD5Encode(getRandomLenght(10), AlipayConfig.input_charset);
		 	SortedMap<String, String> sParaTemp = new TreeMap<String, String>();
		 	sParaTemp.put("appid",SHCD_APPID);
	        sParaTemp.put("mch_id", SHCD_MCHID);
	        sParaTemp.put("sub_mch_id", sub_mch_id);
	        sParaTemp.put("nonce_str", radom);
	        sParaTemp.put("out_trade_no",outTradeNo);
	        String sign = RequestHandler.createSignKey(sParaTemp,SHCD_WEKEY);
			String xmlParam="<xml>"+
					"<appid>"+SHCD_APPID+"</appid>"+
					"<mch_id>"+SHCD_MCHID+"</mch_id>"+
					"<sub_mch_id>"+sub_mch_id+"</sub_mch_id>"+
					"<nonce_str>"+radom+"</nonce_str>"+
					"<sign>"+sign+"</sign>"+
					"<out_trade_no>"+outTradeNo+"</out_trade_no>"+
					"</xml>";
        	HttpPost httpost= HttpClientConnectionManager.getPostMethod("https://api.mch.weixin.qq.com/secapi/pay/reverse");
    		httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
       		CloseableHttpResponse response = httpclient.execute(httpost);
       		String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
       		Map map = new GetWxOrderno().doXMLParse(jsonStr);
       		if(map!=null && (map.get("return_code")).toString().equalsIgnoreCase("SUCCESS")){
       			if((map.get("result_code")).toString().equalsIgnoreCase("SUCCESS")){
       				savePayment(compid, csbillid, outTradeNo, totalAmt, 2);
       				return "OK";
       			}else{
       				savePayment(compid, csbillid, outTradeNo, totalAmt, 2);
       				return "撤销失败！请保留微信订单号 "+ outTradeNo +" 联系总部财务，原因："+(map.get("err_code_des")).toString();
       			}
       		}else{
       			savePayment(compid, csbillid, outTradeNo, totalAmt, 2);
       			return "撤销失败！请保留微信订单号 "+ outTradeNo +" 联系总部财务，原因："+(map.get("return_msg")).toString();
       		}
        } finally {
            httpclient.close();
        }
	}
	
	public static String getRandomLenght(Integer len){
		 java.util.Random r=new java.util.Random(); 
		 String value = new Integer(Math.abs(r.nextInt())).toString();
		 if(len<value.length()){
			 return value.substring(0, len);
		 }else{
			 return value;
		 }
	}
	
	/**
	 * 保存交易记录
	 * @param compid
	 * @param csbillid
	 * @param outTradeNo
	 * @param totalAmt
	 * @param paytype
	 */
	public void savePayment(String compid, String csbillid, String outTradeNo, BigDecimal totalAmt, Integer paytype){
		try{
			Consumepayment payment = new Consumepayment();
			payment.setCscompid(compid);
			payment.setCsbillid(csbillid);
			payment.setScantradeno(outTradeNo);
			payment.setPaydate(CommonTool.getCurrDate());
			payment.setPaytime(CommonTool.getCurrTime());
			payment.setScanpaytype(2);
			payment.setPayamt(totalAmt);
			payment.setPaytype(paytype);
			this.amn_Dao.save(payment);
		}catch(RuntimeException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//String outTradeNo="201507290554161773340407";
		String outTradeNo="201508041141401954568762";
		WechatPayService ws = new WechatPayService();
		try {
			/*ws.requestWebChatPaypalBill("130131892857683416" +
					"", new BigDecimal("0.01"));*/
			String msg = ws.queryWebChatBillStatusReturnString(outTradeNo);
			System.out.println("msg "+ msg);
			//String res = ws.webChatRefundPaypalBillStatus(outTradeNo, new BigDecimal("100"));
			//System.out.println("res "+ res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected boolean deleteDetail(Object curMaster) {
		return false;
	}

	@Override
	protected boolean deleteMaster(Object curMaster) {
		return false;
	}

	@Override
	protected boolean postMaster(Object curMaster) {
		return false;
	}

	@Override
	protected boolean postDetail(Object details) {
		return false;
	}

	@Override
	public List loadMasterDataSet(int pageSize, int startRow) {
		return null;
	}
}
