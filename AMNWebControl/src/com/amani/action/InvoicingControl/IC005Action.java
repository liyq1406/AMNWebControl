package com.amani.action.InvoicingControl;

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


import com.amani.bean.GoodsInserorOuterBean;
import com.amani.service.InvoicingControl.IC005Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ic005")
public class IC005Action {
	@Autowired
	private IC005Service ic005Service;
	private String strCurCompId;
	private String strCurCompName;
	private String strFromDate;
	private String strToDate;
	private String strFromGoodsNo;
	private String strToGoodsNo;
	private String strCurGoodsNo;
	private String strWareId;
	private String searchGoodsType;
	private String strMessage;
	private List<GoodsInserorOuterBean> lsDataSet;

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
		this.lsDataSet=this.ic005Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate),strFromGoodsNo,strToGoodsNo,searchGoodsType);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	
	@Action( value="reloadGoodsStock", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String reloadGoodsStock() {
		this.strMessage="";
		if(this.ic005Service.getDataTool().hasFunctionRights(CommonTool.getLoginInfo("USERID"), "IC005", SystemFinal.UR_SPECIAL_CHECK)==false)
		{
			this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
			return SystemFinal.LOAD_SUCCESS;
		}
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		boolean flag=this.ic005Service.reloadStockByGoodsNo(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate),this.strCurGoodsNo);
		if(flag==false)
			this.strMessage="结算失败,请确认!";
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "loadic005Excel",  results = { 
			 @Result(name = "load_success", location = "/InvoicingControl/IC005/ic005Excel.jsp"),
			 @Result(name = "load_failure",  location = "/InvoicingControl/IC005/ic005Excel.jsp")	
	}) 
	public String loadic005Excel() {  //查询工资
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		this.lsDataSet=this.ic005Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate),strFromGoodsNo,strToGoodsNo,searchGoodsType);
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
			Label head=new Label(0,0,strCurCompId+"["+strCurCompName+"]"+"门店出入库明细",titleFormate);
			sheet.setRowView(0, 500,false);
			sheet.addCell(head);
			
			//制表日期：
			Label makeDate=new Label(0,1,"制表日期：",titleFormate);
			sheet.addCell(makeDate);
			
			
			//制表日期值
			Label makeDateData=new Label(1,1,""+CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet.addCell(makeDateData);
			
			Label str0x=new Label(0,2,"物品大类",titleFormate);
			sheet.addCell(str0x);
			
			Label str0=new Label(1,2,"物品编号",titleFormate);
			sheet.addCell(str0);
			
			Label str1=new Label(2,2,"物品名称",titleFormate);
			sheet.addCell(str1);
			
			Label str2=new Label(3,2,"物品单位",titleFormate);
			     
			sheet.addCell(str2);
	
		      
			
			Label str3=new Label(4,2,"期初量",titleFormate);
			sheet.addCell(str3);
			
			Label str4=new Label(5,2,"期初金额",titleFormate);
			sheet.addCell(str4);
			
			Label stra=new Label(6,2,"入库量",titleFormate);
			sheet.addCell(stra);
			
			Label str5=new Label(7,2,"入库金额",titleFormate);
			sheet.addCell(str5);
			
			
			Label strax=new Label(8,2,"赠送量",titleFormate);
			sheet.addCell(strax);
			
			Label str5ax=new Label(9,2,"赠送金额",titleFormate);
			sheet.addCell(str5ax);
			

			Label str5x=new Label(10,2,"退货量",titleFormate);
			sheet.addCell(str5x);
			
			Label str7x=new Label(11,2,"退货金额",titleFormate);
			sheet.addCell(str7x);
			
			Label str7=new Label(12,2,"出库量",titleFormate);
			sheet.addCell(str7);
			
			Label str8=new Label(13,2,"出库金额",titleFormate);
			sheet.addCell(str8);
			
			Label str8xx=new Label(14,2,"出库成本",titleFormate);
			sheet.addCell(str8xx);
			
			Label str8x=new Label(15,2,"实际库存量",titleFormate);
			sheet.addCell(str8x);
			
			Label str9=new Label(16,2,"实际库存金额",titleFormate);
			sheet.addCell(str9);
			
			Label str10=new Label(17,2,"待发货量",titleFormate);
			sheet.addCell(str10);
			
			Label str11=new Label(18,2,"待发货金额",titleFormate);
			sheet.addCell(str11);
			
			
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				for(int i=0;i<this.lsDataSet.size();i++)
				{
					sheet.addCell(new Label(0,i+3,""+this.lsDataSet.get(i).getStrGoodsClassName()));
					sheet.addCell(new Label(1,i+3,""+this.lsDataSet.get(i).getStrGoodsNo()));
					sheet.addCell(new Label(2,i+3,""+this.lsDataSet.get(i).getStrGoodsName()));
					sheet.addCell(new Label(3,i+3,""+this.lsDataSet.get(i).getStrGoodsUnit()));
					sheet.addCell(new Number(4,i+3,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getBBeginGoodsCount()).doubleValue()));
					sheet.addCell(new Number(5,i+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBBeginGoodsAmt()).doubleValue()));
					sheet.addCell(new Number(6,i+3,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getBInserGoodsCount()).doubleValue()));
					sheet.addCell(new Number(7,i+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBInserGoodsAmt()).doubleValue()));
					sheet.addCell(new Number(8,i+3,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getBHandSelGoodsCount()).doubleValue()));
					sheet.addCell(new Number(9,i+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBHandSelGoodsAmt()).doubleValue()));
					sheet.addCell(new Number(10,i+3,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getBQuitGoodsCount()).doubleValue()));
					sheet.addCell(new Number(11,i+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBQuitGoodsAmt()).doubleValue()));
					sheet.addCell(new Number(12,i+3,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getBOuterGoodsCount()).doubleValue()));
					sheet.addCell(new Number(13,i+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBOuterGoodsAmt()).doubleValue()));
					sheet.addCell(new Number(14,i+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBCostGoodsAmt()).doubleValue()));
					sheet.addCell(new Number(15,i+3,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getBEndGoodsCount()).doubleValue()));
					sheet.addCell(new Number(16,i+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBEndGoodsAmt()).doubleValue()));
					sheet.addCell(new Number(17,i+3,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getBSendGoodsCount()).doubleValue()));
					sheet.addCell(new Number(18,i+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBSendGoodsAmt()).doubleValue()));
					
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
	public IC005Service getIc005Service() {
		return ic005Service;
	}
	@JSON(serialize=false)
	public void setIc005Service(IC005Service ic005Service) {
		this.ic005Service = ic005Service;
	}




	public List<GoodsInserorOuterBean> getLsDataSet() {
		return lsDataSet;
	}

	public void setLsDataSet(List<GoodsInserorOuterBean> lsDataSet) {
		this.lsDataSet = lsDataSet;
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
	public String getStrCurCompName() {
		return strCurCompName;
	}
	public void setStrCurCompName(String strCurCompName) {
		this.strCurCompName = strCurCompName;
	}
	public String getSearchGoodsType() {
		return searchGoodsType;
	}
	public void setSearchGoodsType(String searchGoodsType) {
		this.searchGoodsType = searchGoodsType;
	}
	public String getStrCurGoodsNo() {
		return strCurGoodsNo;
	}
	public void setStrCurGoodsNo(String strCurGoodsNo) {
		this.strCurGoodsNo = strCurGoodsNo;
	}



	
	
	
}
