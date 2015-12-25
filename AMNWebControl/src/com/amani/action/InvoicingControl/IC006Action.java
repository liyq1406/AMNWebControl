package com.amani.action.InvoicingControl;

import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;


import com.amani.bean.GoodsStockChangeBean;
import com.amani.service.InvoicingControl.IC006Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ic006")
public class IC006Action {
	@Autowired
	private IC006Service ic006Service;
	private String strCurCompId;
	private String strFromDate;
	private String strToDate;
	private String strFromGoodsNo;
	private String strToGoodsNo;
	private String strWareId;
	private String strMessage;
	private List<GoodsStockChangeBean> lsDataSet;

	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		this.lsDataSet=this.ic006Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate),strFromGoodsNo,strToGoodsNo,strWareId);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	public String getStrCurCompId() {
		return strCurCompId;
	}
	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}
	public String getStrMessage() {
		return strMessage;
	}
	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}



	@JSON(serialize=false)
	public IC006Service getIc006Service() {
		return ic006Service;
	}
	@JSON(serialize=false)
	public void setIc006Service(IC006Service ic006Service) {
		this.ic006Service = ic006Service;
	}




	public List<GoodsStockChangeBean> getLsDataSet() {
		return lsDataSet;
	}

	public void setLsDataSet(List<GoodsStockChangeBean> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}

	public String getStrFromDate() {
		return strFromDate;
	}

	public void setStrFromDate(String strFromDate) {
		this.strFromDate = strFromDate;
	}

	public String getStrToDate() {
		return strToDate;
	}

	public void setStrToDate(String strToDate) {
		this.strToDate = strToDate;
	}

	public String getStrFromGoodsNo() {
		return strFromGoodsNo;
	}

	public void setStrFromGoodsNo(String strFromGoodsNo) {
		this.strFromGoodsNo = strFromGoodsNo;
	}

	public String getStrToGoodsNo() {
		return strToGoodsNo;
	}

	public void setStrToGoodsNo(String strToGoodsNo) {
		this.strToGoodsNo = strToGoodsNo;
	}

	public String getStrWareId() {
		return strWareId;
	}

	public void setStrWareId(String strWareId) {
		this.strWareId = strWareId;
	}



	
	
	
}
