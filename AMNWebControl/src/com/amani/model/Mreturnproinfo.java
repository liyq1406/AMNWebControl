package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mreturnproinfo  implements java.io.Serializable {


	private MreturnproinfoId id;
    private String breturncompid;
    private String breturnbillid;
	private String returndate;
	private String returntime;
	
	private String returncardno;
	private String cardvesting;
    private String cardtype;
    private String cardtypename;
    private String membername;
    private String memberphone;
    
    private BigDecimal curkeepamt;
    private BigDecimal curkeepproamt;
    private BigDecimal cursendamt;
    private BigDecimal curpointamt;
    
    
    private String changecardno;
    private String opencardtype;
    private String opencardtypename;
    private BigDecimal opentotalamt;
    private BigDecimal returnkeeptotalamt;
    private BigDecimal changetotalamt;
    private BigDecimal costproamt;
	public MreturnproinfoId getId() {
		return id;
	}
	public void setId(MreturnproinfoId id) {
		this.id = id;
	}
	public String getReturndate() {
		return returndate;
	}
	public void setReturndate(String returndate) {
		this.returndate = returndate;
	}
	public String getReturntime() {
		return returntime;
	}
	public void setReturntime(String returntime) {
		this.returntime = returntime;
	}
	public String getReturncardno() {
		return returncardno;
	}
	public void setReturncardno(String returncardno) {
		this.returncardno = returncardno;
	}
	public String getCardvesting() {
		return cardvesting;
	}
	public void setCardvesting(String cardvesting) {
		this.cardvesting = cardvesting;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public String getMemberphone() {
		return memberphone;
	}
	public void setMemberphone(String memberphone) {
		this.memberphone = memberphone;
	}
	public BigDecimal getCurkeepamt() {
		return curkeepamt;
	}
	public void setCurkeepamt(BigDecimal curkeepamt) {
		this.curkeepamt = curkeepamt;
	}
	public BigDecimal getCurkeepproamt() {
		return curkeepproamt;
	}
	public void setCurkeepproamt(BigDecimal curkeepproamt) {
		this.curkeepproamt = curkeepproamt;
	}
	public BigDecimal getCursendamt() {
		return cursendamt;
	}
	public void setCursendamt(BigDecimal cursendamt) {
		this.cursendamt = cursendamt;
	}
	public BigDecimal getCurpointamt() {
		return curpointamt;
	}
	public void setCurpointamt(BigDecimal curpointamt) {
		this.curpointamt = curpointamt;
	}
	public String getChangecardno() {
		return changecardno;
	}
	public void setChangecardno(String changecardno) {
		this.changecardno = changecardno;
	}
	public String getOpencardtype() {
		return opencardtype;
	}
	public void setOpencardtype(String opencardtype) {
		this.opencardtype = opencardtype;
	}
	public BigDecimal getOpentotalamt() {
		return opentotalamt;
	}
	public void setOpentotalamt(BigDecimal opentotalamt) {
		this.opentotalamt = opentotalamt;
	}
	public BigDecimal getReturnkeeptotalamt() {
		return returnkeeptotalamt;
	}
	public void setReturnkeeptotalamt(BigDecimal returnkeeptotalamt) {
		this.returnkeeptotalamt = returnkeeptotalamt;
	}
	public BigDecimal getChangetotalamt() {
		return changetotalamt;
	}
	public void setChangetotalamt(BigDecimal changetotalamt) {
		this.changetotalamt = changetotalamt;
	}
	public String getBreturncompid() {
		return breturncompid;
	}
	public void setBreturncompid(String breturncompid) {
		this.breturncompid = breturncompid;
	}
	public String getBreturnbillid() {
		return breturnbillid;
	}
	public void setBreturnbillid(String breturnbillid) {
		this.breturnbillid = breturnbillid;
	}
	public String getCardtypename() {
		return cardtypename;
	}
	public void setCardtypename(String cardtypename) {
		this.cardtypename = cardtypename;
	}
	public String getOpencardtypename() {
		return opencardtypename;
	}
	public void setOpencardtypename(String opencardtypename) {
		this.opencardtypename = opencardtypename;
	}
	public BigDecimal getCostproamt() {
		return costproamt;
	}
	public void setCostproamt(BigDecimal costproamt) {
		this.costproamt = costproamt;
	}
}