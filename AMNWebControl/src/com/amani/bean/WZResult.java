package com.amani.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WZResult implements Serializable{
	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	private String retCode;
    private String message;
    private List<WZGoods> productDetailInfoList = new ArrayList<WZGoods>();
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<WZGoods> getProductDetailInfoList() {
		return productDetailInfoList;
	}
	public void setProductDetailInfoList(List<WZGoods> productDetailInfoList) {
		this.productDetailInfoList = productDetailInfoList;
	}
}
