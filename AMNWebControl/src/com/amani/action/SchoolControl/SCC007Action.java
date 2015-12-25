package com.amani.action.SchoolControl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.amani.action.AMN_ReportAction;
import com.amani.bean.CommonBean;
import com.amani.model.Commoninfo;
import com.amani.service.SchoolControl.SCC007Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/scc007")
public class SCC007Action extends AMN_ReportAction{
	/**
	 * @Fields serialVersionUID : long  
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private SCC007Service scc007Service;
	private String creditNo;
	private String positionNo;
	private String staffNo;
	private List<CommonBean> dataSet;
	private OutputStream os;
	//默认查询
	@Action( value="loadDataSet", results={ @Result(type="json", name="load_success")})
	public String loadDataSet() {
		this.dataSet=this.scc007Service.loadDataSet(dateFrom, dateTo, creditNo, positionNo);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//导出Excel查询
	@Action(value = "loadExcel",  results = { 
			 @Result(name = "load_success", location = "/SchoolControl/SCC007/scc007Excel.jsp"),
			 @Result(name = "load_failure",  location = "/SchoolControl/SCC007/scc007Excel.jsp")	
	}) 
	public String loadExcel() {
		this.dataSet=this.scc007Service.loadDataSet(dateFrom, dateTo, creditNo, positionNo);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	public void createExcel() throws WriteException, IOException{
		try{
			int excelLength0=5;
			//创建excel工作簿
			WritableWorkbook workbook= Workbook.createWorkbook(os);
			
			//创建新的一页
			WritableSheet sheet =workbook.createSheet("员工培训经历汇总", 0);
			//构造表头
			sheet.mergeCells(0, 0, excelLength0-1, 0); //合并第一行，1到倒数第一行列
			WritableFont bold=new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);//设置字体种类和黑体显示
			
			WritableCellFormat titleFormate =new WritableCellFormat(bold);
			titleFormate.setAlignment(jxl.format.Alignment.CENTRE);//单元格的内容水平居中
			titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
			
			/*WritableCellFormat numberFormate =new WritableCellFormat();
			numberFormate.setAlignment(jxl.format.Alignment.RIGHT);//单元格的内容水平右对齐
			numberFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中*/
			
			jxl.write.WritableFont wfc = new jxl.write.WritableFont(WritableFont.ARIAL, 10, 
					WritableFont.NO_BOLD, false, 
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED); 
			WritableCellFormat totalNumberFormate =new WritableCellFormat(wfc);
			totalNumberFormate.setAlignment(jxl.format.Alignment.RIGHT);//单元格的内容水平右对齐
			totalNumberFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中

			/****创建要显示的内容***/
			//标题
			Label head=new Label(0,0,"员工培训经历汇总",titleFormate);
			sheet.setRowView(0, 500,false);
			sheet.addCell(head);
			
			//制表日期：
			Label makeDate=new Label(0,1,"制表日期：",titleFormate);
			sheet.addCell(makeDate);
			
