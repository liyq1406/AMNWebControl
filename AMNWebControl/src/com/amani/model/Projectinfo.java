package com.amani.model;

import java.math.BigDecimal;

import com.amani.tools.CommonTool;

/**
 * 
 * @author LiuJie Jul 1, 2013 12:37:06 PM
 * @version: 1.0
 * @Copyright: AMN
 */
public class Projectinfo  implements java.io.Serializable {


	private ProjectinfoId id;
	private String prjname;
	private String prjtype;
	private Integer prjpricetype;
	private String prjreporttype;
	private String saleunit; 
	private BigDecimal saleprice; 
    private BigDecimal msalecount;
    private BigDecimal msaleprice;
    private BigDecimal rsalecount; 
    private BigDecimal rsaleprice;
    private BigDecimal hsalecount; 
    private BigDecimal hsaleprice; 
    private BigDecimal ysalecount; 
    private BigDecimal ysaleprice; 
    private BigDecimal onecountprice; 
    private BigDecimal onepageprice; 
    private BigDecimal memberprice; 
    private BigDecimal salelowprice; 
    private Integer needhairflag; 
    private Integer useflag; 
    private Integer saleflag; 
    private Integer rateflag; 
    private Integer prjsaletype; 
    private Integer editflag; 
    private Integer pointtype; 
    private BigDecimal pointvalue; 
    private Integer costtype; 
    private BigDecimal costprice; 
    private BigDecimal kyjrate; 
    private BigDecimal ktcrate; 
    private BigDecimal lyjrate; 
    private BigDecimal ltcrate; 
    private String bprisource;
    private String bprjno;
    private String bprjmodeId;
    private Integer finaltype;
    private String prjabridge;
    private BigDecimal newcosttc; 
    private BigDecimal oldcosttc; 
    private Integer markflag;
    private Integer morelongflag;
	private int selfFlag;//是否输入自己的附属部分
	private String ipadname;//IPAD显示名称
	private String ipddl;//IPAD大类
	public String getIpddl() {
		return ipddl;
	}
	public void setIpddl(String ipddl) {
		this.ipddl = ipddl;
	}
	public Integer getFinaltype() {
		return finaltype;
	}
	public void setFinaltype(Integer finaltype) {
		this.finaltype = finaltype;
	}
	public String getBprjno() {
		return bprjno;
	}
	public void setBprjno(String bprjno) {
		this.bprjno = bprjno;
	}
	public String getBprjmodeId() {
		return bprjmodeId;
	}
	public void setBprjmodeId(String bprjmodeId) {
		this.bprjmodeId = bprjmodeId;
	}
	public ProjectinfoId getId() {
		return id;
	}
	public void setId(ProjectinfoId id) {
		this.id = id;
	}
	public String getPrjname() {
		return prjname;
	}
	public void setPrjname(String prjname) {
		this.prjname = prjname;
	}
	public String getPrjtype() {
		return prjtype;
	}
	public void setPrjtype(String prjtype) {
		this.prjtype = prjtype;
	}
	public Integer getPrjpricetype() {
		return prjpricetype;
	}
	public void setPrjpricetype(Integer prjpricetype) {
		this.prjpricetype = prjpricetype;
	}
	public String getPrjreporttype() {
		return prjreporttype;
	}
	public void setPrjreporttype(String prjreporttype) {
		this.prjreporttype = prjreporttype;
	}
	public String getSaleunit() {
		return saleunit;
	}
	public void setSaleunit(String saleunit) {
		this.saleunit = saleunit;
	}
	public BigDecimal getSaleprice() {
		return CommonTool.FormatBigDecimal(saleprice).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public void setSaleprice(BigDecimal saleprice) {
		this.saleprice = saleprice;
	}
	public BigDecimal getMsalecount() {
		return CommonTool.FormatBigDecimal(msalecount).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public void setMsalecount(BigDecimal msalecount) {
		this.msalecount = msalecount;
	}
	public BigDecimal getMsaleprice() {
		return CommonTool.FormatBigDecimal(msaleprice).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public void setMsaleprice(BigDecimal msaleprice) {
		this.msaleprice = msaleprice;
	}
	public BigDecimal getRsalecount() {
		return CommonTool.FormatBigDecimal(rsalecount).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public void setRsalecount(BigDecimal rsalecount) {
		this.rsalecount = rsalecount;
	}
	public BigDecimal getRsaleprice() {
		return CommonTool.FormatBigDecimal(rsaleprice).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public void setRsaleprice(BigDecimal rsaleprice) {
		this.rsaleprice = rsaleprice;
	}
	public BigDecimal getHsalecount() {
		return CommonTool.FormatBigDecimal(hsalecount).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public void setHsalecount(BigDecimal hsalecount) {
		this.hsalecount = hsalecount;
	}
	public BigDecimal getHsaleprice() {
		return CommonTool.FormatBigDecimal(hsaleprice).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public void setHsaleprice(BigDecimal hsaleprice) {
		this.hsaleprice = hsaleprice;
	}
	public BigDecimal getYsalecount() {
		return CommonTool.FormatBigDecimal(ysalecount).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public void setYsalecount(BigDecimal ysalecount) {
		this.ysalecount = ysalecount;
	}
	public BigDecimal getYsaleprice() {
		return CommonTool.FormatBigDecimal(ysaleprice).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public void setYsaleprice(BigDecimal ysaleprice) {
		this.ysaleprice = ysaleprice;
	}
	public BigDecimal getOnecountprice() {
		return CommonTool.FormatBigDecimal(onecountprice).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public void setOnecountprice(BigDecimal onecountprice) {
		this.onecountprice = onecountprice;
	}
	public BigDecimal getSalelowprice() {
		return CommonTool.FormatBigDecimal(salelowprice).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public void setSalelowprice(BigDecimal salelowprice) {
		this.salelowprice = salelowprice;
	}
	public Integer getNeedhairflag() {
		return needhairflag;
	}
	public void setNeedhairflag(Integer needhairflag) {
		this.needhairflag = needhairflag;
	}
	public Integer getUseflag() {
		return useflag;
	}
	public void setUseflag(Integer useflag) {
		this.useflag = useflag;
	}
	public Integer getSaleflag() {
		return saleflag;
	}
	public void setSaleflag(Integer saleflag) {
		this.saleflag = saleflag;
	}
	public Integer getRateflag() {
		return rateflag;
	}
	public void setRateflag(Integer rateflag) {
		this.rateflag = rateflag;
	}
	public Integer getPrjsaletype() {
		return prjsaletype;
	}
	public void setPrjsaletype(Integer prjsaletype) {
		this.prjsaletype = prjsaletype;
	}
	public Integer getEditflag() {
		return editflag;
	}
	public void setEditflag(Integer editflag) {
		this.editflag = editflag;
	}
	public Integer getPointtype() {
		return pointtype;
	}
	public void setPointtype(Integer pointtype) {
		this.pointtype = pointtype;
	}
	public BigDecimal getPointvalue() {
		return CommonTool.FormatBigDecimal(pointvalue).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public void setPointvalue(BigDecimal pointvalue) {
		this.pointvalue = pointvalue;
	}
	public Integer getCosttype() {
		return costtype;
	}
	public void setCosttype(Integer costtype) {
		this.costtype = costtype;
	}
	public BigDecimal getCostprice() {
		return costprice;
	}
	public void setCostprice(BigDecimal costprice) {
		this.costprice = costprice;
	}
	public BigDecimal getKyjrate() {
		return kyjrate;
	}
	public void setKyjrate(BigDecimal kyjrate) {
		this.kyjrate = kyjrate;
	}
	public BigDecimal getKtcrate() {
		return ktcrate;
	}
	public void setKtcrate(BigDecimal ktcrate) {
		this.ktcrate = ktcrate;
	}
	public BigDecimal getLyjrate() {
		return lyjrate;
	}
	public void setLyjrate(BigDecimal lyjrate) {
		this.lyjrate = lyjrate;
	}
	public BigDecimal getLtcrate() {
		return ltcrate;
	}
	public void setLtcrate(BigDecimal ltcrate) {
		this.ltcrate = ltcrate;
	}
	
	public String getBprisource() {
		return bprisource;
	}
	public void setBprisource(String bprisource) {
		this.bprisource = bprisource;
	}
	public BigDecimal getOnepageprice() {
		return onepageprice;
	}
	public void setOnepageprice(BigDecimal onepageprice) {
		this.onepageprice = onepageprice;
	}
	public int getSelfFlag() {
		return selfFlag;
	}
	public void setSelfFlag(int selfFlag) {
		this.selfFlag = selfFlag;
	}
	public String getPrjabridge() {
		return prjabridge;
	}
	public void setPrjabridge(String prjabridge) {
		this.prjabridge = prjabridge;
	}
	public BigDecimal getMemberprice() {
		return memberprice;
	}
	public void setMemberprice(BigDecimal memberprice) {
		this.memberprice = memberprice;
	}
	public BigDecimal getNewcosttc() {
		return newcosttc;
	}
	public void setNewcosttc(BigDecimal newcosttc) {
		this.newcosttc = newcosttc;
	}
	public BigDecimal getOldcosttc() {
		return oldcosttc;
	}
	public void setOldcosttc(BigDecimal oldcosttc) {
		this.oldcosttc = oldcosttc;
	}
	public Integer getMarkflag() {
		return markflag;
	}
	public void setMarkflag(Integer markflag) {
		this.markflag = markflag;
	}
	public Integer getMorelongflag() {
		return morelongflag;
	}
	public void setMorelongflag(Integer morelongflag) {
		this.morelongflag = morelongflag;
	}
	/**
	 *  @BareFieldName : ipadname
	 *
	 *  @return  the ipadname
	 *
	 *
	 **/
	
	public String getIpadname() {
		return ipadname;
	}
	/**
	 *  @BareFieldName : ipadname
	 *
	 *  @return  the ipadname
	 *
	 *  @param ipadname the ipadname to set
	 *
	 **/
	
	public void setIpadname(String ipadname) {
		this.ipadname = ipadname;
	}

	
}