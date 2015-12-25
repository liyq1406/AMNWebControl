package com.amani.model;

import java.math.BigDecimal;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mproexchangeinfo  implements java.io.Serializable {


	private MproexchangeinfoId id;

	private String changedate;
	private String changetime;
	
	private String changecardno;
	private String changeaccounttype;
    private String changecardtype;
    private String changecardtypename;
    private String membername;
	private String memberphone;
	private String changeopationerid;
	private String changeopationdate;
    private String financedate;
    private Integer backcsflag;
	private String backcsbillid;
	
    private String bchangecompid;
    private String bchangebillid;
    
    private BigDecimal curaccountkeepamt;
    private BigDecimal curaccountdebtamt;
    private BigDecimal curproaccountamt;
    private BigDecimal cursendaccountkeepamt;
    private BigDecimal curproaccountdebtamt;
    private BigDecimal curfaceaccountamt;
	
	public BigDecimal getCurproaccountdebtamt() {
		return curproaccountdebtamt;
	}
	public void setCurproaccountdebtamt(BigDecimal curproaccountdebtamt) {
		this.curproaccountdebtamt = curproaccountdebtamt;
	}
	public BigDecimal getCuraccountkeepamt() {
		return curaccountkeepamt;
	}
	public void setCuraccountkeepamt(BigDecimal curaccountkeepamt) {
		this.curaccountkeepamt = curaccountkeepamt;
	}
	public BigDecimal getCuraccountdebtamt() {
		return curaccountdebtamt;
	}
	public void setCuraccountdebtamt(BigDecimal curaccountdebtamt) {
		this.curaccountdebtamt = curaccountdebtamt;
	}
	public BigDecimal getCurproaccountamt() {
		return curproaccountamt;
	}
	public void setCurproaccountamt(BigDecimal curproaccountamt) {
		this.curproaccountamt = curproaccountamt;
	}
	public MproexchangeinfoId getId() {
		return id;
	}
	public void setId(MproexchangeinfoId id) {
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
	public String getChangecardno() {
		return changecardno;
	}
	public void setChangecardno(String changecardno) {
		this.changecardno = changecardno;
	}
	public String getChangecardtype() {
		return changecardtype;
	}
	public void setChangecardtype(String changecardtype) {
		this.changecardtype = changecardtype;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public String getMemberphone() {
		return memberphone;
	}
	public void setMemberphone(String memberphone) {
		this.memberphone = memberphone;
	}
	public String getChangeopationerid() {
		return changeopationerid;
	}
	public void setChangeopationerid(String changeopationerid) {
		this.changeopationerid = changeopationerid;
	}
	public String getChangeopationdate() {
		return changeopationdate;
	}
	public void setChangeopationdate(String changeopationdate) {
		this.changeopationdate = changeopationdate;
	}
	public String getFinancedate() {
		return financedate;
	}
	public void setFinancedate(String financedate) {
		this.financedate = financedate;
	}
	public Integer getBackcsflag() {
		return backcsflag;
	}
	public void setBackcsflag(Integer backcsflag) {
		this.backcsflag = backcsflag;
	}
	public String getBackcsbillid() {
		return backcsbillid;
	}
	public void setBackcsbillid(String backcsbillid) {
		this.backcsbillid = backcsbillid;
	}
	public String getBchangecompid() {
		return bchangecompid;
	}
	public void setBchangecompid(String bchangecompid) {
		this.bchangecompid = bchangecompid;
	}
	public String getBchangebillid() {
		return bchangebillid;
	}
	public void setBchangebillid(String bchangebillid) {
		this.bchangebillid = bchangebillid;
	}
	public String getChangeaccounttype() {
		return changeaccounttype;
	}
	public void setChangeaccounttype(String changeaccounttype) {
		this.changeaccounttype = changeaccounttype;
	}
	public String getChangecardtypename() {
		return changecardtypename;
	}
	public void setChangecardtypename(String changecardtypename) {
		this.changecardtypename = changecardtypename;
	}
	public BigDecimal getCursendaccountkeepamt() {
		return cursendaccountkeepamt;
	}
	public void setCursendaccountkeepamt(BigDecimal cursendaccountkeepamt) {
		this.cursendaccountkeepamt = cursendaccountkeepamt;
	}
	public BigDecimal getCurfaceaccountamt() {
		return curfaceaccountamt;
	}
	public void setCurfaceaccountamt(BigDecimal curfaceaccountamt) {
		this.curfaceaccountamt = curfaceaccountamt;
	}
}