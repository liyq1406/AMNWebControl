package com.amani.bean;

import java.math.BigDecimal;

public class StaffWorkDetial {
	private String 			personinid		;//员工内部编号
	private int 			actionid		;//单据类型
	private String 			srvdate			;//日期
	private String 			code			;//项目代码,或是卡号,产品码
	private String 			name			;//-名称
	private String 			payway			;//支付方式
	private BigDecimal 		billamt			;//营业金额
	private BigDecimal 		ccount			;//数量
	private BigDecimal 		cost			;//成本
	private BigDecimal 		staffticheng	;//提成
	private BigDecimal 		staffyeji		;//虚业绩
	private BigDecimal      staffshareyeji	;//分享卡金
	
	private String 			billid			;//单号
	private String 			paycode			;//支付代码
	private String 			compid			;//公司别
	private String 			cardid			;//会员卡号
	private String 			cardtype		;//会员卡类型
	public String getPersoninid() {
		return personinid;
	}
	public void setPersoninid(String personinid) {
		this.personinid = personinid;
	}

	public int getActionid() {
		return actionid;
	}
	public void setActionid(int actionid) {
		this.actionid = actionid;
	}
	public String getSrvdate() {
		return srvdate;
	}
	public void setSrvdate(String srvdate) {
		this.srvdate = srvdate;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPayway() {
		return payway;
	}
	public void setPayway(String payway) {
		this.payway = payway;
	}
	public BigDecimal getBillamt() {
		return billamt;
	}
	public void setBillamt(BigDecimal billamt) {
		this.billamt = billamt;
	}
	public BigDecimal getCcount() {
		return ccount;
	}
	public void setCcount(BigDecimal ccount) {
		this.ccount = ccount;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public BigDecimal getStaffticheng() {
		return staffticheng;
	}
	public void setStaffticheng(BigDecimal staffticheng) {
		this.staffticheng = staffticheng;
	}
	public BigDecimal getStaffyeji() {
		return staffyeji;
	}
	public void setStaffyeji(BigDecimal staffyeji) {
		this.staffyeji = staffyeji;
	}
	public String getBillid() {
		return billid;
	}
	public void setBillid(String billid) {
		this.billid = billid;
	}
	public String getPaycode() {
		return paycode;
	}
	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}
	public String getCompid() {
		return compid;
	}
	public void setCompid(String compid) {
		this.compid = compid;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public BigDecimal getStaffshareyeji() {
		return staffshareyeji;
	}
	public void setStaffshareyeji(BigDecimal staffshareyeji) {
		this.staffshareyeji = staffshareyeji;
	}
}
