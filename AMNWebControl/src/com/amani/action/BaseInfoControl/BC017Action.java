package com.amani.action.BaseInfoControl;

import java.util.List;



import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;
import com.amani.bean.Shopownerinfo;


import com.amani.service.BaseInfoControl.BC017Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/bc017")
public class BC017Action extends AMN_ModuleAction{
	@Autowired
	private BC017Service bc017Service;
	private String strJsonParam;	
    private String strCurCompId;
    private String strCurCompName;
    private String strCurEmpId;
    private String strCurEmpName;
    private	String strCurEmpInid;
    private String strCurProjectId;
    private String strMonth;
    private int		nstafftype;
    private List<Shopownerinfo> lsShopownerinfo01;
    private List<Shopownerinfo> lsShopownerinfo02;
    private List<Shopownerinfo> lsShopownerinfo03;
    private List<Shopownerinfo> lsShopownerinfo04;
    private List<Shopownerinfo> lsShopownerinfo05;
    private List<Shopownerinfo> lsShopownerinfo06;
    private List<Shopownerinfo> lsShopownerinfo07;
    private List<Shopownerinfo> lsShopownerinfo08;
    private List<Shopownerinfo> lsShopownerinfo09;
    private List<Shopownerinfo> lsShopownerinfo10;
    private List<Shopownerinfo> lsShopownerinfo11;
    private List<Shopownerinfo> lsShopownerinfo12;
    @JSON(serialize=false)
	public BC017Service getBc017Service() {
		return bc017Service;
	}
    @JSON(serialize=false)
	public void setBc017Service(BC017Service bc017Service) {
		this.bc017Service = bc017Service;
	}
	public String getStrCurCompId() {
		return strCurCompId;
	}
	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}
	public String getStrCurProjectId() {
		return strCurProjectId;
	}
	public void setStrCurProjectId(String strCurProjectId) {
		this.strCurProjectId = strCurProjectId;
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
		return true;
	}
	@Override
	protected boolean beforeAdd() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforeDelete() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	protected boolean beforeLoad() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected boolean beforePost() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	protected boolean deleteActive() {
		// TODO Auto-generated method stub
		return true;
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
	
	public String getStrJsonParam() {
		return strJsonParam;
	}
	public void setStrJsonParam(String strJsonParam) {
		this.strJsonParam = strJsonParam;
	}

	@Override
	public String getStrMessage() {
		// TODO Auto-generated method stub
		return super.getStrMessage();
	}
	@Override
	public void setStrMessage(String strMessage) {
		// TODO Auto-generated method stub
		super.setStrMessage(strMessage);
	}
	

	@Action(value = "validateShandcompid",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateShandcompid()
	{
		try
		{
			this.strMessage="";
			this.strCurCompName=this.bc017Service.getDataTool().loadCompNameById(this.strCurCompId);
			if(CommonTool.FormatString(this.strCurCompName).equals(""))
			{
				this.strMessage="门店编号不存在!";
				return SystemFinal.LOAD_SUCCESS;
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "validateShopWoner",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateShopWoner()
	{
		try
		{
			this.strMessage="";
			StringBuffer validateMsg=new StringBuffer();
			StringBuffer staffnamebuf=new StringBuffer();
			StringBuffer staffmanagerno=new StringBuffer();
			boolean flag=this.bc017Service.getDataTool().loadEmpInidNameById(this.strCurCompId, this.strCurEmpId, validateMsg,staffnamebuf,staffmanagerno);
			if(flag==false)
				this.strMessage=validateMsg.toString();
			else
			{
				this.strCurEmpName=staffnamebuf.toString();
				strCurEmpInid=staffmanagerno.toString();
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	
	
	@Action(value = "createStoreWonerInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String createStoreWonerInfo()
	{
		try
		{
			this.strMessage="";
			if(this.bc017Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX016")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.bc017Service.checkWoner(strCurCompId, strCurEmpInid, strMonth);
			if(flag==false)
			{
				this.strMessage="该员工已经在本店当月设置了店长信息,请确认!";
				return SystemFinal.LOAD_SUCCESS;
			}
			flag=this.bc017Service.postStoreSetInfo(strCurCompId, strCurEmpId, strCurEmpName, strCurEmpInid, strMonth, nstafftype);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	
	@Action(value = "loadCurYearShoperInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadCurYearShoperInfo()
	{
		try
		{
			lsShopownerinfo01=this.bc017Service.loadShopownerinfoByMonth(CommonTool.getCurrDate().substring(0,4)+"01");
			lsShopownerinfo02=this.bc017Service.loadShopownerinfoByMonth(CommonTool.getCurrDate().substring(0,4)+"02");
			lsShopownerinfo03=this.bc017Service.loadShopownerinfoByMonth(CommonTool.getCurrDate().substring(0,4)+"03");
			lsShopownerinfo04=this.bc017Service.loadShopownerinfoByMonth(CommonTool.getCurrDate().substring(0,4)+"04");
			lsShopownerinfo05=this.bc017Service.loadShopownerinfoByMonth(CommonTool.getCurrDate().substring(0,4)+"05");
			lsShopownerinfo06=this.bc017Service.loadShopownerinfoByMonth(CommonTool.getCurrDate().substring(0,4)+"06");
			lsShopownerinfo07=this.bc017Service.loadShopownerinfoByMonth(CommonTool.getCurrDate().substring(0,4)+"07");
			lsShopownerinfo08=this.bc017Service.loadShopownerinfoByMonth(CommonTool.getCurrDate().substring(0,4)+"08");
			lsShopownerinfo09=this.bc017Service.loadShopownerinfoByMonth(CommonTool.getCurrDate().substring(0,4)+"09");
			lsShopownerinfo10=this.bc017Service.loadShopownerinfoByMonth(CommonTool.getCurrDate().substring(0,4)+"10");
			lsShopownerinfo11=this.bc017Service.loadShopownerinfoByMonth(CommonTool.getCurrDate().substring(0,4)+"11");
			lsShopownerinfo12=this.bc017Service.loadShopownerinfoByMonth(CommonTool.getCurrDate().substring(0,4)+"12");
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE; 
		}
	}
	
	@Action(value = "deleteShopWoner",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String deleteShopWoner()
	{
		try
		{
			this.strMessage="";
			if(this.bc017Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX016")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.bc017Service.deleteStoreSetInfo(strCurCompId, strCurEmpInid, strMonth);
			if(flag==false)
			{
				this.strMessage="删除店长信息失败!";
				return SystemFinal.LOAD_SUCCESS;
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;  
		}
	}
	
	public String getStrCurCompName() {
		return strCurCompName;
	}
	public void setStrCurCompName(String strCurCompName) {
		this.strCurCompName = strCurCompName;
	}
	public String getStrCurEmpId() {
		return strCurEmpId;
	}
	public void setStrCurEmpId(String strCurEmpId) {
		this.strCurEmpId = strCurEmpId;
	}
	public String getStrCurEmpName() {
		return strCurEmpName;
	}
	public void setStrCurEmpName(String strCurEmpName) {
		this.strCurEmpName = strCurEmpName;
	}
	public String getStrCurEmpInid() {
		return strCurEmpInid;
	}
	public void setStrCurEmpInid(String strCurEmpInid) {
		this.strCurEmpInid = strCurEmpInid;
	}
	public int getNstafftype() {
		return nstafftype;
	}
	public void setNstafftype(int nstafftype) {
		this.nstafftype = nstafftype;
	}
	public String getStrMonth() {
		return strMonth;
	}
	public void setStrMonth(String strMonth) {
		this.strMonth = strMonth;
	}
	public List<Shopownerinfo> getLsShopownerinfo01() {
		return lsShopownerinfo01;
	}
	public void setLsShopownerinfo01(List<Shopownerinfo> lsShopownerinfo01) {
		this.lsShopownerinfo01 = lsShopownerinfo01;
	}
	public List<Shopownerinfo> getLsShopownerinfo02() {
		return lsShopownerinfo02;
	}
	public void setLsShopownerinfo02(List<Shopownerinfo> lsShopownerinfo02) {
		this.lsShopownerinfo02 = lsShopownerinfo02;
	}
	public List<Shopownerinfo> getLsShopownerinfo03() {
		return lsShopownerinfo03;
	}
	public void setLsShopownerinfo03(List<Shopownerinfo> lsShopownerinfo03) {
		this.lsShopownerinfo03 = lsShopownerinfo03;
	}
	public List<Shopownerinfo> getLsShopownerinfo04() {
		return lsShopownerinfo04;
	}
	public void setLsShopownerinfo04(List<Shopownerinfo> lsShopownerinfo04) {
		this.lsShopownerinfo04 = lsShopownerinfo04;
	}
	public List<Shopownerinfo> getLsShopownerinfo05() {
		return lsShopownerinfo05;
	}
	public void setLsShopownerinfo05(List<Shopownerinfo> lsShopownerinfo05) {
		this.lsShopownerinfo05 = lsShopownerinfo05;
	}
	public List<Shopownerinfo> getLsShopownerinfo06() {
		return lsShopownerinfo06;
	}
	public void setLsShopownerinfo06(List<Shopownerinfo> lsShopownerinfo06) {
		this.lsShopownerinfo06 = lsShopownerinfo06;
	}
	public List<Shopownerinfo> getLsShopownerinfo07() {
		return lsShopownerinfo07;
	}
	public void setLsShopownerinfo07(List<Shopownerinfo> lsShopownerinfo07) {
		this.lsShopownerinfo07 = lsShopownerinfo07;
	}
	public List<Shopownerinfo> getLsShopownerinfo08() {
		return lsShopownerinfo08;
	}
	public void setLsShopownerinfo08(List<Shopownerinfo> lsShopownerinfo08) {
		this.lsShopownerinfo08 = lsShopownerinfo08;
	}
	public List<Shopownerinfo> getLsShopownerinfo09() {
		return lsShopownerinfo09;
	}
	public void setLsShopownerinfo09(List<Shopownerinfo> lsShopownerinfo09) {
		this.lsShopownerinfo09 = lsShopownerinfo09;
	}
	public List<Shopownerinfo> getLsShopownerinfo10() {
		return lsShopownerinfo10;
	}
	public void setLsShopownerinfo10(List<Shopownerinfo> lsShopownerinfo10) {
		this.lsShopownerinfo10 = lsShopownerinfo10;
	}
	public List<Shopownerinfo> getLsShopownerinfo11() {
		return lsShopownerinfo11;
	}
	public void setLsShopownerinfo11(List<Shopownerinfo> lsShopownerinfo11) {
		this.lsShopownerinfo11 = lsShopownerinfo11;
	}
	public List<Shopownerinfo> getLsShopownerinfo12() {
		return lsShopownerinfo12;
	}
	public void setLsShopownerinfo12(List<Shopownerinfo> lsShopownerinfo12) {
		this.lsShopownerinfo12 = lsShopownerinfo12;
	}
   
	
}
