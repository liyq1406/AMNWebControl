package com.amani.model;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Sysmodepurview  implements java.io.Serializable {


	private SysmodepurviewId id;
	private String purviewname;
	private String remark;
	private String bcurpurviewno;
	private String showflag;
	public String getShowflag() {
		return showflag;
	}
	public void setShowflag(String showflag) {
		this.showflag = showflag;
	}
	public String getBcurpurviewno() {
		return bcurpurviewno;
	}
	public void setBcurpurviewno(String bcurpurviewno) {
		this.bcurpurviewno = bcurpurviewno;
	}
	public SysmodepurviewId getId() {
		return id;
	}
	public void setId(SysmodepurviewId id) {
		this.id = id;
	}
	public String getPurviewname() {
		return purviewname;
	}
	public void setPurviewname(String purviewname) {
		this.purviewname = purviewname;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}