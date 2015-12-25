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



import com.amani.bean.SC013Bean;
import com.amani.service.SellReportControl.SC013Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/sc013")
public class SC013Action {
	@Autowired
	private SC013Service sc013Service;
	private String strCurCompId;
	private String strCurCompName;
	private String strSearchType;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private int	    searchType;
	private List<SC013Bean> lsDataSet;
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
		this.lsDataSet=this.sc013Service.loadDateSetByCompId(strCurCompId, CommonTool.FormatString(strFromDate));
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadSC013Excel",  results = { 
			 @Result(name = "load_success", location = "/SellReportControl/SC013/sc013Excel.jsp"),
			 @Result(name = "load_failure",  location = "/SellReportControl/SC013/sc013Excel.jsp")	
	}) 
	public String loadSC013Excel() {  //查询工资
		strCurCompName=this.sc013Service.getDataTool().loadCompNameById(strCurCompId);
		this.lsDataSet=this.sc013Service.loadDateSetByCompId(strCurCompId, CommonTool.FormatString(strFromDate));
		return SystemFinal.LOAD_SUCCESS;

	}
	
	public void createExcel() throws WriteException, IOException
	{
		try
		{
			int excelLength0=17;
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
			Label head=new Label(0,0,strCurCompId+"["+strCurCompName+"]"+"历史数据分析",titleFormate);
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
			
			Label str1=new Label(1,2,"项目名称",titleFormate);
			sheet.addCell(str1);
			
			Label str2=new Label(2,2,"1月",titleFormate);
			     
			sheet.addCell(str2);
	
		      
			
			Label str3=new Label(3,2,"2月",titleFormate);
			sheet.addCell(str3);
			
			Label str4=new Label(4,2,"3月",titleFormate);
			sheet.addCell(str4);
			
			Label stra=new Label(5,2,"4月",titleFormate);
			sheet.addCell(stra);
			
			Label str5=new Label(6,2,"5月",titleFormate);
			sheet.addCell(str5);
			
			Label str7=new Label(7,2,"6月",titleFormate);
			sheet.addCell(str7);
			
			Label str8=new Label(8,2,"7月",titleFormate);
			sheet.addCell(str8);
			
			Label str8x=new Label(9,2,"8月",titleFormate);
			sheet.addCell(str8x);
			
			Label str9=new Label(10,2,"9月",titleFormate);
			sheet.addCell(str9);
			
			Label str11=new Label(11,2,"10月",titleFormate);
			sheet.addCell(str11);
			
			Label str10=new Label(12,2,"11月",titleFormate);
			sheet.addCell(str10);
			
			Label str12=new Label(13,2,"12月",titleFormate);
			sheet.addCell(str12);
			
			Label str13=new Label(14,2,"年总计",titleFormate);
			sheet.addCell(str13);
			
			Label str14=new Label(15,2,"公司前五门店平均",titleFormate);
			sheet.addCell(str14);
			
			Label str15=new Label(16,2,"单店月平均",titleFormate);
			sheet.addCell(str15);
			
			Label str16=new Label(17,2,"公司后五门店平均",titleFormate);
			sheet.addCell(str16);
			
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				for(int i=0;i<this.lsDataSet.size();i++)
				{
					sheet.addCell(new Label(0,i+3,""+this.lsDataSet.get(i).getStrCompName()));
					sheet.addCell(new Label(1,i+3,""+this.lsDataSet.get(i).getStrPrjName()));
					sheet.addCell(new Label(2,i+3,""+this.lsDataSet.get(i).getMonth1num()));
					sheet.addCell(new Label(3,i+3,""+this.lsDataSet.get(i).getMonth2num()));
					sheet.addCell(new Label(4,i+3,""+this.lsDataSet.get(i).getMonth3num()));
					sheet.addCell(new Label(5,i+3,""+this.lsDataSet.get(i).getMonth4num()));
					sheet.addCell(new Label(6,i+3,""+this.lsDataSet.get(i).getMonth5num()));
					sheet.addCell(new Label(7,i+3,""+this.lsDataSet.get(i).getMonth6num()));
					sheet.addCell(new Label(8,i+3,""+this.lsDataSet.get(i).getMonth7num()));
					sheet.addCell(new Label(9,i+3,""+this.lsDataSet.get(i).getMonth8num()));
					sheet.addCell(new Label(10,i+3,""+this.lsDataSet.get(i).getMonth9num()));
					sheet.addCell(new Label(11,i+3,""+this.lsDataSet.get(i).getMonth10num()));
					sheet.addCell(new Label(12,i+3,""+this.lsDataSet.get(i).getMonth11num()));
					sheet.addCell(new Label(13,i+3,""+this.lsDataSet.get(i).getMonth12num()));
					sheet.addCell(new Label(14,i+3,""+this.lsDataSet.get(i).getSummonthnum()));
					sheet.addCell(new Label(15,i+3,""+this.lsDataSet.get(i).getTotalavgbefor()));
					sheet.addCell(new Label(16,i+3,""+this.lsDataSet.get(i).getCompavgbymonth()));
					sheet.addCell(new Label(17,i+3,""+this.lsDataSet.get(i).getTotalavgafter()));
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
	public SC013Service getSc013Service() {
		return sc013Service;
	}
	@JSON(serialize=false)
	public void setSc013Service(SC013Service sc013Service) {
		this.sc013Service = sc013Service;
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

	public List<SC013Bean> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<SC013Bean> lsDataSet) {
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
