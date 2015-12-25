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

import com.amani.bean.PC024Bean;
import com.amani.service.PersonnelControl.PC024Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/pc024")
public class PC024Action {
	@Autowired
	private PC024Service pc024Service;
	private String strCurCompId;
	private String strSearchType;
	private String strMessage;
	private List<PC024Bean> lsDataSet;
	private OutputStream os;
    public OutputStream getOs() {
		return os;
	}
	public void setOs(OutputStream os) {
		this.os = os;
	}
	
	//默认查询
	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		this.lsDataSet=this.pc024Service.loadDateSetByCompId(strCurCompId);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//查询积分详情
	@Action( value="loadDetail", results={ @Result( type="json", name="load_success"),
			@Result( type="json", name="load_failure")})
	public String loadDetail() {
		this.lsDataSet=this.pc024Service.loadDetail(strSearchType);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//导出Excel查询
	@Action(value = "loadPC024Excel",  results = { 
			 @Result(name = "load_success", location = "/PersonnelControl/PC024/pc024Excel.jsp"),
			 @Result(name = "load_failure",  location = "/PersonnelControl/PC024/pc024Excel.jsp")	
	}) 
	public String loadPC024Excel() {
		this.lsDataSet=this.pc024Service.loadDateSetByCompId(strCurCompId);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	public void createExcel() throws WriteException, IOException
	{
		try
		{
			int excelLength0=6;
			//创建excel工作簿
			WritableWorkbook workbook= Workbook.createWorkbook(os);
			
			//创建新的一页
			WritableSheet sheet =workbook.createSheet("员工积分统计", 0);
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
			Label head=new Label(0,0,"员工积分统计",titleFormate);
			sheet.setRowView(0, 500,false);
			sheet.addCell(head);
			
			//制表日期：
			Label makeDate=new Label(0,1,"制表日期：",titleFormate);
			sheet.addCell(makeDate);
			
			//制表日期值
			Label makeDateData=new Label(1,1,""+CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet.addCell(makeDateData);
			
			Label str0=new Label(0,2,"门店编号",titleFormate);
			sheet.addCell(str0);
			
			Label str1=new Label(1,2,"门店名称",titleFormate);
			sheet.addCell(str1);
			
			Label str2=new Label(2,2,"员工编号",titleFormate);
			sheet.addCell(str2);
	
			Label str3=new Label(3,2,"员工名称",titleFormate);
			sheet.addCell(str3);
			
			Label str4=new Label(4,2,"员工职位",titleFormate);
			sheet.addCell(str4);
			
			Label str5=new Label(5,2,"积分",titleFormate);
			sheet.addCell(str5);
			
			int row=3;
			for (PC024Bean bean : lsDataSet) {
				sheet.addCell(new Label(0,row,bean.getCompno()));
				sheet.addCell(new Label(1,row,bean.getCompname()));
				sheet.addCell(new Label(2,row,bean.getStaffno()));
				sheet.addCell(new Label(3,row,bean.getStaffname()));
				sheet.addCell(new Label(4,row,bean.getPosition()));
				sheet.addCell(new Label(5,row,String.valueOf(bean.getCredit())));
				row++;
				List<PC024Bean> list=this.pc024Service.loadDetail(bean.getStaffno());
				for (PC024Bean detail : list) {
					sheet.addCell(new Label(0,row,detail.getClassify()));
					sheet.addCell(new Label(1,row,String.valueOf(detail.getNumber())));
					sheet.addCell(new Label(2,row,detail.getUnit()));
					sheet.addCell(new Label(5,row,String.valueOf(detail.getCredit())));
					row++;
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
	public List<PC024Bean> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<PC024Bean> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	public String getStrSearchType() {
		return strSearchType;
	}
	public void setStrSearchType(String strSearchType) {
		this.strSearchType = strSearchType;
	}
	@JSON(serialize=false)
	public PC024Service getPc024Service() {
		return pc024Service;
	}
	@JSON(serialize=false)
	public void setPc024Service(PC024Service pc024Service) {
		this.pc024Service = pc024Service;
	}
}
