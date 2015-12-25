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

import com.amani.bean.GoodsInserTypeAnalysisBean;
import com.amani.service.SellReportControl.SC014Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/sc014")
public class SC014Action {
	@Autowired
	private SC014Service sc014Service;
	private String strCurCompId;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private OutputStream os;
	private List<GoodsInserTypeAnalysisBean> lsDataSet;


	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		this.lsDataSet=this.sc014Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate));
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadsc014Excel", results = {
			@Result(name = "load_success", location = "/SellReportControl/SC014/sc014Excel.jsp"),
			@Result(name = "load_failure", location = "/SellReportControl/SC014/sc014Excel.jsp") })
	public String loadsc014Excel() {
		this.lsDataSet = this.sc014Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate));
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	
	
	
	
	public void createExcel() throws WriteException, IOException
	{
		try
		{
			int excelLength0=6;
			//创建excel工作簿
			WritableWorkbook workbook= Workbook.createWorkbook(os);
			WritableSheet sheet =workbook.createSheet("卖品统计", 0);
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
			Label head=new Label(0,0,"卖品统计",titleFormate);
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

			List lsName=this.sc014Service.loadPrjTypeName();
			List lsType=this.sc014Service.loadPrjType();
			if(lsName!=null && lsName.size()>0)
			{
				for(int i=0;i<lsName.size();i++)
				{
					sheet.addCell(new Label(2+i,2,lsName.get(i).toString(),titleFormate));
				}
			}
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				for(int i=0;i<this.lsDataSet.size();i++)
				{
					sheet.addCell(new Label(0,i+3,""+this.lsDataSet.get(i).getStrCompId()));
					sheet.addCell(new Label(1,i+3,""+this.lsDataSet.get(i).getStrCompName()));
					if(lsType!=null && lsType.size()>0)
					{
						for(int k=0;k<lsType.size();k++)
						{
							sheet.addCell(new Number(2+k,i+3,CommonTool.GetGymAmt(CommonTool.FormatDouble(this.lsDataSet.get(i).getGoodsInserTypesAmt()[k][0]),0),titleFormate));
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
	public SC014Service getSc014Service() {
		return sc014Service;
	}
	@JSON(serialize=false)
	public void setSc014Service(SC014Service sc014Service) {
		this.sc014Service = sc014Service;
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
	public List<GoodsInserTypeAnalysisBean> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<GoodsInserTypeAnalysisBean> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	public OutputStream getOs() {
		return os;
	}
	public void setOs(OutputStream os) {
		this.os = os;
	}



	
	
}
