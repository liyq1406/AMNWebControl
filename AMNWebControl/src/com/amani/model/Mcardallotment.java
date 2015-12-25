package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mcardallotment  implements java.io.Serializable {


	private McardallotmentId id;
	private String callotdate;
	private String callottime;
	private String callotempid;
	private String recevieempid;
	private String callotopationempid;
	
	private String callotopationdate;
	private Integer checkoutflag;
	private String checkoutdate;
    private String checkoutemp;
   
	private String cappbillid;
	private String cappcompid;
    private String callotwareid;
    private Integer invalid;
    private String bcallotcompid;
    private String bcallotbillid;
	private String callotempText;
    private String recevieempText;
    private String cappcompidText;
	public McardallotmentId getId() {
		return id;
	}
	public void setId(McardallotmentId id) {
		this.id = id;
	}
	public String getCallotdate() {
		return callotdate;
	}
	public void setCallotdate(String callotdate) {
		this.callotdate = callotdate;
	}
	public String getCallottime() {
		return callottime;
	}
	public void setCallottime(String callottime) {
		this.callottime = callottime;
	}
	public String getCallotempid() {
		return callotempid;
	}
	public void setCallotempid(String callotempid) {
		this.callotempid = callotempid;
	}
	public String getCallotopationempid() {
		return callotopationempid;
	}
	public void setCallotopationempid(String callotopationempid) {
		this.callotopationempid = callotopationempid;
	}
	public String getCallotopationdate() {
		return callotopationdate;
	}
	public void setCallotopationdate(String callotopationdate) {
		this.callotopationdate = callotopationdate;
	}
	public Integer getCheckoutflag() {
		return checkoutflag;
	}
	public void setCheckoutflag(Integer checkoutflag) {
		this.checkoutflag = checkoutflag;
	}
	public String getCheckoutdate() {
		return checkoutdate;
	}
	public void setCheckoutdate(String checkoutdate) {
		this.checkoutdate = checkoutdate;
	}
	public String getCheckoutemp() {
		return checkoutemp;
	}
	public void setCheckoutemp(String checkoutemp) {
		this.checkoutemp = checkoutemp;
	}
	public String getCappbillid() {
		return cappbillid;
	}
	public void setCappbillid(String cappbillid) {
		this.cappbillid = cappbillid;
	}
	public String getCappcompid() {
		return cappcompid;
	}
	public void setCappcompid(String cappcompid) {
		this.cappcompid = cappcompid;
	}
	public String getCallotwareid() {
		return callotwareid;
	}
	public void setCallotwareid(String callotwareid) {
		this.callotwareid = callotwareid;
	}
	public Integer getInvalid() {
		return invalid;
	}
	public void setInvalid(Integer invalid) {
		this.invalid = invalid;
	}
	public String getBcallotcompid() {
		return bcallotcompid;
	}
	public void setBcallotcompid(String bcallotcompid) {
		this.bcallotcompid = bcallotcompid;
	}
	public String getBcallotbillid() {
		return bcallotbillid;
	}
	public void setBcallotbillid(String bcallotbillid) {
		this.bcallotbillid = bcallotbillid;
	}
	
	public String getCallotempText() {
		return callotempText;
	}
	public void setCallotempText(String callotempText) {
		this.callotempText = callotempText;
	}
	public String getCappcompidText() {
		return cappcompidText;
	}
	public void setCappcompidText(String cappcompidText) {
		this.cappcompidText = cappcompidText;
	}
	public String getRecevieempid() {
		return recevieempid;
	}
	public void setRecevieempid(String recevieempid) {
		this.recevieempid = recevieempid;
	}
	public String getRecevieempText() {
		return recevieempText;
	}
	public void setRecevieempText(String recevieempText) {
		this.recevieempText = recevieempText;
	}
	
}