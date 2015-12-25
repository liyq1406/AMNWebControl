package com.amani.action.CardControl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ReportAction;
import com.amani.model.Dconsumeinfo;
import com.amani.model.DconsumeinfoId;
import com.amani.model.Dpayinfo;
import com.amani.model.DpayinfoId;
import com.amani.model.Goodsinfo;
import com.amani.model.Mconsumeinfo;
import com.amani.model.MconsumeinfoId;
import com.amani.service.CardControl.CC021Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/cc021")
public class CC021Action extends AMN_ReportAction{
	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private CC021Service cc021Service;
	private String strJsonParam;	
	private String strCurCompId;
	private String strCurBillId;
	private Integer csinfotype;
	private Integer billinsertype;
	private BigDecimal cspayamt;
	private Mconsumeinfo mconsumeinfo;
	private Goodsinfo goodsinfo;
	
	@Action(value = "loadBill",  results = {@Result(name = "load_success", type = "json"),
			@Result(name = "load_failure", type = "json")}) 
	public String loadBill(){
		try{
			strCurBillId=this.cc021Service.loadBill(this.strCurCompId);
			strJsonParam=this.cc021Service.getDataTool().loadSysParam(this.strCurCompId,"SP067");
			return SystemFinal.LOAD_SUCCESS;
		}catch(Exception ex){
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "loadGoods",  results = {@Result(name = "load_success", type = "json"),
			@Result(name = "load_failure", type = "json")}) 
	public String loadGoods(){
		try{
			goodsinfo=this.cc021Service.loadBarByGoods(goodsinfo.getGoodsbarno());
			if(goodsinfo==null){
				this.strMessage="条码产品信息不存在！";
				return SystemFinal.LOAD_FAILURE;
			}
			if(goodsinfo.getUseflag()!=3){
				this.strMessage="条码产品必须为已销售状态！";
				return SystemFinal.LOAD_FAILURE;
			}
			if(goodsinfo.getGoodsusetype()!=2 && goodsinfo.getGoodsusetype()!=3){
				this.strMessage="条码产品用途必须为院装或通用！";
				return SystemFinal.LOAD_FAILURE;
			}
			return SystemFinal.LOAD_SUCCESS;
		}catch(Exception ex){
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "post",  results = {@Result(name = "post_success", type = "json"),
			@Result(name = "post_failure", type = "json")}) 
	public String post(){
		try{
			Gson gson = new Gson();
			List<Dpayinfo> dpayinfoSet = null;
			List<Dconsumeinfo> dconsumeinfoSet = new ArrayList<Dconsumeinfo>();
			String csbillid = this.cc021Service.loadBill(this.strCurCompId);
			mconsumeinfo.setId(new MconsumeinfoId(this.strCurCompId, csbillid));
			mconsumeinfo.setCsmanualno("");
			String date = CommonTool.getCurrDate();
			String time = CommonTool.setTimeMask(CommonTool.getCurrTime(), 1);
			mconsumeinfo.setCsdate(date);
			mconsumeinfo.setCsstarttime(CommonTool.getTimeMask(CommonTool.getCurrTime(), 2));
			mconsumeinfo.setCsendtime(time);
			mconsumeinfo.setCscardno("散客");
			mconsumeinfo.setCsersex(0);
			mconsumeinfo.setCsertype(0);
			mconsumeinfo.setCsopationerid(CommonTool.getLoginInfo("USERID"));
			mconsumeinfo.setCsopationdate(date);
			mconsumeinfo.setFinancedate(date);
			mconsumeinfo.setBackcsflag(0);
			BigDecimal zero = new BigDecimal(0);
			mconsumeinfo.setCscurkeepamt(zero);
			mconsumeinfo.setCscurdepamt(zero);
			if(StringUtils.isNotBlank(strJsonParam)){
				dpayinfoSet = gson.fromJson(strJsonParam, new TypeToken<List<Dpayinfo>>(){}.getType());
				double payseqno=0;
				for (Dpayinfo dpayinfo : dpayinfoSet) {
					dpayinfo.setId(new DpayinfoId(this.strCurCompId,csbillid,"TKUAN",payseqno++));
				}
			}else{
				this.strMessage ="获取支付方式数据失败，请刷新重试";
				return SystemFinal.POST_FAILURE;
			}
			BigDecimal count = new BigDecimal(1);
			String csfirstsaler=this.strCurCompId+billinsertype;
			String csfirstinid = this.cc021Service.getDataTool().loadEmpInidById(this.strCurCompId, csfirstsaler);
			String cspaymode = dpayinfoSet.get(0).getPaymode();
			if(csinfotype==1){
				Dconsumeinfo dconsumeinfo = new Dconsumeinfo();
				dconsumeinfo.setId(new DconsumeinfoId(this.strCurCompId,csbillid,1,0));
				String csitemno=billinsertype==300?"3688888":"4688888";
				dconsumeinfo.setCsitemno(csitemno);
				dconsumeinfo.setCsitemcount(count);
				dconsumeinfo.setCsunitprice(cspayamt);
				dconsumeinfo.setCsdiscount(count);
				dconsumeinfo.setCsdisprice(cspayamt);
				dconsumeinfo.setCsitemamt(cspayamt);
				dconsumeinfo.setCspaymode(cspaymode);
				dconsumeinfo.setCsfirstsaler(csfirstsaler);
				dconsumeinfo.setCsfirsttype("2");//新客类型
				dconsumeinfo.setCsfirstinid(csfirstinid);
				dconsumeinfo.setCsfirstshare(zero);
				dconsumeinfo.setCsproseqno(0d);
				dconsumeinfoSet.add(dconsumeinfo);
			}else{
				if(StringUtils.isNotBlank(strMessage)){
					dconsumeinfoSet = gson.fromJson(strMessage, new TypeToken<List<Dconsumeinfo>>(){}.getType());
					double csseqno=0;
					for (Dconsumeinfo dconsumeinfo : dconsumeinfoSet) {
						dconsumeinfo.setId(new DconsumeinfoId(this.strCurCompId,csbillid,2, csseqno));
						dconsumeinfo.setCsitemcount(count);
						dconsumeinfo.setCsdiscount(count);
						dconsumeinfo.setCsdisprice(dconsumeinfo.getCsunitprice());
						dconsumeinfo.setCspaymode(cspaymode);
						dconsumeinfo.setCsfirstsaler(csfirstsaler);
						dconsumeinfo.setCsfirsttype("2");//新客类型
						dconsumeinfo.setCsfirstinid(csfirstinid);
						dconsumeinfo.setCsfirstshare(zero);
						dconsumeinfo.setCsproseqno(csseqno++);
					}
					this.strMessage="";
				}else{
					this.strMessage ="获取产品明细数据失败，请刷新重试";
					return SystemFinal.POST_FAILURE;
				}
			}
			this.cc021Service.post(mconsumeinfo, dconsumeinfoSet, dpayinfoSet, csinfotype);
			return SystemFinal.POST_SUCCESS;
		}catch(Exception ex){
			ex.printStackTrace();
			this.strMessage = SystemFinal.POST_FAILURE_MSG;
			return SystemFinal.POST_FAILURE;
		}
	} 
	
	public Mconsumeinfo getMconsumeinfo() {
		return mconsumeinfo;
	}
	public void setMconsumeinfo(Mconsumeinfo mconsumeinfo) {
		this.mconsumeinfo = mconsumeinfo;
	}
	public Integer getCsinfotype() {
		return csinfotype;
	}
	public void setCsinfotype(Integer csinfotype) {
		this.csinfotype = csinfotype;
	}
	public Integer getBillinsertype() {
		return billinsertype;
	}
	public void setBillinsertype(Integer billinsertype) {
		this.billinsertype = billinsertype;
	}
	public BigDecimal getCspayamt() {
		return cspayamt;
	}
	public void setCspayamt(BigDecimal cspayamt) {
		this.cspayamt = cspayamt;
	}
	public String getStrJsonParam() {
		return strJsonParam;
	}
	public void setStrJsonParam(String strJsonParam) {
		this.strJsonParam = strJsonParam;
	}
	@Override
	public String getStrMessage() {
		return super.getStrMessage();
	}
	@Override
	public void setStrMessage(String strMessage) {
		super.setStrMessage(strMessage);
	}
	public String getStrCurCompId() {
		return strCurCompId;
	}
	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}
	@JSON(serialize=false)
	public CC021Service getCc021Service() {
		return cc021Service;
	}
	@JSON(serialize=false)
	public void setCc021Service(CC021Service cc021Service) {
		this.cc021Service = cc021Service;
	}
	public String getStrCurBillId() {
		return strCurBillId;
	}
	public void setStrCurBillId(String strCurBillId) {
		this.strCurBillId = strCurBillId;
	}
	public Goodsinfo getGoodsinfo() {
		return goodsinfo;
	}
	public void setGoodsinfo(Goodsinfo goodsinfo) {
		this.goodsinfo = goodsinfo;
	}
}
