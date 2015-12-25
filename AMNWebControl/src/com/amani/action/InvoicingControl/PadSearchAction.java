package com.amani.action.InvoicingControl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.apache.struts2.json.annotations.JSON;
import com.amani.bean.SendBillInfo;
import com.amani.service.InvoicingControl.PadSearchService;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

/**
 * 
 * @author LiuJie Jun 24, 2013 2:35:21 PM
 * @version: 1.0
 * @Copyright: AMN
 */
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/padSearch")
public class PadSearchAction {
	
	@Autowired
	private PadSearchService padSearchService;
	private List<SendBillInfo> lsSendBillInfo=new ArrayList();
	private Map  lsSearComps;
	private Map  lsSearchBills;
	private String strSearchCompId;
	private String strSearchBillId;
	private String strSearchStoreId;
	private String validateMessage="";
	public String getValidateMessage() {
		return validateMessage;
	}

	public void setValidateMessage(String validateMessage) {
		this.validateMessage = validateMessage;
	}

	public String getStrSearchStoreId() {
		return strSearchStoreId;
	}

	public void setStrSearchStoreId(String strSearchStoreId) {
		this.strSearchStoreId = strSearchStoreId;
	}

	@Action(value = "padInterfaceLogin", results = { @Result(name = "load_success", location = "/index.jsp"),
				@Result(name = "load_failure", location = "/Login.jsp")}) 
	public String padInterfaceLogin()
	{
		try
		{
			this.lsSearComps=this.padSearchService.loadSendBillComps();
			
			if(lsSearComps!=null && lsSearComps.size()>0)
			{
				Set<String> keySet=lsSearComps.keySet();
				this.lsSearchBills=this.padSearchService.loadSendBillsByComp(keySet.toArray()[0].toString());
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "padInterfaceChangeStore",  results = { 
			 @Result(name = "load_success", type = "json"),
             @Result(name = "load_failure", type = "json")	
           }) 
	public String padInterfaceChangeStore()
	{
		try
		{
			this.lsSearchBills=this.padSearchService.loadSendBillsByStore(strSearchStoreId);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action( value = "padInterfaceChangeComp", 
			 results = { 
						 @Result(name = "load_success", type = "json"),
			             @Result(name = "load_failure", type = "json")	
			           }
	)
	public String padInterfaceChangeComp()
	{
		this.lsSearchBills=this.padSearchService.loadSendBillsByComp(strSearchCompId);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action( value = "padInterfaceShowDetial", 
			 results = { 
						 @Result(name = "load_success", type = "json"),
			             @Result(name = "load_failure", type = "json")	
			           }
	)
	public String padInterfaceShowDetial()
	{
		this.lsSendBillInfo=this.padSearchService.loadDetialByBillId(strSearchBillId);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action( value = "padInterfacehHandTagert", 
			 results = { 
						 @Result(name = "load_success", type = "json"),
			             @Result(name = "load_failure", type = "json")	
			           }
	)
	public String padInterfacehHandTagert()
	{
		validateMessage="";
		boolean flag=this.padSearchService.handSendBillState(this.strSearchBillId);
		if(flag==false)
		{
			validateMessage="操作失败!";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	public String getStrSearchCompId() {
		return strSearchCompId;
	}
	public void setStrSearchCompId(String strSearchCompId) {
		this.strSearchCompId = strSearchCompId;
	}
	public String getStrSearchBillId() {
		return strSearchBillId;
	}
	public void setStrSearchBillId(String strSearchBillId) {
		this.strSearchBillId = strSearchBillId;
	}
	@JSON(serialize=false)
	public PadSearchService getPadSearchService() {
		return padSearchService;
	}
	@JSON(serialize=false)
	public void setPadSearchService(PadSearchService padSearchService) {
		this.padSearchService = padSearchService;
	}
	public List<SendBillInfo> getLsSendBillInfo() {
		return lsSendBillInfo;
	}
	public void setLsSendBillInfo(List<SendBillInfo> lsSendBillInfo) {
		this.lsSendBillInfo = lsSendBillInfo;
	}

	public Map getLsSearComps() {
		return lsSearComps;
	}

	public void setLsSearComps(Map lsSearComps) {
		this.lsSearComps = lsSearComps;
	}

	public Map getLsSearchBills() {
		return lsSearchBills;
	}

	public void setLsSearchBills(Map lsSearchBills) {
		this.lsSearchBills = lsSearchBills;
	}



}
