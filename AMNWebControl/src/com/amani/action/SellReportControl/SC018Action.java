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

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amani.bean.SC018Bean;
import com.amani.service.SellReportControl.SC018Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/sc018")
public class SC018Action {
	@Autowired
	private SC018Service sc018Service;
	private String strCurCompId;
	private String strDate;
	private String strMessage;
	private List<SC018Bean> lsDataSet;
	private OutputStream os;
    public OutputStream getOs() {
		return os;
	}
	public void setOs(OutputStream os) {
		this.os = os;
	}
	
	//默认查询
	@Action( value="loadDataSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		this.lsDataSet=this.sc018Service.loadDateSetByCompId(strCurCompId, strDate);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//导出Excel查询
	@Action(value = "loadSC018Excel",  results = { 
			 @Result(name = "load_success", location = "/SellReportControl/SC018/sc018Excel.jsp"),
			 @Result(name = "load_failure",  location = "/SellReportControl/SC018/sc018Excel.jsp")	
	}) 
	public String loadSC018Excel() {
		this.lsDataSet=this.sc018Service.loadDateSetByCompId(strCurCompId, strDate);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	public void createExcel() throws WriteException, IOException
	{
		try
		{
			int excelLength0=31;
			//创建excel工作簿
			WritableWorkbook workbook= Workbook.createWorkbook(os);
			
			//创建新的一页
			WritableSheet sheet =workbook.createSheet("新门店运营分析", 0);
			//构造表头
			sheet.mergeCells(0, 0, excelLength0-1, 0); //合并第一行，1到倒数第一行列
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
			Label head=new Label(0,0,"新门店运营分析",titleFormate);
			sheet.setRowView(0, 500,false);
			sheet.addCell(head);
			
			//制表日期：
			Label makeDate=new Label(0,1,"制表日期：",titleFormate);
			sheet.addCell(makeDate);
			
			
			//制表日期值
			Label makeDateData=new Label(1, 1, CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet.addCell(makeDateData);
			Label str0=new Label(0,2,"店编号",titleFormate);
			sheet.addCell(str0);
			sheet.mergeCells(0, 2, 0, 3); 
			Label str1=new Label(1,2,"店名称",titleFormate);
			sheet.addCell(str1);
			sheet.mergeCells(1, 2, 1, 3); 
			Label str2=new Label(2,2,"日期",titleFormate);
			sheet.addCell(str2);
			sheet.mergeCells(2, 2, 2, 3); 
			Label str3=new Label(3,2,"耗卡率",titleFormate);
			sheet.addCell(str3);
			sheet.mergeCells(3, 2, 3, 3);
			
			Label str4=new Label(4,2,"门店会员",titleFormate);
			sheet.addCell(str4);
			sheet.mergeCells(4, 2, 12, 2);
			Label str5=new Label(13,2,"美容疗程",titleFormate);
			sheet.addCell(str5);
			sheet.mergeCells(13, 2, 21, 2);
			Label str6=new Label(22,2,"美发疗程",titleFormate);
			sheet.addCell(str6);
			sheet.mergeCells(22, 2, 30, 2);
			
			Label str04=new Label(4,3,"客单量",titleFormate);
			sheet.addCell(str04);
			Label str05=new Label(5,3,"虚客单价",titleFormate);
			sheet.addCell(str05);
			Label str06=new Label(6,3,"实客单价",titleFormate);
			sheet.addCell(str06);
			Label str07=new Label(7,3,"新增会员",titleFormate);
			sheet.addCell(str07);
			Label str08=new Label(8,3,"总会员量",titleFormate);
			sheet.addCell(str08);
			Label str09=new Label(9,3,"常到会员",titleFormate);
			sheet.addCell(str09);
			Label str10=new Label(10,3,"有效会员",titleFormate);
			sheet.addCell(str10);
			Label str11=new Label(11,3,"沉睡会员",titleFormate);
			sheet.addCell(str11);
			Label str12=new Label(12,3,"流失会员",titleFormate);
			sheet.addCell(str12);
			
			Label str13=new Label(13,3,"客单量",titleFormate);
			sheet.addCell(str13);
			Label str14=new Label(14,3,"虚客单价",titleFormate);
			sheet.addCell(str14);
			Label str15=new Label(15,3,"实客单价",titleFormate);
			sheet.addCell(str15);
			Label str16=new Label(16,3,"会员量",titleFormate);
			sheet.addCell(str16);
			Label str17=new Label(17,3,"新增会员",titleFormate);
			sheet.addCell(str17);
			Label str18=new Label(18,3,"常到会员",titleFormate);
			sheet.addCell(str18);
			Label str19=new Label(19,3,"有效会员",titleFormate);
			sheet.addCell(str19);
			Label str20=new Label(20,3,"沉睡会员",titleFormate);
			sheet.addCell(str20);
			Label str21=new Label(21,3,"流失会员",titleFormate);
			sheet.addCell(str21);
			
			Label str22=new Label(22,3,"客单量",titleFormate);
			sheet.addCell(str22);
			Label str23=new Label(23,3,"虚客单价",titleFormate);
			sheet.addCell(str23);
			Label str24=new Label(24,3,"实客单价",titleFormate);
			sheet.addCell(str24);
			Label str25=new Label(25,3,"会员量",titleFormate);
			sheet.addCell(str25);
			Label str26=new Label(26,3,"新增会员",titleFormate);
			sheet.addCell(str26);
			Label str27=new Label(27,3,"常到会员",titleFormate);
			sheet.addCell(str27);
			Label str28=new Label(28,3,"有效会员",titleFormate);
			sheet.addCell(str28);
			Label str29=new Label(29,3,"沉睡会员",titleFormate);
			sheet.addCell(str29);
			Label str30=new Label(30,3,"流失会员",titleFormate);
			sheet.addCell(str30);
			
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0)
			{
				int row=4;
				for (SC018Bean bean : lsDataSet) {
					sheet.addCell(new Label(0, row, bean.getCompid()));
					sheet.addCell(new Label(1, row, bean.getCompname()));
					sheet.addCell(new Label(2, row, bean.getDqny()));
					sheet.addCell(new Label(3, row, bean.getHkl(), numberFormate));
					sheet.addCell(new Label(4, row, bean.getKdzs(), numberFormate));
					sheet.addCell(new Label(5, row, bean.getXkdj(), numberFormate));
					sheet.addCell(new Label(6, row, bean.getSkdj(), numberFormate));
					sheet.addCell(new Label(7, row, bean.getMdhy_xzhy(), numberFormate));
					sheet.addCell(new Label(8, row, bean.getMdhy_hyzs(), numberFormate));
					sheet.addCell(new Label(9, row, bean.getMdhy_cdhy(), numberFormate));
					sheet.addCell(new Label(10, row, bean.getMdhy_yxhy(), numberFormate));
					sheet.addCell(new Label(11, row, bean.getMdhy_cshy(), numberFormate));
					sheet.addCell(new Label(12, row, bean.getMdhy_lshy(), numberFormate));
					sheet.addCell(new Label(13, row, bean.getMr_kdzs(), numberFormate));
					sheet.addCell(new Label(14, row, bean.getMr_xkdj(), numberFormate));
					sheet.addCell(new Label(15, row, bean.getMr_skdj(), numberFormate));
					sheet.addCell(new Label(16, row, bean.getMrhy_hyzs(), numberFormate));
					sheet.addCell(new Label(17, row, bean.getMrhy_xzhy(), numberFormate));
					sheet.addCell(new Label(18, row, bean.getMrhy_cdhy(), numberFormate));
					sheet.addCell(new Label(19, row, bean.getMrhy_yxhy(), numberFormate));
					sheet.addCell(new Label(20, row, bean.getMrhy_cshy(), numberFormate));
					sheet.addCell(new Label(21, row, bean.getMrhy_lshy(), numberFormate));
					sheet.addCell(new Label(22, row, bean.getMf_kdzs(), numberFormate));
					sheet.addCell(new Label(23, row, bean.getMf_xkdj(), numberFormate));
					sheet.addCell(new Label(24, row, bean.getMf_skdj(), numberFormate));
					sheet.addCell(new Label(25, row, bean.getMfhy_hyzs(), numberFormate));
					sheet.addCell(new Label(26, row, bean.getMfhy_xzhy(), numberFormate));
					sheet.addCell(new Label(27, row, bean.getMfhy_cdhy(), numberFormate));
					sheet.addCell(new Label(28, row, bean.getMfhy_yxhy(), numberFormate));
					sheet.addCell(new Label(29, row, bean.getMfhy_cshy(), numberFormate));
					sheet.addCell(new Label(30, row, bean.getMfhy_lshy(), numberFormate));
					row++;
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
	public String getStrDate() {
		return strDate;
	}
	public void setStrDate(String strDate) {
		if(StringUtils.isEmpty(strDate)){
			strDate=CommonTool.getCurrDate().substring(0,6);
		}else{
			strDate=strDate.substring(0,4)+strDate.substring(5,7);
		}
		this.strDate = strDate;
	}
	public String getStrMessage() {
		return strMessage;
	}
	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}
	public List<SC018Bean> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<SC018Bean> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	@JSON(serialize=false)
	public SC018Service getSc018Service() {
		return sc018Service;
	}
	@JSON(serialize=false)
	public void setSc018Service(SC018Service sc018Service) {
		this.sc018Service = sc018Service;
	}
}
