package com.amani.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 供应商信息
 */
@Entity
@Table(name="supplierprice")
public class Supplierprice implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	//编号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	//供应商编号
	private String supplierno;
	//联系人编号
	private String linkno;
	//产品编号
	private String goodsno;
	//产品名称
	private String goodsname;
	//产品价格
	private Float price;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSupplierno() {
		return supplierno;
	}
	public void setSupplierno(String supplierno) {
		this.supplierno = supplierno;
	}
	public String getLinkno() {
		return linkno;
	}
	public void setLinkno(String linkno) {
		this.linkno = linkno;
	}
	public String getGoodsno() {
		return goodsno;
	}
	public void setGoodsno(String goodsno) {
		this.goodsno = goodsno;
	}
	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
}
