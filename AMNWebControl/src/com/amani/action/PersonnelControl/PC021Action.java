package com.amani.action.PersonnelControl;

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

import com.amani.model.Staffinfo;
import com.amani.service.PersonnelControl.PC010Service;
import com.amani.service.PersonnelControl.PC021Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/pc021")
public class PC021Action {
	@Autowired
	private PC021Service pc021Service;
	private String strCurCompId;
	private String strCurCompName;
	private String strFromDate;
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
		//根据，公司、部门、门店、年份查询员工请假天数
		this.lsDataSet=this.pc021Service.loadDateSetByCompId(strCurCompId,strFromDate);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadpc021Excel",  results = { 
			 @Result(name = "load_success", location = "/PersonnelControl/PC021/pc021Excel.jsp"),
			 @Result(name = "load_failure",  location = "/PersonnelControl/PC021/pc021Excel.jsp")	
	}) 
	public String loadpc021Excel() {
		//根据，公司、部门、门店、年份查询员工请假天数
		strCurCompName = this.pc021Service.getDataTool().loadCompNameById(strCurCompId);
		this.lsDataSet=this.pc021Service.loadDateSetByCompId(strCurCompId,strFromDate);
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
			Label head=new Label(0,0,strCurCompId+"["+strCurCompName+"]"+"门店员工年假统计",titleFormate);
			sheet.setRowView(0, 500,false);
			sheet.mergeCells(0, 0, 11, 0);
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
	
			Label str3=new Label(3,2,"职称",titleFormate);
			sheet.addCell(str3);
			
			Label str4=new Label(4,2,"工号",titleFormate);
			sheet.addCell(str4);
			
			Label str5=new Label(5,2,"姓名",titleFormate);
			sheet.addCell(str5);
			
			Label str6=new Label(6,2,"性别",titleFormate);
			sheet.addCell(str6);
			
			Label str7=new Label(7,2,"手机号码",titleFormate);
			sheet.addCell(str7);
			
			Label str8=new Label(8,2,"身份证号",titleFormate);
			sheet.addCell(str8);

			Label str9=new Label(9,2,"档案号",titleFormate);
			sheet.addCell(str9);
			
			Label str10=new Label(10,2,"入职日期",titleFormate);
			sheet.addCell(str10);
			
			Label str11=new Label(11,2,"年假天数",titleFormate);
			sheet.addCell(str11);
	
			
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				for(int i=0;i<this.lsDataSet.size();i++)
				{
					sheet.addCell(new Label(0,i+3,""+this.lsDataSet.get(i).getBcompname()));
					sheet.addCell(new Label(1,i+3,""+this.lsDataSet.get(i).getDepartmentText()));
					sheet.addCell(new Label(2,i+3,""+this.lsDataSet.get(i).getPositionText()));
					sheet.addCell(new Label(3,i+3,""+this.lsDataSet.get(i).getPositiontitleText()));
					sheet.addCell(new Label(4,i+3,""+this.lsDataSet.get(i).getBstaffno()));
					sheet.addCell(new Label(5,i+3,""+this.lsDataSet.get(i).getStaffname()));
					if(CommonTool.FormatInteger(this.lsDataSet.get(i).getStaffsex())==0)
						sheet.addCell(new Label(6,i+3,"女"));
					else
						sheet.addCell(new Label(6,i+3,"男"));
					sheet.addCell(new Label(7,i+3,""+this.lsDataSet.get(i).getMobilephone()));
					sheet.addCell(new Label(8,i+3,""+this.lsDataSet.get(i).getPccid()));
					sheet.addCell(new Label(9,i+3,""+this.lsDataSet.get(i).getFillno()));
					sheet.addCell(new Label(10,i+3,""+this.lsDataSet.get(i).getArrivaldate()));
					sheet.addCell(new Label(11,i+3,""+this.lsDataSet.get(i).getYears()));
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
	public PC021Service getPc021Service() {
		return pc021Service;
	}
	@JSON(serialize=false)
	public void setPc021Service(PC021Service pc021Service) {
		this.pc021Service = pc021Service;
	}

	public String getStrCurCompName() {
		return strCurCompName;
	}
	public void setStrCurCompName(String strCurCompName) {
		this.strCurCompName = strCurCompName;
	}


	
	public String getStrFromDate() {
		return strFromDate;
	}
	/**
	 *  @BareFieldName : strFromDate
	 *
	 *  @return  the strFromDate
	 *
	 *  @param strFromDate the strFromDate to set
	 *
	 **/
	
	public void setStrFromDate(String strFromDate) {
		this.strFromDate = strFromDate;
	}


	
	
}
