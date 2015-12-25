package com.amani.action.AdvancedOperations;

import java.io.OutputStream;
import java.util.List;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.model.MemberOrderInfo;
import com.amani.model.Projectinfo;
import com.amani.model.Staffinfo;
import com.amani.service.AdvancedOperations.AC014Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ac014")
public class AC014Action {
	@Autowired
	private AC014Service ac014Service;
	private String strCurCompId;
	private String strCurCompName;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private List<MemberOrderInfo> lsDataSet;
	private List<MemberOrderInfo> lsFactDataSet;
	private int    ordersid;
	private	String strMemberPhone;
	private String orderfactdate;
	private String orderfacttime;
	private String orderfactproject;
	private String orderfactempid;
	private	String orderconfirmmsg;
	private String orderdetail;
	private List<Staffinfo> lsStaffinfoDown;
	private List<Projectinfo> lsProjectinfoDown;
	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 								@Result( type="json", name="load_failure")})
	public String loadDataSet() {
		
		this.lsDataSet=this.ac014Service.loadDateSetByCompId(strCurCompId,strFromDate,strToDate);
		this.lsFactDataSet=this.ac014Service.loadFactDateSetByCompId(strCurCompId,strFromDate,strToDate);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action( value="confirmMsg", results={ @Result( type="json", name="load_success"),
				@Result( type="json", name="load_failure")})
	public String confirmMsg()
	{
		try
		{
			this.strMessage="";
			if(orderfactdate.equals(""))
				orderfactdate=CommonTool.getCurrDate();
			if(Integer.parseInt(CommonTool.setDateMask(orderfactdate))<Integer.parseInt(CommonTool.getCurrDate()))
			{
				strMessage="预约日期不能在今天以前!";
				return SystemFinal.LOAD_SUCCESS;
			}
			boolean flag=this.ac014Service.postFactOrders(ordersid, orderfactdate, orderfacttime, orderfactproject, orderfactempid, orderconfirmmsg,orderdetail);
			if(flag==true)
			{
				if(!orderconfirmmsg.equals("") && !strMemberPhone.equals(""))
				{
					String sendmsg=this.ac014Service.sendMsg(strMemberPhone, orderconfirmmsg);
					System.out.println(sendmsg);
				}
			}
			return SystemFinal.LOAD_SUCCESS;
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_SUCCESS;
		}
	}
	
	
	@Action( value="loadCurCompInfo", results={ @Result( type="json", name="load_success"),
			@Result( type="json", name="load_failure")})
	public String loadCurCompInfo()
	{
		try
		{
			lsStaffinfoDown=this.ac014Service.getDataTool().loadEmpsByCompId(this.strCurCompId,2);
			lsProjectinfoDown=this.ac014Service.getDataTool().loadProjectinfoByCompId(this.strCurCompId,1);
			return SystemFinal.LOAD_SUCCESS;
		}catch(Exception ex)
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
	public List<MemberOrderInfo> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<MemberOrderInfo> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	public String getStrCurCompName() {
		return strCurCompName;
	}
	public void setStrCurCompName(String strCurCompName) {
		this.strCurCompName = strCurCompName;
	}
	
	@JSON(serialize=false)
	public AC014Service getAc014Service() {
		return ac014Service;
	}

	@JSON(serialize=false)
	public void setAc014Service(AC014Service ac014Service) {
		this.ac014Service = ac014Service;
	}



	public String getStrMemberPhone() {
		return strMemberPhone;
	}



	public void setStrMemberPhone(String strMemberPhone) {
		this.strMemberPhone = strMemberPhone;
	}



	public String getOrderfactdate() {
		return orderfactdate;
	}



	public void setOrderfactdate(String orderfactdate) {
		this.orderfactdate = orderfactdate;
	}



	public String getOrderfacttime() {
		return orderfacttime;
	}



	public void setOrderfacttime(String orderfacttime) {
		this.orderfacttime = orderfacttime;
	}



	public String getOrderfactproject() {
		return orderfactproject;
	}



	public void setOrderfactproject(String orderfactproject) {
		this.orderfactproject = orderfactproject;
	}



	public String getOrderfactempid() {
		return orderfactempid;
	}



	public void setOrderfactempid(String orderfactempid) {
		this.orderfactempid = orderfactempid;
	}



	public String getOrderconfirmmsg() {
		return orderconfirmmsg;
	}



	public void setOrderconfirmmsg(String orderconfirmmsg) {
		this.orderconfirmmsg = orderconfirmmsg;
	}


	public int getOrdersid() {
		return ordersid;
	}


	public void setOrdersid(int ordersid) {
		this.ordersid = ordersid;
	}


	public List<MemberOrderInfo> getLsFactDataSet() {
		return lsFactDataSet;
	}


	public void setLsFactDataSet(List<MemberOrderInfo> lsFactDataSet) {
		this.lsFactDataSet = lsFactDataSet;
	}


	public String getOrderdetail() {
		return orderdetail;
	}


	public void setOrderdetail(String orderdetail) {
		this.orderdetail = orderdetail;
	}


	public List<Staffinfo> getLsStaffinfoDown() {
		return lsStaffinfoDown;
	}


	public void setLsStaffinfoDown(List<Staffinfo> lsStaffinfoDown) {
		this.lsStaffinfoDown = lsStaffinfoDown;
	}


	public List<Projectinfo> getLsProjectinfoDown() {
		return lsProjectinfoDown;
	}


	public void setLsProjectinfoDown(List<Projectinfo> lsProjectinfoDown) {
		this.lsProjectinfoDown = lsProjectinfoDown;
	}

	
	
}
