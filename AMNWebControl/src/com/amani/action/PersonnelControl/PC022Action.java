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

import com.amani.bean.PC022Bean;
import com.amani.service.PersonnelControl.PC022Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/pc022")
public class PC022Action {
	@Autowired
	private PC022Service pc022Service;
	private String strCurCompId;
	private String strCurCompName;
	private String strFromDate;
	private String strMessage;
	private List<PC022Bean> lsDataSet;
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
		//根据，公司、部门、门店、月份门店考情统计
		this.lsDataSet=this.pc022Service.loadDateSetByCompId(strCurCompId,strFromDate);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	@Action(value = "loadpc022Excel",  results = { 
			 @Result(name = "load_success", location = "/PersonnelControl/PC022/pc022Excel.jsp"),
			 @Result(name = "load_failure",  location = "/PersonnelControl/PC022/pc022Excel.jsp")	
	}) 
	public String loadpc022Excel() {
		//根据，公司、部门、门店、年份查询员工请假天数
		strCurCompName = this.pc022Service.getDataTool().loadCompNameById(strCurCompId);
		this.lsDataSet=this.pc022Service.loadDateSetByCompId(strCurCompId,strFromDate);
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
			Label head=new Label(0,0,strCurCompId+"["+strCurCompName+"]"+"门店考勤统计",titleFormate);
			sheet.setRowView(0, 500,false);
			sheet.mergeCells(0, 0, 32, 0);
			sheet.addCell(head);
			
			//制表日期：
			Label makeDate=new Label(0,1,"制表日期：",titleFormate);
			sheet.addCell(makeDate);
			
			
			//制表日期值
			Label makeDateData=new Label(1,1,""+CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet.addCell(makeDateData);
			
			Label str0=new Label(0,2,"工号",titleFormate);
			sheet.addCell(str0);
			
			Label str1=new Label(1,2,"姓名",titleFormate);
			sheet.addCell(str1);
			
			Label srt = null;
			for(int i=1; i<=31; i++){
				srt = new Label(i+1,2,i+"",titleFormate);
				sheet.addCell(srt);
			}
	
			
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				for(int i=0;i<this.lsDataSet.size();i++)
				{
					sheet.addCell(new Label(0,i+3,""+this.lsDataSet.get(i).getBstaffno()));
					sheet.addCell(new Label(1,i+3,""+this.lsDataSet.get(i).getStaffname()));
					  sheet.addCell(new Label(2,i+3,""+this.lsDataSet.get(i).getData1()));
					  sheet.addCell(new Label(3,i+3,""+this.lsDataSet.get(i).getData2()));
					  sheet.addCell(new Label(4,i+3,""+this.lsDataSet.get(i).getData3()));
					  sheet.addCell(new Label(5,i+3,""+this.lsDataSet.get(i).getData4()));
					  sheet.addCell(new Label(6,i+3,""+this.lsDataSet.get(i).getData5()));
					  sheet.addCell(new Label(7,i+3,""+this.lsDataSet.get(i).getData6()));
					  sheet.addCell(new Label(8,i+3,""+this.lsDataSet.get(i).getData7()));
					  sheet.addCell(new Label(9,i+3,""+this.lsDataSet.get(i).getData8()));
					  sheet.addCell(new Label(10,i+3,""+this.lsDataSet.get(i).getData9()));
					  sheet.addCell(new Label(11,i+3,""+this.lsDataSet.get(i).getData10()));
					  sheet.addCell(new Label(12,i+3,""+this.lsDataSet.get(i).getData11()));
					  sheet.addCell(new Label(13,i+3,""+this.lsDataSet.get(i).getData12()));
					  sheet.addCell(new Label(14,i+3,""+this.lsDataSet.get(i).getData13()));
					  sheet.addCell(new Label(15,i+3,""+this.lsDataSet.get(i).getData14()));
					  sheet.addCell(new Label(16,i+3,""+this.lsDataSet.get(i).getData15()));
					  sheet.addCell(new Label(17,i+3,""+this.lsDataSet.get(i).getData16()));
					  sheet.addCell(new Label(18,i+3,""+this.lsDataSet.get(i).getData17()));
					  sheet.addCell(new Label(19,i+3,""+this.lsDataSet.get(i).getData18()));
					  sheet.addCell(new Label(20,i+3,""+this.lsDataSet.get(i).getData19()));
					  sheet.addCell(new Label(21,i+3,""+this.lsDataSet.get(i).getData20()));
					  sheet.addCell(new Label(22,i+3,""+this.lsDataSet.get(i).getData21()));
					  sheet.addCell(new Label(23,i+3,""+this.lsDataSet.get(i).getData22()));
					  sheet.addCell(new Label(24,i+3,""+this.lsDataSet.get(i).getData23()));
					  sheet.addCell(new Label(25,i+3,""+this.lsDataSet.get(i).getData24()));
					  sheet.addCell(new Label(26,i+3,""+this.lsDataSet.get(i).getData25()));
					  sheet.addCell(new Label(27,i+3,""+this.lsDataSet.get(i).getData26()));
					  sheet.addCell(new Label(28,i+3,""+this.lsDataSet.get(i).getData27()));
					  sheet.addCell(new Label(29,i+3,""+this.lsDataSet.get(i).getData28()));
					  sheet.addCell(new Label(30,i+3,""+this.lsDataSet.get(i).getData29()));
					  sheet.addCell(new Label(31,i+3,""+this.lsDataSet.get(i).getData30()));
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

	public List<PC022Bean> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<PC022Bean> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}

	@JSON(serialize=false)
	public PC022Service getPc022Service() {
		return pc022Service;
	}
	@JSON(serialize=false)
	public void setPc022Service(PC022Service pc022Service) {
		this.pc022Service = pc022Service;
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
