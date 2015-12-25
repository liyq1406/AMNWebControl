package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mstaffrewardinfo  implements java.io.Serializable {


	private MstaffrewardinfoId id;
	private String bentrycompid;
	private String bentrybillid;
    private Integer entryflag;
	private String handcompid;	
	private String handcompname;	
    private Integer  billflag;	
    private Integer  invalid;
	private String operationer;
	private String operationdate;

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
	public Integer getEntryflag() {
		return entryflag;
	}
	public void setEntryflag(Integer entryflag) {
		this.entryflag = entryflag;
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
	public MstaffrewardinfoId getId() {
		return id;
	}
	public void setId(MstaffrewardinfoId id) {
		this.id = id;
	}
	public String getHandcompname() {
		return handcompname;
	}
	public void setHandcompname(String handcompname) {
		this.handcompname = handcompname;
	}
	
}