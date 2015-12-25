package com.amani.action.InvoicingControl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
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

import com.amani.bean.GoodsStockBean;

import com.amani.model.Dgoodsinsertpc;
import com.amani.service.InvoicingControl.IC014Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ic014")
public class IC014Action {
	@Autowired
	private IC014Service ic014Service;
	private String strCurCompId;
	private String strCurCompName;
	private String strFromDate;
	private String strToDate;
	private String strFromGoodsNo;
	private String strToGoodsNo;
	private String strWareId;
	private String strMessage;
	private List<GoodsStockBean> lsDataSet;
	private List<Dgoodsinsertpc> lsPcDataSet;

    private OutputStream os;
	public OutputStream getOs() {
		return os;
	}
	public void setOs(OutputStream os) {
		this.os = os;
	}
	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		this.lsDataSet=this.ic014Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate),strWareId,this.strFromGoodsNo,this.strToGoodsNo );
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action( value="loadPcDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadPcDataSet() {
		
		this.lsPcDataSet=this.ic014Service.loadPcDateSetByCompId(strCurCompId,strWareId,this.strFromGoodsNo,this.strToGoodsNo );
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "loadic014Excel",  results = { 
			 @Result(name = "load_success", location = "/InvoicingControl/IC014/ic014Excel.jsp"),
			 @Result(name = "load_failure",  location = "/InvoicingControl/IC014/ic014Excel.jsp")	
	}) 
	public String loadic014Excel() {  //查询工资
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		this.lsDataSet=this.ic014Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate),strWareId,this.strFromGoodsNo,this.strToGoodsNo );
		return SystemFinal.LOAD_SUCCESS;

	}
	
	public void createExcel() throws WriteException, IOException
	{
		try
		{
			//创建excel工作簿
			WritableWorkbook workbook= Workbook.createWorkbook(os);
			
			//创建新的一页
			WritableSheet sheet =workbook.createSheet("First Sheet", 0);
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
			Label head=new Label(0,0,strCurCompId+"["+strCurCompName+"]"+"门店人事统计",titleFormate);
			sheet.setRowView(0, 500,false);
			sheet.addCell(head);
			
			//制表日期：
			Label makeDate=new Label(0,1,"制表日期：",titleFormate);
			sheet.addCell(makeDate);
			
			
			//制表日期值
			Label makeDateData=new Label(1,1,""+CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet.addCell(makeDateData);
			
			Label str0=new Label(0,2,"物品编号",titleFormate);
			sheet.addCell(str0);
			
			Label str1=new Label(1,2,"物品名称",titleFormate);
			sheet.addCell(str1);
			
			Label str2=new Label(2,2,"物品单位",titleFormate);
			     
			sheet.addCell(str2);
			
			Label str3=new Label(3,2,"数量",titleFormate);
			sheet.addCell(str3);
			
			Label str4=new Label(4,2,"单价",titleFormate);
			sheet.addCell(str4);
			
			Label stra=new Label(5,2,"金额",titleFormate);
			sheet.addCell(stra);
		
			
			
			Label labname=null;
			WritableCellFeatures wcf1=null;
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				for(int i=0;i<this.lsDataSet.size();i++)
				{
					sheet.addCell(new Label(0,i+3,""+this.lsDataSet.get(i).getStrGoodsNo()));
					sheet.addCell(new Label(1,i+3,""+this.lsDataSet.get(i).getStrGoodsName()));
					sheet.addCell(new Label(2,i+3,""+this.lsDataSet.get(i).getStrGoodsUnit()));
					sheet.addCell(new Label(3,i+3,""+CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getBGoodsCount())));
					sheet.addCell(new Label(4,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBGoodsPrice())));
					sheet.addCell(new Label(5,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBGoodsAmt())));
					
				}
			}
			lsDataSet=null;
			labname=null;
			wcf1=null;
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

	
	public List<GoodsStockBean> getLsDataSet() {
		return lsDataSet;
	}
	@JSON(serialize=false)
	public IC014Service getIc014Service() {
		return ic014Service;
	}
	@JSON(serialize=false)
	public void setIc014Service(IC014Service ic014Service) {
		this.ic014Service = ic014Service;
	}
	public String getStrFromGoodsNo() {
		return strFromGoodsNo;
	}
	public void setStrFromGoodsNo(String strFromGoodsNo) {
		this.strFromGoodsNo = strFromGoodsNo;
	}
	public String getStrToGoodsNo() {
		return strToGoodsNo;
	}
	public void setStrToGoodsNo(String strToGoodsNo) {
		this.strToGoodsNo = strToGoodsNo;
	}
	public String getStrWareId() {
		return strWareId;
	}
	public void setStrWareId(String strWareId) {
		this.strWareId = strWareId;
	}
	public void setLsDataSet(List<GoodsStockBean> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	public List<Dgoodsinsertpc> getLsPcDataSet() {
		return lsPcDataSet;
	}
	public void setLsPcDataSet(List<Dgoodsinsertpc> lsPcDataSet) {
		this.lsPcDataSet = lsPcDataSet;
	}
	public String getStrCurCompName() {
		return strCurCompName;
	}
	public void setStrCurCompName(String strCurCompName) {
		this.strCurCompName = strCurCompName;
	}

	
	
}
