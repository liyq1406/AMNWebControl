package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Mconsumeinfo  implements java.io.Serializable {


	private MconsumeinfoId id;
	private String bcscompid;
	private String bcsbillid;
	private String cskeyno;
	private String csmanualno;
	private String csdate;
	private String csstarttime;
	private String csendtime;
	
	private String cscardno;
	private String csname;
	private String csphone;
    private String cscardtype;
    private String strMemberPhone;
	private Integer csersex;
	private Integer csertype;
	private Integer csercount;
	private Integer backcsflag;
	
	private String csopationerid;
	private String csopationdate;
    private String financedate;

    private String backcsbillid;
    private String tuangoucardno;
    private String tiaomacardno;
    private String diyongcardno;
    private BigDecimal cscurkeepamt;
	private BigDecimal cscurdepamt;
	private String cscardtypeName;
	private Integer	reservationflag;
	private Integer	sendpointflag;
	private String reserveStaffinfo;
	private String recommendempid;
	private String recommendempinid;
	private String recommendempname;
	private String cardphone;
	private String randomno;
	private Integer ctypestate;//C类美容师体验活动标识1.已使用 NULL.未使用
	private Integer scanpaytype;//扫码支付类型1.支付宝 2.微信
	private String scanbarcode;//扫码支付条码
	private String scantradeno;//扫码订单号
	private String ticketcode;//卡诗天猫活动券号
	
	public String getRandomno() {
		return randomno;
	}
	public void setRandomno(String randomno) {
		this.randomno = randomno;
	}
	public String getCardphone() {
		return cardphone;
	}
	public void setCardphone(String cardphone) {
		this.cardphone = cardphone;
	}
	public String getReserveStaffinfo() {
		return reserveStaffinfo;
	}
	public void setReserveStaffinfo(String reserveStaffinfo) {
		this.reserveStaffinfo = reserveStaffinfo;
	}
	
	public Integer getReservationflag() {
		return reservationflag;
	}
	public void setReservationflag(Integer reservationflag) {
		this.reservationflag = reservationflag;
	}
	public MconsumeinfoId getId() {
		return id;
	}
	public void setId(MconsumeinfoId id) {
		this.id = id;
	}
	public String getCskeyno() {
		return cskeyno;
	}
	public void setCskeyno(String cskeyno) {
		this.cskeyno = cskeyno;
	}
	public String getCsmanualno() {
		return csmanualno;
	}
	public void setCsmanualno(String csmanualno) {
		this.csmanualno = csmanualno;
	}
	public String getCsdate() {
		return csdate;
	}
	public void setCsdate(String csdate) {
		this.csdate = csdate;
	}
	public String getCsstarttime() {
		return csstarttime;
	}
	public void setCsstarttime(String csstarttime) {
		this.csstarttime = csstarttime;
	}
	public String getCsendtime() {
		return csendtime;
	}
	public void setCsendtime(String csendtime) {
		this.csendtime = csendtime;
	}
	public String getCscardno() {
		return cscardno;
	}
	public void setCscardno(String cscardno) {
		this.cscardno = cscardno;
	}
	public String getCsname() {
		return csname;
	}
	public void setCsname(String csname) {
		this.csname = csname;
	}
	public String getCscardtype() {
		return cscardtype;
	}
	public void setCscardtype(String cscardtype) {
		this.cscardtype = cscardtype;
	}
	public Integer getCsersex() {
		return csersex;
	}
	public void setCsersex(Integer csersex) {
		this.csersex = csersex;
	}
	public Integer getCsertype() {
		return csertype;
	}
	public void setCsertype(Integer csertype) {
		this.csertype = csertype;
	}
	public Integer getCsercount() {
		return csercount;
	}
	public void setCsercount(Integer csercount) {
		this.csercount = csercount;
	}
	public Integer getBackcsflag() {
		return backcsflag;
	}
	public void setBackcsflag(Integer backcsflag) {
		this.backcsflag = backcsflag;
	}
	public String getCsopationerid() {
		return csopationerid;
	}
	public void setCsopationerid(String csopationerid) {
		this.csopationerid = csopationerid;
	}
	public String getCsopationdate() {
		return csopationdate;
	}
	public void setCsopationdate(String csopationdate) {
		this.csopationdate = csopationdate;
	}
	public String getFinancedate() {
		return financedate;
	}
	public void setFinancedate(String financedate) {
		this.financedate = financedate;
	}
	public String getBackcsbillid() {
		return backcsbillid;
	}
	public void setBackcsbillid(String backcsbillid) {
		this.backcsbillid = backcsbillid;
	}
	public BigDecimal getCscurkeepamt() {
		return CommonTool.FormatBigDecimal(cscurkeepamt).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setCscurkeepamt(BigDecimal cscurkeepamt) {
		this.cscurkeepamt = cscurkeepamt;
	}
	public BigDecimal getCscurdepamt() {
		return CommonTool.FormatBigDecimal(cscurdepamt).setScale(2, BigDecimal.ROUND_HALF_UP)  ;
	}
	public void setCscurdepamt(BigDecimal cscurdepamt) {
		this.cscurdepamt = cscurdepamt;
	}
	public String getCscardtypeName() {
		return cscardtypeName;
	}
	public void setCscardtypeName(String cscardtypeName) {
		this.cscardtypeName = cscardtypeName;
	}
	public String getTuangoucardno() {
		return tuangoucardno;
	}
	public void setTuangoucardno(String tuangoucardno) {
		this.tuangoucardno = tuangoucardno;
	}
	public String getTiaomacardno() {
		return tiaomacardno;
	}
	public void setTiaomacardno(String tiaomacardno) {
		this.tiaomacardno = tiaomacardno;
	}
	public String getDiyongcardno() {
		return diyongcardno;
	}
	public void setDiyongcardno(String diyongcardno) {
		this.diyongcardno = diyongcardno;
	}
	public String getStrMemberPhone() {
		return strMemberPhone;
	}
	public void setStrMemberPhone(String strMemberPhone) {
		this.strMemberPhone = strMemberPhone;
	}
	public String getBcscompid() {
		return bcscompid;
	}
	public void setBcscompid(String bcscompid) {
		this.bcscompid = bcscompid;
	}
	public String getBcsbillid() {
		return bcsbillid;
	}
	public void setBcsbillid(String bcsbillid) {
		this.bcsbillid = bcsbillid;
	}
	public String getRecommendempid() {
		return recommendempid;
	}
	public void setRecommendempid(String recommendempid) {
		this.recommendempid = recommendempid;
	}
	public String getRecommendempname() {
		return recommendempname;
	}
	public void setRecommendempname(String recommendempname) {
		this.recommendempname = recommendempname;
	}
	public String getRecommendempinid() {
		return recommendempinid;
	}
	public void setRecommendempinid(String recommendempinid) {
		this.recommendempinid = recommendempinid;
	}
	public Integer getSendpointflag() {
		return sendpointflag;
	}
	public void setSendpointflag(Integer sendpointflag) {
		this.sendpointflag = sendpointflag;
	}
	public String getCsphone() {
		return csphone;
	}
	public void setCsphone(String csphone) {
		this.csphone = csphone;
	}
	public Integer getCtypestate() {
		return ctypestate;
	}
	public void setCtypestate(Integer ctypestate) {
		this.ctypestate = ctypestate;
	}
	public Integer getScanpaytype() {
		return scanpaytype;
	}
	public void setScanpaytype(Integer scanpaytype) {
		this.scanpaytype = scanpaytype;
	}
	public String getScanbarcode() {
		return scanbarcode;
	}
	public void setScanbarcode(String scanbarcode) {
		this.scanbarcode = scanbarcode;
	}
	public String getScantradeno() {
		return scantradeno;
	}
	public void setScantradeno(String scantradeno) {
		this.scantradeno = scantradeno;
	}
	public String getTicketcode() {
		return ticketcode;
	}
	public void setTicketcode(String ticketcode) {
		this.ticketcode = ticketcode;
	}
}