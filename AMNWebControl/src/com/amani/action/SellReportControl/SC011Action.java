package com.amani.action.SellReportControl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
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



import com.amani.bean.ProjectTypeAnalysis;
import com.amani.service.SellReportControl.SC011Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/sc011")
public class SC011Action {
	@Autowired
	private SC011Service sc011Service;
	private String strCurCompId;
	private String strSearchType;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private List<ProjectTypeAnalysis> lsDataSet;
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
		this.lsDataSet=this.sc011Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate));
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "loadsc011Excel",  results = { 
			 @Result(name = "load_success", location = "/SellReportControl/SC011/sc011Excel.jsp"),
			 @Result(name = "load_failure",  location = "/SellReportControl/SC011/sc011Excel.jsp")	
	}) 
	public String loadsc011Excel() {  //查询工资
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		this.lsDataSet=this.sc011Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate));
		return SystemFinal.LOAD_SUCCESS;

	}
	
	public void createExcel() throws WriteException, IOException
	{
		try
		{
			int excelLength0=6;
			//创建excel工作簿
			WritableWorkbook workbook= Workbook.createWorkbook(os);
			WritableSheet sheet =workbook.createSheet("项目消费业绩统计", 0);
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
			Label head=new Label(0,0,"项目消费业绩统计",titleFormate);
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
			
			Label str2=new Label(2,2,"美容项目业绩",titleFormate);
			sheet.addCell(str2);
			
			Label str3=new Label(3,2,"美发项目业绩",titleFormate);
			sheet.addCell(str3);
			
			Label str4=new Label(4,2,"美甲项目业绩",titleFormate);
			sheet.addCell(str4);
			
			Label str5=new Label(5,2,"烫染护项目业绩",titleFormate);
			sheet.addCell(str5);
			
			List lsName=this.sc011Service.loadPrjTypeName();
			List lsType=this.sc011Service.loadPrjType();
			if(lsName!=null && lsName.size()>0)
			{
				for(int i=0;i<lsName.size();i++)
				{
					sheet.addCell(new Label(6+i,2,lsName.get(i).toString(),titleFormate));
				}
			}
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				for(int i=0;i<this.lsDataSet.size();i++)
				{
					sheet.addCell(new Label(0,i+3,""+this.lsDataSet.get(i).getStrCompId()));
					sheet.addCell(new Label(1,i+3,""+this.lsDataSet.get(i).getStrCompName()));
					sheet.addCell(new Number(2,i+3,this.lsDataSet.get(i).getBeautPrjYeji().doubleValue()));
					sheet.addCell(new Number(3,i+3,this.lsDataSet.get(i).getHairPrjYeji().doubleValue()));
					sheet.addCell(new Number(4,i+3,this.lsDataSet.get(i).getFingerPrjYeji().doubleValue()));
					sheet.addCell(new Number(5,i+3,this.lsDataSet.get(i).getTrhPrjYeji().doubleValue()));
					if(lsType!=null && lsType.size()>0)
					{
						for(int k=0;k<lsType.size();k++)
						{
							sheet.addCell(new Number(6+k,i+3,CommonTool.GetGymAmt(CommonTool.FormatDouble(this.lsDataSet.get(i).getPrjTypesAmt()[k][0]),0),titleFormate));
						}
					}
				}
			}
			lsDataSet=null;
			lsName=null;
			lsType=null;
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
	@JSON(serialize=false)
	public SC011Service getSc011Service() {
		return sc011Service;
	}
	@JSON(serialize=false)
	public void setSc011Service(SC011Service sc011Service) {
		this.sc011Service = sc011Service;
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

	public List<ProjectTypeAnalysis> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<ProjectTypeAnalysis> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	public String getStrSearchType() {
		return strSearchType;
	}
	public void setStrSearchType(String strSearchType) {
		this.strSearchType = strSearchType;
	}

	
	
}
