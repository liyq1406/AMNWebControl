package com.amani.action.SellReportControl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.amani.bean.SC019Bean;
import com.amani.model.Companyinfo;
import com.amani.service.SellReportControl.SC019Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/sc019")
public class SC019Action {
	@Autowired
	private SC019Service sc019Service;
	private String strCurCompId;
	private String strDate;
	private String strMessage;
	private Integer sysStatus;
	private List<SC019Bean> lsDataSet;
	private List<Companyinfo> compSet;
	private OutputStream os;
    public OutputStream getOs() {
		return os;
	}
	public void setOs(OutputStream os) {
		this.os = os;
	}
	
	@Action( value="loadCompanySet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadCompanySet() {
		this.compSet=this.sc019Service.loadCompanyData();
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//门店查询
	@Action( value="loadhSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDataSet() {
		this.lsDataSet=this.sc019Service.loadDateSet(strCurCompId, strDate, sysStatus);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//美容部查询
	@Action( value="loadfSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadFaceSet() {
		this.lsDataSet=this.sc019Service.loadFaceSet(strCurCompId, strDate, sysStatus);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//美发部查询
	@Action( value="loadmSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadHairSet() {
		this.lsDataSet=this.sc019Service.loadHairSet(strCurCompId, strDate, sysStatus);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//会员分层查询
	@Action( value="loadvSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadVipSet() {
		this.lsDataSet=this.sc019Service.loadVipSet(strCurCompId, strDate);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//会员明细查询
	@Action( value="loaddSet", results={ @Result( type="json", name="load_success"),
			 @Result( type="json", name="load_failure")})
	public String loadDetailSet() {
		this.lsDataSet=this.sc019Service.loadDetailSet(strCurCompId, strDate);
		return SystemFinal.LOAD_SUCCESS;
	}
	
	//导出Excel查询
	@Action(value = "loadSC019Excel",  results = { 
			 @Result(name = "load_success", location = "/SellReportControl/SC019/sc019Excel.jsp"),
			 @Result(name = "load_failure",  location = "/SellReportControl/SC019/sc019Excel.jsp")	
	}) 
	public String loadSC019Excel() {
		return SystemFinal.LOAD_SUCCESS;
	}
	
	public void createExcel() throws WriteException, IOException{
		try{
			String title=null;
			int excelLength0=0;
			if(sysStatus==1){
				title="阿玛尼门店经营（月）报表";
				excelLength0=17;
			}else if(sysStatus==2){
				title="阿玛尼门店美容经营（月）报表";
				excelLength0=18;
			}else if(sysStatus==3){
				title="阿玛尼门店美发经营（月）报表";
				excelLength0=20;
			}
			//创建excel工作簿
			WritableWorkbook workbook= Workbook.createWorkbook(os);
			
			//创建新的一页
			WritableSheet sheet =workbook.createSheet(title, 0);
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
			Label head=new Label(0,0,title,titleFormate);
			sheet.setRowView(0, 500,false);
			sheet.addCell(head);
			
			//制表日期：
			Label makeDate=new Label(0,1,"制表日期：",titleFormate);
			sheet.addCell(makeDate);
			sheet.mergeCells(1, 1, 2, 1);
			
			//制表日期值
			Label makeDateData=new Label(1, 1, CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet.addCell(makeDateData);
			
			Label lable=new Label(0,2,"门店基础信息",titleFormate);
			sheet.addCell(lable);
			sheet.mergeCells(0, 2, 1, 2);
			
			//加载门店基础信息
			addCompGrid(sheet, 1, titleFormate);
			
			int row=5;//从第六行开始添加表格
			//经营数据1
			Map<Integer, String> header = new HashMap<Integer, String>();
			header.put(0, "经营数据");
			header.put(14, "合计");
			header.put(15, "2015年目标");
			header.put(16, "完成百分比");
			row = addGrid(sheet, row, 2, header, titleFormate, numberFormate);
			
			//经营数据2
			header.put(14, "月均");
			header.put(15, (sysStatus==1 ? "公司":"市场")+"平均值");
			header.put(16, "差异量");
			row = addGrid(sheet, row, 3, header, titleFormate, numberFormate);
			
			if(sysStatus==1){
				//美发人员产出
				header.put(0, "美发人员产出");
				row = addGrid(sheet, row, 4, header, titleFormate, numberFormate);
				
				//营销节奏
				addMarketGrid(sheet, row, 5, titleFormate, numberFormate);
			}else {
				if(sysStatus==2){
					//项目信息
					header.put(0, "项目信息");
					header.put(14, "合计");
					header.put(15, "2015年目标");
					header.put(16, "完成百分比");
					row = addGrid(sheet, row, 4, header, titleFormate, numberFormate);
					
					//顾客分层管理
					header.remove(14);
					header.remove(15);
					header.put(0, "顾客分层管理");
					header.put(2, "2014年");
					header.put(3, "目标");
					header.put(16, "合计");
					row = addCustomer(sheet, row, 5, header, titleFormate, numberFormate);
					
					//美容师人均产出
					header.remove(2);
					header.put(0, "美容师人均产出");
					header.put(2, "人数");
					header.put(15, "月均");
					header.put(16, "市场平均值");
					header.put(17, "差异量");
					row = addOutputGrid(sheet, row, 6, header, titleFormate, numberFormate);
					
					//员工业绩
					header.clear();
					header.put(0, "员工业绩");
					header.put(2, "职级");
					header.put(3, "总实业绩");
					header.put(4, "总客单");
					header.put(5, "客单价");
					row = addFaceEmpGrid(sheet, row, 7, header, titleFormate, numberFormate);
					
					//营销节奏
					addMarketGrid(sheet, row, 8, titleFormate, numberFormate);
				}else if(sysStatus==3){
					//美发师人均产出
					header.remove(14);
					header.put(0, "美发师人均产出");
					header.put(2, "人数");
					header.put(15, "月均");
					header.put(16, "市场平均值");
					header.put(17, "差异量");
					row = addOutputGrid(sheet, row, 4, header, titleFormate, numberFormate);
					
					//顾客分层管理
					header.remove(15);
					header.remove(17);
					header.put(0, "顾客分层管理");
					header.put(2, "2014年");
					header.put(3, "目标");
					header.put(16, "合计");
					row = addCustomer(sheet, row, 5, header, titleFormate, numberFormate);
					
					//员工业绩
					header.remove(16);
					header.put(0, "员工业绩");
					header.put(2, "职级");
					header.put(3, "总实业绩");
					header.put(4, "总客单");
					header.put(5, "客单价");
					header.put(6, "烫染占比");
					header.put(7, "护理占比");
					row = addHairEmpGrid(sheet, row, 6, header, titleFormate, numberFormate);
					
					//营销节奏
					addMarketGrid(sheet, row, 7, titleFormate, numberFormate);
				}
			}
			
			workbook.write();
			workbook.close();
			os.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addCompGrid(WritableSheet sheet, int status, WritableCellFormat titleFormate) throws Exception{
		List<SC019Bean> list = null;
		if(sysStatus==1){
			list = this.sc019Service.loadDateSet(strCurCompId, strDate, status);
		}else if(sysStatus==2){
			list = this.sc019Service.loadFaceSet(strCurCompId, strDate, status);
		}else if(sysStatus==3){
			list = this.sc019Service.loadHairSet(strCurCompId, strDate, status);
		}
		SC019Bean data = list==null || list.get(0)==null ? new SC019Bean() : list.get(0);
		Label lable=new Label(0,3,"编号店名",titleFormate);
		sheet.addCell(lable);
		sheet.mergeCells(0, 3, 1, 3);
		lable=new Label(2,3, data.getAttr1());
		sheet.addCell(lable);
		sheet.mergeCells(2, 3, 3, 3);
		lable=new Label(0,4,"开业时间",titleFormate);
		sheet.addCell(lable);
		sheet.mergeCells(0, 4, 1, 4);
		lable=new Label(2,4, data.getAttr2());
		sheet.addCell(lable);
		sheet.mergeCells(2, 4, 3, 4);
		
		if(sysStatus==1 || sysStatus==2){
			lable=new Label(4,3,"美容房间数",titleFormate);
			sheet.addCell(lable);
			sheet.mergeCells(4, 3, 5, 3);
			lable=new Label(6,3,data.getAttr3());
			sheet.addCell(lable);
			lable=new Label(7,3,"顾问人数",titleFormate);
			sheet.addCell(lable);
			sheet.mergeCells(7, 3, 8, 3);
			lable=new Label(9,3,data.getAttr5());
			sheet.addCell(lable);
			lable=new Label(4,4,"美容经理人数",titleFormate);
			sheet.addCell(lable);
			sheet.mergeCells(4, 4, 5, 4);
			lable=new Label(6,4,data.getAttr4());
			sheet.addCell(lable);
			lable=new Label(7,4,"美容师人数",titleFormate);
			sheet.addCell(lable);
			sheet.mergeCells(7, 4, 8, 4);
			lable=new Label(9,4,data.getAttr6());
			sheet.addCell(lable);
		}else if(sysStatus==3){
			lable=new Label(4,3,"总面积",titleFormate);
			sheet.addCell(lable);
			sheet.mergeCells(4, 3, 5, 3);
			lable=new Label(6,3,data.getAttr7());
			sheet.addCell(lable);
			lable=new Label(7,3,"美发师",titleFormate);
			sheet.addCell(lable);
			sheet.mergeCells(7, 3, 8, 3);
			lable=new Label(9,3,data.getAttr9());
			sheet.addCell(lable);
			lable=new Label(4,4,"烫染师",titleFormate);
			sheet.addCell(lable);
			sheet.mergeCells(4, 4, 5, 4);
			lable=new Label(6,4,data.getAttr8());
			sheet.addCell(lable);
			lable=new Label(7,4,"大堂经理",titleFormate);
			sheet.addCell(lable);
			sheet.mergeCells(7, 4, 8, 4);
			lable=new Label(9,4,data.getAttr10());
			sheet.addCell(lable);
		}
		if(sysStatus==1){
			lable=new Label(10,3,"总面积",titleFormate);
			sheet.addCell(lable);
			sheet.mergeCells(10, 3, 11, 3);
			lable=new Label(12,3,data.getAttr7());
			sheet.addCell(lable);
			lable=new Label(13,3,"美发师",titleFormate);
			sheet.addCell(lable);
			sheet.mergeCells(13, 3, 14, 3);
			lable=new Label(15,3,data.getAttr9());
			sheet.addCell(lable);
			lable=new Label(10,4,"烫染师",titleFormate);
			sheet.addCell(lable);
			sheet.mergeCells(10, 4, 11, 4);
			lable=new Label(12,4,data.getAttr8());
			sheet.addCell(lable);
			lable=new Label(13,4,"大堂经理",titleFormate);
			sheet.addCell(lable);
			sheet.mergeCells(13, 4, 14, 4);
			lable=new Label(15,4,data.getAttr10());
			sheet.addCell(lable);
		}
	}
	
	/**
	 * 添加公共相等属性表
	 * @param sheet
	 * @param row
	 * @param status
	 * @param title
	 * @param titleFormate
	 * @param numberFormate
	 * @throws Exception
	 */
	public int addGrid(WritableSheet sheet, int row, int status, Map<Integer, String> header, 
					WritableCellFormat titleFormate, WritableCellFormat numberFormate) throws Exception{
		row++;
		//添加标题
		for (Map.Entry<Integer, String> entry : header.entrySet()) {
			sheet.addCell(new Label(entry.getKey(), row, entry.getValue(), titleFormate));
		}
		sheet.mergeCells(0, row, 1, row);
		//添加月份标题
		for (int i = 1; i <= 12; i++) {
			sheet.addCell(new Label(i+1, row, (i+"月"),titleFormate));
		}
		row++;
		//填充数据
		List<SC019Bean> list = null;
		if(sysStatus==1){
			list = this.sc019Service.loadDateSet(strCurCompId, strDate, status);
		}else if(sysStatus==2){
			list = this.sc019Service.loadFaceSet(strCurCompId, strDate, status);
		}else if(sysStatus==3){
			list = this.sc019Service.loadHairSet(strCurCompId, strDate, status);
		}
		if(list!=null&& list.size()>0){
			for (SC019Bean bean : list) {
				sheet.addCell(new Label(0, row, bean.getAttr1()));
				sheet.mergeCells(0, row, 1, row);
				sheet.addCell(new Label(2, row, bean.getMonth1(), numberFormate));
				sheet.addCell(new Label(3, row, bean.getMonth2(), numberFormate));
				sheet.addCell(new Label(4, row, bean.getMonth3(), numberFormate));
				sheet.addCell(new Label(5, row, bean.getMonth4(), numberFormate));
				sheet.addCell(new Label(6, row, bean.getMonth5(), numberFormate));
				sheet.addCell(new Label(7, row, bean.getMonth6(), numberFormate));
				sheet.addCell(new Label(8, row, bean.getMonth7(), numberFormate));
				sheet.addCell(new Label(9, row, bean.getMonth8(), numberFormate));
				sheet.addCell(new Label(10, row, bean.getMonth9(), numberFormate));
				sheet.addCell(new Label(11, row, bean.getMonth10(), numberFormate));
				sheet.addCell(new Label(12, row, bean.getMonth11(), numberFormate));
				sheet.addCell(new Label(13, row, bean.getMonth12(), numberFormate));
				sheet.addCell(new Label(14, row, bean.getAttr2(), numberFormate));
				sheet.addCell(new Label(15, row, bean.getAttr3(), numberFormate));
				sheet.addCell(new Label(16, row, bean.getAttr4(), numberFormate));
				row++;
			}
		}
		return row;
	}
	
	/**
	 * 添加营销节奏表
	 * @param sheet
	 * @param row
	 * @param index
	 * @param titleFormate
	 * @param numberFormate
	 * @throws Exception
	 */
	public int addMarketGrid(WritableSheet sheet, int row, int status, WritableCellFormat titleFormate, WritableCellFormat numberFormate) throws Exception{
		row++;
		Label lable=new Label(0,row,"营销节奏",titleFormate);
		sheet.addCell(lable);
		sheet.mergeCells(0, row, 1, row);
		lable=new Label(2,row,"2014年实际达成",titleFormate);
		sheet.addCell(lable);
		sheet.mergeCells(2, row, 3, row);
		lable=new Label(4,row,"2015年计划目标",titleFormate);
		sheet.addCell(lable);
		sheet.mergeCells(4, row, 5, row);
		lable=new Label(6,row,"增长百分比",titleFormate);
		sheet.addCell(lable);
		sheet.mergeCells(6, row, 7, row);
		lable=new Label(8,row,"营销活动计划",titleFormate);
		sheet.addCell(lable);
		sheet.mergeCells(8, row, 10, row);
		//填充数据
		row++;
		List<SC019Bean> list = null;
		if(sysStatus==1){
			list = this.sc019Service.loadDateSet(strCurCompId, strDate, status);
		}else if(sysStatus==2){
			list = this.sc019Service.loadFaceSet(strCurCompId, strDate, status);
		}else if(sysStatus==3){
			list = this.sc019Service.loadHairSet(strCurCompId, strDate, status);
		}
		if(list!=null&& list.size()>0){
			for (SC019Bean bean : list) {
				sheet.addCell(new Label(0, row, bean.getAttr1()));
				sheet.mergeCells(0, row, 1, row);
				sheet.addCell(new Label(2, row, bean.getAttr2(), numberFormate));
				sheet.mergeCells(2, row, 3, row);
				sheet.addCell(new Label(4, row, bean.getAttr3(), numberFormate));
				sheet.mergeCells(4, row, 5, row);
				sheet.addCell(new Label(6, row, bean.getAttr4(), numberFormate));
				sheet.mergeCells(6, row, 7, row);
				sheet.addCell(new Label(8, row, bean.getAttr5(), numberFormate));
				sheet.mergeCells(8, row, 10, row);
				row++;
			}
		}
		return row;
	}
	
	/**
	 * 添加顾客分层管理
	 * @param sheet
	 * @param row
	 * @param status
	 * @param titleFormate
	 * @param numberFormate
	 * @throws Exception
	 */
	public int addCustomer(WritableSheet sheet, int row, int status, Map<Integer, String> header,
			WritableCellFormat titleFormate, WritableCellFormat numberFormate) throws Exception{
		row++;
		//添加标题
		for (Map.Entry<Integer, String> entry : header.entrySet()) {
			sheet.addCell(new Label(entry.getKey(), row, entry.getValue(), titleFormate));
		}
		sheet.mergeCells(0, row, 1, row);
		
		//添加月份标题
		for (int i = 1; i <= 12; i++) {
			sheet.addCell(new Label(i+3, row, (i+"月"),titleFormate));
		}
		row++;
		//填充数据
		List<SC019Bean> list = null;
		if(sysStatus==2){
			list = this.sc019Service.loadFaceSet(strCurCompId, strDate, status);
		}else if(sysStatus==3){
			list = this.sc019Service.loadHairSet(strCurCompId, strDate, status);
		}
		if(list!=null&& list.size()>0){
			for (SC019Bean bean : list) {
				sheet.addCell(new Label(0, row, bean.getAttr1()));
				sheet.mergeCells(0, row, 1, row);
				sheet.addCell(new Label(2, row, bean.getAttr2(), numberFormate));
				sheet.addCell(new Label(3, row, bean.getAttr3(), numberFormate));
				sheet.addCell(new Label(4, row, bean.getMonth1(), numberFormate));
				sheet.addCell(new Label(5, row, bean.getMonth2(), numberFormate));
				sheet.addCell(new Label(6, row, bean.getMonth3(), numberFormate));
				sheet.addCell(new Label(7, row, bean.getMonth4(), numberFormate));
				sheet.addCell(new Label(8, row, bean.getMonth5(), numberFormate));
				sheet.addCell(new Label(9, row, bean.getMonth6(), numberFormate));
				sheet.addCell(new Label(10, row, bean.getMonth7(), numberFormate));
				sheet.addCell(new Label(11, row, bean.getMonth8(), numberFormate));
				sheet.addCell(new Label(12, row, bean.getMonth9(), numberFormate));
				sheet.addCell(new Label(13, row, bean.getMonth10(), numberFormate));
				sheet.addCell(new Label(14, row, bean.getMonth11(), numberFormate));
				sheet.addCell(new Label(15, row, bean.getMonth12(), numberFormate));
				sheet.addCell(new Label(16, row, bean.getAttr4(), numberFormate));
				row++;
			}
		}
		return row;
	}
	
	/**
	 * 添加美容/美发师人均产出
	 * @param sheet
	 * @param row
	 * @param status
	 * @param title
	 * @param titleFormate
	 * @param numberFormate
	 * @throws Exception
	 */
	public int addOutputGrid(WritableSheet sheet, int row, int status, Map<Integer, String> header, 
					WritableCellFormat titleFormate, WritableCellFormat numberFormate) throws Exception{
		row++;
		//添加标题
		for (Map.Entry<Integer, String> entry : header.entrySet()) {
			sheet.addCell(new Label(entry.getKey(), row, entry.getValue(), titleFormate));
		}
		sheet.mergeCells(0, row, 1, row);
		//添加月份标题
		for (int i = 1; i <= 12; i++) {
			sheet.addCell(new Label(i+2, row, (i+"月"),titleFormate));
		}
		row++;
		//填充数据
		List<SC019Bean> list = null;
		if(sysStatus==2){
			list = this.sc019Service.loadFaceSet(strCurCompId, strDate, status);
		}else if(sysStatus==3){
			list = this.sc019Service.loadHairSet(strCurCompId, strDate, status);
		}
		if(list!=null&& list.size()>0){
			for (SC019Bean bean : list) {
				sheet.addCell(new Label(0, row, bean.getAttr1()));
				sheet.mergeCells(0, row, 1, row);
				sheet.addCell(new Label(2, row, bean.getAttr2(), numberFormate));
				sheet.addCell(new Label(3, row, bean.getMonth1(), numberFormate));
				sheet.addCell(new Label(4, row, bean.getMonth2(), numberFormate));
				sheet.addCell(new Label(5, row, bean.getMonth3(), numberFormate));
				sheet.addCell(new Label(6, row, bean.getMonth4(), numberFormate));
				sheet.addCell(new Label(7, row, bean.getMonth5(), numberFormate));
				sheet.addCell(new Label(8, row, bean.getMonth6(), numberFormate));
				sheet.addCell(new Label(9, row, bean.getMonth7(), numberFormate));
				sheet.addCell(new Label(10, row, bean.getMonth8(), numberFormate));
				sheet.addCell(new Label(11, row, bean.getMonth9(), numberFormate));
				sheet.addCell(new Label(12, row, bean.getMonth10(), numberFormate));
				sheet.addCell(new Label(13, row, bean.getMonth11(), numberFormate));
				sheet.addCell(new Label(14, row, bean.getMonth12(), numberFormate));
				sheet.addCell(new Label(15, row, bean.getAttr3(), numberFormate));
				sheet.addCell(new Label(16, row, bean.getAttr4(), numberFormate));
				sheet.addCell(new Label(17, row, bean.getAttr5(), numberFormate));
				row++;
			}
		}
		return row;
	}
	
	/**
	 * 美容部员工业绩
	 * @param sheet
	 * @param row
	 * @param status
	 * @param title
	 * @param titleFormate
	 * @param numberFormate
	 * @throws Exception
	 */
	public int addFaceEmpGrid(WritableSheet sheet, int row, int status, Map<Integer, String> header, 
					WritableCellFormat titleFormate, WritableCellFormat numberFormate) throws Exception{
		row++;
		//添加标题
		for (Map.Entry<Integer, String> entry : header.entrySet()) {
			sheet.addCell(new Label(entry.getKey(), row, entry.getValue(), titleFormate));
		}
		sheet.mergeCells(0, row, 1, row);
		//添加月份标题
		for (int i = 1; i <= 12; i++) {
			sheet.addCell(new Label(i+5, row, (i+"月"),titleFormate));
		}
		row++;
		//填充数据
		List<SC019Bean> list = this.sc019Service.loadFaceSet(strCurCompId, strDate, status);
		if(list!=null&& list.size()>0){
			for (SC019Bean bean : list) {
				sheet.addCell(new Label(0, row, bean.getAttr1()));
				sheet.mergeCells(0, row, 1, row);
				sheet.addCell(new Label(2, row, bean.getAttr2(), numberFormate));
				sheet.addCell(new Label(3, row, bean.getAttr3(), numberFormate));
				sheet.addCell(new Label(4, row, bean.getAttr4(), numberFormate));
				sheet.addCell(new Label(5, row, bean.getAttr5(), numberFormate));
				sheet.addCell(new Label(6, row, bean.getMonth1(), numberFormate));
				sheet.addCell(new Label(7, row, bean.getMonth2(), numberFormate));
				sheet.addCell(new Label(8, row, bean.getMonth3(), numberFormate));
				sheet.addCell(new Label(9, row, bean.getMonth4(), numberFormate));
				sheet.addCell(new Label(10, row, bean.getMonth5(), numberFormate));
				sheet.addCell(new Label(11, row, bean.getMonth6(), numberFormate));
				sheet.addCell(new Label(12, row, bean.getMonth7(), numberFormate));
				sheet.addCell(new Label(13, row, bean.getMonth8(), numberFormate));
				sheet.addCell(new Label(14, row, bean.getMonth9(), numberFormate));
				sheet.addCell(new Label(15, row, bean.getMonth10(), numberFormate));
				sheet.addCell(new Label(16, row, bean.getMonth11(), numberFormate));
				sheet.addCell(new Label(17, row, bean.getMonth12(), numberFormate));
				row++;
			}
		}
		return row;
	}
	
	/**
	 * 美发部员工业绩
	 * @param sheet
	 * @param row
	 * @param status
	 * @param title
	 * @param titleFormate
	 * @param numberFormate
	 * @throws Exception
	 */
	public int addHairEmpGrid(WritableSheet sheet, int row, int status, Map<Integer, String> header, 
					WritableCellFormat titleFormate, WritableCellFormat numberFormate) throws Exception{
		row++;
		//添加标题
		for (Map.Entry<Integer, String> entry : header.entrySet()) {
			sheet.addCell(new Label(entry.getKey(), row, entry.getValue(), titleFormate));
		}
		sheet.mergeCells(0, row, 1, row);
		//添加月份标题
		for (int i = 1; i <= 12; i++) {
			sheet.addCell(new Label(i+7, row, (i+"月"),titleFormate));
		}
		row++;
		//填充数据
		List<SC019Bean> list = this.sc019Service.loadHairSet(strCurCompId, strDate, status);
		if(list!=null&& list.size()>0){
			for (SC019Bean bean : list) {
				sheet.addCell(new Label(0, row, bean.getAttr1()));
				sheet.mergeCells(0, row, 1, row);
				sheet.addCell(new Label(2, row, bean.getAttr2(), numberFormate));
				sheet.addCell(new Label(3, row, bean.getAttr3(), numberFormate));
				sheet.addCell(new Label(4, row, bean.getAttr4(), numberFormate));
				sheet.addCell(new Label(5, row, bean.getAttr5(), numberFormate));
				sheet.addCell(new Label(6, row, bean.getAttr6(), numberFormate));
				sheet.addCell(new Label(7, row, bean.getAttr7(), numberFormate));
				sheet.addCell(new Label(8, row, bean.getMonth1(), numberFormate));
				sheet.addCell(new Label(9, row, bean.getMonth2(), numberFormate));
				sheet.addCell(new Label(10, row, bean.getMonth3(), numberFormate));
				sheet.addCell(new Label(11, row, bean.getMonth4(), numberFormate));
				sheet.addCell(new Label(12, row, bean.getMonth5(), numberFormate));
				sheet.addCell(new Label(13, row, bean.getMonth6(), numberFormate));
				sheet.addCell(new Label(14, row, bean.getMonth7(), numberFormate));
				sheet.addCell(new Label(15, row, bean.getMonth8(), numberFormate));
				sheet.addCell(new Label(16, row, bean.getMonth9(), numberFormate));
				sheet.addCell(new Label(17, row, bean.getMonth10(), numberFormate));
				sheet.addCell(new Label(18, row, bean.getMonth11(), numberFormate));
				sheet.addCell(new Label(19, row, bean.getMonth12(), numberFormate));
				row++;
			}
		}
		return row;
	}
	
	public void createVipExcel() throws WriteException, IOException{
		try{
			int excelLength0=23;
			//创建excel工作簿
			WritableWorkbook workbook= Workbook.createWorkbook(os);
			
			//创建新的一页
			WritableSheet sheet =workbook.createSheet("会员分层报表", 0);
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
			Label head=new Label(0,0,"会员分层报表",titleFormate);
			sheet.setRowView(0, 500,false);
			sheet.addCell(head);
			
			//制表日期：
			Label makeDate=new Label(0,1,"制表日期：",titleFormate);
			sheet.addCell(makeDate);
			
			//制表日期值
			Label makeDateData=new Label(1, 1, CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet.addCell(makeDateData);
			Label label=new Label(0,2,"编号",titleFormate);
			sheet.addCell(label);
			sheet.mergeCells(0, 2, 0, 3); 
			label=new Label(1,2,"名称",titleFormate);
			sheet.addCell(label);
			sheet.mergeCells(1, 2, 1, 3); 
			label=new Label(2,2,"部门",titleFormate);
			sheet.addCell(label);
			sheet.mergeCells(2, 2, 2, 3); 
			
			label=new Label(3,2,"会员数量",titleFormate);
			sheet.addCell(label);
			sheet.mergeCells(3, 2, 8, 2);
			label=new Label(9,2,"顾客分层管理",titleFormate);
			sheet.addCell(label);
			sheet.mergeCells(9, 2, 22, 2);
			
			label=new Label(3,3,"新增会员",titleFormate);
			sheet.addCell(label);
			label=new Label(4,3,"在册会员",titleFormate);
			sheet.addCell(label);
			label=new Label(5,3,"常到会员",titleFormate);
			sheet.addCell(label);
			label=new Label(6,3,"有效会员",titleFormate);
			sheet.addCell(label);
			label=new Label(7,3,"沉睡会员",titleFormate);
			sheet.addCell(label);
			label=new Label(8,3,"流失会员",titleFormate);
			sheet.addCell(label);
			
			label=new Label(9,3,"大客户",titleFormate);
			sheet.addCell(label);
			label=new Label(10,3,"总金额",titleFormate);
			sheet.addCell(label);
			label=new Label(11,3,"A+",titleFormate);
			sheet.addCell(label);
			label=new Label(12,3,"总金额",titleFormate);
			sheet.addCell(label);
			label=new Label(13,3,"A",titleFormate);
			sheet.addCell(label);
			label=new Label(14,3,"总金额",titleFormate);
			sheet.addCell(label);
			label=new Label(15,3,"B",titleFormate);
			sheet.addCell(label);
			label=new Label(16,3,"总金额",titleFormate);
			sheet.addCell(label);
			label=new Label(17,3,"C",titleFormate);
			sheet.addCell(label);
			label=new Label(18,3,"总金额",titleFormate);
			sheet.addCell(label);
			label=new Label(19,3,"D",titleFormate);
			sheet.addCell(label);
			label=new Label(20,3,"总金额",titleFormate);
			sheet.addCell(label);
			label=new Label(21,3,"E",titleFormate);
			sheet.addCell(label);
			label=new Label(22,3,"总金额",titleFormate);
			sheet.addCell(label);
			this.lsDataSet=this.sc019Service.loadVipSet(strCurCompId, strDate);
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0){
				int row=4;
				for (SC019Bean bean : lsDataSet) {
					sheet.addCell(new Label(0, row, bean.getAttr1()));
					sheet.addCell(new Label(1, row, bean.getAttr2()));
					sheet.addCell(new Label(2, row, bean.getAttr3()));
					sheet.addCell(new Label(3, row, bean.getAttr4(), numberFormate));
					sheet.addCell(new Label(4, row, bean.getAttr5(), numberFormate));
					sheet.addCell(new Label(5, row, bean.getAttr6(), numberFormate));
					sheet.addCell(new Label(6, row, bean.getAttr7(), numberFormate));
					sheet.addCell(new Label(7, row, bean.getAttr8(), numberFormate));
					sheet.addCell(new Label(8, row, bean.getAttr9(), numberFormate));
					sheet.addCell(new Label(9, row, bean.getAttr10(), numberFormate));
					sheet.addCell(new Label(10, row, bean.getMonth1(), numberFormate));
					sheet.addCell(new Label(11, row, bean.getMonth2(), numberFormate));
					sheet.addCell(new Label(12, row, bean.getMonth3(), numberFormate));
					sheet.addCell(new Label(13, row, bean.getMonth4(), numberFormate));
					sheet.addCell(new Label(14, row, bean.getMonth5(), numberFormate));
					sheet.addCell(new Label(15, row, bean.getMonth6(), numberFormate));
					sheet.addCell(new Label(16, row, bean.getMonth7(), numberFormate));
					sheet.addCell(new Label(17, row, bean.getMonth8(), numberFormate));
					sheet.addCell(new Label(18, row, bean.getMonth9(), numberFormate));
					sheet.addCell(new Label(19, row, bean.getMonth10(), numberFormate));
					sheet.addCell(new Label(20, row, bean.getMonth11(), numberFormate));
					sheet.addCell(new Label(21, row, bean.getMonth12(), numberFormate));
					sheet.addCell(new Label(22, row, bean.getAttr11(), numberFormate));
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
	
	public void detailExcel() throws WriteException, IOException{
		try{
			int excelLength0=21;
			//创建excel工作簿
			WritableWorkbook workbook= Workbook.createWorkbook(os);
			
			//创建新的一页
			WritableSheet sheet =workbook.createSheet("会员明细报表", 0);
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
			Label head=new Label(0,0,"会员明细报表",titleFormate);
			sheet.setRowView(0, 500,false);
			sheet.addCell(head);
			
			//制表日期：
			Label makeDate=new Label(0,1,"制表日期：",titleFormate);
			sheet.addCell(makeDate);
			
			//制表日期值
			Label makeDateData=new Label(1, 1, CommonTool.getDateMask(CommonTool.getCurrDate()));
			sheet.addCell(makeDateData);
			Label label=new Label(0,2,"门店",titleFormate);
			sheet.addCell(label);
			label=new Label(1,2,"序号",titleFormate);
			sheet.addCell(label);
			label=new Label(2,2,"姓名",titleFormate);
			sheet.addCell(label);
			label=new Label(3,2,"性别",titleFormate);
			sheet.addCell(label);
			label=new Label(4,2,"年龄",titleFormate);
			sheet.addCell(label);
			label=new Label(5,2,"经济情况",titleFormate);
			sheet.addCell(label);
			label=new Label(6,2,"注册时间",titleFormate);
			sheet.addCell(label);
			label=new Label(7,2,"去年消费金额",titleFormate);
			sheet.addCell(label);
			label=new Label(8,2,"消费项目",titleFormate);
			sheet.addCell(label);
			label=new Label(9,2,"去年层级",titleFormate);
			sheet.addCell(label);
			label=new Label(10,2,"今年消费金额",titleFormate);
			sheet.addCell(label);
			label=new Label(11,2,"今年计划",titleFormate);
			sheet.addCell(label);
			label=new Label(12,2,"目标层级",titleFormate);
			sheet.addCell(label);
			label=new Label(13,2,"现有项目",titleFormate);
			sheet.addCell(label);
			label=new Label(14,2,"疗程金额",titleFormate);
			sheet.addCell(label);
			label=new Label(15,2,"规划项目",titleFormate);
			sheet.addCell(label);
			label=new Label(16,2,"预计出单时间",titleFormate);
			sheet.addCell(label);
			label=new Label(17,2,"可能障碍",titleFormate);
			sheet.addCell(label);
			label=new Label(18,2,"攻克方法",titleFormate);
			sheet.addCell(label);
			label=new Label(19,2,"负责美容师",titleFormate);
			sheet.addCell(label);
			label=new Label(20,2,"其他备注",titleFormate);
			sheet.addCell(label);
			this.lsDataSet=this.sc019Service.loadDetailSet(strCurCompId, strDate);
			if(this.lsDataSet!=null&&this.lsDataSet.size()>0){
				int row=3;
				for (SC019Bean bean : lsDataSet) {
					sheet.addCell(new Label(0, row, bean.getAttr1()));
					sheet.addCell(new Label(1, row, bean.getAttr2()));
					sheet.addCell(new Label(2, row, bean.getAttr3()));
					sheet.addCell(new Label(3, row, bean.getAttr4()));
					sheet.addCell(new Label(4, row, bean.getAttr5()));
					sheet.addCell(new Label(5, row, bean.getAttr6()));
					sheet.addCell(new Label(6, row, bean.getAttr7()));
					sheet.addCell(new Label(7, row, bean.getAttr8(), numberFormate));
					sheet.addCell(new Label(8, row, bean.getAttr9()));
					sheet.addCell(new Label(9, row, bean.getAttr10()));
					sheet.addCell(new Label(10, row, bean.getMonth1(), numberFormate));
					sheet.addCell(new Label(11, row, bean.getMonth2()));
					sheet.addCell(new Label(12, row, bean.getMonth3()));
					sheet.addCell(new Label(15, row, bean.getMonth6()));
					sheet.addCell(new Label(16, row, bean.getMonth7()));
					sheet.addCell(new Label(17, row, bean.getMonth8()));
					sheet.addCell(new Label(18, row, bean.getMonth9()));
					sheet.addCell(new Label(19, row, bean.getMonth10()));
					sheet.addCell(new Label(20, row, bean.getMonth11()));
					//处理现有项目多行
					String month4 = bean.getMonth4();
					String month5 = bean.getMonth5();
					if(StringUtils.isNotBlank(month5) && StringUtils.isNotBlank(month4)){
						String[] itemset = StringUtils.split(month4, ";");
						String[] moneyset = StringUtils.split(month5, ";");
						int brow = row;
						for (int i = 0; i < moneyset.length; i++) {
							sheet.addCell(new Label(13, row, itemset[i]));
							sheet.addCell(new Label(14, row, moneyset[i], numberFormate));
							row++;
						}
						int erow = row-1;
						for (int i = 0; i < excelLength0; i++) {
							if(i!=13 && i!=14){
								sheet.mergeCells(i, brow, i, erow); 
							}
						}
					}else{
						sheet.addCell(new Label(13, row, bean.getMonth4()));
						sheet.addCell(new Label(14, row, bean.getMonth5(), numberFormate));
						row++;
					}
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
			strDate=StringUtils.replace(strDate, "-", "");
		}
		this.strDate = strDate;
	}
	public String getStrMessage() {
		return strMessage;
	}
	public void setStrMessage(String strMessage) {
		this.strMessage = strMessage;
	}
	public Integer getSysStatus() {
		return sysStatus;
	}
	public void setSysStatus(Integer sysStatus) {
		this.sysStatus = sysStatus;
	}
	public List<SC019Bean> getLsDataSet() {
		return lsDataSet;
	}
	public void setLsDataSet(List<SC019Bean> lsDataSet) {
		this.lsDataSet = lsDataSet;
	}
	public List<Companyinfo> getCompSet() {
		return compSet;
	}
	public void setCompSet(List<Companyinfo> compSet) {
		this.compSet = compSet;
	}
	@JSON(serialize=false)
	public SC019Service getSc019Service() {
		return sc019Service;
	}
	@JSON(serialize=false)
	public void setSc019Service(SC019Service sc019Service) {
		this.sc019Service = sc019Service;
	}
}
