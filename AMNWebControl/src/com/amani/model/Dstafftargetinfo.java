package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Dstafftargetinfo  implements java.io.Serializable {


	private DstafftargetinfoId id;
	private String handstaffinid;
	private Integer  targettype;	
	private BigDecimal targetamt;
	private String startdate;
	private String enddate;
	

	public String getHandstaffinid() {
		return handstaffinid;
	}
	public void setHandstaffinid(String handstaffinid) {
		this.handstaffinid = handstaffinid;
	}
	public Integer getTargettype() {
		return targettype;
	}
	public void setTargettype(Integer targettype) {
		this.targettype = targettype;
	}
	public BigDecimal getTargetamt() {
		return targetamt;
	}
	public void setTargetamt(BigDecimal targetamt) {
		this.targetamt = targetamt;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public DstafftargetinfoId getId() {
		return id;
	}
	public void setId(DstafftargetinfoId id) {
		this.id = id;
	}

	
}