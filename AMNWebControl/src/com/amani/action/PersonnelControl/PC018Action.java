package com.amani.action.PersonnelControl;

import java.io.IOException;
import java.io.OutputStream;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.model.Mstaffsubsidyinfo;
import com.amani.service.PersonnelControl.PC018Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/pc018")
public class PC018Action {
	@Autowired
	private PC018Service pc018Service;
	private String strCurCompId;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private String strCompId;
	private String strBillId;
	private String strJsonParam;
	private String strSearchStaffNo;
	private List<Mstaffsubsidyinfo> lsDataSet;
	private int billstate;
	private OutputStream os;
	public OutputStream getOs() {
		return os;
	}
	public void setOs(OutputStream os) {
		this.os = os;
	}
	//查询默认发送短信
	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		this.lsDataSet=this.pc018Service.loadDateSetByCompId(strCurCompId, CommonTool.FormatString(strFromDate),billstate,strSearchStaffNo);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "loadpc018Excel",  results = { 
			 @Result(name = "load_success", location = "/PersonnelControl/PC018/pc018Excel.jsp"),
			 @Result(name = "load_failure",  location = "/PersonnelControl/PC018/pc018Excel.jsp")	
	}) 
	public String loadpc018Excel() {  
	
		this.lsDataSet=this.pc018Service.loadDateSetByCompId(strCurCompId, CommonTool.FormatString(strFromDate),billstate,strSearchStaffNo);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	public void createExcel() throws WriteException, IOException
	{
		try
		{
			int excelLength0=11;
			//创建excel工作簿
			WritableWorkbook workbook= Workbook.createWorkbook(os);
			WritableSheet sheet =workbook.createSheet("总部薪资录入统计", 0);
			sheet.mergeCells(0, 0, excelLength0-1, 0); //合并第一行，1到倒数第一行列
		
		
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
			Label head=new Label(0,0,"总部薪资录入统计",titleFormate);
			sheet.setRowView(0, 500,false);
			sheet.addCell(head);
			
			//制表日期：
			Label makeDate=new Label(0,1,"制表日期：",titleFormate);
			sheet.addCell(makeDate);
			
			//制表日期值
			Label makeDateData=new Label(1,1,""+CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet.addCell(makeDateData);
			
			Label str0=new Label(0,2,"保底门店",titleFormate);
			sheet.addCell(str0);
			
			Label str1=new Label(1,2,"公司名称",titleFormate);
			sheet.addCell(str1);
			
			Label str2=new Label(2,2,"保底员工",titleFormate);
			sheet.addCell(str2);
			
			Label str6=new Label(3,2,"内部工号",titleFormate);
			sheet.addCell(str6);
			
			Label str3=new Label(4,2,"员工名称",titleFormate);
			sheet.addCell(str3);
			
			Label str4x=new Label(5,2,"保底额度",titleFormate);
			sheet.addCell(str4x);
			
			Label str5x=new Label(6,2,"保底条件",titleFormate);
			sheet.addCell(str5x);
			
			Label str4=new Label(7,2,"保底方式",titleFormate);
			sheet.addCell(str4);
			
			Label stra=new Label(8,2,"满足条件数",titleFormate);
			sheet.addCell(stra);
		
			Label stra9=new Label(9,2,"起始月份",titleFormate);
			sheet.addCell(stra9);
			
			Label stra10=new Label(10,2,"结束月份",titleFormate);
			sheet.addCell(stra10);
			
		
		
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				for(int i=0;i<this.lsDataSet.size();i++)
				{
					sheet.addCell(new Label(0,i+3,""+this.lsDataSet.get(i).getHandcompid()));
					sheet.addCell(new Label(1,i+3,""+this.lsDataSet.get(i).getHandcompname()));
					sheet.addCell(new Label(2,i+3,""+this.lsDataSet.get(i).getHandstaffid()));
					sheet.addCell(new Label(3,i+3,""+this.lsDataSet.get(i).getHandstaffinid()));
					sheet.addCell(new Label(4,i+3,""+this.lsDataSet.get(i).getHandstaffname()));
					sheet.addCell(new Label(5,i+3,""+this.lsDataSet.get(i).getSubsidyamt().doubleValue()));
					sheet.addCell(new Label(6,i+3,""+this.lsDataSet.get(i).getSubsidyconditiontext()));
					if(CommonTool.FormatInteger(this.lsDataSet.get(i).getSubsidyflag())==1)
						sheet.addCell(new Label(7,i+3,"全部满足"));
					else
						sheet.addCell(new Label(7,i+3,"部分满足"));
					sheet.addCell(new Label(8,i+3,""+this.lsDataSet.get(i).getConditionnum()));
					sheet.addCell(new Label(9,i+3,""+this.lsDataSet.get(i).getStartdate()));
					sheet.addCell(new Label(10,i+3,""+this.lsDataSet.get(i).getEnddate()));
				}
			}
			lsDataSet=null;
			workbook.write();
			workbook.close();
			os.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Action( value="comfrimBill", results={ @Result( type="json", name="post_success"),
			 @Result( type="json", name="post_failure")})
	public String comfrimBill()
	{
		this.strMessage="";
		if(this.pc018Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX016")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
//		boolean flag=this.pc018Service.handBill(strCompId, strBillId,1);
//		if(flag==false)
//			this.strMessage="操作失败!";
		if(!CommonTool.FormatString(strJsonParam).equals(""))
		{
			this.lsDataSet=this.pc018Service.getDataTool().loadDTOList(strJsonParam, Mstaffsubsidyinfo.class);
			boolean flag=this.pc018Service.handLsBill(lsDataSet,1);
			if(flag==false)
				this.strMessage="操作失败!";
		}
		return SystemFinal.POST_SUCCESS;
	}
	
	@Action( value="comfrimbackBill", results={ @Result( type="json", name="post_success"),
			 @Result( type="json", name="post_failure")})
	public String comfrimbackBill()
	{
		this.strMessage="";
		if(this.pc018Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX016")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
//		boolean flag=this.pc018Service.handBill(strCompId, strBillId, 2);
//		if(flag==false)
//			this.strMessage="操作失败!";
		if(!CommonTool.FormatString(strJsonParam).equals(""))
		{
			this.lsDataSet=this.pc018Service.getDataTool().loadDTOList(strJsonParam, Mstaffsubsidyinfo.class);
			boolean flag=this.pc018Service.handLsBill(lsDataSet,2);
			if(flag==false)
				this.strMessage="操作失败!";
		}
		return SystemFinal.POST_SUCCESS;
	}
	
	@Action( value="comfrimstopBill", results={ @Result( type="json", name="post_success"),
			 @Result( type="json", name="post_failure")})
	public String comfrimstopBill()
	{
		this.strMessage="";
		if(this.pc018Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX025")==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.POST_FAILURE;
		}
//		boolean flag=this.pc018Service.handBill(strCompId, strBillId, 2);
//		if(flag==false)
//			this.strMessage="操作失败!";
		if(!CommonTool.FormatString(strJsonParam).equals(""))
		{
			this.lsDataSet=this.pc018Service.getDataTool().loadDTOList(strJsonParam, Mstaffsubsidyinfo.class);
			boolean flag=this.pc018Service.handLsBill(lsDataSet,3);
			if(flag==false)
				this.strMessage="操作失败!";
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
	

	@JSON(serialize=false)
	public PC018Service getPc018Service() {
		return pc018Service;
	}
	@JSON(serialize=false)
	public void setPc018Service(PC018Service pc018Service) {
		this.pc018Service = pc018Service;
	}
	public List<Mstaffsubsidyinfo> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<Mstaffsubsidyinfo> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	public int getBillstate() {
		return billstate;
	}
	public void setBillstate(int billstate) {
		this.billstate = billstate;
	}
	public String getStrCompId() {
		return strCompId;
	}
	public void setStrCompId(String strCompId) {
		this.strCompId = strCompId;
	}
	public String getStrBillId() {
		return strBillId;
	}
	public void setStrBillId(String strBillId) {
		this.strBillId = strBillId;
	}
	public String getStrJsonParam() {
		return strJsonParam;
	}
	public void setStrJsonParam(String strJsonParam) {
		this.strJsonParam = strJsonParam;
	}
	public String getStrSearchStaffNo() {
		return strSearchStaffNo;
	}
	public void setStrSearchStaffNo(String strSearchStaffNo) {
		this.strSearchStaffNo = strSearchStaffNo;
	}

	
	
}
