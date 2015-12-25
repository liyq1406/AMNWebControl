package com.amani.bean;

public class GbaBean {

	private String strCompId;		//门店
	private String cardmonney;		//卡余额
	private String cardtreatment;	//卡内疗程
	private String dataactivity;	//活跃期
	private String memberNo;		//卡号
	private String cardClass;		//卡类型
	private String menberName;		//姓名
	private String showmenberName;		//姓名
	private String phone;			//手机号码
	private String showphone;			//手机号码
	private String showmemberNo;		//卡号
	public String getShowmemberNo() {
		return showmemberNo;
	}
	public void setShowmemberNo(String showmemberNo) {
		this.showmemberNo = showmemberNo;
	}
	public String getShowphone() {
		return showphone;
	}
	public void setShowphone(String showphone) {
		this.showphone = showphone;
	}
	private String birthday;
	private String cardNo;
	
	public String getShowmenberName() {
		return showmenberName;
	}
	public void setShowmenberName(String showmenberName) {
		this.showmenberName = showmenberName;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public GbaBean(){}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
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
	public String getDataactivity() {
		return dataactivity;
	}
	public void setDataactivity(String dataactivity) {
		this.dataactivity = dataactivity;
	}
	public String getCardClass() {
		return cardClass;
	}
	public void setCardClass(String cardClass) {
		this.cardClass = cardClass;
	}
	public String getMenberName() {
		return menberName;
	}
	public void setMenberName(String menberName) {
		this.menberName = menberName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public GbaBean(String strCompId, String cardmonney, String cardtreatment,
			String dataactivity, String memberNo, String cardClass,
			String menberName, String phone, String birthday) {
		super();
		this.strCompId = strCompId;
		this.cardmonney = cardmonney;
		this.cardtreatment = cardtreatment;
		this.dataactivity = dataactivity;
		this.memberNo = memberNo;
		this.cardClass = cardClass;
		this.menberName = menberName;
		this.phone = phone;
		this.birthday = birthday;
	}
	
}
