package com.amani.action.BaseInfoControl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.lang.xwork.ObjectUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;
import com.amani.bean.CommonBean;
import com.amani.bean.WZGoods;
import com.amani.bean.WZResult;

import com.amani.model.Cardchangerule;
import com.amani.model.CardchangeruleId;
import com.amani.model.Cardtypeinfo;
import com.amani.model.CardtypeinfoId;
import com.amani.model.Compwarehouse;
import com.amani.model.Goodsinfo;
import com.amani.model.GoodsinfoId;
import com.amani.model.Projectinfo;
import com.amani.model.ProjectinfoId;

import com.amani.model.Syscommoninfomode;
import com.amani.service.BaseInfoControl.BC013Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/bc013")
public class BC013Action extends AMN_ModuleAction{
	@Autowired
	private BC013Service bc013Service;
	private String strJsonParam;	
    private String strCurCompId;
    private int strCurModeType; //当前模板性质
    private String strCurModeId;//输入模板编号
    private String strProjectId;//当前项目编号
    private String strProjectSource;//当前项目归属
    private String strGoodstId;//当前产品编号
    private String strGoodsSource;//当前产品归属
    private String strCardTypetId;//当前卡类型编号
    private String strCardTypeSource;//当前卡类型归属
    private String targerModeId;//下发到的模板
    private List<Syscommoninfomode> lsinfomodes;
    private List<Projectinfo> 	lsProjectinfo;
    private List<Goodsinfo> 	lsGoodsinfo;
    private List<Cardtypeinfo> lsCardtypeinfo;
    private Syscommoninfomode curInfoMode;
    private List<Syscommoninfomode> lsparentModes;
    private Projectinfo 	curProjectinfo;
    private Goodsinfo   	curGoodsinfo;
    private Cardtypeinfo 	curCardtypeinfo;
    private String factparentmodeid;
	private List<Compwarehouse> lsCompwarehouses;
	
    private List<Cardchangerule> lsCardchangerules;
    private List<WZGoods> wzGoods;
    private List<CommonBean> commoninfo;
	
