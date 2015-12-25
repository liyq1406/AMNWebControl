package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mcardnoinsert  implements java.io.Serializable {


	private McardnoinsertId id;
	private String cinsertdate;	
	private String cinserttime;	
    private String cinsertware;
    private String cinsertper;
    private String cinsertperName;
    private String optionconfrimdate;	
    private String optionconfrimper;	
    private String optioncanceldate;	
    private String optioncancelper;	
    private String createcompname;	
    private Integer checkoutflag;	
    private Integer billflag;	
    private String billno;	
    private String checkoutimgurl;	
    private String checkoutmark;	
    private Integer invalid;
    private String bcinsertcompid;
    private String bcinsertcompName;
    private String bcinsertbillid;
    private byte[] reportimage;
	public byte[] getReportimage() {
		return reportimage;
	}
	public void setReportimage(byte[] reportimage) {
		this.reportimage = reportimage;
	}
	public String getBcinsertcompid() {
		return bcinsertcompid;
	}
	public void setBcinsertcompid(String bcinsertcompid) {
		this.bcinsertcompid = bcinsertcompid;
	}
	public String getBcinsertbillid() {
		return bcinsertbillid;
	}
	public void setBcinsertbillid(String bcinsertbillid) {
		this.bcinsertbillid = bcinsertbillid;
	}
	public McardnoinsertId getId() {
		return id;
	}
	public void setId(McardnoinsertId id) {
		this.id = id;
	}
	public String getCinsertdate() {
		return cinsertdate;
	}
	public void setCinsertdate(String cinsertdate) {
		this.cinsertdate = cinsertdate;
	}
	public String getCinserttime() {
		return cinserttime;
	}
	public void setCinserttime(String cinserttime) {
		this.cinserttime = cinserttime;
	}
	public String getCinsertware() {
		return cinsertware;
	}
	public void setCinsertware(String cinsertware) {
		this.cinsertware = cinsertware;
	}
	public String getCinsertper() {
		return cinsertper;
	}
	public void setCinsertper(String cinsertper) {
		this.cinsertper = cinsertper;
	}
	public String getOptionconfrimdate() {
		return optionconfrimdate;
	}
	public void setOptionconfrimdate(String optionconfrimdate) {
		this.optionconfrimdate = optionconfrimdate;
	}
	public String getOptionconfrimper() {
		return optionconfrimper;
	}
	public void setOptionconfrimper(String optionconfrimper) {
		this.optionconfrimper = optionconfrimper;
	}
	public String getOptioncanceldate() {
		return optioncanceldate;
	}
	public void setOptioncanceldate(String optioncanceldate) {
		this.optioncanceldate = optioncanceldate;
	}
	public String getOptioncancelper() {
		return optioncancelper;
	}
	public void setOptioncancelper(String optioncancelper) {
		this.optioncancelper = optioncancelper;
	}
	public Integer getInvalid() {
		return invalid;
	}
	public void setInvalid(Integer invalid) {
		this.invalid = invalid;
	}

	public String getCinsertperName() {
		return cinsertperName;
	}
	public void setCinsertperName(String cinsertperName) {
		this.cinsertperName = cinsertperName;
	}
	public String getBcinsertcompName() {
		return bcinsertcompName;
	}
	public void setBcinsertcompName(String bcinsertcompName) {
		this.bcinsertcompName = bcinsertcompName;
	}
	public String getCreatecompname() {
		return createcompname;
	}
	public void setCreatecompname(String createcompname) {
		this.createcompname = createcompname;
	}
	public Integer getCheckoutflag() {
		return checkoutflag;
	}
	public void setCheckoutflag(Integer checkoutflag) {
		this.checkoutflag = checkoutflag;
	}
	public Integer getBillflag() {
		return billflag;
	}
	public void setBillflag(Integer billflag) {
		this.billflag = billflag;
	}
	public String getBillno() {
		return billno;
	}
	public void setBillno(String billno) {
		this.billno = billno;
	}
	public String getCheckoutimgurl() {
		return checkoutimgurl;
	}
	public void setCheckoutimgurl(String checkoutimgurl) {
		this.checkoutimgurl = checkoutimgurl;
	}
	public String getCheckoutmark() {
		return checkoutmark;
	}
	public void setCheckoutmark(String checkoutmark) {
		this.checkoutmark = checkoutmark;
	}	
	
	
}