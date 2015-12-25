package com.amani.action.PersonnelControl;

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

import com.amani.model.Staffinfo;
import com.amani.service.PersonnelControl.PC011Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/pc011")
public class PC011Action {
	@Autowired
	private PC011Service pc011Service;
	private String strCurCompId;
	private String strSearchType;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private List<Staffinfo> lsDataSet;
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
		this.lsDataSet=this.pc011Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate));
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "loadPC011Excel",  results = { 
			 @Result(name = "load_success", location = "/PersonnelControl/PC011/pc011Excel.jsp"),
			 @Result(name = "load_failure",  location = "/PersonnelControl/PC011/pc011Excel.jsp")	
	}) 
	public String loadPC011Excel() {  //查询工资
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();

		this.lsDataSet=this.pc011Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate));
		return SystemFinal.LOAD_SUCCESS;

	}
	
	public void createExcel() throws WriteException, IOException
	{
		try
		{
			int excelLength0=7;
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
			Label head=new Label(0,0,"零业绩统计",titleFormate);
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
			
			Label str2=new Label(2,2,"员工编号",titleFormate);
			     
			sheet.addCell(str2);
	
		      
			
			Label str3=new Label(3,2,"员工名称",titleFormate);
			sheet.addCell(str3);
			
			Label str4=new Label(4,2,"内部编号",titleFormate);
			sheet.addCell(str4);
			
			Label stra=new Label(5,2,"员工部门",titleFormate);
			sheet.addCell(stra);
			
			Label str5=new Label(6,2,"员工职位",titleFormate);
			sheet.addCell(str5);
			
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				for(int i=0;i<this.lsDataSet.size();i++)
				{
					sheet.addCell(new Label(0,i+3,""+this.lsDataSet.get(i).getBcompno()));
					sheet.addCell(new Label(1,i+3,""+this.lsDataSet.get(i).getBcompname()));
					sheet.addCell(new Label(2,i+3,""+this.lsDataSet.get(i).getBstaffno()));
					sheet.addCell(new Label(3,i+3,""+this.lsDataSet.get(i).getStaffname()));
					sheet.addCell(new Label(4,i+3,""+this.lsDataSet.get(i).getManageno()));
					sheet.addCell(new Label(5,i+3,""+this.lsDataSet.get(i).getDepartment()));
					sheet.addCell(new Label(6,i+3,""+this.lsDataSet.get(i).getPosition()));
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
	public List<Staffinfo> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<Staffinfo> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	public String getStrSearchType() {
		return strSearchType;
	}
	public void setStrSearchType(String strSearchType) {
		this.strSearchType = strSearchType;
	}
	@JSON(serialize=false)
	public PC011Service getPc011Service() {
		return pc011Service;
	}
	@JSON(serialize=false)
	public void setPc011Service(PC011Service pc011Service) {
		this.pc011Service = pc011Service;
	}

	
	
}
