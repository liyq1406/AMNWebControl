package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dpayoutinfo  implements java.io.Serializable {


	private 	DpayoutinfoId id;
	private 	String 			payoutitemid;	
    private 	BigDecimal 		payoutitemamt;	
    private 	Integer 		checkbookflag;
    private 	String 			checkbookno;
	private 	String 			payoutmark;	
	private 	Integer 		payoutbillstate;	
	private 	String 			payoutbillstateText;
	private 	String 			checkedopationerid;	
	private 	String 			checkedopationdate;	
	private 	String 			confirmopationerid;	
	private 	String 			confirmopationdate;
	private 	String 			bpayoutbillid;
	private 	String 			bpayoutcompid;	
	private 	String 			bpayoutcompname;	
	private 	String			payoutdate;
	private 	String 			payoutopationerid;
    private 	double			bpayoutseqno;
	public String getBpayoutbillid() {
		return bpayoutbillid;
	}
	public void setBpayoutbillid(String bpayoutbillid) {
		this.bpayoutbillid = bpayoutbillid;
	}
	public String getPayoutdate() {
		return payoutdate;
	}
	public void setPayoutdate(String payoutdate) {
		this.payoutdate = payoutdate;
	}
	public String getPayoutopationerid() {
		return payoutopationerid;
	}
	public void setPayoutopationerid(String payoutopationerid) {
		this.payoutopationerid = payoutopationerid;
	}
	public DpayoutinfoId getId() {
		return id;
	}
	public void setId(DpayoutinfoId id) {
		this.id = id;
	}
	public String getPayoutitemid() {
		return payoutitemid;
	}
	public void setPayoutitemid(String payoutitemid) {
		this.payoutitemid = payoutitemid;
	}
	public BigDecimal getPayoutitemamt() {
		return payoutitemamt;
	}
	public void setPayoutitemamt(BigDecimal payoutitemamt) {
		this.payoutitemamt = payoutitemamt;
	}
	public Integer getCheckbookflag() {
		return checkbookflag;
	}
	public void setCheckbookflag(Integer checkbookflag) {
		this.checkbookflag = checkbookflag;
	}
	public String getCheckbookno() {
		return checkbookno;
	}
	public void setCheckbookno(String checkbookno) {
		this.checkbookno = checkbookno;
	}
	public String getPayoutmark() {
		return payoutmark;
	}
	public void setPayoutmark(String payoutmark) {
		this.payoutmark = payoutmark;
	}
	public Integer getPayoutbillstate() {
		return payoutbillstate;
	}
	public void setPayoutbillstate(Integer payoutbillstate) {
		this.payoutbillstate = payoutbillstate;
	}
	public String getCheckedopationerid() {
		return checkedopationerid;
	}
	public void setCheckedopationerid(String checkedopationerid) {
		this.checkedopationerid = checkedopationerid;
	}
	public String getCheckedopationdate() {
		return checkedopationdate;
	}
	public void setCheckedopationdate(String checkedopationdate) {
		this.checkedopationdate = checkedopationdate;
	}
	public String getConfirmopationerid() {
		return confirmopationerid;
	}
	public void setConfirmopationerid(String confirmopationerid) {
		this.confirmopationerid = confirmopationerid;
	}
	public String getConfirmopationdate() {
		return confirmopationdate;
	}
	public void setConfirmopationdate(String confirmopationdate) {
		this.confirmopationdate = confirmopationdate;
	}
	public String getPayoutbillstateText() {
		return payoutbillstateText;
	}
	public void setPayoutbillstateText(String payoutbillstateText) {
		this.payoutbillstateText = payoutbillstateText;
	}
	public String getBpayoutcompid() {
		return bpayoutcompid;
	}
	public void setBpayoutcompid(String bpayoutcompid) {
		this.bpayoutcompid = bpayoutcompid;
	}
	public String getBpayoutcompname() {
		return bpayoutcompname;
	}
	public void setBpayoutcompname(String bpayoutcompname) {
		this.bpayoutcompname = bpayoutcompname;
	}
	public double getBpayoutseqno() {
		return bpayoutseqno;
	}
	public void setBpayoutseqno(double bpayoutseqno) {
		this.bpayoutseqno = bpayoutseqno;
	}	
}