package com.amani.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 职位学分信息
 */
@Entity
@Table(name="schoolemployee")
public class SchoolEmployee implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	//联合主键
	@EmbeddedId
	private SchoolUnionPK pk;
	//是否合格
	private Integer ispass;
	//备注
	private String remark;
	//操作人
	private String operationer;
	//状态
	private Integer state=1;
	//录入部门
	@Transient
	private String salecompid;
	//录入单号
	@Transient
	private String salebillid;
	//工号
	@Transient
	private String staffno;
	//课程
	@Transient
	private String credit;
	//姓名
	@Transient
	private String staffname;
	//职位
	@Transient
	private String position;
	//身份证
	@Transient
	private String pccid;
	//手机
	@Transient
	private String mobilephone;
	
	public SchoolUnionPK getPk() {
		return pk;
	}
	public void setPk(SchoolUnionPK pk) {
		this.pk = pk;
	}
	public Integer getIspass() {
		return ispass;
	}
	public void setIspass(Integer ispass) {
		this.ispass = ispass;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOperationer() {
		return operationer;
	}
	public void setOperationer(String operationer) {
		this.operationer = operationer;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getSalecompid() {
		return salecompid;
	}
	public void setSalecompid(String salecompid) {
		this.salecompid = salecompid;
	}
	public String getSalebillid() {
		return salebillid;
	}
	public void setSalebillid(String salebillid) {
		this.salebillid = salebillid;
	}
	public String getStaffno() {
		return staffno;
	}
	public void setStaffno(String staffno) {
		this.staffno = staffno;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getStaffname() {
		return staffname;
	}
	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPccid() {
		return pccid;
	}
	public void setPccid(String pccid) {
		this.pccid = pccid;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
}
