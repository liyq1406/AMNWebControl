package com.amani.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 合作疗程套餐主档
 */
@Entity
@Table(name="mmedicalcare")
public class Mmedicalcare implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	//编号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	//门店编号
	private String compno;
	//项目单号
	private String salebillid;
	//电话
	private String telephone;
	//唯一编号
	private String onlyno;
	//名称
	private String name;
	//状态
	private Integer state=1;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCompno() {
		return compno;
	}
	public void setCompno(String compno) {
		this.compno = compno;
	}
	public String getSalebillid() {
		return salebillid;
	}
	public void setSalebillid(String salebillid) {
		this.salebillid = salebillid;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getOnlyno() {
		return onlyno;
	}
	public void setOnlyno(String onlyno) {
		this.onlyno = onlyno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
}
