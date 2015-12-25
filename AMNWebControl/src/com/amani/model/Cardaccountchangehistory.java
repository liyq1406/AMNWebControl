package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

public class Cardaccountchangehistory {
	private String changecompid;
	private String changecompname;
	private String changecardno;
	private Integer changeaccounttype;
	private Integer changeseqno;
	private Integer changetype;
	private BigDecimal changeamt;
	private String changebilltype;
	private String changebilltypeText;
	private String changebillno;
	private String chagedate;
	private BigDecimal changebeforeamt;
	private BigDecimal lastamt;
	private String changemark;
	public String getChangemark() {
		return changemark;
	}
	public void setChangemark(String changemark) {
		this.changemark = changemark;
	}
	public BigDecimal getLastamt() {
		return lastamt;
	}
	public void setLastamt(BigDecimal lastamt) {
		this.lastamt = lastamt;
	}
	public String getChangecompid() {
		return changecompid;
	}
	public void setChangecompid(String changecompid) {
		this.changecompid = changecompid;
	}
	public String getChangecompname() {
		return changecompname;
	}
	public void setChangecompname(String changecompname) {
		this.changecompname = changecompname;
	}
	public String getChangecardno() {
		return changecardno;
	}
	public void setChangecardno(String changecardno) {
		this.changecardno = changecardno;
	}
	public Integer getChangeaccounttype() {
		return changeaccounttype;
	}
	public void setChangeaccounttype(Integer changeaccounttype) {
		this.changeaccounttype = changeaccounttype;
	}
	public Integer getChangetype() {
		return changetype;
	}
	public void setChangetype(Integer changetype) {
		this.changetype = changetype;
	}
	public BigDecimal getChangeamt() {
		return CommonTool.FormatBigDecimal(changeamt).setScale(2, BigDecimal.ROUND_HALF_UP)  ;
	}
	public void setChangeamt(BigDecimal changeamt) {
		this.changeamt = changeamt;
	}
	public String getChangebilltype() {
		return changebilltype;
	}
	public void setChangebilltype(String changebilltype) {
		this.changebilltype = changebilltype;
	}
	public String getChangebilltypeText() {
		return changebilltypeText;
	}
	public void setChangebilltypeText(String changebilltypeText) {
		this.changebilltypeText = changebilltypeText;
	}
	public String getChangebillno() {
		return changebillno;
	}
	public void setChangebillno(String changebillno) {
		this.changebillno = changebillno;
	}
	public String getChagedate() {
		return chagedate;
	}
	public void setChagedate(String chagedate) {
		this.chagedate = chagedate;
	}
	public BigDecimal getChangebeforeamt() {
		return CommonTool.FormatBigDecimal(changebeforeamt).setScale(2, BigDecimal.ROUND_HALF_UP)  ;
	}
	public void setChangebeforeamt(BigDecimal changebeforeamt) {
		this.changebeforeamt = changebeforeamt;
	}
	public Integer getChangeseqno() {
		return changeseqno;
	}
	public void setChangeseqno(Integer changeseqno) {
		this.changeseqno = changeseqno;
	}
}
