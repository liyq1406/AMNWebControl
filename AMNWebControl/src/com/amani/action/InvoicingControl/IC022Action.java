package com.amani.action.InvoicingControl;

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

import com.amani.bean.CommonBean;
import com.amani.service.InvoicingControl.IC022Service;
import com.amani.tools.CommonTool;
import com.amani.tools.SystemFinal;

@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/ic022")
public class IC022Action {
	@Autowired
	private IC022Service ic022Service;
	private String beginDate;
	private String endDate;
	private String projectKind;
	private String strCurCompId;
	private List<CommonBean> dataSet;
	private List<CommonBean> goodsList;
	private OutputStream os;
	
		//初始化index页面
		@Action(value="loadIndex", results={@Result(name ="load_success", location="/InvoicingControl/IC022/index.jsp")}) 
		public String loadIndex() {
			//goodsList=ic022Service.queryGoodsList();
		   return SystemFinal.LOAD_SUCCESS;
		}
		
		@Action( value="loadGoods", results={@Result(type="json", name="load_success")})
		public String loadGoods() {
			try{
				goodsList = ic022Service.queryGoodsList();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		
	//查询菲灵产品订单
		@Action( value="query", results={@Result(type="json", name="load_success")})
		public String query() {
			try{
				dataSet = ic022Service.querySet(beginDate, endDate,projectKind,strCurCompId);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return SystemFinal.LOAD_SUCCESS;
		}
		
		//导出Excel查询
		@Action(value="loadExcel", results={@Result(name ="load_success", location="/InvoicingControl/IC022/ic022Excel.jsp")}) 
		public String loadExcel() {
			try{
						dataSet = ic022Service.querySet(beginDate, endDate,projectKind,strCurCompId);
					}catch(Exception ex){
						ex.printStackTrace();
					}
					return SystemFinal.LOAD_SUCCESS;
				}
				
		
		public void createExcel() throws WriteException, IOException{
			try{
				int excelLength0=9;
				//创建excel工作簿
				WritableWorkbook workbook= Workbook.createWorkbook(os);
				
				//创建新的一页
				WritableSheet sheet =workbook.createSheet("菲灵订货成交量", 0);
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
				Label head=new Label(0,0,"菲灵订货成交量",titleFormate);
				sheet.setRowView(0, 500,false);
				sheet.addCell(head);
				
				//制表日期：
				Label makeDate=new Label(0,1,"制表日期：",titleFormate);
				sheet.addCell(makeDate);
				
				//制表日期值
				Label makeDateData=new Label(1,1,CommonTool.getDateMask(CommonTool.getCurrDate()));
				sheet.addCell(makeDateData);
				Label label=new Label(0,2,"店号",titleFormate);
				sheet.addCell(label);
				label=new Label(1,2,"店名",titleFormate);
				sheet.addCell(label);
				label=new Label(2,2,"单号",titleFormate);
				sheet.addCell(label);
				label=new Label(3,2,"产品",titleFormate);
				sheet.addCell(label);
				label=new Label(4,2,"数量",titleFormate);
				sheet.addCell(label);
				label=new Label(5,2,"单价",titleFormate);
				sheet.addCell(label);
				label=new Label(6,2,"厂家结算金额",titleFormate);
				sheet.addCell(label);
				label=new Label(7,2,"门店单价",titleFormate);
				sheet.addCell(label);
				label=new Label(8,2,"门店结算金额",titleFormate);
				sheet.addCell(label);
				
				if(dataSet!=null && dataSet.size()>0){
					int row=3;
					for (CommonBean bean : dataSet) {
						sheet.addCell(new Label(0, row, bean.getAttr1()));
						sheet.addCell(new Label(1, row, bean.getAttr9()));
						sheet.addCell(new Label(2, row, bean.getAttr2()));
						sheet.addCell(new Label(3, row, bean.getAttr8()));
						sheet.addCell(new Label(4, row, bean.getAttr3()));
						sheet.addCell(new Label(5, row, bean.getAttr4()));
						sheet.addCell(new Label(6, row, bean.getAttr5()));
						sheet.addCell(new Label(7, row, bean.getAttr6()));
						sheet.addCell(new Label(8, row, bean.getAttr7()));
						row++;
					}
				}
				dataSet=null;
				workbook.write();
				workbook.close();
				os.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		public String getBeginDate() {
			return beginDate;
		}
		public void setBeginDate(String beginDate) {
			if(StringUtils.isEmpty(beginDate)){
				beginDate=CommonTool.getCurrDate();
			}else{
				beginDate=StringUtils.replace(beginDate, "-", "");
			}
			this.beginDate = beginDate;
		}
		
		public String getEndDate() {
			return endDate;
		}
		public void setEndDate(String endDate) {
			if(StringUtils.isEmpty(endDate)){
				endDate=CommonTool.getCurrDate();
			}else{
				endDate=StringUtils.replace(endDate, "-", "");
			}
			this.endDate = endDate;
		}
		@JSON(serialize=false)
		public IC022Service getIc022Service() {
			return ic022Service;
		}

		@JSON(serialize=false)
		public void setIc022Service(IC022Service ic022Service) {
			this.ic022Service = ic022Service;
		}

		public String getProjectKind() {
			return projectKind;
		}

		public void setProjectKind(String projectKind) {
			this.projectKind = projectKind;
		}

	

		public String getStrCurCompId() {
			return strCurCompId;
		}

		public void setStrCurCompId(String strCurCompId) {
			this.strCurCompId = strCurCompId;
		}

		public List<CommonBean> getDataSet() {
			return dataSet;
		}

		public void setDataSet(List<CommonBean> dataSet) {
			this.dataSet = dataSet;
		}

		public OutputStream getOs() {
			return os;
		}

		public void setOs(OutputStream os) {
			this.os = os;
		}
		public List<CommonBean> getGoodsList() {
			return goodsList;
		}

		public void setGoodsList(List<CommonBean> goodsList) {
			this.goodsList = goodsList;
		}
		
}
