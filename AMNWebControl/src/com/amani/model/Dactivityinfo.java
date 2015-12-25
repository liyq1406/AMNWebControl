package com.amani.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 活动设定明细
 */
@Entity
@Table(name="dactivityinfo")
public class Dactivityinfo  implements Serializable{
	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id; 				//流水编号
	private String activno;		//项目\产品\分类编号
	private String activname;	//项目\产品\分类名称
	private Integer activtype;	//类型1:项目、2:分类
	private String activinid;	//活动唯一编号
	private Integer activcount;	//套数/数量
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getActivno() {
		return activno;
	}
	public void setActivno(String activno) {
		this.activno = activno;
	}
	public String getActivname() {
		return activname;
	}
	public void setActivname(String activname) {
		this.activname = activname;
	}
	public Integer getActivtype() {
		return activtype;
	}
	public void setActivtype(Integer activtype) {
		this.activtype = activtype;
	}
	public String getActivinid() {
		return activinid;
	}
	public void setActivinid(String activinid) {
		this.activinid = activinid;
	}
	public Integer getActivcount() {
		return activcount;
	}
	public void setActivcount(Integer activcount) {
		this.activcount = activcount;
	}
}
