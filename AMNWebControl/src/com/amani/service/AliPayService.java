package com.amani.service;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.alipay.config.AlipayConfig;
import com.alipay.entity.ExtendParams;
import com.alipay.util.AlipaySubmit;
import com.alipay.util.UtilDate;
import com.amani.model.Consumepayment;
import com.amani.tools.CommonTool;
import com.google.gson.Gson;

/**
 * 支付宝服务
 */
@Service
public class AliPayService extends AMN_ModuleService{
	public static final String ALI_TITLE="支付宝订单号  ";
	private Logger logger = Logger.getLogger(this.getClass().getName());
	/***提交订单信息
	 * @throws Exception **/
	public Map<String,String> requestPaypal(String csbillid, String auth_code, BigDecimal totalAmt, String compid, String compname) throws Exception {
		Map<String,String> returnValue = new HashMap<String, String>();
		if(auth_code.equalsIgnoreCase("")==true){
			returnValue.put("state", "获取条形码扫描为空，不可以操作!");
			return returnValue;
		}
		/**业务系统中商户订单号**/
		String outTradeNo =UtilDate.getOrderNum()+getRandomLenght(10);
		returnValue.put("outTradeNo", outTradeNo);
		//System.out.println("AliOutTradeNo："+ outTradeNo);
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "alipay.acquire.createandpay");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("out_trade_no", outTradeNo);
        sParaTemp.put("subject", ("阿玛尼 "+ compname +" 消费"));
        sParaTemp.put("product_code", "BARCODE_PAY_OFFLINE");
		sParaTemp.put("total_fee",(totalAmt).toString());
		sParaTemp.put("seller_id", AlipayConfig.partner);
		sParaTemp.put("dynamic_id_type", "bar_code");
		sParaTemp.put("dynamic_id", auth_code);
		Gson extentGson =new Gson();
		ExtendParams extent = new ExtendParams();
		extent.setAGENT_ID(AlipayConfig.shcdagentid);
		extent.setSTORE_TYPE("1");
		extent.setSTORE_ID("阿玛尼 "+ compid +" 店");
		extent.setSHOP_ID("阿玛尼"+ compname);
		extent.setTERMINAL_ID("阿玛尼 "+ compid +" 店");
		sParaTemp.put("extend_params", extentGson.toJson(extent));
		//建立请求
		try {
			String sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
//		System.out.println("=======================");
//		System.out.println(sParaTemp);
//		System.out.println("=======================");
//		System.out.println("************************");
//		System.out.println(sHtmlText);
//		System.out.println("************************");
			//System.out.println("提交支付宝!");
			//this.getLogger().debug(sHtmlText);
			InputSource in = new InputSource(new StringReader(sHtmlText));  
			in.setEncoding("UTF-8");  
			SAXReader reader = new SAXReader(); 
			Document doc = reader.read(in); 
			Element root = doc.getRootElement(); 
			if(root.elementText("is_success").equalsIgnoreCase("T")==false){
				returnValue.put("state", "支付失败！请保留支付宝订单号 "+ outTradeNo +" 联系总部财务，原因："+root.elementText("error"));
				return returnValue;
			}
			Element responseElement  =root.element("response");
			List<Element> response = responseElement.elements("alipay");
			Boolean payBool = false;
			for(Element element:response){
				if(element.elementText("result_code").equalsIgnoreCase("FAIL")==true){
					returnValue.put("state", "支付失败！请保留支付宝订单号 "+ outTradeNo +" 联系总部财务，原因："+element.elementText("detail_error_des"));
					break;
				}
				if(element.elementText("result_code").equalsIgnoreCase("ORDER_SUCCESS_PAY_SUCCESS")==true 
						|| element.elementText("result_code").equalsIgnoreCase("ORDER_SUCCESS_PAY_FAIL")==true
						|| element.elementText("result_code").equalsIgnoreCase("ORDER_SUCCESS_PAY_INPROCESS")==true){
					payBool = true;
				}else{
					returnValue.put("state", "支付失败！请保留支付宝订单号 "+ outTradeNo +" 联系总部财务，原因："+element.elementText("detail_error_des"));
					break;
				}
				//String trade_no= element.elementText("trade_no");//支付内部交易编号
			}
			if(payBool==true){
				/**提交支付成功，等他客户输入密码等等信息，**/
				returnValue.put("state", "WAITING");
			}
			savePayment(compid, csbillid, outTradeNo, totalAmt, 1);
		} catch (Exception e) {
			
			logger.error("提交支付宝支付订单  requestPaypal:" + e);
		}
		return returnValue;
	}
	
	/**收银主动的取消订单**/
	public String canclePaypalBillStatus(String compid, String csbillid, String outTradeNo, BigDecimal totalAmt) throws Exception{
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "alipay.acquire.cancel");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("out_trade_no", outTradeNo);
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
	    InputSource in = new InputSource(new StringReader(sHtmlText));  
        in.setEncoding("UTF-8");  
		SAXReader reader = new SAXReader(); 
		Document doc = reader.read(in); 
		Element root = doc.getRootElement(); 
		if(root.elementText("is_success").equalsIgnoreCase("T")==false){
			savePayment(compid, csbillid, outTradeNo, totalAmt, 2);
			return  "撤销失败！请保留支付宝订单号 "+ outTradeNo +" 联系总部财务，原因："+root.elementText("error");
		}
		Element responseElement  =root.element("response");
		List<Element> response = responseElement.elements("alipay");
		for(Element element:response){
			if(element.elementText("result_code").equalsIgnoreCase("FAIL")==true){
				savePayment(compid, csbillid, outTradeNo, totalAmt, 2);
				return "撤销失败！请保留支付宝订单号 "+ outTradeNo +" 联系总部财务，原因："+element.elementText("detail_error_des");
			}
			//System.out.println(element.elementText("out_trade_no"));
			if(element.elementText("result_code").equalsIgnoreCase("SUCCESS")==true){
				savePayment(compid, csbillid, outTradeNo, totalAmt, 2);
				return "OK";
			}
		}
		savePayment(compid, csbillid, outTradeNo, totalAmt, 2);
        throw new Exception(ALI_TITLE+outTradeNo+" 单据撤销失败！");
	}
	
	
	/**查询单据状态**/
	public String queryPaypalBillStatusReturnString(String outTradeNo) throws Exception{
		/**重新赋值 key和partner*/
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "alipay.acquire.query");
		sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("out_trade_no", outTradeNo);
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
		InputSource in = new InputSource(new StringReader(sHtmlText));  
        in.setEncoding("UTF-8");  
		SAXReader reader = new SAXReader(); 
		Document doc = reader.read(in); 
		Element root = doc.getRootElement(); 
		if(root.elementText("is_success").equalsIgnoreCase("T")==false){
			return "订单查询失败！请保留支付宝订单号 "+ outTradeNo +" 联系总部财务，原因："+root.elementText("error");
		}
		Element responseElement  =root.element("response");
		List<Element> response = responseElement.elements("alipay");
		for(Element element:response){
			if(element.elementText("result_code").equalsIgnoreCase("FAIL")==true){
				return "订单查询失败！请保留支付宝订单号 "+ outTradeNo +" 联系总部财务，原因："+element.elementText("detail_error_des");
			}
			//System.out.println(element.elementText("out_trade_no"));
			if(element.elementText("trade_status").equalsIgnoreCase("WAIT_BUYER_PAY")==true){
				return "WAITING";
			}else if(element.elementText("trade_status").equalsIgnoreCase("TRADE_CLOSED")==true){
				return "在指定时间段内未支付时关闭的交易或者在交易完成全额退款成功时关闭的交易！请保留支付宝订单号 "+ outTradeNo +" 联系总部财务。";
			}else if(element.elementText("trade_status").equalsIgnoreCase("TRADE_SUCCESS")==true){
				return "OK";
			}else if(element.elementText("trade_status").equalsIgnoreCase("TRADE_PENDING")==true){
				return "等待卖家收款！请保留支付宝订单号 "+ outTradeNo +" 联系总部财务。";
			}else if(element.elementText("trade_status").equalsIgnoreCase("TRADE_FINISHED")==true){
				return "交易成功且结束！请保留支付宝订单号 "+ outTradeNo +" 联系总部财务。";
			}
		}
		throw new Exception(ALI_TITLE+outTradeNo+" 单据状态查询失败！");
	}
	
	/**支付宝退款
	 * @throws Exception **/
	public String refundPaypalBillStatus(String compid, String csbillid, String outTradeNo, BigDecimal totalAmt) throws Exception {
		/**重新赋值*/
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "alipay.acquire.refund");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("out_trade_no", outTradeNo);
        sParaTemp.put("refund_amount", totalAmt.toString());
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest("", "", sParaTemp);
		InputSource in = new InputSource(new StringReader(sHtmlText));  
        in.setEncoding("UTF-8");
		SAXReader reader = new SAXReader(); 
		Document doc = reader.read(in); 
		Element root = doc.getRootElement(); 
		//System.out.println(root.elementText("is_success"));
		if(root.elementText("is_success").equalsIgnoreCase("T")==false){
			savePayment(compid, csbillid, outTradeNo, totalAmt, 3);
			return "退款失败！请保留支付宝订单号 "+ outTradeNo +" 联系总部财务，原因："+root.elementText("error");
		}
		Element responseElement  =root.element("response");
		List<Element> response = responseElement.elements("alipay");
		for(Element element:response){
			if(element.elementText("result_code").equalsIgnoreCase("FAIL")==true){
				savePayment(compid, csbillid, outTradeNo, totalAmt, 3);
				return "退款失败！请保留支付宝订单号 "+ outTradeNo +" 联系总部财务，原因："+element.elementText("detail_error_des");
			}
			if(element.elementText("result_code").equalsIgnoreCase("SUCCESS")==true){
				savePayment(compid, csbillid, outTradeNo, totalAmt, 3);
				return "OK";
			}
		}
		savePayment(compid, csbillid, outTradeNo, totalAmt, 3);
		throw new Exception(ALI_TITLE+outTradeNo+" 单据退款失败！");
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
			payment.setScanpaytype(1);
			payment.setPayamt(totalAmt);
			payment.setPaytype(paytype);
			this.amn_Dao.save(payment);
		}catch(RuntimeException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		AliPayService aliPayService = new AliPayService();
		try {
			String outTradeNo = "201508041139471712670126";//"201507310022031793262719";//"2015072909134281247075";
			/*aliPayService.requestPaypal("287341795328073360" +
					"", new BigDecimal("0.01"));*/
			System.out.println(aliPayService.queryPaypalBillStatusReturnString(outTradeNo));
			//System.out.println(aliPayService.refundPaypalBillStatus(outTradeNo, new BigDecimal("100")));
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
