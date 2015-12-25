package com.amani.action.SellReportControl;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
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

import com.amani.bean.TradeDateCheckBean;
import com.amani.service.SellReportControl.SC010Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/sc010")
public class SC010Action {
	@Autowired
	private SC010Service sc010Service;
	private String strCurCompId;
	private String strCurCompName;
	private String strSearchType;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private List<TradeDateCheckBean> lsDataSet;

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
		this.lsDataSet=this.sc010Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate));
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadSC010Excel",  results = { 
			 @Result(name = "load_success", location = "/SellReportControl/SC010/sc010Excel.jsp"),
			 @Result(name = "load_failure",  location = "/SellReportControl/SC010/sc010Excel.jsp")	
	}) 
	public String loadSC010Excel() {  //查询工资
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		strCurCompName=this.sc010Service.getDataTool().loadCompNameById(strCurCompId);
		this.lsDataSet=this.sc010Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate));
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
			Label head=new Label(0,0,strCurCompId+"["+strCurCompName+"]"+"门店员工工资汇总表",titleFormate);
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
			
			Label str2=new Label(2,2,"日期",titleFormate);
			     
			sheet.addCell(str2);
	
		      
			
			Label str3=new Label(3,2,"卡异动",titleFormate);
			sheet.addCell(str3);
			
			Label str4=new Label(4,2,"美容业绩",titleFormate);
			sheet.addCell(str4);
			
			Label stra=new Label(5,2,"美发业绩",titleFormate);
			sheet.addCell(stra);
			
			Label str5=new Label(6,2,"美甲业绩",titleFormate);
			sheet.addCell(str5);
			
			Label str7=new Label(7,2,"其他业绩",titleFormate);
			sheet.addCell(str7);
			
			Label str8=new Label(8,2,"销售合计",titleFormate);
			sheet.addCell(str8);
			
			Label str8x=new Label(9,2,"现金合计",titleFormate);
			sheet.addCell(str8x);
			
			Label str9=new Label(10,2,"银行卡合计",titleFormate);
			sheet.addCell(str9);
			
			Label str10=new Label(11,2,"第三方支付合计",titleFormate);
			sheet.addCell(str10);
			
			Label str11=new Label(12,2,"大众点评",titleFormate);
			sheet.addCell(str11);
			
			Label str12=new Label(13,2,"美团",titleFormate);
			sheet.addCell(str12);
			
			Label str13=new Label(14,2,"指付通合计",titleFormate);
			sheet.addCell(str13);
			
			Label str14=new Label(15,2,"销卡合计",titleFormate);
			sheet.addCell(str14);
			
			Label str15=new Label(16,2,"现金抵用服务",titleFormate);
			sheet.addCell(str15);
			
			Label str16=new Label(17,2,"项目抵用券服务",titleFormate);
			sheet.addCell(str16);
			
			Label str17=new Label(18,2,"条码卡服务",titleFormate);
			sheet.addCell(str17);
			
			
			Label str18=new Label(19,2,"积分服务",titleFormate);
			sheet.addCell(str18);
			
			Label str19=new Label(20,2,"正常条码卡销售",titleFormate);
			sheet.addCell(str19);
			
			Label str20=new Label(21,2,"赠送条码卡销售",titleFormate);
			sheet.addCell(str20);
			
			
			Label str21=new Label(22,2,"合作项目销售",titleFormate);
			sheet.addCell(str21);
			
			Label str22=new Label(23,2,"退卡",titleFormate);
			sheet.addCell(str22);
			
			Label labname=null;
			WritableCellFeatures wcf1=null;
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				double sum1=0; 
				double sum2=0; 
				double sum3=0; 
				double sum4=0; 
				double sum5=0; 
				double sum6=0; 
				double sum7=0; 
				double sum8=0; 
				double sum9=0; 
				double sum10=0; 
				double sum11=0;
				double sum12=0; 
				double sum13=0; 
				double sum14=0; 
				double sum15=0; 
				double sum16=0; 
				double sum17=0; 
				double sum18=0; 
				double sum19=0; 
				double sum20=0; 
				double sum21=0; 
				for(int i=0;i<this.lsDataSet.size();i++)
				{
					sheet.addCell(new Label(0,i+3,""+this.lsDataSet.get(i).getShopId()));
					sheet.addCell(new Label(1,i+3,""+this.lsDataSet.get(i).getShopName()));
					sheet.addCell(new Label(2,i+3,""+this.lsDataSet.get(i).getDateReport()));
					sheet.addCell(new Label(3,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalCardtrans())));
					sum1=sum1+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalCardtrans()).doubleValue();
					sheet.addCell(new Label(4,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalMrSale())));
					sum2=sum2+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalMrSale()).doubleValue();
					sheet.addCell(new Label(5,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalMfSale())));
					sum3=sum3+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalMfSale()).doubleValue();
					sheet.addCell(new Label(6,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalMjSale())));
					sum4=sum4+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalMjSale()).doubleValue();
					sheet.addCell(new Label(7,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalOther())));
					sum5=sum5+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalOther()).doubleValue();
					sheet.addCell(new Label(8,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalcardSales())));
					sum6=sum6+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalcardSales()).doubleValue();
					sheet.addCell(new Label(9,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalcash())));
					sum7=sum7+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalcash()).doubleValue();
					sheet.addCell(new Label(10,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalcredit())));
					sum8=sum8+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalcredit()).doubleValue();
					sheet.addCell(new Label(11,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalock())));
					sum9=sum9+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalock()).doubleValue();
					sheet.addCell(new Label(12,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalDztgk())));
					sum10=sum10+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalDztgk()).doubleValue();
					sheet.addCell(new Label(13,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalMttgk())));
					sum11=sum11+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalMttgk()).doubleValue();
					sheet.addCell(new Label(14,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalzft())));
					sum12=sum12+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalzft()).doubleValue();
					sheet.addCell(new Label(15,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalCardsCost())));
					sum13=sum13+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalCardsCost()).doubleValue();
					sheet.addCell(new Label(16,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getCashdyservice())));
					sum14=sum14+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getCashdyservice()).doubleValue();
					sheet.addCell(new Label(17,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getPrjdyservice())));
					sum15=sum15+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getPrjdyservice()).doubleValue();
					sheet.addCell(new Label(18,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTmkservice())));
					sum16=sum16+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTmkservice()).doubleValue();
					sheet.addCell(new Label(19,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalCostpoint())));
					sum17=sum17+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalCostpoint()).doubleValue();
					sheet.addCell(new Label(20,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTmkASale())));
					sum18=sum18+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTmkASale()).doubleValue();
					sheet.addCell(new Label(21,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTmkBSale())));
					sum19=sum19+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTmkBSale()).doubleValue();
					sheet.addCell(new Label(22,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalHzSale())));
					sum20=sum20+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalHzSale()).doubleValue();
					sheet.addCell(new Label(23,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalBackCard())));
					sum21=sum21+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalBackCard()).doubleValue();
					
				}
				sheet.addCell(new Label(0,this.lsDataSet.size()+3,""+this.lsDataSet.get(0).getShopId()));
				sheet.addCell(new Label(1,this.lsDataSet.size()+3,""+this.lsDataSet.get(0).getShopName()));
				sheet.addCell(new Label(2,this.lsDataSet.size()+3,"合计"));
				sheet.addCell(new Label(3,this.lsDataSet.size()+3,""+CommonTool.FormatBigDecimal(new BigDecimal(sum1))));
				sheet.addCell(new Label(4,this.lsDataSet.size()+3,""+CommonTool.FormatBigDecimal(new BigDecimal(sum2))));
				sheet.addCell(new Label(5,this.lsDataSet.size()+3,""+CommonTool.FormatBigDecimal(new BigDecimal(sum3))));
				sheet.addCell(new Label(6,this.lsDataSet.size()+3,""+CommonTool.FormatBigDecimal(new BigDecimal(sum4))));
				sheet.addCell(new Label(7,this.lsDataSet.size()+3,""+CommonTool.FormatBigDecimal(new BigDecimal(sum5))));
				sheet.addCell(new Label(8,this.lsDataSet.size()+3,""+CommonTool.FormatBigDecimal(new BigDecimal(sum6))));
				sheet.addCell(new Label(9,this.lsDataSet.size()+3,""+CommonTool.FormatBigDecimal(new BigDecimal(sum7))));
				sheet.addCell(new Label(10,this.lsDataSet.size()+3,""+CommonTool.FormatBigDecimal(new BigDecimal(sum8))));
				sheet.addCell(new Label(11,this.lsDataSet.size()+3,""+CommonTool.FormatBigDecimal(new BigDecimal(sum9))));
				sheet.addCell(new Label(12,this.lsDataSet.size()+3,""+CommonTool.FormatBigDecimal(new BigDecimal(sum10))));
				sheet.addCell(new Label(13,this.lsDataSet.size()+3,""+CommonTool.FormatBigDecimal(new BigDecimal(sum11))));
				sheet.addCell(new Label(14,this.lsDataSet.size()+3,""+CommonTool.FormatBigDecimal(new BigDecimal(sum12))));
				sheet.addCell(new Label(15,this.lsDataSet.size()+3,""+CommonTool.FormatBigDecimal(new BigDecimal(sum13))));
				sheet.addCell(new Label(16,this.lsDataSet.size()+3,""+CommonTool.FormatBigDecimal(new BigDecimal(sum14))));
				sheet.addCell(new Label(17,this.lsDataSet.size()+3,""+CommonTool.FormatBigDecimal(new BigDecimal(sum15))));
				sheet.addCell(new Label(18,this.lsDataSet.size()+3,""+CommonTool.FormatBigDecimal(new BigDecimal(sum16))));
				sheet.addCell(new Label(19,this.lsDataSet.size()+3,""+CommonTool.FormatBigDecimal(new BigDecimal(sum17))));
				sheet.addCell(new Label(20,this.lsDataSet.size()+3,""+CommonTool.FormatBigDecimal(new BigDecimal(sum18))));
				sheet.addCell(new Label(21,this.lsDataSet.size()+3,""+CommonTool.FormatBigDecimal(new BigDecimal(sum19))));
				sheet.addCell(new Label(22,this.lsDataSet.size()+3,""+CommonTool.FormatBigDecimal(new BigDecimal(sum20))));
				sheet.addCell(new Label(23,this.lsDataSet.size()+3,""+CommonTool.FormatBigDecimal(new BigDecimal(sum21))));
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
	@JSON(serialize=false)
	public SC010Service getSc010Service() {
		return sc010Service;
	}
	@JSON(serialize=false)
	public void setSc010Service(SC010Service sc010Service) {
		this.sc010Service = sc010Service;
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

	public List<TradeDateCheckBean> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<TradeDateCheckBean> lsDataSet) {
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

	
	
}