	public List<CommonBean> getCommoninfo() {
		return commoninfo;
	}
	public void setCommoninfo(List<CommonBean> commoninfo) {
		this.commoninfo = commoninfo;
	}
	public List<WZGoods> getWzGoods() {
		return wzGoods;
	}
	public void setWzGoods(List<WZGoods> wzGoods) {
		this.wzGoods = wzGoods;
	}
	public List<Cardchangerule> getLsCardchangerules() {
		return lsCardchangerules;
	}
	public void setLsCardchangerules(List<Cardchangerule> lsCardchangerules) {
		this.lsCardchangerules = lsCardchangerules;
	}
	public String getFactparentmodeid() {
		return factparentmodeid;
	}
	public void setFactparentmodeid(String factparentmodeid) {
		this.factparentmodeid = factparentmodeid;
	}
	public List<Syscommoninfomode> getLsparentModes() {
		return lsparentModes;
	}
	public void setLsparentModes(List<Syscommoninfomode> lsparentModes) {
		this.lsparentModes = lsparentModes;
	}
	public Syscommoninfomode getCurInfoMode() {
		return curInfoMode;
	}
	public void setCurInfoMode(Syscommoninfomode curInfoMode) {
		this.curInfoMode = curInfoMode;
	}
	public String getStrCurCompId() {
		return strCurCompId;
	}
	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}
	@JSON(serialize=false)
	public BC013Service getBc013Service() {
		return bc013Service;
	}
    @JSON(serialize=false)
	public void setBc013Service(BC013Service bc013Service) {
		this.bc013Service = bc013Service;
	}

	public List<Syscommoninfomode> getLsinfomodes() {
		return lsinfomodes;
	}
	public void setLsinfomodes(List<Syscommoninfomode> lsinfomodes) {
		this.lsinfomodes = lsinfomodes;
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
	
	@Action(value = "loadInfoModes",  results = { 
			 @Result(name = "load_success", type = "json"),
    @Result(name = "load_failure", type = "json")	
	}) 
	public String loadInfoModes()
	{
		if(CommonTool.FormatString(this.strCurCompId).equals(""))
		{
			this.lsinfomodes=new ArrayList();
		}
		else
		{
			lsinfomodes=this.bc013Service.loadInfoModesByCompId(strCurCompId);
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
    @Action(value = "loadParentMode",  results = { 
			 @Result(name = "load_success", type = "json"),
    @Result(name = "load_failure", type = "json")	
	}) 
	public String loadParentMode()
	{
		if(  CommonTool.FormatString(this.strCurCompId).equals("") )
		{
			this.lsparentModes=new ArrayList();
		}
		else
		{
			lsparentModes=this.bc013Service.loadInfoModesByTypeId(strCurCompId,strCurModeType);
		}
		return SystemFinal.LOAD_SUCCESS;
	}
    
    
   
    @Action(value = "validateModeId",  results = { 
			 @Result(name = "load_success", type = "json"),
    @Result(name = "load_failure", type = "json")	
	}) 
    public String validateModeId()
    {
    	this.strMessage="";
    	boolean flag=this.bc013Service.validateModeId(this.strCurModeId);
    	if(flag==false)
    	{
    		this.strMessage=" 该模板编号已经存在,请确认!";
    	}
		return SystemFinal.LOAD_SUCCESS;
    }
	
    @Action(value = "post",  results = { 
			 @Result(name = "post_success", type = "json"),
           @Result(name = "post_failure", type = "json")	
        }) 
	@Override
	public String post()
	{
		this.strMessage="";
		if(this.bc013Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC013", SystemFinal.UR_POST)==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		if(!CommonTool.FormatString(this.factparentmodeid).equals(""))
		{
			this.curInfoMode.setParentmodeid(factparentmodeid);
			this.curInfoMode.setCreateemp(CommonTool.getLoginInfo("USERID"));
			this.curInfoMode.setCreatedate(CommonTool.getCurrDate());
		}
		curInfoMode.setCreatedate(CommonTool.setDateMask(curInfoMode.getCreatedate()));
		boolean flag=this.bc013Service.post(this.curInfoMode, null);
		if(flag==false)
		{
			this.strMessage=SystemFinal.POST_FAILURE_MSG;
			return SystemFinal.POST_FAILURE;
		}
		curInfoMode=null;
		return SystemFinal.POST_SUCCESS;
	}
    
    @Action(value = "delete",  results = { 
			 @Result(name = "delete_success", type = "json"),
			 @Result(name = "delete_failure", type = "json")	
       }) 
	@Override
	public String delete()
	{
		this.strMessage="";
		if(this.bc013Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC013", SystemFinal.UR_DELETE)==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.DELETE_FAILURE;
		}
		Syscommoninfomode obj=new Syscommoninfomode();
		obj.setBmodeid(this.strCurModeId);
		boolean flag=this.bc013Service.delete(obj);
		obj=null;
		if(flag==false)
		{
			this.strMessage=SystemFinal.DELETE_FAILURE_MSG;
			return SystemFinal.DELETE_FAILURE;
		}
		return SystemFinal.DELETE_SUCCESS;
	}
    
    // 根据模板编号获取项目明细
    @Action(value = "loadSysModesinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
    public String loadSysModesinfo()
    {
    
    	this.lsProjectinfo=this.bc013Service.loadProjectinfo(this.strCurCompId, this.strCurModeId);
    	this.lsGoodsinfo=this.bc013Service.loadGoodsinfo(this.strCurCompId, this.strCurModeId);
    	this.lsCardtypeinfo=this.bc013Service.loadCardtypeinfo(this.strCurCompId, this.strCurModeId);
    	this.lsCompwarehouses=this.bc013Service.getDataTool().loadCompWareById(strCurCompId);
    	return SystemFinal.LOAD_SUCCESS;
    }
    //-------------------------------项目模板-------------------------------------
    @Action(value = "loadProjectinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadProjectinfo()
	{
		if(  CommonTool.FormatString(this.strCurModeId).equals("") )
		{
			this.curProjectinfo=new Projectinfo();
		}
		else
		{
			curProjectinfo=this.bc013Service.loadProjectinfoById(strCurCompId,this.strCurModeId,this.strProjectId);
		}
		return SystemFinal.LOAD_SUCCESS;
	}
   
    @Action(value = "postProjectInfo",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	
      }) 
	public String postProjectInfo()
	{
		this.strMessage="";
		if(this.bc013Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC013", SystemFinal.UR_POST)==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		curProjectinfo.getId().setPrjmodeId(this.strCurModeId);
		boolean flag=this.bc013Service.postProject(this.curProjectinfo);
		if(flag==false)
		{
			this.strMessage=SystemFinal.POST_FAILURE_MSG;
			return SystemFinal.POST_FAILURE;
		}
		return SystemFinal.POST_SUCCESS;
	}
  
   	@Action(value = "deleteProjectInfo",  results = { 
			 @Result(name = "delete_success", type = "json"),
			 @Result(name = "delete_failure", type = "json")	
     }) 
	public String deleteProjectInfo()
	{
		this.strMessage="";
		if(this.bc013Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC013", SystemFinal.UR_DELETE)==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.DELETE_FAILURE;
		}
		Projectinfo obj =new Projectinfo();
		obj.setId(new ProjectinfoId(this.strCurModeId,this.strProjectId,this.strProjectSource));
		boolean flag=this.bc013Service.deleteProject(obj);
		obj=null;
		if(flag==false)
		{
			this.strMessage=SystemFinal.DELETE_FAILURE_MSG;
			return SystemFinal.DELETE_FAILURE;
		}
		return SystemFinal.DELETE_SUCCESS;
	}
   
   	@Action(value = "validateProjectinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String validateProjectinfo()
	{
		this.strMessage="";
		boolean flag=this.bc013Service.validateProjectById(strCurCompId,this.strCurModeId,this.strProjectId);
		if(flag==false)
		{
			this.strMessage="该项目编号在本模板或继承模板中已存在,请确认";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
   
 	@Action(value = "downProjectMode",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String downProjectMode()
	{
		this.strMessage="";
		boolean flag=this.bc013Service.postDownProjectInfo(this.targerModeId, this.strProjectId);
		if(flag==false)
		{
			this.strMessage="下发失败,请确认";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
   	
   	//----------------------------------产品模板-----------------------------
   	@Action(value = "loadGoodsinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadGoodsinfo()
	{
		if(  CommonTool.FormatString(this.strCurModeId).equals("") )
		{
			this.curGoodsinfo=new Goodsinfo();
		}
		else
		{
			curGoodsinfo=this.bc013Service.loadGoodsinfoById(strCurCompId,this.strCurModeId,this.strGoodstId);
		}
		return SystemFinal.LOAD_SUCCESS;
	}
  
   @Action(value = "postGoodsInfo",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	
     }) 
	public String postGoodsInfo()
	{
		this.strMessage="";
		if(this.bc013Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC013", SystemFinal.UR_POST)==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		this.curGoodsinfo.getId().setGoodsmodeid(this.strCurModeId);
		if(CommonTool.FormatString(this.curGoodsinfo.getGoodsuniquebar()).equals(""))
		{
			this.curGoodsinfo.setGoodsuniquebar(this.bc013Service.loadMaxGoodsuniquebar());
		}
		this.curGoodsinfo.setStopdate(CommonTool.setDateMask(this.curGoodsinfo.getStopdate()));
		boolean flag=this.bc013Service.postGoods(this.curGoodsinfo);
		if(flag==false)
		{
			this.strMessage=SystemFinal.POST_FAILURE_MSG;
			return SystemFinal.POST_FAILURE;
		}
		return SystemFinal.POST_SUCCESS;
	}
 
  	@Action(value = "deleteGoodsInfo",  results = { 
			 @Result(name = "delete_success", type = "json"),
			 @Result(name = "delete_failure", type = "json")	
    }) 
	public String deleteGoodsInfo()
	{
		this.strMessage="";
		if(this.bc013Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC013", SystemFinal.UR_DELETE)==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.DELETE_FAILURE;
		}
		Goodsinfo obj =new Goodsinfo();
		obj.setId(new GoodsinfoId(this.strCurModeId,this.strGoodstId,this.strGoodsSource));
		boolean flag=this.bc013Service.deleteGoods(obj);
		obj=null;
		if(flag==false)
		{
			this.strMessage=SystemFinal.DELETE_FAILURE_MSG;
			return SystemFinal.DELETE_FAILURE;
		}
		return SystemFinal.DELETE_SUCCESS;
	}
  
  	@Action(value = "validateGoodsinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String validateGoodsinfo()
	{
		this.strMessage="";
  	boolean flag=this.bc013Service.validateGoodsById(strCurCompId,this.strCurModeId,this.strGoodstId);
  	if(flag==false)
  	{
  		this.strMessage="该产品编号在本模板或继承模板中已存在,请确认";
  	}
		return SystemFinal.LOAD_SUCCESS;
	}
  	
	//----------------------------------卡类型模板-----------------------------
   	@Action(value = "loadCardTypeinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCardTypeinfo()
	{
		if(  CommonTool.FormatString(this.strCurModeId).equals("") )
		{
			this.curCardtypeinfo=new Cardtypeinfo();
		}
		else
		{
			curCardtypeinfo=this.bc013Service.loadCardtypeinfoById(strCurCompId,this.strCurModeId,this.strCardTypetId);
			this.lsCardchangerules=this.bc013Service.loadCardchangerule(strCurCompId,this.strCurModeId,this.strCardTypetId);
		}
		return SystemFinal.LOAD_SUCCESS;
	}
  
   @Action(value = "postCardTypeInfo",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	
     }) 
	public String postCardTypeInfo()
	{
		this.strMessage="";
		if(this.bc013Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC013", SystemFinal.UR_POST)==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
		if(!CommonTool.FormatString(strJsonParam).equals("") && strJsonParam.indexOf("[")>-1)
		{
			this.lsCardchangerules=this.bc013Service.getDataTool().loadDTOList(this.strJsonParam, Cardchangerule.class);
			if(lsCardchangerules!=null && lsCardchangerules.size()>0)
			{
				for(int i=0;i<lsCardchangerules.size();i++)
				{
					lsCardchangerules.get(i).setId(new CardchangeruleId(this.strCurModeId,curCardtypeinfo.getId().getCardtypeno(),i*1.0));
					lsCardchangerules.get(i).setCardtypesource(curCardtypeinfo.getId().getCardtypesource());
				}
			}
			
		}
		this.curCardtypeinfo.getId().setCardtypemodeid(this.strCurModeId);
		boolean flag=this.bc013Service.postCardType(this.curCardtypeinfo,this.lsCardchangerules);
		if(flag==false)
		{
			this.strMessage=SystemFinal.POST_FAILURE_MSG;
			return SystemFinal.POST_FAILURE;
		}
		return SystemFinal.POST_SUCCESS;
	}
 
  	@Action(value = "deleteCardTypeInfo",  results = { 
			 @Result(name = "delete_success", type = "json"),
			 @Result(name = "delete_failure", type = "json")	
    }) 
	public String deleteCardTypeInfo()
	{
		this.strMessage="";
		if(this.bc013Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC013", SystemFinal.UR_DELETE)==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.DELETE_FAILURE;
		}
		Cardtypeinfo obj =new Cardtypeinfo();
		obj.setId(new CardtypeinfoId(this.strCurModeId,this.strCardTypetId,this.strCardTypeSource));
		boolean flag=this.bc013Service.deleteCardType(obj);
		obj=null;
		if(flag==false)
		{
			this.strMessage=SystemFinal.DELETE_FAILURE_MSG;
			return SystemFinal.DELETE_FAILURE;
		}
		return SystemFinal.DELETE_SUCCESS;
	}
  
  	@Action(value = "validateCardTypeinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String validateCardTypeinfo()
	{
		this.strMessage="";
	  	boolean flag=this.bc013Service.validateCardTypeById(strCurCompId,this.strCurModeId,this.strCardTypetId);
	  	if(flag==false)
	  	{
	  		this.strMessage="该卡类型编号在本模板或继承模板中已存在,请确认";
	  	}
		return SystemFinal.LOAD_SUCCESS;
	}
  	@Action(value = "loadWZGoodsList",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
  	public String loadWZGoodsList(){
  		try{
	  		 // webservice路径  
	         // 这里后面加不加 "?wsdl" 效果都一样的  
	         String endpoint = "http://61.152.163.198:8088/services/AmaniWebService?wsdl";  
	         //String endpoint = "http://123.122.97.226:8888/services/AmaniWebService?wsdl";//测试环境  
	         // 发送短信接口方法名  
	         String operationName = "getMaterialListForAmani";  
	         String targetNamespace = "http://service.amani.webService.portal.gwt.wangzi.haomin.com/";  
	         // 定义service对象  
	         org.apache.axis.client.Service service = new org.apache.axis.client.Service();  
	         // 创建一个call对象  
	         Call call = (Call) service.createCall();  
	         // 设置目标地址，即webservice路径  
	         call.setTargetEndpointAddress(endpoint);  
	         // 设置操作名称，即方法名称  
	         call.setOperationName(new QName(targetNamespace, operationName));  
	         // 设置方法参数  
	         call.setReturnClass(String.class);  
	         // 解决错误：服务器未能识别 HTTP 头 SOAPAction 的值  
	         call.setUseSOAPAction(true);  
	         call.setSOAPActionURI(targetNamespace + operationName);
	         Object obj = call.invoke(new Object[]{});
	         String json = ObjectUtils.toString(obj);
	         if(StringUtils.isNotBlank(json)){
	        	 Gson gson = new Gson();
	        	 WZResult result = gson.fromJson(json, new TypeToken<WZResult>(){}.getType());
	        	 if(StringUtils.equals("1", result.getRetCode())){//成功返回1 失败返回0
	        		 wzGoods =  result.getProductDetailInfoList();
	        	 }else{
	        		 strMessage = result.getMessage();
	        	 }
	         }else{
	        	 strMessage = "菲林查询接口结果为空，请刷新重试！";
	         }
  		}catch (Exception e) {
  			e.printStackTrace();
  			strMessage = "远程调用菲林查询产品接口失败！请刷新重试。";
		}
        return SystemFinal.LOAD_SUCCESS;
	 }
  	//产品限制采购设定加载门店
  	@Action(value = "loadCompanyData",  results = {@Result(name = "load_success", type = "json")}) 
	public String loadCompanyData(){
	  	commoninfo =this.bc013Service.loadCompanyData();
		return SystemFinal.LOAD_SUCCESS;
	}
	public List<Projectinfo> getLsProjectinfo() {
		return lsProjectinfo;
	}
	public void setLsProjectinfo(List<Projectinfo> lsProjectinfo) {
		this.lsProjectinfo = lsProjectinfo;
	}
	public List<Goodsinfo> getLsGoodsinfo() {
		return lsGoodsinfo;
	}
	public void setLsGoodsinfo(List<Goodsinfo> lsGoodsinfo) {
		this.lsGoodsinfo = lsGoodsinfo;
	}
	public List<Cardtypeinfo> getLsCardtypeinfo() {
		return lsCardtypeinfo;
	}
	public void setLsCardtypeinfo(List<Cardtypeinfo> lsCardtypeinfo) {
		this.lsCardtypeinfo = lsCardtypeinfo;
	}
	public int getStrCurModeType() {
		return strCurModeType;
	}
	public void setStrCurModeType(int strCurModeType) {
		this.strCurModeType = strCurModeType;
	}
	public String getStrCurModeId() {
		return strCurModeId;
	}
	public void setStrCurModeId(String strCurModeId) {
		this.strCurModeId = strCurModeId;
	}
	public Projectinfo getCurProjectinfo() {
		return curProjectinfo;
	}
	public void setCurProjectinfo(Projectinfo curProjectinfo) {
		this.curProjectinfo = curProjectinfo;
	}
	public Goodsinfo getCurGoodsinfo() {
		return curGoodsinfo;
	}
	public void setCurGoodsinfo(Goodsinfo curGoodsinfo) {
		this.curGoodsinfo = curGoodsinfo;
	}
	public Cardtypeinfo getCurCardtypeinfo() {
		return curCardtypeinfo;
	}
	public void setCurCardtypeinfo(Cardtypeinfo curCardtypeinfo) {
		this.curCardtypeinfo = curCardtypeinfo;
	}
	public String getStrProjectId() {
		return strProjectId;
	}
	public void setStrProjectId(String strProjectId) {
		this.strProjectId = strProjectId;
	}
	public String getStrProjectSource() {
		return strProjectSource;
	}
	public void setStrProjectSource(String strProjectSource) {
		this.strProjectSource = strProjectSource;
	}
	public String getStrGoodstId() {
		return strGoodstId;
	}
	public void setStrGoodstId(String strGoodstId) {
		this.strGoodstId = strGoodstId;
	}
	public String getStrGoodsSource() {
		return strGoodsSource;
	}
	public void setStrGoodsSource(String strGoodsSource) {
		this.strGoodsSource = strGoodsSource;
	}
	public String getStrCardTypetId() {
		return strCardTypetId;
	}
	public void setStrCardTypetId(String strCardTypetId) {
		this.strCardTypetId = strCardTypetId;
	}
	public String getStrCardTypeSource() {
		return strCardTypeSource;
	}
	public void setStrCardTypeSource(String strCardTypeSource) {
		this.strCardTypeSource = strCardTypeSource;
	}
	public String getTargerModeId() {
		return targerModeId;
	}
	public void setTargerModeId(String targerModeId) {
		this.targerModeId = targerModeId;
	}
	public List<Compwarehouse> getLsCompwarehouses() {
		return lsCompwarehouses;
	}
	public void setLsCompwarehouses(List<Compwarehouse> lsCompwarehouses) {
		this.lsCompwarehouses = lsCompwarehouses;
	}
	
	
}
