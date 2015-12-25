package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mgoodsstockinfo  implements java.io.Serializable {


	private MgoodsstockinfoId id;
	private String changedate;	
	private String changetime;	
    private String changewareid;
    private Integer changeoption;
    private Integer changeflag;	
    private String changestaffid;
	public MgoodsstockinfoId getId() {
		return id;
	}
	public void setId(MgoodsstockinfoId id) {
		this.id = id;
	}
	public String getChangedate() {
		return changedate;
	}
	public void setChangedate(String changedate) {
		this.changedate = changedate;
	}
	public String getChangetime() {
		return changetime;
	}
	public void setChangetime(String changetime) {
		this.changetime = changetime;
	}
	public String getChangewareid() {
		return changewareid;
	}
	public void setChangewareid(String changewareid) {
		this.changewareid = changewareid;
	}
	public Integer getChangeoption() {
		return changeoption;
	}
	public void setChangeoption(Integer changeoption) {
		this.changeoption = changeoption;
	}
	public Integer getChangeflag() {
		return changeflag;
	}
	public void setChangeflag(Integer changeflag) {
		this.changeflag = changeflag;
	}
	public String getChangestaffid() {
		return changestaffid;
	}
	public void setChangestaffid(String changestaffid) {
		this.changestaffid = changestaffid;
	}
}