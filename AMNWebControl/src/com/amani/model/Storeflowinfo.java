package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Storeflowinfo  implements java.io.Serializable {


	private StoreflowinfoId id;
	private Integer appflowtype;
	private String appflowcode;
	private String appflowstore;
	private String appflowvalue;
	private String startdate; 
    private String enddate; 
    private String appflowreason; 
    private Integer appflowstate;
    private String appflowconfirmempid; 
    private String appflowconfirmdate; 
    private String appflowcheckempid; 
    private String appflowcheckdate; 
    private String appflowempname; 
    private String appflowstateText;
    private String bcompid;
    private String bbillid;
    private String bcompname;
	private String appflowname;
	private String appflowstorename;
	private Integer invalid;
	public StoreflowinfoId getId() {
		return id;
	}
	public void setId(StoreflowinfoId id) {
		this.id = id;
	}
	public Integer getAppflowtype() {
		return appflowtype;
	}
	public void setAppflowtype(Integer appflowtype) {
		this.appflowtype = appflowtype;
	}
	public String getAppflowcode() {
		return appflowcode;
	}
	public void setAppflowcode(String appflowcode) {
		this.appflowcode = appflowcode;
	}
	public String getAppflowstore() {
		return appflowstore;
	}
	public void setAppflowstore(String appflowstore) {
		this.appflowstore = appflowstore;
	}
	
	public String getAppflowvalue() {
		return appflowvalue;
	}
	public void setAppflowvalue(String appflowvalue) {
		this.appflowvalue = appflowvalue;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getAppflowreason() {
		return appflowreason;
	}
	public void setAppflowreason(String appflowreason) {
		this.appflowreason = appflowreason;
	}
	public Integer getAppflowstate() {
		return appflowstate;
	}
	public void setAppflowstate(Integer appflowstate) {
		this.appflowstate = appflowstate;
	}
	public String getAppflowconfirmempid() {
		return appflowconfirmempid;
	}
	public void setAppflowconfirmempid(String appflowconfirmempid) {
		this.appflowconfirmempid = appflowconfirmempid;
	}
	public String getAppflowconfirmdate() {
		return appflowconfirmdate;
	}
	public void setAppflowconfirmdate(String appflowconfirmdate) {
		this.appflowconfirmdate = appflowconfirmdate;
	}
	public String getAppflowcheckempid() {
		return appflowcheckempid;
	}
	public void setAppflowcheckempid(String appflowcheckempid) {
		this.appflowcheckempid = appflowcheckempid;
	}
	public String getAppflowcheckdate() {
		return appflowcheckdate;
	}
	public void setAppflowcheckdate(String appflowcheckdate) {
		this.appflowcheckdate = appflowcheckdate;
	}
	public String getAppflowempname() {
		return appflowempname;
	}
	public void setAppflowempname(String appflowempname) {
		this.appflowempname = appflowempname;
	}
	public String getAppflowstateText() {
		return appflowstateText;
	}
	public void setAppflowstateText(String appflowstateText) {
		this.appflowstateText = appflowstateText;
	}
	public String getBcompid() {
		return bcompid;
	}
	public void setBcompid(String bcompid) {
		this.bcompid = bcompid;
	}
	public String getBbillid() {
		return bbillid;
	}
	public void setBbillid(String bbillid) {
		this.bbillid = bbillid;
	}
	public String getBcompname() {
		return bcompname;
	}
	public void setBcompname(String bcompname) {
		this.bcompname = bcompname;
	}
	public String getAppflowname() {
		return appflowname;
	}
	public void setAppflowname(String appflowname) {
		this.appflowname = appflowname;
	}
	public String getAppflowstorename() {
		return appflowstorename;
	}
	public void setAppflowstorename(String appflowstorename) {
		this.appflowstorename = appflowstorename;
	}
	public Integer getInvalid() {
		return invalid;
	}
	public void setInvalid(Integer invalid) {
		this.invalid = invalid;
	}
}