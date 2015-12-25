package com.amani.action.BOOSREP;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ReportAction;
import com.amani.bean.BS001Bean;
import com.amani.service.BOOSREP.BS001Service;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/bs001")
public class BS001Action extends AMN_ReportAction {
	private String strCompId;
	private String strFromDate;
	private String strToDate;
	@Autowired
	private BS001Service bs001Service;
	private List<BS001Bean> lsBeans;
	private String strMessage;
	public String getStrCompId() {
		return strCompId;
	}
	public void setStrCompId(String strCompId) {
		this.strCompId = strCompId;
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
	public List<BS001Bean> getLsBeans() {
		return lsBeans;
	}
	public void setLsBeans(List<BS001Bean> lsBeans) {
		this.lsBeans = lsBeans;
	}
	public String getStrMessage() {
		return strMessage;
	}
	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}
	public void setBs001Service(BS001Service bs001Service) {
		this.bs001Service = bs001Service;
	}
	
	@Action(value = "query",  results = {@Result(name = "load_success", type = "json"),
			@Result(name = "load_failure", type = "json")	
    })
	public String query()
	{
		lsBeans=bs001Service.loadBS001(strCompId, strFromDate, strToDate);
		if(lsBeans==null || lsBeans.size()<1)
		{
			strMessage="没有查询到数据";
			return SystemFinal.LOAD_FAILURE;
		}
		return SystemFinal.LOAD_SUCCESS;
	}
}
