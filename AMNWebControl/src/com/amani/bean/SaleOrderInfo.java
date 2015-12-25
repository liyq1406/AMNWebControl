package com.amani.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 望子订单
 */
public class SaleOrderInfo implements Serializable{

	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	
	private String order_number;     //订单编号
	private String order_date;       //订单日期
	private String customer_id;      //客户ID
	private String delivery_address; //收货地址
	private String memo;             //备注
	private List<SaleOrderProduct> product;//订单明细
	
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getDelivery_address() {
		return delivery_address;
	}
	public void setDelivery_address(String delivery_address) {
		this.delivery_address = delivery_address;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public List<SaleOrderProduct> getProduct() {
		return product;
	}
	public void setProduct(List<SaleOrderProduct> product) {
		this.product = product;
	}
}
