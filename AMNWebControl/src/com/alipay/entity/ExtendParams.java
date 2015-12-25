package com.alipay.entity;

import java.io.Serializable;


/**
 * @author 鲁业军
 * @version 创度软件科技(上海)有限公司
 * 创建时间：2014-10-10 下午3:36:13
 */
public class ExtendParams implements Serializable {

	/**
	 * 支付宝当面付款中，公用业务扩展信息
	 */
	private static final long serialVersionUID = -8189074196097501465L;

	/***代理人号*/
	private String AGENT_ID;
	/**门店类型。0：支付宝门店，指支付 1：商户门店， 指商户自已的门店*/
	private String STORE_TYPE;
	/**门店编号*/
	private String STORE_ID;
	/**终端设备号*/
	private String TERMINAL_ID;
	/**品牌编号*/
	private String SHOP_ID;

	/***代理人号*/
	public String getAGENT_ID() {
		return (AGENT_ID);
	}
	/***代理人号*/
	public void setAGENT_ID(String aGENT_ID) {
		AGENT_ID = aGENT_ID;
	}
	/**门店类型。0：支付宝门店，指支付 1：商户门店， 指商户自已的门店*/
	public String getSTORE_TYPE() {
		return (STORE_TYPE);
	}
	/**门店类型。0：支付宝门店，指支付 1：商户门店， 指商户自已的门店*/
	public void setSTORE_TYPE(String sTORE_TYPE) {
		STORE_TYPE = sTORE_TYPE;
	}
	/**门店编号*/
	public String getSTORE_ID() {
		return (STORE_ID);
	}
	/**门店编号*/
	public void setSTORE_ID(String sTORE_ID) {
		STORE_ID = sTORE_ID;
	}
	public String getTERMINAL_ID() {
		return (TERMINAL_ID);
	}
	public void setTERMINAL_ID(String tERMINAL_ID) {
		TERMINAL_ID = tERMINAL_ID;
	}
	public String getSHOP_ID() {
		return (SHOP_ID);
	}
	public void setSHOP_ID(String sHOP_ID) {
		SHOP_ID = sHOP_ID;
	} 
	
	
}
