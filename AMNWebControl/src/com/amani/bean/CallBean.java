package com.amani.bean;

public class CallBean {
//http://crm.server.com/custom.jsp?call_no=10000&called_no=38618383&agent_num=9501&offer_time=2011_12_01_11_22_00
	private String call_no;		//表示来电主叫号码
	private String called_no;	//表示来电被叫号码    即：客户拨打哪个号码进入呼叫中心系统的。
	private String agent_num;	//表示振铃坐席
	private String offer_time;	//表示来电时间
	public String getCall_no() {
		return call_no;
	}
	public void setCall_no(String callNo) {
		call_no = callNo;
	}
	public String getCalled_no() {
		return called_no;
	}
	public void setCalled_no(String calledNo) {
		called_no = calledNo;
	}
	public String getAgent_num() {
		return agent_num;
	}
	public void setAgent_num(String agentNum) {
		agent_num = agentNum;
	}
	public String getOffer_time() {
		return offer_time;
	}
	public void setOffer_time(String offerTime) {
		offer_time = offerTime;
	}
	
}
