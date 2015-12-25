package com.amani.action.AdvancedOperations;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFeatures;
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

import com.amani.bean.StaffWarkSalaryAnlanysis;
import com.amani.service.AdvancedOperations.AC017Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ac017")
public class AC017Action {
	@Autowired
	private AC017Service ac017Service;
	private String strCurCompId;
	private String strCurCompName;
	private String strSearchType;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private String strStaffInNo;
	private String strAbsencedate;
	private String strStaffNo;
	private String strCurDate;
	private String strCurMonth;
	private String strtoMonth;
	private int notPrintCash;
	private List<StaffWarkSalaryAnlanysis> lsDataSet;
    private OutputStream os;
    private int detialtype;
	public int getDetialtype() {
		return detialtype;
	}
	public void setDetialtype(int detialtype) {
		this.detialtype = detialtype;
	}
	public OutputStream getOs() {
		return os;
	}
	public void setOs(OutputStream os) {
		this.os = os;
	}
	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		strMessage="";
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate().substring(1,6)+"01";
		else
			strFromDate=strFromDate.replace("-", "")+"01";
		this.lsDataSet=this.ac017Service.loadDateSetByCompId(strCurCompId,strFromDate,notPrintCash);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action( value="loadDetialDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDetialDataSet() {
		strMessage="";
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate().substring(1,6)+"01";
		else
			strFromDate=strFromDate.replace("-", "")+"01";
		this.lsDataSet=this.ac017Service.loadDetailDateSetByCompId(strCurCompId,strFromDate,detialtype);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadTExcel",  results = { 
			 @Result(name = "load_success", location = "/AdvancedOperations/AC017/ac017TExcel.jsp"),
			 @Result(name = "load_failure",  location = "/AdvancedOperations/AC017/ac017TExcel.jsp")	
	}) 
	public String loadTExcel() {  //查询工资
		strMessage="";
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate().substring(1,6)+"01";
		else
			strFromDate=strFromDate.replace("-", "")+"01";
		this.lsDataSet=this.ac017Service.loadDateSetByCompId(strCurCompId,strFromDate,notPrintCash);
		strCurCompName=this.ac017Service.getDataTool().loadCompNameById(strCurCompId);
		return SystemFinal.LOAD_SUCCESS;

	}
	
	@Action(value = "loadTBExcel",  results = { 
			 @Result(name = "load_success", location = "/AdvancedOperations/AC017/ac017TExcel.jsp"),
			 @Result(name = "load_failure",  location = "/AdvancedOperations/AC017/ac017TExcel.jsp")	
	}) 
	public String loadTBExcel() {  //查询工资
		strMessage="";
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate().substring(1,6)+"01";
		else
			strFromDate=strFromDate.replace("-", "")+"01";
		this.lsDataSet=this.ac017Service.loadDateSetByCompIdTA(strCurCompId,strFromDate,notPrintCash);
		strCurCompName=this.ac017Service.getDataTool().loadCompNameById(strCurCompId);
		return SystemFinal.LOAD_SUCCESS;

	}
	
	
	public void createTExcel() throws WriteException, IOException
	{
		try
		{
			int excelLength0=12;
			//创建excel工作簿
			WritableWorkbook workbook= Workbook.createWorkbook(os);
			
			//创建新的一页
			WritableSheet sheet =workbook.createSheet("First Sheet", 0);
			//构造表头
			sheet.mergeCells(0, 0, excelLength0-1, 0); //合并第一行，1到倒数第一行列
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
			Label head=new Label(0,0,strCurCompId+"["+strCurCompName+"]"+"门店工资汇总",titleFormate);
			sheet.setRowView(0, 500,false);
			sheet.addCell(head);
			
			//制表日期：
			Label makeDate=new Label(0,1,"制表日期：",titleFormate);
			sheet.addCell(makeDate);
			
			
			//制表日期值
			Label makeDateData=new Label(1,1,""+CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet.addCell(makeDateData);
			
			Label str0=new Label(0,2,"门店",titleFormate);
			sheet.addCell(str0);
			
			Label str1=new Label(1,2,"门店名称",titleFormate);
			sheet.addCell(str1);
			
			Label str2=new Label(2,2,"在职有社保总额",titleFormate);
			sheet.addCell(str2);
	
			Label str3=new Label(3,2,"在职无社保总额",titleFormate);
			sheet.addCell(str3);
			
			Label str4=new Label(4,2,"待发工资总额",titleFormate);
			sheet.addCell(str4);
			
			Label stra=new Label(5,2,"离职工资总额",titleFormate);
			sheet.addCell(stra);
			
			
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				for(int i=0;i<this.lsDataSet.size();i++)
				{
					sheet.addCell(new Label(0,i+3,""+this.lsDataSet.get(i).getStrCompId()));
					sheet.addCell(new Label(1,i+3,""+this.lsDataSet.get(i).getStrCompName()));
					sheet.addCell(new Number(2,i+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getSumHasSocialSaraly()).doubleValue()));
					sheet.addCell(new Number(3,i+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getSumNoSocialSaraly()).doubleValue()));
					sheet.addCell(new Number(4,i+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getSumReadSalary()).doubleValue()));
					sheet.addCell(new Number(5,i+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getSumLeavelSalary()).doubleValue()));
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
	
	
	@Action(value = "loadac017Excel",  results = { 
			 @Result(name = "load_success", location = "/AdvancedOperations/AC017/ac017Excel.jsp"),
			 @Result(name = "load_failure",  location = "/AdvancedOperations/AC017/ac017Excel.jsp")	
	}) 
	public String loadac017Excel() {  //查询工资
		strMessage="";
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate().substring(1,6)+"01";
		else
			strFromDate=strFromDate.replace("-", "")+"01";
		this.lsDataSet=this.ac017Service.loadDetailDateSetByCompId(strCurCompId,strFromDate);
		strCurCompName=this.ac017Service.getDataTool().loadCompNameById(strCurCompId);
		return SystemFinal.LOAD_SUCCESS;

	}
	public void createExcel() throws WriteException, IOException
	{
		try
		{
			int excelLength0=39;
			//创建excel工作簿
			WritableWorkbook workbook= Workbook.createWorkbook(os);
			
			
			WritableFont bold=new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,true);//设置字体种类和黑体显示
			WritableCellFormat titleFormate =new WritableCellFormat(bold);
			titleFormate.setAlignment(jxl.format.Alignment.CENTRE);//单元格的内容水平居中
			titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
			titleFormate.setWrap(true);
			WritableCellFormat numberFormate =new WritableCellFormat();
			numberFormate.setAlignment(jxl.format.Alignment.RIGHT);//单元格的内容水平右对齐
			numberFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
			
			jxl.write.WritableFont wfc = new jxl.write.WritableFont(WritableFont.ARIAL, 10, 
					WritableFont.NO_BOLD, false, 
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED); 
			WritableCellFormat totalNumberFormate =new WritableCellFormat(wfc);
			totalNumberFormate.setAlignment(jxl.format.Alignment.RIGHT);//单元格的内容水平右对齐
			totalNumberFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中

			//创建新的一页
			WritableSheet sheetA =workbook.createSheet("在职有银行卡有社保", 0);
			WritableSheet sheetB =workbook.createSheet("在职有银行卡有社保(补)", 1);
			WritableSheet sheetC =workbook.createSheet("在职有银行卡无社保", 2);
			WritableSheet sheetD =workbook.createSheet("在职无银行卡", 3);
			WritableSheet sheetE =workbook.createSheet("离职有银行卡有社保", 4);
			WritableSheet sheetF =workbook.createSheet("离职有银行卡有社保(补)", 5);
			WritableSheet sheetG =workbook.createSheet("离职有银行卡无社保", 6);
			WritableSheet sheetH =workbook.createSheet("离职无银行卡", 7);
			WritableSheet sheetI =workbook.createSheet("待发", 8);
			
			Label Acell0=new Label(0,0,"门店号",titleFormate);
			Label Bcell0=new Label(0,0,"门店号",titleFormate);
			Label Ccell0=new Label(0,0,"门店号",titleFormate);
			Label Dcell0=new Label(0,0,"门店号",titleFormate);
			Label Ecell0=new Label(0,0,"门店号",titleFormate);
			Label Fcell0=new Label(0,0,"门店号",titleFormate);
			Label Gcell0=new Label(0,0,"门店号",titleFormate);
			Label Hcell0=new Label(0,0,"门店号",titleFormate);
			Label Icell0=new Label(0,0,"门店号",titleFormate);
			sheetA.addCell(Acell0);
			sheetB.addCell(Bcell0);
			sheetC.addCell(Ccell0);
			sheetD.addCell(Dcell0);
			sheetE.addCell(Ecell0);
			sheetF.addCell(Fcell0);
			sheetG.addCell(Gcell0);
			sheetH.addCell(Hcell0);
			sheetI.addCell(Icell0);
			Label Acell1=new Label(1,0,"门店名称",titleFormate);
			Label Bcell1=new Label(1,0,"门店名称",titleFormate);
			Label Ccell1=new Label(1,0,"门店名称",titleFormate);
			Label Dcell1=new Label(1,0,"门店名称",titleFormate);
			Label Ecell1=new Label(1,0,"门店名称",titleFormate);
			Label Fcell1=new Label(1,0,"门店名称",titleFormate);
			Label Gcell1=new Label(1,0,"门店名称",titleFormate);
			Label Hcell1=new Label(1,0,"门店名称",titleFormate);
			Label Icell1=new Label(1,0,"门店名称",titleFormate);
			sheetA.addCell(Acell1);
			sheetB.addCell(Bcell1);
			sheetC.addCell(Ccell1);
			sheetD.addCell(Dcell1);
			sheetE.addCell(Ecell1);
			sheetF.addCell(Fcell1);
			sheetG.addCell(Gcell1);
			sheetH.addCell(Hcell1);
			sheetI.addCell(Icell1);
			Label Acell2=new Label(2,0,"工号",titleFormate);
			Label Bcell2=new Label(2,0,"工号",titleFormate);
			Label Ccell2=new Label(2,0,"工号",titleFormate);
			Label Dcell2=new Label(2,0,"工号",titleFormate);
			Label Ecell2=new Label(2,0,"工号",titleFormate);
			Label Fcell2=new Label(2,0,"工号",titleFormate);
			Label Gcell2=new Label(2,0,"工号",titleFormate);
			Label Hcell2=new Label(2,0,"工号",titleFormate);
			Label Icell2=new Label(2,0,"工号",titleFormate);
			sheetA.addCell(Acell2);
			sheetB.addCell(Bcell2);
			sheetC.addCell(Ccell2);
			sheetD.addCell(Dcell2);
			sheetE.addCell(Ecell2);
			sheetF.addCell(Fcell2);
			sheetG.addCell(Gcell2);
			sheetH.addCell(Hcell2);
			sheetI.addCell(Icell2);
			Label Acell3=new Label(3,0,"姓名",titleFormate);
			Label Bcell3=new Label(3,0,"姓名",titleFormate);
			Label Ccell3=new Label(3,0,"姓名",titleFormate);
			Label Dcell3=new Label(3,0,"姓名",titleFormate);
			Label Ecell3=new Label(3,0,"姓名",titleFormate);
			Label Fcell3=new Label(3,0,"姓名",titleFormate);
			Label Gcell3=new Label(3,0,"姓名",titleFormate);
			Label Hcell3=new Label(3,0,"姓名",titleFormate);
			Label Icell3=new Label(3,0,"姓名",titleFormate);
			sheetA.addCell(Acell3);
			sheetB.addCell(Bcell3);
			sheetC.addCell(Ccell3);
			sheetD.addCell(Dcell3);
			sheetE.addCell(Ecell3);
			sheetF.addCell(Fcell3);
			sheetG.addCell(Gcell3);
			sheetH.addCell(Hcell3);
			sheetI.addCell(Icell3);
			Label Acell4=new Label(4,0,"职位",titleFormate);
			Label Bcell4=new Label(4,0,"职位",titleFormate);
			Label Ccell4=new Label(4,0,"职位",titleFormate);
			Label Dcell4=new Label(4,0,"职位",titleFormate);
			Label Ecell4=new Label(4,0,"职位",titleFormate);
			Label Fcell4=new Label(4,0,"职位",titleFormate);
			Label Gcell4=new Label(4,0,"职位",titleFormate);
			Label Hcell4=new Label(4,0,"职位",titleFormate);
			Label Icell4=new Label(4,0,"职位",titleFormate);
			sheetA.addCell(Acell4);
			sheetB.addCell(Bcell4);
			sheetC.addCell(Ccell4);
			sheetD.addCell(Dcell4);
			sheetE.addCell(Ecell4);
			sheetF.addCell(Fcell4);
			sheetG.addCell(Gcell4);
			sheetH.addCell(Hcell4);
			sheetI.addCell(Icell4);
			Label Acell5=new Label(5,0,"社保",titleFormate);
			Label Bcell5=new Label(5,0,"社保",titleFormate);
			Label Ccell5=new Label(5,0,"社保",titleFormate);
			Label Dcell5=new Label(5,0,"社保",titleFormate);
			Label Ecell5=new Label(5,0,"社保",titleFormate);
			Label Fcell5=new Label(5,0,"社保",titleFormate);
			Label Gcell5=new Label(5,0,"社保",titleFormate);
			Label Hcell5=new Label(5,0,"社保",titleFormate);
			Label Icell5=new Label(5,0,"社保",titleFormate);
			sheetA.addCell(Acell5);
			sheetB.addCell(Bcell5);
			sheetC.addCell(Ccell5);
			sheetD.addCell(Dcell5);
			sheetE.addCell(Ecell5);
			sheetF.addCell(Fcell5);
			sheetG.addCell(Gcell5);
			sheetH.addCell(Hcell5);
			sheetI.addCell(Icell5);
			Label Acell6=new Label(6,0,"身份证",titleFormate);
			Label Bcell6=new Label(6,0,"身份证",titleFormate);
			Label Ccell6=new Label(6,0,"身份证",titleFormate);
			Label Dcell6=new Label(6,0,"身份证",titleFormate);
			Label Ecell6=new Label(6,0,"身份证",titleFormate);
			Label Fcell6=new Label(6,0,"身份证",titleFormate);
			Label Gcell6=new Label(6,0,"身份证",titleFormate);
			Label Hcell6=new Label(6,0,"身份证",titleFormate);
			Label Icell6=new Label(6,0,"身份证",titleFormate);
			
			sheetA.addCell(Acell6);
			sheetB.addCell(Bcell6);
			sheetC.addCell(Ccell6);
			sheetD.addCell(Dcell6);
			sheetE.addCell(Ecell6);
			sheetF.addCell(Fcell6);
			sheetG.addCell(Gcell6);
			sheetH.addCell(Hcell6);
			sheetI.addCell(Icell6);
			Label Acell7=new Label(7,0,"银行卡号",titleFormate);
			Label Bcell7=new Label(7,0,"银行卡号",titleFormate);
			Label Ccell7=new Label(7,0,"银行卡号",titleFormate);
			Label Dcell7=new Label(7,0,"银行卡号",titleFormate);
			Label Ecell7=new Label(7,0,"银行卡号",titleFormate);
			Label Fcell7=new Label(7,0,"银行卡号",titleFormate);
			Label Gcell7=new Label(7,0,"银行卡号",titleFormate);
			Label Hcell7=new Label(7,0,"银行卡号",titleFormate);
			Label Icell7=new Label(7,0,"银行卡号",titleFormate);
			sheetA.addCell(Acell7);
			sheetB.addCell(Bcell7);
			sheetC.addCell(Ccell7);
			sheetD.addCell(Dcell7);
			sheetE.addCell(Ecell7);
			sheetF.addCell(Fcell7);
			sheetG.addCell(Gcell7);
			sheetH.addCell(Hcell7);
			sheetI.addCell(Icell7);
			Label Acell9=new Label(8,0,"银行名称",titleFormate);
			Label Bcell9=new Label(8,0,"银行名称",titleFormate);
			Label Ccell9=new Label(8,0,"银行名称",titleFormate);
			Label Dcell9=new Label(8,0,"银行名称",titleFormate);
			Label Ecell9=new Label(8,0,"银行名称",titleFormate);
			Label Fcell9=new Label(8,0,"银行名称",titleFormate);
			Label Gcell9=new Label(8,0,"银行名称",titleFormate);
			Label Hcell9=new Label(8,0,"银行名称",titleFormate);
			Label Icell9=new Label(8,0,"银行名称",titleFormate);
			sheetA.addCell(Acell9);
			sheetB.addCell(Bcell9);
			sheetC.addCell(Ccell9);
			sheetD.addCell(Dcell9);
			sheetE.addCell(Ecell9);
			sheetF.addCell(Fcell9);
			sheetG.addCell(Gcell9);
			sheetH.addCell(Hcell9);
			sheetI.addCell(Icell9);
			Label Acell8=new Label(9,0,"实发工资",titleFormate);
			Label Bcell8=new Label(9,0,"实发工资",titleFormate);
			Label Ccell8=new Label(9,0,"实发工资",titleFormate);
			Label Dcell8=new Label(9,0,"实发工资",titleFormate);
			Label Ecell8=new Label(9,0,"实发工资",titleFormate);
			Label Fcell8=new Label(9,0,"实发工资",titleFormate);
			Label Gcell8=new Label(9,0,"实发工资",titleFormate);
			Label Hcell8=new Label(9,0,"实发工资",titleFormate);
			Label Icell8=new Label(9,0,"实发工资",titleFormate);
			sheetA.addCell(Acell8);
			sheetB.addCell(Bcell8);
			sheetC.addCell(Ccell8);
			sheetD.addCell(Dcell8);
			sheetE.addCell(Ecell8);
			sheetF.addCell(Fcell8);
			sheetG.addCell(Gcell8);
			sheetH.addCell(Hcell8);
			sheetI.addCell(Icell8);
			int excelnumA=1;
			int excelnumB=1;
			int excelnumC=1;
			int excelnumD=1;
			int excelnumE=1;
			int excelnumF=1;
			int excelnumG=1;
			int excelnumH=1;
			int excelnumI=1;
			
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				for( int i=0;i<lsDataSet.size();i++)
				{
					if(CommonTool.FormatInteger(lsDataSet.get(i).getPageType())==1)
					{
						sheetA.addCell(new Label(0,excelnumA,""+this.lsDataSet.get(i).getStrCompId()));
						sheetA.addCell(new Label(1,excelnumA,""+this.lsDataSet.get(i).getStrCompName()));
						sheetA.addCell(new Label(2,excelnumA,""+this.lsDataSet.get(i).getStaffno()));
						sheetA.addCell(new Label(3,excelnumA,""+this.lsDataSet.get(i).getStaffname()));
						sheetA.addCell(new Label(4,excelnumA,""+this.lsDataSet.get(i).getStaffpositionname()));
						sheetA.addCell(new Label(5,excelnumA,""+this.lsDataSet.get(i).getStaffsocialsource()));
						sheetA.addCell(new Label(6,excelnumA,""+this.lsDataSet.get(i).getStaffpcid()));
						sheetA.addCell(new Label(7,excelnumA,""+this.lsDataSet.get(i).getStaffbankaccountno()));
						sheetA.addCell(new Label(8,excelnumA,""+this.lsDataSet.get(i).getStrBackName()));
						sheetA.addCell(new Number(9,excelnumA,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getFactpaysalary()).doubleValue()));
						excelnumA++;
					}
					else if(CommonTool.FormatInteger(lsDataSet.get(i).getPageType())==2)
					{
						sheetB.addCell(new Label(0,excelnumB,""+this.lsDataSet.get(i).getStrCompId()));
						sheetB.addCell(new Label(1,excelnumB,""+this.lsDataSet.get(i).getStrCompName()));
						sheetB.addCell(new Label(2,excelnumB,""+this.lsDataSet.get(i).getStaffno()));
						sheetB.addCell(new Label(3,excelnumB,""+this.lsDataSet.get(i).getStaffname()));
						sheetB.addCell(new Label(4,excelnumB,""+this.lsDataSet.get(i).getStaffpositionname()));
						sheetB.addCell(new Label(5,excelnumB,""+this.lsDataSet.get(i).getStaffsocialsource()));
						sheetB.addCell(new Label(6,excelnumB,""+this.lsDataSet.get(i).getStaffpcid()));
						sheetB.addCell(new Label(7,excelnumB,""+this.lsDataSet.get(i).getStaffbankaccountno()));
						sheetB.addCell(new Label(8,excelnumB,""+this.lsDataSet.get(i).getStrBackName()));
						sheetB.addCell(new Number(9,excelnumB,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getFactpaysalary()).doubleValue()));
						excelnumB++;
					}
					else if(CommonTool.FormatInteger(lsDataSet.get(i).getPageType())==3)
					{
						sheetC.addCell(new Label(0,excelnumC,""+this.lsDataSet.get(i).getStrCompId()));
						sheetC.addCell(new Label(1,excelnumC,""+this.lsDataSet.get(i).getStrCompName()));
						sheetC.addCell(new Label(2,excelnumC,""+this.lsDataSet.get(i).getStaffno()));
						sheetC.addCell(new Label(3,excelnumC,""+this.lsDataSet.get(i).getStaffname()));
						sheetC.addCell(new Label(4,excelnumC,""+this.lsDataSet.get(i).getStaffpositionname()));
						sheetC.addCell(new Label(5,excelnumC,""+this.lsDataSet.get(i).getStaffsocialsource()));
						sheetC.addCell(new Label(6,excelnumC,""+this.lsDataSet.get(i).getStaffpcid()));
						sheetC.addCell(new Label(7,excelnumC,""+this.lsDataSet.get(i).getStaffbankaccountno()));
						sheetC.addCell(new Label(8,excelnumC,""+this.lsDataSet.get(i).getStrBackName()));
						sheetC.addCell(new Number(9,excelnumC,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getFactpaysalary()).doubleValue()));
						excelnumC++;
					}
					else if(CommonTool.FormatInteger(lsDataSet.get(i).getPageType())==4)
					{
						sheetD.addCell(new Label(0,excelnumD,""+this.lsDataSet.get(i).getStrCompId()));
						sheetD.addCell(new Label(1,excelnumD,""+this.lsDataSet.get(i).getStrCompName()));
						sheetD.addCell(new Label(2,excelnumD,""+this.lsDataSet.get(i).getStaffno()));
						sheetD.addCell(new Label(3,excelnumD,""+this.lsDataSet.get(i).getStaffname()));
						sheetD.addCell(new Label(4,excelnumD,""+this.lsDataSet.get(i).getStaffpositionname()));
						sheetD.addCell(new Label(5,excelnumD,""+this.lsDataSet.get(i).getStaffsocialsource()));
						sheetD.addCell(new Label(6,excelnumD,""+this.lsDataSet.get(i).getStaffpcid()));
						sheetD.addCell(new Label(7,excelnumD,""+this.lsDataSet.get(i).getStaffbankaccountno()));
						sheetD.addCell(new Label(8,excelnumD,""+this.lsDataSet.get(i).getStrBackName()));
						sheetD.addCell(new Number(9,excelnumD,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getFactpaysalary()).doubleValue()));
						excelnumD++;
					}
					else if(CommonTool.FormatInteger(lsDataSet.get(i).getPageType())==5)
					{
						sheetE.addCell(new Label(0,excelnumE,""+this.lsDataSet.get(i).getStrCompId()));
						sheetE.addCell(new Label(1,excelnumE,""+this.lsDataSet.get(i).getStrCompName()));
						sheetE.addCell(new Label(2,excelnumE,""+this.lsDataSet.get(i).getStaffno()));
						sheetE.addCell(new Label(3,excelnumE,""+this.lsDataSet.get(i).getStaffname()));
						sheetE.addCell(new Label(4,excelnumE,""+this.lsDataSet.get(i).getStaffpositionname()));
						sheetE.addCell(new Label(5,excelnumE,""+this.lsDataSet.get(i).getStaffsocialsource()));
						sheetE.addCell(new Label(6,excelnumE,""+this.lsDataSet.get(i).getStaffpcid()));
						sheetE.addCell(new Label(7,excelnumE,""+this.lsDataSet.get(i).getStaffbankaccountno()));
						sheetE.addCell(new Label(8,excelnumE,""+this.lsDataSet.get(i).getStrBackName()));
						sheetE.addCell(new Number(9,excelnumE,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getFactpaysalary()).doubleValue()));
						excelnumE++;
					}
					else if(CommonTool.FormatInteger(lsDataSet.get(i).getPageType())==6)
					{
						sheetF.addCell(new Label(0,excelnumF,""+this.lsDataSet.get(i).getStrCompId()));
						sheetF.addCell(new Label(1,excelnumF,""+this.lsDataSet.get(i).getStrCompName()));
						sheetF.addCell(new Label(2,excelnumF,""+this.lsDataSet.get(i).getStaffno()));
						sheetF.addCell(new Label(3,excelnumF,""+this.lsDataSet.get(i).getStaffname()));
						sheetF.addCell(new Label(4,excelnumF,""+this.lsDataSet.get(i).getStaffpositionname()));
						sheetF.addCell(new Label(5,excelnumF,""+this.lsDataSet.get(i).getStaffsocialsource()));
						sheetF.addCell(new Label(6,excelnumF,""+this.lsDataSet.get(i).getStaffpcid()));
						sheetF.addCell(new Label(7,excelnumF,""+this.lsDataSet.get(i).getStaffbankaccountno()));
						sheetF.addCell(new Label(8,excelnumF,""+this.lsDataSet.get(i).getStrBackName()));
						sheetF.addCell(new Number(9,excelnumF,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getFactpaysalary()).doubleValue()));
						excelnumF++;
					}
					else if(CommonTool.FormatInteger(lsDataSet.get(i).getPageType())==7)
					{
						sheetG.addCell(new Label(0,excelnumG,""+this.lsDataSet.get(i).getStrCompId()));
						sheetG.addCell(new Label(1,excelnumG,""+this.lsDataSet.get(i).getStrCompName()));
						sheetG.addCell(new Label(2,excelnumG,""+this.lsDataSet.get(i).getStaffno()));
						sheetG.addCell(new Label(3,excelnumG,""+this.lsDataSet.get(i).getStaffname()));
						sheetG.addCell(new Label(4,excelnumG,""+this.lsDataSet.get(i).getStaffpositionname()));
						sheetG.addCell(new Label(5,excelnumG,""+this.lsDataSet.get(i).getStaffsocialsource()));
						sheetG.addCell(new Label(6,excelnumG,""+this.lsDataSet.get(i).getStaffpcid()));
						sheetG.addCell(new Label(7,excelnumG,""+this.lsDataSet.get(i).getStaffbankaccountno()));
						sheetG.addCell(new Label(8,excelnumG,""+this.lsDataSet.get(i).getStrBackName()));
						sheetG.addCell(new Number(9,excelnumG,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getFactpaysalary()).doubleValue()));
						excelnumG++;
					}
					else if(CommonTool.FormatInteger(lsDataSet.get(i).getPageType())==8)
					{
						sheetH.addCell(new Label(0,excelnumH,""+this.lsDataSet.get(i).getStrCompId()));
						sheetH.addCell(new Label(1,excelnumH,""+this.lsDataSet.get(i).getStrCompName()));
						sheetH.addCell(new Label(2,excelnumH,""+this.lsDataSet.get(i).getStaffno()));
						sheetH.addCell(new Label(3,excelnumH,""+this.lsDataSet.get(i).getStaffname()));
						sheetH.addCell(new Label(4,excelnumH,""+this.lsDataSet.get(i).getStaffpositionname()));
						sheetH.addCell(new Label(5,excelnumH,""+this.lsDataSet.get(i).getStaffsocialsource()));
						sheetH.addCell(new Label(6,excelnumH,""+this.lsDataSet.get(i).getStaffpcid()));
						sheetH.addCell(new Label(7,excelnumH,""+this.lsDataSet.get(i).getStaffbankaccountno()));
						sheetH.addCell(new Label(8,excelnumH,""+this.lsDataSet.get(i).getStrBackName()));
						sheetH.addCell(new Number(9,excelnumH,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getFactpaysalary()).doubleValue()));
						excelnumH++;
					}
					else if(CommonTool.FormatInteger(lsDataSet.get(i).getPageType())==9)
					{
						sheetI.addCell(new Label(0,excelnumI,""+this.lsDataSet.get(i).getStrCompId()));
						sheetI.addCell(new Label(1,excelnumI,""+this.lsDataSet.get(i).getStrCompName()));
						sheetI.addCell(new Label(2,excelnumI,""+this.lsDataSet.get(i).getStaffno()));
						sheetI.addCell(new Label(3,excelnumI,""+this.lsDataSet.get(i).getStaffname()));
						sheetI.addCell(new Label(4,excelnumI,""+this.lsDataSet.get(i).getStaffpositionname()));
						sheetI.addCell(new Label(5,excelnumI,""+this.lsDataSet.get(i).getStaffsocialsource()));
						sheetI.addCell(new Label(6,excelnumI,""+this.lsDataSet.get(i).getStaffpcid()));
						sheetI.addCell(new Label(7,excelnumI,""+this.lsDataSet.get(i).getStaffbankaccountno()));
						sheetI.addCell(new Label(8,excelnumI,""+this.lsDataSet.get(i).getStrBackName()));
						sheetI.addCell(new Number(9,excelnumI,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getFactpaysalary()).doubleValue()));
						excelnumI++;
					}
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
	public List<StaffWarkSalaryAnlanysis> getLsDataSet() {
		return lsDataSet;
	}
	public int getNotPrintCash() {
		return notPrintCash;
	}
	public void setNotPrintCash(int notPrintCash) {
		this.notPrintCash = notPrintCash;
	}
	public void setLsDataSet(List<StaffWarkSalaryAnlanysis> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	public String getStrSearchType() {
		return strSearchType;
	}
	public void setStrSearchType(String strSearchType) {
		this.strSearchType = strSearchType;
	}
	
	public String getStrCurCompName() {
		return strCurCompName;
	}
	public void setStrCurCompName(String strCurCompName) {
		this.strCurCompName = strCurCompName;
	}
	
	public String getStrStaffInNo() {
		return strStaffInNo;
	}
	public void setStrStaffInNo(String strStaffInNo) {
		this.strStaffInNo = strStaffInNo;
	}
	public String getStrAbsencedate() {
		return strAbsencedate;
	}
	public void setStrAbsencedate(String strAbsencedate) {
		this.strAbsencedate = strAbsencedate;
	}
	public String getStrStaffNo() {
		return strStaffNo;
	}
	public void setStrStaffNo(String strStaffNo) {
		this.strStaffNo = strStaffNo;
	}
	public String getStrCurDate() {
		return strCurDate;
	}
	public void setStrCurDate(String strCurDate) {
		this.strCurDate = strCurDate;
	}
	public String getStrCurMonth() {
		return strCurMonth;
	}
	public void setStrCurMonth(String strCurMonth) {
		this.strCurMonth = strCurMonth;
	}
	public String getStrtoMonth() {
		return strtoMonth;
	}
	public void setStrtoMonth(String strtoMonth) {
		this.strtoMonth = strtoMonth;
	}
	
	@JSON(serialize=false)
	public AC017Service getAc017Service() {
		return ac017Service;
	}
	@JSON(serialize=false)
	public void setAc017Service(AC017Service ac017Service) {
		this.ac017Service = ac017Service;
	}


	
	
}
