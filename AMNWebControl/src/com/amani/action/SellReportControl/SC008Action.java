package com.amani.action.SellReportControl;

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



import com.amani.bean.CompMoreTradeAnlysis;
import com.amani.bean.SC017Bean;
import com.amani.service.SellReportControl.SC008Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/sc008")
public class SC008Action {
	@Autowired
	private SC008Service sc008Service;
	private String strCurCompId;
	private String strSearchType;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private OutputStream os;
	public OutputStream getOs() {
		return os;
	}


	public void setOs(OutputStream os) {
		this.os = os;
	}
	private List<CompMoreTradeAnlysis> lsDataSet;

	//查询默认发送短信
	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		this.lsDataSet=this.sc008Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate),Integer.parseInt(strSearchType));
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "loadExcel",  results = { 
			 @Result(name = "load_success", location = "/SellReportControl/SC008/sc008Excel.jsp"),
			 @Result(name = "load_failure",  location = "/SellReportControl/SC008/sc008Excel.jsp")	
	}) 
	public String loadExcel() {
		this.strMessage="";
		this.lsDataSet=this.sc008Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate),Integer.parseInt(strSearchType));
		if(lsDataSet==null || lsDataSet.size()<1)
		{
			this.strMessage="没有查询到数据";
		}
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	public void createExcel() throws WriteException, IOException
	{
		try
		{
			int excelLength0=6;
			//创建excel工作簿
			WritableWorkbook workbook= Workbook.createWorkbook(os);
			WritableSheet sheet =workbook.createSheet("项目消费业绩统计", 0);
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
			Label head=new Label(0,0,"门店业绩指标分析",titleFormate);
			sheet.setRowView(0, 500,false);
			sheet.addCell(head);
			
			//制表日期：
			Label makeDate=new Label(0,1,"制表日期：",titleFormate);
			sheet.addCell(makeDate);
			
			//制表日期值
			Label makeDateData=new Label(1,1,""+CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet.addCell(makeDateData);
			
			Label str0=new Label(0,2,"",titleFormate);
			sheet.addCell(str0);
			
			Label str1=new Label(1,2,"",titleFormate);
			sheet.addCell(str1);
			
			Label str2=new Label(2,2,"",titleFormate);
			sheet.addCell(str2);
			
			Label str3=new Label(3,2,"",titleFormate);
			sheet.addCell(str3);
			
			Label str4=new Label(4,2,"",titleFormate);
			sheet.addCell(str4);
			
			
			Label str5=new Label(5,2,"",titleFormate);
			sheet.addCell(str5);
			
			
			Label str6=new Label(6,2,"",titleFormate);
			sheet.addCell(str6);
			
			Label str7=new Label(7,2,"",titleFormate);
			sheet.addCell(str7);
			
			
			Label str8=new Label(8,2,"",titleFormate);
			sheet.addCell(str8);
			
			Label str9=new Label(9,2,"",titleFormate);
			sheet.addCell(str9);
			
			Label str10=new Label(10,2,"门店新增疗程",titleFormate);
			sheet.addCell(str10);
			
			Label str11=new Label(11,2,"",titleFormate);
			sheet.addCell(str11);
			
			
			Label str12=new Label(12,2,"",titleFormate);
			sheet.addCell(str12);
			
			Label str13=new Label(13,2,"美容疗程会员",titleFormate);
			sheet.addCell(str13);
			
			Label str14=new Label(14,2,"",titleFormate);
			sheet.addCell(str14);
			
			
			
			Label str15=new Label(15,2,"",titleFormate);
			sheet.addCell(str15);
			
			Label str16=new Label(16,2,"美发疗程会员",titleFormate);
			sheet.addCell(str16);
			
			Label str17=new Label(17,2,"",titleFormate);
			sheet.addCell(str17);
			
			
			Label str18=new Label(18,2,"",titleFormate);
			sheet.addCell(str18);
			
			Label str19=new Label(19,2,"离职人数",titleFormate);
			sheet.addCell(str19);
			
			
			
			///////////////////
			
			Label str00=new Label(0,3,"门店",titleFormate);
			sheet.addCell(str00);
			
			Label str01=new Label(1,3,"名称",titleFormate);
			sheet.addCell(str01);
			
			Label str02=new Label(2,3,"日期",titleFormate);
			sheet.addCell(str02);
			
			Label str03=new Label(3,3,"总虚业绩",titleFormate);
			sheet.addCell(str03);
			
			Label str04=new Label(4,3,"总实业绩",titleFormate);
			sheet.addCell(str04);
			
			
			Label str05=new Label(5,3,"耗卡率",titleFormate);
			sheet.addCell(str05);
			
			
			Label str06=new Label(6,3,"新增会员",titleFormate);
			sheet.addCell(str06);
			
			Label str07=new Label(7,3,"有效会员",titleFormate);
			sheet.addCell(str07);
			
			
			Label str08=new Label(8,3,"会员数",titleFormate);
			sheet.addCell(str08);
			
			Label str09=new Label(9,3,"美容",titleFormate);
			sheet.addCell(str09);
			
			Label str010=new Label(10,3,"美发",titleFormate);
			sheet.addCell(str010);
			
			Label str011=new Label(11,3,"合计",titleFormate);
			sheet.addCell(str011);
			
			
			Label str012=new Label(12,3,"会员数",titleFormate);
			sheet.addCell(str012);
			
			Label str013=new Label(13,3,"有效会员数",titleFormate);
			sheet.addCell(str013);
			
			Label str014=new Label(14,3,"会员保有率",titleFormate);
			sheet.addCell(str014);
			
			
			
			Label str015=new Label(15,3,"会员数",titleFormate);
			sheet.addCell(str015);
			
			Label str016=new Label(16,3,"有效会员数",titleFormate);
			sheet.addCell(str016);
			
			Label str017=new Label(17,3,"会员保有率",titleFormate);
			sheet.addCell(str017);
			
			
			Label str018=new Label(18,3,"核心员工",titleFormate);
			sheet.addCell(str018);
			
			Label str019=new Label(19,3,"总人数",titleFormate);
			sheet.addCell(str019);
			
			/*Label str4=new Label(4,2,"季卡套数(美发)",titleFormate);
			sheet.addCell(str4);
			
			Label str5=new Label(5,2,"月卡套数(美发)",titleFormate);
			sheet.addCell(str5);
			
			Label str21=new Label(6,2,"年卡套数(美容)",titleFormate);
			sheet.addCell(str21);
			
			Label str32=new Label(7,2,"半年卡套数(美容)",titleFormate);
			sheet.addCell(str32);
			
			Label str43=new Label(8,2,"季卡套数(美容)",titleFormate);
			sheet.addCell(str43);
			
			Label str54=new Label(9,2,"月卡套数(美容)",titleFormate);
			sheet.addCell(str54);*/
			
			int index=4;
			
			if(lsDataSet!=null && lsDataSet.size()>0)
			{
				for(CompMoreTradeAnlysis bean:lsDataSet)
				{
					Label str111=new Label(0,index,bean.getStrCompId(),titleFormate);
					sheet.addCell(str111);
					
					Label str112=new Label(1,index,bean.getStrCompName(),titleFormate);
					sheet.addCell(str112);
					
					Label str113=new Label(2,index,bean.getStrDate()+"",titleFormate);
					sheet.addCell(str113);
					
					Label str114=new Label(3,index,bean.getTotalxuyejizbF()+"",titleFormate);
					sheet.addCell(str114);
					
					
					Label str115=new Label(4,index,bean.getTotalshiyeji()+"",titleFormate);
					sheet.addCell(str115);
					
					Label str116=new Label(5,index,bean.getCostcardratezbF()+"",titleFormate);
					sheet.addCell(str116);
					
					Label str117=new Label(6,index,bean.getAddmemcount()+"",titleFormate);
					sheet.addCell(str117);
					
					
					Label str118=new Label(7,index,bean.getGoodmemcountzbF()+"",titleFormate);
					sheet.addCell(str118);
					
					
					Label str119=new Label(8,index,bean.getMemcount()+"",titleFormate);
					sheet.addCell(str119);
					
					Label str120=new Label(9,index,bean.getAddbeatypromems()+"",titleFormate);
					sheet.addCell(str120);
					
					Label str121=new Label(10,index,bean.getAddhairpromems()+"",titleFormate);
					sheet.addCell(str121);
					
					Label str122=new Label(11,index,bean.getAddpromemszbF()+"",titleFormate);
					sheet.addCell(str122);
					
					Label str123=new Label(12,index,bean.getBeatycount()+"",titleFormate);
					sheet.addCell(str123);
					
					Label str124=new Label(13,index,bean.getBeatygoodcount()+"",titleFormate);
					sheet.addCell(str124);
					
					Label str125=new Label(14,index,bean.getBeatygoodratezbF()+"",titleFormate);
					sheet.addCell(str125);
					
					Label str126=new Label(15,index,bean.getHaircount()+"",titleFormate);
					sheet.addCell(str126);
					
					
					Label str127=new Label(16,index,bean.getHairgoodcount()+"",titleFormate);
					sheet.addCell(str127);
					
					
					Label str128=new Label(17,index,bean.getHairgoodratezbF()+"",titleFormate);
					sheet.addCell(str128);
					
					Label str129=new Label(18,index,bean.getLeavelcorecount()+"",titleFormate);
					sheet.addCell(str129);
					
					Label str130=new Label(19,index,bean.getLeavelcountzbF()+"",titleFormate);
					sheet.addCell(str130);
					
					/*Label str10=new Label(4,index,bean.getJkcount_mf()+"",titleFormate);
					sheet.addCell(str10);
					
					Label str11=new Label(5,index,bean.getMonthcount_mf()+"",titleFormate);
					sheet.addCell(str11);
					
					
					Label str14=new Label(6,index,bean.getYearcount_mr()+"",titleFormate);
					sheet.addCell(str14);
					
					Label str15=new Label(7,index,bean.getBncount_mr()+"",titleFormate);
					sheet.addCell(str15);
					
					Label str16=new Label(8,index,bean.getJkcount_mr()+"",titleFormate);
					sheet.addCell(str16);
					
					Label str17=new Label(9,index,bean.getMonthcount_mr()+"",titleFormate);
					sheet.addCell(str17);*/
					index++;
				}
			}
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
	public SC008Service getSc008Service() {
		return sc008Service;
	}
	@JSON(serialize=false)
	public void setSc008Service(SC008Service sc008Service) {
		this.sc008Service = sc008Service;
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

	public List<CompMoreTradeAnlysis> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<CompMoreTradeAnlysis> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	public String getStrSearchType() {
		return strSearchType;
	}
	public void setStrSearchType(String strSearchType) {
		this.strSearchType = strSearchType;
	}

	
	
}
