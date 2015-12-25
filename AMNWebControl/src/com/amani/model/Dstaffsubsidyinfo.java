package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dstaffsubsidyinfo  implements java.io.Serializable {


	private DstaffsubsidyinfoId id;
	private String handstaffinid;
	private Integer  subsidytype;	
	private BigDecimal subsidyamt;
	
	public DstaffsubsidyinfoId getId() {
		return id;
	}
	public void setId(DstaffsubsidyinfoId id) {
		this.id = id;
	}
	public Integer getSubsidytype() {
		return subsidytype;
	}
	public void setSubsidytype(Integer subsidytype) {
		this.subsidytype = subsidytype;
	}
	public BigDecimal getSubsidyamt() {
		return subsidyamt;
	}
	public void setSubsidyamt(BigDecimal subsidyamt) {
		this.subsidyamt = subsidyamt;
	}
	public String getHandstaffinid() {
		return handstaffinid;
	}
	public void setHandstaffinid(String handstaffinid) {
		this.handstaffinid = handstaffinid;
	}

	
}