			//制表日期值
			Label makeDateData=new Label(1, 1, CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet.addCell(makeDateData);
			Label str0=new Label(0,2,"门店",titleFormate);
			sheet.addCell(str0);
			Label str1=new Label(1,2,"姓名",titleFormate);
			sheet.addCell(str1);
			Label str2=new Label(2,2,"职位",titleFormate);
			sheet.addCell(str2);
			Label str3=new Label(3,2,"学习课程",titleFormate);
			sheet.addCell(str3);
			Label str4=new Label(4,2,"学习时间",titleFormate);
			sheet.addCell(str4);
			if(this.dataSet!=null && this.dataSet.size()>0){
				int row=3;
				Map<String, String> postion = new HashMap<String, String>();
				List<Commoninfo> list = this.scc007Service.getDataTool().loadCommonInfoByCode("GZGW");
				for (Commoninfo commoninfo : list) {//拉取工作岗位集合
					postion.put(commoninfo.getId().getParentcodekey(), commoninfo.getParentcodevalue());
				}
				for (CommonBean bean : dataSet) {
					sheet.addCell(new Label(0, row, bean.getAttr1()));
					sheet.addCell(new Label(1, row, bean.getAttr2()));
					sheet.addCell(new Label(2, row, postion.get(bean.getAttr3())));
					sheet.addCell(new Label(3, row, bean.getAttr4()));
					sheet.addCell(new Label(4, row, bean.getAttr5()));
					row++;
				}
			}
			dataSet=null;
			workbook.write();
			workbook.close();
			os.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	//明细默认查询
	@Action( value="loadDetailData", results={ @Result(type="json", name="load_success")})
	public String loadDetailData() {
		this.dataSet=this.scc007Service.loadDetailData(dateFrom, dateTo, staffNo, strMessage);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//导出Excel查询
	@Action(value = "loadDetailExcel",  results = { 
			@Result(name = "load_success", location = "/SchoolControl/SCC007/scc007Excel.jsp"),
			@Result(name = "load_failure",  location = "/SchoolControl/SCC007/scc007Excel.jsp")	
	}) 
	public String loadDetailExcel() {
		try {
			this.dataSet=this.scc007Service.loadDetailData(dateFrom, dateTo, staffNo, new String(strMessage.getBytes("ISO-8859-1"), "GBK"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	public void createDetailExcel() throws WriteException, IOException{
		try{
			int excelLength0=2;
			//创建excel工作簿
			WritableWorkbook workbook= Workbook.createWorkbook(os);
			
			//创建新的一页
			WritableSheet sheet =workbook.createSheet("员工培训经历明细", 0);
			//构造表头
			sheet.mergeCells(0, 0, excelLength0-1, 0); //合并第一行，1到倒数第一行列
			WritableFont bold=new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);//设置字体种类和黑体显示
			
			WritableCellFormat titleFormate =new WritableCellFormat(bold);
			titleFormate.setAlignment(jxl.format.Alignment.CENTRE);//单元格的内容水平居中
			titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
			
			jxl.write.WritableFont wfc = new jxl.write.WritableFont(WritableFont.ARIAL, 10, 
					WritableFont.NO_BOLD, false, 
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED); 
			WritableCellFormat totalNumberFormate =new WritableCellFormat(wfc);
			totalNumberFormate.setAlignment(jxl.format.Alignment.RIGHT);//单元格的内容水平右对齐
			totalNumberFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
			
			/****创建要显示的内容***/
			//标题
			Label head=new Label(0,0,"员工培训经历明细",titleFormate);
			sheet.setRowView(0, 500,false);
			sheet.addCell(head);
			
			//制表日期：
			Label makeDate=new Label(0,1,"制表日期：",titleFormate);
			sheet.addCell(makeDate);
			
			//制表日期值
			Label makeDateData=new Label(1, 1, CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet.addCell(makeDateData);
			Label str1=new Label(0,2,"学习课程",titleFormate);
			sheet.addCell(str1);
			Label str2=new Label(1,2,"学习时间",titleFormate);
			sheet.addCell(str2);
			if(this.dataSet!=null && this.dataSet.size()>0){
				int row=3;
				for (CommonBean bean : dataSet) {
					sheet.addCell(new Label(0, row, bean.getAttr1()));
					sheet.addCell(new Label(1, row, bean.getAttr2()));
					row++;
				}
			}
			dataSet=null;
			workbook.write();
			workbook.close();
			os.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getDateFrom() {
		return dateFrom;
	}
	@Override
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	@Override
	public String getDateTo() {
		return dateTo;
	}
	@Override
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public String getCreditNo() {
		return creditNo;
	}
	public void setCreditNo(String creditNo) {
		this.creditNo = creditNo;
	}
	public String getPositionNo() {
		return positionNo;
	}
	public void setPositionNo(String positionNo) {
		this.positionNo = positionNo;
	}
	public String getStaffNo() {
		return staffNo;
	}
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}
	@Override
	public String getStrMessage() {
		return strMessage;
	}
	@Override
	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}
	@JSON(serialize=false)
	public SCC007Service getScc007Service() {
		return scc007Service;
	}
	@JSON(serialize=false)
	public void setScc007Service(SCC007Service scc007Service) {
		this.scc007Service = scc007Service;
	}
	public List<CommonBean> getDataSet() {
		return dataSet;
	}
	public void setDataSet(List<CommonBean> dataSet) {
		this.dataSet = dataSet;
	}
	public OutputStream getOs() {
		return os;
	}
	public void setOs(OutputStream os) {
		this.os = os;
	}
}
