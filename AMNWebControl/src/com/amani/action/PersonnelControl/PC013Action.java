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


import com.amani.bean.PC013Bean;
import com.amani.service.PersonnelControl.PC013Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/pc013")
public class PC013Action {
	@Autowired
	private PC013Service pc013Service;
	private String strCurCompId;
	private int targetcount;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private List<PC013Bean> lsDataSet;
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
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		this.lsDataSet=this.pc013Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate),targetcount);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "loadpc013Excel",  results = { 
			 @Result(name = "load_success", location = "/PersonnelControl/PC013/pc013Excel.jsp"),
			 @Result(name = "load_failure",  location = "/PersonnelControl/PC013/pc013Excel.jsp")	
	}) 
	public String loadpc013Excel() {  
	
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		this.lsDataSet=this.pc013Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate),targetcount);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	public void createExcel() throws WriteException, IOException
	{
		try
		{
			int excelLength0=7;
			//System.out.println(lsDataSet.size());
			//创建excel工作簿
			WritableWorkbook workbook= Workbook.createWorkbook(os);
			WritableSheet sheet =workbook.createSheet("入职申请", 0);
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
			Label head=new Label(0,0,"总监首席老客统计",titleFormate);
			sheet.setRowView(0, 500,false);
			sheet.addCell(head);
			
			//制表日期：
			Label makeDate=new Label(0,1,"制表日期：",titleFormate);
			sheet.addCell(makeDate);
			
			//制表日期值
			Label makeDateData=new Label(1,1,""+CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet.addCell(makeDateData);
			
			Label str0=new Label(0,2,"公司编号",titleFormate);
			sheet.addCell(str0);
			
			Label str1=new Label(1,2,"公司名称",titleFormate);
			sheet.addCell(str1);
			
			Label str2=new Label(2,2,"员工编号",titleFormate);
			sheet.addCell(str2);
			
			Label str6=new Label(3,2,"入职日期",titleFormate);
			sheet.addCell(str6);
			
			Label str3=new Label(4,2,"员工名称",titleFormate);
			sheet.addCell(str3);
			
			Label str4x=new Label(5,2,"职位编号",titleFormate);
			sheet.addCell(str4x);
			
			Label str5x=new Label(6,2,"职位名称",titleFormate);
			sheet.addCell(str5x);
			
			Label str4=new Label(7,2,"老客个数",titleFormate);
			sheet.addCell(str4);
			
			Label stra=new Label(8,2,"差异个数",titleFormate);
			sheet.addCell(stra);
		
		
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				for(int i=0;i<this.lsDataSet.size();i++)
				{
					sheet.addCell(new Label(0,i+3,""+this.lsDataSet.get(i).getStrCompid()));
					sheet.addCell(new Label(1,i+3,""+this.lsDataSet.get(i).getStrCompName()));
					sheet.addCell(new Label(2,i+3,""+this.lsDataSet.get(i).getStrEmpNo()));
					sheet.addCell(new Label(3,i+3,""+CommonTool.getDateMask(this.lsDataSet.get(i).getStrEntrydata())));
					sheet.addCell(new Label(4,i+3,""+this.lsDataSet.get(i).getStrEmpName()));
					sheet.addCell(new Label(5,i+3,""+this.lsDataSet.get(i).getStrPostion()));
					sheet.addCell(new Label(6,i+3,""+this.lsDataSet.get(i).getStrPostionText()));
					sheet.addCell(new Label(7,i+3,""+this.lsDataSet.get(i).getCcount()));
					sheet.addCell(new Label(8,i+3,""+this.lsDataSet.get(i).getDifCount()));
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
	public List<PC013Bean> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<PC013Bean> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	

	public int getTargetcount() {
		return targetcount;
	}
	public void setTargetcount(int targetcount) {
		this.targetcount = targetcount;
	}
	@JSON(serialize=false)
	public PC013Service getPc013Service() {
		return pc013Service;
	}
	@JSON(serialize=false)
	public void setPc013Service(PC013Service pc013Service) {
		this.pc013Service = pc013Service;
	}

	
	
}
