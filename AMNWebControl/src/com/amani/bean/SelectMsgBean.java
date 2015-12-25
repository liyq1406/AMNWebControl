package com.amani.bean;

public class SelectMsgBean {

	private String strCompId;		//门店
	private String cardmonney;		//卡余额
	private String cardtreatment;	//卡内疗程
	private String birthday;		//生日
	private String dataactivity;	//活跃期
	private String name;			//姓名
	private String mobilphone;		//手机号码
	private String cardId;			//卡号
	private String cardtype;		//卡类型
	private String cardmonneys;
	
	public String getCardmonneys() {
		return cardmonneys;
	}
	public void setCardmonneys(String cardmonneys) {
		this.cardmonneys = cardmonneys;
	}
	public String getStrCompId() {
		return strCompId;
	}
	public void setStrCompId(String strCompId) {
		this.strCompId = strCompId;
	}
	public String getCardmonney() {
		return cardmonney;
	}
	public void setCardmonney(String cardmonney) {
		this.cardmonney = cardmonney;
	}
	public String getCardtreatment() {
		return cardtreatment;
	}
	public void setCardtreatment(String cardtreatment) {
		this.cardtreatment = cardtreatment;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getDataactivity() {
		return dataactivity;
	}
	public void setDataactivity(String dataactivity) {
		this.dataactivity = dataactivity;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobilphone() {
		return mobilphone;
	}
	public void setMobilphone(String mobilphone) {
		this.mobilphone = mobilphone;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	public SelectMsgBean(){}
}
