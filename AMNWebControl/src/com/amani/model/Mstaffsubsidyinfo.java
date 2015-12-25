package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mstaffsubsidyinfo  implements java.io.Serializable {


	private MstaffsubsidyinfoId id;
	private String bentrycompid;
	private String bentrybillid;
	private String handcompid;	
	private String handcompname;	
	private String handstaffid;
	private String handstaffinid;
	private String handstaffname;
	private BigDecimal subsidyamt;
	private Integer subsidyflag;
	private Integer conditionnum;
    private Integer  billflag;	
    private Integer  invalid;
	private String operationer;
	private String operationdate;
	private String startdate;
	private String enddate;
	private String subsidycondition;
	private String subsidyconditiontext;
	private String appstaffname;
	private String checkstaffname;
	public String getAppstaffname() {
		return appstaffname;
	}
	public void setAppstaffname(String appstaffname) {
		this.appstaffname = appstaffname;
	}
	public String getCheckstaffname() {
		return checkstaffname;
	}
	public void setCheckstaffname(String checkstaffname) {
		this.checkstaffname = checkstaffname;
	}
	public String getSubsidycondition() {
		return subsidycondition;
	}
	public void setSubsidycondition(String subsidycondition) {
		this.subsidycondition = subsidycondition;
	}
	public String getSubsidyconditiontext() {
		return subsidyconditiontext;
	}
	public void setSubsidyconditiontext(String subsidyconditiontext) {
		this.subsidyconditiontext = subsidyconditiontext;
	}
	public String getOperationer() {
		return operationer;
	}
	public void setOperationer(String operationer) {
		this.operationer = operationer;
	}
	public String getOperationdate() {
		return operationdate;
	}
	public void setOperationdate(String operationdate) {
		this.operationdate = operationdate;
	}

	public String getHandcompid() {
		return handcompid;
	}
	public void setHandcompid(String handcompid) {
		this.handcompid = handcompid;
	}
	
	public Integer getInvalid() {
		return invalid;
	}
	public void setInvalid(Integer invalid) {
		this.invalid = invalid;
	}
	public String getBentrycompid() {
		return bentrycompid;
	}
	public void setBentrycompid(String bentrycompid) {
		this.bentrycompid = bentrycompid;
	}
	public String getBentrybillid() {
		return bentrybillid;
	}
	public void setBentrybillid(String bentrybillid) {
		this.bentrybillid = bentrybillid;
	}
	
	public Integer getBillflag() {
		return billflag;
	}
	public void setBillflag(Integer billflag) {
		this.billflag = billflag;
	}
	
	public MstaffsubsidyinfoId getId() {
		return id;
	}
	public void setId(MstaffsubsidyinfoId id) {
		this.id = id;
	}
	public String getHandcompname() {
		return handcompname;
	}
	public void setHandcompname(String handcompname) {
		this.handcompname = handcompname;
	}
	public String getHandstaffid() {
		return handstaffid;
	}
	public void setHandstaffid(String handstaffid) {
		this.handstaffid = handstaffid;
	}
	public String getHandstaffinid() {
		return handstaffinid;
	}
	public void setHandstaffinid(String handstaffinid) {
		this.handstaffinid = handstaffinid;
	}
	public String getHandstaffname() {
		return handstaffname;
	}
	public void setHandstaffname(String handstaffname) {
		this.handstaffname = handstaffname;
	}
	public BigDecimal getSubsidyamt() {
		return subsidyamt;
	}
	public void setSubsidyamt(BigDecimal subsidyamt) {
		this.subsidyamt = subsidyamt;
	}
	public Integer getSubsidyflag() {
		return subsidyflag;
	}
	public void setSubsidyflag(Integer subsidyflag) {
		this.subsidyflag = subsidyflag;
	}
	public Integer getConditionnum() {
		return conditionnum;
	}
	public void setConditionnum(Integer conditionnum) {
		this.conditionnum = conditionnum;
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
	
}