package com.amani.action.CardControl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;
import com.amani.model.Cardtypeinfo;
import com.amani.model.Compwarehouse;
import com.amani.model.Dcardnoinsert;
import com.amani.model.DcardnoinsertId;
import com.amani.model.Mcardnoinsert;
import com.amani.model.McardnoinsertId;
import com.amani.model.Staffinfo;
import com.amani.service.CardControl.CC001Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/cc001")
public class CC001Action extends AMN_ModuleAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private CC001Service cc001Service;
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
	private String strJsonParam;
	private String strCurCompId;
	private String strCurBillId;
	private String strCurCompName;
	private String strCurEmpId;
	private String strCurEmpName;
	private List<Staffinfo> lsStaffinfo;
	private List<Mcardnoinsert> lsMcardnoinserts;
	private List<Dcardnoinsert> lsDcardnoinsert;
	private Mcardnoinsert curMcardnoinsert;
	private List<Compwarehouse> lsCompwarehouses;
	private List<Cardtypeinfo> lsCardtypeinfos;
	private String factcInsertware;//仓库下拉框
	private String strCardfrom;
	private String strCardend;
	private String strWareId;
	private String strCardType;
	private int cardIdLength;//系统参数中设定的会员卡序号长度
    private String  numberOfCardFilter;//卡需要过滤的数字
    private File upload;
    private String currentImgBuf;
    private InputStream inputStream;
    private String strBillImageurl;
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getCurrentImgBuf() {
		return currentImgBuf;
	}
	public void setCurrentImgBuf(String currentImgBuf) {
		this.currentImgBuf = currentImgBuf;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getStrBillImageurl() {
		return strBillImageurl;
	}
	public void setStrBillImageurl(String strBillImageurl) {
		this.strBillImageurl = strBillImageurl;
	}
	public String getFactcInsertware() {
		return factcInsertware;
	}
	public void setFactcInsertware(String factcInsertware) {
		this.factcInsertware = factcInsertware;
	}
	public String getStrCurCompId() {
		return strCurCompId;
	}

	public void setStrCurCompId(String strCurCompId) {
		this.strCurCompId = strCurCompId;
	}

	

	public String getStrJsonParam() {
		return strJsonParam;
	}

	public void setStrJsonParam(String strJsonParam) {
		this.strJsonParam = strJsonParam;
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
		if(!CommonTool.FormatString(this.factcInsertware).equals(""))
		{
			this.curMcardnoinsert.setCinsertware(factcInsertware);
		}
		curMcardnoinsert.setOptionconfrimdate(CommonTool.getCurrDate());
		curMcardnoinsert.setOptionconfrimper(CommonTool.getLoginInfo("USERID"));
		curMcardnoinsert.setCinsertdate(CommonTool.setDateMask(curMcardnoinsert.getCinsertdate()));
		curMcardnoinsert.setCinserttime(CommonTool.setTimeMask(curMcardnoinsert.getCinserttime(), 1));
		curMcardnoinsert.getId().setCinsertbillid(this.cc001Service.getDataTool().loadBillIdByRule(curMcardnoinsert.getId().getCinsertcompid(),"mcardnoinsert", "cinsertbillid", "SP012"));
		this.lsDcardnoinsert=this.cc001Service.getDataTool().loadDTOList(strJsonParam, Dcardnoinsert.class);
		if(lsDcardnoinsert!=null && lsDcardnoinsert.size()>0)
		{
			for(int i=0;i<lsDcardnoinsert.size();i++)
			{
				if(!CommonTool.FormatString(lsDcardnoinsert.get(i).getCardtypeid()).equals(""))
				{
					lsDcardnoinsert.get(i).setId(new DcardnoinsertId(curMcardnoinsert.getId().getCinsertcompid(),curMcardnoinsert.getId().getCinsertbillid(),i*1.0));
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
			if(this.cc001Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC001", SystemFinal.UR_DELETE)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.DELETE_FAILURE;
			}
			Mcardnoinsert obj=new Mcardnoinsert();
			obj.setId(new McardnoinsertId(this.strCurCompId,this.strCurBillId));
			boolean flag=this.cc001Service.delete(obj);
			obj=null;
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
			this.strMessage="";
			if(this.cc001Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "CC001", SystemFinal.UR_POST)==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			this.beforePost();
			boolean flag=this.cc001Service.post(this.curMcardnoinsert, this.lsDcardnoinsert);
			if(flag==false)
			{
				this.strMessage=SystemFinal.POST_FAILURE_MSG;
				return SystemFinal.POST_FAILURE;
			}
			flag=this.cc001Service.postCardIntoStore(curMcardnoinsert.getId().getCinsertcompid(), curMcardnoinsert.getId().getCinsertbillid(),1);
			if(flag==false)
			{
				this.strMessage="生成卡入库历史失败!";
				return SystemFinal.POST_FAILURE;
			}
			curMcardnoinsert=null;
			lsDcardnoinsert=null;
			return SystemFinal.POST_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.POST_FAILURE;
		}
	}
	
	@Action(value = "loadCardInsertInfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadCardInsertInfo()
	{
		try
		{
			this.lsMcardnoinserts=this.cc001Service.loadMasterDataSet(30, 0);
			if(lsMcardnoinserts!=null && lsMcardnoinserts.size()>0)
			{
				for(int i=0;i<lsMcardnoinserts.size();i++)
				{
					lsMcardnoinserts.get(i).setBcinsertcompid(lsMcardnoinserts.get(i).getId().getCinsertcompid());
					lsMcardnoinserts.get(i).setBcinsertbillid(lsMcardnoinserts.get(i).getId().getCinsertbillid());
					lsMcardnoinserts.get(i).setCinsertdate(CommonTool.getDateMask(lsMcardnoinserts.get(i).getCinsertdate()));
					lsMcardnoinserts.get(i).setOptionconfrimdate(CommonTool.getDateMask(lsMcardnoinserts.get(i).getOptionconfrimdate()));
					lsMcardnoinserts.get(i).setCinserttime(CommonTool.getTimeMask(lsMcardnoinserts.get(i).getCinserttime(),1));
				}
				this.lsDcardnoinsert=this.cc001Service.loadDcardnoinserts(lsMcardnoinserts.get(0).getId().getCinsertcompid(), lsMcardnoinserts.get(0).getId().getCinsertbillid());
				if(lsDcardnoinsert!=null && lsDcardnoinsert.size()>0)
				{
					for(int i=0;i<lsDcardnoinsert.size();i++)
					{
						lsDcardnoinsert.get(i).setBcinsertcompid(lsDcardnoinsert.get(i).getId().getCinsertcompid());
						lsDcardnoinsert.get(i).setBcinsertbillid(lsDcardnoinsert.get(i).getId().getCinsertbillid());
					}
				}
				curMcardnoinsert=lsMcardnoinserts.get(0);
				curMcardnoinsert.setBcinsertcompName(this.cc001Service.getDataTool().loadCompNameById(curMcardnoinsert.getBcinsertcompid()));
				StringBuffer validatemsg=new StringBuffer();
				curMcardnoinsert.setCinsertperName(this.cc001Service.getDataTool().loadEmpNameById(this.curMcardnoinsert.getBcinsertcompid(), this.curMcardnoinsert.getCinsertper(),validatemsg));
				validatemsg=null;
			}
			else
			{
				this.curMcardnoinsert=this.cc001Service.addMastRecord();
				curMcardnoinsert.setBcinsertcompName(this.cc001Service.getDataTool().loadCompNameById(curMcardnoinsert.getBcinsertcompid()));
				this.lsMcardnoinserts=new ArrayList();
				this.lsMcardnoinserts.add(curMcardnoinsert);
			}
			this.lsCompwarehouses=this.cc001Service.getDataTool().loadCompWareById(this.curMcardnoinsert.getBcinsertcompid());
			this.lsCardtypeinfos=this.cc001Service.getDataTool().loadCardType(this.curMcardnoinsert.getBcinsertcompid());
			double cardlen=Double.parseDouble(CommonTool.FormatString(this.cc001Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP019")));
			if(cardlen==0)
			{
				this.cardIdLength=0;
			}
			else
			{
				this.cardIdLength=((Double)cardlen).intValue();
			}
			//获得系统中设定的要过滤的卡号尾数
			this.numberOfCardFilter=CommonTool.FormatString(this.cc001Service.getDataTool().loadSysParam(CommonTool.getLoginInfo("COMPID"),"SP020"));
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		} 
	}
	
	@Action(value = "loadDcardnoinsert",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String loadDcardnoinsert()
	{
		try
		{
			this.curMcardnoinsert=this.cc001Service.loadMcardnoinsertById(this.strCurCompId, this.strCurBillId);
			this.lsDcardnoinsert=this.cc001Service.loadDcardnoinserts(this.strCurCompId, this.strCurBillId);
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "add",  results = { 
			 @Result(name = "add_success", type = "json"),
           @Result(name = "add_failure", type = "json")	
        }) 
	@Override
	public String add()
	{
		try
		{
			this.curMcardnoinsert=this.cc001Service.addMastRecord();
			return SystemFinal.ADD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.ADD_FAILURE;
		}
	}
	
	@Action(value = "validateInserper",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	   })
	public String validateInserper()
	{
		try
		{
			StringBuffer validateMsg=new StringBuffer();
			this.strCurEmpName=this.cc001Service.getDataTool().loadEmpNameById(this.strCurCompId, this.strCurEmpId, validateMsg);
			this.strMessage=validateMsg.toString();
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	
	/***********检查起始编号**************/
	@Action(value = "checkCardNoFrom", results = { @Result(name = "check_success", type = "json"),
											@Result(name = "check_failure", type = "json")})
    public String checkCardNoFrom()
    {
    	try
    	{
    		if(this.cc001Service.validateCardNoFrom(this.strCardfrom,this.strCardType,this.strWareId))
    		{
    			this.strMessage="";
    		}
    		else
    		{
    			this.strMessage="该卡号已经存在!";
    		}
    		return "check_success";
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    		return "check_failure";
    	}
    }
    /***********检查结束编号**************/
	@Action(value = "checkCardNoEnd", results = { @Result(name = "check_success", type = "json"),
											@Result(name = "check_failure", type = "json")})
    public String checkCardNoEnd()
    {
    	try
    	{
    		if(this.cc001Service.validateCardNoFrom(this.strCardend,this.strCardType,this.strWareId))
    		{
    			this.strMessage="";
    		}
    		else
    		{
    			this.strMessage="该卡号已经存在!";
    		}
    		return "check_success";
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    		return "check_failure";
    	}
    }
    
    /************检查起始结束范围是否存在*************/
	@Action(value = "checkCardRange", results = { @Result(name = "check_success", type = "json"),
											@Result(name = "check_failure", type = "json")})
    public String checkCardRange()
    {
    	try
    	{
    		if(this.cc001Service.validatecardRange(this.strCardfrom, strCardend, strCardType, strWareId))
    		{
    			this.strMessage="";
    		}
    		else
    		{
    			this.strMessage="该范围已有卡入库";
    		}
    		return "check_success";
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    		return "check_failure";
    	}
    }
	
	 public String loadBillImage()
	  {
	    try
	    {
	      this.strMessage = "";
	      if (!(CommonTool.FormatString(this.strBillImageurl).equals("")))
	      {
	        byte[] billbytes = CommonTool.loadByteByFile(this.strBillImageurl);
	        if (billbytes == null)
	        {
	          billbytes = CommonTool.loadByteByFile("D:\\amnBillImage\\defult.jpg");
	        }
	        this.strBillImageurl = this.strBillImageurl.substring(0, this.strBillImageurl.indexOf("."));
	        String[] billinfo = this.strBillImageurl.split("_");
	        if (billinfo.length == 3)
	        {
	          String strCompId = billinfo[1];
	          String strBillId = billinfo[2];
	          this.curMcardnoinsert = this.cc001Service.loadMcardnoinsertById(strCompId, strBillId);
	          this.curMcardnoinsert.setOptionconfrimdate(CommonTool.setDateMask(this.curMcardnoinsert.getOptioncanceldate()));
	          this.curMcardnoinsert.setCinsertdate(CommonTool.setDateMask(this.curMcardnoinsert.getCinsertdate()));
	          this.curMcardnoinsert.setCinserttime(CommonTool.setTimeMask(this.curMcardnoinsert.getCinserttime(), 1));
//	          if (this.curMcardnoinsert != null)
//	          {
//	            this.curMcardnoinsert.setReportimage(billbytes);
//	            boolean falg = this.cc001Service.handBillImage(this.curMcardnoinsert);
//	            this.curMcardnoinsert = null;
//	          }
	          this.inputStream = new ByteArrayInputStream(billbytes);
	          billbytes = (byte[])null;
	        }
	      }
	      return "load_success";
	    }
	    catch (Exception ex)
	    {
	      ex.printStackTrace(); }
	    return "load_failure";
	  }

	@JSON(serialize=false)
	public CC001Service getCc001Service() {
		return cc001Service;
	}
	@JSON(serialize=false)
	public void setCc001Service(CC001Service cc001Service) {
		this.cc001Service = cc001Service;
	}
	public String getStrCurBillId() {
		return strCurBillId;
	}
	public void setStrCurBillId(String strCurBillId) {
		this.strCurBillId = strCurBillId;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public List<Mcardnoinsert> getLsMcardnoinserts() {
		return lsMcardnoinserts;
	}
	public void setLsMcardnoinserts(List<Mcardnoinsert> lsMcardnoinserts) {
		this.lsMcardnoinserts = lsMcardnoinserts;
	}
	public List<Dcardnoinsert> getLsDcardnoinsert() {
		return lsDcardnoinsert;
	}
	public void setLsDcardnoinsert(List<Dcardnoinsert> lsDcardnoinsert) {
		this.lsDcardnoinsert = lsDcardnoinsert;
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
	public Mcardnoinsert getCurMcardnoinsert() {
		return curMcardnoinsert;
	}
	public void setCurMcardnoinsert(Mcardnoinsert curMcardnoinsert) {
		this.curMcardnoinsert = curMcardnoinsert;
	}
	public List<Compwarehouse> getLsCompwarehouses() {
		return lsCompwarehouses;
	}
	public void setLsCompwarehouses(List<Compwarehouse> lsCompwarehouses) {
		this.lsCompwarehouses = lsCompwarehouses;
	}
	public List<Cardtypeinfo> getLsCardtypeinfos() {
		return lsCardtypeinfos;
	}
	public void setLsCardtypeinfos(List<Cardtypeinfo> lsCardtypeinfos) {
		this.lsCardtypeinfos = lsCardtypeinfos;
	}
	public List<Staffinfo> getLsStaffinfo() {
		return lsStaffinfo;
	}
	public void setLsStaffinfo(List<Staffinfo> lsStaffinfo) {
		this.lsStaffinfo = lsStaffinfo;
	}
	public String getStrCardfrom() {
		return strCardfrom;
	}
	public void setStrCardfrom(String strCardfrom) {
		this.strCardfrom = strCardfrom;
	}
	public String getStrCardend() {
		return strCardend;
	}
	public void setStrCardend(String strCardend) {
		this.strCardend = strCardend;
	}
	public String getStrWareId() {
		return strWareId;
	}
	public void setStrWareId(String strWareId) {
		this.strWareId = strWareId;
	}
	public String getStrCardType() {
		return strCardType;
	}
	public void setStrCardType(String strCardType) {
		this.strCardType = strCardType;
	}
	public int getCardIdLength() {
		return cardIdLength;
	}
	public void setCardIdLength(int cardIdLength) {
		this.cardIdLength = cardIdLength;
	}
	public String getNumberOfCardFilter() {
		return numberOfCardFilter;
	}
	public void setNumberOfCardFilter(String numberOfCardFilter) {
		this.numberOfCardFilter = numberOfCardFilter;
	}


}
