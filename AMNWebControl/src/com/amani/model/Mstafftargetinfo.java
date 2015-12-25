package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mstafftargetinfo  implements java.io.Serializable {


	private MstafftargetinfoId id;
	private String bentrycompid;
	private String bentrybillid;
	private String handcompid;	
	private String handcompname;	
	private String handstaffid;
	private String handstaffinid;
	private String handstaffname;
	private BigDecimal targeamt;
	private Integer targeflag;
	private Integer  targemode;
	private Integer  targeyejitype;
	private Integer conditionnum;
    private Integer  billflag;	
    private Integer  invalid;
	private String operationer;
	private String operationdate;
	private String startdate;
	private String enddate;
	private String subsidycondition;
	private String subsidyconditiontext;
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

	public BigDecimal getTargeamt() {
		return targeamt;
	}
	public void setTargeamt(BigDecimal targeamt) {
		this.targeamt = targeamt;
	}
	public Integer getTargeflag() {
		return targeflag;
	}
	public void setTargeflag(Integer targeflag) {
		this.targeflag = targeflag;
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
	public MstafftargetinfoId getId() {
		return id;
	}
	public void setId(MstafftargetinfoId id) {
		this.id = id;
	}
	public Integer getTargemode() {
		return targemode;
	}
	public void setTargemode(Integer targemode) {
		this.targemode = targemode;
	}
	public Integer getTargeyejitype() {
		return targeyejitype;
	}
	public void setTargeyejitype(Integer targeyejitype) {
		this.targeyejitype = targeyejitype;
	}
	
}