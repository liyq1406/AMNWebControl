package com.amani.action.BaseInfoControl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.util.JSONUtils;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.xwork.ObjectUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;
import com.amani.model.Companyinfo;
import com.amani.model.Compchainstruct;
import com.amani.model.Compscheduling;
import com.amani.model.Compwarehouse;
import com.amani.model.CompwarehouseId;
import com.amani.model.Roominfo;
import com.amani.model.WxProduct;
import com.amani.service.BaseInfoControl.BC001Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/bc001")
public class BC001Action extends AMN_ModuleAction{
	
	@Autowired
	private BC001Service bc001Service;
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
	private List<Companyinfo> 		lsCompanyinfos=new ArrayList();
	private List<Compchainstruct>	lsCompchainstructs=new ArrayList();
	private List<Compwarehouse>		lsCompwarehouses=new ArrayList();
	private List<Compscheduling>	lsCompscheduling=new ArrayList();
	private List<Roominfo> lsRoominfo =new ArrayList();
	private String strJsonParam;
	private String strJsonSchedulParam;
	private String strJsonRoomParam;
	private Companyinfo curCompanyinfo;
	private String strCurCompId;
	private String mangerPassword;
	private String strCurWareHouseId;
	private WxProduct wxProduct = new WxProduct();

	public String getStrCurCompId() {
		return strCurCompId;
	}

	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}

	public Companyinfo getCurCompanyinfo() {
		return curCompanyinfo;
	}

	public void setCurCompanyinfo(Companyinfo curCompanyinfo) {
		this.curCompanyinfo = curCompanyinfo;
	}

	public String getStrJsonParam() {
		return strJsonParam;
	}

	public void setStrJsonParam(String strJsonParam) {
		this.strJsonParam = strJsonParam;
	}

	public List<Companyinfo> getLsCompanyinfos() {
		return lsCompanyinfos;
	}

	public void setLsCompanyinfos(List<Companyinfo> lsCompanyinfos) {
		this.lsCompanyinfos = lsCompanyinfos;
	}

	public List<Compchainstruct> getLsCompchainstructs() {
		return lsCompchainstructs;
	}

	public void setLsCompchainstructs(List<Compchainstruct> lsCompchainstructs) {
		this.lsCompchainstructs = lsCompchainstructs;
	}

	public List<Compwarehouse> getLsCompwarehouses() {
		return lsCompwarehouses;
	}

	public void setLsCompwarehouses(List<Compwarehouse> lsCompwarehouses) {
		this.lsCompwarehouses = lsCompwarehouses;
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
		return false;
	}

	@Override
	protected boolean beforeLoad() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean beforePost() {
		
		lsCompwarehouses=new ArrayList();
		lsCompscheduling=new ArrayList();
		lsRoominfo = new ArrayList();
		if(!CommonTool.FormatString(strJsonParam).equals("") && strJsonParam.indexOf("[")>-1)
		{
			this.lsCompwarehouses=this.bc001Service.getDataTool().loadDTOList(strJsonParam,Compwarehouse.class);
			if(lsCompwarehouses!=null && lsCompwarehouses.size()>0)
			{
				for(int i=0;i<lsCompwarehouses.size();i++)
				{
					if(!CommonTool.FormatString(lsCompwarehouses.get(i).getBwarehouseno()).equals(""))
					{
						lsCompwarehouses.get(i).setId(new CompwarehouseId(this.strCurCompId,lsCompwarehouses.get(i).getBwarehouseno()));
					}
				}
			}
		}
		if(!CommonTool.FormatString(strJsonSchedulParam).equals("") && strJsonSchedulParam.indexOf("[")>-1)
		{
			this.lsCompscheduling=this.bc001Service.getDataTool().loadDTOList(strJsonSchedulParam,Compscheduling.class);
			if(lsCompscheduling!=null && lsCompscheduling.size()>0)
			{
				for(int i=0;i<lsCompscheduling.size();i++)
				{
					if(!CommonTool.FormatString(lsCompscheduling.get(i).getSchedulno()).equals(""))
					{
						lsCompscheduling.get(i).setCompno(strCurCompId);
					}
				}
			}
		}
		if(!CommonTool.FormatString(strJsonRoomParam).equals("") && strJsonRoomParam.indexOf("[")>-1)
		{
			this.lsRoominfo=this.bc001Service.getDataTool().loadDTOList(strJsonRoomParam,Roominfo.class);
			if(lsRoominfo!=null && lsRoominfo.size()>0)
			{
				for(int i=0;i<lsRoominfo.size();i++)
				{
					if(!CommonTool.FormatString(lsRoominfo.get(i).getRoomno()).equals(""))
					{
						lsRoominfo.get(i).setCompno(strCurCompId);
					}
				}
			}
		}
		return true;
	}

	@Override
	protected boolean deleteActive() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Action(value = "delete",  results = { 
			 @Result(name = "delete_success", type = "json"),
           @Result(name = "delete_failure", type = "json")	
        }) 
	@Override
	public String delete()
	{
		try
		{
			this.strMessage="";
			if(this.bc001Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC001", SystemFinal.UR_SPECIAL_COST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.DELETE_FAILURE;
			}
			boolean flag = this.bc001Service.deleteWareHouse(this.strCurCompId,this.strCurWareHouseId);
			if(flag==false)
			{
				this.strMessage=SystemFinal.DELETE_FAILURE_MSG;
				return SystemFinal.DELETE_FAILURE;
			}
			return SystemFinal.DELETE_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.DELETE_FAILURE;
		}
	}

	@Override
	protected boolean loadActive() {
		// TODO Auto-generated method stub
		return true;
	}
    
	@Override
	protected boolean postActive() {
	
		return true;
	}
	
	@Action(value = "post",  results = { 
			 @Result(name = "post_success", type = "json"),
            @Result(name = "post_failure", type = "json")	
         }) 
	@Override
	public String post()
	{
		try
		{
			this.beforePost();
			this.strMessage="";
			if(this.bc001Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC001", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.bc001Service.postCompinfo(curCompanyinfo, this.lsCompwarehouses,this.lsCompscheduling,this.lsRoominfo);
			this.lsCompwarehouses=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	@Action(value="upload", results={@Result(name="post_success", type="json")})	
	public String uploadImg()
	{
		HttpServletRequest request = ServletActionContext.getRequest(); 
		String base64Img = request.getParameter("mainimageurl");
//		request.getInputStream();
		try {
//			String base64Img = wxProduct.getMainimageurl();
			if(StringUtils.isBlank(base64Img)){
				strMessage = "";
				return SystemFinal.POST_SUCCESS; 
			}
			String base64Str = StringUtils.substring(base64Img, base64Img.indexOf(",")+1);
			// webservice路径  
	    	// 这里后面加不加 "?wsdl" 效果都一样的  
	    	String endpoint = "http://222.73.31.3:7009/AMNCommonService/services/ImageCloudService";  
	    	// 上传图片接口方法名  
	    	String operationName = "uploadFile";  
	    	String targetNamespace = "http://services.amani.com/";  
	    	// 定义service对象  
	    	org.apache.axis.client.Service service = new org.apache.axis.client.Service();  
	    	// 创建一个call对象  
	    	Call call = (Call) service.createCall();  
	    	// 设置目标地址，即webservice路径  
	    	call.setTargetEndpointAddress(endpoint);  
	    	// 设置操作名称，即方法名称  
	    	call.setOperationName(new QName(targetNamespace, operationName));  
	    	// 设置方法参数  
	    	call.addParameter(new QName(targetNamespace, "file"), XMLType.SOAP_BASE64BINARY, ParameterMode.IN); 
	    	call.addParameter(new QName(targetNamespace, "bucket"), XMLType.XSD_STRING, ParameterMode.IN); 
	    	call.addParameter(new QName(targetNamespace, "key"), XMLType.XSD_STRING, ParameterMode.IN); 
	    	call.setReturnClass(String.class);  
	    	// 解决错误：服务器未能识别 HTTP 头 SOAPAction 的值  
	    	call.setUseSOAPAction(true);  
	    	call.setSOAPActionURI(targetNamespace + operationName);
	    	String title = System.currentTimeMillis()+wxProduct.getName();
	    	Object obj = call.invoke(new Object[]{Base64.decodeBase64(base64Str), "amani", title});
	    	String result = ObjectUtils.toString(obj);
	    	if(StringUtils.isNotBlank(result)){
	    		Gson gson = new Gson();
	    		Map<String, String> map = gson.fromJson(result, new TypeToken<Map<String, String>>(){}.getType());
	    		strMessage = map.get("url");
	    	}
		} catch (Exception e) {
			e.printStackTrace();
			strMessage = "";
		}
		return SystemFinal.POST_SUCCESS;
	}
	
	@Action(value = "postChainInfo",  results = { 
			 @Result(name = "post_success", type = "json"),
           @Result(name = "post_failure", type = "json")	
        }) 
	public String  postChainInfo()
	{
		try
		{
			this.strMessage="";
			if(this.bc001Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC001", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			List<Compchainstruct>  lsCompchainstruct=this.bc001Service.getDataTool().loadDTOList(strJsonParam,Compchainstruct.class);
			if(lsCompchainstruct!=null && lsCompchainstruct.size()>0)
			{
				boolean flag=this.bc001Service.handChainInfo(lsCompchainstruct);
				if(flag==false)
				{
					this.strMessage=SystemFinal.POST_FAILURE_MSG;
					return SystemFinal.POST_FAILURE;
				}
			}
			lsCompchainstruct=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	
	@Action(value = "validateInitRight",  results = { 
			 @Result(name = "post_success", type = "json"),
          @Result(name = "post_failure", type = "json")	
       }) 
	public String  validateInitRight()
	{
		try
		{
			this.strMessage="";
			if(this.bc001Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "BC001", SystemFinal.UR_SPECIAL_CHECK)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.bc001Service.handMangerPass(this.strCurCompId, this.mangerPassword);
			if(flag==false)
			{
				this.strMessage="初始化失败!";
			}
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}

	

	private static void setDataFormat2JAVA(){ 
//		设定日期转换格式 
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"})); 
		} 
	
	@Action(value = "loadMaster",  results = { 
			 @Result(name = "load_success", type = "json"),
           @Result(name = "load_failure", type = "json")	
        })  
	public String loadMaster()
	{
		this.strCurCompId=CommonTool.getLoginInfo("COMPID");
		
		if(lsCompanyinfos!=null && lsCompanyinfos.size()>0)
		{
			for(int i=0;i<lsCompanyinfos.size();i++)
			{
				
			}
		}
		//this.lsCompchainstructs=this.bc001Service.loadCompchainstructByCurCompId();
		//this.lsCompwarehouses=this.bc001Service.loadCompwarehouseByCurCompId(strCurCompId);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadCurWareHouse",  results = { 
			 @Result(name = "load_success", type = "json"),
          @Result(name = "load_failure", type = "json")	
       }) 
    public String loadCurWareHouse()
	{
		this.curCompanyinfo=this.bc001Service.loadCompanyinfoByCurCompId(strCurCompId);
		this.lsCompwarehouses=this.bc001Service.loadCompwarehouseByCurCompId(strCurCompId);
		if(this.lsCompwarehouses!=null && this.lsCompwarehouses.size()>0)
		{
			for(int i=0;i<this.lsCompwarehouses.size();i++)
			{
				this.lsCompwarehouses.get(i).setBwarehouseno(this.lsCompwarehouses.get(i).getId().getWarehouseno());
			}
		}
		this.lsCompscheduling=this.bc001Service.loadCompschedulingByCurCompId(strCurCompId);
		//得到门店房间信息
		this.lsRoominfo=this.bc001Service.loadRoominfoByCurCompId(strCurCompId);
		//this.bc001Service.loadCompanyinfoByCurCompId_JDBC(strCurCompId);
		return SystemFinal.LOAD_SUCCESS;
	}
	@JSON(serialize=false)
	public BC001Service getBc001Service() {
		return bc001Service;
	}
	@JSON(serialize=false)
	public void setBc001Service(BC001Service bc001Service) {
		this.bc001Service = bc001Service;
	}

	public String getStrCurWareHouseId() {
		return strCurWareHouseId;
	}

	public void setStrCurWareHouseId(String strCurWareHouseId) {
		this.strCurWareHouseId = strCurWareHouseId;
	}

	public List<Compscheduling> getLsCompscheduling() {
		return lsCompscheduling;
	}

	public void setLsCompscheduling(List<Compscheduling> lsCompscheduling) {
		this.lsCompscheduling = lsCompscheduling;
	}

	public String getStrJsonSchedulParam() {
		return strJsonSchedulParam;
	}

	public void setStrJsonSchedulParam(String strJsonSchedulParam) {
		this.strJsonSchedulParam = strJsonSchedulParam;
	}

	public String getMangerPassword() {
		return mangerPassword;
	}

	public void setMangerPassword(String mangerPassword) {
		this.mangerPassword = mangerPassword;
	}

	public List<Roominfo> getLsRoominfo() {
		return lsRoominfo;
	}

	public void setLsRoominfo(List<Roominfo> lsRoominfo) {
		this.lsRoominfo = lsRoominfo;
	}

	public String getStrJsonRoomParam() {
		return strJsonRoomParam;
	}

	public void setStrJsonRoomParam(String strJsonRoomParam) {
		this.strJsonRoomParam = strJsonRoomParam;
	}

	public WxProduct getWxProduct() {
		return wxProduct;
	}

	public void setWxProduct(WxProduct wxProduct) {
		this.wxProduct = wxProduct;
	}
	
}
