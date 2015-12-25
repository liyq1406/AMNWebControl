package com.amani.action.PersonnelControl;

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

import com.amani.model.Staffinfo;

import com.amani.service.PersonnelControl.PC010Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/pc010")
public class PC010Action {
	@Autowired
	private PC010Service pc010Service;
	private String strCurCompId;
	private String strCurCompName;
	private String department;
	private String postion;
	private int socialsourceflag;
	private String strMessage;
	private List<Staffinfo> lsDataSet;
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
		
		this.lsDataSet=this.pc010Service.loadDateSetByCompId(strCurCompId,department,postion,socialsourceflag);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadpc010Excel",  results = { 
			 @Result(name = "load_success", location = "/PersonnelControl/PC010/pc010Excel.jsp"),
			 @Result(name = "load_failure",  location = "/PersonnelControl/PC010/pc010Excel.jsp")	
	}) 
	public String loadpc010Excel() {  //查询工资
		this.lsDataSet=this.pc010Service.loadDateSetByCompId(strCurCompId,department,postion,socialsourceflag);
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
			
			Label str0=new Label(0,2,"门店",titleFormate);
			sheet.addCell(str0);
			
			Label str1=new Label(1,2,"部门",titleFormate);
			sheet.addCell(str1);
			
			Label str2=new Label(2,2,"职位",titleFormate);
			     
			sheet.addCell(str2);
	
		      
			
			Label str3=new Label(3,2,"工号",titleFormate);
			sheet.addCell(str3);
			
			Label str4=new Label(4,2,"姓名",titleFormate);
			sheet.addCell(str4);
			
			Label stra=new Label(5,2,"性别",titleFormate);
			sheet.addCell(stra);
			
			Label str5=new Label(6,2,"手机号码",titleFormate);
			sheet.addCell(str5);
			
			Label str7=new Label(7,2,"身份证号",titleFormate);
			sheet.addCell(str7);
			
			Label str8=new Label(8,2,"身份证地址",titleFormate);
			sheet.addCell(str8);
			
			Label str8x=new Label(9,2,"入职日期",titleFormate);
			sheet.addCell(str8x);
			
			Label str9x=new Label(10,2,"档案号",titleFormate);
			sheet.addCell(str9x);
			
			Label str9=new Label(11,2,"合同终止日期",titleFormate);
			sheet.addCell(str9);
			
			Label str10=new Label(12,2,"基本工资",titleFormate);
			sheet.addCell(str10);
			
			Label str11=new Label(13,2,"社保类型",titleFormate);
			sheet.addCell(str11);
			
			Label str11x=new Label(14,2,"社保",titleFormate);
			sheet.addCell(str11x);
			
			Label str12=new Label(15,2,"业务人员",titleFormate);
			sheet.addCell(str12);
			
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				for(int i=0;i<this.lsDataSet.size();i++)
				{
					sheet.addCell(new Label(0,i+3,""+this.lsDataSet.get(i).getBcompname()));
					sheet.addCell(new Label(1,i+3,""+this.lsDataSet.get(i).getDepartmentText()));
					sheet.addCell(new Label(2,i+3,""+this.lsDataSet.get(i).getPositionText()));
					sheet.addCell(new Label(3,i+3,""+this.lsDataSet.get(i).getBstaffno()));
					sheet.addCell(new Label(4,i+3,""+this.lsDataSet.get(i).getStaffname()));
					if(CommonTool.FormatInteger(this.lsDataSet.get(i).getStaffsex())==0)
						sheet.addCell(new Label(5,i+3,"女"));
					else
						sheet.addCell(new Label(5,i+3,"男"));
					sheet.addCell(new Label(6,i+3,""+this.lsDataSet.get(i).getMobilephone()));
					sheet.addCell(new Label(7,i+3,""+this.lsDataSet.get(i).getPccid()));
					sheet.addCell(new Label(8,i+3,""+this.lsDataSet.get(i).getAaddress()));
					sheet.addCell(new Label(9,i+3,""+this.lsDataSet.get(i).getArrivaldate()));
					sheet.addCell(new Label(10,i+3,""+this.lsDataSet.get(i).getFillno()));
					sheet.addCell(new Label(11,i+3,""+this.lsDataSet.get(i).getContractdate()));
					sheet.addCell(new Label(12,i+3,""+CommonTool.FormatBigDecimalZ(this.lsDataSet.get(i).getBasesalary())));
					sheet.addCell(new Label(13,i+3,""+this.lsDataSet.get(i).getSocialsource()));
					sheet.addCell(new Label(14,i+3,""+CommonTool.FormatBigDecimal(this.lsDataSet.get(i).getSocialsecurity())));
					if(CommonTool.FormatInteger(this.lsDataSet.get(i).getBusinessflag())==1)
						sheet.addCell(new Label(15,i+3,"业务"));
					else
						sheet.addCell(new Label(15,i+3,"非业务"));
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

	public List<Staffinfo> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<Staffinfo> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}

	@JSON(serialize=false)
	public PC010Service getPc010Service() {
		return pc010Service;
	}
	@JSON(serialize=false)
	public void setPc010Service(PC010Service pc010Service) {
		this.pc010Service = pc010Service;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getStrCurCompName() {
		return strCurCompName;
	}
	public void setStrCurCompName(String strCurCompName) {
		this.strCurCompName = strCurCompName;
	}
	public String getPostion() {
		return postion;
	}
	public void setPostion(String postion) {
		this.postion = postion;
	}
	public int getSocialsourceflag() {
		return socialsourceflag;
	}
	public void setSocialsourceflag(int socialsourceflag) {
		this.socialsourceflag = socialsourceflag;
	}

	
	
}
