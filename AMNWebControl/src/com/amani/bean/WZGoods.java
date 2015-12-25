package com.amani.bean;

import java.io.Serializable;

public class WZGoods implements Serializable{

	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	// 产品ID
	private String materialID;
	// 产品分类名称
	private String materialTypeName;
	// 产品编号
	private String materialCode;
	// 产品名称
    protected String materialName;
	// 产品规格
	private String materialSpecification;
	// 产品分类
	private String materialTypeID;
	// 计量单位
	private String unitName;
	public String getMaterialID() {
		return materialID;
	}
	public void setMaterialID(String materialID) {
		this.materialID = materialID;
	}
	public String getMaterialTypeName() {
		return materialTypeName;
	}
	public void setMaterialTypeName(String materialTypeName) {
		this.materialTypeName = materialTypeName;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getMaterialSpecification() {
		return materialSpecification;
	}
	public void setMaterialSpecification(String materialSpecification) {
		this.materialSpecification = materialSpecification;
	}
	public String getMaterialTypeID() {
		return materialTypeID;
	}
	public void setMaterialTypeID(String materialTypeID) {
		this.materialTypeID = materialTypeID;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
}
