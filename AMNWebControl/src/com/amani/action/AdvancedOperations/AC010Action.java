package com.amani.action.AdvancedOperations;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.Call;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import com.amani.action.AMN_ModuleAction;
import com.amani.bean.CallingAdminBean;
import com.amani.bean.CallsWatingBean;
import com.amani.model.Companyinfo;
import com.amani.model.Intentiondetail;
import com.amani.model.Staffinfo;
import com.amani.service.AdvancedOperations.AC002Service;
import com.amani.service.AdvancedOperations.AC009Service;
import com.amani.service.AdvancedOperations.AC010Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ac010")
public class AC010Action extends AMN_ModuleAction{
	@Autowired
	private  AC010Service ac010Service;
	private List<CallingAdminBean> lsCallingAdminBean;
	private List<CallsWatingBean> lsCallsWatingBean;
   
	/**
	 * 生成二级目录
	 * @return
	 * @throws Exception
	 */
	@Action(value = "loadbybillid",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String loadbybillid() throws Exception
	{
		try {
			this.lsCallingAdminBean=this.ac010Service.selectbycallbillid(calltime);
		} catch (Exception e) {
		}
		return "load_success";
	}
	/**
	 * 录音查询
	 * @return
	 * @throws Exception
	 */
	@Action(value = "selectlu",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String selectlu() throws Exception
	{
		try {
			this.lsCallingAdminBean=this.ac010Service.selectlu(calltime,calltimes, callon, calledon,calltype);
		} catch (Exception e) {
		}
		return "load_success";
	}
	/**
	 * 显示加载日期
	 * @return
	 * @throws Exception
	 */
	@Action(value = "selectrecord",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String selectrecord() throws Exception
	{
		try {
			this.lsCallingAdminBean=this.ac010Service.selectludata();
		} catch (Exception e) {
		}
		return "load_success";
	}
	@Action(value = "selectrecords",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String selectrecords() throws Exception
	{
		try {
			this.lsCallingAdminBean=this.ac010Service.getDataTool().loadDTOList(strJsonParam,Intentiondetail.class);
			 if(lsCallingAdminBean!=null && lsCallingAdminBean.size()>0)
				{
				 this.lsCallingAdminBean=this.ac010Service.selectludatas(calltime,callbillid, lsCallingAdminBean);
				}
		} catch (Exception e) {
		}
		return "load_success";
	}
	/**
	 * 加载录音日期
	 * @return
	 * @throws Exception
	 */
	@Action(value = "selectludate",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_faile", type = "json")	   })
	public String selectludate() throws Exception
	{
		try {
			 this.lsCallingAdminBean=this.ac010Service.getDataTool().loadDTOList(strJsonParam,Intentiondetail.class);
			 if(lsCallingAdminBean!=null && lsCallingAdminBean.size()>0)
				{
				 this.lsCallingAdminBean=this.ac010Service.loadFileInfo(lsCallingAdminBean);
				}
		} catch (Exception e) {
		}
		return "load_success";
	}
	@Override
	protected boolean addActive() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean afterAdd() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean afterDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean afterLoad() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean afterPost() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforeAdd() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforeDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforeLoad() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforePost() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean deleteActive() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean loadActive() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean postActive() {
		// TODO Auto-generated method stub
		return false;
	}
	@JSON(serialize=false)
	public AC010Service getAc010Service() {
		return ac010Service;
	}
	@JSON(serialize=false)
	public void setAc010Service(AC010Service ac010Service) {
		this.ac010Service = ac010Service;
	}
	public List<CallingAdminBean> getLsCallingAdminBean() {
		return lsCallingAdminBean;
	}
	public void setLsCallingAdminBean(List<CallingAdminBean> lsCallingAdminBean) {
		this.lsCallingAdminBean = lsCallingAdminBean;
	}

	private String strJsonParam;

	public String getStrJsonParam() {
		return strJsonParam;
	}
	public void setStrJsonParam(String strJsonParam) {
		this.strJsonParam = strJsonParam;
	}
	private String calltime;
	public String getCalltime() {
		return calltime;
	}
	public void setCalltime(String calltime) {
		this.calltime = calltime;
	}
	private String callbillid;
	public String getCallbillid() {
		return callbillid;
	}
	public void setCallbillid(String callbillid) {
		this.callbillid = callbillid;
	}
	private String callon;
	private String calledon;
	private String callhoues;
	public String getCallon() {
		return callon;
	}
	public void setCallon(String callon) {
		this.callon = callon;
	}
	public String getCalledon() {
		return calledon;
	}
	public void setCalledon(String calledon) {
		this.calledon = calledon;
	}
	public String getCallhoues() {
		return callhoues;
	}
	public void setCallhoues(String callhoues) {
		this.callhoues = callhoues;
	}
	private String calltimes;

	public String getCalltimes() {
		return calltimes;
	}
	public void setCalltimes(String calltimes) {
		this.calltimes = calltimes;
	}
	private String membername;
	private String ordertime;
	private String calluserid;
	private String calltype;
	
	public String getCalltype() {
		return calltype;
	}
	public void setCalltype(String calltype) {
		this.calltype = calltype;
	}
	public List<CallsWatingBean> getLsCallsWatingBean() {
		return lsCallsWatingBean;
	}
	public void setLsCallsWatingBean(List<CallsWatingBean> lsCallsWatingBean) {
		this.lsCallsWatingBean = lsCallsWatingBean;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public String getCalluserid() {
		return calluserid;
	}
	public void setCalluserid(String calluserid) {
		this.calluserid = calluserid;
	}
	private String callons;

	public String getCallons() {
		return callons;
	}
	public void setCallons(String callons) {
		this.callons = callons;
	}
	
}
