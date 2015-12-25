package com.amani.action.InvoicingControl;

import com.amani.bean.GoodsInserTypeAnalysisBean;
import com.amani.bean.GoodsOuterAnalysisBean;
import com.amani.service.InvoicingControl.IC016Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

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

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ic016")
public class IC016Action
{

  @Autowired
  private IC016Service ic016Service;
  private String strCurCompId;
  private String strFromDate;
  private String strToDate;
  private String strFromGoodsNo;
  private String strToGoodsNo;
  private String strWareId;
  private String strMessage;
  private String searchGoodsType;
  private List<GoodsOuterAnalysisBean> lsDataSet;
  private List<GoodsInserTypeAnalysisBean> lsTypeDataSet;
  private OutputStream os;
	public OutputStream getOs() {
		return os;
	}
	public void setOs(OutputStream os) {
		this.os = os;
	}
  @Action(value="loadDataSet", results={@org.apache.struts2.convention.annotation.Result(type="json", name="load_success"), @org.apache.struts2.convention.annotation.Result(type="json", name="load_failure")})
  public String loadDataSet()
  {
    if (this.strFromDate.equals(""))
      this.strFromDate = CommonTool.getCurrDate();
    if (this.strToDate.equals(""))
      this.strToDate = CommonTool.getCurrDate();
    this.lsDataSet = this.ic016Service.loadDateSetByCompId(this.strCurCompId, CommonTool.setDateMask(this.strFromDate), CommonTool.setDateMask(this.strToDate), this.strFromGoodsNo, this.strToGoodsNo,searchGoodsType);
    this.lsTypeDataSet = this.ic016Service.loadTypeDateSetByCompId(this.strCurCompId, CommonTool.setDateMask(this.strFromDate), CommonTool.setDateMask(this.strToDate), this.strFromGoodsNo, this.strToGoodsNo);
    return "load_success";
  }
  
  
  @Action(value = "loadic016Excel",  results = { 
			 @Result(name = "load_success", location = "/InvoicingControl/IC016/ic016Excel.jsp"),
			 @Result(name = "load_failure",  location = "/InvoicingControl/IC016/ic016Excel.jsp")	
	}) 
	public String loadic016Excel() {  //查询工资
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		this.lsDataSet = this.ic016Service.loadDateSetByCompId(this.strCurCompId, CommonTool.setDateMask(this.strFromDate), CommonTool.setDateMask(this.strToDate), this.strFromGoodsNo, this.strToGoodsNo,searchGoodsType);
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
			Label head=new Label(0,0,"门店出库明细",titleFormate);
			sheet.setRowView(0, 500,false);
			sheet.addCell(head);
			
			//制表日期：
			Label makeDate=new Label(0,1,"制表日期：",titleFormate);
			sheet.addCell(makeDate);
			
			
			//制表日期值
			Label makeDateData=new Label(1,1,""+CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet.addCell(makeDateData);
			
			Label str0x=new Label(0,2,"物品编号",titleFormate);
			sheet.addCell(str0x);
			
			Label str0=new Label(1,2,"物品名称",titleFormate);
			sheet.addCell(str0);
			
			Label str1=new Label(2,2,"物品单位",titleFormate);
			sheet.addCell(str1);
			
			Label str2=new Label(3,2,"物品类别",titleFormate);
			sheet.addCell(str2);
			Label str3=new Label(4,2,"出库日期",titleFormate);
			sheet.addCell(str3);
			
			Label str4=new Label(5,2,"出库方式",titleFormate);
			sheet.addCell(str4);
			
			Label stra=new Label(6,2,"出库单号",titleFormate);
			sheet.addCell(stra);
			
			Label str5=new Label(7,2,"门店",titleFormate);
			sheet.addCell(str5);
			
			
			Label strax=new Label(8,2,"门店名称",titleFormate);
			sheet.addCell(strax);
			
			Label str5ax=new Label(9,2,"数量",titleFormate);
			sheet.addCell(str5ax);
			

			Label str5x=new Label(10,2,"出库单价",titleFormate);
			sheet.addCell(str5x);
			
			Label str7x=new Label(11,2,"出库金额",titleFormate);
			sheet.addCell(str7x);
			
			Label str7=new Label(12,2,"成本价",titleFormate);
			sheet.addCell(str7);
			
			Label str8=new Label(13,2,"成本金额",titleFormate);
			sheet.addCell(str8);
			
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				for(int i=0;i<this.lsDataSet.size();i++)
				{
					sheet.addCell(new Label(0,i+3,""+this.lsDataSet.get(i).getStrGoodsId()));
					sheet.addCell(new Label(1,i+3,""+this.lsDataSet.get(i).getStrGoodsName()));
					sheet.addCell(new Label(2,i+3,""+this.lsDataSet.get(i).getStrGoodsUnit()));
					sheet.addCell(new Label(3,i+3,""+this.lsDataSet.get(i).getStrGoodsType()));
					sheet.addCell(new Label(4,i+3,""+this.lsDataSet.get(i).getStrOuterDate()));
					sheet.addCell(new Label(5,i+3,""+this.lsDataSet.get(i).getStrOutOptionName()));
					sheet.addCell(new Label(6,i+3,""+this.lsDataSet.get(i).getStrOuterBillId()));
					sheet.addCell(new Label(7,i+3,""+this.lsDataSet.get(i).getStrReciverCompId()));
					sheet.addCell(new Label(8,i+3,""+this.lsDataSet.get(i).getStrReciverCompName()));
					sheet.addCell(new Number(9,i+3,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getBOuterCount()).doubleValue()));
					sheet.addCell(new Number(10,i+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBOuterPrice()).doubleValue()));
					sheet.addCell(new Number(11,i+3,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getBOuterAmt()).doubleValue()));
					sheet.addCell(new Number(12,i+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBOuterCostPrice()).doubleValue()));
					sheet.addCell(new Number(13,i+3,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getBOuterCostAmt()).doubleValue()));
					
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
    return this.strCurCompId; }

  public void setStrCurCompId(String strCurCompId) {
    this.strCurCompId = strCurCompId; }

  public String getStrMessage() {
    return this.strMessage; }

  public void setStrMessage(String strMessage) {
    this.strMessage = strMessage;
  }

  @JSON(serialize=false)
  public IC016Service getIc016Service()
  {
    return this.ic016Service; }

  @JSON(serialize=false)
  public void setIc016Service(IC016Service ic016Service) {
    this.ic016Service = ic016Service;
  }

  public String getStrFromDate() {
    return this.strFromDate;
  }

  public void setStrFromDate(String strFromDate) {
    this.strFromDate = strFromDate;
  }

  public String getStrToDate() {
    return this.strToDate;
  }

  public void setStrToDate(String strToDate) {
    this.strToDate = strToDate;
  }

  public String getStrFromGoodsNo() {
    return this.strFromGoodsNo;
  }

  public void setStrFromGoodsNo(String strFromGoodsNo) {
    this.strFromGoodsNo = strFromGoodsNo;
  }

  public String getStrToGoodsNo() {
    return this.strToGoodsNo;
  }

  public void setStrToGoodsNo(String strToGoodsNo) {
    this.strToGoodsNo = strToGoodsNo;
  }

  public String getStrWareId() {
    return this.strWareId;
  }

  public void setStrWareId(String strWareId) {
    this.strWareId = strWareId;
  }

  public List<GoodsOuterAnalysisBean> getLsDataSet() {
    return this.lsDataSet;
  }

  public void setLsDataSet(List<GoodsOuterAnalysisBean> lsDataSet) {
    this.lsDataSet = lsDataSet;
  }

  public List<GoodsInserTypeAnalysisBean> getLsTypeDataSet() {
    return this.lsTypeDataSet;
  }

  public void setLsTypeDataSet(List<GoodsInserTypeAnalysisBean> lsTypeDataSet) {
    this.lsTypeDataSet = lsTypeDataSet;
  }

public String getSearchGoodsType() {
	return searchGoodsType;
}

public void setSearchGoodsType(String searchGoodsType) {
	this.searchGoodsType = searchGoodsType;
}
}