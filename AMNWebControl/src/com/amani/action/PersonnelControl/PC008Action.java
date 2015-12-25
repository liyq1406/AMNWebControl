package com.amani.action.PersonnelControl;

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

import com.amani.bean.StaffWarkSalaryAnlanysis;
import com.amani.model.Staffabsenceinfo;
import com.amani.service.PersonnelControl.PC008Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/pc008")
public class PC008Action {
	@Autowired
	private PC008Service pc008Service;
	private String strCurCompId;
	private String strCurCompName;
	private String strSearchType;
	private String strFromDate;
	private String strToDate;
	private String strMessage;
	private List<Staffabsenceinfo> lsStaffabsenceinfo;
	private String strStaffInNo;
	private String strAbsencedate;
	private String strStaffNo;
	private String strCurDate;
	private String strCurMonth;
	private String strtoMonth;
	private int iA3;
	private List<StaffWarkSalaryAnlanysis> lsDataSet;
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
		this.lsDataSet=this.pc008Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate));
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadpc008Excel",  results = { 
			 @Result(name = "load_success", location = "/PersonnelControl/PC008/pc008Excel.jsp"),
			 @Result(name = "load_failure",  location = "/PersonnelControl/PC008/pc008Excel.jsp")	
	}) 
	public String loadpc008Excel() {  //查询工资
		if(strFromDate.equals(""))
			strFromDate=CommonTool.getCurrDate();
		if(strToDate.equals(""))
			strToDate=CommonTool.getCurrDate();
		strCurCompName=this.pc008Service.getDataTool().loadCompNameById(strCurCompId);
		this.lsDataSet=this.pc008Service.loadDateSetByCompId(strCurCompId, CommonTool.setDateMask(strFromDate), CommonTool.setDateMask(strToDate));
		return SystemFinal.LOAD_SUCCESS;

	}
	
	public void createExcel() throws WriteException, IOException
	{
		if(CommonTool.FormatInteger(iA3)==1)
		{
			createExcelA3();
		}
		else
		{
			createExcelNoA3();
		}
	}
	
	public void createExcelA3() throws WriteException, IOException
	{
		try
		{
			int excelLength0=24;
			//创建excel工作簿
			WritableWorkbook workbook= Workbook.createWorkbook(os);
			
			//创建新的一页
			WritableSheet sheet =workbook.createSheet("First Sheet", 0);
			//构造表头
			sheet.mergeCells(0, 0, excelLength0-1, 0); //合并第一行，1到倒数第一行列
			
			//区分在职和非离职
			int leaveCount=0;
			int	daifaCount=0;
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				for(int i=0;i<this.lsDataSet.size();i++)
				{
					if(CommonTool.FormatInteger(this.lsDataSet.get(i).getStaffcurstate())==3)
					{
						leaveCount=leaveCount+1;
					}
					else if(CommonTool.FormatInteger(this.lsDataSet.get(i).getStaffcurstate())==4)
					{
						daifaCount=daifaCount+1;
					}
				}
			}
			
			
			WritableFont bold=new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);//设置字体种类和黑体显示
			
			WritableCellFormat titleFormate =new WritableCellFormat(bold);
			titleFormate.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN); 
			titleFormate.setAlignment(jxl.format.Alignment.CENTRE);//单元格的内容水平居中
			titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
			titleFormate.setWrap(true);
			WritableCellFormat numberFormate =new WritableCellFormat();
			numberFormate.setAlignment(jxl.format.Alignment.RIGHT);//单元格的内容水平右对齐
			numberFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
			
			jxl.write.WritableFont wfc = new jxl.write.WritableFont(WritableFont.ARIAL, 10, 
					WritableFont.NO_BOLD, false, 
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED); 
			WritableCellFormat totalNumberFormate =new WritableCellFormat(wfc);
			totalNumberFormate.setAlignment(jxl.format.Alignment.RIGHT);//单元格的内容水平右对齐
			totalNumberFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中


			
			//标题
			Label head=new Label(0,0,strCurCompId+"["+strCurCompName+"]"+"门店在职员工工资汇总表(3月)",titleFormate);
			sheet.setRowView(0, 500,false);
			sheet.addCell(head);
			
			
			//制表日期：
			Label makeDate=new Label(0,1,"制表人：",titleFormate);
			sheet.addCell(makeDate);
			

			//制表日期值
			Label makeDateData=new Label(1,1,CommonTool.getLoginInfo("USERNAME")+"["+CommonTool.getDateMask(CommonTool.getCurrDate())+"]");
			sheet.addCell(makeDateData);


			
			Label str0=new Label(0,2,"银行账号",titleFormate);
			sheet.addCell(str0);
			
			Label str2=new Label(1,2,"姓名",titleFormate);
			sheet.addCell(str2);
			
			Label str1=new Label(2,2,"编号",titleFormate);
			sheet.addCell(str1);
			
			Label str3=new Label(3,2,"职位",titleFormate);
			sheet.addCell(str3);
			
			Label str4=new Label(4,2,"身份证",titleFormate);
			sheet.addCell(str4);
			
			Label stra=new Label(5,2,"个人业绩",titleFormate);
			sheet.addCell(stra);
			
			Label str5=new Label(6,2,"门店业绩",titleFormate);
			sheet.addCell(str5);
			
			Label str7=new Label(7,2,"底薪",titleFormate);
			sheet.addCell(str7);
			
			Label str13=new Label(8,2,"奖励",titleFormate);
			sheet.addCell(str13);
			
			Label str11x=new Label(9,2,"补贴",titleFormate);
			sheet.addCell(str11x);
			
			Label str10=new Label(10,2,"保底",titleFormate);
			sheet.addCell(str10);
			
			Label str10x=new Label(11,2,"差异调整",titleFormate);
			sheet.addCell(str10x);
			

			Label str12=new Label(12,2,"迟到",titleFormate);
			sheet.addCell(str12);
			
			
			Label str9=new Label(13,2,"离职",titleFormate);
			sheet.addCell(str9);
	
			
			Label str11=new Label(14,2,"罚款",titleFormate);
			sheet.addCell(str11);
			
			
			Label str15=new Label(15,2,"社保",titleFormate);
			sheet.addCell(str15);
			
			Label str16=new Label(16,2,"税前工资",titleFormate);
			sheet.addCell(str16);
			
			Label str17=new Label(17,2,"所得税",titleFormate);
			sheet.addCell(str17);
			
			Label str11xx=new Label(18,2,"代扣+住宿+学习费+扣成本",titleFormate);
			sheet.addCell(str11xx);
			
		
			
			Label str20x=new Label(19,2,"责任金返还",titleFormate);
			sheet.addCell(str20x);
			
			Label str20xx=new Label(20,2,"责任金抵押",titleFormate);
			sheet.addCell(str20xx);
			
			Label str22=new Label(21,2,"税后工资",titleFormate);
			sheet.addCell(str22);
			
			Label str23=new Label(22,2,"补充工资",titleFormate);
			sheet.addCell(str23);
			
			Label str24=new Label(23,2,"签名",titleFormate);
			sheet.addCell(str24);
			Label labname=null;
			WritableCellFeatures wcf1=null;
			sheet.setColumnView(0, 20);
			sheet.setColumnView(3, 10);
			sheet.setColumnView(4, 20);
			int k=0;
			//在职员工
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				double 	totalExcelA=0;
				double	totalExcelB=0;
				for(int i=0;i<this.lsDataSet.size();i++)
				{
					if(CommonTool.FormatInteger(this.lsDataSet.get(i).getStaffcurstate())!=2)
						continue;
					wcf1=null;
					sheet.addCell(new Label(0,k+3,""+this.lsDataSet.get(i).getStaffbankaccountno(),titleFormate));
					labname=new Label(1,k+3,""+this.lsDataSet.get(i).getStaffname(),titleFormate);
					if(!CommonTool.FormatString(this.lsDataSet.get(i).getStaffmark()).equals(""))
					{
						wcf1 = new WritableCellFeatures();
						wcf1.setComment(CommonTool.FormatString(this.lsDataSet.get(i).getStaffmark()));//设置批注内容
						labname.setCellFeatures(wcf1);//将批注添加到表格上
					}
					sheet.addCell(labname);
					sheet.addCell(new Label(2,k+3,""+this.lsDataSet.get(i).getStaffno(),titleFormate));
					sheet.addCell(new Label(3,k+3,""+this.lsDataSet.get(i).getStaffpositionname(),titleFormate));
					sheet.addCell(new Label(4,k+3,""+this.lsDataSet.get(i).getStaffpcid(),titleFormate));
					sheet.addCell(new Number(5,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStafftotalyeji()).doubleValue(),titleFormate));
					sheet.addCell(new Number(6,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffshopyeji()).doubleValue(),titleFormate));
					sheet.addCell(new Number(7,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffbasesalary()).doubleValue(),titleFormate));
			
					sheet.addCell(new Number(8,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffreward()).doubleValue(),titleFormate));
					sheet.addCell(new Number(9,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStoresubsidy()).doubleValue()+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBeatysubsidy()).doubleValue(),titleFormate));
					sheet.addCell(new Number(10,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffsubsidy()).doubleValue(),titleFormate));
					sheet.addCell(new Number(11,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffamtchange()).doubleValue(),titleFormate));
					
					sheet.addCell(new Number(12,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getLatdebit()).doubleValue(),titleFormate));
					sheet.addCell(new Number(13,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getLeaveldebit()).doubleValue(),titleFormate));
					sheet.addCell(new Number(14,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffdebit()).doubleValue(),titleFormate));
					sheet.addCell(new Number(15,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffsocials()).doubleValue(),titleFormate));
					sheet.addCell(new Number(16,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getNeedpaysalary()).doubleValue(),titleFormate));
					sheet.addCell(new Number(17,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getSalarydebit()).doubleValue(),titleFormate));
					sheet.addCell(new Number(18,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffdaikou()).doubleValue()
							+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaydebit()).doubleValue()
							+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStudydebit()).doubleValue()
							+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffcost()).doubleValue(),titleFormate));

					sheet.addCell(new Number(19,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getZerenjinback()).doubleValue(),titleFormate));
					sheet.addCell(new Number(20,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getZerenjincost()).doubleValue(),titleFormate));
					sheet.addCell(new Number(21,k+3,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryA()).doubleValue(),titleFormate));
					sheet.addCell(new Number(22,k+3,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryB()).doubleValue(),titleFormate));
					sheet.addCell(new Label(23,k+3,"",titleFormate));
					totalExcelA=totalExcelA+CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryA()).doubleValue();
					totalExcelB=totalExcelB+CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryB()).doubleValue();
					k++;
				}
				sheet.addCell(new Label(0,k+3,"",titleFormate));
				sheet.mergeCells(0, k+3, 19, k+3); //合并第一行，1到倒数第一行列
				sheet.addCell(new Label(20,k+3,"总计",titleFormate));
				sheet.addCell(new Number(21,k+3,CommonTool.GetGymAmt(totalExcelA,0),titleFormate));
				sheet.addCell(new Number(22,k+3,CommonTool.GetGymAmt(totalExcelB,0),titleFormate));
				if(leaveCount>0)
				{
					k=k+1;
					sheet.mergeCells(0, k+3, excelLength0-1, k+3); //合并第一行，1到倒数第一行列
					head=new Label(0,k+3,strCurCompId+"["+strCurCompName+"]"+"门店离职员工工资汇总表(3月)",titleFormate);
					sheet.setRowView(k+3, 500,false);
					sheet.addCell(head);
					
					totalExcelA=0;
					totalExcelB=0;
					k++;
			
					
					 str0=new Label(0,k+3,"银行账号",titleFormate);
					sheet.addCell(str0);
					
					 str2=new Label(1,k+3,"姓名",titleFormate);
					sheet.addCell(str2);
					
					 str1=new Label(2,k+3,"编号",titleFormate);
					sheet.addCell(str1);
					
					 str3=new Label(3,k+3,"职位",titleFormate);
					sheet.addCell(str3);
					
					 str4=new Label(4,k+3,"身份证",titleFormate);
					sheet.addCell(str4);
					 stra=new Label(5,k+3,"个人业绩",titleFormate);
					sheet.addCell(stra);
					
					 str5=new Label(6,k+3,"门店业绩",titleFormate);
					sheet.addCell(str5);
					
					 str7=new Label(7,k+3,"底薪",titleFormate);
					sheet.addCell(str7);
					
					 str13=new Label(8,k+3,"奖励",titleFormate);
					sheet.addCell(str13);
					
					 str11x=new Label(9,k+3,"补贴",titleFormate);
					sheet.addCell(str11x);
					
					 str10=new Label(10,k+3,"保底",titleFormate);
					sheet.addCell(str10);
					
					 str10x=new Label(11,k+3,"差异调整",titleFormate);
					sheet.addCell(str10x);
					
			
					
					 str12=new Label(12,k+3,"迟到",titleFormate);
					sheet.addCell(str12);
					 str9=new Label(13,k+3,"离职",titleFormate);
					sheet.addCell(str9);
					
					 str11=new Label(14,k+3,"罚款",titleFormate);
					sheet.addCell(str11);
					 str15=new Label(15,k+3,"应缴社保",titleFormate);
					sheet.addCell(str15);
					 str16=new Label(16,k+3,"税前工资",titleFormate);
					sheet.addCell(str16);
					 str17=new Label(17,k+3,"所得税",titleFormate);
					sheet.addCell(str17);
					
					 str11xx=new Label(18,k+3,"代扣+住宿+学习费+扣成本",titleFormate);
					sheet.addCell(str11xx);
					
					
					
					 str20x=new Label(19,k+3,"责任金返还",titleFormate);
					sheet.addCell(str20x);
					
					 str20xx=new Label(20,k+3,"责任金抵押",titleFormate);
					sheet.addCell(str20xx);
					
				
					
					 str22=new Label(21,k+3,"税后工资",titleFormate);
					sheet.addCell(str22);
					
					 str23=new Label(22,k+3,"补充工资",titleFormate);
					sheet.addCell(str23);
					
					 str24=new Label(23,k+3,"签名",titleFormate);
					sheet.addCell(str24);
					k++;
				}
				//离职员工
				for(int i=0;i<this.lsDataSet.size();i++)
				{	
						if(CommonTool.FormatInteger(this.lsDataSet.get(i).getStaffcurstate())!=3)
							continue;
						wcf1=null;
						sheet.addCell(new Label(0,k+3,""+this.lsDataSet.get(i).getStaffbankaccountno(),titleFormate));
						labname=new Label(1,k+3,""+this.lsDataSet.get(i).getStaffname(),titleFormate);
						if(!CommonTool.FormatString(this.lsDataSet.get(i).getStaffmark()).equals(""))
						{
							wcf1 = new WritableCellFeatures();
							wcf1.setComment(CommonTool.FormatString(this.lsDataSet.get(i).getStaffmark()));//设置批注内容
							labname.setCellFeatures(wcf1);//将批注添加到表格上
						}
						sheet.addCell(labname);
						sheet.addCell(new Label(2,k+3,""+this.lsDataSet.get(i).getStaffno(),titleFormate));
						sheet.addCell(new Label(3,k+3,""+this.lsDataSet.get(i).getStaffpositionname(),titleFormate));
						sheet.addCell(new Label(4,k+3,""+this.lsDataSet.get(i).getStaffpcid(),titleFormate));
						sheet.addCell(new Number(5,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStafftotalyeji()).doubleValue(),titleFormate));
						sheet.addCell(new Number(6,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffshopyeji()).doubleValue(),titleFormate));
						sheet.addCell(new Number(7,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffbasesalary()).doubleValue(),titleFormate));
				
						sheet.addCell(new Number(8,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffreward()).doubleValue(),titleFormate));
						sheet.addCell(new Number(9,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStoresubsidy()).doubleValue()+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBeatysubsidy()).doubleValue(),titleFormate));
						sheet.addCell(new Number(10,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffsubsidy()).doubleValue(),titleFormate));
						sheet.addCell(new Number(11,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffamtchange()).doubleValue(),titleFormate));
						
						sheet.addCell(new Number(12,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getLatdebit()).doubleValue(),titleFormate));
						sheet.addCell(new Number(13,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getLeaveldebit()).doubleValue(),titleFormate));
						sheet.addCell(new Number(14,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffdebit()).doubleValue(),titleFormate));
						sheet.addCell(new Number(15,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffsocials()).doubleValue(),titleFormate));
						sheet.addCell(new Number(16,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getNeedpaysalary()).doubleValue(),titleFormate));
						sheet.addCell(new Number(17,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getSalarydebit()).doubleValue(),titleFormate));
						sheet.addCell(new Number(18,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffdaikou()).doubleValue()
								+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaydebit()).doubleValue()
								+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStudydebit()).doubleValue()
								+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffcost()).doubleValue(),titleFormate));

						sheet.addCell(new Number(19,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getZerenjinback()).doubleValue(),titleFormate));
						sheet.addCell(new Number(20,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getZerenjincost()).doubleValue(),titleFormate));
						sheet.addCell(new Number(21,k+3,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryA()).doubleValue(),titleFormate));
						sheet.addCell(new Number(22,k+3,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryB()).doubleValue(),titleFormate));
						sheet.addCell(new Label(23,k+3,"",titleFormate));
						totalExcelA=totalExcelA+CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryA()).doubleValue();
						totalExcelB=totalExcelB+CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryB()).doubleValue();
						k++;
				}
				if(leaveCount>0)
				{	
					sheet.addCell(new Label(0,k+3,"",titleFormate));
					sheet.mergeCells(0, k+3, 19, k+3); //合并第一行，1到倒数第一行列
					sheet.addCell(new Label(20,k+3,"总计",titleFormate));
					sheet.addCell(new Number(21,k+3,CommonTool.GetGymAmt(totalExcelA,0),titleFormate));
					sheet.addCell(new Number(22,k+3,CommonTool.GetGymAmt(totalExcelB,0),titleFormate));
				}
				if(daifaCount>0)
				{
					k=k+1;
					sheet.mergeCells(0, k+3, excelLength0-1, k+3); //合并第一行，1到倒数第一行列
					head=new Label(0,k+3,strCurCompId+"["+strCurCompName+"]"+"门店待发员工工资汇总表(3月)",titleFormate);
					sheet.setRowView(k+3, 500,false);
					sheet.addCell(head);
					totalExcelA=0;
					totalExcelB=0;
					k++;
				
					 str0=new Label(0,k+3,"银行账号",titleFormate);
					sheet.addCell(str0);
					
					 str2=new Label(1,k+3,"姓名",titleFormate);
					sheet.addCell(str2);
					
					 str1=new Label(2,k+3,"编号",titleFormate);
					sheet.addCell(str1);
					
					 str3=new Label(3,k+3,"职位",titleFormate);
					sheet.addCell(str3);
				
					 str4=new Label(4,k+3,"身份证",titleFormate);
					sheet.addCell(str4);
					
				
					
					 stra=new Label(5,k+3,"个人业绩",titleFormate);
					sheet.addCell(stra);
					
					 str5=new Label(6,k+3,"门店业绩",titleFormate);
					sheet.addCell(str5);
					
					 str7=new Label(7,k+3,"底薪",titleFormate);
					sheet.addCell(str7);
					
				
				
					
					 str13=new Label(8,k+3,"奖励",titleFormate);
					sheet.addCell(str13);
					
					 str11x=new Label(9,k+3,"补贴",titleFormate);
					sheet.addCell(str11x);
					
					 str10=new Label(10,k+3,"保底",titleFormate);
					sheet.addCell(str10);
					
					 str10x=new Label(11,k+3,"差异调整",titleFormate);
					sheet.addCell(str10x);
					
					
					
				
					
				
					
					 str12=new Label(12,k+3,"迟到",titleFormate);
					sheet.addCell(str12);
					
					
					 str9=new Label(13,k+3,"离职",titleFormate);
					sheet.addCell(str9);
					
			
					
					 str11=new Label(14,k+3,"罚款",titleFormate);
					sheet.addCell(str11);
					
					
			
					
					 str15=new Label(15,k+3,"社保",titleFormate);
					sheet.addCell(str15);
					
					 str16=new Label(16,k+3,"税前工资",titleFormate);
					sheet.addCell(str16);
					
					 str17=new Label(17,k+3,"所得税",titleFormate);
					sheet.addCell(str17);
					
					 str11xx=new Label(18,k+3,"代扣+住宿+学习费+扣成本",titleFormate);
					sheet.addCell(str11xx);
					
				
					 str20x=new Label(19,k+3,"责任金返还",titleFormate);
					sheet.addCell(str20x);
					
					 str20xx=new Label(20,k+3,"责任金抵押",titleFormate);
					sheet.addCell(str20xx);
					
				
					
					 str22=new Label(21,k+3,"税后工资",titleFormate);
					sheet.addCell(str22);
					
					 str23=new Label(22,k+3,"补充工资",titleFormate);
					sheet.addCell(str23);
					
					 str24=new Label(23,k+3,"签名",titleFormate);
					sheet.addCell(str24);
					k++;
				}
				//待发员工
				for(int i=0;i<this.lsDataSet.size();i++)
				{
						if(CommonTool.FormatInteger(this.lsDataSet.get(i).getStaffcurstate())!=4)
							continue;
						wcf1=null;
						sheet.addCell(new Label(0,k+3,""+this.lsDataSet.get(i).getStaffbankaccountno(),titleFormate));
						labname=new Label(1,k+3,""+this.lsDataSet.get(i).getStaffname(),titleFormate);
						if(!CommonTool.FormatString(this.lsDataSet.get(i).getStaffmark()).equals(""))
						{
							wcf1 = new WritableCellFeatures();
							wcf1.setComment(CommonTool.FormatString(this.lsDataSet.get(i).getStaffmark()));//设置批注内容
							labname.setCellFeatures(wcf1);//将批注添加到表格上
						}
						sheet.addCell(labname);
						sheet.addCell(new Label(2,k+3,""+this.lsDataSet.get(i).getStaffno(),titleFormate));
						sheet.addCell(new Label(3,k+3,""+this.lsDataSet.get(i).getStaffpositionname(),titleFormate));
						sheet.addCell(new Label(4,k+3,""+this.lsDataSet.get(i).getStaffpcid(),titleFormate));
						sheet.addCell(new Number(5,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStafftotalyeji()).doubleValue(),titleFormate));
						sheet.addCell(new Number(6,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffshopyeji()).doubleValue(),titleFormate));
						sheet.addCell(new Number(7,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffbasesalary()).doubleValue(),titleFormate));
				
						sheet.addCell(new Number(8,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffreward()).doubleValue(),titleFormate));
						sheet.addCell(new Number(9,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStoresubsidy()).doubleValue()+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBeatysubsidy()).doubleValue(),titleFormate));
						sheet.addCell(new Number(10,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffsubsidy()).doubleValue(),titleFormate));
						sheet.addCell(new Number(11,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffamtchange()).doubleValue(),titleFormate));
						
						sheet.addCell(new Number(12,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getLatdebit()).doubleValue(),titleFormate));
						sheet.addCell(new Number(13,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getLeaveldebit()).doubleValue(),titleFormate));
						sheet.addCell(new Number(14,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffdebit()).doubleValue(),titleFormate));
						sheet.addCell(new Number(15,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffsocials()).doubleValue(),titleFormate));
						sheet.addCell(new Number(16,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getNeedpaysalary()).doubleValue(),titleFormate));
						sheet.addCell(new Number(17,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getSalarydebit()).doubleValue(),titleFormate));
						sheet.addCell(new Number(18,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffdaikou()).doubleValue()
								+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaydebit()).doubleValue()
								+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStudydebit()).doubleValue()
								+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffcost()).doubleValue(),titleFormate));

						sheet.addCell(new Number(19,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getZerenjinback()).doubleValue(),titleFormate));
						sheet.addCell(new Number(20,k+3,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getZerenjincost()).doubleValue(),titleFormate));
						sheet.addCell(new Number(21,k+3,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryA()).doubleValue(),titleFormate));
						sheet.addCell(new Number(22,k+3,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryB()).doubleValue(),titleFormate));
						sheet.addCell(new Label(23,k+3,"",titleFormate));
						totalExcelA=totalExcelA+CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryA()).doubleValue();
						totalExcelB=totalExcelB+CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryB()).doubleValue();
						k++;
				}
				if(daifaCount>0)
				{	
					sheet.addCell(new Label(0,k+3,"",titleFormate));
					sheet.mergeCells(0, k+3, 19, k+3); //合并第一行，1到倒数第一行列
					sheet.addCell(new Label(20,k+3,"总计",titleFormate));
					sheet.addCell(new Number(21,k+3,CommonTool.GetGymAmt(totalExcelA,0),titleFormate));
					sheet.addCell(new Number(22,k+3,CommonTool.GetGymAmt(totalExcelB,0),titleFormate));
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
	
	public void createExcelNoA3() throws WriteException, IOException
	{
		try
		{
			int excelLength0=39;
			//创建excel工作簿
			WritableWorkbook workbook= Workbook.createWorkbook(os);
			
			//创建新的一页
			WritableSheet sheet =workbook.createSheet("First Sheet", 0);
			//构造表头
			sheet.mergeCells(0, 0, excelLength0-1, 0); //合并第一行，1到倒数第一行列
			
			//区分在职和非离职
			int leaveCount=0;
			int	daifaCount=0;
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				for(int i=0;i<this.lsDataSet.size();i++)
				{
					if(CommonTool.FormatInteger(this.lsDataSet.get(i).getStaffcurstate())==3)
					{
						leaveCount=leaveCount+1;
					}
					else if(CommonTool.FormatInteger(this.lsDataSet.get(i).getStaffcurstate())==4)
					{
						daifaCount=daifaCount+1;
					}
				}
			}
			
			
			WritableFont bold=new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,true);//设置字体种类和黑体显示
			
			WritableCellFormat titleFormate =new WritableCellFormat(bold);
			titleFormate.setAlignment(jxl.format.Alignment.CENTRE);//单元格的内容水平居中
			titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
			titleFormate.setWrap(true);
			WritableCellFormat numberFormate =new WritableCellFormat();
			numberFormate.setAlignment(jxl.format.Alignment.RIGHT);//单元格的内容水平右对齐
			numberFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
			
			jxl.write.WritableFont wfc = new jxl.write.WritableFont(WritableFont.ARIAL, 10, 
					WritableFont.NO_BOLD, false, 
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED); 
			WritableCellFormat totalNumberFormate =new WritableCellFormat(wfc);
			totalNumberFormate.setAlignment(jxl.format.Alignment.RIGHT);//单元格的内容水平右对齐
			totalNumberFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中


			
			//标题
			Label head=new Label(0,0,strCurCompId+"["+strCurCompName+"]"+"门店在职员工工资汇总表(3月)",titleFormate);
			sheet.setRowView(0, 500,false);
			sheet.addCell(head);
			
			
			//制表日期：
			Label makeDate=new Label(0,1,"制表人：",titleFormate);
			sheet.addCell(makeDate);
			

			//制表日期值
			Label makeDateData=new Label(1,1,CommonTool.getLoginInfo("USERNAME")+"["+CommonTool.getDateMask(CommonTool.getCurrDate())+"]");
			sheet.addCell(makeDateData);

			sheet.mergeCells(8, 2, 16, 2); //合并第一行，1到倒数第一行列
			sheet.mergeCells(20, 2, 24, 2); //合并第一行，1到倒数第一行列
			
			Label str0x20x=new Label(0,2,"",titleFormate);
			sheet.addCell(str0x20x);
			Label str0x20=new Label(1,2,"",titleFormate);
			sheet.addCell(str0x20);
			Label str0x21=new Label(2,2,"",titleFormate);
			sheet.addCell(str0x21);
			Label str0x22=new Label(3,2,"",titleFormate);
			sheet.addCell(str0x22);
			Label str0x23=new Label(4,2,"",titleFormate);
			sheet.addCell(str0x23);
			Label str0x24=new Label(5,2,"",titleFormate);
			sheet.addCell(str0x24);
			Label str0x25=new Label(6,2,"",titleFormate);
			sheet.addCell(str0x25);
			Label str0x26=new Label(7,2,"",titleFormate);
			sheet.addCell(str0x26);
		
			Label str0x27=new Label(8,2,"应付工资",titleFormate);
			sheet.addCell(str0x27);
			Label str0x215=new Label(17,2,"",titleFormate);
			sheet.addCell(str0x215);
			Label str0x216=new Label(18,2,"",titleFormate);
			sheet.addCell(str0x216);
			Label str0x217=new Label(19,2,"",titleFormate);
			sheet.addCell(str0x217);
			Label str0x219=new Label(20,2,"关爱基金扣款",titleFormate);
			sheet.addCell(str0x219);
			
			
			Label str0x=new Label(0,3,"门店",titleFormate);
			sheet.addCell(str0x);
			
			Label str0=new Label(1,3,"银行账号",titleFormate);
			sheet.addCell(str0);
			
			Label str2=new Label(2,3,"姓名",titleFormate);
			sheet.addCell(str2);
			
			Label str1=new Label(3,3,"编号",titleFormate);
			sheet.addCell(str1);
			
			Label str3=new Label(4,3,"职位",titleFormate);
			sheet.addCell(str3);
			
			Label str3x=new Label(5,3,"社保",titleFormate);
			sheet.addCell(str3x);
			
			Label str4=new Label(6,3,"身份证",titleFormate);
			sheet.addCell(str4);
			
			Label str8x=new Label(7,3,"缺勤",titleFormate);
			sheet.addCell(str8x);
			
			Label stra=new Label(8,3,"个人业绩",titleFormate);
			sheet.addCell(stra);
			
			Label str5=new Label(9,3,"门店业绩",titleFormate);
			sheet.addCell(str5);
			
			Label str7=new Label(10,3,"底薪",titleFormate);
			sheet.addCell(str7);
			
			Label str8=new Label(11,3,"美容补贴",titleFormate);
			sheet.addCell(str8);
			
			Label str81x=new Label(12,3,"指标",titleFormate);
			sheet.addCell(str81x);
			
			Label str13=new Label(13,3,"奖励",titleFormate);
			sheet.addCell(str13);
			
			Label str11x=new Label(14,3,"门店补贴",titleFormate);
			sheet.addCell(str11x);
			
			Label str10=new Label(15,3,"保底",titleFormate);
			sheet.addCell(str10);
			
			Label str10x=new Label(16,3,"差异调整",titleFormate);
			sheet.addCell(str10x);
			
			
			
			Label str101x=new Label(17,3,"应付工资",titleFormate);
			sheet.addCell(str101x);
			
			
			Label str102x=new Label(18,3,"关爱(付)",titleFormate);
			sheet.addCell(str102x);
			
			
			Label str103x=new Label(19,3,"小计",titleFormate);
			sheet.addCell(str103x);
			
			
		
			
			Label str12=new Label(20,3,"迟到",titleFormate);
			sheet.addCell(str12);
			
			
			Label str9=new Label(21,3,"离职扣款",titleFormate);
			sheet.addCell(str9);
			
			Label str9x=new Label(22,3,"关爱基金",titleFormate);
			sheet.addCell(str9x);
			
			Label str11=new Label(23,3,"罚款",titleFormate);
			sheet.addCell(str11);
			
			
			
			Label str15x=new Label(24,3,"小计",titleFormate);
			sheet.addCell(str15x);
			
			
			Label str15=new Label(25,3,"应缴社保",titleFormate);
			sheet.addCell(str15);
			
			Label str16=new Label(26,3,"税前工资",titleFormate);
			sheet.addCell(str16);
			
			Label str17=new Label(27,3,"个人所得税",titleFormate);
			sheet.addCell(str17);
			
			Label str11xx=new Label(28,3,"代扣",titleFormate);
			sheet.addCell(str11xx);
			
			Label str18=new Label(29,3,"住宿",titleFormate);
			sheet.addCell(str18);
			
			Label str19=new Label(30,3,"学习费",titleFormate);
			sheet.addCell(str19);
			
			Label str20=new Label(31,3,"扣成本",titleFormate);
			sheet.addCell(str20);
			
			Label str20x=new Label(32,3,"责任金返还",titleFormate);
			sheet.addCell(str20x);
			
			Label str20xx=new Label(33,3,"责任金抵押",titleFormate);
			sheet.addCell(str20xx);
			
			
			Label str21=new Label(34,3,"税后扣款小计",titleFormate);
			sheet.addCell(str21);
			
			Label str22=new Label(35,3,"税后工资",titleFormate);
			sheet.addCell(str22);
			
			Label str23=new Label(36,3,"补充工资",titleFormate);
			sheet.addCell(str23);
			
			Label str24=new Label(37,3,"签名",titleFormate);
			sheet.addCell(str24);
			Label labname=null;
			WritableCellFeatures wcf1=null;
		
			int k=0;
			//在职员工
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				double 	totalExcelA=0;
				double	totalExcelB=0;
				for(int i=0;i<this.lsDataSet.size();i++)
				{
					if(CommonTool.FormatInteger(this.lsDataSet.get(i).getStaffcurstate())!=2)
						continue;
					wcf1=null;
					sheet.addCell(new Label(0,k+4,""+this.lsDataSet.get(i).getStrCompName()));
					sheet.addCell(new Label(1,k+4,""+this.lsDataSet.get(i).getStaffbankaccountno()));
					labname=new Label(2,k+4,""+this.lsDataSet.get(i).getStaffname());
					if(!CommonTool.FormatString(this.lsDataSet.get(i).getStaffmark()).equals(""))
					{
						wcf1 = new WritableCellFeatures();
						wcf1.setComment(CommonTool.FormatString(this.lsDataSet.get(i).getStaffmark()));//设置批注内容
						labname.setCellFeatures(wcf1);//将批注添加到表格上
					}
					sheet.addCell(labname);
					sheet.addCell(new Label(3,k+4,""+this.lsDataSet.get(i).getStaffno()));
					sheet.addCell(new Label(4,k+4,""+this.lsDataSet.get(i).getStaffpositionname()));
					sheet.addCell(new Label(5,k+4,""+this.lsDataSet.get(i).getStaffsocialsource()));
					sheet.addCell(new Label(6,k+4,""+this.lsDataSet.get(i).getStaffpcid()));
					sheet.addCell(new Number(7,k+4,this.lsDataSet.get(i).getWorkdays()));
					sheet.addCell(new Number(8,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStafftotalyeji()).doubleValue()));
					sheet.addCell(new Number(9,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffshopyeji()).doubleValue()));
					sheet.addCell(new Number(10,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffbasesalary()).doubleValue()));
					sheet.addCell(new Number(11,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBeatysubsidy()).doubleValue()));
					sheet.addCell(new Number(12,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStafftargetreward()).doubleValue()));
					sheet.addCell(new Number(13,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffreward()).doubleValue()));
					sheet.addCell(new Number(14,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffsubsidy()).doubleValue()));
					sheet.addCell(new Number(15,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStoresubsidy()).doubleValue()));
					sheet.addCell(new Number(16,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffamtchange()).doubleValue()));
					sheet.addCell(new Number(17,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getNeedpaybsalary()).doubleValue()));
					sheet.addCell(new Number(18,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBasestaffpayamt()).doubleValue()));
					sheet.addCell(new Number(19,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalneedpay()).doubleValue()));
		
					sheet.addCell(new Number(20,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getLatdebit()).doubleValue()));
					sheet.addCell(new Number(21,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getLeaveldebit()).doubleValue()));
					sheet.addCell(new Number(22,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBasestaffamt()).doubleValue()));
					sheet.addCell(new Number(23,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffdebit()).doubleValue()));
					sheet.addCell(new Number(24,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getCosttotal()).doubleValue()));
					sheet.addCell(new Number(25,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffsocials()).doubleValue()));
					sheet.addCell(new Number(26,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getNeedpaysalary()).doubleValue()));
					sheet.addCell(new Number(27,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getSalarydebit()).doubleValue()));
					sheet.addCell(new Number(28,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffdaikou()).doubleValue()));
					sheet.addCell(new Number(29,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaydebit()).doubleValue()));
					sheet.addCell(new Number(30,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStudydebit()).doubleValue()));
					sheet.addCell(new Number(31,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffcost()).doubleValue()));
					sheet.addCell(new Number(32,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getZerenjinback()).doubleValue()));
					sheet.addCell(new Number(33,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getZerenjincost()).doubleValue()));
					sheet.addCell(new Number(34,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getSumcost()).doubleValue()));
					sheet.addCell(new Number(35,k+4,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryA()).doubleValue()));
					sheet.addCell(new Number(36,k+4,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryB()).doubleValue()));
					sheet.addCell(new Label(37,k+4,""));
					totalExcelA=totalExcelA+CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryA()).doubleValue();
					totalExcelB=totalExcelB+CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryB()).doubleValue();
					k++;
				}
				sheet.addCell(new Label(0,k+4,""));
				sheet.mergeCells(0, k+4, 33, k+4); //合并第一行，1到倒数第一行列
				sheet.addCell(new Label(34,k+4,"总计",titleFormate));
				sheet.addCell(new Number(35,k+4,CommonTool.GetGymAmt(totalExcelA,0)));
				sheet.addCell(new Number(36,k+4,CommonTool.GetGymAmt(totalExcelB,0)));
				if(leaveCount>0)
				{
					k=k+1;
					sheet.mergeCells(0, k+4, excelLength0-1, k+4); //合并第一行，1到倒数第一行列
					head=new Label(0,k+4,strCurCompId+"["+strCurCompName+"]"+"门店离职员工工资汇总表(3月)",titleFormate);
					sheet.setRowView(k+4, 500,false);
					sheet.addCell(head);
					
					totalExcelA=0;
					totalExcelB=0;
					k++;
					str0x=new Label(0,k+4,"门店",titleFormate);
					sheet.addCell(str0x);
					
					 str0=new Label(1,k+4,"银行账号",titleFormate);
					sheet.addCell(str0);
					
					 str2=new Label(2,k+4,"姓名",titleFormate);
					sheet.addCell(str2);
					
					 str1=new Label(3,k+4,"编号",titleFormate);
					sheet.addCell(str1);
					
					 str3=new Label(4,k+4,"职位",titleFormate);
					sheet.addCell(str3);
					
					 str3x=new Label(5,k+4,"社保",titleFormate);
					sheet.addCell(str3x);
					
					 str4=new Label(6,k+4,"身份证",titleFormate);
					sheet.addCell(str4);
					
					 str8x=new Label(7,k+4,"缺勤",titleFormate);
					sheet.addCell(str8x);
					
					 stra=new Label(8,k+4,"个人业绩",titleFormate);
					sheet.addCell(stra);
					
					 str5=new Label(9,k+4,"门店业绩",titleFormate);
					sheet.addCell(str5);
					
					 str7=new Label(10,k+4,"底薪",titleFormate);
					sheet.addCell(str7);
					
					 str8=new Label(11,k+4,"美容补贴",titleFormate);
					sheet.addCell(str8);
					
					 str81x=new Label(12,k+4,"指标",titleFormate);
					sheet.addCell(str81x);
					
					 str13=new Label(13,k+4,"奖励",titleFormate);
					sheet.addCell(str13);
					
					 str11x=new Label(14,k+4,"门店补贴",titleFormate);
					sheet.addCell(str11x);
					
					 str10=new Label(15,k+4,"保底",titleFormate);
					sheet.addCell(str10);
					
					 str10x=new Label(16,k+4,"差异调整",titleFormate);
					sheet.addCell(str10x);
					
					
					
					 str101x=new Label(17,k+4,"应付工资",titleFormate);
					sheet.addCell(str101x);
					
					
					 str102x=new Label(18,k+4,"关爱(付)",titleFormate);
					sheet.addCell(str102x);
					
					
					 str103x=new Label(19,k+4,"小计",titleFormate);
					sheet.addCell(str103x);
					
					
				
					
					 str12=new Label(20,k+4,"迟到",titleFormate);
					sheet.addCell(str12);
					
					
					 str9=new Label(21,k+4,"离职扣款",titleFormate);
					sheet.addCell(str9);
					
					 str9x=new Label(22,k+4,"关爱基金",titleFormate);
					sheet.addCell(str9x);
					
					 str11=new Label(23,k+4,"罚款",titleFormate);
					sheet.addCell(str11);
					
					
					
					 str15x=new Label(24,k+4,"小计",titleFormate);
					sheet.addCell(str15x);
					
					
					 str15=new Label(25,k+4,"应缴社保",titleFormate);
					sheet.addCell(str15);
					
					 str16=new Label(26,k+4,"税前工资",titleFormate);
					sheet.addCell(str16);
					
					 str17=new Label(27,k+4,"个人所得税",titleFormate);
					sheet.addCell(str17);
					
					 str11xx=new Label(28,k+4,"代扣",titleFormate);
					sheet.addCell(str11xx);
					
					 str18=new Label(29,k+4,"住宿",titleFormate);
					sheet.addCell(str18);
					
					 str19=new Label(30,k+4,"学习费",titleFormate);
					sheet.addCell(str19);
					
					 str20=new Label(31,k+4,"扣成本",titleFormate);
					sheet.addCell(str20);
					
					 str20x=new Label(32,k+4,"责任金返还",titleFormate);
					sheet.addCell(str20x);
					
					 str20xx=new Label(33,k+4,"责任金抵押",titleFormate);
					sheet.addCell(str20xx);
					
					
					 str21=new Label(34,k+4,"税后扣款小计",titleFormate);
					sheet.addCell(str21);
					
					 str22=new Label(35,k+4,"税后工资",titleFormate);
					sheet.addCell(str22);
					
					 str23=new Label(36,k+4,"补充工资",titleFormate);
					sheet.addCell(str23);
					
					 str24=new Label(37,k+4,"签名",titleFormate);
					sheet.addCell(str24);
					k++;
				}
				//离职员工
				for(int i=0;i<this.lsDataSet.size();i++)
				{
						if(CommonTool.FormatInteger(this.lsDataSet.get(i).getStaffcurstate())!=3)
							continue;
						wcf1=null;
						sheet.addCell(new Label(0,k+4,""+this.lsDataSet.get(i).getStrCompName()));
						sheet.addCell(new Label(1,k+4,""+this.lsDataSet.get(i).getStaffbankaccountno()));
						labname=new Label(2,k+4,""+this.lsDataSet.get(i).getStaffname());
						if(!CommonTool.FormatString(this.lsDataSet.get(i).getStaffmark()).equals(""))
						{
							wcf1 = new WritableCellFeatures();
							wcf1.setComment(CommonTool.FormatString(this.lsDataSet.get(i).getStaffmark()));//设置批注内容
							labname.setCellFeatures(wcf1);//将批注添加到表格上
						}
						sheet.addCell(labname);
						sheet.addCell(new Label(3,k+4,""+this.lsDataSet.get(i).getStaffno()));
						sheet.addCell(new Label(4,k+4,""+this.lsDataSet.get(i).getStaffpositionname()));
						sheet.addCell(new Label(5,k+4,""+this.lsDataSet.get(i).getStaffsocialsource()));
						sheet.addCell(new Label(6,k+4,""+this.lsDataSet.get(i).getStaffpcid()));
						sheet.addCell(new Number(7,k+4,this.lsDataSet.get(i).getWorkdays()));
						sheet.addCell(new Number(8,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStafftotalyeji()).doubleValue()));
						sheet.addCell(new Number(9,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffshopyeji()).doubleValue()));
						sheet.addCell(new Number(10,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffbasesalary()).doubleValue()));
						sheet.addCell(new Number(11,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBeatysubsidy()).doubleValue()));
						sheet.addCell(new Number(12,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStafftargetreward()).doubleValue()));
						sheet.addCell(new Number(13,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffreward()).doubleValue()));
						sheet.addCell(new Number(14,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffsubsidy()).doubleValue()));
						sheet.addCell(new Number(15,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStoresubsidy()).doubleValue()));
						sheet.addCell(new Number(16,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffamtchange()).doubleValue()));
						sheet.addCell(new Number(17,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getNeedpaybsalary()).doubleValue()));
						sheet.addCell(new Number(18,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBasestaffpayamt()).doubleValue()));
						sheet.addCell(new Number(19,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalneedpay()).doubleValue()));
			
						sheet.addCell(new Number(20,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getLatdebit()).doubleValue()));
						sheet.addCell(new Number(21,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getLeaveldebit()).doubleValue()));
						sheet.addCell(new Number(22,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBasestaffamt()).doubleValue()));
						sheet.addCell(new Number(23,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffdebit()).doubleValue()));
						sheet.addCell(new Number(24,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getCosttotal()).doubleValue()));
						sheet.addCell(new Number(25,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffsocials()).doubleValue()));
						sheet.addCell(new Number(26,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getNeedpaysalary()).doubleValue()));
						sheet.addCell(new Number(27,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getSalarydebit()).doubleValue()));
						sheet.addCell(new Number(28,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffdaikou()).doubleValue()));
						sheet.addCell(new Number(29,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaydebit()).doubleValue()));
						sheet.addCell(new Number(30,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStudydebit()).doubleValue()));
						sheet.addCell(new Number(31,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffcost()).doubleValue()));
						sheet.addCell(new Number(32,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getZerenjinback()).doubleValue()));
						sheet.addCell(new Number(33,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getZerenjincost()).doubleValue()));
						sheet.addCell(new Number(34,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getSumcost()).doubleValue()));
						sheet.addCell(new Number(35,k+4,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryA()).doubleValue()));
						sheet.addCell(new Number(36,k+4,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryB()).doubleValue()));
						sheet.addCell(new Label(37,k+4,""));
						totalExcelA=totalExcelA+CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryA()).doubleValue();
						totalExcelB=totalExcelB+CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryB()).doubleValue();
						k++;
				}
				if(leaveCount>0)
				{	
					sheet.addCell(new Label(0,k+4,""));
					sheet.mergeCells(0, k+4, 33, k+4); //合并第一行，1到倒数第一行列
					sheet.addCell(new Label(34,k+4,"总计",titleFormate));
					sheet.addCell(new Number(35,k+4,CommonTool.GetGymAmt(totalExcelA,0)));
					sheet.addCell(new Number(36,k+4,CommonTool.GetGymAmt(totalExcelB,0)));
				}
				if(daifaCount>0)
				{
					k=k+1;
					sheet.mergeCells(0, k+4, excelLength0-1, k+4); //合并第一行，1到倒数第一行列
					head=new Label(0,k+4,strCurCompId+"["+strCurCompName+"]"+"门店待发员工工资汇总表(3月)",titleFormate);
					sheet.setRowView(k+4, 500,false);
					sheet.addCell(head);
					totalExcelA=0;
					totalExcelB=0;
					k++;
					str0x=new Label(0,k+4,"门店",titleFormate);
					sheet.addCell(str0x);
					
					 str0=new Label(1,k+4,"银行账号",titleFormate);
					sheet.addCell(str0);
					
					 str2=new Label(2,k+4,"姓名",titleFormate);
					sheet.addCell(str2);
					
					 str1=new Label(3,k+4,"编号",titleFormate);
					sheet.addCell(str1);
					
					 str3=new Label(4,k+4,"职位",titleFormate);
					sheet.addCell(str3);
					
					 str3x=new Label(5,k+4,"社保",titleFormate);
					sheet.addCell(str3x);
					
					 str4=new Label(6,k+4,"身份证",titleFormate);
					sheet.addCell(str4);
					
					 str8x=new Label(7,k+4,"缺勤",titleFormate);
					sheet.addCell(str8x);
					
					 stra=new Label(8,k+4,"个人业绩",titleFormate);
					sheet.addCell(stra);
					
					 str5=new Label(9,k+4,"门店业绩",titleFormate);
					sheet.addCell(str5);
					
					 str7=new Label(10,k+4,"底薪",titleFormate);
					sheet.addCell(str7);
					
					 str8=new Label(11,k+4,"美容补贴",titleFormate);
					sheet.addCell(str8);
					
					 str81x=new Label(12,k+4,"指标",titleFormate);
					sheet.addCell(str81x);
					
					 str13=new Label(13,k+4,"奖励",titleFormate);
					sheet.addCell(str13);
					
					 str11x=new Label(14,k+4,"门店补贴",titleFormate);
					sheet.addCell(str11x);
					
					 str10=new Label(15,k+4,"保底",titleFormate);
					sheet.addCell(str10);
					
					 str10x=new Label(16,k+4,"差异调整",titleFormate);
					sheet.addCell(str10x);
					
					
					
					 str101x=new Label(17,k+4,"应付工资",titleFormate);
					sheet.addCell(str101x);
					
					
					 str102x=new Label(18,k+4,"关爱(付)",titleFormate);
					sheet.addCell(str102x);
					
					
					 str103x=new Label(19,k+4,"小计",titleFormate);
					sheet.addCell(str103x);
					
					
				
					
					 str12=new Label(20,k+4,"迟到",titleFormate);
					sheet.addCell(str12);
					
					
					 str9=new Label(21,k+4,"离职扣款",titleFormate);
					sheet.addCell(str9);
					
					 str9x=new Label(22,k+4,"关爱基金",titleFormate);
					sheet.addCell(str9x);
					
					 str11=new Label(23,k+4,"罚款",titleFormate);
					sheet.addCell(str11);
					
					
					
					 str15x=new Label(24,k+4,"小计",titleFormate);
					sheet.addCell(str15x);
					
					
					 str15=new Label(25,k+4,"应缴社保",titleFormate);
					sheet.addCell(str15);
					
					 str16=new Label(26,k+4,"税前工资",titleFormate);
					sheet.addCell(str16);
					
					 str17=new Label(27,k+4,"个人所得税",titleFormate);
					sheet.addCell(str17);
					
					 str11xx=new Label(28,k+4,"代扣",titleFormate);
					sheet.addCell(str11xx);
					
					 str18=new Label(29,k+4,"住宿",titleFormate);
					sheet.addCell(str18);
					
					 str19=new Label(30,k+4,"学习费",titleFormate);
					sheet.addCell(str19);
					
					 str20=new Label(31,k+4,"扣成本",titleFormate);
					sheet.addCell(str20);
					
					 str20x=new Label(32,k+4,"责任金返还",titleFormate);
					sheet.addCell(str20x);
					
					 str20xx=new Label(33,k+4,"责任金抵押",titleFormate);
					sheet.addCell(str20xx);
					
					
					 str21=new Label(34,k+4,"税后扣款小计",titleFormate);
					sheet.addCell(str21);
					
					 str22=new Label(35,k+4,"税后工资",titleFormate);
					sheet.addCell(str22);
					
					 str23=new Label(36,k+4,"补充工资",titleFormate);
					sheet.addCell(str23);
					
					 str24=new Label(37,k+4,"签名",titleFormate);
					sheet.addCell(str24);
					k++;
				}
				//待发员工
				for(int i=0;i<this.lsDataSet.size();i++)
				{
						if(CommonTool.FormatInteger(this.lsDataSet.get(i).getStaffcurstate())!=4)
							continue;
						wcf1=null;
						sheet.addCell(new Label(0,k+4,""+this.lsDataSet.get(i).getStrCompName()));
						sheet.addCell(new Label(1,k+4,""+this.lsDataSet.get(i).getStaffbankaccountno()));
						labname=new Label(2,k+4,""+this.lsDataSet.get(i).getStaffname());
						if(!CommonTool.FormatString(this.lsDataSet.get(i).getStaffmark()).equals(""))
						{
							wcf1 = new WritableCellFeatures();
							wcf1.setComment(CommonTool.FormatString(this.lsDataSet.get(i).getStaffmark()));//设置批注内容
							labname.setCellFeatures(wcf1);//将批注添加到表格上
						}
						sheet.addCell(labname);
						sheet.addCell(new Label(3,k+4,""+this.lsDataSet.get(i).getStaffno()));
						sheet.addCell(new Label(4,k+4,""+this.lsDataSet.get(i).getStaffpositionname()));
						sheet.addCell(new Label(5,k+4,""+this.lsDataSet.get(i).getStaffsocialsource()));
						sheet.addCell(new Label(6,k+4,""+this.lsDataSet.get(i).getStaffpcid()));
						sheet.addCell(new Number(7,k+4,this.lsDataSet.get(i).getWorkdays()));
						sheet.addCell(new Number(8,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStafftotalyeji()).doubleValue()));
						sheet.addCell(new Number(9,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffshopyeji()).doubleValue()));
						sheet.addCell(new Number(10,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffbasesalary()).doubleValue()));
						sheet.addCell(new Number(11,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBeatysubsidy()).doubleValue()));
						sheet.addCell(new Number(12,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStafftargetreward()).doubleValue()));
						sheet.addCell(new Number(13,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffreward()).doubleValue()));
						sheet.addCell(new Number(14,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffsubsidy()).doubleValue()));
						sheet.addCell(new Number(15,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStoresubsidy()).doubleValue()));
						sheet.addCell(new Number(16,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffamtchange()).doubleValue()));
						sheet.addCell(new Number(17,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getNeedpaybsalary()).doubleValue()));
						sheet.addCell(new Number(18,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBasestaffpayamt()).doubleValue()));
						sheet.addCell(new Number(19,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalneedpay()).doubleValue()));
			
						sheet.addCell(new Number(20,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getLatdebit()).doubleValue()));
						sheet.addCell(new Number(21,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getLeaveldebit()).doubleValue()));
						sheet.addCell(new Number(22,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBasestaffamt()).doubleValue()));
						sheet.addCell(new Number(23,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffdebit()).doubleValue()));
						sheet.addCell(new Number(24,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getCosttotal()).doubleValue()));
						sheet.addCell(new Number(25,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffsocials()).doubleValue()));
						sheet.addCell(new Number(26,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getNeedpaysalary()).doubleValue()));
						sheet.addCell(new Number(27,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getSalarydebit()).doubleValue()));
						sheet.addCell(new Number(28,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffdaikou()).doubleValue()));
						sheet.addCell(new Number(29,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaydebit()).doubleValue()));
						sheet.addCell(new Number(30,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStudydebit()).doubleValue()));
						sheet.addCell(new Number(31,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffcost()).doubleValue()));
						sheet.addCell(new Number(32,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getZerenjinback()).doubleValue()));
						sheet.addCell(new Number(33,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getZerenjincost()).doubleValue()));
						sheet.addCell(new Number(34,k+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getSumcost()).doubleValue()));
						sheet.addCell(new Number(35,k+4,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryA()).doubleValue()));
						sheet.addCell(new Number(36,k+4,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryB()).doubleValue()));
						sheet.addCell(new Label(37,k+4,""));
						totalExcelA=totalExcelA+CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryA()).doubleValue();
						totalExcelB=totalExcelB+CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryB()).doubleValue();
						k++;
				}
				if(daifaCount>0)
				{	
					sheet.addCell(new Label(0,k+4,""));
					sheet.mergeCells(0, k+4, 33, k+4); //合并第一行，1到倒数第一行列
					sheet.addCell(new Label(34,k+4,"总计",titleFormate));
					sheet.addCell(new Number(35,k+4,CommonTool.GetGymAmt(totalExcelA,0)));
					sheet.addCell(new Number(36,k+4,CommonTool.GetGymAmt(totalExcelB,0)));
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
	
	/*public void createExcel() throws WriteException, IOException
	{
		try
		{
			int excelLength0=39;
			//创建excel工作簿
			WritableWorkbook workbook= Workbook.createWorkbook(os);
			
			//创建新的一页
			WritableSheet sheet =workbook.createSheet("First Sheet", 0);
			//构造表头
			sheet.mergeCells(0, 0, excelLength0-1, 0); //合并第一行，1到倒数第一行列
			
			//区分在职和非离职
			int leaveCount=0;
			int	daifaCount=0;
			double sumLeave=0;
			double sumDaifa=0;
			double sumWroks=0;
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				for(int i=0;i<this.lsDataSet.size();i++)
				{
					if(CommonTool.FormatInteger(this.lsDataSet.get(i).getStaffcurstate())==3)
					{
						leaveCount=leaveCount+1;
						sumLeave=sumLeave+this.lsDataSet.get(i).getFactpaysalary().doubleValue();
					}
					else if(CommonTool.FormatInteger(this.lsDataSet.get(i).getStaffcurstate())==4)
					{
						daifaCount=daifaCount+1;
						sumDaifa=sumDaifa+this.lsDataSet.get(i).getFactpaysalary().doubleValue();
					}
					else
					{
						sumWroks=sumWroks+this.lsDataSet.get(i).getFactpaysalary().doubleValue();
					}
				}
				
			}
			
			
			WritableFont bold=new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD);//设置字体种类和黑体显示
			
			WritableCellFormat titleFormate =new WritableCellFormat(bold);
			titleFormate.setAlignment(jxl.format.Alignment.CENTRE);//单元格的内容水平居中
			titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
			titleFormate.setWrap(true);
			WritableCellFormat numberFormate =new WritableCellFormat();
			numberFormate.setAlignment(jxl.format.Alignment.RIGHT);//单元格的内容水平右对齐
			numberFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中
			
			jxl.write.WritableFont wfc = new jxl.write.WritableFont(WritableFont.ARIAL, 10, 
					WritableFont.NO_BOLD, false, 
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED); 
			WritableCellFormat totalNumberFormate =new WritableCellFormat(wfc);
			totalNumberFormate.setAlignment(jxl.format.Alignment.RIGHT);//单元格的内容水平右对齐
			totalNumberFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//单元格的内容垂直居中


			
			//标题
			Label head=new Label(0,0,strCurCompId+"["+strCurCompName+"]"+"门店员工工资汇总表(3月)",titleFormate);
			sheet.setRowView(0, 500,false);
			sheet.addCell(head);
			
			
			//制表日期：
			Label makeDate=new Label(0,1,"制表日期：",titleFormate);
			sheet.addCell(makeDate);
			

			//制表日期值
			Label makeDateData=new Label(1,1,""+CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet.addCell(makeDateData);
			
			Label str0x20x=new Label(0,2,"",titleFormate);
			sheet.addCell(str0x20x);
			Label str0x20=new Label(1,2,"",titleFormate);
			sheet.addCell(str0x20);
			Label str0x21=new Label(2,2,"",titleFormate);
			sheet.addCell(str0x21);
			Label str0x22=new Label(3,2,"",titleFormate);
			sheet.addCell(str0x22);
			Label str0x23=new Label(4,2,"",titleFormate);
			sheet.addCell(str0x23);
			Label str0x24=new Label(5,2,"",titleFormate);
			sheet.addCell(str0x24);
			Label str0x25=new Label(6,2,"",titleFormate);
			sheet.addCell(str0x25);
			Label str0x26=new Label(7,2,"",titleFormate);
			sheet.addCell(str0x26);
			Label str0x26x=new Label(8,2,"",titleFormate);
			sheet.addCell(str0x26x);
			Label str0x27=new Label(9,2,"应付工资",titleFormate);
			sheet.addCell(str0x27);
			Label str0x215=new Label(17,2,"",titleFormate);
			sheet.addCell(str0x215);
			Label str0x216=new Label(18,2,"",titleFormate);
			sheet.addCell(str0x216);
			Label str0x217=new Label(19,2,"",titleFormate);
			sheet.addCell(str0x217);
			Label str0x218=new Label(20,2,"",titleFormate);
			sheet.addCell(str0x218);
			Label str0x219=new Label(21,2,"关爱基金扣款",titleFormate);
			sheet.addCell(str0x219);
			
			sheet.mergeCells(9, 2, 17, 2); //合并第一行，1到倒数第一行列
			sheet.mergeCells(21, 2, 25, 2); //合并第一行，1到倒数第一行列
			
			Label str0xx=new Label(0,3,"状态",titleFormate);
			sheet.addCell(str0xx);
			
			Label str0x=new Label(1,3,"门店",titleFormate);
			sheet.addCell(str0x);
			
			Label str0=new Label(2,3,"银行账号",titleFormate);
			sheet.addCell(str0);
			
			Label str2=new Label(3,3,"姓名",titleFormate);
			sheet.addCell(str2);
			
			Label str1=new Label(4,3,"编号",titleFormate);
			sheet.addCell(str1);
			
			Label str3=new Label(5,3,"职位",titleFormate);
			sheet.addCell(str3);
			
			Label str3x=new Label(6,3,"社保",titleFormate);
			sheet.addCell(str3x);
			
			Label str4=new Label(7,3,"身份证",titleFormate);
			sheet.addCell(str4);
			
			Label str8x=new Label(8,3,"缺勤",titleFormate);
			sheet.addCell(str8x);
			
			Label stra=new Label(9,3,"个人业绩",titleFormate);
			sheet.addCell(stra);
			
			Label str5=new Label(10,3,"门店业绩",titleFormate);
			sheet.addCell(str5);
			
			Label str7=new Label(11,3,"底薪",titleFormate);
			sheet.addCell(str7);
			
			Label str8=new Label(12,3,"美容补贴",titleFormate);
			sheet.addCell(str8);
			
			Label str81x=new Label(13,3,"指标",titleFormate);
			sheet.addCell(str81x);
			
			Label str13=new Label(14,3,"奖励",titleFormate);
			sheet.addCell(str13);
			
			Label str11x=new Label(15,3,"门店补贴",titleFormate);
			sheet.addCell(str11x);
			
			Label str10=new Label(16,3,"保底",titleFormate);
			sheet.addCell(str10);
			
			Label str10x=new Label(17,3,"差异调整",titleFormate);
			sheet.addCell(str10x);
			
			
			
			Label str101x=new Label(18,3,"应付工资",titleFormate);
			sheet.addCell(str101x);
			
			
			Label str102x=new Label(19,3,"关爱(付)",titleFormate);
			sheet.addCell(str102x);
			
			
			Label str103x=new Label(20,3,"小计",titleFormate);
			sheet.addCell(str103x);
			
			
		
			
			Label str12=new Label(21,3,"迟到",titleFormate);
			sheet.addCell(str12);
			
			
			Label str9=new Label(22,3,"离职扣款",titleFormate);
			sheet.addCell(str9);
			
			Label str9x=new Label(23,3,"关爱基金",titleFormate);
			sheet.addCell(str9x);
			
			Label str11=new Label(24,3,"罚款",titleFormate);
			sheet.addCell(str11);
			
			
			
			Label str15x=new Label(25,3,"小计",titleFormate);
			sheet.addCell(str15x);
			
			
			Label str15=new Label(26,3,"应缴社保",titleFormate);
			sheet.addCell(str15);
			
			Label str16=new Label(27,3,"税前工资",titleFormate);
			sheet.addCell(str16);
			
			Label str17=new Label(28,3,"个人所得税",titleFormate);
			sheet.addCell(str17);
			
			Label str11xx=new Label(29,3,"代扣",titleFormate);
			sheet.addCell(str11xx);
			
			Label str18=new Label(30,3,"住宿",titleFormate);
			sheet.addCell(str18);
			
			Label str19=new Label(31,3,"学习费",titleFormate);
			sheet.addCell(str19);
			
			Label str20=new Label(32,3,"扣成本",titleFormate);
			sheet.addCell(str20);
			
			Label str20x=new Label(33,3,"责任金返还",titleFormate);
			sheet.addCell(str20x);
			
			Label str20xx=new Label(34,3,"责任金抵押",titleFormate);
			sheet.addCell(str20xx);
			
			
			Label str21=new Label(35,3,"税后扣款小计",titleFormate);
			sheet.addCell(str21);
			
			Label str22=new Label(36,3,"税后工资",titleFormate);
			sheet.addCell(str22);
			
			Label str23=new Label(37,3,"补充工资",titleFormate);
			sheet.addCell(str23);
			
			Label str24=new Label(38,3,"签名",titleFormate);
			sheet.addCell(str24);
			Label labname=null;
			WritableCellFeatures wcf1=null;
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				double 	totalExcelA=0;
				double	totalExcelB=0;
				double	sumExcel=0;
				for(int i=0;i<this.lsDataSet.size();i++)
				{
					wcf1=null;
					sheet.addCell(new Label(1,i+4,""+this.lsDataSet.get(i).getStrCompName()));
					sheet.addCell(new Label(2,i+4,""+this.lsDataSet.get(i).getStaffbankaccountno()));
					labname=new Label(3,i+4,""+this.lsDataSet.get(i).getStaffname());
					if(!CommonTool.FormatString(this.lsDataSet.get(i).getStaffmark()).equals(""))
					{
						wcf1 = new WritableCellFeatures();
						wcf1.setComment(CommonTool.FormatString(this.lsDataSet.get(i).getStaffmark()));//设置批注内容
						labname.setCellFeatures(wcf1);//将批注添加到表格上
					}
					sheet.addCell(labname);
					sheet.addCell(new Label(4,i+4,""+this.lsDataSet.get(i).getStaffno()));
					sheet.addCell(new Label(5,i+4,""+this.lsDataSet.get(i).getStaffpositionname()));
					sheet.addCell(new Label(6,i+4,""+this.lsDataSet.get(i).getStaffsocialsource()));
					sheet.addCell(new Label(7,i+4,""+this.lsDataSet.get(i).getStaffpcid()));
					sheet.addCell(new Number(8,i+4,this.lsDataSet.get(i).getWorkdays()));
					sheet.addCell(new Number(9,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStafftotalyeji()).doubleValue()));
					sheet.addCell(new Number(10,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffshopyeji()).doubleValue()));
					sheet.addCell(new Number(11,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffbasesalary()).doubleValue()));
					sheet.addCell(new Number(12,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBeatysubsidy()).doubleValue()));
					sheet.addCell(new Number(13,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStafftargetreward()).doubleValue()));
					sheet.addCell(new Number(14,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffreward()).doubleValue()));
					sheet.addCell(new Number(15,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffsubsidy()).doubleValue()));
					sheet.addCell(new Number(16,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStoresubsidy()).doubleValue()));
					sheet.addCell(new Number(17,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffamtchange()).doubleValue()));
					sheet.addCell(new Number(18,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getNeedpaybsalary()).doubleValue()));
					sheet.addCell(new Number(19,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBasestaffpayamt()).doubleValue()));
					sheet.addCell(new Number(20,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getTotalneedpay()).doubleValue()));
		
					sheet.addCell(new Number(21,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getLatdebit()).doubleValue()));
					sheet.addCell(new Number(22,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getLeaveldebit()).doubleValue()));
					sheet.addCell(new Number(23,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getBasestaffamt()).doubleValue()));
					sheet.addCell(new Number(24,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffdebit()).doubleValue()));
					sheet.addCell(new Number(25,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getCosttotal()).doubleValue()));
					sheet.addCell(new Number(26,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffsocials()).doubleValue()));
					sheet.addCell(new Number(27,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getNeedpaysalary()).doubleValue()));
					sheet.addCell(new Number(28,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getSalarydebit()).doubleValue()));
					sheet.addCell(new Number(29,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffdaikou()).doubleValue()));
					sheet.addCell(new Number(30,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaydebit()).doubleValue()));
					sheet.addCell(new Number(31,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStudydebit()).doubleValue()));
					sheet.addCell(new Number(32,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getStaffcost()).doubleValue()));
					sheet.addCell(new Number(33,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getZerenjinback()).doubleValue()));
					sheet.addCell(new Number(34,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getZerenjincost()).doubleValue()));
					sheet.addCell(new Number(35,i+4,CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getSumcost()).doubleValue()));
					sheet.addCell(new Number(36,i+4,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryA()).doubleValue()));
					sheet.addCell(new Number(37,i+4,CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryB()).doubleValue()));
					sheet.addCell(new Label(38,i+4,""));
					totalExcelA=totalExcelA+CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryA()).doubleValue();
					totalExcelB=totalExcelB+CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getExcelsalaryB()).doubleValue();
				}
				sumExcel=totalExcelA+totalExcelB;
				sheet.mergeCells(0, 4, 0, 3+this.lsDataSet.size()-leaveCount-daifaCount); //合并第一行，1到倒数第一行列
				sheet.mergeCells(0, 3+this.lsDataSet.size()-leaveCount-daifaCount+1, 0, 3+this.lsDataSet.size()-daifaCount); //合并第一行，1到倒数第一行列
				sheet.mergeCells(0, 3+this.lsDataSet.size()-daifaCount+1, 0, 3+this.lsDataSet.size()); //合并第一行，1到倒数第一行列
				
				sheet.addCell(new Label(0,4,"在职人员 ("+CommonTool.GetGymAmt(sumWroks,0)+")",titleFormate));
				sheet.addCell(new Label(0,3+this.lsDataSet.size()-leaveCount-daifaCount+1,"离职人员 ("+CommonTool.GetGymAmt(sumLeave,0)+")",titleFormate));
				sheet.addCell(new Label(0,3+this.lsDataSet.size()-daifaCount+1,"待发人员 ("+CommonTool.GetGymAmt(sumDaifa,0)+")",titleFormate));
				
				sheet.setColumnView(0, 10);
				sheet.addCell(new Label(0,this.lsDataSet.size()+4,""));
				sheet.addCell(new Label(0,this.lsDataSet.size()+5,""));
				sheet.mergeCells(0, this.lsDataSet.size()+4, 34, this.lsDataSet.size()+4); //合并第一行，1到倒数第一行列
				sheet.mergeCells(0, this.lsDataSet.size()+5, 34, this.lsDataSet.size()+5); //合并第一行，1到倒数第一行列
				
				sheet.addCell(new Label(35,this.lsDataSet.size()+4,"合计"));
				sheet.addCell(new Number(36,this.lsDataSet.size()+4,CommonTool.GetGymAmt(totalExcelA,0)));
				sheet.addCell(new Number(37,this.lsDataSet.size()+4,CommonTool.GetGymAmt(totalExcelB,0)));
				sheet.addCell(new Label(35,this.lsDataSet.size()+5,"总计"));
				sheet.addCell(new Number(37,this.lsDataSet.size()+5,CommonTool.GetGymAmt(sumExcel,0)));
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
	}*/
	
	@Action(value = "loadStaffAbsence",  results = { 
			 @Result(name = "load_success", type = "json"),
   @Result(name = "load_failure", type = "json")	
	}) 
	public String loadStaffAbsence()
	{
		try
		{
			if(strFromDate.equals(""))
				strFromDate=CommonTool.getCurrDate();
			if(strToDate.equals(""))
				strToDate=CommonTool.getCurrDate();
			this.lsStaffabsenceinfo=this.pc008Service.loadAbsenceInfos(this.strCurCompId, this.strStaffInNo, CommonTool.setDateMask(strFromDate),CommonTool.setDateMask(strToDate));
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "postAbsenceStaff",  results = { 
			 @Result(name = "load_success", type = "json"),
  @Result(name = "load_failure", type = "json")	
	}) 
	public String postAbsenceStaff()
	{
		try
		{
			this.strMessage="";
			if(this.pc008Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX015")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			if(CommonTool.setDateMask(strAbsencedate).equals(""))
			{
				this.strMessage="请注意缺勤日期格式不正确!";
				return SystemFinal.LOAD_SUCCESS;
			}
			boolean flag=this.pc008Service.validateStaffAbsence(strStaffInNo, this.strAbsencedate);
			if(flag==true)
			{
				this.strMessage="该员工当天的缺勤记录已录入系统!";
				return SystemFinal.LOAD_SUCCESS;
			}
			flag=this.pc008Service.postStaffabsenceinfo(this.strCurCompId, strStaffNo, strStaffInNo, strAbsencedate);
			if(flag==false)
			{
				this.strMessage="录入失败!";
				return SystemFinal.LOAD_SUCCESS;
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	
	@Action(value = "postAllAbsenceStaff",  results = { 
			 @Result(name = "load_success", type = "json"),
 @Result(name = "load_failure", type = "json")	
	}) 
	public String postAllAbsenceStaff()
	{
		try
		{
			this.strMessage="";
			if(this.pc008Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX015")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			if(CommonTool.FormatString(this.strtoMonth).equals(""))
			{
				this.strMessage="请注意必须选择缺勤日期!";
				return SystemFinal.LOAD_SUCCESS;
			}
			String[] toDays=strtoMonth.split(",");
			boolean flag=this.pc008Service.postAllStaffabsenceinfo(this.strCurCompId, strStaffNo, strStaffInNo, toDays);
			toDays=null;
			if(flag==false)
			{
				this.strMessage="录入失败!";
				return SystemFinal.LOAD_SUCCESS;
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	@Action(value = "markOneDataSet",  results = { 
			 @Result(name = "load_success", type = "json"),
 @Result(name = "load_failure", type = "json")	
	}) 
	public String markOneDataSet()
	{
		try
		{
			this.strMessage="";
			if(this.pc008Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX015")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			
			boolean flag=this.pc008Service.validateStaffmark(this.strCurCompId, strStaffInNo, this.strCurMonth);
			if(flag==true)
			{
				this.strMessage="该员工当天已经标记过!";
				return SystemFinal.LOAD_SUCCESS;
			}
			flag=this.pc008Service.postStaffMarkInfo(this.strCurCompId, strStaffInNo, strCurMonth);
			if(flag==false)
			{
				this.strMessage="录入失败!";
				return SystemFinal.LOAD_SUCCESS;
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	
	@Action(value = "dmarkOneDataSet",  results = { 
			 @Result(name = "load_success", type = "json"),
			 @Result(name = "load_failure", type = "json")	
	}) 
	public String dmarkOneDataSet()
	{
		try
		{
			this.strMessage="";
			if(this.pc008Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX015")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			boolean flag=this.pc008Service.deleteSalaryMark(this.strCurCompId, strStaffInNo, strCurMonth);
			if(flag==false)
			{
				this.strMessage="作废失败!";
				return SystemFinal.LOAD_SUCCESS;
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
		}
	}
	
	
	@Action(value = "comfrimstopAbsence",  results = { 
			 @Result(name = "load_success", type = "json"),
 @Result(name = "load_failure", type = "json")	
	}) 
	public String comfrimstopAbsence()
	{
		try
		{
			this.strMessage="";
			if(this.pc008Service.getDataTool().hasSpecialRights(CommonTool.getLoginInfo("USERID"), "QX015")==false)
			{
				this.strMessage=SystemFinal.NO_OPERATE_RIGHT;
				return SystemFinal.POST_FAILURE;
			}
			if(CommonTool.setDateMask(this.strCurDate).equals(""))
			{
				this.strMessage="请注意缺勤日期格式不正确!";
				return SystemFinal.LOAD_SUCCESS;
			}
			
			boolean flag=this.pc008Service.deleteStaffAbsence(this.strCurCompId, strStaffInNo, strCurDate);
			if(flag==false)
			{
				this.strMessage="作废失败!";
				return SystemFinal.LOAD_SUCCESS;
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return SystemFinal.LOAD_FAILURE;
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
	public List<StaffWarkSalaryAnlanysis> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<StaffWarkSalaryAnlanysis> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	public String getStrSearchType() {
		return strSearchType;
	}
	public void setStrSearchType(String strSearchType) {
		this.strSearchType = strSearchType;
	}
	@JSON(serialize=false)
	public PC008Service getPc008Service() {
		return pc008Service;
	}
	@JSON(serialize=false)
	public void setPc008Service(PC008Service pc008Service) {
		this.pc008Service = pc008Service;
	}
	public String getStrCurCompName() {
		return strCurCompName;
	}
	public void setStrCurCompName(String strCurCompName) {
		this.strCurCompName = strCurCompName;
	}
	public List<Staffabsenceinfo> getLsStaffabsenceinfo() {
		return lsStaffabsenceinfo;
	}
	public void setLsStaffabsenceinfo(List<Staffabsenceinfo> lsStaffabsenceinfo) {
		this.lsStaffabsenceinfo = lsStaffabsenceinfo;
	}
	public String getStrStaffInNo() {
		return strStaffInNo;
	}
	public void setStrStaffInNo(String strStaffInNo) {
		this.strStaffInNo = strStaffInNo;
	}
	public String getStrAbsencedate() {
		return strAbsencedate;
	}
	public void setStrAbsencedate(String strAbsencedate) {
		this.strAbsencedate = strAbsencedate;
	}
	public String getStrStaffNo() {
		return strStaffNo;
	}
	public void setStrStaffNo(String strStaffNo) {
		this.strStaffNo = strStaffNo;
	}
	public String getStrCurDate() {
		return strCurDate;
	}
	public void setStrCurDate(String strCurDate) {
		this.strCurDate = strCurDate;
	}
	public String getStrCurMonth() {
		return strCurMonth;
	}
	public void setStrCurMonth(String strCurMonth) {
		this.strCurMonth = strCurMonth;
	}
	public String getStrtoMonth() {
		return strtoMonth;
	}
	public void setStrtoMonth(String strtoMonth) {
		this.strtoMonth = strtoMonth;
	}
	public int getIA3() {
		return iA3;
	}
	public void setIA3(int ia3) {
		iA3 = ia3;
	}

	
	
}
