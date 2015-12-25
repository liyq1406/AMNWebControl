package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mreturngoodsinfo  implements java.io.Serializable {


	private MreturngoodsinfoId id;
	private String returndate;	
	private String returntime;	
    private String returnstaffid;
    private String returnstaffname;
    private Integer returnstate;
    private String returnwareid;
    private String returnopationerid;
    private String returnopationdate;
    private String confirmopationerid;
    private String confirmopationdate;
    private	String confirmcompid;
    private String breturncompid;
    private String breturncompname;
    private String breturnbillid;
	public MreturngoodsinfoId getId() {
		return id;
	}
	public void setId(MreturngoodsinfoId id) {
		this.id = id;
	}
	public String getReturndate() {
		return returndate;
	}
	public void setReturndate(String returndate) {
		this.returndate = returndate;
	}
	public String getReturntime() {
		return returntime;
	}
	public void setReturntime(String returntime) {
		this.returntime = returntime;
	}
	public String getReturnstaffid() {
		return returnstaffid;
	}
	public void setReturnstaffid(String returnstaffid) {
		this.returnstaffid = returnstaffid;
	}
	public String getReturnstaffname() {
		return returnstaffname;
	}
	public void setReturnstaffname(String returnstaffname) {
		this.returnstaffname = returnstaffname;
	}
	public Integer getReturnstate() {
		return returnstate;
	}
	public void setReturnstate(Integer returnstate) {
		this.returnstate = returnstate;
	}
	public String getReturnwareid() {
		return returnwareid;
	}
	public void setReturnwareid(String returnwareid) {
		this.returnwareid = returnwareid;
	}
	public String getReturnopationerid() {
		return returnopationerid;
	}
	public void setReturnopationerid(String returnopationerid) {
		this.returnopationerid = returnopationerid;
	}
	public String getReturnopationdate() {
		return returnopationdate;
	}
	public void setReturnopationdate(String returnopationdate) {
		this.returnopationdate = returnopationdate;
	}
	public String getConfirmopationerid() {
		return confirmopationerid;
	}
	public void setConfirmopationerid(String confirmopationerid) {
		this.confirmopationerid = confirmopationerid;
	}
	public String getConfirmopationdate() {
		return confirmopationdate;
	}
	public void setConfirmopationdate(String confirmopationdate) {
		this.confirmopationdate = confirmopationdate;
	}
	public String getBreturncompid() {
		return breturncompid;
	}
	public void setBreturncompid(String breturncompid) {
		this.breturncompid = breturncompid;
	}
	public String getBreturncompname() {
		return breturncompname;
	}
	public void setBreturncompname(String breturncompname) {
		this.breturncompname = breturncompname;
	}
	public String getBreturnbillid() {
		return breturnbillid;
	}
	public void setBreturnbillid(String breturnbillid) {
		this.breturnbillid = breturnbillid;
	}
	public String getConfirmcompid() {
		return confirmcompid;
	}
	public void setConfirmcompid(String confirmcompid) {
		this.confirmcompid = confirmcompid;
	}
}