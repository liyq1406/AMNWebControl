package com.amani.model;

public class BlackList {
	private String id;//唯一编号
	private String mobilephone; //手机号码
	private String acceptdate;//接受日期
	private String content;//内容
	private String operdate;//接受日期
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getAcceptdate() {
		return acceptdate;
	}
	public void setAcceptdate(String acceptdate) {
		this.acceptdate = acceptdate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOperdate() {
		return operdate;
	}
	public void setOperdate(String operdate) {
		this.operdate = operdate;
	}
}
