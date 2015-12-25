package com.amani.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 活动赠送主档
 */
@Entity
@Table(name="mactivitygive")
public class Mactivitygive  implements Serializable{
	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id; 				//流水编号
	private String activcompid; //门店编号
	private String activinid; //活动唯一编号
	private Integer activorand; //活动逻辑1:或、2:且
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getActivcompid() {
		return activcompid;
	}
	public void setActivcompid(String activcompid) {
		this.activcompid = activcompid;
	}
	public String getActivinid() {
		return activinid;
	}
	public void setActivinid(String activinid) {
		this.activinid = activinid;
	}
	public Integer getActivorand() {
		return activorand;
	}
	public void setActivorand(Integer activorand) {
		this.activorand = activorand;
	}
}
