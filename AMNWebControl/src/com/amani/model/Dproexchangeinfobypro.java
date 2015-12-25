package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dproexchangeinfobypro  implements java.io.Serializable {


	private DproexchangeinfobyproId id;
	private String changeproid;
	private String changeproname;
	private BigDecimal changeprocount;
	private BigDecimal changeproamt;
	private BigDecimal lastcount;
	private BigDecimal lastamt;
	private String bprojectno;
	private Double bproseqno;
	public String getBprojectno() {
		return bprojectno;
	}
	public void setBprojectno(String bprojectno) {
		this.bprojectno = bprojectno;
	}
	public DproexchangeinfobyproId getId() {
		return id;
	}
	public void setId(DproexchangeinfobyproId id) {
		this.id = id;
	}
	public String getChangeproid() {
		return changeproid;
	}
	public void setChangeproid(String changeproid) {
		this.changeproid = changeproid;
	}
	public BigDecimal getChangeprocount() {
		return changeprocount;
	}
	public void setChangeprocount(BigDecimal changeprocount) {
		this.changeprocount = changeprocount;
	}
	public BigDecimal getChangeproamt() {
		return changeproamt;
	}
	public void setChangeproamt(BigDecimal changeproamt) {
		this.changeproamt = changeproamt;
	}
	public String getChangeproname() {
		return changeproname;
	}
	public void setChangeproname(String changeproname) {
		this.changeproname = changeproname;
	}
	public Double getBproseqno() {
		return bproseqno;
	}
	public void setBproseqno(Double bproseqno) {
		this.bproseqno = bproseqno;
	}
	public BigDecimal getLastcount() {
		return lastcount;
	}
	public void setLastcount(BigDecimal lastcount) {
		this.lastcount = lastcount;
	}
	public BigDecimal getLastamt() {
		return lastamt;
	}
	public void setLastamt(BigDecimal lastamt) {
		this.lastamt = lastamt;
	}
	
}