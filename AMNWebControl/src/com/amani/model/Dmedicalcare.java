package com.amani.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 合作疗程套餐明细
 */
@Entity
@Table(name="dmedicalcare")
public class Dmedicalcare implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	//编号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String compno;		// 门店编号  
	private String salebillid;	//项目单号
	private String telephone; 	//电话
	private String packageno;	//套餐编号
	private String itemno;		//项目编码
	private String onlyno;		//唯一编号
	private Integer salecount;	//次数
	private Float saleamt;		//金额
	private Integer lastcount;	//剩余次数
	private Float lastamt;		//剩余金额
	private String saledate;	//购买日期
	private Integer state=1;	//状态
	@Transient
	private String name;		//姓名
	@Transient
	private String bonlyno; 	//主档唯一编号
	@Transient
	private String packagename; //项目名称
	@Transient
	private Float packageprice;	//项目价格
	
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
	public String getPackageno() {
		return packageno;
	}
	public void setPackageno(String packageno) {
		this.packageno = packageno;
	}
	public String getItemno() {
		return itemno;
	}
	public void setItemno(String itemno) {
		this.itemno = itemno;
	}
	public String getOnlyno() {
		return onlyno;
	}
	public void setOnlyno(String onlyno) {
		this.onlyno = onlyno;
	}
	public Integer getSalecount() {
		return salecount;
	}
	public void setSalecount(Integer salecount) {
		this.salecount = salecount;
	}
	public Float getSaleamt() {
		return saleamt;
	}
	public void setSaleamt(Float saleamt) {
		this.saleamt = saleamt;
	}
	public Integer getLastcount() {
		return lastcount;
	}
	public void setLastcount(Integer lastcount) {
		this.lastcount = lastcount;
	}
	public Float getLastamt() {
		return lastamt;
	}
	public void setLastamt(Float lastamt) {
		this.lastamt = lastamt;
	}
	public String getSaledate() {
		return saledate;
	}
	public void setSaledate(String saledate) {
		this.saledate = saledate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBonlyno() {
		return bonlyno;
	}
	public void setBonlyno(String bonlyno) {
		this.bonlyno = bonlyno;
	}
	public String getPackagename() {
		return packagename;
	}
	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}
	public Float getPackageprice() {
		return packageprice;
	}
	public void setPackageprice(Float packageprice) {
		this.packageprice = packageprice;
	}
}
