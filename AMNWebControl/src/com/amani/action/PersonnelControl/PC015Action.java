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

import com.amani.bean.StaffinfoAnalysis;
import com.amani.service.PersonnelControl.PC015Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/pc015")
public class PC015Action {
	@Autowired
	private PC015Service pc015Service;
	private String strCurCompId;
	private String strCurCompName;
	private int searchType;
	private int procType;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private List<StaffinfoAnalysis> lsDataSet;
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
		execProc();
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadpc015Excel",  results = { 
			 @Result(name = "load_success", location = "/PersonnelControl/PC015/pc015Excel.jsp"),
			 @Result(name = "load_failure",  location = "/PersonnelControl/PC015/pc015Excel.jsp")	
	}) 
	public String loadpc015Excel() {
		execProc();
		return SystemFinal.LOAD_SUCCESS;
	}
	
	private void execProc(){
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		String proc = null;
		if(procType!=0){
			//入职率、离职率汇总、明细之间切换
			proc = procType==1?"upg_position_analisisbycomp_bymonth_detail":"upg_position_analisisbycomp_bymonth";
			//Excel下载文件名
			strCurCompName = searchType==1?"入职率统计":"离职率统计";
		}else{//门店职位异动统计
			proc = "upg_position_analisisbycomp";
			strCurCompName="门店职位异动统计";
		}
		this.lsDataSet = this.pc015Service.loadDateSetByCompId(proc,
				strCurCompId, searchType, CommonTool.setDateMask(strFromDate),
				CommonTool.setDateMask(strToDate), procType);
	}
	
	public void createExcel() throws WriteException, IOException
	{
		try
		{
			int excelLength0=20;
			//创建excel工作簿
			WritableWorkbook workbook= Workbook.createWorkbook(os);
			WritableSheet sheet =workbook.createSheet(strCurCompName, 0);
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
			Label head=new Label(0,0,strCurCompName,titleFormate);
			sheet.setRowView(0, 500,false);
			sheet.addCell(head);
			
			//制表日期：
			Label makeDate=new Label(0,1,"制表日期：",titleFormate);
			sheet.addCell(makeDate);
			
			//制表日期值
			Label makeDateData=new Label(1,1,""+CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet.addCell(makeDateData);
			
			Label str0=new Label(0,2,"编号",titleFormate);
			sheet.addCell(str0);
			
			Label str1=new Label(1,2,"门店",titleFormate);
			sheet.addCell(str1);
			List lsName=this.pc015Service.loadPrjTypeName();
			List lsType=this.pc015Service.loadPrjType();
			//入职率、离职率增加日期字段
			int add = 2;
			int count = lsName==null ? 0 : lsName.size();
			if(procType==1){
				add=3;
				Label str3=new Label(2,2,"日期",titleFormate);
				sheet.addCell(str3);
			}
			for(int i=0;i<count;i++){
				sheet.addCell(new Label(add+i,2,lsName.get(i).toString(),titleFormate));
			}
			Label stra=new Label((add+count),2,"合计",titleFormate);
			sheet.addCell(stra);
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				int typeCount = lsType==null ? 0 : lsType.size();
				for(int i=0;i<this.lsDataSet.size();i++){
					sheet.addCell(new Label(0,i+3,""+this.lsDataSet.get(i).getStrCompId()));
					sheet.addCell(new Label(1,i+3,""+this.lsDataSet.get(i).getStrCompName()));
					if(procType==1){
						sheet.addCell(new Label(2,i+3,this.lsDataSet.get(i).getStrDate()));
					}
					for(int k=0;k<typeCount;k++){
						sheet.addCell(new Label(add+k,i+3,this.lsDataSet.get(i).getPrjTypesAmt()[k][0],numberFormate));
					}
					sheet.addCell(new Label((add+typeCount),i+3,this.lsDataSet.get(i).getTotalCount(),numberFormate));
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
	public List<StaffinfoAnalysis> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<StaffinfoAnalysis> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	@JSON(serialize=false)
	public PC015Service getPc015Service() {
		return pc015Service;
	}
	@JSON(serialize=false)
	public void setPc015Service(PC015Service pc015Service) {
		this.pc015Service = pc015Service;
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
	public int getProcType() {
		return procType;
	}
	public void setProcType(int procType) {
		this.procType = procType;
	}
}
