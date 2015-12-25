package com.amani.model;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Compwarehouse  implements java.io.Serializable {


	private CompwarehouseId id;
	private String warehousename;
	private String bwarehouseno;
	private String warehousecontact;
	private String warehousephone;
	private String warehousefax;
	private String warehouseaddress;
	public CompwarehouseId getId() {
		return id;
	}
	public void setId(CompwarehouseId id) {
		this.id = id;
	}
	public String getWarehousename() {
		return warehousename;
	}
	public void setWarehousename(String warehousename) {
		this.warehousename = warehousename;
	}
	public String getWarehousecontact() {
		return warehousecontact;
	}
	public void setWarehousecontact(String warehousecontact) {
		this.warehousecontact = warehousecontact;
	}
	public String getWarehousephone() {
		return warehousephone;
	}
	public void setWarehousephone(String warehousephone) {
		this.warehousephone = warehousephone;
	}
	public String getWarehousefax() {
		return warehousefax;
	}
	public void setWarehousefax(String warehousefax) {
		this.warehousefax = warehousefax;
	}
	public String getWarehouseaddress() {
		return warehouseaddress;
	}
	public void setWarehouseaddress(String warehouseaddress) {
		this.warehouseaddress = warehouseaddress;
	}
	public String getBwarehouseno() {
		return bwarehouseno;
	}
	public void setBwarehouseno(String bwarehouseno) {
		this.bwarehouseno = bwarehouseno;
	}

	
}