package com.amani.action.InvoicingControl;

import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.model.Dgoodsbarinfo;
import com.amani.model.Dgoodsinsert;
import com.amani.service.InvoicingControl.IC015Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ic015")
public class IC015Action {
	@Autowired
	private IC015Service ic015Service;
	private String 	strCurCompId;
	private String 	strGoodsNo;
	private String 	strGoodsBarNo;
	private String  strToGoodsBarNo;
	private String  strParams;
	public String getStrParams() {
		return strParams;
	}

	public void setStrParams(String strParams) {
		this.strParams = strParams;
	}

	public boolean isbRet() {
		return bRet;
	}

	public void setbRet(boolean bRet) {
		this.bRet = bRet;
	}

	private boolean bRet;
	public String getStrToGoodsBarNo() {
		return strToGoodsBarNo;
	}

	public void setStrToGoodsBarNo(String strToGoodsBarNo) {
		this.strToGoodsBarNo = strToGoodsBarNo;
	}

	private int	 	iBarState;
	private String 	strMessage;
	private List<Dgoodsbarinfo> lsDataSet;

	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {

		this.lsDataSet=this.ic015Service.loadDateSetByCompId(strCurCompId, strGoodsNo,strGoodsBarNo,iBarState);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action( value="checkBarInfo", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String checkBarInfo()
	{
		bRet=false;
		bRet=ic015Service.checkBarInfo(strGoodsBarNo, strToGoodsBarNo);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action( value="post", results={ @Result( type="json", name="post_success"),
			 @Result( type="json", name="post_failure")})
	public String post()
	{
		try {
			List<Dgoodsinsert> lsDgoodsinserts=ic015Service.getDataTool().loadDTOList(strParams, Dgoodsinsert.class);
			bRet=ic015Service.updProdDate(lsDgoodsinserts);
		} catch (Exception e) {
			e.printStackTrace();
			bRet=false;
		}
		return SystemFinal.POST_SUCCESS;
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
	public IC015Service getIc015Service() {
		return ic015Service;
	}
	@JSON(serialize=false)
	public void setIc015Service(IC015Service ic015Service) {
		this.ic015Service = ic015Service;
	}


	public void setLsDataSet(List<Dgoodsbarinfo> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}

	public String getStrGoodsNo() {
		return strGoodsNo;
	}

	public void setStrGoodsNo(String strGoodsNo) {
		this.strGoodsNo = strGoodsNo;
	}

	public String getStrGoodsBarNo() {
		return strGoodsBarNo;
	}

	public void setStrGoodsBarNo(String strGoodsBarNo) {
		this.strGoodsBarNo = strGoodsBarNo;
	}

	public List<Dgoodsbarinfo> getLsDataSet() {
		return lsDataSet;
	}

	public int getIBarState() {
		return iBarState;
	}

	public void setIBarState(int barState) {
		iBarState = barState;
	}

	
	
}
