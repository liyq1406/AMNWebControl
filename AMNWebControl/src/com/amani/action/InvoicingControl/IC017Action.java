package com.amani.action.InvoicingControl;

import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;


import com.amani.model.Dconsumeinfo;
import com.amani.model.Projectcostinfo;
import com.amani.service.InvoicingControl.IC017Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ic017")
public class IC017Action {
	@Autowired
	private IC017Service ic017Service;
	private String strCurCompId;
	private String strFromDate;
	private String strToDate;
	private String strFromGoodsNo;
	private String strToGoodsNo;
	private String strGoodsNo;
	private String strGoodsType;
	private String strMessage;
	private  List<Dconsumeinfo> lsDconsumeinfo;
	private List<Projectcostinfo> lsDataSet;

	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		this.lsDataSet=this.ic017Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate),strFromGoodsNo,strToGoodsNo,strGoodsType);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action( value="loadProjectDetialInfo", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadProjectDetialInfo()
	{
		try
		{
			this.lsDconsumeinfo=this.ic017Service.loadDataSet(this.strCurCompId,CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate), this.strGoodsNo);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_SUCCESS;
		}
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
	public IC017Service getIc017Service() {
		return ic017Service;
	}
	@JSON(serialize=false)
	public void setIc017Service(IC017Service ic017Service) {
		this.ic017Service = ic017Service;
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

	public List<Projectcostinfo> getLsDataSet() {
		return lsDataSet;
	}

	public void setLsDataSet(List<Projectcostinfo> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}

	public String getStrGoodsType() {
		return strGoodsType;
	}

	public void setStrGoodsType(String strGoodsType) {
		this.strGoodsType = strGoodsType;
	}

	public String getStrGoodsNo() {
		return strGoodsNo;
	}

	public void setStrGoodsNo(String strGoodsNo) {
		this.strGoodsNo = strGoodsNo;
	}

	public List<Dconsumeinfo> getLsDconsumeinfo() {
		return lsDconsumeinfo;
	}

	public void setLsDconsumeinfo(List<Dconsumeinfo> lsDconsumeinfo) {
		this.lsDconsumeinfo = lsDconsumeinfo;
	}	
	
}
