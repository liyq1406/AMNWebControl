package com.amani.action.PersonnelControl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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

import com.amani.model.Staffchangeinfo;

import com.amani.service.PersonnelControl.PC006Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/pc006")
public class PC006Action {
	@Autowired
	private PC006Service pc006Service;
	private String strCurCompId;
	private String strMonth;
	private String strMessage;
	private List<Staffchangeinfo> lsDataSetA;   //入职明细
	private List<Staffchangeinfo> lsDataSetB;   //离职明细
	private List<Staffchangeinfo> lsDataSetC;   //本店调动明细
	private List<Staffchangeinfo> lsDataSetD;   //跨店调动明细
	private List<Staffchangeinfo> lsDataSetE;	//重回公司明细
	private List<Staffchangeinfo> lsDataSetF;	//请假申请明细
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
		if(strMonth.equals(""))
			strMonth=CommonTool.getCurrDate().substring(0,6);
		else
			strMonth=strMonth.substring(0,4)+strMonth.substring(5,7);
		lsDataSetA=new ArrayList();
		lsDataSetB=new ArrayList();
		lsDataSetC=new ArrayList();
		lsDataSetD=new ArrayList();
		lsDataSetE=new ArrayList();
		lsDataSetF=new ArrayList();
		List<Staffchangeinfo> ls=this.pc006Service.loadDateSetByCompId(strCurCompId,strMonth);
		if(ls!=null && ls.size()>0)
		{
			for(int i=0;i<ls.size();i++)
			{
				if(ls.get(i).getBchangetype()==2)
				{
					lsDataSetA.add(ls.get(i));
				}
				else if(ls.get(i).getBchangetype()==1)
				{
					lsDataSetB.add(ls.get(i));
				}
				else if(ls.get(i).getBchangetype()==5)
				{
					lsDataSetC.add(ls.get(i));
				}
				else if(ls.get(i).getBchangetype()==6)
				{
					lsDataSetD.add(ls.get(i));
				}
				else if(ls.get(i).getBchangetype()==3)
				{
					lsDataSetE.add(ls.get(i));
				}
				else if(ls.get(i).getBchangetype()==4)
				{
					lsDataSetF.add(ls.get(i));
				}
			}
		}
		ls=null;
		return SystemFinal.LOAD_SUCCESS;
	}
	
	
	@Action(value = "loadpc006Excel",  results = { 
			 @Result(name = "load_success", location = "/PersonnelControl/PC006/pc006Excel.jsp"),
			 @Result(name = "load_failure",  location = "/PersonnelControl/PC006/pc006Excel.jsp")	
	}) 
	public String loadpc006Excel() {  //查询工资
		if(strMonth.equals(""))
			strMonth=CommonTool.getCurrDate().substring(0,6);
		else
			strMonth=strMonth.substring(0,4)+strMonth.substring(5,7);
		lsDataSetA=new ArrayList();
		lsDataSetB=new ArrayList();
		lsDataSetC=new ArrayList();
		lsDataSetD=new ArrayList();
		lsDataSetE=new ArrayList();
		lsDataSetF=new ArrayList();
		List<Staffchangeinfo> ls=this.pc006Service.loadDateSetByCompId(strCurCompId,strMonth);
		if(ls!=null && ls.size()>0)
		{
			for(int i=0;i<ls.size();i++)
			{
				if(ls.get(i).getBchangetype()==2)
				{
					lsDataSetA.add(ls.get(i));
				}
				else if(ls.get(i).getBchangetype()==1)
				{
					lsDataSetB.add(ls.get(i));
				}
				else if(ls.get(i).getBchangetype()==5)
				{
					lsDataSetC.add(ls.get(i));
				}
				else if(ls.get(i).getBchangetype()==6)
				{
					lsDataSetD.add(ls.get(i));
				}
				else if(ls.get(i).getBchangetype()==3)
				{
					lsDataSetE.add(ls.get(i));
				}
				else if(ls.get(i).getBchangetype()==4)
				{
					lsDataSetF.add(ls.get(i));
				}
			}
		}
		ls=null;
		return SystemFinal.LOAD_SUCCESS;

	}
	
	
	public void createExcel() throws WriteException, IOException
	{
		try
		{
			//创建excel工作簿
			WritableWorkbook workbook= Workbook.createWorkbook(os);
			int excelLength0=10;
			int excelLength1=12;
			int excelLength2=13;
			int excelLength3=15;
			int excelLength4=10;
			//创建新的一页
			WritableSheet sheet =workbook.createSheet("入职申请", 0);
			sheet.mergeCells(0, 0, excelLength0-1, 0); //合并第一行，1到倒数第一行列
			//创建新的一页
			WritableSheet sheet1 =workbook.createSheet("离职申请", 1);
			sheet1.mergeCells(0, 0, excelLength1-1, 0); //合并第一行，1到倒数第一行列
			//创建新的一页
			WritableSheet sheet2 =workbook.createSheet("本店调动", 2);
			sheet2.mergeCells(0, 0, excelLength2-1, 0); //合并第一行，1到倒数第一行列
			//创建新的一页
			WritableSheet sheet3 =workbook.createSheet("跨店调动", 3);
			sheet3.mergeCells(0, 0, excelLength3-1, 0); //合并第一行，1到倒数第一行列
			//创建新的一页
			WritableSheet sheet4 =workbook.createSheet("重回公司", 4);
			sheet4.mergeCells(0, 0, excelLength4-1, 0); //合并第一行，1到倒数第一行列
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
			Label head=new Label(0,0,"门店人事异动汇总表[入职申请]",titleFormate);
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
			
			Label str2=new Label(2,2,"部门",titleFormate);
			sheet.addCell(str2);
			
			Label str3=new Label(3,2,"职位",titleFormate);
			sheet.addCell(str3);
			
			Label str4=new Label(4,2,"工号",titleFormate);
			sheet.addCell(str4);
			
			Label str5=new Label(5,2,"姓名",titleFormate);
			sheet.addCell(str5);
			
			Label str6=new Label(6,2,"手机号码",titleFormate);
			sheet.addCell(str6);
			
			Label str7=new Label(7,2,"身份证号码",titleFormate);
			sheet.addCell(str7);
			
			Label str8=new Label(8,2,"入职日期",titleFormate);
			sheet.addCell(str8);
			
			Label str9=new Label(9,2,"备注",titleFormate);
			sheet.addCell(str9);
			
			if(this.lsDataSetA!=null&&this.lsDataSetA.size()>0)
			{
				for(int i=0;i<this.lsDataSetA.size();i++)
				{
					sheet.addCell(new Label(0,i+3,""+this.lsDataSetA.get(i).getAppchangecompid()));
					sheet.addCell(new Label(1,i+3,""+this.lsDataSetA.get(i).getAppchangecompname()));
					sheet.addCell(new Label(2,i+3,""+this.lsDataSetA.get(i).getAfterdepartmentText()));
					sheet.addCell(new Label(3,i+3,""+this.lsDataSetA.get(i).getAfterpostationText()));
					sheet.addCell(new Label(4,i+3,""+this.lsDataSetA.get(i).getChangestaffno()));
					sheet.addCell(new Label(5,i+3,""+this.lsDataSetA.get(i).getChangestaffname()));
					sheet.addCell(new Label(6,i+3,""+this.lsDataSetA.get(i).getStaffphone()));
					sheet.addCell(new Label(7,i+3,""+this.lsDataSetA.get(i).getStaffpcid()));
					sheet.addCell(new Label(8,i+3,""+CommonTool.getDateMask(this.lsDataSetA.get(i).getValidateenddate())));
					sheet.addCell(new Label(9,i+3,""+CommonTool.FormatString(this.lsDataSetA.get(i).getRemark())));
				}
			}
			lsDataSetA=null;
			
			
			/****创建要显示的内容***/
			//标题
			Label head1=new Label(0,0,"门店人事异动汇总表[离职申请]",titleFormate);
			sheet1.setRowView(0, 500,false);
			sheet1.addCell(head1);
			
			//制表日期：
			Label makeDate1=new Label(0,1,"制表日期：",titleFormate);
			sheet1.addCell(makeDate1);
			
			
			//制表日期值
			Label makeDateData1=new Label(1,1,""+CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet1.addCell(makeDateData1);
			
			Label str10=new Label(0,2,"门店编号",titleFormate);
			sheet1.addCell(str10);
			
			Label str11=new Label(1,2,"门店名称",titleFormate);
			sheet1.addCell(str11);
			
			Label str12=new Label(2,2,"部门",titleFormate);
			sheet1.addCell(str12);
			
			Label str13=new Label(3,2,"职位",titleFormate);
			sheet1.addCell(str13);
			
			Label str14=new Label(4,2,"工号",titleFormate);
			sheet1.addCell(str14);
			
			Label str15=new Label(5,2,"姓名",titleFormate);
			sheet1.addCell(str15);
			
			Label str16=new Label(6,2,"手机号码",titleFormate);
			sheet1.addCell(str16);
			
			Label str17=new Label(7,2,"身份证号码",titleFormate);
			sheet1.addCell(str17);
			
			Label str18=new Label(8,2,"入职日期",titleFormate);
			sheet1.addCell(str18);
			
			Label str19=new Label(9,2,"离职日期",titleFormate);
			sheet1.addCell(str19);
			
			Label str110=new Label(10,2,"社保",titleFormate);
			sheet1.addCell(str110);
			
			Label str112=new Label(11,2,"离职类型",titleFormate);
			sheet1.addCell(str112);
			
			Label str111=new Label(12,2,"离职备注",titleFormate);
			sheet1.addCell(str111);
			Label str113=new Label(13,2,"入职备注",titleFormate);
			sheet1.addCell(str113);
			if(this.lsDataSetB!=null&&this.lsDataSetB.size()>0)
			{
				for(int i=0;i<this.lsDataSetB.size();i++)
				{
					sheet1.addCell(new Label(0,i+3,""+this.lsDataSetB.get(i).getAppchangecompid()));
					sheet1.addCell(new Label(1,i+3,""+this.lsDataSetB.get(i).getAppchangecompname()));
					sheet1.addCell(new Label(2,i+3,""+this.lsDataSetB.get(i).getAfterdepartmentText()));
					sheet1.addCell(new Label(3,i+3,""+this.lsDataSetB.get(i).getAfterpostationText()));
					sheet1.addCell(new Label(4,i+3,""+this.lsDataSetB.get(i).getChangestaffno()));
					sheet1.addCell(new Label(5,i+3,""+this.lsDataSetB.get(i).getChangestaffname()));
					sheet1.addCell(new Label(6,i+3,""+this.lsDataSetB.get(i).getStaffphone()));
					sheet1.addCell(new Label(7,i+3,""+this.lsDataSetB.get(i).getStaffpcid()));
					sheet1.addCell(new Label(8,i+3,""+CommonTool.getDateMask(this.lsDataSetB.get(i).getArrivaldate())));
					sheet1.addCell(new Label(9,i+3,""+CommonTool.getDateMask(this.lsDataSetB.get(i).getValidateenddate())));
					sheet1.addCell(new Label(10,i+3,""+this.lsDataSetB.get(i).getSocialsecurity().toString()));
					sheet1.addCell(new Label(11,i+3,""+this.lsDataSetB.get(i).getLeaveltypeText()));		
					sheet1.addCell(new Label(12,i+3,""+CommonTool.FormatString(this.lsDataSetB.get(i).getRemark())));
					sheet1.addCell(new Label(13,i+3,""+CommonTool.FormatString(this.lsDataSetB.get(i).getStaffmark())));
				}
			}
			lsDataSetB=null;
			
			
			/****创建要显示的内容***/
			//标题
			Label head2=new Label(0,0,"门店人事异动汇总表[本店调动]",titleFormate);
			sheet2.setRowView(0, 500,false);
			sheet2.addCell(head2);
			
			//制表日期：
			Label makeDate2=new Label(0,1,"制表日期：",titleFormate);
			sheet2.addCell(makeDate2);
			
			
			//制表日期值
			Label makeDateData2=new Label(1,1,""+CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet2.addCell(makeDateData2);
			
			Label str20=new Label(0,2,"门店编号",titleFormate);
			sheet2.addCell(str20);
			
			Label str21=new Label(1,2,"门店名称",titleFormate);
			sheet2.addCell(str21);
			
			Label str22=new Label(2,2,"原部门",titleFormate);
			sheet2.addCell(str22);
			
			Label str23=new Label(3,2,"新部门",titleFormate);
			sheet2.addCell(str23);
			
			Label str24=new Label(4,2,"原职位",titleFormate);
			sheet2.addCell(str24);
			
			Label str25=new Label(5,2,"新职位",titleFormate);
			sheet2.addCell(str25);
			
			Label str26=new Label(6,2,"原工号",titleFormate);
			sheet2.addCell(str26);
			
			Label str27=new Label(7,2,"新工号",titleFormate);
			sheet2.addCell(str27);
			
			Label str28=new Label(8,2,"姓名",titleFormate);
			sheet2.addCell(str28);
			
			Label str29=new Label(9,2,"手机号码",titleFormate);
			sheet2.addCell(str29);
			
			Label str210=new Label(10,2,"身份证号码",titleFormate);
			sheet2.addCell(str210);
			
			
			Label str211=new Label(11,2,"生效日期",titleFormate);
			sheet2.addCell(str211);

			Label str212=new Label(12,2,"备注",titleFormate);
			sheet2.addCell(str212);
			
			if(this.lsDataSetC!=null&&this.lsDataSetC.size()>0)
			{
				for(int i=0;i<this.lsDataSetC.size();i++)
				{
					sheet2.addCell(new Label(0,i+3,""+this.lsDataSetC.get(i).getAppchangecompid()));
					sheet2.addCell(new Label(1,i+3,""+this.lsDataSetC.get(i).getAppchangecompname()));
					sheet2.addCell(new Label(2,i+3,""+this.lsDataSetC.get(i).getBeforedepartmentText()));
					sheet2.addCell(new Label(3,i+3,""+this.lsDataSetC.get(i).getAfterdepartmentText()));
					sheet2.addCell(new Label(4,i+3,""+this.lsDataSetC.get(i).getBeforepostationText()));
					sheet2.addCell(new Label(5,i+3,""+this.lsDataSetC.get(i).getAfterpostationText()));
					sheet2.addCell(new Label(6,i+3,""+this.lsDataSetC.get(i).getChangestaffno()));
					sheet2.addCell(new Label(7,i+3,""+this.lsDataSetC.get(i).getAfterstaffno()));
					sheet2.addCell(new Label(8,i+3,""+this.lsDataSetC.get(i).getChangestaffname()));
					sheet2.addCell(new Label(9,i+3,""+this.lsDataSetC.get(i).getStaffphone()));
					sheet2.addCell(new Label(10,i+3,""+this.lsDataSetC.get(i).getStaffpcid()));
					sheet2.addCell(new Label(11,i+3,""+CommonTool.getDateMask(this.lsDataSetC.get(i).getValidateenddate())));
					sheet2.addCell(new Label(12,i+3,""+CommonTool.FormatString(this.lsDataSetC.get(i).getRemark())));
				}
			}
			lsDataSetC=null;
			
			/****创建要显示的内容***/
			//标题
			Label head3=new Label(0,0,"门店人事异动汇总表[跨店调动]",titleFormate);
			sheet3.setRowView(0, 500,false);
			sheet3.addCell(head3);
			
			//制表日期：
			Label makeDate3=new Label(0,1,"制表日期：",titleFormate);
			sheet3.addCell(makeDate3);
			
			
			//制表日期值
			Label makeDateData3=new Label(1,1,""+CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet3.addCell(makeDateData3);
			
			Label str30=new Label(0,2,"老门店编号",titleFormate);
			sheet3.addCell(str30);
			
			Label str31=new Label(1,2,"老门店名称",titleFormate);
			sheet3.addCell(str31);
			
			
			Label str30x=new Label(2,2,"新门店编号",titleFormate);
			sheet3.addCell(str30x);
			
			Label str31x=new Label(3,2,"新门店名称",titleFormate);
			sheet3.addCell(str31x);
			
			Label str32=new Label(4,2,"原部门",titleFormate);
			sheet3.addCell(str32);
			
			Label str33=new Label(5,2,"新部门",titleFormate);
			sheet3.addCell(str33);
			
			Label str34=new Label(6,2,"原职位",titleFormate);
			sheet3.addCell(str34);
			
			Label str35=new Label(7,2,"新职位",titleFormate);
			sheet3.addCell(str35);
			
			Label str36=new Label(8,2,"原工号",titleFormate);
			sheet3.addCell(str36);
			
			Label str37=new Label(9,2,"新工号",titleFormate);
			sheet3.addCell(str37);
			
			Label str38=new Label(10,2,"姓名",titleFormate);
			sheet3.addCell(str38);
			
			Label str39=new Label(11,2,"手机号码",titleFormate);
			sheet3.addCell(str39);
			
			Label str310=new Label(12,2,"身份证号码",titleFormate);
			sheet3.addCell(str310);
			
			
			Label str311=new Label(12,2,"生效日期",titleFormate);
			sheet3.addCell(str311);

			Label str312=new Label(14,2,"备注",titleFormate);
			sheet3.addCell(str312);
			
			if(this.lsDataSetD!=null&&this.lsDataSetD.size()>0)
			{
				for(int i=0;i<this.lsDataSetD.size();i++)
				{
					sheet3.addCell(new Label(0,i+3,""+this.lsDataSetD.get(i).getAppchangecompid()));
					sheet3.addCell(new Label(1,i+3,""+this.lsDataSetD.get(i).getAppchangecompname()));
					sheet3.addCell(new Label(2,i+3,""+this.lsDataSetD.get(i).getAftercompid()));
					sheet3.addCell(new Label(3,i+3,""+this.lsDataSetD.get(i).getAftercompname()));
					sheet3.addCell(new Label(4,i+3,""+this.lsDataSetD.get(i).getBeforedepartmentText()));
					sheet3.addCell(new Label(5,i+3,""+this.lsDataSetD.get(i).getAfterdepartmentText()));
					sheet3.addCell(new Label(6,i+3,""+this.lsDataSetD.get(i).getBeforepostationText()));
					sheet3.addCell(new Label(7,i+3,""+this.lsDataSetD.get(i).getAfterpostationText()));
					sheet3.addCell(new Label(8,i+3,""+this.lsDataSetD.get(i).getChangestaffno()));
					sheet3.addCell(new Label(9,i+3,""+this.lsDataSetD.get(i).getAfterstaffno()));
					sheet3.addCell(new Label(10,i+3,""+this.lsDataSetD.get(i).getChangestaffname()));
					sheet3.addCell(new Label(11,i+3,""+this.lsDataSetD.get(i).getStaffphone()));
					sheet3.addCell(new Label(12,i+3,""+this.lsDataSetD.get(i).getStaffpcid()));
					sheet3.addCell(new Label(13,i+3,""+CommonTool.getDateMask(this.lsDataSetD.get(i).getValidateenddate())));
					sheet3.addCell(new Label(14,i+3,""+CommonTool.FormatString(this.lsDataSetD.get(i).getRemark())));
				}
			}
			lsDataSetD=null;
			
			
			
			/****创建要显示的内容***/
			//标题
			Label head4=new Label(0,0,"门店人事异动汇总表[重回调动]",titleFormate);
			sheet4.setRowView(0, 500,false);
			sheet4.addCell(head4);
			
			//制表日期：
			Label makeDate4=new Label(0,1,"制表日期：",titleFormate);
			sheet4.addCell(makeDate4);
			
			
			//制表日期值
			Label makeDateData4=new Label(1,1,""+CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet4.addCell(makeDateData4);
			
			Label str40=new Label(0,2,"门店编号",titleFormate);
			sheet4.addCell(str40);
			
			Label str41=new Label(1,2,"门店名称",titleFormate);
			sheet4.addCell(str41);
			
			Label str42=new Label(2,2,"原部门",titleFormate);
			sheet4.addCell(str42);
			
			Label str43=new Label(3,2,"新部门",titleFormate);
			sheet4.addCell(str43);
			
			Label str44=new Label(4,2,"原职位",titleFormate);
			sheet4.addCell(str44);
			
			Label str45=new Label(5,2,"新职位",titleFormate);
			sheet4.addCell(str45);
			
			Label str46=new Label(6,2,"原工号",titleFormate);
			sheet4.addCell(str46);
			
			Label str47=new Label(7,2,"新工号",titleFormate);
			sheet4.addCell(str47);
			
			Label str48=new Label(8,2,"姓名",titleFormate);
			sheet4.addCell(str48);
			
			Label str49=new Label(9,2,"手机号码",titleFormate);
			sheet4.addCell(str49);
			
			Label str410=new Label(10,2,"身份证号码",titleFormate);
			sheet4.addCell(str410);
			
			
			Label str411=new Label(11,2,"生效日期",titleFormate);
			sheet4.addCell(str411);

			Label str412=new Label(12,2,"备注",titleFormate);
			sheet4.addCell(str412);
			
			if(this.lsDataSetE!=null&&this.lsDataSetE.size()>0)
			{
				for(int i=0;i<this.lsDataSetE.size();i++)
				{
					sheet4.addCell(new Label(0,i+3,""+this.lsDataSetE.get(i).getAftercompid()));
					sheet4.addCell(new Label(1,i+3,""+this.lsDataSetE.get(i).getAftercompname()));
					sheet4.addCell(new Label(2,i+3,""+this.lsDataSetE.get(i).getBeforedepartmentText()));
					sheet4.addCell(new Label(3,i+3,""+this.lsDataSetE.get(i).getAfterdepartmentText()));
					sheet4.addCell(new Label(4,i+3,""+this.lsDataSetE.get(i).getBeforepostationText()));
					sheet4.addCell(new Label(5,i+3,""+this.lsDataSetE.get(i).getAfterpostationText()));
					sheet4.addCell(new Label(6,i+3,""+this.lsDataSetE.get(i).getChangestaffno()));
					sheet4.addCell(new Label(7,i+3,""+this.lsDataSetE.get(i).getAfterstaffno()));
					sheet4.addCell(new Label(8,i+3,""+this.lsDataSetE.get(i).getChangestaffname()));
					sheet4.addCell(new Label(9,i+3,""+this.lsDataSetE.get(i).getStaffphone()));
					sheet4.addCell(new Label(10,i+3,""+this.lsDataSetE.get(i).getStaffpcid()));
					sheet4.addCell(new Label(11,i+3,""+CommonTool.getDateMask(this.lsDataSetE.get(i).getValidateenddate())));
					sheet4.addCell(new Label(12,i+3,""+CommonTool.FormatString(this.lsDataSetE.get(i).getRemark())));
				}
			}
			lsDataSetE=null;
			
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
	public PC006Service getPc006Service() {
		return pc006Service;
	}
	@JSON(serialize=false)
	public void setPc006Service(PC006Service pc006Service) {
		this.pc006Service = pc006Service;
	}
	public String getStrMonth() {
		return strMonth;
	}
	public void setStrMonth(String strMonth) {
		this.strMonth = strMonth;
	}
	public List<Staffchangeinfo> getLsDataSetA() {
		return lsDataSetA;
	}
	public void setLsDataSetA(List<Staffchangeinfo> lsDataSetA) {
		this.lsDataSetA = lsDataSetA;
	}
	public List<Staffchangeinfo> getLsDataSetB() {
		return lsDataSetB;
	}
	public void setLsDataSetB(List<Staffchangeinfo> lsDataSetB) {
		this.lsDataSetB = lsDataSetB;
	}
	public List<Staffchangeinfo> getLsDataSetC() {
		return lsDataSetC;
	}
	public void setLsDataSetC(List<Staffchangeinfo> lsDataSetC) {
		this.lsDataSetC = lsDataSetC;
	}
	public List<Staffchangeinfo> getLsDataSetD() {
		return lsDataSetD;
	}
	public void setLsDataSetD(List<Staffchangeinfo> lsDataSetD) {
		this.lsDataSetD = lsDataSetD;
	}
	public List<Staffchangeinfo> getLsDataSetE() {
		return lsDataSetE;
	}
	public void setLsDataSetE(List<Staffchangeinfo> lsDataSetE) {
		this.lsDataSetE = lsDataSetE;
	}
	public List<Staffchangeinfo> getLsDataSetF() {
		return lsDataSetF;
	}
	public void setLsDataSetF(List<Staffchangeinfo> lsDataSetF) {
		this.lsDataSetF = lsDataSetF;
	}
	
	
	
}
