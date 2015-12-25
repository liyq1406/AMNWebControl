package com.amani.model;


/**
 * 
 * @author wwj
 * @version: 1.0
 * @Copyright: AMN
 */
@SuppressWarnings("serial")
public class StaffHairdesignerinfo  implements java.io.Serializable {
	private Integer id;
	private String name;
	private String staffopenid;
	private String imageurl;
	private String content;
	private Integer ordernum;
	private Integer praisenum;
	private String label2;
	private String lable;
	private String auditstate;
	private String manageno;
	private String staffno;
	private String staffname;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStaffopenid() {
		return staffopenid;
	}
	public void setStaffopenid(String staffopenid) {
		this.staffopenid = staffopenid;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(Integer ordernum) {
		this.ordernum = ordernum;
	}
	public Integer getPraisenum() {
		return praisenum;
	}
	public void setPraisenum(Integer praisenum) {
		this.praisenum = praisenum;
	}
	public String getLabel2() {
		return label2;
	}
	public void setLabel2(String label2) {
		this.label2 = label2;
	}
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	public String getAuditstate() {
		return auditstate;
	}
	public void setAuditstate(String auditstate) {
		this.auditstate = auditstate;
	}
	public String getManageno() {
		return manageno;
	}
	public void setManageno(String manageno) {
		this.manageno = manageno;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStaffno() {
		return staffno;
	}
	public void setStaffno(String staffno) {
		this.staffno = staffno;
	}
	public String getStaffname() {
		return staffname;
	}
	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}
}