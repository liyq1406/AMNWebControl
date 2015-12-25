package com.amani.action.SellReportControl;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
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



import com.amani.bean.SC012Bean;
import com.amani.service.SellReportControl.SC012Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/sc012")
public class SC012Action {
	@Autowired
	private SC012Service sc012Service;
	private String strCurCompId;
	private String strCurCompName;
	private String strSearchType;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private int	    searchType;
	private List<SC012Bean> lsDataSet;
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
		this.lsDataSet=this.sc012Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate),searchType);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadSC012Excel",  results = { 
			 @Result(name = "load_success", location = "/SellReportControl/SC012/sc012Excel.jsp"),
			 @Result(name = "load_failure",  location = "/SellReportControl/SC012/sc012Excel.jsp")	
	}) 
	public String loadSC012Excel() {  //查询工资
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		strCurCompName=this.sc012Service.getDataTool().loadCompNameById(strCurCompId);
		this.lsDataSet=this.sc012Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate),searchType);
		return SystemFinal.LOAD_SUCCESS;

	}
	
	public void createExcel() throws WriteException, IOException
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
			
			Label str2=new Label(2,2,"异动类型",titleFormate);
			     
			sheet.addCell(str2);
	
		      
			
			Label str3=new Label(3,2,"异动日期",titleFormate);
			sheet.addCell(str3);
			
			Label str4=new Label(4,2,"异动单号",titleFormate);
			sheet.addCell(str4);
			
			Label stra=new Label(5,2,"原卡号",titleFormate);
			sheet.addCell(stra);
			
			Label str5=new Label(6,2,"原卡类型",titleFormate);
			sheet.addCell(str5);
			
			Label str7=new Label(7,2,"类型名称",titleFormate);
			sheet.addCell(str7);
			
			Label str8=new Label(8,2,"新卡号",titleFormate);
			sheet.addCell(str8);
			
			Label str8x=new Label(9,2,"新卡类型",titleFormate);
			sheet.addCell(str8x);
			
			Label str9=new Label(10,2,"类型名称",titleFormate);
			sheet.addCell(str9);
			
			Label str11=new Label(11,2,"会员姓名",titleFormate);
			sheet.addCell(str11);
			
			Label str10=new Label(12,2,"异动金额",titleFormate);
			sheet.addCell(str10);
			
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				for(int i=0;i<this.lsDataSet.size();i++)
				{
					sheet.addCell(new Label(0,i+3,""+this.lsDataSet.get(i).getStrCompId()));
					sheet.addCell(new Label(1,i+3,""+this.lsDataSet.get(i).getStrCompName()));
					sheet.addCell(new Label(2,i+3,""+this.lsDataSet.get(i).getChangeTypename()));
					sheet.addCell(new Label(3,i+3,""+this.lsDataSet.get(i).getChangeDate()));
					sheet.addCell(new Label(4,i+3,""+this.lsDataSet.get(i).getChangeBillno()));
					sheet.addCell(new Label(5,i+3,""+this.lsDataSet.get(i).getOldCardNo()));
					sheet.addCell(new Label(6,i+3,""+this.lsDataSet.get(i).getOldCardType()));
					sheet.addCell(new Label(7,i+3,""+this.lsDataSet.get(i).getOldCardTypename()));
					sheet.addCell(new Label(8,i+3,""+this.lsDataSet.get(i).getNewCardNo()));
					sheet.addCell(new Label(9,i+3,""+this.lsDataSet.get(i).getNewCardType()));
					sheet.addCell(new Label(10,i+3,""+this.lsDataSet.get(i).getNewCardTypeName()));
					sheet.addCell(new Label(11,i+3,""+this.lsDataSet.get(i).getMembername()));
					sheet.addCell(new Number(12,i+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getChangeamt()).doubleValue()));
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
	@JSON(serialize=false)
	public SC012Service getSc012Service() {
		return sc012Service;
	}
	@JSON(serialize=false)
	public void setSc012Service(SC012Service sc012Service) {
		this.sc012Service = sc012Service;
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

	public List<SC012Bean> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<SC012Bean> lsDataSet) {
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
	public int getSearchType() {
		return searchType;
	}
	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}

	
	
}
