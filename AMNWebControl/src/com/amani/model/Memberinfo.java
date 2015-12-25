package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Memberinfo  implements java.io.Serializable {


	private MemberinfoId id;
	private String membercreatedate;	
	private String membername;	
    private String memberaddress;
    private String membertphone;

    private String membermphone;
    private String memberFax;
    private String memberemail;
    private String memberzip;
    private Integer membersex;	
    private String memberpaperworkno;
    private String memberbirthday;
    private String memberjob;
    private String cardnotomemberno;
    private String memberqqno;
    private String membermsnno;
    private String membertype;
    private String bmemberno;
    private String bmembervesting;
    private Integer isSendMsg;//是否发送短息
	public Integer getIsSendMsg() {
		return isSendMsg;
	}
	public void setIsSendMsg(Integer isSendMsg) {
		this.isSendMsg = isSendMsg;
	}
	public String getBmembervesting() {
		return bmembervesting;
	}
	public void setBmembervesting(String bmembervesting) {
		this.bmembervesting = bmembervesting;
	}
	public MemberinfoId getId() {
		return id;
	}
	public void setId(MemberinfoId id) {
		this.id = id;
	}
	public String getMembercreatedate() {
		return membercreatedate;
	}
	public void setMembercreatedate(String membercreatedate) {
		this.membercreatedate = membercreatedate;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public String getMemberaddress() {
		return memberaddress;
	}
	public void setMemberaddress(String memberaddress) {
		this.memberaddress = memberaddress;
	}
	public String getMembertphone() {
		return membertphone;
	}
	public void setMembertphone(String membertphone) {
		this.membertphone = membertphone;
	}
	public String getMembermphone() {
		return membermphone;
	}
	public void setMembermphone(String membermphone) {
		this.membermphone = membermphone;
	}
	public String getMemberFax() {
		return memberFax;
	}
	public void setMemberFax(String memberFax) {
		this.memberFax = memberFax;
	}
	public String getMemberemail() {
		return memberemail;
	}
	public void setMemberemail(String memberemail) {
		this.memberemail = memberemail;
	}
	public String getMemberzip() {
		return memberzip;
	}
	public void setMemberzip(String memberzip) {
		this.memberzip = memberzip;
	}
	public Integer getMembersex() {
		return membersex;
	}
	public void setMembersex(Integer membersex) {
		this.membersex = membersex;
	}
	public String getMemberpaperworkno() {
		return memberpaperworkno;
	}
	public void setMemberpaperworkno(String memberpaperworkno) {
		this.memberpaperworkno = memberpaperworkno;
	}
	public String getMemberbirthday() {
		return memberbirthday;
	}
	public void setMemberbirthday(String memberbirthday) {
		this.memberbirthday = memberbirthday;
	}
	public String getMemberjob() {
		return memberjob;
	}
	public void setMemberjob(String memberjob) {
		this.memberjob = memberjob;
	}
	public String getCardnotomemberno() {
		return cardnotomemberno;
	}
	public void setCardnotomemberno(String cardnotomemberno) {
		this.cardnotomemberno = cardnotomemberno;
	}
	public String getMemberqqno() {
		return memberqqno;
	}
	public void setMemberqqno(String memberqqno) {
		this.memberqqno = memberqqno;
	}
	public String getMembermsnno() {
		return membermsnno;
	}
	public void setMembermsnno(String membermsnno) {
		this.membermsnno = membermsnno;
	}
	public String getMembertype() {
		return membertype;
	}
	public void setMembertype(String membertype) {
		this.membertype = membertype;
	}
	public String getBmemberno() {
		return bmemberno;
	}
	public void setBmemberno(String bmemberno) {
		this.bmemberno = bmemberno;
	}
	
}