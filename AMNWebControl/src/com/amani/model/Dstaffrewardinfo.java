package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dstaffrewardinfo  implements java.io.Serializable {


	private DstaffrewardinfoId id;
	private String bentrycompid;
	private String bentrybillid;
	private Double bentryseqno;
	private String handcompid;	
	private String handcompname;
	private String handstaffid;
	private String handstaffname;
	private String handstaffinid;
	private String entryreason;	
	private String entrydate;	
	private String entrytype;
	private Integer  billflag;	
	private BigDecimal rewardamt;
	private String operationer;
	private String operationdate;
    private Integer entryflag;
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
	public DstaffrewardinfoId getId() {
		return id;
	}
	public void setId(DstaffrewardinfoId id) {
		this.id = id;
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
	public String getEntryreason() {
		return entryreason;
	}
	public void setEntryreason(String entryreason) {
		this.entryreason = entryreason;
	}
	public String getEntrydate() {
		return entrydate;
	}
	public void setEntrydate(String entrydate) {
		this.entrydate = entrydate;
	}
	public String getEntrytype() {
		return entrytype;
	}
	public void setEntrytype(String entrytype) {
		this.entrytype = entrytype;
	}
	public BigDecimal getRewardamt() {
		return rewardamt;
	}
	public void setRewardamt(BigDecimal rewardamt) {
		this.rewardamt = rewardamt;
	}
	public String getHandstaffname() {
		return handstaffname;
	}
	public void setHandstaffname(String handstaffname) {
		this.handstaffname = handstaffname;
	}
	public String getHandcompid() {
		return handcompid;
	}
	public void setHandcompid(String handcompid) {
		this.handcompid = handcompid;
	}
	public String getHandcompname() {
		return handcompname;
	}
	public void setHandcompname(String handcompname) {
		this.handcompname = handcompname;
	}
	public Integer getBillflag() {
		return billflag;
	}
	public void setBillflag(Integer billflag) {
		this.billflag = billflag;
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
	public Double getBentryseqno() {
		return bentryseqno;
	}
	public void setBentryseqno(Double bentryseqno) {
		this.bentryseqno = bentryseqno;
	}
	
}