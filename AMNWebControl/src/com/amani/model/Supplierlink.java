package com.amani.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 供应商联系人信息
 */
@Entity
@Table(name="supplierlink")
public class Supplierlink implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	//编号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	//联系人名称
	private String name;
	//供应商编号
	private String supplierno;
	//电话
	private String mobile;
	//是否主要联系人
	private Integer ismain;
	//状态
	private Integer state=1;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSupplierno() {
		return supplierno;
	}
	public void setSupplierno(String supplierno) {
		this.supplierno = supplierno;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getIsmain() {
		return ismain;
	}
	public void setIsmain(Integer ismain) {
		this.ismain = ismain;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
}
