package com.amani.action.PersonnelControl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import java.util.List;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import org.apache.struts2.json.annotations.JSON;
import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.action.AMN_ModuleAction;
import com.amani.bean.FaceId_EmployeeBean;
import com.amani.bean.FaceId_RecordBean;

import com.amani.model.Personinfo;
import com.amani.model.StaffMachineinfo;
import com.amani.model.Staffinfo;
import com.amani.model.Sysuserinfo;

import com.amani.service.ICommonService;
import com.amani.service.PersonnelControl.PC001Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/pc001")
public class PC001Action extends AMN_ModuleAction{
	@Autowired
	private PC001Service pc001Service;
	private String strJsonParam;	
    private String strCurCompId;
    private String strCurProjectId;
    private List<Personinfo> lsPersoninfo;
    private List<Staffinfo> IsStaffinfo;
    private List<StaffMachineinfo> lsStaffMachineinfo;
    private int strPersonid;	
    private String curLoginUserNo;		//用户编号
    private String strNeedComfirmDate; //需要审核的日期
    private String strDdate;
    private String strDdatelast;
    private String strHanddate;
    private String strTHanddate;
    private OutputStream os;
	private String leavestaffno;
	private String leavedate;
	private int	leavetype;
	private String leavemark;
	private String strFromTime;
	private String strToTime;
    private List<Sysuserinfo> lsMangerPers;
	public String getLeavestaffno() {
		return leavestaffno;
	}
	public void setLeavestaffno(String leavestaffno) {
		this.leavestaffno = leavestaffno;
	}
	public String getLeavedate() {
		return leavedate;
	}
	public void setLeavedate(String leavedate) {
		this.leavedate = leavedate;
	}
	public int getLeavetype() {
		return leavetype;
	}
	public void setLeavetype(int leavetype) {
		this.leavetype = leavetype;
	}
	public String getLeavemark() {
		return leavemark;
	}
	public void setLeavemark(String leavemark) {
		this.leavemark = leavemark;
	}
	public String getStrFromTime() {
		return strFromTime;
	}
	public void setStrFromTime(String strFromTime) {
		this.strFromTime = strFromTime;
	}
	public String getStrToTime() {
		return strToTime;
	}
	public void setStrToTime(String strToTime) {
		this.strToTime = strToTime;
	}
	public String getStrHanddate() {
		return strHanddate;
	}
	public void setStrHanddate(String strHanddate) {
		this.strHanddate = strHanddate;
	}
	public int getStrPersonid() {
		return strPersonid;
	}
	public void setStrPersonid(int strPersonid) {
		this.strPersonid = strPersonid;
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

	public List<Personinfo> getLsPersoninfo() {
		return lsPersoninfo;
	}
	public void setLsPersoninfo(List<Personinfo> lsPersoninfo) {
		this.lsPersoninfo = lsPersoninfo;
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
		// TODO Auto-generated method stubselectListStaffMachineinfo
		super.setStrMessage(strMessage);
	}
	
	@Action(value = "uploadFingerIdInfo",  results = { //上传指纹
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public boolean uploadFingerIdInfo()
	{
		try
		{
			Service service = new ObjectServiceFactory().create(ICommonService.class);  
			XFireProxyFactory factory = new XFireProxyFactory(XFireFactory.newInstance().getXFire());  
			String url = "http://222.73.31.10:7001/AMNCommonService/services/CommonService";
			ICommonService commonService = (ICommonService) factory.create(service,url);
			//先从总部机器上下载员工指纹
			int strFingerMacineId=Integer.parseInt(CommonTool.FormatString(this.pc001Service.getDataTool().loadSysParam("001","SP072")));
			String strFingerMacineIp=this.pc001Service.getDataTool().loadSysParam("001","SP073");
			int result=commonService.CKT_RegisterNet_local(strFingerMacineId,strFingerMacineIp);
			if(CommonTool.FormatString(strFingerMacineIp).equals("") || CommonTool.FormatInteger(strFingerMacineId)==0)
			{
				strMessage="该门店未设置指纹信息设置";
				return false;
			}
			if(result==0)
			{
				strMessage="连接总部考勤机失败,请核实系统指纹信息设置";
				return false;
			}
			result=commonService.CKT_NetDaemon_local();
			String strUploadFilenameone="D:/finger/"+strPersonid+"_0.anv";
			result=commonService.CKT_GetFPTemplateSaveFile_local(strFingerMacineId,strPersonid,0,strUploadFilenameone);
			if(result==0)
			{
				this.pc001Service.uploadStoreFingerId(strPersonid, strUploadFilenameone);
				strMessage=strMessage+"备份员工第一个指纹失败";
				//return SystemFinal.LOAD_SUCCESS;
			}
			String strUploadFilenametwo="D:/finger/"+strPersonid+"_1.anv";
			result=commonService.CKT_GetFPTemplateSaveFile_local(strFingerMacineId,strPersonid,1,strUploadFilenametwo);
			if(result==0)
			{
				this.pc001Service.uploadStoreFingerId(strPersonid, strUploadFilenameone);
				strMessage=strMessage+"备份员工第二个指纹失败";
				//return SystemFinal.LOAD_SUCCESS;
			}
			commonService.CKT_Disconnect_local();
			commonService=null;
			return true;
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}


	
	public void createExcel() throws WriteException, IOException
	{
		try
		{
			//创建excel工作簿
			WritableWorkbook workbook= Workbook.createWorkbook(os);
			
			//创建新的一页
			WritableSheet sheet =workbook.createSheet("First Sheet", 0);
			//构造表头
			
			WritableFont bold=new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);//设置字体种类和黑体显示
			
			WritableCellFormat titleFormate =new WritableCellFormat(bold);
			titleFormate.setAlignment(jxl.format.Alignment.CENTRE);//单元格的内容水平居中
			titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
			
			WritableCellFormat numberFormate =new WritableCellFormat();
			numberFormate.setAlignment(jxl.format.Alignment.RIGHT);//单元格的内容水平右对齐
			numberFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
			
			jxl.write.WritableFont wfc = new jxl.write.WritableFont(WritableFont.ARIAL, 10, 
					WritableFont.NO_BOLD, false, 
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED); 
			WritableCellFormat totalNumberFormate =new WritableCellFormat(wfc);
			totalNumberFormate.setAlignment(jxl.format.Alignment.RIGHT);//单元格的内容水平右对齐
			totalNumberFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中


			/****创建要显示的内容***/
			//标题
			Label head=new Label(0,0,"门店考勤表",titleFormate);
			sheet.setRowView(0, 500,false);
			sheet.addCell(head);
			
			//制表日期：
			Label makeDate=new Label(0,1,"制表日期：",titleFormate);
			sheet.addCell(makeDate);
			
			//制表日期值
			Label makeDateData=new Label(1,1,""+CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet.addCell(makeDateData);
			
			Label str0=new Label(0,2,"考勤机ID",titleFormate);
			sheet.addCell(str0);
			
			Label str1=new Label(1,2,"考勤门店",titleFormate);
			sheet.addCell(str1);
			
			Label str2=new Label(2,2,"员工工号",titleFormate);
			sheet.addCell(str2);
			
			Label str3=new Label(3,2,"员工名称",titleFormate);
			sheet.addCell(str3);
			
			Label str4=new Label(4,2,"考勤日期",titleFormate);
			sheet.addCell(str4);
			
			Label stra=new Label(5,2,"上班时间",titleFormate);
			sheet.addCell(stra);
			
			Label str5=new Label(6,2,"下班时间",titleFormate);
			sheet.addCell(str5);
		
			if(this.lsStaffMachineinfo!=null&&this.lsStaffMachineinfo.size()>0)
			{
				for(int i=0;i<this.lsStaffMachineinfo.size();i++)
				{
					sheet.addCell(new Label(0,i+3,""+this.lsStaffMachineinfo.get(i).getMachineid()));
					sheet.addCell(new Label(1,i+3,""+this.lsStaffMachineinfo.get(i).getCompno()));
					sheet.addCell(new Label(2,i+3,""+this.lsStaffMachineinfo.get(i).getStaffno()));
					sheet.addCell(new Label(3,i+3,""+this.lsStaffMachineinfo.get(i).getStaffname()));
					sheet.addCell(new Label(4,i+3,""+this.lsStaffMachineinfo.get(i).getDdate()));
					sheet.addCell(new Label(5,i+3,""+this.lsStaffMachineinfo.get(i).getAttime()));
					sheet.addCell(new Label(6,i+3,""+this.lsStaffMachineinfo.get(i).getPttime()));
				}
			}
			lsStaffMachineinfo=null;
			workbook.write();
			workbook.close();
			os.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Action(value = "SelectFingerDataSet",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String SelectFingerDataSet() {  //查询考勤
	
		if(strDdate.equals(""))
			strDdate=CommonTool.getCurrDate();
		if(strDdatelast.equals(""))
			strDdatelast=CommonTool.getCurrDate();
		this.lsStaffMachineinfo=this.pc001Service.selectStaffMachineinfo(curLoginUserNo,strDdate,strDdatelast);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "comfrimBill",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String comfrimBill() {  //查询考勤
	
		this.strMessage="";
		if(this.pc001Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "PC001", SystemFinal.UR_SPECIAL_CHECK)==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.DELETE_FAILURE;
		}
		boolean flag=this.pc001Service.comfrimBillByDate(curLoginUserNo,this.strNeedComfirmDate);
		if(flag==false)
			this.strMessage="审核失败,请确认";
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "loadCurMangerlist",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadCurMangerlist() {  //查询考勤
		this.lsMangerPers=this.pc001Service.loadMangerPersByUserId(curLoginUserNo);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	

	@Action(value = "selectListStaffMachineinfo",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String selectListStaffMachineinfo() {  //列表查询考勤
		int kaoqingtype=Integer.parseInt(CommonTool.FormatString(this.pc001Service.getDataTool().loadSysParam(strCurCompId,"SP096")));
		if(kaoqingtype==0)
		{
			strMessage="该门店使用的是打卡考勤机!";
			return SystemFinal.LOAD_SUCCESS;
		}
		else if(kaoqingtype==1) //指纹
			this.lsStaffMachineinfo=this.pc001Service.selectListStaffMachineinfo(strPersonid,strDdate,strDdatelast);
		else
			this.lsStaffMachineinfo=this.pc001Service.selectListFaceStaffMachineinfo(strCurCompId,strPersonid,strDdate,strDdatelast);
		return SystemFinal.LOAD_SUCCESS;
	}

	
	@Action(value = "loadFingerDataSet",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String loadFingerDataSet()  //下载考勤
	{
		try
		{
			int kaoqingtype=Integer.parseInt(CommonTool.FormatString(this.pc001Service.getDataTool().loadSysParam(strCurCompId,"SP096")));
			if(kaoqingtype==0)
			{
				strMessage="该门店使用的是打卡考勤机!";
				return SystemFinal.LOAD_SUCCESS;
			}
			else if(kaoqingtype==1) //指纹考勤
			{
				Service service = new ObjectServiceFactory().create(ICommonService.class);  
				XFireProxyFactory factory = new XFireProxyFactory(XFireFactory.newInstance().getXFire());  
				String url = "http://222.73.31.10:7001/AMNCommonService/services/CommonService";
				ICommonService commonService = (ICommonService) factory.create(service,url);
				strMessage="";
				int strFingerMacineId=Integer.parseInt(CommonTool.FormatString(this.pc001Service.getDataTool().loadSysParam(strCurCompId,"SP072")));
				String strFingerMacineIp=this.pc001Service.getDataTool().loadSysParam(this.strCurCompId,"SP073");
				if(CommonTool.FormatString(strFingerMacineIp).equals("") || CommonTool.FormatInteger(strFingerMacineId)==0)
				{
					strMessage="该门店未设置指纹信息设置";
					return SystemFinal.LOAD_SUCCESS;
				}
				int result=commonService.CKT_RegisterNet_local(strFingerMacineId,strFingerMacineIp);
				if(result==0)
				{
					strMessage="连接门店考勤机失败,请核实系统指纹信息设置";
					return SystemFinal.LOAD_SUCCESS;
				}
				result=commonService.CKT_NetDaemon_local();
				if(CommonTool.setDateMask(strHanddate).equals("") || CommonTool.setDateMask(strTHanddate).equals("") )
				{
					strMessage="请填写下载的起始日期";
					return SystemFinal.LOAD_SUCCESS;
				}
				if(Integer.parseInt(CommonTool.setDateMask(strHanddate))>Integer.parseInt(CommonTool.setDateMask(strTHanddate)))
				{
					strMessage="下载日期设置不正确";
					return SystemFinal.LOAD_SUCCESS;
				}
				int subDate=CommonTool.dateSubDate(CommonTool.setDateMask(strHanddate),CommonTool.setDateMask(strTHanddate));
				if(subDate>31)
				{
					strMessage="下载日期不能超过一个月";
					return SystemFinal.LOAD_SUCCESS;
				}
				if(strHanddate.equals(""))
					strHanddate=CommonTool.getCurrDate();
				else
					strHanddate=CommonTool.setDateMask(strHanddate);
				String strTargetDate=strHanddate;
				String strRecord="";
				int i=1;
				while( i<=subDate+1)
				{
					strRecord="";
					System.out.println(strTargetDate);
					strTargetDate=CommonTool.datePlusDay(strTargetDate,1);
					 strRecord=commonService.GetClockingRecordProgress_localbyDate(strFingerMacineId,CommonTool.getDateMask(strTargetDate));
						if(strRecord.equals("null") || CommonTool.FormatString(strRecord).indexOf("{")==-1)
						{
							strMessage="当天没有考勤信息,请确认";
							i++;
							continue;
						}
						if(!CommonTool.FormatString(strRecord).equals(""))
						{
							this.pc001Service.handStaffRecord(strFingerMacineId,strRecord,strTargetDate);
						}
						i++;
				}
				return SystemFinal.LOAD_SUCCESS;
			}
			else
			{
				Service service = new ObjectServiceFactory().create(ICommonService.class);  
				XFireProxyFactory factory = new XFireProxyFactory(XFireFactory.newInstance().getXFire());  
				String url = "http://10.0.0.243:7003/AMNFaceWebService/services/CommonService";
				ICommonService commonService = (ICommonService) factory.create(service,url);
				int strFingerMacineId=Integer.parseInt(CommonTool.FormatString(this.pc001Service.getDataTool().loadSysParam(strCurCompId,"SP072")));
				String strFingerMacineIp=this.pc001Service.getDataTool().loadSysParam(this.strCurCompId,"SP073");
				if(CommonTool.FormatString(strFingerMacineIp).equals(""))
				{
					strMessage="该门店未设置指纹IP信息";
					return SystemFinal.LOAD_SUCCESS;
				}
				if(CommonTool.setDateMask(strHanddate).equals("") || CommonTool.setDateMask(strTHanddate).equals("") )
				{
					strMessage="请填写下载的起始日期";
					return SystemFinal.LOAD_SUCCESS;
				}
				if(Integer.parseInt(CommonTool.setDateMask(strHanddate))>Integer.parseInt(CommonTool.setDateMask(strTHanddate)))
				{
					strMessage="下载日期设置不正确";
					return SystemFinal.LOAD_SUCCESS;
				}
				int subDate=CommonTool.dateSubDate(CommonTool.setDateMask(strHanddate),CommonTool.setDateMask(strTHanddate));
				if(subDate>31)
				{
					strMessage="下载日期不能超过一个月";
					return SystemFinal.LOAD_SUCCESS;
				}
				if(strHanddate.equals(""))
					strHanddate=CommonTool.getCurrDate();
				else
					strHanddate=CommonTool.setDateMask(strHanddate);
				List<FaceId_RecordBean> lsRecord=commonService.loadEmpKqInfo(strFingerMacineIp, CommonTool.setDateMask(strHanddate), CommonTool.setDateMask(strTHanddate));
				if(lsRecord!=null && lsRecord.size()>0)
				{
						boolean flag=this.pc001Service.postStaffRecord(strFingerMacineId,strHanddate,strTHanddate,lsRecord);
						if(flag==false)
						{
							strMessage="下载失败,请刷新重试";
							return SystemFinal.LOAD_SUCCESS;
						}
				}
				lsRecord=null;
				return SystemFinal.LOAD_SUCCESS;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}


	@Action(value = "loadFingerInfos",  results = { 
			 @Result(name = "load_success", type = "json"),
      @Result(name = "load_failure", type = "json")	
   }) 
	public String loadFingerInfos()  //加载门店考勤
	{
		try
		{
			int kaoqingtype=Integer.parseInt(CommonTool.FormatString(this.pc001Service.getDataTool().loadSysParam(strCurCompId,"SP096")));
			if(kaoqingtype==0)
			{
				strMessage="该门店使用的是打卡考勤机!";
				return SystemFinal.LOAD_SUCCESS;
			}
			else if(kaoqingtype==1) //指纹
			{
				Service service = new ObjectServiceFactory().create(ICommonService.class);  
				XFireProxyFactory factory = new XFireProxyFactory(XFireFactory.newInstance().getXFire());  
				String url = "http://222.73.31.10:7001/AMNCommonService/services/CommonService";
				ICommonService commonService = (ICommonService) factory.create(service,url);
				int	   strFingerMacineId=Integer.parseInt(CommonTool.FormatString(this.pc001Service.getDataTool().loadSysParam(strCurCompId,"SP072")));
				String strFingerMacineIp=this.pc001Service.getDataTool().loadSysParam(this.strCurCompId,"SP073");
				if(CommonTool.FormatString(strFingerMacineIp).equals("") || CommonTool.FormatInteger(strFingerMacineId)==0)
				{
					strMessage="该门店未设置指纹信息设置";
					return SystemFinal.LOAD_SUCCESS;
				}
				int result=commonService.CKT_RegisterNet_local(strFingerMacineId,strFingerMacineIp);
				if(result==0)
				{
					strMessage="连接门店考勤机失败,请核实系统指纹信息设置";
					return SystemFinal.LOAD_SUCCESS;
				}
				result=commonService.CKT_NetDaemon_local();
				String strStaffInfo=commonService.CKT_ListPersonInfoEx_local(strFingerMacineId);
				System.out.println("结果"+strStaffInfo);
				this.lsPersoninfo=this.pc001Service.getDataTool().loadDTOList(strStaffInfo, Personinfo.class);
				if(this.lsPersoninfo==null )
					this.lsPersoninfo=new ArrayList();
				for(int i=0;i<lsPersoninfo.size();i++){
					lsPersoninfo.get(i).setFingerMacineIp(strFingerMacineIp);
					lsPersoninfo.get(i).setFingerMacineId(strFingerMacineId);
				}
				commonService.CKT_Disconnect_local();
				return SystemFinal.LOAD_SUCCESS;
			}
			else  //人脸考勤
			{
				Service service = new ObjectServiceFactory().create(ICommonService.class);  
				XFireProxyFactory factory = new XFireProxyFactory(XFireFactory.newInstance().getXFire());  
				String url = "http://10.0.0.243:7003/AMNFaceWebService/services/CommonService";
				ICommonService commonService = (ICommonService) factory.create(service,url);
				int	   strFingerMacineId=Integer.parseInt(CommonTool.FormatString(this.pc001Service.getDataTool().loadSysParam(strCurCompId,"SP072")));
				String strFingerMacineIp=this.pc001Service.getDataTool().loadSysParam(this.strCurCompId,"SP073");
				if(CommonTool.FormatString(strFingerMacineIp).equals(""))
				{
					strMessage="该门店未设置指纹IP信息";
					return SystemFinal.LOAD_SUCCESS;
				}
				List<FaceId_EmployeeBean> lsRecord=commonService.loadAllEmpFaceInfo(strFingerMacineIp);
				if(lsRecord!=null && lsRecord.size()>0)
				{
					this.lsPersoninfo=new ArrayList();
					Personinfo record=null;
					for(int i=0;i<lsRecord.size();i++)
					{
						record=new Personinfo();
						record.setFingerMacineId(strFingerMacineId);
						record.setPersonID(Integer.parseInt(lsRecord.get(i).getId()));
						record.setUserName(lsRecord.get(i).getName());
						record.setFingerMacineIp(strFingerMacineIp);
						lsPersoninfo.add(record);
					}
				}
				lsRecord=null;
				return SystemFinal.LOAD_SUCCESS;
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "handStaffLeave",  results = { 
			 @Result(name = "post_success", type = "json"),
			 @Result(name = "post_failure", type = "json")	
     }) 
	public String handStaffLeave()
	{
		this.strMessage="";
		boolean flag=this.pc001Service.postLeaveInfo(leavestaffno, leavedate, strFromTime, strToTime, leavetype, leavemark);
		if(flag==false)
		{
			this.strMessage="操作失败";
		}
		return SystemFinal.POST_SUCCESS;
	}
	@JSON(serialize=false)
	public PC001Service getPc001Service() {
		return pc001Service;
	}
	@JSON(serialize=false)
	public void setPc001Service(PC001Service pc001Service) {
		this.pc001Service = pc001Service;
	}
	private String strInfo;

	public List<Staffinfo> getIsStaffinfo() {
		return IsStaffinfo;
	}
	public void setIsStaffinfo(List<Staffinfo> isStaffinfo) {
		IsStaffinfo = isStaffinfo;
	}
	public String getStrInfo() {
		return strInfo;
	}
	public void setStrInfo(String strInfo) {
		this.strInfo = strInfo;
	}
	public List<StaffMachineinfo> getLsStaffMachineinfo() {
		return lsStaffMachineinfo;
	}
	public void setLsStaffMachineinfo(List<StaffMachineinfo> lsStaffMachineinfo) {
		this.lsStaffMachineinfo = lsStaffMachineinfo;
	}
	public String getStrDdate() {
		return strDdate;
	}
	public void setStrDdate(String strDdate) {
		this.strDdate = strDdate;
	}
	public String getStrDdatelast() {
		return strDdatelast;
	}
	public void setStrDdatelast(String strDdatelast) {
		this.strDdatelast = strDdatelast;
	}
	public OutputStream getOs() {
		return os;
	}
	public void setOs(OutputStream os) {
		this.os = os;
	}
	public String getStrTHanddate() {
		return strTHanddate;
	}
	public void setStrTHanddate(String strTHanddate) {
		this.strTHanddate = strTHanddate;
	}
	public String getCurLoginUserNo() {
		return curLoginUserNo;
	}
	public void setCurLoginUserNo(String curLoginUserNo) {
		this.curLoginUserNo = curLoginUserNo;
	}
	public List<Sysuserinfo> getLsMangerPers() {
		return lsMangerPers;
	}
	public void setLsMangerPers(List<Sysuserinfo> lsMangerPers) {
		this.lsMangerPers = lsMangerPers;
	}
	public String getStrNeedComfirmDate() {
		return strNeedComfirmDate;
	}
	public void setStrNeedComfirmDate(String strNeedComfirmDate) {
		this.strNeedComfirmDate = strNeedComfirmDate;
	}

	
}
