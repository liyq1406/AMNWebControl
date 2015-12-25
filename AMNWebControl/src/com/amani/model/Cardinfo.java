package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Cardinfo  implements java.io.Serializable {


	private CardinfoId id;
	private String cardtype;	
	private String membernotocard;	
    private String salecarddate;
    private String cutoffdate;
    private Integer cardstate;	
    private String salebillno;
    private String costpassword;
    private String searchpassword;
    private String cardremark;
    private Integer cardsource;	
    private Integer costcountbydebts;	
    private BigDecimal costamtbydebts;
	private BigDecimal costamt;
	private String membername;
	private String memberpcid;
	private Integer membersex;
	private BigDecimal account2Amt;
	private BigDecimal account2debtAmt;
	private BigDecimal account3Amt;
	private BigDecimal account3debtAmt;
	private BigDecimal account4Amt;
	private BigDecimal account4debtAmt;
	private BigDecimal account5Amt;
	private BigDecimal account5debtAmt;
	private BigDecimal account6Amt;
	private BigDecimal account6debtAmt;
	private BigDecimal account7Amt;
	private BigDecimal account7debtAmt;
	private BigDecimal dCanCostAmt;
	private String cardtypeName;
	private String bcardno;
	private String bcardvesting;
	private String memberphone;
	private String showmemberphone;
    private String memberpaperworkno;
	private BigDecimal dSaleLowAmt;
	private BigDecimal dFillLowAmt;
	private Integer openflag;	
	private Integer fillflag;	
	private Integer sendamtflag;
	private Integer changeflag;	
	private Integer changerule;
	private BigDecimal slaeproerate;
	public BigDecimal getSlaeproerate() {
		return slaeproerate;
	}
	public void setSlaeproerate(BigDecimal slaeproerate) {
		this.slaeproerate = slaeproerate;
	}
	public String getBcardno() {
		return bcardno;
	}
	public void setBcardno(String bcardno) {
		this.bcardno = bcardno;
	}
	public CardinfoId getId() {
		return id;
	}
	public void setId(CardinfoId id) {
		this.id = id;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getMembernotocard() {
		return membernotocard;
	}
	public void setMembernotocard(String membernotocard) {
		this.membernotocard = membernotocard;
	}
	public String getSalecarddate() {
		return salecarddate;
	}
	public void setSalecarddate(String salecarddate) {
		this.salecarddate = salecarddate;
	}
	public String getCutoffdate() {
		return cutoffdate;
	}
	public void setCutoffdate(String cutoffdate) {
		this.cutoffdate = cutoffdate;
	}
	public Integer getCardstate() {
		return cardstate;
	}
	public void setCardstate(Integer cardstate) {
		this.cardstate = cardstate;
	}
	public String getSalebillno() {
		return salebillno;
	}
	public void setSalebillno(String salebillno) {
		this.salebillno = salebillno;
	}
	public String getCostpassword() {
		return costpassword;
	}
	public void setCostpassword(String costpassword) {
		this.costpassword = costpassword;
	}
	public String getSearchpassword() {
		return searchpassword;
	}
	public void setSearchpassword(String searchpassword) {
		this.searchpassword = searchpassword;
	}
	public String getCardremark() {
		return cardremark;
	}
	public void setCardremark(String cardremark) {
		this.cardremark = cardremark;
	}
	public Integer getCardsource() {
		return cardsource;
	}
	public void setCardsource(Integer cardsource) {
		this.cardsource = cardsource;
	}
	public Integer getCostcountbydebts() {
		return costcountbydebts;
	}
	public void setCostcountbydebts(Integer costcountbydebts) {
		this.costcountbydebts = costcountbydebts;
	}
	public BigDecimal getCostamtbydebts() {
		return CommonTool.FormatBigDecimal(costamtbydebts).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setCostamtbydebts(BigDecimal costamtbydebts) {
		this.costamtbydebts = costamtbydebts;
	}
	public BigDecimal getCostamt() {
		return CommonTool.FormatBigDecimal(costamt).setScale(2, BigDecimal.ROUND_HALF_UP)  ;
	}
	public void setCostamt(BigDecimal costamt) {
		this.costamt = costamt;
	}
	public BigDecimal getAccount2Amt() {
		return CommonTool.FormatBigDecimal(account2Amt).setScale(2, BigDecimal.ROUND_HALF_UP)  ;
	}
	public void setAccount2Amt(BigDecimal account2Amt) {
		this.account2Amt = account2Amt;
	}
	public BigDecimal getAccount2debtAmt() {
		return CommonTool.FormatBigDecimal(account2debtAmt).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setAccount2debtAmt(BigDecimal account2debtAmt) {
		this.account2debtAmt = account2debtAmt;
	}
	public BigDecimal getAccount3Amt() {
		return CommonTool.FormatBigDecimal(account3Amt).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setAccount3Amt(BigDecimal account3Amt) {
		this.account3Amt = account3Amt;
	}
	public BigDecimal getAccount3debtAmt() {
		return CommonTool.FormatBigDecimal(account3debtAmt).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setAccount3debtAmt(BigDecimal account3debtAmt) {
		this.account3debtAmt = account3debtAmt;
	}
	public BigDecimal getAccount5Amt() {
		return CommonTool.FormatBigDecimal(account5Amt).setScale(2, BigDecimal.ROUND_HALF_UP)  ;
	}
	public void setAccount5Amt(BigDecimal account5Amt) {
		this.account5Amt = account5Amt;
	}
	public BigDecimal getAccount5debtAmt() {
		return CommonTool.FormatBigDecimal(account5debtAmt).setScale(2, BigDecimal.ROUND_HALF_UP) ;
	}
	public void setAccount5debtAmt(BigDecimal account5debtAmt) {
		this.account5debtAmt = account5debtAmt;
	}
	public String getCardtypeName() {
		return cardtypeName;
	}
	public void setCardtypeName(String cardtypeName) {
		this.cardtypeName = cardtypeName;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public BigDecimal getDCanCostAmt() {
		return dCanCostAmt;
	}
	public void setDCanCostAmt(BigDecimal canCostAmt) {
		dCanCostAmt = canCostAmt;
	}
	public Integer getMembersex() {
		return membersex;
	}
	public void setMembersex(Integer membersex) {
		this.membersex = membersex;
	}
	public String getMemberphone() {
		return memberphone;
	}
	public void setMemberphone(String memberphone) {
		this.memberphone = memberphone;
	}
	public String getBcardvesting() {
		return bcardvesting;
	}
	public void setBcardvesting(String bcardvesting) {
		this.bcardvesting = bcardvesting;
	}
	public BigDecimal getDSaleLowAmt() {
		return dSaleLowAmt;
	}
	public void setDSaleLowAmt(BigDecimal saleLowAmt) {
		dSaleLowAmt = saleLowAmt;
	}
	public BigDecimal getDFillLowAmt() {
		return dFillLowAmt;
	}
	public void setDFillLowAmt(BigDecimal fillLowAmt) {
		dFillLowAmt = fillLowAmt;
	}
	public Integer getFillflag() {
		return fillflag;
	}
	public void setFillflag(Integer fillflag) {
		this.fillflag = fillflag;
	}
	public Integer getOpenflag() {
		return openflag;
	}
	public void setOpenflag(Integer openflag) {
		this.openflag = openflag;
	}
	public BigDecimal getAccount4Amt() {
		return account4Amt;
	}
	public void setAccount4Amt(BigDecimal account4Amt) {
		this.account4Amt = account4Amt;
	}
	public BigDecimal getAccount4debtAmt() {
		return account4debtAmt;
	}
	public void setAccount4debtAmt(BigDecimal account4debtAmt) {
		this.account4debtAmt = account4debtAmt;
	}
	public String getMemberpaperworkno() {
		return memberpaperworkno;
	}
	public void setMemberpaperworkno(String memberpaperworkno) {
		this.memberpaperworkno = memberpaperworkno;
	}
	public Integer getChangerule() {
		return changerule;
	}
	public void setChangerule(Integer changerule) {
		this.changerule = changerule;
	}
	public String getMemberpcid() {
		return memberpcid;
	}
	public void setMemberpcid(String memberpcid) {
		this.memberpcid = memberpcid;
	}
	public Integer getChangeflag() {
		return changeflag;
	}
	public void setChangeflag(Integer changeflag) {
		this.changeflag = changeflag;
	}
	public BigDecimal getAccount6Amt() {
		return account6Amt;
	}
	public void setAccount6Amt(BigDecimal account6Amt) {
		this.account6Amt = account6Amt;
	}
	public BigDecimal getAccount6debtAmt() {
		return account6debtAmt;
	}
	public void setAccount6debtAmt(BigDecimal account6debtAmt) {
		this.account6debtAmt = account6debtAmt;
	}
	public Integer getSendamtflag() {
		return sendamtflag;
	}
	public void setSendamtflag(Integer sendamtflag) {
		this.sendamtflag = sendamtflag;
	}
	public String getShowmemberphone() {
		return showmemberphone;
	}
	public void setShowmemberphone(String showmemberphone) {
		this.showmemberphone = showmemberphone;
	}
	public BigDecimal getAccount7Amt() {
		return account7Amt;
	}
	public void setAccount7Amt(BigDecimal account7Amt) {
		this.account7Amt = account7Amt;
	}
	public BigDecimal getAccount7debtAmt() {
		return account7debtAmt;
	}
	public void setAccount7debtAmt(BigDecimal account7debtAmt) {
		this.account7debtAmt = account7debtAmt;
	}
}